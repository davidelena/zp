package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.ResumeWorkExpDTO;

/**
 * 简历-实习和工作经历
 * @author dailiwei
 *
 */
public interface ResumeWorkExpDAO {

	int insertResumeWorkExp(ResumeWorkExpDTO resumeWorkExpDTO);

	int updateResumeWorkExp(ResumeWorkExpDTO resumeWorkExpDTO);

	int deleteResumeWorkExp(int id);

	ResumeWorkExpDTO queryResumeWorkExp(int id);
	
	List<ResumeWorkExpDTO> queryResumeWorkExps(Map<String, Object> queryMap);
	
}
