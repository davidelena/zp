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

import com.dophin.dto.ResumeActivityExpDTO;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeActivityExpService extends TestBaseService {

	@Autowired
	private ResumeActivityExpService resumeActivityExpService;

	@Test
	public void testInsertResumeActivityExp() {

		ResumeActivityExpDTO resumeActivityExpDTO = new ResumeActivityExpDTO();
		resumeActivityExpDTO.setResumeId(2);
		resumeActivityExpDTO.setActivityName("活动名称" + timestamp);
		resumeActivityExpDTO.setPositionName("职位名称" + timestamp);
		resumeActivityExpDTO.setStartTime(new Date());
		resumeActivityExpDTO.setEndTime(DateUtils.addDays(new Date(), 1));
		resumeActivityExpDTO.setActivityDesc("活动描述" + timestamp);

		int id = resumeActivityExpService.insertResumeActivityExp(resumeActivityExpDTO);
		System.err.println(id);

	}

	@Test
	public void testQueryResumeActivityExp() {
		int id = 1;
		ResumeActivityExpDTO resumeActivityExpDTO = resumeActivityExpService.queryResumeActivityExp(id);
		System.err.println(resumeActivityExpDTO);
	}

	@Test
	public void testQueryResumeActivityExps() {
		int resumeId = 1;
		List<ResumeActivityExpDTO> ls = resumeActivityExpService.queryResumeActivityExps(resumeId, true);
		System.err.println(ls.size());
		for (ResumeActivityExpDTO item : ls) {
			System.err.println(item);
			System.err.println(item.getResumeInfoDTO());
		}
	}

	@Test
	public void testUpdateResumeActivityExp() {
		int id = 4;
		ResumeActivityExpDTO resumeActivityExpDTO = resumeActivityExpService.queryResumeActivityExp(id);
		System.err.println("before: " + resumeActivityExpDTO);
		resumeActivityExpDTO.setResumeId(2);
		resumeActivityExpDTO.setActivityName("更新后活动名称" + timestamp);
		resumeActivityExpDTO.setPositionName("更新后职位名称" + timestamp);
		resumeActivityExpDTO.setStartTime(DateUtils.addDays(new Date(), 2));
		resumeActivityExpDTO.setEndTime(DateUtils.addDays(new Date(), 3));
		resumeActivityExpDTO.setActivityDesc("更新后活动描述" + timestamp);

		int count = resumeActivityExpService.updateResumeActivityExp(resumeActivityExpDTO);
		System.err.println(count);

		ResumeActivityExpDTO result = resumeActivityExpService.queryResumeActivityExp(id);
		System.err.println(result);
	}

	@Test
	public void testDeleteResumeActivityExp() {
		int count = resumeActivityExpService.deleteResumeActivityExp(4);
		System.err.println(count);
	}

}
