package org.FieldCongratulations.id3.exception;

public class FrameConvertException extends Throwable {
	private String msg = "";
	public static final long serialVersionUID = 0x66ccff;

	public FrameConvertException(String message) {
		msg = message;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
