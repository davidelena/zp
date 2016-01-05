package com.dophin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.ResumeEducationExpDTO;
import com.dophin.enums.AchievementEnum;
import com.dophin.enums.EducationEnum;
import com.dophin.enums.SpecialityEnum;
import com.dophin.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeEducationExpService extends TestBaseService {

	@Autowired
	private ResumeEducationExpService resumeEducationExpService;

	@Test
	public void testQueryResumeIdByMajorType() {
		String resumeIds = "1,2";
		int majorType = 2;
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("resumeIds", resumeIds);
		queryMap.put("majorType", majorType);
		List<Integer> ls = resumeEducationExpService.queryResumeIdByMajorType(queryMap);
		for (Integer item : ls) {
			System.err.println(item);
		}
	}

	@Test
	public void testInsertResumeEducationExp() {
		ResumeEducationExpDTO resumeEducationExpDTO = new ResumeEducationExpDTO();
		resumeEducationExpDTO.setResumeId(19);
		resumeEducationExpDTO.setDiploma(EducationEnum.Specialty.getCode());
		resumeEducationExpDTO.setSchool("学校" + timestamp);
		resumeEducationExpDTO.setMajor("专业" + System.currentTimeMillis());
		resumeEducationExpDTO.setMajorType(SpecialityEnum.Education.getCode());
		resumeEducationExpDTO.setScoreTop(AchievementEnum.Percentage10.getCode());
		resumeEducationExpDTO.setAcademicStarts(new Date());
		resumeEducationExpDTO.setGraduationTime(DateUtils.addDays(new Date(), 1));

		int id = resumeEducationExpService.insertResumeEducationExp(resumeEducationExpDTO);
		System.err.println(id);
	}

	@Test
	public void testUpdateResumeEducationExp() {
		ResumeEducationExpDTO resumeEducationExpDTO = resumeEducationExpService.queryResumeEducationExp(13);
		System.err.println("before: " + resumeEducationExpDTO);
		resumeEducationExpDTO.setSchool("更新学校" + timestamp);
		resumeEducationExpDTO.setDiploma(EducationEnum.Master.getCode());
		resumeEducationExpDTO.setMajorType(SpecialityEnum.Economics.getCode());
		resumeEducationExpDTO.setScoreTop(AchievementEnum.Percentage5.getCode());

		int count = resumeEducationExpService.updateResumeEducationExp(resumeEducationExpDTO);
		System.err.println(count);
		ResumeEducationExpDTO resultDto = resumeEducationExpService.queryResumeEducationExp(13);
		System.err.println("after: " + resultDto);
		System.err.println(resultDto.getSchool());
	}

	@Test
	public void testDeleteResumeEducationExp() {
		// int count = resumeEducationExpService.deleteResumeEducationExp(7);

		for (int i = 1; i <= 9; i++) {
			DeleteResponse response = elasticSearchUtils.doDeleteAction(CommonUtils.ES_INDEX, "resumeeducationexp", i);
		}
		// System.err.println(count);
	}

	@Test
	public void testInsertEs() throws JsonProcessingException {
		List<ResumeEducationExpDTO> ls = resumeEducationExpService.queryResumeEducationExps(6);
		for (ResumeEducationExpDTO item : ls) {
			String result = mapper.writeValueAsString(item);
			elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, "resumeeducationexp", item.getId(), result);
		}
	}

	@Test
	public void testQueryResumeEducationExp() {
		ResumeEducationExpDTO resumeEducationExpDTO = resumeEducationExpService.queryResumeEducationExp(2);
		System.err.println(resumeEducationExpDTO);
		System.err.println(resumeEducationExpDTO.getSchool());
		System.err.println(resumeEducationExpDTO.getResumeInfoDTO());
		System.err.println(resumeEducationExpDTO.getDiplomaDesc());
		System.err.println(resumeEducationExpDTO.getMajorTypeDesc());
		System.err.println(resumeEducationExpDTO.getScoreTopDesc());
	}

	@Test
	public void testQueryResumeEducationExps() {
		List<ResumeEducationExpDTO> resumeEducationExpDTOs = resumeEducationExpService.queryResumeEducationExps(1, true);
		for (ResumeEducationExpDTO resumeEducationExpDTO : resumeEducationExpDTOs) {
			System.err.println(resumeEducationExpDTO);
			System.err.println(resumeEducationExpDTO.getResumeInfoDTO());
		}
	}

}
