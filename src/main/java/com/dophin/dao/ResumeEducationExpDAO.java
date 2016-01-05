package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.ResumeEducationExpDTO;

/**
 * 简历-教育经历DAO
 * @author dailiwei
 *
 */
public interface ResumeEducationExpDAO {
	
	int insertResumeEducationExp(ResumeEducationExpDTO resumeEducationExpDTO);

	int updateResumeEducationExp(ResumeEducationExpDTO resumeEducationExpDTO);

	int deleteResumeEducationExp(int id);

	ResumeEducationExpDTO queryResumeEducationExp(int id);

	List<ResumeEducationExpDTO> queryResumeEducationExps(int resumeId);
	
	List<Integer> queryResumeIdByMajorType(Map<String, Object> queryMap);
	
}
