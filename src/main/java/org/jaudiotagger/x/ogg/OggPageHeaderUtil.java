package org.jaudiotagger.x.ogg;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.ogg.util.OggPageHeader;
import org.jaudiotagger.logging.ErrorMessage;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.x.stream.FileChannelFileInputStreamV2;

import java.io.IOException;
import java.util.Arrays;

public class OggPageHeaderUtil {


    public static OggPageHeader read(FileChannelFileInputStreamV2 raf) throws IOException, CannotReadException {
        long start = raf.position();

        byte[] b = new byte[OggPageHeader.CAPTURE_PATTERN.length];
        raf.read(b);
        if (!(Arrays.equals(b, OggPageHeader.CAPTURE_PATTERN))) {
            raf.position(start);
            if (AbstractID3v2Tag.isId3Tag(raf.source)) {
                raf.read(b);
                if ((Arrays.equals(b, OggPageHeader.CAPTURE_PATTERN))) {
                    //Go to the end of the ID3 header
                    start = raf.position() - OggPageHeader.CAPTURE_PATTERN.length;
                }
            } else {
                throw new CannotReadException(ErrorMessage.OGG_HEADER_CANNOT_BE_FOUND.getMsg(new String(b)));
            }
        }

        raf.position(start + OggPageHeader.FIELD_PAGE_SEGMENTS_POS);
        int pageSegments = raf.readByte() & 0xFF; //unsigned
        raf.position(start);

        b = new byte[OggPageHeader.OGG_PAGE_HEADER_FIXED_LENGTH + pageSegments];
        raf.read(b);


        OggPageHeader pageHeader = new OggPageHeader(b);
        pageHeader.setStartByte(start);
        //Now just after PageHeader, ready for Packet Data
        return pageHeader;
    }

}
