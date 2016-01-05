package com.dophin.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.CompanyInfoDTO;
import com.dophin.enums.CompanySizeEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCompanyInfoService extends TestBaseService {
	@Autowired
	private CompanyMemberService companyMemberService;

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertCampanyInfo() {
		CompanyInfoDTO companyInfoDTO = new CompanyInfoDTO();
		companyInfoDTO.setMemberId(String.valueOf(timestamp));
		companyInfoDTO.setName("企业名称" + timestamp);
		companyInfoDTO.setIndustry(8);
		companyInfoDTO.setHeaderQuartersId(2600);
		companyInfoDTO.setHeaderQuarters("总部城市" + timestamp);
		companyInfoDTO.setDetailAddress("具体地址：" + timestamp);
		companyInfoDTO.setLogo("Logo路径" + timestamp);
		companyInfoDTO.setOfficialWebsite("http://www.google.com" + timestamp);
		companyInfoDTO.setScale(CompanySizeEnum.Size501To1000.getCode());
		companyInfoDTO.setSynopsis("公司简介" + timestamp);
		companyInfoDTO.setProduct("公司产品" + timestamp);
		companyInfoDTO.setAchievements("公司主要成就" + timestamp);
		companyInfoDTO.setWechat("公司微信" + timestamp);
		companyInfoDTO.setWeibo("公司微博" + timestamp);
		companyInfoDTO.setSeniorExecutiveDesc("公司高管简介" + timestamp);
		companyInfoDTO.setStatus(1);

		int id = companyMemberService.insertCompanyInfo(companyInfoDTO);
		System.err.println(id);
	}

	@Test
	public void testDeleteCampanyInfo() {
		int id = 1;
		String memberId = "1443803318506";
		int count1 = companyMemberService.deleteCompanyInfo(id);
		int count2 = companyMemberService.deleteCompanyInfo(memberId);
		System.err.println(count1);
		System.err.println(count2);
	}

	@Test
	public void testQueryCampanyInfo() {
		int id = 7;
		String memberId = "1445836364841";
		CompanyInfoDTO info1 = companyMemberService.queryCompanyInfo(id);
		CompanyInfoDTO info2 = companyMemberService.queryCompanyInfo(memberId);
		System.err.println(info1);
		System.err.println(info2);
	}

	@Test
	public void testUpdateCampanyInfo() {
		// String memberId = "1445269174208";
		// CompanyInfoDTO originalInfo =
		// companyMemberService.queryCompanyInfo(memberId);
		CompanyInfoDTO originalInfo = companyMemberService.queryCompanyInfo(7);
		System.err.println("originalInfo: " + originalInfo);
		originalInfo.setIndustry(16);
		originalInfo.setScale(2);
		originalInfo.setHeaderQuartersId(300);
		originalInfo.setDetailAddress("更新后地址："+timestamp);
		int count = companyMemberService.updateCompanyInfo(originalInfo);
		System.err.println("affect: " + count);
		CompanyInfoDTO resultInfo = companyMemberService.queryCompanyInfo(7);
		System.err.println(resultInfo);
	}

}
