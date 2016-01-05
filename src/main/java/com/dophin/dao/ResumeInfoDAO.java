package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.ResumeInfoDTO;

/**
 * 简历-基本信息
 * 
 * @author dailiwei
 * 
 */
public interface ResumeInfoDAO {

	int insertResumeInfo(ResumeInfoDTO resumeInfoDTO);

	int updateResumeInfo(ResumeInfoDTO resumeInfoDTO);

	int deleteResumeInfo(int id);

	ResumeInfoDTO queryResumeInfo(int id);

	List<ResumeInfoDTO> queryResumeInfos(Map<String, Object> queryMap);

}
