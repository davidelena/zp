package com.dophin.utils;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import com.dophin.utils.QiniuUtils;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛工具类测试
 * 
 * @author David.dai
 * 
 */
public class TestQiniuUtils {

	private static final String ZP_QINIU_ACCESS_KEY = "5n3bnh2dQVnZF4cH6yTb3Vbqz_xWxpWzxbDTbww0";
	private static final String ZP_QINIU_SECRECT_KEY = "OMaW_6fTFLHKd5CFkBnQ0JSZ18Hy6sONEgkj2keh";
	private static final String BUCKET = "first-portal";
	private static Auth auth = Auth.create(ZP_QINIU_ACCESS_KEY, ZP_QINIU_SECRECT_KEY);

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void qiniuUpload() throws IOException {
		String key = "touxiang_male.jpg";
		String token = auth.uploadToken(BUCKET, key);
		// String path = "D:" + File.separator + "qiniu_pic" + File.separator +
		// "二维码 242.jpg";
		// String path = "D:" + File.separator + "qiniu_pic" + File.separator +
		// "Golang与高性能DSP竞价系统.pdf";
		String path = "D:" + File.separator + "qiniu_pic" + File.separator + "touxiang_male.jpg";

		/*
		 * byte[] fileBytes = null; try (FileInputStream fis = new
		 * FileInputStream(new File(path))) { fileBytes = new
		 * byte[fis.available()]; fis.read(fileBytes); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

		UploadManager uploadManager = new UploadManager();
		Response response = uploadManager.put(new File(path), key, token);
		String url = response.url();
		StringMap stringMap = response.jsonToMap();
		System.err.println("url: " + url);
		System.err.println("stringMap: " + stringMap);
		System.err.println("isOK: " + response.isOK());
		System.err.println("bodyString: " + response.bodyString());
	}

	@Test
	public void qiniuUpload2() throws IOException {
		String key = "opus_50_19_soamonitor.zip";
		String token = auth.uploadToken(BUCKET, key);
		String path = "D:" + File.separator + "qiniu_pic" + File.separator + "soamonitor.zip";

		byte[] fileBytes = null;
		try (FileInputStream fis = new FileInputStream(new File(path))) {
			fileBytes = new byte[fis.available()];
			fis.read(fileBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		UploadManager uploadManager = new UploadManager();
		System.err.println(fileBytes.length);
		Response response = uploadManager.put(fileBytes, key, token);
		String url = response.url();
		StringMap stringMap = response.jsonToMap();
		System.err.println("url: " + url);
		System.err.println("stringMap: " + stringMap);
		System.err.println("isOK: " + response.isOK());
		System.err.println("bodyString: " + response.bodyString());
	}

	@Test
	public void testDeleteFile() {
		String key = "touxiang_male.jpg";
		QiniuUtils qiniuUtils = new QiniuUtils();
		qiniuUtils.deleteFile(key);
	}

	@Test
	public void testQiniuFileExists() {
		boolean flag = QiniuUtils.isExists("headpic_1_liualiu1.jpg", "headpic_");
		System.err.println(flag);
	}
}
