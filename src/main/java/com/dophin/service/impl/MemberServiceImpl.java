package com.dophin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.MemberDAO;
import com.dophin.dto.CompanyInfoDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.StudentInfoDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.service.CompanyMemberService;
import com.dophin.service.ConstantsService;
import com.dophin.service.MemberRecruitService;
import com.dophin.service.MemberService;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.StudentMemberService;

@Service
public class MemberServiceImpl extends BaseService implements MemberService {

	private static final String ES_MEMBER_INFO = "memberinfo";

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private CompanyMemberService companyMemberService;

	@Autowired
	private StudentMemberService studentMemberService;

	@Autowired
	private MemberRecruitService memberRecruitService;

	@Autowired
	private ConstantsService constantsService;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public String insertMemberInfo(MemberDTO memberDTO) {
		memberDAO.insertMemberInfo(memberDTO);
		try {
			// 此时应该还没有招聘信息的职位所以不需要发布
			MemberDTO insertMemberDTO = queryMemberInfo(memberDTO.getMemberId());
			String result = mapper.writeValueAsString(insertMemberDTO);
			doIndexAction(result, memberDTO.getId(), ES_MEMBER_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return memberDTO.getMemberId();
	}

	@Override
	public int updateMemberInfo(MemberDTO memberDTO) {

		int count = memberDAO.updateMemberInfo(memberDTO);
		// 此处一定要查询更新湾最新的MemberDTO存入es索引库中
		MemberDTO updatedMemberDTO = queryMemberInfo(memberDTO.getMemberId());
		try {

			String result = mapper.writeValueAsString(updatedMemberDTO);
			doUpdateAction(result, updatedMemberDTO.getId(), ES_MEMBER_INFO);
			
			logger.info("execute resumeinfo action... for memberid: " + memberDTO.getMemberId());
			List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos(memberDTO.getMemberId());
			String resumeInfoJsonStr = StringUtils.EMPTY;
			for (ResumeInfoDTO resumeInfoDTO : resumeInfoDTOs) {
				resumeInfoJsonStr = mapper.writeValueAsString(resumeInfoDTO);
				doUpdateAction(resumeInfoJsonStr, resumeInfoDTO.getId(), ES_RESUME_INFO);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteMemberInfo(String id) {
		int delId = queryMemberInfo(id).getId();
		int count = memberDAO.deleteMemberInfo(id);
		try {
			doDeleteAction(delId, ES_MEMBER_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public MemberDTO queryMemberInfo(Map<String, Object> queryMap) {
		MemberDTO memberDTO = memberDAO.queryMemberInfo(queryMap);
		if (memberDTO != null) {
			CompanyInfoDTO companyInfoDTO = companyMemberService.queryCompanyInfo(memberDTO.getCompanyId());
			StudentInfoDTO studentInfoDTO = studentMemberService.queryStudentInfo(memberDTO.getStudentId());
			UniversityDTO universityDTO = constantsService.queryUniversity(memberDTO.getSchoolId());

			if (companyInfoDTO != null) {
				memberDTO.setCompanyInfoDTO(companyInfoDTO);
			}
			if (studentInfoDTO != null) {
				memberDTO.setStudentInfoDTO(studentInfoDTO);
			}
			if (universityDTO != null) {
				memberDTO.setUniversityDTO(universityDTO);
				memberDTO.setSchoolDesc(universityDTO.getName());
			}
		}

		return memberDTO;
	}

	@Override
	public MemberDTO queryMemberInfo(String memberId) {
		Map<String, Object> queryMap = getMemberIdMap(memberId);
		return queryMemberInfo(queryMap);
	}

	@Override
	public boolean isMemberExists(Map<String, Object> queryMap) {
		int count = memberDAO.isMemberExists(queryMap);
		return count > 0;
	}

	@Override
	public boolean isMemberExists(String memberId, int source) {
		Map<String, Object> queryMap = getMemberIdMap(memberId);
		queryMap.put("source", source);
		return isMemberExists(queryMap);
	}

	private Map<String, Object> getMemberIdMap(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return queryMap;
	}

	@Override
	public MemberDTO queryMemberInfoByMobile(String mobile, int source) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("source", source);
		queryMap.put("mobile", mobile);
		return queryMemberInfo(queryMap);
	}

	@Override
	public MemberDTO queryMemberInfoByEmail(String email, int source) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("source", source);
		queryMap.put("email", email);
		return queryMemberInfo(queryMap);
	}

	@Override
	public List<MemberDTO> queryCompanyIdBySuffix(Map<String, Object> queryMap) {
		return memberDAO.queryCompanyIdBySuffix(queryMap);
	}

	@Override
	public List<MemberDTO> queryMemberInfoByConditions(Map<String, Object> queryMap) {
		return memberDAO.queryMemberInfoByConditions(queryMap);
	}
}
