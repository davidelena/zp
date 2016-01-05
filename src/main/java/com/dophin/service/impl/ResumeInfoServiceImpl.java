package com.dophin.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeInfoDAO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.ResumeActivityExpDTO;
import com.dophin.dto.ResumeCustomDTO;
import com.dophin.dto.ResumeEducationExpDTO;
import com.dophin.dto.ResumeHobbySpecialDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeOpusInfoDTO;
import com.dophin.dto.ResumePrizeInfoDTO;
import com.dophin.dto.ResumeProjectExpDTO;
import com.dophin.dto.ResumeSocialNetDTO;
import com.dophin.dto.ResumeSpecialtySkillDTO;
import com.dophin.dto.ResumeWorkExpDTO;
import com.dophin.service.MemberService;
import com.dophin.service.ResumeActivityExpService;
import com.dophin.service.ResumeCustomService;
import com.dophin.service.ResumeEducationExpService;
import com.dophin.service.ResumeHobbySpecialService;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumeOpusInfoService;
import com.dophin.service.ResumePrizeInfoService;
import com.dophin.service.ResumeProjectExpService;
import com.dophin.service.ResumeSocialNetService;
import com.dophin.service.ResumeSpecialtySkillService;
import com.dophin.service.ResumeWorkExpService;

@Service
public class ResumeInfoServiceImpl extends BaseService implements ResumeInfoService {

	@Autowired
	private ResumeInfoDAO resumeInfoDAO;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ResumeEducationExpService resumeEducationExpService;

	@Autowired
	private ResumeWorkExpService resumeWorkExpService;

	@Autowired
	private ResumeSpecialtySkillService resumeSpecialtySkillService;

	@Autowired
	private ResumeActivityExpService resumeActivityExpService;

	@Autowired
	private ResumePrizeInfoService resumePrizeInfoService;

	@Autowired
	private ResumeProjectExpService resumeProjectExpService;

	@Autowired
	private ResumeHobbySpecialService resumeHobbySpecialService;

	@Autowired
	private ResumeSocialNetService resumeSocialNetService;

	@Autowired
	private ResumeOpusInfoService resumeOpusInfoService;

	@Autowired
	private ResumeCustomService resumeCustomService;

