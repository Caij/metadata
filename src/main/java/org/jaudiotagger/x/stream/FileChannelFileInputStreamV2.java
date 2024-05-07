package org.jaudiotagger.x.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Wraps a {@link RandomAccessFile} into an {@link InputStream}.<br>
 *
 * @author Christian Laireiter
 */
public final class FileChannelFileInputStreamV2 extends InputStream {

    /**
     * The file access to read from.<br>
     */
    public final FileChannel source;

    /**
     * Creates an instance that will provide {@link InputStream} functionality
     * on the given {@link RandomAccessFile} by delegating calls.<br>
     *
     * @param file The file to read.
     */
    public FileChannelFileInputStreamV2(final FileChannel file) {
        super();
        if (file == null) {
            throw new IllegalArgumentException("null");
        }
        this.source = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        int bytesRead = source.read(byteBuffer); // 从通道读取数据到缓冲区
        if (bytesRead == -1) {
            return -1; // 表示已经读取到文件末尾
        }
        byteBuffer.flip(); // 反转缓冲区，以便从缓冲区读取数据
        return byteBuffer.get() & 0xFF; // 返回缓冲区中的下一个字节数据（0-255之间的整数）
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(final byte[] buffer, final int off, final int len) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(len);
        int bytesRead = source.read(byteBuffer);
        if (bytesRead == -1) {
            return -1; // 表示已经读取到文件末尾
        }
        byteBuffer.flip();
        byteBuffer.get(buffer, off, len);
        return bytesRead;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long skip(final long amount) throws IOException {
        if (amount < 0) {
            throw new IllegalArgumentException("invalid negative value");
        }
        long position = source.position();
        long newPosition = Math.min(position + amount, source.size());
        source.position(newPosition);
        return newPosition - position;
    }

    public void holder() throws IOException {
        if (source instanceof SlideBufferFileChannel) {
            ((SlideBufferFileChannel) source).holder();
        }
    }

    public int readByte() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        int bytesRead = source.read(byteBuffer); // 从通道读取数据到缓冲区
        if (bytesRead == -1) {
            return -1; // 表示已经读取到文件末尾
        }
        byteBuffer.flip(); // 反转缓冲区，以便从缓冲区读取数据
        return byteBuffer.get();
    }

    public void position(long position) throws IOException {
        source.position(position);
    }

    public long position() throws IOException {
        return source.position();
    }

    public int readFully(byte[] buf) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
        int bytesRead = source.read(byteBuffer);
        if (bytesRead == -1) {
            return -1; // 表示已经读取到文件末尾
        }
        return bytesRead;
    }


}
