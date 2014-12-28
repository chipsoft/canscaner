package ua.com.chipsoft.j2534;

import java.util.Arrays;

/**
 * Created by chipsoft on 29.12.14.
 */
public class HighIOPackage {
    private final int tag;
    private final int len;
    private final int error;
    private int crc;
    private final byte[] buff;

    public int getTag() {
        return tag;
    }

    public int getLen() {
        return len;
    }

    public int getError() {
        return error;
    }

    public int getCrc() {
        return crc;
    }

    public byte[] getBuff() {
        return buff;
    }

    public HighIOPackage(int tag, byte[] buff) {
        this(tag, buff.length, 0, 0, buff);
        crc = evalCRC();
    }

    public HighIOPackage(int tag, int len, int error, int crc, byte[] buff) {
        this.tag = tag;
        this.len = len;
        this.error = error;
        this.crc = crc;
        this.buff = buff;
    }

    @Override
    public String toString() {
        return "HighIOPackage{" +
                "tag=" + tag +
                ", len=" + len +
                ", error=" + error +
                ", crc=" + crc +
                ", buff=" + Arrays.toString(buff) +
                '}';
    }

    private int evalCRC(byte[] dataBuffer) {
        int currentCRC = 0;
        for (byte aDataBuffer : dataBuffer) {
            currentCRC += aDataBuffer;
        }
        return currentCRC;
    }

    private int evalCRC() {
        return evalCRC(buff);
    }

    public boolean isCRCValid() {
        return (evalCRC() == crc);
    }
}
