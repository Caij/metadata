package com.caij.puremusic.media.util.metadata.mp3;

import com.caij.puremusic.media.util.metadata.*;
import com.caij.puremusic.media.util.metadata.data.Format;
import com.caij.puremusic.media.util.metadata.data.MediaMetadata;
import com.caij.puremusic.media.util.metadata.data.Metadata;
import com.caij.puremusic.media.util.metadata.data.id3.Id3Decoder;
import com.caij.puremusic.media.util.metadata.data.id3.Id3Peeker;
import com.caij.puremusic.media.util.metadata.data.id3.MlltFrame;
import com.caij.puremusic.media.util.metadata.data.id3.TextInformationFrame;

import java.io.EOFException;
import java.io.IOException;

import static java.lang.Math.max;

public class Mp3Reader implements MetadataReader {

    /** The maximum number of bytes to search when synchronizing, before giving up. */
    private static final int MAX_SYNC_BYTES = 128 * 1024;

    /**
     * The maximum number of bytes to peek when sniffing, excluding the ID3 header, before giving up.
     */
    private static final int MAX_SNIFF_BYTES = 32 * 1024;

    private static final int SCRATCH_LENGTH = 10;

    private static final int MPEG_AUDIO_HEADER_MASK = 0xFFFE0C00;

    private final Id3Peeker id3Peeker;
    private final GaplessInfoHolder gaplessInfoHolder;
    private final MpegAudioUtil.Header synchronizedHeader;

    private Metadata metadata;

    private final ParsableByteArray scratch;
    private int synchronizedHeaderData;

    private long basisTimeUs;

    private int sampleBytesRemaining;

    private static final int SEEK_HEADER_XING = 0x58696e67;
    private static final int SEEK_HEADER_INFO = 0x496e666f;
    private static final int SEEK_HEADER_VBRI = 0x56425249;
    private static final int SEEK_HEADER_UNSET = 0;

    public Mp3Reader() {
        id3Peeker = new Id3Peeker();
        gaplessInfoHolder = new GaplessInfoHolder();
        scratch = new ParsableByteArray(SCRATCH_LENGTH);
        synchronizedHeader = new MpegAudioUtil.Header();
        basisTimeUs = Util.TIME_UNSET;
    }


    @Override
    public MetadataWrapper read(ExtractorInput extractorInput) throws IOException {
        if (synchronizedHeaderData == 0) {
            try {
                synchronize(extractorInput, false);
            } catch (EOFException e) {
                return null;
            }
        }

        long durationUs = maybeReadSeekFrame(extractorInput);
        if (durationUs == Util.TIME_UNSET) {
            durationUs = maybeHandleSeekMetadata(metadata, extractorInput.getPosition());
        }

        if (durationUs == Util.TIME_UNSET) {
            extractorInput.peekFully(scratch.getData(), 0, 4);
            scratch.setPosition(0);
            synchronizedHeader.setForHeaderData(scratch.readInt());
            durationUs = getTimeUsAtPosition(extractorInput.getLength(), extractorInput.getPosition(), synchronizedHeader.bitrate);
        }

        long durationMs = -1;
        if (durationUs != Util.TIME_UNSET) {
            durationMs = Util.usToMs(durationUs);
        }

        if (metadata != null) {
            MediaMetadata newMetadata = MetadataUtil
                    .populateFromMetadata(new MediaMetadata.Builder(), metadata)
                    .build();
            Format format = new Format.Builder()
                    .setSampleMimeType(synchronizedHeader.mimeType)
                    .setMaxInputSize(MpegAudioUtil.MAX_FRAME_SIZE_BYTES)
                    .setChannelCount(synchronizedHeader.channels)
                    .setSampleRate(synchronizedHeader.sampleRate)
                    .setEncoderDelay(gaplessInfoHolder.encoderDelay)
                    .setEncoderPadding(gaplessInfoHolder.encoderPadding)
                    .setMetadata(metadata)
                    .build();
            return MetadataUtil.parse(format, newMetadata, durationMs);
        }
        return null;
    }

    private static long maybeHandleSeekMetadata(
            Metadata metadata, long firstFramePosition) {
        if (metadata != null) {
            int length = metadata.length();
            for (int i = 0; i < length; i++) {
                Metadata.Entry entry = metadata.get(i);
                if (entry instanceof MlltFrame) {
                    return  getId3TlenUs(metadata);
                }
            }
        }
        return Util.TIME_UNSET;
    }

