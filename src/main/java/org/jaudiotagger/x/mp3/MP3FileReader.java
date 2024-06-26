package org.jaudiotagger.x.mp3;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.x.AudioFileReader;
import org.jaudiotagger.x.XAudioFile;
import org.jaudiotagger.x.stream.ChannelCompat;
import org.jaudiotagger.x.stream.SlideBufferFileChannel;

import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Read Mp3 Info (retrofitted to entagged ,done differently to entagged which is why some methods throw RuntimeException)
 * because done elsewhere
 */
public class MP3FileReader extends AudioFileReader {

    @Override
    protected GenericAudioHeader getEncodingInfo(FileChannel raf) {
        throw new RuntimeException("MP3FileReader.getEncodingInfo should be called");
    }

    @Override
    protected Tag getTag(FileChannel raf) {
        throw new RuntimeException("MP3FileReader.getEncodingInfo should be called");
    }

    /**
     * @param f
     * @return
     */
    //Override because we read mp3s differently to the entagged code
    public XAudioFile read(ChannelCompat f) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        FileChannel slideBufferFileChannel = null;
        try {
            slideBufferFileChannel = f.newFileChannel();
            XMP3File mp3File = new XMP3File(slideBufferFileChannel, MP3File.LOAD_IDV1TAG | MP3File.LOAD_IDV2TAG, true);
            return mp3File;
        } finally {
            if (slideBufferFileChannel != null) slideBufferFileChannel.close();
        }
    }

}
