package org.jaudiotagger.x;

import org.jaudiotagger.x.FileChannelInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class BuffInputStream {

    private static final int TRANSFER_SIZE = 8192;
    private final long size;
    private final InputStream in;
    private byte[] data = new byte[TRANSFER_SIZE];
    private final byte[] skipBuffer = new byte[TRANSFER_SIZE];

    private long position;

    public BuffInputStream(long size, InputStream in) {
        this.size = size;
        this.in = in;
    }

    public synchronized int readProxy(long currentPosition, byte b[], int off, int len) throws IOException {
        return readProxyInner(currentPosition, b, off, len);
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
                data = Arrays.copyOf(data, data.length + TRANSFER_SIZE);
            }
            System.arraycopy(skipBuffer, 0, data, (int) position, nr);
            remaining -= nr;
            position += nr;
        }
    }

    public FileChannel newFileChannel() {
        return new FileChannelInputStream(this);
    }

    public long size() {
        return size;
    }
}