	@Override
	public int insertResumeInfo(ResumeInfoDTO resumeInfoDTO) {
		Calendar c = Calendar.getInstance();
		c.setTime(resumeInfoDTO.getGraduationTime());
		int year = c.get(Calendar.YEAR);
		resumeInfoDTO.setGraduationYear(year);
		resumeInfoDAO.insertResumeInfo(resumeInfoDTO);
		try {
			ResumeInfoDTO insertResumeInfoDTO = queryResumeInfo(resumeInfoDTO.getId());
			String result = mapper.writeValueAsString(insertResumeInfoDTO);
			doIndexAction(result, insertResumeInfoDTO.getId(), ES_RESUME_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeInfoDTO.getId();
	}

	@Override
	public int updateResumeInfo(ResumeInfoDTO resumeInfoDTO) {
		Calendar c = Calendar.getInstance();
		c.setTime(resumeInfoDTO.getGraduationTime());
		int year = c.get(Calendar.YEAR);
		resumeInfoDTO.setGraduationYear(year);
		int count = resumeInfoDAO.updateResumeInfo(resumeInfoDTO);
		try {
			ResumeInfoDTO updatedResumeInfoDTO = queryResumeInfo(resumeInfoDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeInfoDTO);
			doUpdateAction(result, updatedResumeInfoDTO.getId(), ES_RESUME_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeInfo(int id) {
		int count = resumeInfoDAO.deleteResumeInfo(id);
		try {
			doDeleteAction(id, ES_RESUME_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	private void fillResumeInfoData(ResumeInfoDTO resumeInfoDTO) {
		if (resumeInfoDTO != null) {
			MemberDTO memberDTO = memberService.queryMemberInfo(resumeInfoDTO.getMemberId());
			resumeInfoDTO.setMemberDTO(memberDTO);

			List<ResumeEducationExpDTO> resumeEducationExpDTOs = resumeEducationExpService.queryResumeEducationExps(resumeInfoDTO.getId());
			for (ResumeEducationExpDTO resumeEducationExpDTO : resumeEducationExpDTOs) {
				resumeEducationExpDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeEducationExpDTOs(resumeEducationExpDTOs);

			List<ResumeWorkExpDTO> resumeWorkExpDTOs = resumeWorkExpService.queryResumeWorkExps(resumeInfoDTO.getId());
			for (ResumeWorkExpDTO resumeWorkExpDTO : resumeWorkExpDTOs) {
				resumeWorkExpDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeWorkExpDTOs(resumeWorkExpDTOs);

			ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkillByResumeId(resumeInfoDTO.getId());
			if (resumeSpecialtySkillDTO != null) {
				resumeInfoDTO.setResumeSpecialtySkillDTO(resumeSpecialtySkillDTO);
			}

			List<ResumeActivityExpDTO> resumeActivityExpDTOs = resumeActivityExpService.queryResumeActivityExps(resumeInfoDTO.getId());
			for (ResumeActivityExpDTO resumeActivityExpDTO : resumeActivityExpDTOs) {
				resumeActivityExpDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeActivityExpDTOs(resumeActivityExpDTOs);

			List<ResumePrizeInfoDTO> resumePrizeInfoDTOs = resumePrizeInfoService.queryResumePrizeInfos(resumeInfoDTO.getId());
			for (ResumePrizeInfoDTO resumePrizeInfoDTO : resumePrizeInfoDTOs) {
				resumePrizeInfoDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumePrizeInfoDTOs(resumePrizeInfoDTOs);

			List<ResumeProjectExpDTO> resumeProjectExpDTOs = resumeProjectExpService.queryResumeProjectExps(resumeInfoDTO.getId());
			for (ResumeProjectExpDTO resumeProjectExpDTO : resumeProjectExpDTOs) {
				resumeProjectExpDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeProjectExpDTOs(resumeProjectExpDTOs);

			List<ResumeHobbySpecialDTO> resumeHobbySpecialDTOs = resumeHobbySpecialService.queryResumeHobbySpecials(resumeInfoDTO.getId());
			for (ResumeHobbySpecialDTO resumeHobbySpecialDTO : resumeHobbySpecialDTOs) {
				resumeHobbySpecialDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeHobbySpecialDTOs(resumeHobbySpecialDTOs);

			List<ResumeSocialNetDTO> resumeSocialNetDTOs = resumeSocialNetService.queryResumeSocialNets(resumeInfoDTO.getId());
			for (ResumeSocialNetDTO resumeSocialNetDTO : resumeSocialNetDTOs) {
				resumeSocialNetDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeSocialNetDTOs(resumeSocialNetDTOs);

			List<ResumeOpusInfoDTO> resumeOpusInfoDTOs = resumeOpusInfoService.queryResumeOpusInfos(resumeInfoDTO.getId());
			for (ResumeOpusInfoDTO resumeOpusInfoDTO : resumeOpusInfoDTOs) {
				resumeOpusInfoDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeOpusInfoDTOs(resumeOpusInfoDTOs);

			List<ResumeCustomDTO> resumeCustomDTOs = resumeCustomService.queryResumeCustoms(resumeInfoDTO.getId());
			for (ResumeCustomDTO resumeCustomDTO : resumeCustomDTOs) {
				resumeCustomDTO.setResumeInfoDTO(null);
			}
			resumeInfoDTO.setResumeCustomDTOs(resumeCustomDTOs);
		}
	}

	@Override
	public ResumeInfoDTO queryResumeInfo(int id) {
		ResumeInfoDTO resumeInfoDTO = resumeInfoDAO.queryResumeInfo(id);
		fillResumeInfoData(resumeInfoDTO);
		return resumeInfoDTO;
	}

	@Override
	public List<ResumeInfoDTO> queryResumeInfos(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return queryResumeInfos(queryMap);
	}

	@Override
	public List<ResumeInfoDTO> queryResumeInfos() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		return queryResumeInfos(queryMap);
	}

	@Override
	public List<ResumeInfoDTO> queryResumeInfos(Map<String, Object> queryMap) {
		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoDAO.queryResumeInfos(queryMap);
		for (ResumeInfoDTO resumeInfoDTO : resumeInfoDTOs) {
			fillResumeInfoData(resumeInfoDTO);
		}
		return resumeInfoDTOs;
	}

	@Override
	public List<ResumeInfoDTO> queryResumeInfosByIds(String ids) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("ids", ids);
		return queryResumeInfos(queryMap);
	}

}
