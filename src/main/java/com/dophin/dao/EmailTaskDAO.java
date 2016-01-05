package com.dophin.dao;

import java.util.Map;

import com.dophin.dto.EmailTaskDTO;

public interface EmailTaskDAO {
	
	int insertEmailTaskInfo(EmailTaskDTO emailTaskDTO);

	int updateEmailTaskInfo(EmailTaskDTO emailTaskDTO);

	int deleteEmailTaskInfo(int id);

	EmailTaskDTO queryEmailTaskInfo(Map<String, Object> queryMap);
}
