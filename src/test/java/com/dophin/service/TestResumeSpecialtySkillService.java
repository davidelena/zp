package com.dophin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dao.ResumeSpecialtySkillDAO;
import com.dophin.dto.ResumeSpecialtySkillDTO;
import com.dophin.enums.ComputerSkillEnum;
import com.dophin.enums.EnglishSkillEnum;
import com.dophin.enums.OtherLanguageSkillEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeSpecialtySkillService extends TestBaseService {

	@Autowired
	private ResumeSpecialtySkillService resumeSpecialtySkillService;
	
	@Autowired
	private ResumeSpecialtySkillDAO resumeSpecialtySkillDAO;

	@Test
	public void testInsertResumeSpecialtySkill() {

		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
		resumeSpecialtySkillDTO.setResumeId(19);
		resumeSpecialtySkillDTO.setEnLangSkill(String.format("%d-%s,%d-%s,%d-%s", EnglishSkillEnum.CET4.getCode(), "良好",
				EnglishSkillEnum.CambridgeBizMiddle.getCode(), "熟练使用", EnglishSkillEnum.CET6.getCode(), "口语能力"));
		resumeSpecialtySkillDTO.setOtherLangSkill(String.format("%d-%s,%d-%s", OtherLanguageSkillEnum.Arabic.getCode(), "熟练使用",
				OtherLanguageSkillEnum.Japanese.getCode(), "日语一级口语"));
		resumeSpecialtySkillDTO.setComputerSkill(String.format("%d-%s,%d-%s", ComputerSkillEnum.Java.getCode(), "精通", ComputerSkillEnum.CSharp.getCode(),
				"三年经验"));
		resumeSpecialtySkillDTO.setCertificate(String.format("%s,%s", "高级软件工程师考试", "架构师考试"));

		int id = resumeSpecialtySkillService.insertResumeSpecialtySkill(resumeSpecialtySkillDTO);
		System.err.println(id);

	}

	@Test
	public void testQueryResumeSpecialtySkill() {
		int id = 7;
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkill(id);
		System.err.println(resumeSpecialtySkillDTO);
		System.err.println(resumeSpecialtySkillDTO.getEnLangSkillDTOs());
		System.err.println(resumeSpecialtySkillDTO.getOtherLangSkillDTOs());
		System.err.println(resumeSpecialtySkillDTO.getComputerSkillDTOs());
		System.err.println(resumeSpecialtySkillDTO.getCertificateDTOs());
	}

	@Test
	public void testQueryResumeSpecialtySkills() {
		int resumeId = 2;
		ResumeSpecialtySkillDTO info = resumeSpecialtySkillService.queryResumeSpecialtySkillByResumeId(resumeId, true);
		System.err.println(info);
	}

	@Test
	public void testUpdateResumeSpecialtySkill() {
		int id = 6;
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkill(id);

		System.err.println("before: " + resumeSpecialtySkillDTO);
		resumeSpecialtySkillDTO.setResumeId(2);
		resumeSpecialtySkillDTO.setEnLangSkill("更新后英语" + timestamp);
		resumeSpecialtySkillDTO.setOtherLangSkill("更新后其他语种" + timestamp);
		resumeSpecialtySkillDTO.setComputerSkill("更新后计算机技术" + timestamp);
		resumeSpecialtySkillDTO.setCertificate("更新后证书" + timestamp);

		int count = resumeSpecialtySkillService.updateResumeSpecialtySkill(resumeSpecialtySkillDTO);
		System.err.println(count);

		ResumeSpecialtySkillDTO result = resumeSpecialtySkillService.queryResumeSpecialtySkill(id);
		System.err.println(result);
	}

	@Test
	public void testDeleteResumeSpecialtySkill() {
		int count = resumeSpecialtySkillService.deleteResumeSpecialtySkill(2);
		System.err.println(count);
	}

}
