package org.FieldCongratulations.id3.id3v2;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.FieldCongratulations.id3.exception.FrameConvertException;

public class ID3V2APICFrame extends ID3V2BasicFrame {
	public byte type;
	public String mime;
	public byte mimeEncoding;
	//public String description = "";
	//public byte descriptionEncoding = 0;
	public byte[] data;

	/*
	public byte[] getFrameAsByteArray() throws UnsupportedEncodingException {
		byte[] descriptionBytes = description.getBytes(ID3Encoding.getEncoding(descriptionEncoding));
		
		byte[] result = new byte[0];
		return result;
	}
	*/

	@Override
	public String toString() {
		return String.format(
				"APIC {\n\tMIME:%s\n\tMIMEEncode:%d\n\tPictureType:%d\n\tPictureSize:%d\n\tFirst 2 bytes: %X %X\n}"
				, mime, mimeEncoding, type, data.length, data[0], data[1]);
	}

	public ID3V2APICFrame(byte[] pdata) throws UnsupportedEncodingException {
		setRawData(pdata);
		mime = getPictureMIME();
		data = getPictureData();
		mimeEncoding = getRawData()[0];
		type = getPictureType();
	}

	public ID3V2APICFrame(ID3V2BasicFrame f) throws UnsupportedEncodingException, FrameConvertException {
		if (!f.getFrameID().contentEquals("APIC"))
			throw new FrameConvertException("Can not convert ID3V2 frame \"" + f.getFrameID() + "\" to frame \"APIC\"");
		setRawData(f.getRawData());
		mime = getPictureMIME();
		data = getPictureData();
		mimeEncoding = getRawData()[0];
		type = getPictureType();
	}

	private int ansiC_strlen(byte[] s, int encoding) {
		int i = 0;
		if (encoding == 0) {
			while (s[i] != 0)
				i++;
			i++;
		} else {
			while (!(s[i] == 0 && s[i + 1] == 0))
				i += 2;
			i += 2;
		}
		return i;
	}

	public byte[] getPictureData() {
		int startOffset = ansiC_strlen(Arrays.copyOfRange(getRawData(), 1, getRawData().length), getRawData()[0]) + 2;
		return Arrays.copyOfRange(getRawData(), startOffset + 1, getRawData().length);
	}

	public String getPictureMIME() throws UnsupportedEncodingException {
		return new String(
				Arrays.copyOfRange(getRawData(), 1,
						ansiC_strlen(Arrays.copyOfRange(getRawData(), 1, getRawData().length), getRawData()[0])),
				getRawData()[0] == 0 ? "GB2312" : "UTF-8");
	}

	public byte getPictureType() {
		return getRawData()[ansiC_strlen(Arrays.copyOfRange(getRawData(), 1, getRawData().length), getRawData()[0]) + 1];
	}
}
