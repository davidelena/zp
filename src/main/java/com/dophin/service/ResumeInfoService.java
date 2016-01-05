package com.dophin.service;

import java.util.List;
import java.util.Map;

import com.dophin.dto.ResumeInfoDTO;

public interface ResumeInfoService {

	int insertResumeInfo(ResumeInfoDTO resumeInfoDTO);

	int updateResumeInfo(ResumeInfoDTO resumeInfoDTO);

	int deleteResumeInfo(int id);
	
	ResumeInfoDTO queryResumeInfo(int id);
	
	List<ResumeInfoDTO> queryResumeInfos(String memberId);
	
	List<ResumeInfoDTO> queryResumeInfosByIds(String ids);
	
	List<ResumeInfoDTO> queryResumeInfos(Map<String, Object> queryMap);
	
	List<ResumeInfoDTO> queryResumeInfos();
}
