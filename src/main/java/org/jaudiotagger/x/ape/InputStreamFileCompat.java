package org.jaudiotagger.x.ape;

import davaguine.jmac.tools.File;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class InputStreamFileCompat extends File {

    FileChannel source;
    long length;
    private byte readBuffer[] = new byte[8];

    private long markPosition = -1;

    public InputStreamFileCompat(FileChannel source, long length) {
        this.source = source;
        this.length = length;
    }

    public void mark(int readlimit) throws IOException {
        markPosition = source.position();
    }

    public void reset() throws IOException {
        if (markPosition >= 0)
            source.position(markPosition);
    }

    public int read() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        int bytesRead = source.read(byteBuffer); // 从通道读取数据到缓冲区
        if (bytesRead == -1) {
            return -1; // 表示已经读取到文件末尾
        }
        byteBuffer.flip(); // 反转缓冲区，以便从缓冲区读取数据
        return byteBuffer.get() & 0xFF; // 返回缓冲区中的下一个字节数据（0-255之间的整数）
    }

    public short readShortBack() throws IOException {
        return (short) (read() | (read() << 8));
    }

    public int readIntBack() throws IOException {
        return read() | (read() << 8) | (read() << 16) | (read() << 24);
    }

    public long readLongBack() throws IOException {
        return read() |
                (read() << 8) |
                (read() << 16) |
                (read() << 24) |
                (read() << 32) |
                (read() << 40) |
                (read() << 48) |
                (read() << 56);
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int offs, int len) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(len);
        int bytesRead = source.read(byteBuffer);
        if (bytesRead == -1) {
            return -1; // 表示已经读取到文件末尾
        }
        byteBuffer.flip();
        byteBuffer.get(b, offs, len);
        return bytesRead;
    }

    public void readFully(byte[] b) throws IOException {
        read(b);
    }

    public void readFully(byte[] b, int offs, int len) throws IOException {
        read(b, offs, len);
    }

    public void close() throws IOException {
        source.close();
    }

    public boolean readBoolean() throws IOException {
        int ch = read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }

    public byte readByte() throws IOException {
        int ch = read();
        if (ch < 0)
            throw new EOFException();
        return (byte)(ch);
    }

    public char readChar() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (char)((ch1 << 8) + (ch2 << 0));
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public int readInt() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    public String readLine() throws IOException {
        throw new IOException();
//        return file.readLine();
    }

    public long readLong() throws IOException {
        readFully(readBuffer, 0, 8);
        return (((long)readBuffer[0] << 56) +
                ((long)(readBuffer[1] & 255) << 48) +
                ((long)(readBuffer[2] & 255) << 40) +
                ((long)(readBuffer[3] & 255) << 32) +
                ((long)(readBuffer[4] & 255) << 24) +
                ((readBuffer[5] & 255) << 16) +
                ((readBuffer[6] & 255) <<  8) +
                ((readBuffer[7] & 255) <<  0));
    }

    public short readShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (short)((ch1 << 8) + (ch2 << 0));
    }

    public int readUnsignedByte() throws IOException {
        int ch = read();
        if (ch < 0)
            throw new EOFException();
        return ch;
    }

    public int readUnsignedShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch1 << 8) + (ch2 << 0);
    }

    public String readUTF() throws IOException {
        throw new IOException();
//        return file.readUTF();
    }

    public int skipBytes(int amount) throws IOException {
        if (amount < 0) {
            throw new IllegalArgumentException("invalid negative value");
        }
        long position = source.position();
        long newPosition = Math.min(position + amount, source.size());
        source.position(newPosition);
        return (int) (newPosition - position);
    }

    public long length() throws IOException {
        return length;
    }

    public void seek(long pos) throws IOException {
        source.position(pos);
    }

    public long getFilePointer() throws IOException {
        return source.position();
    }

    public void setLength(long newLength) throws IOException {
        throw new IOException();
    }

    public void write(byte[] b, int off, int len) throws IOException {
        throw new IOException();
    }

    public boolean isLocal() {
        return true;
    }

    public String getFilename() {
        return "xxx.ape";
    }
}
