package com.dophin.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.ResumeCustomDTO;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeCustomService extends TestBaseService {

	@Autowired
	private ResumeCustomService resumeCustomService;

	@Test
	public void testInsertResumeCustom() {

		ResumeCustomDTO resumeCustomDTO = new ResumeCustomDTO();
		resumeCustomDTO.setResumeId(2);
		resumeCustomDTO.setName("自定义板块名称" + timestamp);
		resumeCustomDTO.setDescription("自定义板块说明" + timestamp);

		int id = resumeCustomService.insertResumeCustom(resumeCustomDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryResumeCustom() {
		int id = 1;
		ResumeCustomDTO resumeCustomDTO = resumeCustomService.queryResumeCustom(id);
		System.err.println(resumeCustomDTO);
		System.err.println(resumeCustomDTO.getResumeInfoDTO());
	}

	@Test
	public void testQueryResumeCustoms() {
		List<ResumeCustomDTO> ls = resumeCustomService.queryResumeCustoms(1, true);
		for (ResumeCustomDTO item : ls) {
			System.err.println(item);
			System.err.println(item.getResumeInfoDTO());
		}
	}

	@Test
	public void testUpdateResumeCustom() {
		ResumeCustomDTO resumeCustomDTO = resumeCustomService.queryResumeCustom(4);
		System.err.println("before: " + resumeCustomDTO);

		resumeCustomDTO.setResumeId(2);
		resumeCustomDTO.setName("更新后自定义板块名称" + timestamp);
		resumeCustomDTO.setDescription("更新后自定义板块说明" + timestamp);

		int count = resumeCustomService.updateResumeCustom(resumeCustomDTO);
		System.err.println(count);

		ResumeCustomDTO result = resumeCustomService.queryResumeCustom(1);
		System.err.println("after: " + result);
	}

	@Test
	public void testDeleteResumeCustom() {
		int count = resumeCustomService.deleteResumeCustom(4);
		System.err.println(count);
	}

}
