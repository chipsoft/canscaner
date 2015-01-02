package ua.com.chipsoft.j2534;

import java.util.Arrays;

/**
 * Created by chipsoft on 29.12.14.
 */
public class HighIOPackage {
    public static final int MIN_TAG_VALUE = 0;
    public static final int MAX_TAG_VALUE = 0xFFFF;
    public static final int MIN_LEN_VALUE = 0;
    public static final int MAX_LEN_VALUE = 0xFFFF;
    public static final int MIN_ERR_VALUE = 0;
    public static final int MAX_ERR_VALUE = 0xFFFF;
    public static final int MIN_CRC_VALUE = 0;
    public static final int MAX_CRC_VALUE = 0xFFFF;
    public static final int HEADER_LENGTH = 8;
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

    public static HighIOPackage createInstance(int tag, int len, int error, int crc, byte[] buff) throws IllegalHighIOPackage {
        if (tag >= MIN_TAG_VALUE && tag <= MAX_TAG_VALUE &&
                len >= MIN_LEN_VALUE && len <= MAX_LEN_VALUE &&
                error >= MIN_ERR_VALUE && error <= MAX_ERR_VALUE &&
                crc >= MIN_CRC_VALUE && crc <= MAX_CRC_VALUE &&
                len == buff.length &&
                evalCRC(buff) == crc) {
            return new HighIOPackage(tag, len, error, crc, buff);
        }
        throw new IllegalHighIOPackage("HighIOPackage{" +
                "tag=" + tag +
                ", len=" + len +
                ", error=" + error +
                ", crc=" + crc +
                ", buff=" + Arrays.toString(buff) +
                '}');
    }

    public byte[] getBuff() {
        return buff;
    }

    public HighIOPackage(int tag, byte[] buff) {
        this(tag, buff.length, 0, 0, buff);
        crc = evalCRC();
    }

    private HighIOPackage(int tag, int len, int error, int crc, byte[] buff) {
//        TODO Check correct values of variable
        this.tag = tag;
        this.len = len;
        this.error = error;
        this.crc = crc;
        this.buff = buff;
    }

    public byte[] toByteArray() {
        byte[] dataArray = new byte[HEADER_LENGTH + buff.length];
        crc = evalCRC(); // Correct CRC for a buffer
        dataArray[0] = (byte) (this.tag & 0xFF);
        dataArray[1] = (byte) ((this.tag >> 8) & 0xFF);
        dataArray[2] = (byte) (this.len & 0xFF);
        dataArray[3] = (byte) ((this.len >> 8) & 0xFF);
        dataArray[4] = (byte) (this.error & 0xFF);
        dataArray[5] = (byte) ((this.error >> 8) & 0xFF);
        dataArray[6] = (byte) (this.crc & 0xFF);
        dataArray[7] = (byte) ((this.crc >> 8) & 0xFF);
        for (int i = 0; i < buff.length; i++) {
            dataArray[HEADER_LENGTH + i] = buff[i];
        }
        return dataArray;
    }

    public static HighIOPackage fromByteArray(byte[] dataArray) throws IllegalHighIOPackage {
        return createInstance(dataArray[0] + (dataArray[1] << 8),
                dataArray[2] + (dataArray[3] << 8),
                dataArray[4] + (dataArray[5] << 8),
                dataArray[6] + (dataArray[7] << 8),
                Arrays.copyOfRange(dataArray, HEADER_LENGTH, 0));
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

    private static int evalCRC(byte[] dataBuffer) {
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
