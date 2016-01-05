package com.dophin.dao;

import java.util.List;

import com.dophin.dto.ResumeOpusInfoDTO;

/**
 * 简历-作品
 * @author dailiwei
 *
 */
public interface ResumeOpusInfoDAO {
	
	int insertResumeOpusInfo(ResumeOpusInfoDTO resumeOpusInfoDTO);

	int updateResumeOpusInfo(ResumeOpusInfoDTO resumeOpusInfoDTO);

	int deleteResumeOpusInfo(int id);

	ResumeOpusInfoDTO queryResumeOpusInfo(int id);

	List<ResumeOpusInfoDTO> queryResumeOpusInfos(int resumeId);
	
}
