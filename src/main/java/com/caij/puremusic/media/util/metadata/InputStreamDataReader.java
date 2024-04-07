package com.caij.puremusic.media.util.metadata;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamDataReader implements DataReader {

    private final InputStream inputStream;

    public InputStreamDataReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        return inputStream.read(buffer, offset, length);
    }
}
