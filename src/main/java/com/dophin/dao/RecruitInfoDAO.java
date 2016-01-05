package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.RecruitInfoDTO;

/**
 * 招聘发布职位信息
 * @author David.dai
 *
 */
public interface RecruitInfoDAO
{
	int insertRecruitInfo(RecruitInfoDTO recruitInfoDTO);
	
	int updateRecruitInfo(RecruitInfoDTO recruitInfoDTO);
	
	int deleteRecruitInfo(int id);
	
	RecruitInfoDTO queryRecruitInfo(Map<String, Object> queryMap);
	
	List<RecruitInfoDTO> queryRecruitInfos(Map<String, Object> queryMap);
	
	int queryRecruitInfosCount(Map<String, Object> queryMap);
	
}
