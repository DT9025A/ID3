package org.FieldCongratulations.id3;

public class ID3Encoding
{
	public static final byte GBK = 0;
	public static final byte UCS_2 = 1;
	public static final byte UTF_16BE_Without_BOM = 2;
	public static final byte UTF_8 = 3;
	
	public static String getEncoding(byte encoding) {
		return switch (encoding) {
			case ID3Encoding.UCS_2 -> "UTF_16LE";
			case ID3Encoding.UTF_16BE_Without_BOM -> "UTF_16BE";
			case ID3Encoding.UTF_8 -> "UTF-8";
			default -> "GBK";
		};
	}
}
