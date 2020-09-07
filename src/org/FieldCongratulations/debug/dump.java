package org.FieldCongratulations.debug;

public class dump
{
//	private static String[] Hex={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

	public static <T> String dumpArray(T[] b) {
		StringBuilder stringBuilder = new StringBuilder(b.getClass().getName()+" (array)[");
		stringBuilder.append(b.length);
		stringBuilder.append("] : {");
		for (T c : b) {
			stringBuilder.append(c);
			stringBuilder.append(", ");
		}
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
		stringBuilder.append("}");
		return stringBuilder.toString();
	}

/*	private static String b2h(byte x) {
		int y = x & 0xff;
		return Hex[y / 16] + Hex[y % 16];
	}*/

	public static String bin(int data) {
		StringBuilder sb = new StringBuilder("Integer:");
		sb.append(String.format("%d HEX:%X OCT:%o BIN:", data, data, data));
		for (int i=0;i < 32;i++)
			sb.append(((data << i) & 0x80000000) == 0 ?"0": "1");
		return sb.toString();
	}
}
