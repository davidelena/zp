package com.dophin.dao;

import com.dophin.dto.ResumeSpecialtySkillDTO;

/**
 * 简历-专业技能
 * @author dailiwei
 *
 */
public interface ResumeSpecialtySkillDAO {
	
	int insertResumeSpecialtySkill(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO);

	int updateResumeSpecialtySkill(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO);

	int deleteResumeSpecialtySkill(int id);

	ResumeSpecialtySkillDTO queryResumeSpecialtySkill(int id);

	ResumeSpecialtySkillDTO queryResumeSpecialtySkillByResumeId(int resumeId);
	
}
