package org.FieldCongratulations.id3.id3v2;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ID3V2BasicFrame {
	private String frameID = "";// 4byte
	private int size = 0;// 4byte
	private byte[] flags;// 2byte
	private byte[] rawData;// 数据区data

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public byte[] getRawData() {
		return rawData;
	}

	public void setFlags(byte[] flags) {
		this.flags = flags;
	}

	public byte[] getFlags() {
		return flags;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setFrameID(String frameID) {
		this.frameID = frameID;
	}

	public String getFrameID() {
		return frameID;
	}

	public byte[] getByteArray() {
		return null;
	}
}
