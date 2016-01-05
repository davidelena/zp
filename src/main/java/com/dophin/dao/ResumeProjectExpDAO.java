package com.dophin.dao;

import java.util.List;

import com.dophin.dto.ResumeProjectExpDTO;

/**
 * 简历-项目经验
 * @author David.dai
 *
 */
public interface ResumeProjectExpDAO
{
	int insertResumeProjectExp(ResumeProjectExpDTO resumeProjectExpDTO);

	int updateResumeProjectExp(ResumeProjectExpDTO resumeProjectExpDTO);

	int deleteResumeProjectExp(int id);

	ResumeProjectExpDTO queryResumeProjectExp(int id);

	List<ResumeProjectExpDTO> queryResumeProjectExps(int resumeId);
}
