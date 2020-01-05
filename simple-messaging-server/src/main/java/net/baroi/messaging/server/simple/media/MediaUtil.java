package net.baroi.messaging.server.simple.media;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;

/**
 * 
 * @author Aldrin Baroi
 *
 */
public class MediaUtil {

	/**
	 * 
	 * @param mediaContent
	 * @return
	 */
	public static String encodeToBase64(byte[] mediaContent) {
		return Base64.getEncoder().encodeToString(mediaContent);
	}

	/**
	 * 
	 * @param mediaContent
	 * @return
	 */
	public static byte[] decodeFromBase64(String mediaContent) {
		return Base64.getDecoder().decode(mediaContent);
	}

	/**
	 * 
	 * @param mediaContent
	 * @return
	 */
	public static InputStream getInputStream(String mediaContent) {
		return new ByteArrayInputStream(decodeFromBase64(mediaContent));
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Media laodMediaContent(String path) throws IOException {
		Media media = new Media();
		File file = new File(path);
		String fileName = file.getName();
		media.setMediaName(fileName);
		FileInputStream fileInputStream = new FileInputStream(file);
		media.setMediaType(Files.probeContentType(file.toPath()));
		try (fileInputStream) {
			byte[] mediaContent = new byte[(int) file.length()];
			fileInputStream.read(mediaContent);
			fileInputStream.close();
			media.setMediaContent(encodeToBase64(mediaContent));
			return media;
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 
	 * @param media
	 * @param saveDirectory
	 * @throws IOException
	 */
	public static void saveMediaContent(Media media, String saveDirectory) throws IOException {
		byte[] content = decodeFromBase64(media.getMediaContent());
		FileOutputStream fos = new FileOutputStream(saveDirectory + "/" + media.getMediaName());
		try (fos) {
			fos.write(content);
			fos.close();
		} catch (IOException e) {
			throw e;
		}
	}
}
