package com.dophin.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码工具类
 * 
 * @author David.dai
 * 
 */
public class QrCodeUtils {

	private static final String JPG = "jpg";
	private static final String UTF_8 = "UTF-8";
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static Logger logger = Logger.getLogger(QrCodeUtils.class);

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	public static boolean generateQrcode(String contents, String key, String path) {
		return generateQrcode(contents, key, path, 300, 300);
	}

	public static boolean generateQrcode(String contents, String key, String path, Integer width, Integer height) {
		boolean flag = false;
		try {
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, UTF_8);
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			BitMatrix bitMatrix = multiFormatWriter.encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
			File file = new File(path, key);
			QrCodeUtils.writeToFile(bitMatrix, JPG, file);
			flag = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return flag;
	}
}
