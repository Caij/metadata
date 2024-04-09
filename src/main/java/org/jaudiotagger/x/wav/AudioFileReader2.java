package org.jaudiotagger.x.wav;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.logging.ErrorMessage;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.x.AudioFileReader;
import org.jaudiotagger.x.stream.ChannelCompat;
import org.jaudiotagger.x.stream.SlideBufferFileChannel;
import org.jaudiotagger.x.stream.SlideBufferInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Replacement for AudioFileReader class
 */
public abstract class AudioFileReader2 extends AudioFileReader {


    @Override
    public AudioFile read(ChannelCompat f) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        SlideBufferFileChannel channel = null;
        try {
            channel = f.newFileChannel();
            GenericAudioHeader info = getEncodingInfoV2(channel);
            channel.position(0);
            Tag tag = getTagV2(channel);
            return new AudioFile(new File(""), info, tag);
        } catch (IllegalArgumentException e) {
            logger.warning(ErrorMessage.GENERAL_READ_FAILED_DO_NOT_HAVE_PERMISSION_TO_READ_FILE.getMsg(f));
            throw new CannotReadException(ErrorMessage.GENERAL_READ_FAILED_DO_NOT_HAVE_PERMISSION_TO_READ_FILE.getMsg(f));
        } catch (FileNotFoundException e) {
            logger.warning("Unable to read file: " + f + " " + e.getMessage());
            throw e;
        } finally {
            if (channel != null) channel.close();
        }
    }

    /**
     * Read Encoding Information
     *
     * @param channel
     * @return
     * @throws CannotReadException
     * @throws IOException
     */
    protected abstract GenericAudioHeader getEncodingInfoV2(SlideBufferFileChannel channel) throws CannotReadException, IOException;

    @Override
    protected GenericAudioHeader getEncodingInfo(SlideBufferFileChannel raf) {
        throw new UnsupportedOperationException("Old method not used in version 2");
    }

    /**
     * Read tag Information
     *
     * @param channel
     * @return
     * @throws CannotReadException
     * @throws IOException
     */
    protected abstract Tag getTagV2(SlideBufferFileChannel channel) throws CannotReadException, IOException;

    @Override
    protected Tag getTag(SlideBufferFileChannel file) {
        throw new UnsupportedOperationException("Old method not used in version 2");
    }
}
