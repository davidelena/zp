package com.dophin.dao;

import java.util.List;

import com.dophin.dto.ResumePrizeInfoDTO;

public interface ResumePrizeInfoDAO {

	int insertResumePrizeInfo(ResumePrizeInfoDTO resumePrizeInfoDTO);

	int updateResumePrizeInfo(ResumePrizeInfoDTO resumePrizeInfoDTO);

	int deleteResumePrizeInfo(int id);

	ResumePrizeInfoDTO queryResumePrizeInfo(int id);

	List<ResumePrizeInfoDTO> queryResumePrizeInfos(int resumeId);
}
