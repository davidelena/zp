package com.dophin.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.ResumeHobbySpecialDTO;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeHobbySpecialService extends TestBaseService {
	@Autowired
	private ResumeHobbySpecialService resumeHobbySpecialService;

	@Test
	public void testInsertResumeHobbySpecial() {
		ResumeHobbySpecialDTO resumeHobbySpecialDTO = new ResumeHobbySpecialDTO();
		resumeHobbySpecialDTO.setResumeId(2);
		resumeHobbySpecialDTO.setSpecialName("爱好特长名称" + timestamp);
		resumeHobbySpecialDTO.setSpecialDesc("爱好特长描述" + timestamp);

		int id = resumeHobbySpecialService.insertResumeHobbySpecial(resumeHobbySpecialDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryResumeHobbySpecial() {
		int id = 6;
		ResumeHobbySpecialDTO resumeHobbySpecialDTO = resumeHobbySpecialService.queryResumeHobbySpecial(id);
		System.err.println(resumeHobbySpecialDTO);
	}

	@Test
	public void testQueryResumeHobbySpecials() {
		List<ResumeHobbySpecialDTO> ls = resumeHobbySpecialService.queryResumeHobbySpecials(1,true);
		for (ResumeHobbySpecialDTO resumeHobbySpecialDTO : ls) {
			System.err.println(resumeHobbySpecialDTO);
			System.err.println(resumeHobbySpecialDTO.getResumeInfoDTO());
		}
	}

	@Test
	public void testUpdateResumeHobbySpecial() {
		int id = 7;
		ResumeHobbySpecialDTO resumeHobbySpecialDTO = resumeHobbySpecialService.queryResumeHobbySpecial(id);
		System.err.println("before: " + resumeHobbySpecialDTO);

		resumeHobbySpecialDTO.setResumeId(2);
		resumeHobbySpecialDTO.setSpecialName("更新后爱好特长名称" + timestamp);
		resumeHobbySpecialDTO.setSpecialDesc("更新后爱好特长描述" + timestamp);

		int count = resumeHobbySpecialService.updateResumeHobbySpecial(resumeHobbySpecialDTO);
		System.err.println(count);

		ResumeHobbySpecialDTO result = resumeHobbySpecialService.queryResumeHobbySpecial(id);
		System.err.println("after: " + result);

	}

	@Test
	public void testDeleteResumeHobbySpecial() {
		int count = resumeHobbySpecialService.deleteResumeHobbySpecial(7);
		System.err.println(count);
	}
}
