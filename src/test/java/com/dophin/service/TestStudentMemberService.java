package com.dophin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.StudentInfoDTO;
import com.dophin.enums.JobStatusEnum;
import com.dophin.enums.JobTypeEnum;
import com.dophin.enums.RecommendFrequencyEnum;
import com.dophin.enums.SalaryLevelEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestStudentMemberService extends TestBaseService {

	@Autowired
	private StudentMemberService studentMemberService;

	@Test
	public void testInsertStudentInfo() {

		StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
		studentInfoDTO.setMemberId(String.valueOf(timestamp));
		studentInfoDTO.setDemandPosition("高级java开发工程师" + timestamp);
		studentInfoDTO.setDemandType(JobTypeEnum.FullTime.getCode());
		studentInfoDTO.setDemandCity("300,400,2600");
		studentInfoDTO.setDemandIndustry("18,28,38");
		studentInfoDTO.setDemandSalary(SalaryLevelEnum.S10000To20000.getCode());
		studentInfoDTO.setAllowRecommend(true);
		studentInfoDTO.setAllowEmail(true);
		studentInfoDTO.setJobStatus(JobStatusEnum.NoButWantApply.getCode());
		studentInfoDTO.setRecommendEmail(String.valueOf(timestamp) + "@qq.com");
		studentInfoDTO.setRecommendFrequency(RecommendFrequencyEnum.Every7Day.getCode());

		int newId = studentMemberService.insertStudentInfo(studentInfoDTO);
		System.err.println(newId);
	}

	@Test
	public void testDeleteStudentInfo() {
		int id = 1;
		int count = studentMemberService.deleteStudentInfo(id);

		String memberId = "1443166503678";
		int count2 = studentMemberService.deleteStudentInfo(memberId);

		String mid = "1443193744710";
		int count3 = studentMemberService.deleteStudentInfo(mid);

		System.err.println(count);
		System.err.println(count2);
		System.err.println(count3);
	}

	@Test
	public void testQueryStudentInfo() {

		int id = 9;
		// String memberId = "1445272235898";
		StudentInfoDTO item = studentMemberService.queryStudentInfo(id);
		// StudentInfoDTO item2 =
		// studentMemberService.queryStudentInfo(memberId);

		System.err.println(item);
		System.err.println(item.isAllowEmail());
		System.err.println(item.isAllowRecommend());
		// System.err.println(item2);
	}

	@Test
	public void testUpdateStudentInfo() {
		String memberId = "1445914514449";
		StudentInfoDTO item = studentMemberService.queryStudentInfo(memberId);
		System.err.println(item);

		item.setJobStatus(JobStatusEnum.Applying.getCode());
		item.setDemandSalary(SalaryLevelEnum.S5000To8000.getCode());
		item.setRecommendFrequency(RecommendFrequencyEnum.Every7Day.getCode());
		item.setDemandIndustry("18,28,38");// 18,28,38
		item.setDemandCity("501,502,503");// 2600,300
		item.setAllowEmail(false);
		item.setAllowRecommend(false);

		int count = studentMemberService.updateStudentInfo(item);
		System.err.println(count);
		StudentInfoDTO result = studentMemberService.queryStudentInfo(memberId);
		System.err.println(result);
	}

}
