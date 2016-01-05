package com.dophin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.MemberDTO;
import com.dophin.enums.GenderEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMemberService extends TestBaseService {

	@Autowired
	private MemberService memberService;

	private MemberDTO getMemberDTO() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setName("测试名称" + timestamp);
		memberDTO.setEmail("274714290@qq.com" + timestamp);
		memberDTO.setCommonEmail("274714290@qq.com" + timestamp);
		memberDTO.setPassword(DigestUtils.md5Hex("123456"));
		memberDTO.setMobile(String.valueOf(timestamp));
		memberDTO.setSource(1);
		memberDTO.setSchoolId(6);
		memberDTO.setIsVerifyEmail(1);
		memberDTO.setIsVerifyCellphone(1);
		memberDTO.setAreaCode("SH" + +timestamp);
		memberDTO.setTelephone("541112345");
		memberDTO.setDemand(0);
		memberDTO.setExtension("Extension" + timestamp);
		memberDTO.setCreateTime(new Date());
		memberDTO.setUpdateTime(DateUtils.addDays(new Date(), 1));
		memberDTO.setCompanyEmailSuffix("qq.com");
		memberDTO.setHasFillBasic(true);
		memberDTO.setSex(GenderEnum.Male.getCode());
		memberDTO.setStatus(1);

		return memberDTO;
	}

	@Test
	public void testInsertMemberInfo() {
		MemberDTO memberDTO = getMemberDTO();
		String id = memberService.insertMemberInfo(memberDTO);
		System.err.println("newId: " + id);
	}

	@Test
	public void testUpdateMemberInfo() {
		String memberId = "1441704227484";
		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		System.err.println("before: " + memberDTO);
		memberDTO.setName("学生用户测试2");
		memberDTO.setCompanyId(4);
		memberDTO.setSchoolId(776);
		memberDTO.setStudentId(1);
		memberDTO.setCompanyEmailSuffix("hupu.com");
		memberDTO.setHasFillBasic(false);
		memberDTO.setCommonEmail("daivddai@163.com");
		memberDTO.setSex(GenderEnum.Male.getCode());
		memberService.updateMemberInfo(memberDTO);
		MemberDTO afterMemberDTO = memberService.queryMemberInfo(memberId);
		System.err.println("after: " + afterMemberDTO);
		System.err.println(afterMemberDTO.getSex());
	}

	@Test
	public void testDeleteMemberInfo() {
		String memberId = "1445829071517";
		int count = memberService.deleteMemberInfo(memberId);
		System.err.println("count: " + count);
		MemberDTO resultDto = memberService.queryMemberInfo(memberId);
		System.err.println("resultDto: " + resultDto);
	}

	@Test
	public void testQueryMemberDTO() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", "1449683600127");
		MemberDTO result = memberService.queryMemberInfo(queryMap);
		System.err.println(result);
		System.err.println(result.getUniversityDTO());
		System.err.println(result.getCompanyInfoDTO());
		System.err.println(result.getStudentInfoDTO());
		System.err.println(result.getCompanyEmailSuffix());
		System.err.println(result.isHasFillBasic());
		System.err.println(result.getCommonEmail());
		System.err.println(result.getSex());
		System.err.println(result.getSexDesc());
		System.err.println(result.isFirst());
	}

	@Test
	public void testIsMemberExists() {
		String memberId = "1444663997999";
		boolean result = memberService.isMemberExists(memberId, 1);
		System.err.println("isExists: " + result);
	}

	@Test
	public void testIsFirstCompanyMember() {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyEmailSuffix", "hupu.com");
		queryMap.put("source", 2);
		boolean result = memberService.isMemberExists(queryMap);
		System.err.println(result);
	}

	@Test
	public void testQueryBySuffix() {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("companyEmailSuffix", "hupu.com");
		queryMap.put("source", 2);
		List<MemberDTO> ls = memberService.queryCompanyIdBySuffix(queryMap);
		System.err.println(ls.get(0).getCompanyId());
	}

	@Test
	public void testIsMemberExistsByEmail() {
		String email = "274714290@qq.com";
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("email", email);
		queryMap.put("source", 2);
		boolean result = memberService.isMemberExists(queryMap);
		System.err.println("isExistsByEmail: " + result);
	}

	@Test
	public void testIsMemberExistsByCompanyEmailSuffix() {
		String suffix = "hupu1.com";
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("companyEmailSuffix", suffix);
		queryMap.put("source", 1);
		boolean result = memberService.isMemberExists(queryMap);
		System.err.println("isExists: " + result);
	}
}
