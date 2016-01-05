package com.dophin.service.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ApplyFeedBackDAO;
import com.dophin.dto.ApplyFeedBackDTO;
import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.FeedStatusEnum;
import com.dophin.service.MemberRecruitService;
import com.dophin.service.RecruitInfoService;

/**
 * 学生业务信息类
 * 
 * @author David.dai
 * 
 */
@Service
public class StudentBusinessService {
	@Autowired
	private ApplyFeedBackDAO applyFeedBackDAO;

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private MemberRecruitService memberRecruitService;

	/**
	 * 查看简历反馈状态
	 * 
	 * @param memberId
	 *            为空条件为所有 申请人id
	 * @param feedStatus
	 *            为空为所有 反馈状态
	 * @return 相应的反馈条件列表
	 */
	public List<ApplyFeedBackDTO> queryApplyFeedBackDTOs(String memberId, String feedStatusStr) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (!StringUtils.isBlank(memberId)) {
			queryMap.put("memberId", memberId);
		}
		if (!StringUtils.isBlank(feedStatusStr)) {
			queryMap.put("feedStatusStr", feedStatusStr);
		}

		return applyFeedBackDAO.queryApplyFeedBackInfos(queryMap);
	}

	/**
	 * 投递简历
	 * 
	 * @param memberId
	 *            申请学生账户id
	 * @param resumeId
	 *            投递用的简历id
	 * @param recruitId
	 *            投递的职位id
	 */
	public int sendResume(String memberId, int resumeId, int recruitId) {
		MemberRecruitDTO memberRecruitDTO = new MemberRecruitDTO();
		memberRecruitDTO.setMemberId(memberId);
		memberRecruitDTO.setResumeId(resumeId);
		memberRecruitDTO.setRecruitId(recruitId);
		memberRecruitDTO.setFeedStatus(FeedStatusEnum.NonViewed.getCode());

		return memberRecruitService.insertMemberRecruit(memberRecruitDTO);
	}

	/**
	 * 查看公司主页下面的职位列表
	 * 
	 * @param companyId
	 * @return
	 */
	public List<RecruitInfoDTO> queryCompanyPositionList(int companyId) {
		return recruitInfoService.queryRecruitInfoByCompanyId(companyId);
	}
}
