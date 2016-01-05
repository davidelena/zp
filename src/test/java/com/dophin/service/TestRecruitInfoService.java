package com.dophin.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dao.ConstantsDAO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.WorkCityDTO;
import com.dophin.enums.AchievementEnum;
import com.dophin.enums.InternshipDaysEnum;
import com.dophin.enums.InternshipExpEnum;
import com.dophin.enums.InvitePostEnum;
import com.dophin.enums.JobTypeEnum;
import com.dophin.enums.PositionAvailableEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.PositionSchoolTypeEnum;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.enums.RecruitEmailTypeEnum;
import com.dophin.enums.SchoolActivityEnum;
import com.dophin.enums.SkillLevelEnum;
import com.dophin.enums.SpecialityEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRecruitInfoService extends TestBaseService
{

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private ConstantsDAO constantsDAO;

	private String[] postionNames = new String[] { "高级软件开发工程师", "高级测试工程师", "高级ued设计", "高级运维工程师", "高级前端开发工程师" };
	private PositionStatusEnum[] positionStatusEnums = new PositionStatusEnum[] { PositionStatusEnum.Offline,
			PositionStatusEnum.Online, PositionStatusEnum.Draft };

	@Test
	public void testQueryRecruitInfosCount()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", "1445836364841");
		int count = recruitInfoService.queryRecruitInfosCount(queryMap);
		System.err.println(count);
	}
	
	@Test
	public void batchInsertToTestPagenate()
	{
		for (int i = 0; i < 19; i++)
		{
			testInserRecruitInfo();
		}
	}
	
	@Test
	public void testInserRecruitInfo()
	{
		Random r = new Random();
		RecruitInfoDTO recruitInfoDTO = new RecruitInfoDTO();
		recruitInfoDTO.setMemberId("1445836364841");
		recruitInfoDTO.setCompanyId(8);
		recruitInfoDTO.setRecruitType(JobTypeEnum.FullTime.getCode());
		recruitInfoDTO.setPositionType(2);
		recruitInfoDTO.setPositionName(postionNames[r.nextInt(postionNames.length)]);
		recruitInfoDTO.setDepartmentName("部门名称" + timestamp);
		recruitInfoDTO.setPostDuty("岗位职责" + timestamp);
		recruitInfoDTO.setWorkAddress(String.format("%d-%s", 2600, "上海" + timestamp));
		// recruitInfoDTO.setWorkCityDTOs(Arrays.asList(new WorkCityDTO[] { new
		// WorkCityDTO(2600, "上海", "上海" + timestamp),
		// new WorkCityDTO(3401, "杭州", "杭州" + timestamp), }));
		recruitInfoDTO.setMinSalary(2000);
		recruitInfoDTO.setMaxSalary(10000);
		recruitInfoDTO.setIsNegotiable(true);
		recruitInfoDTO.setImportantRemark("1,2,3,4,5,6");
		recruitInfoDTO.setNeedNum(10);
		recruitInfoDTO.setAcceptEmail("274714299@qq.com");
		recruitInfoDTO.setEmailType(RecruitEmailTypeEnum.SendToMailbox.getCode());
		recruitInfoDTO.setValidityTime(new Date());
		recruitInfoDTO.setSchool(PositionSchoolTypeEnum.SchoolType211.getCode());
		recruitInfoDTO.setMajor(SpecialityEnum.Agriculture.getCode());
		recruitInfoDTO.setEducational(PositionEducationEnum.MasterPlus.getCode());
		recruitInfoDTO.setScore(AchievementEnum.Percentage10.getCode());
		recruitInfoDTO.setInternshipDays(InternshipDaysEnum.Day2.getCode());
		recruitInfoDTO.setInternshipExp(InternshipExpEnum.NeedInternshipExp.getCode());
		recruitInfoDTO.setActivityExp(SchoolActivityEnum.NeedSchoolActivityExp.getCode());
		recruitInfoDTO.setSkill("1,2,3");
		recruitInfoDTO.setSkillLevel(SkillLevelEnum.Master.getCode());
		recruitInfoDTO.setEnglish("1,2,3");
		recruitInfoDTO.setOtherLanguage("1,2,3");
		recruitInfoDTO.setPositionAvailable(PositionAvailableEnum.ToAll.getCode());
		recruitInfoDTO.setInvitePost(InvitePostEnum.InviteSuitableGuy.getCode());
		recruitInfoDTO.setOtherClaim("其他申明" + timestamp);
		recruitInfoDTO.setPositionStatus(positionStatusEnums[r.nextInt(positionStatusEnums.length)].getCode());

		int id = recruitInfoService.insertRecruitInfo(recruitInfoDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryRecruitInfo()
	{
		int id = 3;
		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(id);
		System.err.println(recruitInfoDTO);
		System.err.println(recruitInfoDTO.getSkillLevel());
	}

	@Test
	public void testQueryRecruitByMemberId()
	{
		List<RecruitInfoDTO> ls = recruitInfoService.queryRecruitInfoMemberId("1441704227484");
		System.err.println(ls.size());
		for (RecruitInfoDTO item : ls)
		{
			System.err.println(item);
			System.err.println(item.getMemberDTO());
			System.err.println(item.getCompanyInfoDTO());
		}
	}

	@Test
	public void testQueryRecruitByCompanyId()
	{
		List<RecruitInfoDTO> ls = recruitInfoService.queryRecruitInfoByCompanyId(2);
		System.err.println(ls.size());
		for (RecruitInfoDTO item : ls)
		{
			System.err.println(item);
			System.err.println(item.getMemberDTO());
			System.err.println(item.getCompanyInfoDTO());
			System.err.println(item.getWorkCityDTO());
		}
	}

	@Test
	public void testQueryRecruitInfos()
	{
		List<RecruitInfoDTO> ls = recruitInfoService.queryRecruitInfos(new HashMap<String, Object>(), false);
		System.err.println(ls.size());
		for (RecruitInfoDTO item : ls)
		{
			System.err.println(item);
			System.err.println(sdf.format(item.getCreateTime()));
		}
	}

	@Test
	public void testUpdateRecruitInfo()
	{
		int id = 16;
		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(id);

		System.err.println("before: " + recruitInfoDTO);
		recruitInfoDTO.setMemberId("1441704227484");
		recruitInfoDTO.setCompanyId(2);
		recruitInfoDTO.setRecruitType(JobTypeEnum.Internship.getCode());
		recruitInfoDTO.setPositionType(3);
		recruitInfoDTO.setPositionName("更新职位名称" + timestamp);
		recruitInfoDTO.setDepartmentName("更新部门名称" + timestamp);
		recruitInfoDTO.setPostDuty("更新岗位职责" + timestamp);
		recruitInfoDTO.setWorkAddress(String.format("%d-%s", 721, "721" + timestamp));
		recruitInfoDTO.setMinSalary(2000);
		recruitInfoDTO.setMaxSalary(10000);
		recruitInfoDTO.setIsNegotiable(true);
		recruitInfoDTO.setImportantRemark("7,8,9,10,11,12");
		recruitInfoDTO.setNeedNum(10);
		recruitInfoDTO.setAcceptEmail("274714299@qq.com");
		recruitInfoDTO.setEmailType(RecruitEmailTypeEnum.OnlyToNotifyMeCount.getCode());
		recruitInfoDTO.setValidityTime(new Date());
		recruitInfoDTO.setSchool(PositionSchoolTypeEnum.SchoolType985.getCode());
		recruitInfoDTO.setMajor(SpecialityEnum.Economics.getCode());
		recruitInfoDTO.setEducational(PositionEducationEnum.DoctorPlus.getCode());
		recruitInfoDTO.setScore(AchievementEnum.Percentage50.getCode());
		recruitInfoDTO.setInternshipDays(InternshipDaysEnum.Day5.getCode());
		recruitInfoDTO.setInternshipExp(InternshipExpEnum.NoLimit.getCode());
		recruitInfoDTO.setActivityExp(SchoolActivityEnum.NoLimit.getCode());
		recruitInfoDTO.setSkill("4,5,6");
		recruitInfoDTO.setSkillLevel(SkillLevelEnum.General.getCode());
		recruitInfoDTO.setEnglish("4,5,6");
		recruitInfoDTO.setOtherLanguage("4,5,6");
		recruitInfoDTO.setPositionAvailable(PositionAvailableEnum.ToSuitableGuy.getCode());
		recruitInfoDTO.setInvitePost(InvitePostEnum.NonInvite.getCode());
		recruitInfoDTO.setOtherClaim("更新后其他申明" + timestamp);
		recruitInfoDTO.setPositionStatus(PositionStatusEnum.Offline.getCode());

		int count = recruitInfoService.updateRecruitInfo(recruitInfoDTO);
		System.err.println(count);

		RecruitInfoDTO resultDto = recruitInfoService.queryRecruitInfo(id);
		System.err.println("after: " + resultDto);
	}

	@Test
	public void testDeleteRecruitInfo()
	{
		for (int i = 1; i < 8; i++)
		{
			int count = recruitInfoService.deleteRecruitInfo(i);
			System.err.println(count);
		}
	}

}
