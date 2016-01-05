package com.dophin.service;

import com.dophin.dto.ResumeSpecialtySkillDTO;

/**
 * 简历-专业技能
 * @author dailiwei
 *
 */
public interface ResumeSpecialtySkillService {

	int insertResumeSpecialtySkill(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO);

	int updateResumeSpecialtySkill(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO);

	int deleteResumeSpecialtySkill(int id);

	ResumeSpecialtySkillDTO queryResumeSpecialtySkill(int id);

	ResumeSpecialtySkillDTO queryResumeSpecialtySkillByResumeId(int resumeId);
	
	ResumeSpecialtySkillDTO queryResumeSpecialtySkillByResumeId(int resumeId, boolean isFillResumeInfo);
	
}
