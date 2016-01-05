package com.dophin.service;

import java.util.List;

import com.dophin.dto.ResumePrizeInfoDTO;

public interface ResumePrizeInfoService {

	int insertResumePrizeInfo(ResumePrizeInfoDTO resumePrizeInfoDTO);

	int updateResumePrizeInfo(ResumePrizeInfoDTO resumePrizeInfoDTO);

	int deleteResumePrizeInfo(int id);

	ResumePrizeInfoDTO queryResumePrizeInfo(int id);

	List<ResumePrizeInfoDTO> queryResumePrizeInfos(int resumeId);
	
	List<ResumePrizeInfoDTO> queryResumePrizeInfos(int resumeId, boolean isFillResumeInfo);
	
}
