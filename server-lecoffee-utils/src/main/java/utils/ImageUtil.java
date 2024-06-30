package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageUtil {
	public static String geRenderedImage(byte[] imageBytes) {
		return imageBytes != null ? Base64.getEncoder().encodeToString(imageBytes) : null;
	}
	
	public static byte[] getBytesFromInputStream(InputStream stream) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		try {
			while ((nRead = stream.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}
}
