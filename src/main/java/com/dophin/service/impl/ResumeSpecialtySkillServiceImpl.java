package com.dophin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeSpecialtySkillDAO;
import com.dophin.dto.CertificateDTO;
import com.dophin.dto.ComputerSkillDTO;
import com.dophin.dto.EnLangSkillDTO;
import com.dophin.dto.OtherLangSkillDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeSpecialtySkillDTO;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumeSpecialtySkillService;

@Service
public class ResumeSpecialtySkillServiceImpl extends BaseService implements ResumeSpecialtySkillService {

	private static final String ES_RESUME_SPECIALTY_SKILL = "resumespecialtyskill";

	@Autowired
	private ResumeSpecialtySkillDAO resumeSpecialtySKillDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeSpecialtySkill(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO) {
		resumeSpecialtySKillDAO.insertResumeSpecialtySkill(resumeSpecialtySkillDTO);
		try {
			ResumeSpecialtySkillDTO insertResumeSpecialtySkillDTO = queryResumeSpecialtySkill(resumeSpecialtySkillDTO.getId());
			String result = mapper.writeValueAsString(insertResumeSpecialtySkillDTO);
			doIndexAction(result, insertResumeSpecialtySkillDTO.getId(), ES_RESUME_SPECIALTY_SKILL);

			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeSpecialtySkillDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeSpecialtySkillDTO.getId();
	}

	@Override
	public int updateResumeSpecialtySkill(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO) {
		int count = resumeSpecialtySKillDAO.updateResumeSpecialtySkill(resumeSpecialtySkillDTO);
		try {
			ResumeSpecialtySkillDTO updatedResumeSpecialtySkillDTO = queryResumeSpecialtySkill(resumeSpecialtySkillDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeSpecialtySkillDTO);
			doUpdateAction(result, updatedResumeSpecialtySkillDTO.getId(), ES_RESUME_SPECIALTY_SKILL);

			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeSpecialtySkillDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeSpecialtySkill(int id) {
		int resumeId = resumeSpecialtySKillDAO.queryResumeSpecialtySkill(id).getResumeId();
		int count = resumeSpecialtySKillDAO.deleteResumeSpecialtySkill(id);
		try {
			doDeleteAction(id, ES_RESUME_SPECIALTY_SKILL);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	private void fillSpecialtySkillInfo(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO) {

		// 填充英语技能
		List<EnLangSkillDTO> enLangSkillDTOs = new ArrayList<>();
		String[] englishSkills = StringUtils.split(resumeSpecialtySkillDTO.getEnLangSkill(), ",");
		int name = 0;
		String score = "";
		for (String englishSkill : englishSkills) {
			String[] info = StringUtils.split(englishSkill, "-");
			name = info.length > 0 ? NumberUtils.toInt(info[0], 0) : 0;
			score = info.length > 1 ? info[1] : "";
			enLangSkillDTOs.add(new EnLangSkillDTO(name, score));
		}
		resumeSpecialtySkillDTO.setEnLangSkillDTOs(enLangSkillDTOs);

		// 填充其他语言技能
		List<OtherLangSkillDTO> otherLangSkillDTOs = new ArrayList<>();
		String[] otherSkills = StringUtils.split(resumeSpecialtySkillDTO.getOtherLangSkill(), ",");
		int otherName = 0;
		String level = "";
		for (String otherSkill : otherSkills) {
			String[] info = StringUtils.split(otherSkill, "-");
			otherName = info.length > 0 ? NumberUtils.toInt(info[0], 0) : 0;
			level = info.length > 1 ? info[1] : "";
			otherLangSkillDTOs.add(new OtherLangSkillDTO(otherName, level));
		}
		resumeSpecialtySkillDTO.setOtherLangSkillDTOs(otherLangSkillDTOs);

		// 填充计算机技能
		List<ComputerSkillDTO> computerSkillDTOs = new ArrayList<>();
		String[] computerSkills = StringUtils.split(resumeSpecialtySkillDTO.getComputerSkill(), ",");
		int cpSkill = 0;
		int cpSkillLevel = 0;
		for (String computerSkill : computerSkills) {
			String[] info = StringUtils.split(computerSkill, "-");
			cpSkill = info.length > 0 ? NumberUtils.toInt(info[0], 0) : 0;
			cpSkillLevel = info.length > 1 ? NumberUtils.toInt(info[1], 0) : 0;
			computerSkillDTOs.add(new ComputerSkillDTO(cpSkill, cpSkillLevel));
		}
		resumeSpecialtySkillDTO.setComputerSkillDTOs(computerSkillDTOs);

		// 填充证书
		List<CertificateDTO> certificateDTOs = new ArrayList<>();
		String[] certificates = StringUtils.split(resumeSpecialtySkillDTO.getCertificate(), ",");
		for (String certificate : certificates) {
			certificateDTOs.add(new CertificateDTO(certificate));
		}
		resumeSpecialtySkillDTO.setCertificateDTOs(certificateDTOs);

	}

	@Override
	public ResumeSpecialtySkillDTO queryResumeSpecialtySkill(int id) {
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySKillDAO.queryResumeSpecialtySkill(id);
		if (resumeSpecialtySkillDTO != null) {
			fillSpecialtySkillInfo(resumeSpecialtySkillDTO);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeSpecialtySkillDTO.getResumeId());
			resumeSpecialtySkillDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeSpecialtySkillDTO;
	}

	@Override
	public ResumeSpecialtySkillDTO queryResumeSpecialtySkillByResumeId(int resumeId) {
		return queryResumeSpecialtySkillByResumeId(resumeId, false);
	}

	@Override
	public ResumeSpecialtySkillDTO queryResumeSpecialtySkillByResumeId(int resumeId, boolean isFillResumeInfo) {
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySKillDAO.queryResumeSpecialtySkillByResumeId(resumeId);
		if (resumeSpecialtySkillDTO != null) {
			fillSpecialtySkillInfo(resumeSpecialtySkillDTO);
		}
		ResumeInfoDTO resumeInfoDTO = null;

		if (isFillResumeInfo) {
			if (resumeSpecialtySkillDTO != null) {
				resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeSpecialtySkillDTO.getResumeId());
				resumeSpecialtySkillDTO.setResumeInfoDTO(resumeInfoDTO);
			}
		}
		return resumeSpecialtySkillDTO;
	}
}
