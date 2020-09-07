package org.FieldCongratulations.id3.id3v2;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.FieldCongratulations.id3.ID3Encoding;
import org.FieldCongratulations.id3.ID3Versions;
import org.FieldCongratulations.utils.ArrayUtils;

public class ID3V2StringFrame extends ID3V2BasicFrame {
    private byte encoding = ID3Encoding.UCS_2;
    private byte id3Version = ID3Versions.ID3V2_3;
    private String string = "";
    private String commentLanguage = null;

    public ID3V2StringFrame(ID3V2BasicFrame f) throws UnsupportedEncodingException {
        setRawData(f.getRawData());
        indexData(f.getFrameID().equalsIgnoreCase("COMM"));
    }

    public ID3V2StringFrame(byte[] dat, boolean isCOMM) throws UnsupportedEncodingException {
        setRawData(dat);
        indexData(isCOMM);
    }

    private void indexData(boolean isComm) throws UnsupportedEncodingException {
        encoding = getRawData()[0];
        byte[] temp = Arrays.copyOfRange(getRawData(), 1, getRawData().length);
        int zeroIndex;

        if (isComm &&
                (zeroIndex = indexOf(Arrays.copyOfRange(temp, 1, temp.length))) != -1) {
            commentLanguage = new String(Arrays.copyOfRange(temp, 0, zeroIndex + 1));
            string = new String(Arrays.copyOfRange(temp, zeroIndex + 2, temp.length), getCharset());
        } else {
            string = new String(temp, getCharset());
        }
    }

    private int indexOf(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == (byte) 0)
                return i;
        }
        return -1;
    }

    public String getNativeLanguage() {
        return commentLanguage;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setId3Version(byte id3Version) {
        this.id3Version = id3Version;
    }

    public byte getId3Version() {
        return id3Version;
    }

    public void setEncoding(byte encoding) {
        this.encoding = encoding;
    }

    public byte getEncoding() {
        return encoding;
    }

    private String getCharset() {
        return ID3Encoding.getEncoding(encoding);
    }

    public byte[] getFrameAsByteArray() throws UnsupportedEncodingException {
        byte[] stringSrc = string.getBytes(getCharset());
        Byte[] result = new Byte[stringSrc.length + 1];
        ArrayUtils.appendArrayFromIndex(1, ArrayUtils.byteToByteArray(stringSrc), result);
        return ArrayUtils.byteTobyteArray(result);
    }
}
