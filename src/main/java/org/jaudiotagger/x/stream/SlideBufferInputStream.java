package org.jaudiotagger.x.stream;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SlideBufferInputStream {

    private static final int TRANSFER_SIZE = 8192;
    private final long size;
    private final InputStream in;
    private byte[] data;
    private final byte[] skipBuffer = new byte[TRANSFER_SIZE];

    private long position;
    private final long initPosition;

    public SlideBufferInputStream(long size, long initPosition, InputStream in, int maxBufferLength) {
        this.size = size;
        this.initPosition = initPosition;
        this.in = in;
        this.data = new byte[maxBufferLength];
    }

    public synchronized int readProxy(long currentPosition, byte b[], int off, int len) throws IOException {
        if (currentPosition < initPosition) {
            throw new IOException("position ill");
        }
        long readPosition = currentPosition - initPosition;
        return readProxyInner(readPosition, b, off, len);
    }

    private int readProxyInner(long currentPosition, byte b[], int off, int len) throws IOException {
        if (currentPosition < size) {
            long newPosition = currentPosition + off + len;
            fill(newPosition);
            int readLen = (int) Math.min(len, size - currentPosition);
            System.arraycopy(data, (int) (currentPosition + off), b, 0, readLen);
            return readLen;
        } else {
            return -1;
        }
    }

    public void fill(long newPosition) throws IOException {
        long remaining = newPosition - position;
        int nr;
        while (remaining > 0) {
            nr = in.read(skipBuffer, 0, (int)Math.min(TRANSFER_SIZE, remaining));
            if (nr < 0) {
                break;
            }
            if (position + nr > data.length) {
                data = Arrays.copyOf(data, data.length + TRANSFER_SIZE * 100);
            }
            System.arraycopy(skipBuffer, 0, data, (int) position, nr);
            remaining -= nr;
            position += nr;
        }
    }


    public void close() throws IOException {
        in.close();
    }
}
