package com.dophin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class CommonUtils {
	private static Logger logger = Logger.getLogger(CommonUtils.class);

	public static WebApplicationContext wctx;
	public static ApplicationContext ctx;
	public static String CONTEXT_URL = "localhost";
	public static String MAIL_SMTP_HOST = "smtp.mxhichina.com";
	public static String MAIL_USER = "us@zhancampus.com";
	public static String MAIL_PASSWORD = "diyiZHAN072315";
	public static String SMS_APPID = "10346";
	public static String SMS_SIGNATURE = "2a122ee20d4c57d08c56c174e880ca73";
	public static String SMS_URL = "http://api.submail.cn/message/xsend.json";
	public static String SMS_REGISTER_CODE = "ZLsEP2";
	public static String SMS_RESETPWD_CODE = "6zim52";
	public static String SMS_RESETPWD_NOTIFIY_CODE = "7uBqT4";
	public static String SMS_BINDING_NOTIFIY_CODE = "l3eJv2";
	public static String ES_HOST = "121.41.72.42";
	public static String QINIU_ACCESS_KEY = "5n3bnh2dQVnZF4cH6yTb3Vbqz_xWxpWzxbDTbww0";
	public static String QINIU_SCECRECT_KEY = "OMaW_6fTFLHKd5CFkBnQ0JSZ18Hy6sONEgkj2keh";
	public static String QINIU_BUCKET = "first-portal";
	public static String QINIU_FILE_DOMAIN = "http://7xonvy.com2.z0.glb.qiniucdn.com";

	public static String TASK_TIME = "01:00:00";

	public static int ES_PORT = 9300;
	public static String ES_INDEX = "firstportal";
	public static String YYYY_MM_DD = "yyyy-MM-dd";
	public static String YYYY_MM_DD_DATA = "yyyy/MM/dd";
	public static String YYYY_MM_DD_CHINESE = "yyyy年MM月dd日";

	private static final int PAGE_SIZE = 5;

	static {
		try {
			PropertiesConfiguration conf = new PropertiesConfiguration("config.properties");
			CONTEXT_URL = conf.getString("context.url", CONTEXT_URL);
			MAIL_SMTP_HOST = conf.getString("mail.smtp.host", MAIL_SMTP_HOST);
			MAIL_USER = conf.getString("mail.user", MAIL_USER);
			MAIL_PASSWORD = conf.getString("mail.password", MAIL_PASSWORD);

			SMS_APPID = conf.getString("sms.appid", SMS_APPID);
			SMS_SIGNATURE = conf.getString("sms.signature", SMS_SIGNATURE);
			SMS_URL = conf.getString("sms.url", SMS_URL);
			SMS_REGISTER_CODE = conf.getString("sms.project.code", SMS_REGISTER_CODE);

			ES_HOST = conf.getString("elasticsearch.host", ES_HOST);
			ES_PORT = conf.getInt("elasticsearch.port", ES_PORT);
			ES_INDEX = conf.getString("elasticsearch.dataIndex", ES_INDEX);

			QINIU_ACCESS_KEY = conf.getString("qiniu.accesskey", QINIU_ACCESS_KEY);
			QINIU_SCECRECT_KEY = conf.getString("qiniu.secrectkey", QINIU_SCECRECT_KEY);
			QINIU_BUCKET = conf.getString("qiniu.bucket", QINIU_BUCKET);
			QINIU_FILE_DOMAIN = conf.getString("qiniu.file.domain", QINIU_FILE_DOMAIN);

			TASK_TIME = conf.getString("task.time", TASK_TIME);

		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public static ApplicationContext getApplicationContext() {
		if (ctx == null) {
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return ctx;
	}

	/**
	 * 正则验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 正则验证手机
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 产生6位不重复的数字验证码
	 */
	public static String generateRandomSix() {
		Random r = new Random();
		List<Integer> ls = new ArrayList<Integer>(6);
		StringBuilder sb = new StringBuilder();
		int count = 0;
		int current;
		while (true) {
			current = r.nextInt(9);
			if (!ls.contains(current)) {
				ls.add(current);
				count++;
			}

			if (count == 6) {
				break;
			}
		}

		for (Integer item : ls) {
			sb.append(String.valueOf(item));
		}

		return sb.toString();
	}

	/**
	 * 生成我的申请人列表
	 * 
	 * @param size
	 * @param pn
	 * @return
	 */
	public static String generateMyApplicantPnHtml(int size, int pn) {
		int pageIndexNum = (size % PAGE_SIZE == 0) ? (size / PAGE_SIZE) : (size / PAGE_SIZE) + 1;

		String firstPage = String.format("<a href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">首页</a>",
				1);
		StringBuilder sb = new StringBuilder(firstPage);
		if (pn == 1) {
			sb.append("<a href=\"javascript:void(0)\">上一页</a>");
		} else {
			sb.append(String.format("<a href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">上一页</a>",
					(pn - 1)));
		}
		if (pageIndexNum <= 5) {
			for (int i = 1; i <= pageIndexNum; i++) {
				if (i == pn) {
					String html = String.format(
							"<a id=\"page_%d\" style=\"background: #676767 none repeat scroll 0 0\" "
									+ "href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">%d</a>", i, i,
							i);
					sb.append(html);
				} else {
					sb.append(String
							.format("<a id=\"page_%d\" href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">%d</a>",
									i, i, i));
				}
			}
		} else {
			if (pn >= 5) {
				for (int i = (pageIndexNum - 4); i <= pageIndexNum; i++) {
					if (i == pn) {
						String html = String.format(
								"<a id=\"page_%d\" style=\"background: #676767 none repeat scroll 0 0\" "
										+ "href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">%d</a>", i,
								i, i);
						sb.append(html);
					} else {
						sb.append(String
								.format("<a id=\"page_%d\" href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">%d</a>",
										i, i, i));
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					if (i == pn) {
						String html = String.format(
								"<a id=\"page_%d\" style=\"background: #676767 none repeat scroll 0 0\" "
										+ "href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">%d</a>", i,
								i, i);
						sb.append(html);
					} else {
						sb.append(String
								.format("<a id=\"page_%d\" href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">%d</a>",
										i, i, i));
					}
				}
			}
		}

		if (pn == pageIndexNum) {
			sb.append("<a class=\"disabled\" href=\"javascript:void(0)\">下一页</a>");
		} else {
			sb.append(String.format("<a href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">下一页</a>",
					(pn + 1)));
		}
		sb.append(String.format("<a href=\"javascript:;\" class=\"pn\" onclick=\"setPnSettings(%d)\">尾页</a>",
				pageIndexNum));
		return sb.toString();
	}

	/**
	 * 生成分页html
	 * 
	 * @param size
	 * @param pn
	 * @param url
	 * @return
	 */
	public static String generatePnHtml(int size, int pn, String url) {
		int pageIndexNum = (size % PAGE_SIZE == 0) ? (size / PAGE_SIZE) : (size / PAGE_SIZE) + 1;

		StringBuilder sb = new StringBuilder(String.format("<a href=\"%s&pn=%d\">首页</a>", url, 1));
		if (pn == 1) {
			sb.append("<a class=\"disabled\" href=\"javascript:void(0)\">上一页</a>");
		} else {
			sb.append(String.format("<a href=\"%s&pn=%d\">上一页</a>", url, (pn - 1)));
		}
		if (pageIndexNum <= 5) {
			for (int i = 1; i <= pageIndexNum; i++) {
				if (i == pn) {
					String html = String.format("<a style=\"background: #676767 none repeat scroll 0 0\" "
							+ "href=\"%s&pn=%d\">%d</a>", url, i, i);
					sb.append(html);
				} else {
					sb.append(String.format("<a href=\"%s&pn=%d\">%d</a>", url, i, i));
				}
			}
		} else {
			if (pn >= 5) {
				for (int i = (pageIndexNum - 4); i <= pageIndexNum; i++) {
					if (i == pn) {
						String html = String.format("<a style=\"background: #676767 none repeat scroll 0 0\" "
								+ "href=\"%s&pn=%d\">%d</a>", url, i, i);
						sb.append(html);
					} else {
						sb.append(String.format("<a href=\"%s&pn=%d\">%d</a>", url, i, i));
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					if (i == pn) {
						String html = String.format("<a style=\"background: #676767 none repeat scroll 0 0\" "
								+ "href=\"%s&pn=%d\">%d</a>", url, i, i);
						sb.append(html);
					} else {
						sb.append(String.format("<a href=\"%s&pn=%d\">%d</a>", url, i, i));
					}
				}
			}
		}

		if (pn == pageIndexNum) {
			sb.append("<a class=\"disabled\" href=\"javascript:void(0)\">下一页</a>");
		} else {
			sb.append(String.format("<a href=\"%s&pn=%d\">下一页</a>", url, (pn + 1)));
		}
		sb.append(String.format("<a href=\"%s&pn=%d\">尾页</a>", url, pageIndexNum));
		return sb.toString();
	}

	/**
	 * 搜索用生成通用分页插件
	 * 
	 * @param size
	 * @param pn
	 * @return
	 */
	public static String generateSearchPnHtml(int size, int pn) {
		int pageIndexNum = (size % PAGE_SIZE == 0) ? (size / PAGE_SIZE) : (size / 5) + 1;

		StringBuilder sb = new StringBuilder("<a href=\"javascript:;\" onclick=\"setPn(1)\">首页</a>");
		if (pn == 1) {
			sb.append("<a class=\"disabled\" href=\"javascript:void(0)\">上一页</a>");
		} else {
			sb.append(String.format("<a href=\"javascript:;\" onclick=\"setPn(%d)\">上一页</a>", (pn - 1)));
		}
		if (pageIndexNum <= 5) {
			for (int i = 1; i <= pageIndexNum; i++) {
				if (i == pn) {
					String html = String.format("<a style=\"background: #676767 none repeat scroll 0 0\" "
							+ "href=\"javascript:;\" onclick=\"setPn(%d)\">%d</a>", i, i);
					sb.append(html);
				} else {
					sb.append(String.format("<a href=\"javascript:;\" onclick=\"setPn(%d)\">%d</a>", i, i));
				}
			}
		} else {
			if (pn >= 5) {
				for (int i = (pageIndexNum - 4); i <= pageIndexNum; i++) {
					if (i == pn) {
						String html = String.format("<a style=\"background: #676767 none repeat scroll 0 0\" "
								+ "href=\"javascript:;\" onclick=\"setPn(%d)\">%d</a>", i, i);
						sb.append(html);
					} else {
						sb.append(String.format("<a href=\"javascript:;\" onclick=\"setPn(%d)\">%d</a>", i, i));
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					if (i == pn) {
						String html = String.format("<a style=\"background: #676767 none repeat scroll 0 0\" "
								+ "href=\"javascript:;\" onclick=\"setPn(%d)\">%d</a>", i, i);
						sb.append(html);
					} else {
						sb.append(String.format("<a href=\"javascript:;\" onclick=\"setPn(%d)\">%d</a>", i, i));
					}
				}
			}
		}

		if (pn == pageIndexNum) {
			sb.append("<a class=\"disabled\" href=\"javascript:;\">下一页</a>");
		} else {
			sb.append(String.format("<a href=\"javascript:;\" onclick=\"setPn(%d)\">下一页</a>", (pn + 1)));
		}
		sb.append(String.format("<a href=\"javascript:;\" onclick=\"setPn(%d)\">尾页</a>", pageIndexNum));
		return sb.toString();
	}

	/**
	 * 转化为int list工具类
	 * 
	 * @return
	 */
	public static List<Integer> toIntList(String item) {
		List<Integer> list = new ArrayList<Integer>();
		String[] arr = StringUtils.split(item, ",");
		int id = 0;
		for (String idStr : arr) {
			id = NumberUtils.toInt(idStr, 0);
			list.add(id);
		}
		return list;
	}
}
