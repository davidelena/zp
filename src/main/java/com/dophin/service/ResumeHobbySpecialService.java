package com.dophin.service;

import java.util.List;

import com.dophin.dto.ResumeHobbySpecialDTO;

/**
 * 简历-爱好特长
 * @author David.dai
 *
 */
public interface ResumeHobbySpecialService
{
	int insertResumeHobbySpecial(ResumeHobbySpecialDTO resumeHobbySpecialDTO);

	int updateResumeHobbySpecial(ResumeHobbySpecialDTO resumeHobbySpecialDTO);

	int deleteResumeHobbySpecial(int id);

	ResumeHobbySpecialDTO queryResumeHobbySpecial(int id);

	List<ResumeHobbySpecialDTO> queryResumeHobbySpecials(int resumeId);
	
	List<ResumeHobbySpecialDTO> queryResumeHobbySpecials(int resumeId, boolean isFillResumeInfo);
}
