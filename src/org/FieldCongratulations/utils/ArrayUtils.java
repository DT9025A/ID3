package org.FieldCongratulations.utils;

public class ArrayUtils
{/*
	public static <T> T[] appendArray(T[] src1, T[] src2, T[] dst) {
		int ptr = 0;
		int len = src1.length + src2.length;
		if (dst.length < len)
			return null;
		for (T s : src1)
			dst[ptr++] = s;
		for (T s:src2)
			dst[ptr++] = s;
		return dst;
	}*/

	public static <T> T[] appendArrayFromIndex(int index, T[] src, T[] dst) {
		if (dst.length < index + src.length)
			return null;
		for (T s : src)
			dst[index++] = s;
		return dst;
	}

	public static Byte[] byteToByteArray(byte[] arr) {
		Byte[] b=new Byte[arr.length];
		int p=0;
		for (byte c:arr)
			b[p++] = c;
		return b;
	}

	public static byte[] byteTobyteArray(Byte[] arr) {
		byte[] b=new byte[arr.length];
		int p=0;
		for (Byte c:arr)
			b[p++] = c;
		return b;
	}
}
