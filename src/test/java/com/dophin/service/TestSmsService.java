package com.dophin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.SmsCodeDTO;
import com.dophin.dto.SmsLogDTO;
import com.dophin.enums.SmsTypeEnum;
import com.dophin.utils.CommonUtils;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSmsService extends TestBaseService {
	
	private static Logger logger = Logger.getLogger(TestSmsService.class);
	private static final String STATUS = "status";
	private static final String MSG = "msg";
	private static final String SUCCESS = "success";

	@Autowired
	private SmsService smsService;

	@Test
	public void testInsertSmsCode() {
		SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
		smsCodeDTO.setId(0);
		smsCodeDTO.setMobile("13661896734");
		smsCodeDTO.setCode(generateRandomSix());

		int id = smsService.insertSmsCode(smsCodeDTO);
		System.out.println(id);
	}

	@Test
	public void testQuerySmsCode() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", 18);

		SmsCodeDTO smsCodeDTO = smsService.querySmsCode(queryMap);
		System.err.println(smsCodeDTO);

		Map<String, Object> queryMap2 = new HashMap<String, Object>();
		queryMap2.put("mobile", "13661896735");
		SmsCodeDTO smsCodeDTO2 = smsService.querySmsCode(queryMap2);
		System.err.println(smsCodeDTO2);

	}

	@Test
	public void testUpdateSmsCode() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", 2);

		SmsCodeDTO smsCodeDTO = smsService.querySmsCode(queryMap);
		System.err.println(smsCodeDTO);

		smsCodeDTO.setCode("123456");
		smsService.updateSmsCode(smsCodeDTO);

		SmsCodeDTO resultDTO = smsService.querySmsCode(queryMap);
		System.err.println(resultDTO);
	}

	@Test
	public void testDeleteSmsCode() {
		int count = smsService.deleteSmsCode(1);
		System.err.println(count);
	}

	@Test
	public void testInsertSmsLog() {
		SmsLogDTO smsLogDTO = new SmsLogDTO();
		smsLogDTO.setContent("测试内容");
		smsLogDTO.setMobile(String.valueOf(System.currentTimeMillis()));
		smsLogDTO.setSmsType(SmsTypeEnum.Register.getCode());

		int count = smsService.insertSmsLog(smsLogDTO);
		System.err.println(count);
	}
	
	@Test
	public void testQuerySmsLog() {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("id", 1);
		SmsLogDTO smsLogDTO = smsService.querySmsLog(queryMap);
		System.err.println(smsLogDTO);
		
		Map<String, Object> queryMap2 = new HashMap<>();
		queryMap2.put("mobile", "1442910693174");
		SmsLogDTO smsLogDTO2 = smsService.querySmsLog(queryMap2);
		System.err.println(smsLogDTO2);
	}
	
	@Test
	public void testUpdateSmsLog() {
		
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("id", 1);
		SmsLogDTO smsLogDTO = smsService.querySmsLog(queryMap);
		System.err.println(smsLogDTO);
		
		smsLogDTO.setContent("更新后内容");
		smsLogDTO.setSmsType(SmsTypeEnum.Notification.getCode());
		smsService.updateSmsLog(smsLogDTO);
		
		SmsLogDTO smsLogDTO2 = smsService.querySmsLog(queryMap);
		System.err.println(smsLogDTO2);
		
	}
	
	@Test
	public void testIsSmsCodeExists() {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("mobile", "13661896735");
		boolean isExists = smsService.isSmsCodeExists(queryMap);
		System.err.println(isExists);
	}
	
	@Test
	public void testQuerySmsLogs() {
		List<SmsLogDTO> ls = smsService.querySmsLogs();
		System.err.println(ls.size());
		for (SmsLogDTO item : ls) {
			System.err.println(item);
		}
	}
	
	@Test
	public void testDeleteSmsLog() {
		int count = smsService.deleteSmsLog(1);
		System.err.println(count);
	}
	

	@Test
	public void testGenerateRandomSix() {
		System.err.println(generateRandomSix());
	}
	
	@Test
	public void testSendSmsCode() {
		sendSmsCode("13661896734", generateRandomSix(), new HashMap<String, Object>());
	}
	
	private void sendSmsCode(String mobile, String code, Map<String, Object> resultMap) {

		try {
			Form form = new Form();
			form.param("appid", CommonUtils.SMS_APPID);
			form.param("to", mobile);
			form.param("project", CommonUtils.SMS_REGISTER_CODE);
			form.param("signature", CommonUtils.SMS_SIGNATURE);
			form.param("vars", "{\"code\":\"" + code + "\"}");

			Entity<Form> formEntity = Entity.form(form);
			Response response = ClientBuilder.newClient().target(CommonUtils.SMS_URL)
					.request(MediaType.APPLICATION_JSON).post(formEntity);
			logger.info("send sms response status: " + response.getStatusInfo().getStatusCode());
			System.out.println();
			String responseStr = response.readEntity(String.class);

			@SuppressWarnings("unchecked")
			Map<String, Object> result = mapper.readValue(responseStr, Map.class);
			if (result.containsKey(STATUS)) {
				String status = result.get(STATUS).toString();
				if (status.equals(SUCCESS)) {
					resultMap.put(SUCCESS, 1);
					resultMap.put(MSG, "");
				} else {
					String msg = result.containsKey(MSG) ? result.get(MSG).toString() : "";
					resultMap.put(SUCCESS, 1);
					resultMap.put(MSG, msg);
				}
			} else {
				resultMap.put(SUCCESS, 1);
				resultMap.put(MSG, "");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put(SUCCESS, 0);
			resultMap.put(MSG, e.getMessage());
		}

	}

	/**
	 * 产生6位不重复的数字验证码
	 */
	public String generateRandomSix() {
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

}
