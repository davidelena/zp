package com.dophin.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.ResumeOpusInfoDTO;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeOpusInfoService extends TestBaseService {

	@Autowired
	private ResumeOpusInfoService resumeOpusInfoService;

	@Test
	public void testInsertResumOpusInfo() {
		ResumeOpusInfoDTO resumeOpusInfoDTO = new ResumeOpusInfoDTO();
		resumeOpusInfoDTO.setResumeId(2);
		resumeOpusInfoDTO.setOpusName("作品名称" + timestamp);
		resumeOpusInfoDTO.setOpusPath("作品路径" + timestamp);
		resumeOpusInfoDTO.setOpusUrl("作品URL" + timestamp);

		int id = resumeOpusInfoService.insertResumeOpusInfo(resumeOpusInfoDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryResumOpusInfo() {
		int id = 1;
		ResumeOpusInfoDTO resumeOpusInfoDTO = resumeOpusInfoService.queryResumeOpusInfo(id);
		System.err.println(resumeOpusInfoDTO);
		System.err.println(resumeOpusInfoDTO.getResumeInfoDTO());
	}

	@Test
	public void testQueryResumOpusInfos() {
		List<ResumeOpusInfoDTO> ls = resumeOpusInfoService.queryResumeOpusInfos(2, true);
		for (ResumeOpusInfoDTO item : ls) {
			System.err.println(item);
			System.err.println(item.getResumeInfoDTO());
		}
	}

	@Test
	public void testUpdateResumOpusInfo() {
		int id = 6;
		ResumeOpusInfoDTO resumeOpusInfoDTO = resumeOpusInfoService.queryResumeOpusInfo(id);
		System.err.println("before: " + resumeOpusInfoDTO);
		resumeOpusInfoDTO.setResumeId(2);
		resumeOpusInfoDTO.setOpusName("更新后作品名称" + timestamp);
		resumeOpusInfoDTO.setOpusPath("更新后作品路径" + timestamp);
		resumeOpusInfoDTO.setOpusUrl("更新后作品URL" + timestamp);

		int count = resumeOpusInfoService.updateResumeOpusInfo(resumeOpusInfoDTO);
		System.err.println(count);

		ResumeOpusInfoDTO result = resumeOpusInfoService.queryResumeOpusInfo(id);
		System.err.println("after: " + result);
	}

	@Test
	public void testDeleteResumOpusInfo() {
		int count = resumeOpusInfoService.deleteResumeOpusInfo(6);
		System.err.println(count);
	}

}
