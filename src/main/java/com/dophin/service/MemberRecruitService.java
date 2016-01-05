package com.dophin.service;

import java.util.List;
import java.util.Map;

import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.RecruitResumeCountDTO;

/**
 * 
 * 学生-招聘-简历 关系表
 * 
 * @author dailiwei
 * 
 */
public interface MemberRecruitService {

	int insertMemberRecruit(MemberRecruitDTO memberRecruitDTO);

	int updateMemberRecruit(MemberRecruitDTO memberRecruitDTO);

	int deleteMemberRecruit(int id);
	
	int deleteMemberRecruitByCondition(Map<String, Object> queryMap);

	MemberRecruitDTO queryMemberRecruit(int id);
	
	MemberRecruitDTO queryMemberRecruit(int id, boolean isFillData);
	
	MemberRecruitDTO queryMemberRecruit(Map<String, Object> queryMap);
	
	MemberRecruitDTO queryMemberRecruit(Map<String, Object> queryMap, boolean isFillData);
	
	MemberRecruitDTO queryMemberRecruitByConditions(Map<String, Object> queryMap);
	
	List<MemberRecruitDTO> queryMemberRecruits(Map<String, Object> queryMap, boolean isFillData);
	
	List<MemberRecruitDTO> queryMemberRecruits(String memberId, boolean isFillData);
	
	List<MemberRecruitDTO> queryMemberRecruitsByRecruitId(int recruitId, boolean isFillData);
	
	List<RecruitResumeCountDTO> queryJobResumeApplyCount(Map<String, Object> queryMap);
	
	Map<Integer, RecruitResumeCountDTO> queryJobResumeApplyCountForMap(Integer recruitId);
	
	List<MemberRecruitDTO> queryMemberRecruitsForMyApply(Map<String, Object> queryMap);
	
	int queryMemberRecruitsForMyApplyCount(Map<String, Object> queryMap);
}
