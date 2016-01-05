package com.dophin.service;

import java.util.List;

import com.dophin.dto.ResumeOpusInfoDTO;

/**
 * 作品简历
 * @author dailiwei
 *
 */
public interface ResumeOpusInfoService {

	int insertResumeOpusInfo(ResumeOpusInfoDTO resumeOpusInfoDTO);

	int updateResumeOpusInfo(ResumeOpusInfoDTO resumeOpusInfoDTO);

	int deleteResumeOpusInfo(int id);

	ResumeOpusInfoDTO queryResumeOpusInfo(int id);

	List<ResumeOpusInfoDTO> queryResumeOpusInfos(int resumeId);
	
	List<ResumeOpusInfoDTO> queryResumeOpusInfos(int resumeId, boolean isFillResumeInfo);
	
}
