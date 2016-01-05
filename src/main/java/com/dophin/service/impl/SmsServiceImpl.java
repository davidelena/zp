package com.dophin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.SmsDAO;
import com.dophin.dto.SmsCodeDTO;
import com.dophin.dto.SmsLogDTO;
import com.dophin.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private SmsDAO smsDAO;

	@Override
	public int insertSmsCode(SmsCodeDTO smsCodeDTO) {
		smsDAO.insertSmsCode(smsCodeDTO);
		return smsCodeDTO.getId();
	}

	@Override
	public int updateSmsCode(SmsCodeDTO smsCodeDTO) {
		return smsDAO.updateSmsCode(smsCodeDTO);
	}

	@Override
	public int deleteSmsCode(Map<String, Object> queryMap) {
		return smsDAO.deleteSmsCode(queryMap);
	}
	
	@Override
	public int deleteSmsCode(String mobile) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("mobile", mobile);
		return smsDAO.deleteSmsCode(queryMap);
	}

	@Override
	public int deleteSmsCode(int id) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("id", id);
		return smsDAO.deleteSmsCode(queryMap);
	}

	@Override
	public SmsCodeDTO querySmsCode(Map<String, Object> queryMap) {
		return smsDAO.querySmsCode(queryMap);
	}

	@Override
	public SmsCodeDTO querySmsCode(String mobile) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("mobile", mobile);
		return querySmsCode(queryMap);
	}

	@Override
	public boolean isSmsCodeExists(Map<String, Object> queryMap) {
		int count = smsDAO.isSmsCodeExists(queryMap);
		return count > 0;
	}

	@Override
	public int insertSmsLog(SmsLogDTO smsLogDTO) {
		smsDAO.insertSmsLog(smsLogDTO);
		return smsLogDTO.getId();
	}

	@Override
	public int updateSmsLog(SmsLogDTO smsLogDTO) {
		return smsDAO.updateSmsLog(smsLogDTO);
	}

	@Override
	public int deleteSmsLog(int id) {
		return smsDAO.deleteSmsLog(id);
	}

	@Override
	public SmsLogDTO querySmsLog(Map<String, Object> queryMap) {
		return smsDAO.querySmsLog(queryMap);
	}

	@Override
	public List<SmsLogDTO> querySmsLogs() {
		return smsDAO.querySmsLogs();
	}

}
