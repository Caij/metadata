package org.jaudiotagger.x.mp3;
/**
 * @author : Paul Taylor
 * @author : Eric Farng
 * <p>
 * Version @version:$Id$
 * <p>
 * MusicTag Copyright (C)2003,2004
 * <p>
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 * you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */


import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.*;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.logging.*;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagNotFoundException;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.id3.*;
import org.jaudiotagger.tag.lyrics3.AbstractLyrics3;
import org.jaudiotagger.x.ID3V2TagUtil;
import org.jaudiotagger.x.XAudioFile;
import org.jaudiotagger.x.stream.SlideBufferFileChannel;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

/**
 * This class represents a physical MP3 File
 */
public class XMP3File extends XAudioFile {
    private static final int MINIMUM_FILESIZE = 150;

    protected static AbstractTagDisplayFormatter tagFormatter;

    /**
     * the ID3v2 tag that this file contains.
     */
    private AbstractID3v2Tag id3v2tag = null;

    /**
     * Representation of the idv2 tag as a idv24 tag
     */
    private ID3v24Tag id3v2Asv24tag = null;

    /**
     * The Lyrics3 tag that this file contains.
     */
    private AbstractLyrics3 lyrics3tag = null;


    /**
     * The ID3v1 tag that this file contains.
     */
    private ID3v1Tag id3v1tag = null;

    /**
     * Creates a new empty MP3File datatype that is not associated with a
     * specific file.
     */
    public XMP3File() {
    }


    /* Load ID3V1tag if exists */
    public static final int LOAD_IDV1TAG = 2;

    /* Load ID3V2tag if exists */
    public static final int LOAD_IDV2TAG = 4;

    /**
     * This option is currently ignored
     */
    public static final int LOAD_LYRICS3 = 8;

    public static final int LOAD_ALL = LOAD_IDV1TAG | LOAD_IDV2TAG | LOAD_LYRICS3;


    private void readV1Tag(FileChannel fc, int loadOptions) throws IOException {
        if ((loadOptions & LOAD_IDV1TAG) != 0) {
            logger.finer("Attempting to read id3v1tags");
            try {
                id3v1tag = ID3V2TagUtil.createID3v11Tag(fc);
            } catch (TagNotFoundException ex) {
                logger.config("No ids3v11 tag found");
            }

            try {
                if (id3v1tag == null) {
                    id3v1tag = ID3V2TagUtil.createID3v1Tag(fc);
                }
            } catch (TagNotFoundException ex) {
                logger.config("No id3v1 tag found");
            }
        }
    }

