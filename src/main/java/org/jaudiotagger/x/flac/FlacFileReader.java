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
package org.jaudiotagger.x.flac;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.x.stream.SlideBufferFileChannel;
import org.jaudiotagger.x.wav.AudioFileReader2;

import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Read encoding and tag info for Flac file (open source lossless encoding)
 */
public class FlacFileReader extends AudioFileReader2 {

    private FlacInfoReader ir = new FlacInfoReader();
    private FlacTagReader tr = new FlacTagReader();

    protected GenericAudioHeader getEncodingInfoV2(SlideBufferFileChannel channel) throws CannotReadException, IOException {
        return ir.read(channel);
    }

    protected Tag getTagV2(SlideBufferFileChannel channel) throws CannotReadException, IOException {
        return tr.read(channel);
    }

}
