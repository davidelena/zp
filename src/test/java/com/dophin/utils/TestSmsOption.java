package com.dophin.utils;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试短信对接
 * 
 * @author dailiwei
 * 
 */
public class TestSmsOption {

	private String appid = "10346";
	private String secretpwd = "2a122ee20d4c57d08c56c174e880ca73";
	private String smsUrl = "http://api.submail.cn/message/xsend.json";
	private String projectCode = "ZLsEP2";

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSms() {
		System.out.println("sms接口");

		Form form = new Form();
		form.param("appid", appid);
		form.param("to", "13661896734");
		form.param("project", projectCode);
		form.param("signature", secretpwd);
		form.param("vars", "{\"code\":\"" + System.currentTimeMillis() + "\"}");

		Entity<Form> formEntity = Entity.form(form);
		Response response = ClientBuilder.newClient().target(smsUrl).request(MediaType.APPLICATION_JSON)
				.post(formEntity);
		System.out.println(response.getStatusInfo().getStatusCode());
		String result = response.readEntity(String.class);
		System.out.println(result);
		System.out.println("短信发送成功！");
		
	}
	
	@Test
	public void testGetSmsConfig() {
		System.out.println(CommonUtils.SMS_APPID);
		System.out.println(CommonUtils.SMS_URL);
		System.out.println(CommonUtils.SMS_REGISTER_CODE);
		System.out.println(CommonUtils.SMS_SIGNATURE);
		
	}

	@Test
	public void testSmsByConfig() {
		System.out.println("sms接口byconfig");

		Form form = new Form();
		form.param("appid", CommonUtils.SMS_APPID);
		form.param("to", "13661896734");
		form.param("project", CommonUtils.SMS_REGISTER_CODE);
		form.param("signature", CommonUtils.SMS_SIGNATURE);
		form.param("vars", "{\"code\":\"" + System.currentTimeMillis() + "\"}");

		Entity<Form> formEntity = Entity.form(form);
		Response response = ClientBuilder.newClient().target(CommonUtils.SMS_URL).request(MediaType.APPLICATION_JSON)
				.post(formEntity);
		System.out.println(response.getStatusInfo().getStatusCode());
		String result = response.readEntity(String.class);
		System.out.println(result);
		System.out.println("短信发送成功！");
	}
}
