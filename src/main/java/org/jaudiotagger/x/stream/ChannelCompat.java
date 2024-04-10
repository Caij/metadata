package org.jaudiotagger.x.stream;

import java.nio.channels.FileChannel;

public interface ChannelCompat {

    public FileChannel newFileChannel();

    long size();
}
