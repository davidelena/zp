package com.dophin.service;

import java.util.List;
import java.util.Map;

import com.dophin.dto.SmsCodeDTO;
import com.dophin.dto.SmsLogDTO;

public interface SmsService {
	
	int insertSmsCode(SmsCodeDTO smsCodeDTO);
	
	int updateSmsCode(SmsCodeDTO smsCodeDTO);
	
	int deleteSmsCode(int id);
	
	int deleteSmsCode(String mobile);
	
	int deleteSmsCode(Map<String, Object> queryMap);
	
	SmsCodeDTO querySmsCode(Map<String, Object> queryMap);
	
	SmsCodeDTO querySmsCode(String mobile);
	
	boolean isSmsCodeExists(Map<String, Object> queryMap);
	
	
	int insertSmsLog(SmsLogDTO smsLogDTO);
	
	int updateSmsLog(SmsLogDTO smsLogDTO);
	
	int deleteSmsLog(int id);
	
	SmsLogDTO querySmsLog(Map<String, Object> queryMap);

	List<SmsLogDTO> querySmsLogs();
	
}
