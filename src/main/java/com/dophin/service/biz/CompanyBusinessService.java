package com.dophin.service.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.InformInterviewDAO;
import com.dophin.dao.MyApplicantDAO;
import com.dophin.dao.RecruitInfoDAO;
import com.dophin.dto.FilterMyApplicantCondition;
import com.dophin.dto.InformInterviewDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.MyApplicantDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.EducationEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.PositionSchoolTypeEnum;
import com.dophin.service.MemberService;
import com.dophin.service.ResumeEducationExpService;

/**
 * 企业用户业务操作
 * 
 * @author David.dai
 * 
 */
@Service
public class CompanyBusinessService {

	private static Logger logger = Logger.getLogger(CompanyBusinessService.class);

	@Autowired
	private RecruitInfoDAO recruitInfoDAO;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MyApplicantDAO myApplicantDAO;

	@Autowired
	private ResumeEducationExpService resumeEducationExpService;

	@Autowired
	private InformInterviewDAO informInterviewDAO;

	/**
	 * 获取当前发布者的职位列表（根据状态筛选）
	 * 
	 * @param memberId
	 * @param positionStatus
	 * @return
	 */
	public List<RecruitInfoDTO> queryRecruitInfoForPublisher(String memberId, int positionStatus) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		queryMap.put("positionStatus", positionStatus);
		return recruitInfoDAO.queryRecruitInfos(queryMap);
	}

	/**
	 * 获取发布者所属公司的所有职位列表（根据状态筛选）
	 * 
	 * @param memberId
	 *            发布者账户id
	 * @param positionStatus
	 *            职位状态
	 * @return 返回对应的结果列表
	 */
	public List<RecruitInfoDTO> queryRecruitInfoForMemberCompany(String memberId, int positionStatus) {
		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		int companyId = memberDTO.getCompanyId();

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("companyId", companyId);
		queryMap.put("positionStatus", positionStatus);
		return recruitInfoDAO.queryRecruitInfos(queryMap);
	}

	/**
	 * 当前发布者下面获取到的所有申请人数量
	 * 
	 * @param memberId
	 * @return
	 */
	public int queryMyApplicantInfosCount(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return myApplicantDAO.queryMyApplicantInfosCount(queryMap);
	}

	/**
	 * 获取我的申请人列表
	 * 
	 * @return
	 */
	public List<MyApplicantDTO> queryMyApplicantInfos(FilterMyApplicantCondition condition) {
		// 过滤相关申请人条件条件
		Map<String, Object> queryMap = new HashMap<String, Object>();

		if (condition != null) {
			if (condition.getFeedStatus() > 0 && condition.getFeedStatus() != 5) {
				queryMap.put("feedStatus", condition.getFeedStatus());
			}
			if (!StringUtils.isBlank(condition.getMemberId())) {
				queryMap.put("memberId", condition.getMemberId());
			}
			if (condition.getRecruitId() > 0) {
				queryMap.put("recruitId", condition.getRecruitId());
			}
			if (condition.getSchool() > 0) {
				PositionSchoolTypeEnum positionSchoolTypeEnum = PositionSchoolTypeEnum.getPositionSchoolTypeEnum(condition.getSchool());
				if (positionSchoolTypeEnum != null) {
					if (positionSchoolTypeEnum.getCode() >= 3) {
						queryMap.put("schoolType", positionSchoolTypeEnum.getType());
					} else {
						queryMap.put("schoolTag", positionSchoolTypeEnum.getTag());
					}
				}
			}
			if (condition.getDiploma() > 0) {
				PositionEducationEnum positionEducationEnum = PositionEducationEnum.getPositionEducationEnum(condition.getDiploma());
				if (positionEducationEnum != null) {
					List<EducationEnum> educationEnums = positionEducationEnum.getEducationList();
					List<Integer> list = new ArrayList<>();
					for (EducationEnum educationEnum : educationEnums) {
						list.add(educationEnum.getCode());
					}
					queryMap.put("diploma", StringUtils.join(list, ","));
				}
			}
			if (condition.getGraduationYear() > 0) {
				queryMap.put("graduationYear", condition.getGraduationYear());
			}
			if (condition.getGender() > 0) {
				queryMap.put("sex", condition.getGender());
			}
			if (condition.getWorkExpCount() >= 0) {
				queryMap.put("workExpCount", condition.getWorkExpCount());
			}
			if (condition.getStart() >= 0) {
				queryMap.put("start", condition.getStart());
			}
			if (condition.getSize() >= 0) {
				queryMap.put("size", condition.getSize());
			}
		}

		List<MyApplicantDTO> resultLs = myApplicantDAO.queryMyApplicantInfos(queryMap);
		List<MyApplicantDTO> newResultLs = new ArrayList<>();

		if (condition != null) {
			List<Integer> resumeIds = new ArrayList<Integer>();
			for (MyApplicantDTO item : resultLs) {
				if (!resumeIds.contains(item.getResumeId())) {
					resumeIds.add(item.getResumeId());
				}
			}

			// 过滤专业分类
			if (condition.getMajorType() > 0) {
				String ids = StringUtils.join(resumeIds, ",");
				Map<String, Object> qMap = new HashMap<String, Object>();
				qMap.put("resumeIds", ids);
				qMap.put("majorType", condition.getMajorType());
				List<Integer> validResumeIds = resumeEducationExpService.queryResumeIdByMajorType(qMap);

				for (MyApplicantDTO item : resultLs) {
					if (validResumeIds.contains(item.getResumeId())) {
						newResultLs.add(item);
					}
				}

				resultLs = newResultLs;
			}
		}

		return resultLs;
	}

	/**
	 * 发送面试通知
	 * 
	 * @param informInterviewDTO
	 *            面试通知实体类
	 * @return 返回面试通知记录表新增ID
	 */
	public int informInterview(InformInterviewDTO informInterviewDTO) {
		Map<String, Object> queryMap = new HashMap<>();
		if (informInterviewDTO.getId() > 0) {
			queryMap.put("id", informInterviewDTO.getId());
		} else if (!StringUtils.isEmpty(informInterviewDTO.getMemberId())) {
			queryMap.put("memberId", informInterviewDTO.getMemberId());
		}

		int count = informInterviewDAO.deleteInformInterviewInfo(queryMap);
		logger.info("delete " + count + " inform_interview record for memberId: " + informInterviewDTO.getMemberId());
		int count2 = informInterviewDAO.insertInformInterviewInfo(informInterviewDTO);
		logger.info("add " + count2 + " inform_interview record for memberId: " + informInterviewDTO.getMemberId());
		return informInterviewDTO.getId();
	}

	/**
	 * 根据id或者memberid查询相应通知面试信息
	 * 
	 * @param id
	 * @param memberId
	 * @return
	 */
	public InformInterviewDTO queryInformInterview(int id, String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (id > 0) {
			queryMap.put("id", id);
		}
		if (!StringUtils.isBlank(memberId)) {
			queryMap.put("memberId", memberId);
		}

		return informInterviewDAO.queryInformInterviewInfo(queryMap);
	}
}
