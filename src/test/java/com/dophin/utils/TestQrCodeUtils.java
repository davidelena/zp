package com.dophin.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class TestQrCodeUtils {

	@Test
	public void testGenerateQrcode() throws WriterException, IOException {
		String url = "http://www.baidu.com";
		String path = "D:" + File.separator + "qiniu_pic";
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
		File file = new File(path, "baidu_qrcode.jpg");
		QrCodeUtils.writeToFile(bitMatrix, "jpg", file);
		System.err.println("生成二维码成功!");

	}
}
