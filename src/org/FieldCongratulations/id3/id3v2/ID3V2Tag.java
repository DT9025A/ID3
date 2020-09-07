package org.FieldCongratulations.id3.id3v2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

import org.FieldCongratulations.id3.ID3Versions;

public class ID3V2Tag {
	public int version = ID3Versions.NULL;
	public byte resVersion = 0;
	public byte flag = 0;
	public int size = 0;
	public byte[] rawData = null;
	public ArrayList<ID3V2BasicFrame> frameList = new ArrayList<>();

	public void analyseFrames() throws IOException {
		if (rawData == null)
			return;

		InputStream isr = new ByteArrayInputStream(rawData);
		byte[] data;
		ID3V2BasicFrame frame;

		while (isr.available() > 10) {
			
			data = new byte[4];
			frame = new ID3V2BasicFrame();

			// read frame id
			isr.read(data);
			frame.setFrameID(new String(data));

			// read frame size
			isr.read(data);
			frame.setSize(((data[0] & 0xff) << 24) | 
					((data[1] & 0xff) << 16) | 
					((data[2] & 0xff) << 8) | 
					(data[3] & 0xff));

			if (frame.getSize() < 1)
				break;

			// read flags
			data = new byte[2];
			isr.read(data);
			frame.setFlags(data);

			// read frame data
			data = new byte[frame.getSize()];
			isr.read(data);
			frame.setRawData(data);

			frameList.add(frame);
		}
		isr.close();
	}

	public String dumpFrames() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		Pattern p = Pattern.compile("(T\\w{3}|COMM)");
		ID3V2StringFrame id3V2StringFrame;

		for (ID3V2BasicFrame f : frameList) {
			sb.append(f.getFrameID());
			sb.append(", ");
			sb.append(f.getSize());
			sb.append("bytes, ");
			if (p.matcher(f.getFrameID()).matches()) {
				id3V2StringFrame = new ID3V2StringFrame(f);
				if(id3V2StringFrame.getNativeLanguage() != null && !Objects.equals(id3V2StringFrame.getNativeLanguage(), "")) {
					sb.append("Language: ").append(id3V2StringFrame.getNativeLanguage()).append(", ");
				}
				sb.append("Content : ").append(id3V2StringFrame.getString());
			}
			else if (f.getFrameID().contentEquals("APIC"))
				sb.append(new ID3V2APICFrame(f.getRawData()).toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public ID3V2BasicFrame getFrameAt(int p) {
		return frameList.get(p);
	}

	@Override
	public String toString() {
		String s = null;
		try {
			s = dumpFrames();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

}
