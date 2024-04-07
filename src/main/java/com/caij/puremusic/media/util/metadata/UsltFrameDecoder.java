package com.caij.puremusic.media.util.metadata;

import java.nio.charset.Charset;

public class UsltFrameDecoder {

    private static final int ID3_TEXT_ENCODING_ISO_8859_1 = 0;
    private static final int ID3_TEXT_ENCODING_UTF_16 = 1;
    private static final int ID3_TEXT_ENCODING_UTF_16BE = 2;
    private static final int ID3_TEXT_ENCODING_UTF_8 = 3;

    public static String decode(ParsableByteArray id3Data, int frameSize) {
        if (frameSize < 4) {
            // Frame is malformed.
            return null;
        }

        int encoding = id3Data.readUnsignedByte();
        Charset charset = getCharsetName(encoding);

        byte[] lang = new byte[3];
        id3Data.readBytes(lang, 0, 3); // language
        byte[] rest = new byte[frameSize - 4];
        id3Data.readBytes(rest, 0, frameSize - 4);

        int descriptionEndIndex = indexOfEos(rest, 0, encoding);
        int textStartIndex = descriptionEndIndex + delimiterLength(encoding);
        int textEndIndex = indexOfEos(rest, textStartIndex, encoding);
        return decodeStringIfValid(rest, textStartIndex, textEndIndex, charset);
    }

    private static Charset getCharsetName(int encodingByte) {
        String name;
        switch (encodingByte) {
            case ID3_TEXT_ENCODING_UTF_16:
                name = "UTF-16";
                break;
            case ID3_TEXT_ENCODING_UTF_16BE:
                name = "UTF-16BE";
                break;
            case ID3_TEXT_ENCODING_UTF_8:
                name = "UTF-8";
                break;
            case ID3_TEXT_ENCODING_ISO_8859_1:
            default:
                name = "ISO-8859-1";
                break;
        }
        return Charset.forName(name);
    }

    private static int indexOfEos(byte[] data, int fromIndex, int encoding) {
        int terminationPos = indexOfZeroByte(data, fromIndex);

        // For single byte encoding charsets, we're done.
        if (encoding == ID3_TEXT_ENCODING_ISO_8859_1 || encoding == ID3_TEXT_ENCODING_UTF_8) {
            return terminationPos;
        }

        // Otherwise ensure an even index and look for a second zero byte.
        while (terminationPos < data.length - 1) {
            if (terminationPos % 2 == 0 && data[terminationPos + 1] == 0) {
                return terminationPos;
            }
            terminationPos = indexOfZeroByte(data, terminationPos + 1);
        }

        return data.length;
    }

    private static int indexOfZeroByte(byte[] data, int fromIndex) {
        for (int i = fromIndex; i < data.length; i++) {
            if (data[i] == 0) {
                return i;
            }
        }
        return data.length;
    }

    private static int delimiterLength(int encodingByte) {
        return (encodingByte == ID3_TEXT_ENCODING_ISO_8859_1 || encodingByte == ID3_TEXT_ENCODING_UTF_8) ? 1 : 2;
    }

    private static String decodeStringIfValid(byte[] data, int from, int to, Charset charset) {
        if (to <= from || to > data.length) {
            return "";
        }
        return new String(data, from, to - from, charset);
    }
}
