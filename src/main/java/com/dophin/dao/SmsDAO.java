package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.SmsCodeDTO;
import com.dophin.dto.SmsLogDTO;

public interface SmsDAO {
	
	/*
	 * 短信验证码
	 */
	int insertSmsCode(SmsCodeDTO smsCodeDTO);
	
	int updateSmsCode(SmsCodeDTO smsCodeDTO);
	
	int deleteSmsCode(Map<String, Object> queryMap);
	
	SmsCodeDTO querySmsCode(Map<String, Object> queryMap);
	
	int isSmsCodeExists(Map<String, Object> queryMap);
	
	/*
	 * 短信验证码日志
	 */
	int insertSmsLog(SmsLogDTO smsLogDTO);
	
	int updateSmsLog(SmsLogDTO smsLogDTO);
	
	int deleteSmsLog(int id);
	
	SmsLogDTO querySmsLog(Map<String, Object> queryMap);

	List<SmsLogDTO> querySmsLogs();
}
