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
package org.jaudiotagger.x;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.logging.ErrorMessage;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v22Tag;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.reference.ID3V2Version;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

/**
 * <p>This is the main object manipulated by the user representing an audiofile, its properties and its tag.
 * <p>The preferred way to obtain an <code>AudioFile</code> is to use the <code>AudioFileIO.read(File)</code> method.
 * <p>The <code>AudioHeader</code> contains every properties associated with the file itself (no meta-data), like the bitrate, the sampling rate, the encoding audioHeaders, etc.
 * <p>To get the meta-data contained in this file you have to get the <code>Tag</code> of this <code>AudioFile</code>
 *
 * @author Raphael Slinckx
 * @version $Id$
 * @see AudioFileIO
 * @see Tag
 * @since v0.01
 */
public class XAudioFile {
    //Logger
    public static Logger logger = Logger.getLogger("org.jaudiotagger.audio");

    /**
     * The physical file that this instance represents.
     */

    /**
     * The Audio header info
     */
    protected AudioHeader audioHeader;

    /**
     * The tag
     */
    protected Tag tag;

    /**
     * The tag
     */
    protected String extension;

    public XAudioFile() {

    }

    /**
     * <p>These constructors are used by the different readers, users should not use them, but use the <code>AudioFileIO.read(File)</code> method instead !.
     * <p>Create the AudioFile representing file f, the encoding audio headers and containing the tag
     *
     * @param audioHeader the encoding audioHeaders over this file
     * @param tag         the tag contained in this file or null if no tag exists
     */
    public XAudioFile(AudioHeader audioHeader, Tag tag) {
        this.audioHeader = audioHeader;
        this.tag = tag;
    }


    /**
     * <p>These constructors are used by the different readers, users should not use them, but use the <code>AudioFileIO.read(File)</code> method instead !.
     * <p>Create the AudioFile representing file denoted by pathnames, the encoding audio Headers and containing the tag
     *
     * @param s           The pathname of the audio file
     * @param audioHeader the encoding audioHeaders over this file
     * @param tag         the tag contained in this file
     */
    public XAudioFile(String s, AudioHeader audioHeader, Tag tag) {
        this.audioHeader = audioHeader;
        this.tag = tag;
    }


    /**
     * Set the file extension
     *
     * @param ext
     */
    public void setExt(String ext) {
        this.extension = ext;
    }

    /**
     * Retrieve the file extension
     *
     * @return
     */
    public String getExt() {
        return extension;
    }

    /**
     * Assign a tag to this audio file
     *
     * @param tag Tag to be assigned
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * Return audio header information
     *
     * @return
     */
    public AudioHeader getAudioHeader() {
        return audioHeader;
    }

    /**
     * <p>Returns the tag contained in this AudioFile, the <code>Tag</code> contains any useful meta-data, like
     * artist, album, title, etc. If the file does not contain any tag the null is returned. Some audio formats do
     * not allow there to be no tag so in this case the reader would return an empty tag whereas for others such
     * as mp3 it is purely optional.
     *
     * @return Returns the tag contained in this AudioFile, or null if no tag exists.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * <p>Returns a multi-line string with the file path, the encoding audioHeader, and the tag contents.
     *
     * @return A multi-line string with the file path, the encoding audioHeader, and the tag contents.
     * TODO Maybe this can be changed ?
     */
    public String toString() {
        return "AudioFile "
                + "  --------\n" + audioHeader.toString() + "\n" + ((tag == null) ? "" : tag.toString()) + "\n-------------------";
    }

    /**
     * Check does file exist
     *
     * @param file
     * @throws FileNotFoundException if file not found
     */
    public void checkFileExists(File file) throws FileNotFoundException {
        logger.config("Reading file:" + "path" + file.getPath() + ":abs:" + file.getAbsolutePath());
        if (!file.exists()) {
            logger.severe("Unable to find:" + file.getPath());
            throw new FileNotFoundException(ErrorMessage.UNABLE_TO_FIND_FILE.getMsg(file.getPath()));
        }
    }

    /**
     * If using ID3 format convert tag from current version to another as specified by id3V2Version,
     *
     * @return the converted tag or the original if no conversion necessary
     */
    public AbstractID3v2Tag convertID3Tag(AbstractID3v2Tag tag, ID3V2Version id3V2Version) {
        if (tag instanceof ID3v24Tag) {
            switch (id3V2Version) {
                case ID3_V22:
                    return new ID3v22Tag(tag);
                case ID3_V23:
                    return new ID3v23Tag(tag);
                case ID3_V24:
                    return tag;
            }
        } else if (tag instanceof ID3v23Tag) {
            switch (id3V2Version) {
                case ID3_V22:
                    return new ID3v22Tag(tag);
                case ID3_V23:
                    return tag;
                case ID3_V24:
                    return new ID3v24Tag(tag);
            }
        } else if (tag instanceof ID3v22Tag) {
            switch (id3V2Version) {
                case ID3_V22:
                    return tag;
                case ID3_V23:
                    return new ID3v23Tag(tag);
                case ID3_V24:
                    return new ID3v24Tag(tag);
            }
        }
        return null;
    }
}
