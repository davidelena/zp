package com.dophin.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.ApplyFeedBackDTO;
import com.dophin.dto.FilterMyApplicantCondition;
import com.dophin.dto.InformInterviewDTO;
import com.dophin.dto.MyApplicantDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.FeedStatusEnum;
import com.dophin.enums.GenderEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.PositionSchoolTypeEnum;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.service.biz.CompanyBusinessService;
import com.dophin.service.biz.StudentBusinessService;
import com.dophin.utils.CommonUtils;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBusinessService extends TestBaseService {
	@Autowired
	private StudentBusinessService studentBusinessService;

	@Autowired
	private CompanyBusinessService companyBusinessService;

	@Autowired
	private MemberService memberService;

	@Test
	public void testGetApplyFeedBackDTOs() {
		String memberId = "1441704227484"; // 1441704227484
		String feedStatusStr = "4"; // 1,2
		List<ApplyFeedBackDTO> ls = studentBusinessService.queryApplyFeedBackDTOs(memberId, feedStatusStr);
		System.err.println(ls.size());
		for (ApplyFeedBackDTO item : ls) {
			System.err.println(item);
		}
	}

	@Test
	public void testqueryCompanyPositionList() {
		int companyId = 2;
		List<RecruitInfoDTO> ls = studentBusinessService.queryCompanyPositionList(companyId);
		System.err.println(ls.size());
		for (RecruitInfoDTO item : ls) {
			System.err.println(item);
		}
	}

	@Test
	public void testSendResume() {
		String memberId = "1441704227484";
		int resumeId = 2;
		int recruitId = 1;

		int count = studentBusinessService.sendResume(memberId, resumeId, recruitId);
		System.err.println(count);
	}

	@Test
	public void testQueryRecruitInfoForPublisher() {
		String memberId = "1441704227484";
		int positionStatus = PositionStatusEnum.Online.getCode();

		List<RecruitInfoDTO> ls = companyBusinessService.queryRecruitInfoForPublisher(memberId, positionStatus);
		System.err.println(ls.size());
		for (RecruitInfoDTO item : ls) {
			System.err.println(item);
		}
	}

	@Test
	public void testQueryRecruitInfoForMemberCompany() {
		String memberId = "1441704227484";
		int positionStatus = PositionStatusEnum.Online.getCode();

		List<RecruitInfoDTO> ls = companyBusinessService.queryRecruitInfoForMemberCompany(memberId, positionStatus);
		System.err.println(ls.size());
		for (RecruitInfoDTO item : ls) {
			System.err.println(item);
		}
	}

	@Test
	public void testQueryMyApplicantInfos() {
		FilterMyApplicantCondition condition = new FilterMyApplicantCondition();
		condition.setMemberId("1445836364841");
		List<MyApplicantDTO> ls = companyBusinessService.queryMyApplicantInfos(condition);
		System.err.println(ls.size());
		for (MyApplicantDTO item : ls) {
			System.err.println(item);
		}
	}

	/*
	 * 我的申请人
	 */
	@Test
	public void testQueryMyApplicantResumeInfos() {
		FilterMyApplicantCondition condition = new FilterMyApplicantCondition();
		condition.setMemberId("1441704227484");
		condition.setFeedStatus(FeedStatusEnum.NonViewed.getCode());
		condition.setSchool(PositionSchoolTypeEnum.SchoolType211.getCode());
		condition.setDiploma(PositionEducationEnum.MasterPlus.getCode());
		condition.setGraduationYear(2014);
		condition.setGender(GenderEnum.Male.getCode());
		condition.setRecruitId(2);
		condition.setMajorType(2);
		// condition.setWorkExpCount(0);
		System.err.println(condition.getWorkExpCount());

		List<MyApplicantDTO> ls = companyBusinessService.queryMyApplicantInfos(condition);
		for (MyApplicantDTO item : ls) {
			System.err.println(item);
		}
	}

	@Test
	public void testInformInterview() {
		// String memberId = "1441704227484";
		// MemberDTO memberDTO = memberService.queryMemberInfo(memberId);

		InformInterviewDTO informInterviewDTO = new InformInterviewDTO();
		informInterviewDTO.setMemberId(String.valueOf(timestamp));
		informInterviewDTO.setMemberName("面试者姓名" + timestamp);
		informInterviewDTO.setMemberEmail("" + timestamp + "@qq.com");
		informInterviewDTO.setInterviewDate(new Date());
		informInterviewDTO.setDetailTime("具体面试" + timestamp);
		informInterviewDTO.setInterviewAddress("具体面试地点" + timestamp);
		informInterviewDTO.setContactPerson("联系人" + timestamp);
		informInterviewDTO.setContactPhone("联系电话" + timestamp);
		informInterviewDTO.setInterviewContent("通知面试内容" + timestamp);

		int id = companyBusinessService.informInterview(informInterviewDTO);
		System.err.println(id);
	}

	@Test
	public void testInformInterviewInsert() {

		InformInterviewDTO informInterviewDTO = new InformInterviewDTO();
		informInterviewDTO.setMemberId("1445704283175");
		informInterviewDTO.setMemberName("面试者姓名" + timestamp);
		informInterviewDTO.setMemberEmail("" + timestamp + "@qq.com");
		informInterviewDTO.setInterviewDate(new Date());
		informInterviewDTO.setDetailTime("具体面试" + timestamp);
		informInterviewDTO.setInterviewAddress("具体面试地点" + timestamp);
		informInterviewDTO.setContactPerson("联系人" + timestamp);
		informInterviewDTO.setContactPhone("联系电话" + timestamp);
		informInterviewDTO.setInterviewContent("通知面试内容" + timestamp);
		int id = companyBusinessService.informInterview(informInterviewDTO);
		System.err.println(id);
	}

	@Test
	public void testInformInterviewNotify() {
		int id = 0;
		String memberId = "1445704292307";
		InformInterviewDTO informInterviewDTO = companyBusinessService.queryInformInterview(id, memberId);
		System.err.println(informInterviewDTO);
	}

	@Test
	public void testHttpClients() {
		String html = StringUtils.EMPTY;
		String url = "http://" + CommonUtils.CONTEXT_URL + "/resume/resume_info_view?id=1445836364841&rsid=19";
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.OK.value()) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println(html);
	}

}
