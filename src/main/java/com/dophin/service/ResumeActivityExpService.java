package com.dophin.service;

import java.util.List;

import com.dophin.dto.ResumeActivityExpDTO;

/**
 * 简历-校园活动经历
 * @author dailiwei
 *
 */
public interface ResumeActivityExpService {

	int insertResumeActivityExp(ResumeActivityExpDTO resumeActivityExpDTO);

	int updateResumeActivityExp(ResumeActivityExpDTO resumeActivityExpDTO);

	int deleteResumeActivityExp(int id);

	ResumeActivityExpDTO queryResumeActivityExp(int id);

	List<ResumeActivityExpDTO> queryResumeActivityExps(int resumeId);
	
	List<ResumeActivityExpDTO> queryResumeActivityExps(int resumeId, boolean isFillResumeInfo);
}
