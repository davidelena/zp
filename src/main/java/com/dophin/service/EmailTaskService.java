package com.dophin.service;

import java.util.Map;

import com.dophin.dto.EmailTaskDTO;

public interface EmailTaskService {
	
	int insertEmailTaskInfo(EmailTaskDTO emailTaskDTO);

	int updateEmailTaskInfo(EmailTaskDTO emailTaskDTO);

	int deleteEmailTaskInfo(int id);

	EmailTaskDTO queryEmailTaskInfo(Map<String, Object> queryMap);
	
	EmailTaskDTO queryEmailTaskInfo(int id);
}
