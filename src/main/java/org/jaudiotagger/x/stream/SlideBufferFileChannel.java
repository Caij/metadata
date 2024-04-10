package org.jaudiotagger.x.stream;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Map;

public class SlideBufferFileChannel extends FileChannel {

    private byte[] buf = new byte[0];

    private SlideBufferInputStream in;
    private long slideBufferInputStreamPosition = -1;

    private long position;

    private long initPosition = 0;

    private final InputStreamFactory inputStreamFactory;
    private Map<Long, SlideBufferInputStream> caches = new HashMap<>();

    public SlideBufferFileChannel(InputStreamFactory inputStreamFactory) {
       this.inputStreamFactory = inputStreamFactory;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }

        initStream();

        int len = dst.remaining();
        int n = 0;
        try {
            begin();
            if (buf.length < len)
                buf = new byte[len];
            do {
                int count = this.read(buf,  n, len - n);
                if (count < 0)
                    break;
                n += count;
            } while (n < len);
            if (n > 0) {
                dst.put(buf, 0, n);
            }
        } finally {
            end(n > 0);
        }
        return n;
    }

    @Override
    public int read(ByteBuffer dst, long position) throws IOException {
        long old = position;
        position(position);
        int read = read(dst);
        position(old);
        return read;
    }

    public int read(byte[] dst) throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }

        return readProxy(dst, 0, dst.length);
    }


    public void holder() throws IOException {
        initPosition = Math.max(position - inputStreamFactory.getMaxBufferLength(), 0);
        initStream();
    }

    private void initStream() throws IOException {
        if (initPosition != slideBufferInputStreamPosition) {
            SlideBufferInputStream slideBufferInputStream = caches.get(initPosition);
            if (slideBufferInputStream != null) {
                in = slideBufferInputStream;
            } else {
                in = inputStreamFactory.newInputStream(initPosition);
                caches.put(initPosition, in);
            }
            slideBufferInputStreamPosition = initPosition;
        }
    }

    public int read(byte[] dst, int offset, int length) throws IOException {
        if (!isOpen()) {
            throw new ClosedChannelException();
        }
        return readProxy(dst, offset, length);
    }

    public int read() throws IOException {
        byte[] buf = new byte[1];
        int read = read(buf);
        if (read > 0) {
            return buf[0];
        }
        throw new EOFException();
    }

//    public final byte readByte() throws IOException {
//        int ch = this.read();
//        if (ch < 0)
//            throw new EOFException();
//        return (byte)(ch);
//    }

//    public final void readFully(byte b[]) throws IOException {
//        readFully(b, 0, b.length);
//    }
//
//    public final void readFully(byte b[], int off, int len) throws IOException {
//        int n = 0;
//        do {
//            int count = this.read(b, off + n, len - n);
//            if (count < 0)
//                throw new EOFException();
//            n += count;
//        } while (n < len);
//    }


    private int readProxy(byte b[], int off, int len) throws IOException {
        initStream();

        int readLen = in.readProxy(position, b, off, len);
        if (readLen > 0) {
            position = position + off + readLen;
        }
        return readLen;
    }

    @Override
    public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public long position() throws IOException {
        return position;
    }

    @Override
    public FileChannel position(long newPosition) throws IOException {
        if (initPosition == -1 || newPosition < initPosition
                || newPosition > (initPosition + inputStreamFactory.getMaxBufferLength())) {
            initPosition = newPosition;
        }
        position = newPosition;
        return this;
    }


//    public long skipBytes(int maxValue) throws IOException {
//        byte[] buff = new byte[maxValue];
//        readFully(buff);
//        return maxValue;
//    }

    @Override
    public long size() throws IOException {
        return inputStreamFactory.size();
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
        for (Map.Entry<Long, SlideBufferInputStream> entry : caches.entrySet()) {
            entry.getValue().close();
        }
    }

}
