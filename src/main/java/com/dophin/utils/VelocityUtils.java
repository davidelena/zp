package com.dophin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.dophin.dto.CertificateDTO;
import com.dophin.dto.ComputerSkillDTO;
import com.dophin.dto.EnLangSkillDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.OtherLangSkillDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeSpecialtySkillDTO;

public class VelocityUtils {

	public static final String VM_TEMPLATE = "fp_template.vm";

	public static void createDoc(VelocityContext vc, String templateLoadPath, String templetePath, String srcPath)
			throws Exception {

		Properties ps = new Properties();
		ps.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, templateLoadPath);// 这是模板所在路径
		VelocityEngine ve = new VelocityEngine();
		ve.init(ps);
		ve.init();
		Template template = ve.getTemplate(templetePath, "utf-8");
		File srcFile = new File(srcPath);// 输出路径
		FileOutputStream fos = new FileOutputStream(srcFile);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
		template.merge(vc, writer);
		writer.flush();
		writer.close();
		fos.close();

	}

	public static void initVmData(VelocityContext velocityContext, ResumeInfoDTO resumeInfoDTO) {
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
}
