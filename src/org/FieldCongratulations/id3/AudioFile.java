package org.FieldCongratulations.id3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.FieldCongratulations.id3.id3v1.ID3V1Tag;
import org.FieldCongratulations.id3.id3v2.ID3V2Tag;

public class AudioFile
{
	public static int getFileID3Version(String filename) throws FileNotFoundException,IOException {
		return getFileID3Version(new File(filename));
	}

	public static byte getFileID3Version(File file) throws FileNotFoundException,IOException {
		byte result = 0;
		RandomAccessFile srcFile = new RandomAccessFile(file, "r");
		byte[] tag = new byte[3];
		byte[] tID3V1 = new byte[] { 'T', 'A', 'G' };
		byte[] tID3V2 = new byte[] { 'I', 'D', '3' };
		srcFile.read(tag, 0, 3);
		if (Arrays.equals(tID3V2, tag)) {
			switch (srcFile.readByte()) {
				case 3 -> result |= ID3Versions.ID3V2_3;
				case 4 -> result |= ID3Versions.ID3V2_4;
				default -> result |= ID3Versions.NULL;
			}
		}
		srcFile.seek(srcFile.length() - 128);
		srcFile.read(tag);
		if (Arrays.equals(tID3V1, tag)) {
			result |= ID3Versions.ID3V1;
		} else
			result |= ID3Versions.NULL;

		srcFile.close();
		return result;
	}

	public static ID3V2Tag readID3V2Tag(String file) throws IOException {
		return readID3V2Tag(new File(file));
	}

	public static ID3V2Tag readID3V2Tag(File file) throws IOException {
		ID3V2Tag result = new ID3V2Tag();
		int versions = getFileID3Version(file);
		RandomAccessFile src = new RandomAccessFile(file, "r");
		byte[] data = new byte[4];

		src.seek(3);

		if ((versions & 0x06) != 0)
			result.version = src.readByte();
		else {
			src.close();
			return result;
		}

		result.resVersion = src.readByte();
		result.flag = src.readByte();

		src.read(data);
		result.size = ((data[0] & 0x7f) << 21) | 
				((data[1] & 0x7f) << 14) | 
				((data[2] & 0x7f) << 7) | 
				(data[3] & 0x7f);

		result.rawData = new byte[result.size];
		src.read(result.rawData);

		src.close();

		result.analyseFrames();

		return result;
	}

	public static ID3V1Tag readID3V1Tag(String filename) throws FileNotFoundException,IOException {
		return readID3V1Tag(new File(filename));
	}

	public static ID3V1Tag readID3V1Tag(File file) throws FileNotFoundException,IOException {
		ID3V1Tag result = new ID3V1Tag();
		int versions = getFileID3Version(file);
		RandomAccessFile src = new RandomAccessFile(file, "r");
		byte[] info = new byte[30];
		byte reserved;

		if ((versions & ID3Versions.ID3V1) == 0) {
			src.close();
			return result;
		}
			
		src.seek(src.length() - 125);
		src.read(info);
		result.Title = new String(info,"GBK");
		src.read(info);
		result.Artist = new String(info,"GBK");
		src.read(info);
		result.Album = new String(info,"GBK");

		info = new byte[4];
		src.read(info);
		result.Year = new String(info);

		info = new byte[28];

		src.read(info);
		result.Comment = new String(info,"GBK");

		if ((reserved = src.readByte()) == 0)
			result.Track = src.readByte();
		else
			result.Comment += new String(new byte[]{reserved,src.readByte()},"GBK");

		result.Genre = src.readByte();

		src.close();
		return result;
	}
}