    private long maybeReadSeekFrame(ExtractorInput input) throws IOException {
        ParsableByteArray frame = new ParsableByteArray(synchronizedHeader.frameSize);
        input.peekFully(frame.getData(), 0, synchronizedHeader.frameSize);
        int xingBase =
                (synchronizedHeader.version & 1) != 0
                        ? (synchronizedHeader.channels != 1 ? 36 : 21) // MPEG 1
                        : (synchronizedHeader.channels != 1 ? 21 : 13); // MPEG 2 or 2.5
        int seekHeader = getSeekFrameHeader(frame, xingBase);
        long durationUs = Util.TIME_UNSET;
        switch (seekHeader) {
            case SEEK_HEADER_XING:
            case SEEK_HEADER_INFO:
                XingFrame xingFrame = XingFrame.parse(synchronizedHeader, frame);
                if (!gaplessInfoHolder.hasGaplessInfo()
                        && xingFrame.encoderDelay != Util.LENGTH_UNSET
                        && xingFrame.encoderPadding != Util.LENGTH_UNSET) {
                    gaplessInfoHolder.encoderDelay = xingFrame.encoderDelay;
                    gaplessInfoHolder.encoderPadding = xingFrame.encoderPadding;
                }
                long startPosition = input.getPosition();
                input.skipFully(synchronizedHeader.frameSize);
                // An Xing frame indicates the file is VBR (so we have to use the seek header for seeking)
                // while an Info header indicates the file is CBR, in which case ConstantBitrateSeeker will
                // give more accurate seeking than the low-resolution seek table in the Info header. We can
                // still use the length from the Info frame if we don't know the stream length directly.
                if (seekHeader == SEEK_HEADER_XING) {
                    durationUs = readXing(input.getLength(), xingFrame, startPosition);
                } else { // seekHeader == SEEK_HEADER_INFO
                    long streamLength =
                            xingFrame.dataSize != Util.LENGTH_UNSET
                                    ? startPosition + xingFrame.dataSize
                                    : Util.LENGTH_UNSET;
                    durationUs = getTimeUsAtPosition(streamLength, input.getPosition(), synchronizedHeader.bitrate);
                }
                break;
            case SEEK_HEADER_VBRI:
                durationUs = readXing(input.getLength(), input.getPosition(), synchronizedHeader, frame);
                input.skipFully(synchronizedHeader.frameSize);
                break;
            case SEEK_HEADER_UNSET:
            default:
                // This frame doesn't contain seeking information, so reset the peek position.
                input.resetPeekPosition();
        }
        return durationUs;
    }

    private static int getSeekFrameHeader(ParsableByteArray frame, int xingBase) {
        if (frame.limit() >= xingBase + 4) {
            frame.setPosition(xingBase);
            int headerData = frame.readInt();
            if (headerData == SEEK_HEADER_XING || headerData == SEEK_HEADER_INFO) {
                return headerData;
            }
        }
        if (frame.limit() >= 40) {
            frame.setPosition(36); // MPEG audio header (4 bytes) + 32 bytes.
            if (frame.readInt() == SEEK_HEADER_VBRI) {
                return SEEK_HEADER_VBRI;
            }
        }
        return SEEK_HEADER_UNSET;
    }

    private long computeTimeUs(long samplesRead) {
        return basisTimeUs + samplesRead * Util.MICROS_PER_SECOND / synchronizedHeader.sampleRate;
    }

