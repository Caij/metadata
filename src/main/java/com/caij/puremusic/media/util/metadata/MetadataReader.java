package com.caij.puremusic.media.util.metadata;


import java.io.IOException;

public interface MetadataReader {

    /**
     * continuing from the position in the stream reached by the returning call.
     */
    int RESULT_CONTINUE = 0;

    /**
     * from a specified position in the stream.
     */
    int RESULT_SEEK = 1;

    int RESULT_END_OF_INPUT = Util.RESULT_END_OF_INPUT;


    public MetadataWrapper read(ExtractorInput extractorInput) throws IOException;
}
