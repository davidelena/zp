package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.MyApplicantDTO;

/**
 * 我的申请人DTO
 * 
 * @author David.dai
 * 
 */
public interface MyApplicantDAO {
	
	List<MyApplicantDTO> queryMyApplicantInfos(Map<String, Object> queryMap);

	int queryMyApplicantInfosCount(Map<String, Object> queryMap);
}
