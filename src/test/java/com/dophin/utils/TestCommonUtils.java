package com.dophin.utils;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Before;
import org.junit.Test;

public class TestCommonUtils {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCommonUtils() {
		System.out.println(CommonUtils.CONTEXT_URL);
		System.out.println(CommonUtils.MAIL_SMTP_HOST);
		System.out.println(CommonUtils.MAIL_USER);
		System.out.println(CommonUtils.MAIL_PASSWORD);
		System.out.println(DigestUtils.md5Hex("123456"));
		
		System.err.println(CommonUtils.QINIU_ACCESS_KEY);
		System.err.println(CommonUtils.QINIU_SCECRECT_KEY);
		System.err.println(CommonUtils.QINIU_BUCKET);
		System.err.println(CommonUtils.QINIU_FILE_DOMAIN);
		
		System.err.println(CommonUtils.TASK_TIME);
	}
	
	@Test
	public void testEs() {
		
		String url = "http://115.159.62.132:9200/_count?pretty";
		Response response = ClientBuilder.newClient().target(url).request(MediaType.APPLICATION_JSON_TYPE).get();
		String result = response.readEntity(String.class);
		System.out.println(result);
	}
	
	@Test
	public void testString() {
		String memberid = "1441712160702";
		System.out.println(memberid);
		String result = StringUtils.reverse(memberid);
		System.out.println(result);
		System.out.println(StringUtils.reverse(result));
		
		boolean flag = false;
		System.err.println(String.format("flag: %s", flag));
	}
	
	@Test
	public void testStr() {
		System.out.println(DigestUtils.md5Hex("david123456"));
	}

	@Test
	public void testSecurityUtils() throws Exception {
		String memberid = "1441712160702";
		byte[] initKey = DESUtils.initkey();
		System.out.println(initKey.length);
		String mmk = Base64.encodeBase64String(initKey);
		System.out.println("密钥：" + mmk);
		System.out.println("反编译密钥：" + Base64.decodeBase64(mmk).length);

		System.out.println("加密前字段：" + memberid);
		byte[] afterMemberid = DESUtils.encrypt(memberid.getBytes(), initKey);
		System.out.println("加密后字段：" + Base64.encodeBase64String(afterMemberid));

		byte[] data = DESUtils.decrypt(afterMemberid, initKey);
		System.out.println("解密后字段：" + new String());
	}

	@Test
	public void testEmail() {
		String testEmail = "274714299@qq.com";
		sendActivationMail(testEmail, "test email");
	}

	public void sendActivationMail(String toAddress, String content) {
		Email email = new SimpleEmail();
		email.setHostName(CommonUtils.MAIL_SMTP_HOST);
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator(CommonUtils.MAIL_USER, CommonUtils.MAIL_PASSWORD));
		email.setSSLOnConnect(false);
		email.setCharset("UTF-8");
		try {
			email.setFrom(CommonUtils.MAIL_USER);
			email.setSubject("注册激活邮件");
			StrBuilder contents = new StrBuilder();
			contents.appendln("亲爱的用户您好，");
			contents.appendln("\t请点击以下激活链接，激活您的账号");
			contents.appendln(String.format("\t激活地址：%s", content));
			email.setMsg(contents.toString());

			email.addTo(toAddress);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testToIntList() {
		String str1 = "88";
		String str2 = "12,13,15,";
		String str3= "16,18";
		
		List<Integer> list1 = CommonUtils.toIntList(str1);
		List<Integer> list2 = CommonUtils.toIntList(str2);
		List<Integer> list3 = CommonUtils.toIntList(str3);
		System.err.println(list1);
		System.err.println(list2);
		System.err.println(list3);
	}

}