    private boolean synchronize(ExtractorInput input, boolean sniffing) throws IOException {
        int validFrameCount = 0;
        int candidateSynchronizedHeaderData = 0;
        int peekedId3Bytes = 0;
        int searchedBytes = 0;
        int searchLimitBytes = sniffing ? MAX_SNIFF_BYTES : MAX_SYNC_BYTES;
        input.resetPeekPosition();
        if (input.getPosition() == 0) {
            // We need to parse enough ID3 metadata to retrieve any gapless/seeking playback information
            // even if ID3 metadata parsing is disabled.
            boolean parseAllId3Frames = true;
            Id3Decoder.FramePredicate id3FramePredicate = null;
            metadata = id3Peeker.peekId3Data(input, id3FramePredicate);
            if (metadata != null) {
                gaplessInfoHolder.setFromMetadata(metadata);
            }
            peekedId3Bytes = (int) input.getPeekPosition();
            if (!sniffing) {
                input.skipFully(peekedId3Bytes);
            }
        }
        while (true) {
            if (peekEndOfStreamOrHeader(input)) {
                if (validFrameCount > 0) {
                    // We reached the end of the stream but found at least one valid frame.
                    break;
                }
                throw new EOFException();
            }
            scratch.setPosition(0);
            int headerData = scratch.readInt();
            int frameSize;
            if ((candidateSynchronizedHeaderData != 0
                    && !headersMatch(headerData, candidateSynchronizedHeaderData))
                    || (frameSize = MpegAudioUtil.getFrameSize(headerData)) == Util.LENGTH_UNSET) {
                // The header doesn't match the candidate header or is invalid. Try the next byte offset.
                if (searchedBytes++ == searchLimitBytes) {
                    if (!sniffing) {
                        throw ParserException.createForMalformedContainer(
                                "Searched too many bytes.", /* cause= */ null);
                    }
                    return false;
                }
                validFrameCount = 0;
                candidateSynchronizedHeaderData = 0;
                if (sniffing) {
                    input.resetPeekPosition();
                    input.advancePeekPosition(peekedId3Bytes + searchedBytes);
                } else {
                    input.skipFully(1);
                }
            } else {
                // The header matches the candidate header and/or is valid.
                validFrameCount++;
                if (validFrameCount == 1) {
                    synchronizedHeader.setForHeaderData(headerData);
                    candidateSynchronizedHeaderData = headerData;
                } else if (validFrameCount == 4) {
                    break;
                }
                input.advancePeekPosition(frameSize - 4);
            }
        }
        // Prepare to read the synchronized frame.
        if (sniffing) {
            input.skipFully(peekedId3Bytes + searchedBytes);
        } else {
            input.resetPeekPosition();
        }
        synchronizedHeaderData = candidateSynchronizedHeaderData;
        return true;
    }

    private boolean peekEndOfStreamOrHeader(ExtractorInput extractorInput) throws IOException {
        try {
            return !extractorInput.peekFully(
                    scratch.getData(), /* offset= */ 0, /* length= */ 4, /* allowEndOfInput= */ true);
        } catch (EOFException e) {
            return true;
        }
    }

    private static boolean headersMatch(int headerA, long headerB) {
        return (headerA & MPEG_AUDIO_HEADER_MASK) == (headerB & MPEG_AUDIO_HEADER_MASK);
    }


    private static long getTimeUsAtPosition(long position, long firstFrameBytePosition, int bitrate) {
        return max(0, position - firstFrameBytePosition)
                * Util.BITS_PER_BYTE
                * Util.MICROS_PER_SECOND
                / bitrate;
    }

    private static long readXing(long inputLength,
                                 long position,
                                 MpegAudioUtil.Header mpegAudioHeader,
                                 ParsableByteArray frame) {
        frame.skipBytes(10);
        int numFrames = frame.readInt();
        if (numFrames <= 0) {
            return Util.TIME_UNSET;
        }
        int sampleRate = mpegAudioHeader.sampleRate;
        long durationUs =
                Util.scaleLargeTimestamp(
                        numFrames, Util.MICROS_PER_SECOND * (sampleRate >= 32000 ? 1152 : 576), sampleRate);
        return durationUs;
    }

    private static long readXing(long inputLength, XingFrame xingFrame, long position) {
        if (xingFrame.frameCount == Util.LENGTH_UNSET && xingFrame.frameCount == 0) {
            // If the frame count is missing/invalid, the header can't be used to determine the duration.
            return Util.TIME_UNSET;
        }
        // TODO: b/319235116 - Handle encoder delay and padding when calculating duration.
        // Audio requires both a start and end PCM sample, so subtract one from the sample count before
        // calculating the duration.
        long durationUs =
                Util.sampleCountToDurationUs(
                        (xingFrame.frameCount * xingFrame.header.samplesPerFrame) - 1,
                        xingFrame.header.sampleRate);
        return durationUs;
    }

    private static long getId3TlenUs(Metadata metadata) {
        if (metadata != null) {
            int length = metadata.length();
            for (int i = 0; i < length; i++) {
                Metadata.Entry entry = metadata.get(i);
                if (entry instanceof TextInformationFrame
                        && ((TextInformationFrame) entry).id.equals("TLEN")) {
                    return Util.msToUs(Long.parseLong(((TextInformationFrame) entry).values.get(0)));
                }
            }
        }
        return Util.TIME_UNSET;
    }
}
