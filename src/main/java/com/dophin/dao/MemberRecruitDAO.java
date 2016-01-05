package com.dophin.dao;

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
public interface MemberRecruitDAO {

	int insertMemberRecruit(MemberRecruitDTO memberRecruitDTO);

	int updateMemberRecruit(MemberRecruitDTO memberRecruitDTO);

	int deleteMemberRecruit(int id);
	
	int deleteMemberRecruitByCondition(Map<String, Object> queryMap);

	MemberRecruitDTO queryMemberRecruit(Map<String, Object> queryMap);
	
	MemberRecruitDTO queryMemberRecruitByConditions(Map<String, Object> queryMap);
	
	List<MemberRecruitDTO> queryMemberRecruits(Map<String, Object> queryMap);

	List<RecruitResumeCountDTO> queryJobResumeApplyCount(Map<String, Object> queryMap);
	
	List<MemberRecruitDTO> queryMemberRecruitsForMyApply(Map<String, Object> queryMap);
	
	int queryMemberRecruitsForMyApplyCount(Map<String, Object> queryMap);
}
