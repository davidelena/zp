package com.dophin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.EmailTaskDAO;
import com.dophin.dto.EmailTaskDTO;
import com.dophin.service.EmailTaskService;

@Service
public class EmailTaskServiceImpl implements EmailTaskService {

	@Autowired
	private EmailTaskDAO emailTaskDAO;

	@Override
	public int insertEmailTaskInfo(EmailTaskDTO emailTaskDTO) {
		emailTaskDAO.insertEmailTaskInfo(emailTaskDTO);
		return emailTaskDTO.getId();
	}

	@Override
	public int updateEmailTaskInfo(EmailTaskDTO emailTaskDTO) {
		return emailTaskDAO.updateEmailTaskInfo(emailTaskDTO);
	}

	@Override
	public int deleteEmailTaskInfo(int id) {
		return emailTaskDAO.deleteEmailTaskInfo(id);
	}

	@Override
	public EmailTaskDTO queryEmailTaskInfo(Map<String, Object> queryMap) {
		return emailTaskDAO.queryEmailTaskInfo(queryMap);
	}

	@Override
	public EmailTaskDTO queryEmailTaskInfo(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return queryEmailTaskInfo(queryMap);
	}

}
