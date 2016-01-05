package com.dophin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.MemberRecruitDAO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.RecruitResumeCountDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.service.MemberRecruitService;
import com.dophin.service.MemberService;
import com.dophin.service.RecruitInfoService;
import com.dophin.service.ResumeInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MemberRecruitServiceImpl extends BaseService implements MemberRecruitService {

	private static final String ES_MEMBER_RECRUIT = "memberrecruit";

	@Autowired
	private MemberRecruitDAO memberRecruitDAO;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Autowired
	private RecruitInfoService recruitInfoService;

	private void doUpdateRecruitInfo(int id) throws JsonProcessingException {
		RecruitInfoDTO updatedRecruitInfoDTO = recruitInfoService.queryRecruitInfo(id);
		String result2 = mapper.writeValueAsString(updatedRecruitInfoDTO);
		doUpdateAction(result2, updatedRecruitInfoDTO.getId(), ES_RECRUIT_INFO);
	}

	@Override
	public int insertMemberRecruit(MemberRecruitDTO memberRecruitDTO) {
		memberRecruitDAO.insertMemberRecruit(memberRecruitDTO);
		try {
			MemberRecruitDTO newMemberRecruitDTO = queryMemberRecruit(memberRecruitDTO.getId());		
			String result = mapper.writeValueAsString(newMemberRecruitDTO);
			doIndexAction(result, memberRecruitDTO.getId(), ES_MEMBER_RECRUIT);

			// 更新了memberRecruitInfo需要级联更新recruitInfo索引
			doUpdateRecruitInfo(memberRecruitDTO.getRecruitId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return memberRecruitDTO.getId();
	}

	@Override
	public int updateMemberRecruit(MemberRecruitDTO memberRecruitDTO) {
		int count = memberRecruitDAO.updateMemberRecruit(memberRecruitDTO);
		try {
			// 此处一定要查询更新湾最新的MemberDTO存入es索引库中
			MemberRecruitDTO updatedMemberRecruitDTO = queryMemberRecruit(memberRecruitDTO.getId());
			String result = mapper.writeValueAsString(updatedMemberRecruitDTO);
			doUpdateAction(result, updatedMemberRecruitDTO.getId(), ES_MEMBER_RECRUIT);

			// 更新了memberRecruitInfo需要级联更新recruitInfo索引
			doUpdateRecruitInfo(memberRecruitDTO.getRecruitId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteMemberRecruit(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		MemberRecruitDTO memberRecruitDTO = memberRecruitDAO.queryMemberRecruitByConditions(queryMap);
		int recruitId = memberRecruitDTO == null ? 0 : memberRecruitDTO.getRecruitId();
		int count = memberRecruitDAO.deleteMemberRecruit(id);
		try {
			doDeleteAction(id, ES_MEMBER_RECRUIT);
			// 更新了memberRecruitInfo需要级联更新recruitInfo索引
			doUpdateRecruitInfo(recruitId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public MemberRecruitDTO queryMemberRecruit(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return queryMemberRecruit(queryMap);
	}

	@Override
	public MemberRecruitDTO queryMemberRecruit(int id, boolean isFillData) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return queryMemberRecruit(queryMap, isFillData);
	}

	@Override
	public MemberRecruitDTO queryMemberRecruit(Map<String, Object> queryMap) {
		return queryMemberRecruit(queryMap, true);
	}

	@Override
	public MemberRecruitDTO queryMemberRecruit(Map<String, Object> queryMap, boolean isFillData) {
		MemberRecruitDTO memberRecruitDTO = memberRecruitDAO.queryMemberRecruit(queryMap);
		if (isFillData) {
			if (memberRecruitDTO != null) {
				MemberDTO memberDTO = memberService.queryMemberInfo(memberRecruitDTO.getMemberId());
				ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(memberRecruitDTO.getResumeId());
				RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(memberRecruitDTO.getRecruitId());
				memberRecruitDTO.setMemberDTO(memberDTO);
				memberRecruitDTO.setResumeInfoDTO(resumeInfoDTO);
				memberRecruitDTO.setRecruitInfoDTO(recruitInfoDTO);
			}
		}

		return memberRecruitDTO;
	}

	@Override
	public List<MemberRecruitDTO> queryMemberRecruits(Map<String, Object> queryMap, boolean isFillData) {
		List<MemberRecruitDTO> memberRecruitDTOs = memberRecruitDAO.queryMemberRecruits(queryMap);
		for (MemberRecruitDTO memberRecruitDTO : memberRecruitDTOs) {

			if (isFillData) {
				if (memberRecruitDTO != null) {
					MemberDTO memberDTO = memberService.queryMemberInfo(memberRecruitDTO.getMemberId());
					ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(memberRecruitDTO.getResumeId());
					RecruitInfoDTO recruitInfoDTO = recruitInfoService
							.queryRecruitInfo(memberRecruitDTO.getRecruitId());
					memberRecruitDTO.setMemberDTO(memberDTO);
					memberRecruitDTO.setResumeInfoDTO(resumeInfoDTO);
					memberRecruitDTO.setRecruitInfoDTO(recruitInfoDTO);
				}
			} else {
				memberRecruitDTO.setMemberDTO(null);
				memberRecruitDTO.setResumeInfoDTO(null);
				memberRecruitDTO.setRecruitInfoDTO(null);
			}
		}
		return memberRecruitDTOs;
	}

	@Override
	public List<MemberRecruitDTO> queryMemberRecruits(String memberId, boolean isFillData) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return queryMemberRecruits(queryMap, isFillData);
	}

	@Override
	public List<MemberRecruitDTO> queryMemberRecruitsByRecruitId(int recruitId, boolean isFillData) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("recruitId", recruitId);
		return queryMemberRecruits(queryMap, isFillData);
	}

	@Override
	public int deleteMemberRecruitByCondition(Map<String, Object> queryMap) {
		return memberRecruitDAO.deleteMemberRecruitByCondition(queryMap);
	}

	@Override
	public List<RecruitResumeCountDTO> queryJobResumeApplyCount(Map<String, Object> queryMap) {
		return memberRecruitDAO.queryJobResumeApplyCount(queryMap);
	}

	@Override
	public Map<Integer, RecruitResumeCountDTO> queryJobResumeApplyCountForMap(Integer recruitId) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("recruitId", recruitId);
		List<RecruitResumeCountDTO> ls = queryJobResumeApplyCount(queryMap);

		Map<Integer, RecruitResumeCountDTO> resultMap = new HashMap<>();
		for (RecruitResumeCountDTO item : ls) {
			if (!resultMap.containsKey(item.getRecruitId())) {
				resultMap.put(item.getRecruitId(), item);
			}
		}

		return resultMap;
	}

	@Override
	public List<MemberRecruitDTO> queryMemberRecruitsForMyApply(Map<String, Object> queryMap) {
		return memberRecruitDAO.queryMemberRecruitsForMyApply(queryMap);
	}

	@Override
	public int queryMemberRecruitsForMyApplyCount(Map<String, Object> queryMap) {
		return memberRecruitDAO.queryMemberRecruitsForMyApplyCount(queryMap);
	}

	@Override
	public MemberRecruitDTO queryMemberRecruitByConditions(Map<String, Object> queryMap) {
		return memberRecruitDAO.queryMemberRecruitByConditions(queryMap);
	}

}