    private void readV2Tag(FileChannel fc, int loadOptions, int startByte) throws IOException, TagException {
        //We know where the actual Audio starts so load all the file from start to that point into
        //a buffer then we can read the IDv2 information without needing any more File I/O
        if (startByte >= AbstractID3v2Tag.TAG_HEADER_LENGTH) {
            logger.finer("Attempting to read id3v2tags");
            ByteBuffer bb;
            bb = ByteBuffer.allocate(startByte);
            // XXX: don't change it to map
            // https://stackoverflow.com/questions/28378713/bytebuffer-getbyte-int-int-failed-on-android-ics-and-jb
            fc.position(0);
            fc.read(bb);

            try {
                bb.rewind();

                if ((loadOptions & LOAD_IDV2TAG) != 0) {
                    logger.config("Attempting to read id3v2tags");
                    try {
                        this.setID3v2Tag(new ID3v24Tag(bb));
                    } catch (TagNotFoundException ex) {
                        logger.config("No id3v24 tag found");
                    }

                    try {
                        if (id3v2tag == null) {
                            this.setID3v2Tag(new ID3v23Tag(bb));
                        }
                    } catch (TagNotFoundException ex) {
                        logger.config("No id3v23 tag found");
                    }

                    try {
                        if (id3v2tag == null) {
                            this.setID3v2Tag(new ID3v22Tag(bb));
                        }
                    } catch (TagNotFoundException ex) {
                        logger.config("No id3v22 tag found");
                    }
                }
            } finally {
                //Workaround for 4724038 on Windows
                bb.clear();
                if (bb.isDirect() && !TagOptionSingleton.getInstance().isAndroid()) {
                    // Reflection substitute for following code:
                    //    ((sun.nio.ch.DirectBuffer) bb).cleaner().clean();
                    // which causes exception on Android - Sun NIO classes are not available
                    try {
                        Class<?> clazz = Class.forName("sun.nio.ch.DirectBuffer");
                        Method cleanerMethod = clazz.getMethod("cleaner");
                        Object cleaner = cleanerMethod.invoke(bb);  // cleaner = bb.cleaner()
                        if (cleaner != null) {
                            Method cleanMethod = cleaner.getClass().getMethod("clean");
                            cleanMethod.invoke(cleaner);   // cleaner.clean()
                        }
                    } catch (ClassNotFoundException e) {
                        logger.severe("Could not load sun.nio.ch.DirectBuffer.");
                    } catch (NoSuchMethodException e) {
                        logger.severe("Could not invoke DirectBuffer method - " + e.getMessage());
                    } catch (InvocationTargetException e) {
                        logger.severe("Could not invoke DirectBuffer method - target exception");
                    } catch (IllegalAccessException e) {
                        logger.severe("Could not invoke DirectBuffer method - illegal access");
                    }
                }
            }
        } else {
            logger.config("Not enough room for valid id3v2 tag:" + startByte);
        }
    }


