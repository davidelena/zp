package com.dophin.utils;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

/**
 * 图像工具类
 * @author dailiwei
 *
 */
public class ImageUtils {
	
	private static Logger logger = Logger.getLogger(ImageUtils.class);

	/**
	 * 图片缩放
	 * 
	 * @param srcImg
	 *            原图片
	 * @param destImg
	 *            缩放后的图片
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 */
	public static void scale(String srcImg, String destImg, int w, int h) {
		try {
			double wr = 0, hr = 0;
			File srcFile = new File(srcImg);
			File destFile = new File(destImg);
			BufferedImage bufImg = ImageIO.read(srcFile);
			Image Itemp = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			wr = w * 1.0 / bufImg.getWidth();
			hr = h * 1.0 / bufImg.getHeight();
			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
			Itemp = ato.filter(bufImg, null);
			ImageIO.write((BufferedImage) Itemp, destImg.substring(destImg.lastIndexOf(".") + 1), destFile);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 图像裁剪类
	 * 
	 * @param srcImg
	 *            原图像地址
	 * @param destImg
	 *            结果图像地址
	 * @param x
	 *            裁剪坐标x
	 * @param y
	 *            裁剪坐标y
	 * @param w
	 *            裁剪宽度w
	 * @param h
	 *            裁剪高度h
	 */
	public static void cut(String srcImg, String destImg, int x, int y, int w, int h) {

		try {
			Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = (ImageReader) iterator.next();
			InputStream in = new FileInputStream(srcImg);

			ImageInputStream iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, w, h);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "jpg", new File(destImg));

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
