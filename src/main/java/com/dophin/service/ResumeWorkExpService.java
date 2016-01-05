package com.dophin.service;

import java.util.List;

import com.dophin.dto.ResumeWorkExpDTO;

/**
 * 简历-实习和工作经历
 * 
 * @author dailiwei
 * 
 */
public interface ResumeWorkExpService {

	int insertResumeWorkExp(ResumeWorkExpDTO resumeWorkExpDTO);

	int updateResumeWorkExp(ResumeWorkExpDTO resumeWorkExpDTO);

	int deleteResumeWorkExp(int id);

	ResumeWorkExpDTO queryResumeWorkExp(int id);

	List<ResumeWorkExpDTO> queryResumeWorkExps(int resumeId);
	
	List<ResumeWorkExpDTO> queryResumeWorkExps(int resumeId, boolean isFillResumeInfo);
	
}
