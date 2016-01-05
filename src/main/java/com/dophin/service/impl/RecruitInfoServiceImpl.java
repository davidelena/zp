package com.dophin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ConstantsDAO;
import com.dophin.dao.RecruitInfoDAO;
import com.dophin.dto.CompanyInfoDTO;
import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.WorkCityDTO;
import com.dophin.enums.ComputerSkillEnum;
import com.dophin.enums.EnglishSkillEnum;
import com.dophin.enums.JobBenefitsEnum;
import com.dophin.enums.OtherLanguageSkillEnum;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.service.CompanyMemberService;
import com.dophin.service.MemberRecruitService;
import com.dophin.service.MemberService;
import com.dophin.service.RecruitInfoService;

@Service
public class RecruitInfoServiceImpl extends BaseService implements RecruitInfoService {

	@Autowired
	private RecruitInfoDAO recruitDAO;

	@Autowired
	private CompanyMemberService companyMemberService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ConstantsDAO constantsDAO;

	@Autowired
	private MemberRecruitService memberRecruitService;

	@Override
	public int insertRecruitInfo(RecruitInfoDTO recruitInfoDTO) {
		recruitDAO.insertRecruitInfo(recruitInfoDTO);
		try {
			RecruitInfoDTO insertRecruitInfoDTO = queryRecruitInfo(recruitInfoDTO.getId());
			String result = mapper.writeValueAsString(insertRecruitInfoDTO);
			doIndexAction(result, insertRecruitInfoDTO.getId(), ES_RECRUIT_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return recruitInfoDTO.getId();
	}

	@Override
	public int updateRecruitInfo(RecruitInfoDTO recruitInfoDTO) {
		int count = recruitDAO.updateRecruitInfo(recruitInfoDTO);
		try {
			RecruitInfoDTO updatedRecruitInfoDTO = queryRecruitInfo(recruitInfoDTO.getId());
			String result = mapper.writeValueAsString(updatedRecruitInfoDTO);
			doUpdateAction(result, updatedRecruitInfoDTO.getId(), ES_RECRUIT_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteRecruitInfo(int id) {
		int count = recruitDAO.deleteRecruitInfo(id);
		try {
			doDeleteAction(id, ES_RECRUIT_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	private void fillRecruitInfo(RecruitInfoDTO recruitInfoDTO, boolean isFillData) {
		if (recruitInfoDTO != null) {
			if (isFillData) {
				CompanyInfoDTO companyInfoDTO = companyMemberService.queryCompanyInfo(recruitInfoDTO.getCompanyId());
				if (companyInfoDTO != null) {
					recruitInfoDTO.setCompanyInfoDTO(companyInfoDTO);
				}

				MemberDTO memberDTO = memberService.queryMemberInfo(recruitInfoDTO.getMemberId());
				if (memberDTO != null) {
					recruitInfoDTO.setMemberDTO(memberDTO);
				}

				PositionTypeDTO positionTypeDTO = constantsDAO.queryPositionType(recruitInfoDTO.getPositionType());
				if (positionTypeDTO != null) {
					recruitInfoDTO.setPositionTypeDesc(positionTypeDTO.getName());
				}

				String workAddress = recruitInfoDTO.getWorkAddress();
				String[] tempArr = StringUtils.split(workAddress, "-");
				if (tempArr.length == 2) {
					int cityId = tempArr.length > 0 ? NumberUtils.toInt(tempArr[0], 0) : 0;
					String detailAddress = tempArr.length > 1 ? tempArr[1] : "";
					GeoAreaDTO geoAreaDTO = constantsDAO.queryGeoArea(cityId);
					WorkCityDTO workCityDTO = new WorkCityDTO(cityId, geoAreaDTO.getName(), detailAddress);
					recruitInfoDTO.setWorkCityDTO(workCityDTO);
				} else if (tempArr.length == 1) {
					int cityId = tempArr.length > 0 ? NumberUtils.toInt(tempArr[0], 0) : 0;
					GeoAreaDTO geoAreaDTO = constantsDAO.queryGeoArea(cityId);
					WorkCityDTO workCityDTO = new WorkCityDTO(cityId, geoAreaDTO.getName(), "");
					recruitInfoDTO.setWorkCityDTO(workCityDTO);
				}

				List<MemberRecruitDTO> memberRecruitDTOs = memberRecruitService.queryMemberRecruitsByRecruitId(
						recruitInfoDTO.getId(), false);
				if (memberRecruitDTOs != null) {
					recruitInfoDTO.setMemberRecruitDTOs(memberRecruitDTOs);
				}
			}

			String[] importantRemarks = StringUtils.split(recruitInfoDTO.getImportantRemark(), ",");
			List<JobBenefitsEnum> ls = new ArrayList<JobBenefitsEnum>();
			List<String> importantRemarkDescLs = new ArrayList<String>();
			JobBenefitsEnum jobBenefitsEnum = null;
			for (String item : importantRemarks) {
				if (item != null) {
					jobBenefitsEnum = JobBenefitsEnum.getJobBenefitsEnum(NumberUtils.toInt(item, 0));
					ls.add(jobBenefitsEnum);
					importantRemarkDescLs.add(jobBenefitsEnum.getDesc());
				}
			}

			recruitInfoDTO.setImportantRemarkDesc(StringUtils.join(importantRemarkDescLs, ","));
			recruitInfoDTO.setImportantRemarkList(ls);

			String[] skillKeys = StringUtils.split(recruitInfoDTO.getSkill(), ",");
			List<String> skillDescLs = new ArrayList<>();
			List<ComputerSkillEnum> computerSkills = new ArrayList<ComputerSkillEnum>();
			int tempKey = 0;
			for (String skillKey : skillKeys) {
				tempKey = NumberUtils.toInt(skillKey, 0);
				computerSkills.add(ComputerSkillEnum.getComputerSkillEnum(tempKey));
				skillDescLs.add(ComputerSkillEnum.getComputerSkillEnum(tempKey).getDesc());
			}

			tempKey = 0;
			String[] englishLangKeys = StringUtils.split(recruitInfoDTO.getEnglish(), ",");
			List<EnglishSkillEnum> englishSkills = new ArrayList<EnglishSkillEnum>();
			List<String> englishDescLs = new ArrayList<>();
			for (String englishLangKey : englishLangKeys) {
				tempKey = NumberUtils.toInt(englishLangKey, 0);
				englishSkills.add(EnglishSkillEnum.getEnglishSkillEnum(tempKey));
				englishDescLs.add(EnglishSkillEnum.getEnglishSkillEnum(tempKey).getDesc());
			}

			tempKey = 0;
			String[] otherLangKeys = StringUtils.split(recruitInfoDTO.getOtherLanguage(), ",");
			List<OtherLanguageSkillEnum> otherLangSkills = new ArrayList<OtherLanguageSkillEnum>();
			List<String> otherLanguageSkillDescLs = new ArrayList<>();
			for (String otherLangKey : otherLangKeys) {
				tempKey = NumberUtils.toInt(otherLangKey, 0);
				otherLangSkills.add(OtherLanguageSkillEnum.getOtherLanguageSkillEnum(tempKey));
				otherLanguageSkillDescLs.add(OtherLanguageSkillEnum.getOtherLanguageSkillEnum(tempKey).getDesc());
			}

			recruitInfoDTO.setSkillDesc(StringUtils.join(skillDescLs, ","));
			recruitInfoDTO.setSkillList(computerSkills);
			recruitInfoDTO.setEnglishDesc(StringUtils.join(englishDescLs, ","));
			recruitInfoDTO.setEnglishSkillList(englishSkills);
			recruitInfoDTO.setOtherLanguageDesc(StringUtils.join(otherLanguageSkillDescLs, ","));
			recruitInfoDTO.setOtherLanguageSkillList(otherLangSkills);
		}
	}

	@Override
	public RecruitInfoDTO queryRecruitInfo(Map<String, Object> queryMap) {
		RecruitInfoDTO recruitInfoDTO = recruitDAO.queryRecruitInfo(queryMap);
		fillRecruitInfo(recruitInfoDTO, true);
		return recruitInfoDTO;
	}

	@Override
	public RecruitInfoDTO queryRecruitInfo(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return queryRecruitInfo(queryMap);
	}

	@Override
	public List<RecruitInfoDTO> queryRecruitInfos(Map<String, Object> queryMap) {
		List<RecruitInfoDTO> ls = recruitDAO.queryRecruitInfos(queryMap);
		for (RecruitInfoDTO recruitInfoDTO : ls) {
			fillRecruitInfo(recruitInfoDTO, true);
		}
		return ls;
	}

	@Override
	public List<RecruitInfoDTO> queryRecruitInfos(Map<String, Object> queryMap, boolean isFillData) {
		List<RecruitInfoDTO> ls = recruitDAO.queryRecruitInfos(queryMap);
		for (RecruitInfoDTO recruitInfoDTO : ls) {
			fillRecruitInfo(recruitInfoDTO, isFillData);
		}
		return ls;
	}

	@Override
	public List<RecruitInfoDTO> queryRecruitInfoMemberId(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return queryRecruitInfos(queryMap);
	}

	@Override
	public List<RecruitInfoDTO> queryRecruitInfoByCompanyId(int companyId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("companyId", companyId);
		return queryRecruitInfos(queryMap);
	}

	@Override
	public List<RecruitInfoDTO> queryOnlineRecruitInfoByCompanyId(int companyId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("companyId", companyId);
		queryMap.put("positionStatus", PositionStatusEnum.Online.getCode());
		return queryRecruitInfos(queryMap);
	}

	@Override
	public int queryRecruitInfosCount(Map<String, Object> queryMap) {
		return recruitDAO.queryRecruitInfosCount(queryMap);
	}

	@Override
	public List<RecruitInfoDTO> queryRecruitInfoMemberId(String memberId, boolean isFillData) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return queryRecruitInfos(queryMap, isFillData);
	}

}
