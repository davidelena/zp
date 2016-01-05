package com.dophin.dao;

import java.util.Map;

import com.dophin.dto.InformInterviewDTO;

/**
 * 面试通知DAO
 * @author David.dai
 *
 */
public interface InformInterviewDAO
{
	int insertInformInterviewInfo(InformInterviewDTO informInterviewDTO);
	
	int deleteInformInterviewInfo(Map<String, Object> queryMap);

	InformInterviewDTO queryInformInterviewInfo(Map<String, Object> queryMap);
}
