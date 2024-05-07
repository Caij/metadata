package org.jaudiotagger.x.ape;

import davaguine.jmac.info.APEInfo;
import davaguine.jmac.info.APETag;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.x.AudioFileReader;
import org.jaudiotagger.x.XAudioFile;
import org.jaudiotagger.x.stream.ChannelCompat;

import java.io.IOException;
import java.nio.channels.FileChannel;

public class ApeFileReader extends AudioFileReader {
    @Override
    protected GenericAudioHeader getEncodingInfo(FileChannel raf) throws CannotReadException, IOException {
        return null;
    }

    @Override
    protected Tag getTag(FileChannel raf) throws CannotReadException, IOException {
        return null;
    }

    @Override
    public XAudioFile read(ChannelCompat f) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        FileChannel fileChannel = f.newFileChannel();
        try {
            InputStreamFileCompat inputStreamFileCompat = new InputStreamFileCompat(fileChannel, f.size());
            APEInfo apeInfo = new APEInfo(inputStreamFileCompat);
            APETag apeTag = apeInfo.getApeInfoTag();
            apeTag.GetTagBytes();
            return new XAudioFile(new ApeAudioHeader(apeInfo), new ApeTag(apeTag));
        } finally {
            fileChannel.close();
        }
    }
}
