package org.jaudiotagger.audio.ape;

import davaguine.jmac.info.APEInfo;
import davaguine.jmac.info.APETag;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.generic.AudioFileReader;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.x.ape.ApeAudioHeader;
import org.jaudiotagger.x.ape.ApeTag;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ApeFileReader extends AudioFileReader {
    @Override
    protected GenericAudioHeader getEncodingInfo(RandomAccessFile raf) throws CannotReadException, IOException {
        return null;
    }

    @Override
    protected Tag getTag(RandomAccessFile raf) throws CannotReadException, IOException {
        return null;
    }

    @Override
    public AudioFile read(File f) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        davaguine.jmac.tools.RandomAccessFile randomAccessFile = new davaguine.jmac.tools.RandomAccessFile(f, "r");
        try {
            APEInfo apeInfo = new APEInfo(randomAccessFile);
            APETag apeTag = apeInfo.getApeInfoTag();
            apeTag.GetTagBytes();
            return new AudioFile(f, new ApeAudioHeader(apeInfo), new ApeTag(apeTag));
        } finally {
            randomAccessFile.close();
        }
    }


    //    @Override
//    protected GenericAudioHeader getEncodingInfo(FileChannel raf) throws CannotReadException, IOException {
//        return null;
//    }
//
//    @Override
//    protected Tag getTag(FileChannel raf) throws CannotReadException, IOException {
//        return null;
//    }
//
//    @Override
//    public XAudioFile read(ChannelCompat f) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
//        FileChannel fileChannel = f.newFileChannel();
//        try {
//            InputStreamFileCompat inputStreamFileCompat = new InputStreamFileCompat(fileChannel, f.size());
//            APEInfo apeInfo = new APEInfo(inputStreamFileCompat);
//            XAudioFile xAudioFile = new XAudioFile(new ApeAudioHeader(apeInfo), new ApeTag(apeInfo.getApeInfoTag()));
//            return xAudioFile;
//        } finally {
//            fileChannel.close();
//        }
//    }
}
