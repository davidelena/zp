package com.dophin.utils;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

public class QiniuUtils {
	private static final String ZP_QINIU_ACCESS_KEY = CommonUtils.QINIU_ACCESS_KEY;
	private static final String ZP_QINIU_SECRECT_KEY = CommonUtils.QINIU_SCECRECT_KEY;
	private static final String BUCKET = CommonUtils.QINIU_BUCKET;
	private static Auth auth = Auth.create(ZP_QINIU_ACCESS_KEY, ZP_QINIU_SECRECT_KEY);
	private static UploadManager uploadManager = new UploadManager();
	private static Logger logger = Logger.getLogger(QiniuUtils.class);

	/**
	 * 七牛上传文件
	 * 
	 * @param data
	 *            文件流二进制数组
	 * @param key
	 *            文件key
	 * @param token
	 *            文件上传凭证
	 * @return 文件是否上传响应流
	 */
	public static Response uploadFile(byte[] data, String key, String token) {
		try {
			Response response = uploadManager.put(data, key, getToken(key));
			logger.info("url: " + response.url());
			logger.info("stringMap: " + response.jsonToMap());
			logger.info("isOK: " + response.isOK());
			logger.info("bodyString: " + response.bodyString());
			return response;

		} catch (QiniuException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 删除七牛云存储文件
	 */
	public static void deleteFile(String key) {
		try {
			BucketManager bucketManager = new BucketManager(auth);
			bucketManager.delete(CommonUtils.QINIU_BUCKET, key);
			System.err.println(auth);
		} catch (QiniuException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static String getToken(String key) {
		return auth.uploadToken(BUCKET, key);
	}

	public static boolean isExists(String key, String prefix) {
		logger.info("key: " + key);
		boolean flag = false;
		BucketManager bucketManager = new BucketManager(auth);
		BucketManager.FileListIterator it = bucketManager.createFileListIterator(CommonUtils.QINIU_BUCKET, prefix);
		while (it.hasNext()) {
			FileInfo[] items = it.next();
			if (items.length > 0) {
				if (key.equalsIgnoreCase(items[0].key)) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}
}
