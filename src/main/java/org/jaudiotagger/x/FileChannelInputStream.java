package org.jaudiotagger.x;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;

public class FileChannelInputStream extends FileChannel {

    private byte[] buf = new byte[0];

    private final BuffInputStream in;

    private long position;

    public FileChannelInputStream(BuffInputStream inputStream) {
        in = inputStream;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
        int len = dst.remaining();
        int totalRead = 0;
        int bytesRead = 0;
        try {
            begin();
            if (buf.length < len)
                buf = new byte[len];
            bytesRead = readProxy(buf, 0, len);
            if (bytesRead > 0) {
                dst.put(buf, 0, bytesRead);
            }
        } finally {
            end(bytesRead > 0);
        }
        return totalRead;
    }

    private int readProxy(byte b[], int off, int len) throws IOException {
        int readLen = in.readProxy(position, b, off, len);
        if (readLen > 0) {
            position = position + off + readLen;
        }
        return readLen;
    }

    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
        return 0;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        return 0;
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
        return 0;
    }

    @Override
    public long position() throws IOException {
        return position;
    }

    @Override
    public FileChannel position(long newPosition) throws IOException {
        position = newPosition;
        return this;
    }

    @Override
    public long size() throws IOException {
        return in.size();
    }

    @Override
    public FileChannel truncate(long size) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public void force(boolean metaData) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public long transferFrom(ReadableByteChannel src, long position, long count) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public int read(ByteBuffer dst, long position) throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
        int len = dst.remaining();
        int totalRead = 0;
        int bytesRead = 0;
        try {
            begin();
            byte[] buf = new byte[len];
            bytesRead = in.readProxy(position, buf, 0, len);
            if (bytesRead > 0) {
                dst.put(buf, 0, bytesRead);
            }
        } finally {
            end(bytesRead > 0);
        }
        return totalRead;
    }

    @Override
    public int write(ByteBuffer src, long position) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public MappedByteBuffer map(MapMode mode, long position, long size) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public FileLock lock(long position, long size, boolean shared) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public FileLock tryLock(long position, long size, boolean shared) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    protected void implCloseChannel() throws IOException {

    }
}
