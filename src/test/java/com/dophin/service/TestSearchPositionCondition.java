package com.dophin.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.FilterRecruitInfoCondition;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.ApplyProgressEnum;
import com.dophin.enums.InternshipDaysEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.SalaryLevelEnum;
import com.dophin.service.biz.SearchRecuritInfoService;
import com.dophin.service.impl.SearchRecruitInfoServiceFulltimeImpl;
import com.dophin.service.impl.SearchRecruitInfoServiceInternshipImpl;

/**
 * 搜索全职或者兼职学生职位
 * 
 * @author David.dai
 * 
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSearchPositionCondition {

	/**
	 * 测试全职工作
	 */
	@Test
	public void testSearchRecuritInfoService() {
		FilterRecruitInfoCondition condition = new FilterRecruitInfoCondition();
		SearchRecuritInfoService service = new SearchRecruitInfoServiceFulltimeImpl();
		// condition.setApplyProgress(ApplyProgressEnum.In3Days.getCode());
		// condition.setIndustries("72");
		condition.setAddressCities("712,2600,3405");
		// condition.setMajor(4);
		// condition.setDiploma(PositionEducationEnum.UndergraduatePlus.getCode());
		// condition.setDemandSalary(SalaryLevelEnum.S10000To20000.getCode());
		// condition.setMyFavorite(true);
		// condition.setSearchTxt("数据挖掘");
		// condition.setFrom(1);
		// condition.setSize(1);

		List<RecruitInfoDTO> ls = service.searchRecruitInfoWithConditions(condition);
		System.err.println("size：" + ls.size());
		System.err.println("total: " + condition.getTotal());
		for (RecruitInfoDTO item : ls) {
			System.err.println("=======================");
			System.err.println(item);
			System.err.println(item.getValidityTimeDesc());
			System.err.println(item.getWorkCityDTO().getCityID());
			System.err.println(item.getWorkCityDTO().getCity());
		}
	}

	/**
	 * 学生搜索实习职位
	 */
	@Test
	public void testSearchRecuritInfoServiceInternship() {
		FilterRecruitInfoCondition condition = new FilterRecruitInfoCondition();
		SearchRecuritInfoService service = new SearchRecruitInfoServiceInternshipImpl();
		// condition.setInternshipDays(InternshipDaysEnum.Day3.getCode());
		// condition.setIndustries("72");
		// condition.setAddressCities("400");
		// condition.setDiploma(PositionEducationEnum.UndergraduatePlus.getCode());
		// condition.setDemandSalary(SalaryLevelEnum.S5000To8000.getCode());

		// condition.setMyFavorite(true);
		// condition.setSearchTxt("有限公司");
		// condition.setFrom(1);
		// condition.setSize(1);

		List<RecruitInfoDTO> ls = service.searchRecruitInfoWithConditions(condition);
		System.err.println(ls.size());
		for (RecruitInfoDTO item : ls) {
			System.err.println("====================");
			System.err.println(item);
			System.err.println(item.getValidityTimeDesc());
		}
	}
}
