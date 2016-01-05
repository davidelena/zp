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

import com.dophin.dto.ResumeWorkExpDTO;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeWorkExp extends TestBaseService {

	@Autowired
	private ResumeWorkExpService resumeWorkExpService;

	@Test
	public void testInsertResumeWorkExp() {
		ResumeWorkExpDTO resumeWorkExpDTO = new ResumeWorkExpDTO();
		resumeWorkExpDTO.setResumeId(7);
		resumeWorkExpDTO.setCompanyName("公司名称" + timestamp);
		resumeWorkExpDTO.setPositionName("职位名称" + timestamp);
		resumeWorkExpDTO.setStartTime(new Date());
		resumeWorkExpDTO.setEndTime(DateUtils.addDays(new Date(), 1));
		resumeWorkExpDTO.setWorkDescription("工作描述" + timestamp);

		int id = resumeWorkExpService.insertResumeWorkExp(resumeWorkExpDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryResumeWorkExp() {
		int id = 1;
		ResumeWorkExpDTO resumeWorkExpDTO = resumeWorkExpService.queryResumeWorkExp(id);
		System.err.println(resumeWorkExpDTO);
		System.err.println(resumeWorkExpDTO.getResumeInfoDTO());
	}

	@Test
	public void testQueryResumeWorkExps() {
		List<ResumeWorkExpDTO> ls = resumeWorkExpService.queryResumeWorkExps(1, false);
		System.err.println(ls.size());
		for (ResumeWorkExpDTO item : ls) {
			System.err.println(item);
			System.err.println(item.getResumeInfoDTO());
		}
	}

	@Test
	public void testUpdateResumeWorkExp() {
		int id = 6;
		ResumeWorkExpDTO resumeWorkExpDTO = resumeWorkExpService.queryResumeWorkExp(id);
		System.err.println("before: " + resumeWorkExpDTO);

		resumeWorkExpDTO.setResumeId(2);
		resumeWorkExpDTO.setCompanyName("更新后公司名称" + timestamp);
		resumeWorkExpDTO.setPositionName("更新后职位名称" + timestamp);
		resumeWorkExpDTO.setStartTime(DateUtils.addDays(new Date(), 2));
		resumeWorkExpDTO.setEndTime(DateUtils.addDays(new Date(), 3));
		resumeWorkExpDTO.setWorkDescription("更新后工作描述" + timestamp);

		int count = resumeWorkExpService.updateResumeWorkExp(resumeWorkExpDTO);
		System.err.println(count);

		ResumeWorkExpDTO result = resumeWorkExpService.queryResumeWorkExp(id);
		System.err.println("after: " + result);

	}

	@Test
	public void testDeleteResumeWorkExp() {
		int count = resumeWorkExpService.deleteResumeWorkExp(5);
		System.err.println(count);
	}

}