    /**
     *
     * @param startByte
     * @param endByte
     * @return
     * @throws Exception
     *
     * @return true if all the bytes between in the file between startByte and endByte are null, false
     * otherwise
     */
    private boolean isFilePortionNull(FileChannel fc, int startByte, int endByte) throws IOException {
        logger.config("Checking file portion:" + Hex.asHex(startByte) + ":" + Hex.asHex(endByte));
        fc.position(startByte);
        ByteBuffer bb = ByteBuffer.allocateDirect(endByte - startByte);
        fc.read(bb);
        while (bb.hasRemaining()) {
            if (bb.get() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Regets the audio header starting from start of file, and write appropriate logging to indicate
     * potential problem to user.
     *
     * @param startByte
     * @param firstHeaderAfterTag
     * @return
     * @throws IOException
     * @throws InvalidAudioFrameException
     */
    private MP3AudioHeader checkAudioStart(FileChannel fileChannel, long startByte, MP3AudioHeader firstHeaderAfterTag) throws IOException, InvalidAudioFrameException {
        MP3AudioHeader headerOne;
        MP3AudioHeader headerTwo;

        //because we cant agree on start location we reread the audioheader from the start of the file, at least
        //this way we cant overwrite the audio although we might overwrite part of the tag if we write this file
        //back later
        headerOne = ID3V2TagUtil.MP3AudioHeader(fileChannel, 0);
        logger.config("Checking from start:" + headerOne);

        //Although the id3 tag size appears to be incorrect at least we have found the same location for the start
        //of audio whether we start searching from start of file or at the end of the alleged of file so no real
        //problem
        if (firstHeaderAfterTag.getMp3StartByte() == headerOne.getMp3StartByte()) {
            return firstHeaderAfterTag;
        } else {
            //Same frame count so probably both audio headers with newAudioHeader being the first one
            if (firstHeaderAfterTag.getNumberOfFrames() == headerOne.getNumberOfFrames()) {
                return headerOne;
            }

            //If the size reported by the tag header is a little short and there is only nulls between the recorded value
            //and the start of the first audio found then we stick with the original header as more likely that currentHeader
            //DataInputStream not really a header
            if (isFilePortionNull(fileChannel, (int) startByte, (int) firstHeaderAfterTag.getMp3StartByte())) {
                return firstHeaderAfterTag;
            }

            headerTwo = ID3V2TagUtil.MP3AudioHeader(fileChannel, headerOne.getMp3StartByte()
                    + headerOne.mp3FrameHeader.getFrameLength());
            //Skip to the next header (header 2, counting from start of file)

            //It matches the header we found when doing the original search from after the ID3Tag therefore it
            //seems that newAudioHeader was a false match and the original header was correct
            if (headerTwo.getMp3StartByte() == firstHeaderAfterTag.getMp3StartByte()) {
                return firstHeaderAfterTag;
            }

            //It matches the frameCount the header we just found so lends weight to the fact that the audio does indeed start at new header
            //however it maybe that neither are really headers and just contain the same data being misrepresented as headers.
            if (headerTwo.getNumberOfFrames() == headerOne.getNumberOfFrames()) {
                return headerOne;
            }
            ///Doesnt match the frameCount lets go back to the original header
            else {
                return firstHeaderAfterTag;
            }
        }
    }

    public XMP3File(FileChannel fc, int loadOptions, boolean readOnly) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        //Read ID3v2 tag size (if tag exists) to allow audioHeader parsing to skip over tag
        long tagSizeReportedByHeader = ID3V2TagUtil.getV2TagSizeIfExists(fc);
        logger.config("TagHeaderSize:" + Hex.asHex(tagSizeReportedByHeader));
        fc.position(0);
        audioHeader =  ID3V2TagUtil.MP3AudioHeader(fc, tagSizeReportedByHeader);

        fc.position(0);

        //If the audio header is not straight after the end of the tag then search from start of file
        if (tagSizeReportedByHeader != ((MP3AudioHeader) audioHeader).getMp3StartByte()) {
            logger.config("First header found after tag:" + audioHeader);
            audioHeader = checkAudioStart(fc, tagSizeReportedByHeader, (MP3AudioHeader) audioHeader);
        }

        //Read v1 tags (if any)
        readV1Tag(fc, loadOptions);

        fc.position(0);

        //Read v2 tags (if any)
        readV2Tag(fc, loadOptions, (int) ((MP3AudioHeader) audioHeader).getMp3StartByte());

        //If we have a v2 tag use that, if we do not but have v1 tag use that
        //otherwise use nothing
        //TODO:if have both should we merge
        //rather than just returning specific ID3v22 tag, would it be better to return v24 version ?
        if (this.getID3v2Tag() != null) {
            tag = this.getID3v2Tag();
        } else if (id3v1tag != null) {
            tag = id3v1tag;
        }
    }



    /**
     * Return audio header
     * @return
     */
    public MP3AudioHeader getMP3AudioHeader() {
        return (MP3AudioHeader) getAudioHeader();
    }

    /**
     * Returns true if this datatype contains an <code>Id3v1</code> tag
     *
     * @return true if this datatype contains an <code>Id3v1</code> tag
     */
    public boolean hasID3v1Tag() {
        return (id3v1tag != null);
    }

    /**
     * Returns true if this datatype contains an <code>Id3v2</code> tag
     *
     * @return true if this datatype contains an <code>Id3v2</code> tag
     */
    public boolean hasID3v2Tag() {
        return (id3v2tag != null);
    }

    /**
     * Returns true if this datatype contains a <code>Lyrics3</code> tag
     * TODO disabled until Lyrics3 fixed
     * @return true if this datatype contains a <code>Lyrics3</code> tag
     */
    /*
    public boolean hasLyrics3Tag()
    {
        return (lyrics3tag != null);
    }
    */

    /**
     * Sets the ID3v1(_1)tag to the tag provided as an argument.
     *
     * @param id3v1tag
     */
    public void setID3v1Tag(ID3v1Tag id3v1tag) {
        logger.config("setting tagv1:v1 tag");
        this.id3v1tag = id3v1tag;
    }

    public void setID3v1Tag(Tag id3v1tag) {
        logger.config("setting tagv1:v1 tag");
        this.id3v1tag = (ID3v1Tag) id3v1tag;
    }

    /**
     * Sets the <code>ID3v1</code> tag for this dataType. A new
     * <code>ID3v1_1</code> dataType is created from the argument and then used
     * here.
     *
     * @param mp3tag Any MP3Tag dataType can be used and will be converted into a
     *               new ID3v1_1 dataType.
     */
    public void setID3v1Tag(AbstractTag mp3tag) {
        logger.config("setting tagv1:abstract");
        id3v1tag = new ID3v11Tag(mp3tag);
    }

    /**
     * Returns the <code>ID3v1</code> tag for this dataType.
     *
     * @return the <code>ID3v1</code> tag for this dataType
     */
    public ID3v1Tag getID3v1Tag() {
        return id3v1tag;
    }

    /**
     * Sets the <code>ID3v2</code> tag for this dataType. A new
     * <code>ID3v2_4</code> dataType is created from the argument and then used
     * here.
     *
     * @param mp3tag Any MP3Tag dataType can be used and will be converted into a
     *               new ID3v2_4 dataType.
     */
    public void setID3v2Tag(AbstractTag mp3tag) {
        id3v2tag = new ID3v24Tag(mp3tag);

    }

    /**
     * Sets the v2 tag to the v2 tag provided as an argument.
     * Also store a v24 version of tag as v24 is the interface to be used
     * when talking with client applications.
     *
     * @param id3v2tag
     */
    public void setID3v2Tag(AbstractID3v2Tag id3v2tag) {
        this.id3v2tag = id3v2tag;
        if (id3v2tag instanceof ID3v24Tag) {
            this.id3v2Asv24tag = (ID3v24Tag) this.id3v2tag;
        } else {
            this.id3v2Asv24tag = new ID3v24Tag(id3v2tag);
        }
    }

    /**
     * Set v2 tag ,don't need to set v24 tag because saving
     *
     * @param id3v2tag
     */
    //TODO temp its rather messy
    public void setID3v2TagOnly(AbstractID3v2Tag id3v2tag) {
        this.id3v2tag = id3v2tag;
        this.id3v2Asv24tag = null;
    }

    /**
     * Returns the <code>ID3v2</code> tag for this datatype.
     *
     * @return the <code>ID3v2</code> tag for this datatype
     */
    public AbstractID3v2Tag getID3v2Tag() {
        return id3v2tag;
    }

    /**
     * @return a representation of tag as v24
     */
    public ID3v24Tag getID3v2TagAsv24() {
        return id3v2Asv24tag;
    }

    /**
     * Sets the <code>Lyrics3</code> tag for this dataType. A new
     * <code>Lyrics3v2</code> dataType is created from the argument and then
     *
     * used here.
     *
     * @param mp3tag Any MP3Tag dataType can be used and will be converted into a
     *               new Lyrics3v2 dataType.
     */
    /*
    public void setLyrics3Tag(AbstractTag mp3tag)
    {
        lyrics3tag = new Lyrics3v2(mp3tag);
    }
    */

    /**
     *
     *
     * @param lyrics3tag
     */
    /*
    public void setLyrics3Tag(AbstractLyrics3 lyrics3tag)
    {
        this.lyrics3tag = lyrics3tag;
    }
    */

    /**
     * Returns the <code>ID3v1</code> tag for this datatype.
     *
     * @return the <code>ID3v1</code> tag for this datatype
     */
    /*
    public AbstractLyrics3 getLyrics3Tag()
    {
        return lyrics3tag;
    }
    */


    /**
     * Check can write to file
     *
     * @param file
     * @throws IOException
     */
    public void precheck(File file) throws IOException {
        if (!file.exists()) {
            logger.severe(ErrorMessage.GENERAL_WRITE_FAILED_BECAUSE_FILE_NOT_FOUND.getMsg(file.getName()));
            throw new IOException(ErrorMessage.GENERAL_WRITE_FAILED_BECAUSE_FILE_NOT_FOUND.getMsg(file.getName()));
        }

        if (TagOptionSingleton.getInstance().isCheckIsWritable() && !file.canWrite()) {
            logger.severe(ErrorMessage.GENERAL_WRITE_FAILED.getMsg(file.getName()));
            throw new IOException(ErrorMessage.GENERAL_WRITE_FAILED.getMsg(file.getName()));
        }

        if (file.length() <= MINIMUM_FILESIZE) {
            logger.severe(ErrorMessage.GENERAL_WRITE_FAILED_BECAUSE_FILE_IS_TOO_SMALL.getMsg(file.getName()));
            throw new IOException(ErrorMessage.GENERAL_WRITE_FAILED_BECAUSE_FILE_IS_TOO_SMALL.getMsg(file.getName()));
        }
    }

    /**
     * Saves the tags in this dataType to the file argument. It will be saved as
     * TagConstants.MP3_FILE_SAVE_WRITE
     *
     * @param fileToSave file to save the this dataTypes tags to
     * @throws FileNotFoundException if unable to find file
     * @throws IOException           on any I/O error
     */
    public void save(File fileToSave) throws IOException {
        //Ensure we are dealing with absolute filepaths not relative ones
        File file = fileToSave.getAbsoluteFile();

        logger.config("Saving  : " + file.getPath());

        //Checks before starting write
        precheck(file);

        RandomAccessFile rfile = null;
        try {
            //ID3v2 Tag
            if (TagOptionSingleton.getInstance().isId3v2Save()) {
                if (id3v2tag == null) {
                    rfile = new RandomAccessFile(file, "rw");
                    (new ID3v24Tag()).delete(rfile);
                    (new ID3v23Tag()).delete(rfile);
                    (new ID3v22Tag()).delete(rfile);
                    logger.config("Deleting ID3v2 tag:" + file.getName());
                    rfile.close();
                } else {
                    logger.config("Writing ID3v2 tag:" + file.getName());
                    final MP3AudioHeader mp3AudioHeader = (MP3AudioHeader) this.getAudioHeader();
                    final long mp3StartByte = mp3AudioHeader.getMp3StartByte();
                    final long newMp3StartByte = id3v2tag.write(file, mp3StartByte);
                    if (mp3StartByte != newMp3StartByte) {
                        logger.config("New mp3 start byte: " + newMp3StartByte);
                        mp3AudioHeader.setMp3StartByte(newMp3StartByte);
                    }

                }
            }
            rfile = new RandomAccessFile(file, "rw");

            //Lyrics 3 Tag
            if (TagOptionSingleton.getInstance().isLyrics3Save()) {
                if (lyrics3tag != null) {
                    lyrics3tag.write(rfile);
                }
            }
            //ID3v1 tag
            if (TagOptionSingleton.getInstance().isId3v1Save()) {
                logger.config("Processing ID3v1");
                if (id3v1tag == null) {
                    logger.config("Deleting ID3v1");
                    (new ID3v1Tag()).delete(rfile);
                } else {
                    logger.config("Saving ID3v1");
                    id3v1tag.write(rfile);
                }
            }
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, ErrorMessage.GENERAL_WRITE_FAILED_BECAUSE_FILE_NOT_FOUND.getMsg(file.getName()), ex);
            throw ex;
        } catch (IOException iex) {
            logger.log(Level.SEVERE, ErrorMessage.GENERAL_WRITE_FAILED_BECAUSE.getMsg(file.getName(), iex.getMessage()), iex);
            throw iex;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, ErrorMessage.GENERAL_WRITE_FAILED_BECAUSE.getMsg(file.getName(), re.getMessage()), re);
            throw re;
        } finally {
            if (rfile != null) {
                rfile.close();
            }
        }
    }

    public static AbstractTagDisplayFormatter getStructureFormatter() {
        return tagFormatter;
    }

    /**
     * Set the Tag
     *
     * If the parameter tag is a v1tag then the v1 tag is set if v2tag then the v2tag.
     *
     * @param tag
     */
    public void setTag(Tag tag) {
        this.tag = tag;
        if (tag instanceof ID3v1Tag) {
            setID3v1Tag((ID3v1Tag) tag);
        } else {
            setID3v2Tag((AbstractID3v2Tag) tag);
        }
    }
}

