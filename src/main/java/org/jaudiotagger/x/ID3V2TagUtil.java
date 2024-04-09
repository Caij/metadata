package org.jaudiotagger.x;

import org.jaudiotagger.audio.asf.data.AsfHeader;
import org.jaudiotagger.audio.asf.io.AsfHeaderReader;
import org.jaudiotagger.audio.asf.io.FullRequestInputStream;
import org.jaudiotagger.audio.asf.io.RandomAccessFileInputstream;
import org.jaudiotagger.audio.asf.util.Utils;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.logging.ErrorMessage;
import org.jaudiotagger.tag.TagNotFoundException;
import org.jaudiotagger.tag.id3.*;
import org.jaudiotagger.x.stream.FileChannelFileInputstream;
import org.jaudiotagger.x.stream.SlideBufferFileChannel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class ID3V2TagUtil {

    public static long getV2TagSizeIfExists(FileChannel fc) throws IOException {
        ByteBuffer bb = null;
        //Read possible Tag header  Byte Buffer
        bb = ByteBuffer.allocate(AbstractID3v2Tag.TAG_HEADER_LENGTH);
        fc.read(bb);
        bb.flip();
        if (bb.limit() < (AbstractID3v2Tag.TAG_HEADER_LENGTH)) {
            return 0;
        }

        //ID3 identifier
        byte[] tagIdentifier = new byte[AbstractID3v2Tag.FIELD_TAGID_LENGTH];
        bb.get(tagIdentifier, 0, AbstractID3v2Tag.FIELD_TAGID_LENGTH);
        if (!(Arrays.equals(tagIdentifier, AbstractID3v2Tag.TAG_ID))) {
            return 0;
        }

        //Is it valid Major Version
        byte majorVersion = bb.get();
        if ((majorVersion != ID3v22Tag.MAJOR_VERSION) && (majorVersion != ID3v23Tag.MAJOR_VERSION) && (majorVersion != ID3v24Tag.MAJOR_VERSION)) {
            return 0;
        }

        //Skip Minor Version
        bb.get();

        //Skip Flags
        bb.get();

        //Get size as recorded in frame header
        int frameSize = ID3SyncSafeInteger.bufferToValue(bb);

        //addField header size to frame size
        frameSize += AbstractID3v2Tag.TAG_HEADER_LENGTH;
        return frameSize;
    }

    protected static final int TAG_LENGTH = 128;
    public static ID3v1Tag createID3v1Tag(FileChannel fc) throws IOException, TagNotFoundException {
        ID3v1Tag id3v1Tag = new ID3v1Tag();
        ByteBuffer byteBuffer;
        fc.position(fc.size() - TAG_LENGTH);
        byteBuffer = ByteBuffer.allocate(TAG_LENGTH);
        fc.read(byteBuffer);
        byteBuffer.flip();
        id3v1Tag.read(byteBuffer);
        return id3v1Tag;
    }

    public static ID3v1Tag createID3v11Tag(FileChannel fc) throws IOException, TagNotFoundException {
        ID3v11Tag id3v11Tag = new ID3v11Tag();
        ByteBuffer byteBuffer = ByteBuffer.allocate(TAG_LENGTH);

        fc.position(fc.size() - TAG_LENGTH);

        fc.read(byteBuffer);
        byteBuffer.flip();
        id3v11Tag.read(byteBuffer);
        return id3v11Tag;
    }

    public static MP3AudioHeader MP3AudioHeader(final FileChannel fileChannel, long startByte) throws IOException, InvalidAudioFrameException {
        MP3AudioHeader mp3AudioHeader = new MP3AudioHeader();
        if (!mp3AudioHeader.seek(fileChannel, startByte)) {
            throw new InvalidAudioFrameException(ErrorMessage.NO_AUDIO_HEADER_FOUND.getMsg(""));
        }
        return mp3AudioHeader;
    }

    private static InputStream createStream(final SlideBufferFileChannel raf) {
        return new FullRequestInputStream(new BufferedInputStream(new FileChannelFileInputstream(raf)));
    }

    public static AsfHeader readInfoHeader(final SlideBufferFileChannel file) throws IOException {
        final InputStream stream = createStream(file);
        return AsfHeaderReader.INFO_READER.read(Utils.readGUID(stream), stream, 0);
    }

    public static AsfHeader readTagHeader(final SlideBufferFileChannel file) throws IOException {
        final InputStream stream = createStream(file);
        return AsfHeaderReader.TAG_READER.read(Utils.readGUID(stream), stream, 0);
    }

}
