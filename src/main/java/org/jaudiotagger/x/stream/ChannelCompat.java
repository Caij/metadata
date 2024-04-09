package org.jaudiotagger.x.stream;

import java.nio.channels.FileChannel;

public interface ChannelCompat {

    public SlideBufferFileChannel newFileChannel();

    long size();
}
