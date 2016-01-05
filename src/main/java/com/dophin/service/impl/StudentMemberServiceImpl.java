package com.dophin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ConstantsDAO;
import com.dophin.dao.StudentMemberDAO;
import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.StudentInfoDTO;
import com.dophin.service.MemberService;
import com.dophin.service.RecruitInfoService;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.StudentMemberService;

@Service
public class StudentMemberServiceImpl extends BaseService implements StudentMemberService {

	@Autowired
	private StudentMemberDAO studentMemberDAO;

	@Autowired
	private ConstantsDAO constantsDAO;

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private ResumeInfoService resumeInfoService;
	
	@Autowired
	private MemberService memberService;

	@Override
	public int insertStudentInfo(StudentInfoDTO studentInfoDTO) {
		studentMemberDAO.insertStudentInfo(studentInfoDTO);
		try {
			StudentInfoDTO insertStudentInfoDTO = queryStudentInfo(studentInfoDTO.getId());
			String result = mapper.writeValueAsString(insertStudentInfoDTO);
			doIndexAction(result, studentInfoDTO.getId(), ES_STUDENT_INFO);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return studentInfoDTO.getId();
	}

	@Override
	public int updateStudentInfo(StudentInfoDTO studentInfoDTO) {
		int count = studentMemberDAO.updateStudentInfo(studentInfoDTO);
		
		// 此处一定要查询更新湾最新的MemberDTO存入es索引库中
		StudentInfoDTO updatedStudentInfoDTO = queryStudentInfo(studentInfoDTO.getId());
		try {

			String result = mapper.writeValueAsString(updatedStudentInfoDTO);
			doUpdateAction(result, updatedStudentInfoDTO.getId(), ES_STUDENT_INFO);
			
			//更新招聘职位级联
			logger.info("execute recruitinfo action... for studentid: " + studentInfoDTO.getId());
			List<RecruitInfoDTO> recruitInfoDTOs = recruitInfoService.queryRecruitInfoMemberId(studentInfoDTO
					.getMemberId());
			String recruitInfoJsonStr = StringUtils.EMPTY;
			for (RecruitInfoDTO recruitInfoDTO : recruitInfoDTOs) {
				recruitInfoJsonStr = mapper.writeValueAsString(recruitInfoDTO);
				doUpdateAction(recruitInfoJsonStr, recruitInfoDTO.getId(), ES_RECRUIT_INFO);
			}

			//更新简历信息级联
			logger.info("execute resumeinfo action... for studentid: " + studentInfoDTO.getId());
			List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos(studentInfoDTO.getMemberId());
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
	public int deleteStudentInfo(Map<String, Object> queryMap) {
		return studentMemberDAO.deleteStudentInfo(queryMap);
	}

	@Override
	public StudentInfoDTO queryStudentInfo(Map<String, Object> queryMap) {
		StudentInfoDTO studentInfoDTO = studentMemberDAO.queryStudentInfo(queryMap);

		if (studentInfoDTO != null) {
			Map<String, Object> queryGeoAreaMap = new HashMap<>();
			if (!StringUtils.isEmpty(studentInfoDTO.getDemandCity())) {
				queryGeoAreaMap.put("cityIds", studentInfoDTO.getDemandCity());
				List<GeoAreaDTO> geoAreaDTOs = constantsDAO.queryGeoAreas(queryGeoAreaMap);
				studentInfoDTO.setDemandCitys(geoAreaDTOs);
			}

			Map<String, Object> queryIndustryMap = new HashMap<>();
			if (!StringUtils.isEmpty(studentInfoDTO.getDemandIndustry())) {
				queryIndustryMap.put("industryIds", studentInfoDTO.getDemandIndustry());
				List<IndustryDTO> industryDTOs = constantsDAO.queryIndustries(queryIndustryMap);
				studentInfoDTO.setDemandIndustries(industryDTOs);
			}
		}

		return studentInfoDTO;
	}

	@Override
	public int deleteStudentInfo(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return deleteStudentInfo(queryMap);
	}

	@Override
	public int deleteStudentInfo(String memberId) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("memberId", memberId);
		return deleteStudentInfo(queryMap);
	}

	@Override
	public StudentInfoDTO queryStudentInfo(int id) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("id", id);
		return queryStudentInfo(queryMap);
	}

	@Override
	public StudentInfoDTO queryStudentInfo(String memberId) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("memberId", memberId);
		return queryStudentInfo(queryMap);
	}

}
