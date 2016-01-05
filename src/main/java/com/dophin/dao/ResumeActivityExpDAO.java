package com.dophin.dao;

import java.util.List;

import com.dophin.dto.ResumeActivityExpDTO;

/**
 * 简历-校园活动经历
 * @author dailiwei
 *
 */
public interface ResumeActivityExpDAO {
	
	int insertResumeActivityExp(ResumeActivityExpDTO resumeActivityExpDTO);

	int updateResumeActivityExp(ResumeActivityExpDTO resumeActivityExpDTO);

	int deleteResumeActivityExp(int id);

	ResumeActivityExpDTO queryResumeActivityExp(int id);

	List<ResumeActivityExpDTO> queryResumeActivityExps(int resumeId);
	
}
