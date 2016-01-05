package com.dophin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dophin.dto.EmailTaskDTO;
import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.enums.ComputerSkillEnum;
import com.dophin.enums.SmsTypeEnum;
import com.dophin.service.ConstantsService;
import com.dophin.service.EmailTaskService;
import com.dophin.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);
	protected static final String MOBILE = "mobile";
	protected static final String EMAIL = "email";
	protected static final String STATUS = "status";
	protected static final String MSG = "msg";
	protected static final String SUCCESS = "success";
	protected static final String MESSAGE = "message";
	protected static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private EmailTaskService emailTaskService;

	@Autowired
	protected ConstantsService constantsService;

	/**
	 * 发送验证码短信
	 * 
	 * @param mobile
	 *            手机
	 * @param code
	 *            随机验证码
	 * @param resultMap
	 *            结果map
	 */
	protected void sendSmsCode(String mobile, String code, Map<String, Object> resultMap, SmsTypeEnum smsType) {

		try {
			Form form = new Form();
			form.param("appid", CommonUtils.SMS_APPID);
			form.param("to", mobile);
			String smsTypeCode = "";
			if (smsType == SmsTypeEnum.Register) {
				smsTypeCode = CommonUtils.SMS_REGISTER_CODE;
			} else if (smsType == SmsTypeEnum.ResetPassword) {
				smsTypeCode = CommonUtils.SMS_RESETPWD_CODE;
			} else if (smsType == SmsTypeEnum.Notification) {
				smsTypeCode = CommonUtils.SMS_RESETPWD_NOTIFIY_CODE;
			} else if (smsType == SmsTypeEnum.BindingMobile) {
				smsTypeCode = CommonUtils.SMS_BINDING_NOTIFIY_CODE;
			}

			form.param("project", smsTypeCode);
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
					resultMap.put(SUCCESS, true);
					resultMap.put(MSG, "");
				} else {
					String msg = result.containsKey(MSG) ? result.get(MSG).toString() : "";
					resultMap.put(SUCCESS, true);
					resultMap.put(MSG, msg);
				}
			} else {
				resultMap.put(SUCCESS, true);
				resultMap.put(MSG, "");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put(SUCCESS, false);
			resultMap.put(MSG, e.getMessage());
		}

	}

	/**
	 * 行业map
	 * 
	 * @return 行业map
	 */
	protected Map<String, List<IndustryDTO>> getIndustryMap() {
		Map<Integer, IndustryDTO> industryDataMap = new HashMap<Integer, IndustryDTO>();
		Map<Integer, List<IndustryDTO>> tempMap = new HashMap<>();
		Map<String, List<IndustryDTO>> resultMap = new HashMap<>();

		List<IndustryDTO> ls = constantsService.queryIndustries(null);
		for (IndustryDTO industryDTO : ls) {
			if (!industryDataMap.containsKey(industryDTO.getId())) {
				industryDataMap.put(industryDTO.getId(), industryDTO);
			}

			if (industryDTO.getParentId() == 0) {
				if (!tempMap.containsKey(industryDTO.getId())) {
					tempMap.put(industryDTO.getId(), new ArrayList<IndustryDTO>());
				}
			} else {
				if (!tempMap.containsKey(industryDTO.getParentId())) {
					tempMap.put(industryDTO.getParentId(), new ArrayList<IndustryDTO>());
				}
				tempMap.get(industryDTO.getParentId()).add(industryDTO);
			}
		}

		String key = "";
		for (Entry<Integer, List<IndustryDTO>> kv : tempMap.entrySet()) {
			key = industryDataMap.containsKey(kv.getKey()) ? industryDataMap.get(kv.getKey()).getName() : "";
			if (!resultMap.containsKey(key)) {
				resultMap.put(key, kv.getValue());
			}
		}

		return resultMap;
	}

	/**
	 * 获取最新map
	 * 
	 * @return
	 */
	protected Map<String, List<GeoAreaDTO>> getGeoAreaSearchMap() {
		Map<Integer, GeoAreaDTO> geoDataMap = new HashMap<Integer, GeoAreaDTO>();
		Map<Integer, List<GeoAreaDTO>> tempMap = new HashMap<>();
		Map<String, List<GeoAreaDTO>> resultMap = new HashMap<>();
		List<Integer> otherCitys = Arrays.asList(200, 2900, 3100, 3500);

		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(null);
		for (GeoAreaDTO geoAreaDTO : ls) {
			if (!geoDataMap.containsKey(geoAreaDTO.getId())) {
				geoDataMap.put(geoAreaDTO.getId(), geoAreaDTO);
			}

			if (geoAreaDTO.getParentId() == 0) {
				if (!tempMap.containsKey(geoAreaDTO.getId())) {
					tempMap.put(geoAreaDTO.getId(), new ArrayList<GeoAreaDTO>());
					if (geoAreaDTO.isHotCity() || otherCitys.contains(geoAreaDTO.getId())) {
						tempMap.get(geoAreaDTO.getId()).add(geoAreaDTO);
					}
				}
			} else {
				if (!tempMap.containsKey(geoAreaDTO.getParentId())) {
					tempMap.put(geoAreaDTO.getParentId(), new ArrayList<GeoAreaDTO>());
				}
				tempMap.get(geoAreaDTO.getParentId()).add(geoAreaDTO);
			}
		}

		String key = "";
		for (Entry<Integer, List<GeoAreaDTO>> kv : tempMap.entrySet()) {
			key = geoDataMap.containsKey(kv.getKey()) ? geoDataMap.get(kv.getKey()).getName() : "";
			if (!resultMap.containsKey(key)) {
				resultMap.put(key, kv.getValue());
			}
		}

		return resultMap;
	}

	/**
	 * 获取大学列表
	 * 
	 * @return
	 */
	protected Map<String, List<UniversityDTO>> getUniversityMap() {
		Map<String, List<UniversityDTO>> resultMap = new HashMap<String, List<UniversityDTO>>();
		List<UniversityDTO> ls = constantsService.queryUniversities();
		for (UniversityDTO item : ls) {
			if (!resultMap.containsKey(item.getGeo())) {
				resultMap.put(item.getGeo(), new ArrayList<UniversityDTO>());
			}

			resultMap.get(item.getGeo()).add(item);
		}

		return resultMap;
	}

	/**
	 * 获取大学geo列表
	 * 
	 * @return
	 */
	protected List<GeoAreaDTO> getUniversityGeoList() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 0);
		List<GeoAreaDTO> queryLs = constantsService.queryGeoAreas(queryMap);
		List<GeoAreaDTO> resultLs = new ArrayList<GeoAreaDTO>();
		for (GeoAreaDTO item : queryLs) {
			resultLs.add(item);
		}

		return resultLs;
	}

	/**
	 * 获取职位列别
	 * 
	 * @return
	 */
	protected Map<String, List<PositionTypeDTO>> getPositionTypeMap() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 0);
		queryMap.put("subParentId", 0);
		List<PositionTypeDTO> ls = constantsService.queryPositionTypes(queryMap);
		Map<Integer, String> parentMap = new HashMap<Integer, String>();
		Map<Integer, String> subParentMap = new HashMap<Integer, String>();

		for (PositionTypeDTO parentItem : ls) {
			if (!parentMap.containsKey(parentItem.getId())) {
				parentMap.put(parentItem.getId(), parentItem.getName());
			}
		}

		List<PositionTypeDTO> secondLs = constantsService.querySecondPositionTypes();
		for (PositionTypeDTO subParentItem : secondLs) {
			if (!parentMap.containsKey(subParentItem.getId())) {
				subParentMap.put(subParentItem.getId(), subParentItem.getName());
			}
		}

		Map<String, List<PositionTypeDTO>> resultMap = new TreeMap<String, List<PositionTypeDTO>>();
		List<PositionTypeDTO> thirdLs = constantsService.queryThirdPositionTypes();
		String parentItemName = "";
		String subParentItemName = "";
		String key = "";
		for (PositionTypeDTO item : thirdLs) {
			parentItemName = parentMap.containsKey(item.getParentId()) ? parentMap.get(item.getParentId()) : "";
			subParentItemName = subParentMap.containsKey(item.getSubParentId()) ? subParentMap.get(item
					.getSubParentId()) : "";
			key = String.format("%s-%s", parentItemName, subParentItemName);

			if (!resultMap.containsKey(key)) {
				resultMap.put(key, new ArrayList<PositionTypeDTO>());
			}

			resultMap.get(key).add(item);
		}

		return resultMap;
	}

	/**
	 * 获取热门城市列表
	 * 
	 * @return 热门城市列表
	 */
	protected List<GeoAreaDTO> getHotCities() {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("isHotCity", true);
		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(queryMap);
		return ls;
	}

	/**
	 * 获取港澳台和海外城市
	 * 
	 * @return
	 */
	protected List<GeoAreaDTO> getOtherCities() {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("cityIds", "200,2900,3100,3500"); // 香港，澳门，台湾，海外
		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(queryMap);
		return ls;
	}

	/**
	 * 城市map（不包含热门城市和直辖市）
	 * 
	 * @return 城市map
	 */
	protected Map<String, List<GeoAreaDTO>> getGeoAreaMap() {
		List<Integer> otherCitys = Arrays.asList(200, 2900, 3100, 3500);
		Map<String, List<GeoAreaDTO>> resultMap = new HashMap<>();
		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(0);
		for (GeoAreaDTO item : ls) {
			if (!resultMap.containsKey(item.getName())) {
				if (!item.isHotCity() && !otherCitys.contains(item.getId())) {
					resultMap.put(item.getName(), new ArrayList<GeoAreaDTO>());
				}
			}

			// 天津需要特殊添加
			if (item.getId() == 2800 && !item.isHotCity() && !otherCitys.contains(item.getId())) {
				resultMap.get(item.getName()).add(item);
			}

			List<GeoAreaDTO> childLs = constantsService.queryGeoAreas(item.getId());
			for (GeoAreaDTO geoAreaDTO : childLs) {

				if (!geoAreaDTO.isHotCity() && geoAreaDTO.getParentId() > 0) {
					resultMap.get(item.getName()).add(geoAreaDTO);
				}
			}
		}

		return resultMap;
	}

	/**
	 * 获取计算机技能下拉框
	 * 
	 * @return
	 */
	protected Map<String, List<ComputerSkillEnum>> getComputerSkillMap() {
		Map<String, List<ComputerSkillEnum>> resultMap = new HashMap<>();
		ComputerSkillEnum[] computerSkillEnums = ComputerSkillEnum.values();
		for (ComputerSkillEnum computerSkillEnum : computerSkillEnums) {
			if (!resultMap.containsKey(computerSkillEnum.getParent())) {
				resultMap.put(computerSkillEnum.getParent(), new ArrayList<ComputerSkillEnum>());
			}

			resultMap.get(computerSkillEnum.getParent()).add(computerSkillEnum);
		}

		return resultMap;
	}

	/**
	 * 发送邮件
	 * 
	 * @param toAddress
	 * @param subject
	 * @param contents
	 * @return
	 */
	protected boolean sendMail(String toAddress, String subject, String title, String contents) {
		Email email = new SimpleEmail();
		email.setHostName(CommonUtils.MAIL_SMTP_HOST);
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator(CommonUtils.MAIL_USER, CommonUtils.MAIL_PASSWORD));
		email.setSSLOnConnect(false);
		email.setCharset("UTF-8");
		try {
			email.setFrom(CommonUtils.MAIL_USER);
			email.setSubject(subject);
			email.setMsg(contents);
			email.addTo(toAddress);
			email.send();

			// 插入邮箱激活邮件记录
			EmailTaskDTO emailTaskDTO = new EmailTaskDTO();
			emailTaskDTO.setMailto(toAddress);
			emailTaskDTO.setTitle(title);
			emailTaskDTO.setContent(contents.toString());
			emailTaskService.insertEmailTaskInfo(emailTaskDTO);

			return true;

		} catch (EmailException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 发送Html格式的面试通知
	 * 
	 * @param toAddress
	 * @param subject
	 * @param title
	 * @param contents
	 * @return
	 */
	protected boolean sendHtmlMail(String toAddress, String subject, String title, String contents) {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(CommonUtils.MAIL_SMTP_HOST);
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator(CommonUtils.MAIL_USER, CommonUtils.MAIL_PASSWORD));
		email.setSSLOnConnect(false);
		email.setCharset("UTF-8");
		try {
			email.setFrom(CommonUtils.MAIL_USER);
			email.setSubject(subject);
			email.setMsg(contents);
			email.addTo(toAddress);
			email.send();

			// 插入邮箱激活邮件记录
			EmailTaskDTO emailTaskDTO = new EmailTaskDTO();
			emailTaskDTO.setMailto(toAddress);
			emailTaskDTO.setTitle(title);
			emailTaskDTO.setContent(contents.toString());
			emailTaskService.insertEmailTaskInfo(emailTaskDTO);

			return true;

		} catch (EmailException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	// 项目投票 start
	protected String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
