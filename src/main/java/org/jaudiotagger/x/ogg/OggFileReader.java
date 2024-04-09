/*
 * Entagged Audio Tag library
 * Copyright (c) 2003-2005 Raphaël Slinckx <raphael@slinckx.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.jaudiotagger.x.ogg;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.x.AudioFileReader;
import org.jaudiotagger.x.stream.ChannelCompat;
import org.jaudiotagger.x.stream.SlideBufferFileChannel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;

/**
 * Read Ogg File Tag and Encoding information
 * <p>
 * Only implemented for ogg files containing a vorbis stream with vorbis comments
 */
public class OggFileReader extends AudioFileReader {
    // Logger Object
    public static Logger logger = Logger.getLogger("org.jaudiotagger.audio.ogg");

    private OggInfoReader ir;
    private OggVorbisTagReader vtr;

    public OggFileReader() {
        ir = new OggInfoReader();
        vtr = new OggVorbisTagReader();
    }

    @Override
    protected GenericAudioHeader getEncodingInfo(SlideBufferFileChannel raf) throws CannotReadException, IOException {
        return ir.read(raf);
    }

    @Override
    protected Tag getTag(SlideBufferFileChannel raf) throws CannotReadException, IOException {
        return vtr.read(raf);
    }

}

