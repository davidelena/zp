package com.dophin.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.ResumeActivityExpDTO;
import com.dophin.dto.ResumeCustomDTO;
import com.dophin.dto.ResumeEducationExpDTO;
import com.dophin.dto.ResumeHobbySpecialDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeOpusInfoDTO;
import com.dophin.dto.ResumePrizeInfoDTO;
import com.dophin.dto.ResumeProjectExpDTO;
import com.dophin.dto.ResumeSocialNetDTO;
import com.dophin.dto.ResumeWorkExpDTO;
import com.dophin.enums.EducationEnum;
import com.dophin.enums.GenderEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeInfoService extends TestBaseService
{

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Test
	public void testInsertResumeInfo()
	{
		String memberId = "1445836364841";// 1441704227484
		ResumeInfoDTO resumeInfoDTO = new ResumeInfoDTO();
		resumeInfoDTO.setMemberId(memberId);
		resumeInfoDTO.setResumeName("简历测试名称" + timestamp);
		resumeInfoDTO.setAvatar("avatar" + timestamp);
		resumeInfoDTO.setSex(GenderEnum.Male.getCode());
		resumeInfoDTO.setMajor("专业" + timestamp);
		resumeInfoDTO.setDiploma(EducationEnum.Master.getCode());
		resumeInfoDTO.setGraduationTime(new Date());
		resumeInfoDTO.setUploadPath("上传路径" + timestamp);

		int id = resumeInfoService.insertResumeInfo(resumeInfoDTO);
		System.err.println("newId: " + id);
	}

	@Test
	public void testUpdateResumeInfo()
	{
		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(5);

		System.err.println("更新前：" + resumeInfoDTO);

		resumeInfoDTO.setMemberId("1443881807232");
		resumeInfoDTO.setAvatar("更新后" + timestamp);
		resumeInfoDTO.setSex(GenderEnum.Female.getCode());
		resumeInfoDTO.setResumeName("更新后的简历名称" + timestamp);
		resumeInfoDTO.setDiploma(EducationEnum.Undergraduate.getCode());
		resumeInfoDTO.setUploadPath("更新后的上传路径：" + timestamp);
		Calendar c = Calendar.getInstance();
		c.set(2012, 9, 10, 0, 0, 0);
		resumeInfoDTO.setGraduationTime(c.getTime());

		int count = resumeInfoService.updateResumeInfo(resumeInfoDTO);
		System.err.println(count);

		ResumeInfoDTO result = resumeInfoService.queryResumeInfo(5);
		System.err.println("更新后：" + result);
	}

	@Test
	public void testDeleteResumeInfo()
	{
		int id = 5;
		int count = resumeInfoService.deleteResumeInfo(id);
		System.err.println(count);
	}

	@Test
	public void testQueryResumeInfo()
	{

		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(3);
		System.err.println(resumeInfoDTO);
		System.err.println(resumeInfoDTO.getMemberDTO());
		System.err.println(resumeInfoDTO.getMemberDTO().getUniversityDTO());

		System.err.println("ResumeEducationExp: ");
		System.err.println(resumeInfoDTO.getResumeEducationExpDTOs().size());
		for (ResumeEducationExpDTO resumeEducationExpDTO : resumeInfoDTO.getResumeEducationExpDTOs())
		{
			System.err.println(resumeEducationExpDTO);
		}

		System.err.println("ResumeWorkExp: ");
		System.err.println(resumeInfoDTO.getResumeWorkExpDTOs().size());
		for (ResumeWorkExpDTO resumeWorkExpDTO : resumeInfoDTO.getResumeWorkExpDTOs())
		{
			System.err.println(resumeWorkExpDTO);
		}

		System.err.println("ResumeSpecialtySkill: ");
		System.err.println(resumeInfoDTO.getResumeSpecialtySkillDTO());

		System.err.println("ResumeActivityExp: ");
		System.err.println(resumeInfoDTO.getResumeActivityExpDTOs().size());
		for (ResumeActivityExpDTO resumeActivityExpDTO : resumeInfoDTO.getResumeActivityExpDTOs())
		{
			System.err.println(resumeActivityExpDTO);
		}

		System.err.println("ResumePrizeInfo: ");
		System.err.println(resumeInfoDTO.getResumePrizeInfoDTOs().size());
		for (ResumePrizeInfoDTO resumePrizeInfoDTO : resumeInfoDTO.getResumePrizeInfoDTOs())
		{
			System.err.println(resumePrizeInfoDTO);
		}

		System.err.println("ResumeProjectExp: ");
		System.err.println(resumeInfoDTO.getResumeProjectExpDTOs().size());
		for (ResumeProjectExpDTO resumeProjectExpDTO : resumeInfoDTO.getResumeProjectExpDTOs())
		{
			System.err.println(resumeProjectExpDTO);
		}

		System.err.println("ResumeHobbySpecial: ");
		System.err.println(resumeInfoDTO.getResumeHobbySpecialDTOs().size());
		for (ResumeHobbySpecialDTO resumeHobbySpecialDTO : resumeInfoDTO.getResumeHobbySpecialDTOs())
		{
			System.err.println(resumeHobbySpecialDTO);
		}

		System.err.println("ResumeSocialNet: ");
		System.err.println(resumeInfoDTO.getResumeSocialNetDTOs().size());
		for (ResumeSocialNetDTO resumeSocialNetDTO : resumeInfoDTO.getResumeSocialNetDTOs())
		{
			System.err.println(resumeSocialNetDTO);
		}

		System.err.println("ResumeOpusInfo: ");
		System.err.println(resumeInfoDTO.getResumeOpusInfoDTOs().size());
		for (ResumeOpusInfoDTO resumeOpusInfoDTO : resumeInfoDTO.getResumeOpusInfoDTOs())
		{
			System.err.println(resumeOpusInfoDTO);
		}

		System.err.println("ResumeCustom: ");
		System.err.println(resumeInfoDTO.getResumeCustomDTOs().size());
		for (ResumeCustomDTO resumeCustomDTO : resumeInfoDTO.getResumeCustomDTOs())
		{
			System.err.println(resumeCustomDTO);
		}
	}

	@Test
	public void testQueryResumeInfos()
	{
		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos("1441704227484");
		System.err.println(resumeInfoDTOs.size());
		for (ResumeInfoDTO resumeInfoDTO : resumeInfoDTOs)
		{
			System.err.println(resumeInfoDTO);
		}
	}

	@Test
	public void testQueryResumeInfos2()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("ids", "1,2,4");
		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos(queryMap);
		System.err.println(resumeInfoDTOs.size());
		for (ResumeInfoDTO resumeInfoDTO : resumeInfoDTOs)
		{
			resumeInfoDTO.setStatus(1);
			resumeInfoService.updateResumeInfo(resumeInfoDTO);
		}
	}

	@Test
	public void testQueryResumeInfosWithNoArgs()
	{
		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos();
		System.err.println(resumeInfoDTOs.size());
		for (ResumeInfoDTO resumeInfoDTO : resumeInfoDTOs)
		{
			System.err.println(resumeInfoDTO);
		}
	}

}
