package org.jaudiotagger.x.stream;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface InputStreamFactory {
    SlideBufferInputStream newInputStream(long initPosition) throws IOException;

    int getMaxBufferLength();

    long size();
}
