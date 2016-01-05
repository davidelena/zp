package com.dophin.service;

import java.util.List;
import java.util.Map;

import com.dophin.dto.RecruitInfoDTO;

/**
 * 招聘服务
 * 
 * @author dailiwei
 * 
 */
public interface RecruitInfoService {

	int insertRecruitInfo(RecruitInfoDTO recruitInfoDTO);

	int updateRecruitInfo(RecruitInfoDTO recruitInfoDTO);

	int deleteRecruitInfo(int id);

	RecruitInfoDTO queryRecruitInfo(Map<String, Object> queryMap);

	RecruitInfoDTO queryRecruitInfo(int id);

	List<RecruitInfoDTO> queryRecruitInfos(Map<String, Object> queryMap);

	List<RecruitInfoDTO> queryRecruitInfos(Map<String, Object> queryMap, boolean isFillData);

	List<RecruitInfoDTO> queryRecruitInfoMemberId(String memberId);

	List<RecruitInfoDTO> queryRecruitInfoMemberId(String memberId, boolean isFillData);

	List<RecruitInfoDTO> queryRecruitInfoByCompanyId(int companyId);

	List<RecruitInfoDTO> queryOnlineRecruitInfoByCompanyId(int companyId);

	int queryRecruitInfosCount(Map<String, Object> queryMap);

}
