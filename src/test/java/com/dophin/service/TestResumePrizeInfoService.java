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

import com.dophin.dto.ResumePrizeInfoDTO;
import com.dophin.enums.RewardLevelEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumePrizeInfoService extends TestBaseService {

	@Autowired
	private ResumePrizeInfoService resumePrizeInfoService;

	@Test
	public void testInsertResumePrizeInfo() {

		ResumePrizeInfoDTO resumePrizeInfoDTO = new ResumePrizeInfoDTO();
		resumePrizeInfoDTO.setResumeId(2);
		resumePrizeInfoDTO.setPrizeName("奖项名称" + timestamp);
		resumePrizeInfoDTO.setPrizeLevel(RewardLevelEnum.SchoolLevel.getCode());
		resumePrizeInfoDTO.setGainTime(new Date());

		int id = resumePrizeInfoService.insertResumePrizeInfo(resumePrizeInfoDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryResumePrizeInfo() {
		int id = 2;
		ResumePrizeInfoDTO resumePrizeInfoDTO = resumePrizeInfoService.queryResumePrizeInfo(id);
		System.err.println(resumePrizeInfoDTO);
		System.err.println(resumePrizeInfoDTO.getResumeInfoDTO());
	}

	@Test
	public void testQueryResumePrizeInfos() {
		List<ResumePrizeInfoDTO> ls = resumePrizeInfoService.queryResumePrizeInfos(2, true);
		for (ResumePrizeInfoDTO item : ls) {
			System.err.println(item);
			System.err.println(item.getResumeInfoDTO());
		}
	}

	@Test
	public void testUpdateResumePrizeInfo() {
		int id = 6;
		ResumePrizeInfoDTO resumePrizeInfoDTO = resumePrizeInfoService.queryResumePrizeInfo(id);
		System.err.println("before: " + resumePrizeInfoDTO);
		resumePrizeInfoDTO.setResumeId(2);
		resumePrizeInfoDTO.setPrizeName("更新后奖项名称" + timestamp);
		resumePrizeInfoDTO.setPrizeLevel(RewardLevelEnum.CityLevel.getCode());
		resumePrizeInfoDTO.setGainTime(DateUtils.addDays(new Date(), 1));

		int count = resumePrizeInfoService.updateResumePrizeInfo(resumePrizeInfoDTO);
		System.err.println(count);

		ResumePrizeInfoDTO result = resumePrizeInfoService.queryResumePrizeInfo(id);
		System.err.println("after: " + result);
	}

	@Test
	public void testDeleteResumePrizeInfo() {
		int count = resumePrizeInfoService.deleteResumePrizeInfo(6);
		System.err.println(count);
	}

}
