package com.dophin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.CertificateDTO;
import com.dophin.dto.ComputerSkillDTO;
import com.dophin.dto.EnLangSkillDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.OtherLangSkillDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeSpecialtySkillDTO;
import com.dophin.utils.VelocityUtils;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestVelocity {

	@Autowired
	private ResumeInfoService resumeInfoService;

	private ResumeInfoDTO getRecruitInfoDTO(int id) {
		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(id);
		return resumeInfoDTO;
	}

	@Test
	public void test() {
		System.err.println(getRecruitInfoDTO(1));
	}

	private void initVmData(VelocityContext velocityContext, ResumeInfoDTO resumeInfoDTO) {
		if (resumeInfoDTO != null) {
			MemberDTO memberDTO = resumeInfoDTO.getMemberDTO();
			ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeInfoDTO.getResumeSpecialtySkillDTO();

			velocityContext.put("resumeName", memberDTO == null ? "" : memberDTO.getName());
			velocityContext.put("university", memberDTO == null ? "" : memberDTO.getUniversityDTO().getName());
			velocityContext.put("major", resumeInfoDTO.getMajor());
			velocityContext.put("diploma", resumeInfoDTO.getDiplomaDesc());
			velocityContext.put("graduationYear", resumeInfoDTO.getGraduationYear());
			velocityContext.put("mobile", resumeInfoDTO.getMemberDTO().getMobile());
			velocityContext.put("email", resumeInfoDTO.getMemberDTO().getEmail());
			velocityContext.put("resumeEducationDTOs", resumeInfoDTO.getResumeEducationExpDTOs());
			velocityContext.put("resumeWorkExpDTOs", resumeInfoDTO.getResumeWorkExpDTOs());
			List<EnLangSkillDTO> enLangSkillDTOs = resumeSpecialtySkillDTO == null ? new ArrayList<EnLangSkillDTO>()
					: resumeSpecialtySkillDTO.getEnLangSkillDTOs();
			velocityContext.put("resumeSpecialEnSkillDTOs", enLangSkillDTOs);
			List<OtherLangSkillDTO> otherLangSkillDTOs = resumeSpecialtySkillDTO == null ? new ArrayList<OtherLangSkillDTO>()
					: resumeSpecialtySkillDTO.getOtherLangSkillDTOs();
			velocityContext.put("resumeSpecialOtherSkillDTOs", otherLangSkillDTOs);
			List<ComputerSkillDTO> computerSkillDTOs = resumeSpecialtySkillDTO == null ? new ArrayList<ComputerSkillDTO>()
					: resumeSpecialtySkillDTO.getComputerSkillDTOs();
			velocityContext.put("resumeSpecialComputerSkillDTOs", computerSkillDTOs);
			List<CertificateDTO> certSkillDTOs = resumeSpecialtySkillDTO == null ? new ArrayList<CertificateDTO>()
					: resumeSpecialtySkillDTO.getCertificateDTOs();
			velocityContext.put("resumeSpecialCertificateDTOs", certSkillDTOs);
			velocityContext.put("resumeActivityExpDTOs", resumeInfoDTO.getResumeActivityExpDTOs());
			velocityContext.put("resumePrizeInfoDTOs", resumeInfoDTO.getResumePrizeInfoDTOs());
			velocityContext.put("resumeProjectExpDTOs", resumeInfoDTO.getResumeProjectExpDTOs());
			velocityContext.put("resumeHobbySpecialDTOs", resumeInfoDTO.getResumeHobbySpecialDTOs());
			velocityContext.put("resumeSocialNetDTOs", resumeInfoDTO.getResumeSocialNetDTOs());
			velocityContext.put("resumeCustomDTOs", resumeInfoDTO.getResumeCustomDTOs());
		}
	}

	@Test
	public void testVelocity() {
		ResumeInfoDTO resumeInfoDTO = getRecruitInfoDTO(1);
		String srcPath = "C:/Users/Administrator/Desktop/" + resumeInfoDTO.getResumeName() + ".doc";
		String vmpath = "fp_template.vm";
		VelocityContext velocityContext = new VelocityContext();
		initVmData(velocityContext, resumeInfoDTO);
		try {
			VelocityUtils.createDoc(velocityContext, "E:" + File.separator, vmpath, srcPath);
			System.err.println("velocity template test success!");

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
