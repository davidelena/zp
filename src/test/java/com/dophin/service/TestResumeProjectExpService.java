package com.dophin.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.ResumeProjectExpDTO;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeProjectExpService extends TestBaseService {
	@Autowired
	private ResumeProjectExpService resumeProjectExpService;

	@Test
	public void testInsertResumeProjectExp() {
		ResumeProjectExpDTO resumeProjectExpDTO = new ResumeProjectExpDTO();
		resumeProjectExpDTO.setResumeId(2);
		resumeProjectExpDTO.setProjectName("项目名称" + timestamp);
		resumeProjectExpDTO.setPositionName("职位名称" + timestamp);
		resumeProjectExpDTO.setStartTime(new Date());
		resumeProjectExpDTO.setEndTime(DateUtils.addDays(new Date(), 1));
		resumeProjectExpDTO.setProjectDesc("项目描述" + timestamp);

		int id = resumeProjectExpService.insertResumeProjectExp(resumeProjectExpDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryResumeProjectExp() {
		int id = 1;
		ResumeProjectExpDTO resumeProjectExpDTO = resumeProjectExpService.queryResumeProjectExp(id);
		System.err.println(resumeProjectExpDTO);
		System.err.println(resumeProjectExpDTO.getResumeInfoDTO());
	}

	@Test
	public void testQueryResumeProjectExps() {
		List<ResumeProjectExpDTO> ls = resumeProjectExpService.queryResumeProjectExps(1, true);
		System.err.println(ls.size());
		for (ResumeProjectExpDTO item : ls) {
			System.err.println(item);
			System.err.println(item.getResumeInfoDTO());
		}
	}

	@Test
	public void testUpdateResumeProjectExp() {
		int id = 6;
		ResumeProjectExpDTO resumeProjectExpDTO = resumeProjectExpService.queryResumeProjectExp(id);
		System.err.println("before: " + resumeProjectExpDTO);

		resumeProjectExpDTO.setResumeId(2);
		resumeProjectExpDTO.setProjectName("更新后项目名称" + timestamp);
		resumeProjectExpDTO.setPositionName("更新后职位名称" + timestamp);
		resumeProjectExpDTO.setStartTime(DateUtils.addDays(new Date(), 2));
		resumeProjectExpDTO.setEndTime(DateUtils.addDays(new Date(), 3));
		resumeProjectExpDTO.setProjectDesc("更新后项目描述" + timestamp);

		int count = resumeProjectExpService.updateResumeProjectExp(resumeProjectExpDTO);
		System.err.println(count);

		ResumeProjectExpDTO result = resumeProjectExpService.queryResumeProjectExp(id);
		System.err.println("after: " + result);

	}

	@Test
	public void testDeleteResumeProjectExp() {
		int count = resumeProjectExpService.deleteResumeProjectExp(6);
		System.err.println(count);
	}

}
