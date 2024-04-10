package org.jaudiotagger.x;


import org.jaudiotagger.audio.SupportedFileFormat;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.logging.ErrorMessage;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.x.asf.AsfFileReader;
import org.jaudiotagger.x.flac.FlacFileReader;
import org.jaudiotagger.x.mp3.MP3FileReader;
import org.jaudiotagger.x.mp4.Mp4FileReader;
import org.jaudiotagger.x.ogg.OggFileReader;
import org.jaudiotagger.x.stream.ChannelCompat;
import org.jaudiotagger.x.wav.WavFileReader;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XAudioFileIO {

    private Map<String, AudioFileReader> readers = new HashMap<String, AudioFileReader>();

    private static XAudioFileIO defaultInstance;

    public static XAudioFileIO getDefaultAudioFileIO() {
        if (defaultInstance == null) {
            defaultInstance = new XAudioFileIO();
        }
        return defaultInstance;
    }

    private XAudioFileIO() {
        prepareReadersAndWriters();
    }

    private void prepareReadersAndWriters() {

        // Tag Readers
        readers.put(SupportedFileFormat.OGG.getFilesuffix(), new OggFileReader());
        readers.put(SupportedFileFormat.FLAC.getFilesuffix(), new FlacFileReader());
        readers.put(SupportedFileFormat.MP3.getFilesuffix(), new MP3FileReader());
        readers.put(SupportedFileFormat.MP4.getFilesuffix(), new Mp4FileReader());
        readers.put(SupportedFileFormat.M4A.getFilesuffix(), new Mp4FileReader());
        readers.put(SupportedFileFormat.M4P.getFilesuffix(), new Mp4FileReader());
        readers.put(SupportedFileFormat.M4B.getFilesuffix(), new Mp4FileReader());
        readers.put(SupportedFileFormat.WAV.getFilesuffix(), new WavFileReader());
        readers.put(SupportedFileFormat.WMA.getFilesuffix(), new AsfFileReader());
//        readers.put(SupportedFileFormat.AIF.getFilesuffix(), new AiffFileReader());
//        readers.put(SupportedFileFormat.AIFC.getFilesuffix(), new AiffFileReader());
//        readers.put(SupportedFileFormat.AIFF.getFilesuffix(), new AiffFileReader());
//        readers.put(SupportedFileFormat.DSF.getFilesuffix(), new DsfFileReader());
//        readers.put(SupportedFileFormat.OPUS.getFilesuffix(), new OpusFileReader());
//        readers.put(SupportedFileFormat.RA.getFilesuffix(), new RealFileReader());
//        readers.put(SupportedFileFormat.RM.getFilesuffix(), new RealFileReader());
    }

    public XAudioFile readAs(ChannelCompat f, String ext)
            throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
//      String ext = Utils.getExtension(f);

        AudioFileReader afr = readers.get(ext);
        if (afr == null) {
            throw new CannotReadException(ErrorMessage.NO_READER_FOR_THIS_FORMAT.getMsg(ext));
        }
        XAudioFile tempFile = afr.read(f);
        tempFile.setExt(ext);
        return tempFile;
    }

    public static XAudioFile read(ChannelCompat f, String ext)
            throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        return getDefaultAudioFileIO().readAs(f, ext);
    }
}
