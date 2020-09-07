package org.FieldCongratulations.id3.id3v1;
import java.util.*;
import org.FieldCongratulations.utils.*;
import java.io.*;

public class ID3V1Tag
{
	public String Title = "";
	public String Artist = "";
	public String Album = "";
	public String Year = "";
	public String Comment = "";
	public byte Track = -1;
	public byte Genre = 0;

	@Override
	public String toString() {
		return String.format("ID3V1 Tag {\n\tTitle:%s\n\tArtist:%s\n\tAlbum:%s\n\tYear:%s\n\tComment:%s\n\tTrack:%d\n\tGenre:%d\n}",
							 Title, Artist, Album, Year, Comment,Track ,Genre);
		//return super.toString();
	}

	public Byte[] getTagAsByteArray() throws UnsupportedEncodingException {
		Byte[] result = new Byte[128];
		Arrays.fill(result, (byte)0);
		result = ArrayUtils.appendArrayFromIndex(0, ArrayUtils.byteToByteArray("TAG".getBytes()), result);
		result = ArrayUtils.appendArrayFromIndex(3, ArrayUtils.byteToByteArray(Arrays.copyOfRange(Title.getBytes("GBK"), 0, 30)), result);
		result = ArrayUtils.appendArrayFromIndex(33, ArrayUtils.byteToByteArray(Arrays.copyOfRange(Artist.getBytes("GBK"), 0, 30)), result);
		result = ArrayUtils.appendArrayFromIndex(63, ArrayUtils.byteToByteArray(Arrays.copyOfRange(Album.getBytes("GBK"), 0, 30)), result);
		result = ArrayUtils.appendArrayFromIndex(93, ArrayUtils.byteToByteArray(Arrays.copyOfRange(Year.getBytes(), 0, 4)), result);
		if (Track != -1) {
			result = ArrayUtils.appendArrayFromIndex(97, ArrayUtils.byteToByteArray(Arrays.copyOfRange(Comment.getBytes(), 0, 29)), result);
			result[125] = 0;
			result[126] = Track;
			//result[127]=Genre;
		} else {
			result = ArrayUtils.appendArrayFromIndex(97, ArrayUtils.byteToByteArray(Arrays.copyOfRange(Comment.getBytes("GBK"), 0, 30)), result);
		}
		result[127] = Genre;
		return result;
	}


}
