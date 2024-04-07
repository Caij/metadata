package com.caij.puremusic.media.util.metadata.flac;

import com.caij.puremusic.media.util.metadata.*;
import com.caij.puremusic.media.util.metadata.data.Format;
import com.caij.puremusic.media.util.metadata.data.MediaMetadata;
import com.caij.puremusic.media.util.metadata.data.Metadata;

import java.io.IOException;

import static java.lang.Math.max;

public class FlacReader implements MetadataReader {

    private static final int STATE_READ_ID3_METADATA = 0;
    private static final int STATE_GET_STREAM_MARKER_AND_INFO_BLOCK_BYTES = 1;
    private static final int STATE_READ_STREAM_MARKER = 2;
    private static final int STATE_READ_METADATA_BLOCKS = 3;
    private static final int STATE_GET_FRAME_START_MARKER = 4;

    private Metadata id3Metadata;
    private int state;
    private final byte[] streamMarkerAndInfoBlock;
    private FlacStreamMetadata flacStreamMetadata;
    private int minFrameSize;
    private Format format;

    public FlacReader() {
        streamMarkerAndInfoBlock =
                new byte[FlacConstants.STREAM_MARKER_SIZE + FlacConstants.STREAM_INFO_BLOCK_SIZE];
        state = STATE_READ_ID3_METADATA;
    }

    @Override
    public MetadataWrapper read(ExtractorInput extractorInput) throws IOException {
        readId3Metadata(extractorInput);
        getStreamMarkerAndInfoBlockBytes(extractorInput);
        readStreamMarker(extractorInput);
        readMetadataBlocks(extractorInput);
        if (format != null) {
            MediaMetadata newMetadata = MetadataUtil
                    .populateFromMetadata(new MediaMetadata.Builder(), format.metadata)
                    .build();
            return MetadataUtil.parse(format, newMetadata, Util.usToMs(flacStreamMetadata.getDurationUs()));
        }
        return null;
    }

    private void readId3Metadata(ExtractorInput input) throws IOException {
        id3Metadata = FlacMetadataReader.readId3Metadata(input, /* parseData= */ true);
        state = STATE_GET_STREAM_MARKER_AND_INFO_BLOCK_BYTES;
    }

    private void getStreamMarkerAndInfoBlockBytes(ExtractorInput input) throws IOException {
        input.peekFully(streamMarkerAndInfoBlock, 0, streamMarkerAndInfoBlock.length);
        input.resetPeekPosition();
        state = STATE_READ_STREAM_MARKER;
    }

    private void readStreamMarker(ExtractorInput input) throws IOException {
        FlacMetadataReader.readStreamMarker(input);
        state = STATE_READ_METADATA_BLOCKS;
    }

    private void readMetadataBlocks(ExtractorInput input) throws IOException {
        boolean isLastMetadataBlock = false;
        FlacMetadataReader.FlacStreamMetadataHolder metadataHolder =
                new FlacMetadataReader.FlacStreamMetadataHolder(flacStreamMetadata);
        while (!isLastMetadataBlock) {
            isLastMetadataBlock = FlacMetadataReader.readMetadataBlock(input, metadataHolder);
            // Save the current metadata in case an exception occurs.
            flacStreamMetadata = Util.castNonNull(metadataHolder.flacStreamMetadata);
        }

        Util.checkNotNull(flacStreamMetadata);
        minFrameSize = max(flacStreamMetadata.minFrameSize, FlacConstants.MIN_FRAME_HEADER_SIZE);
        this.format = flacStreamMetadata.getFormat(streamMarkerAndInfoBlock, id3Metadata);

        state = STATE_GET_FRAME_START_MARKER;
    }
}
