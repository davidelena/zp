package com.dophin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.MemberDTO;
import com.dophin.enums.RecruitDemandEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCompanyMember extends TestBaseService 
{
	@Autowired
	private MemberService memberService;

	@Test
	public void testInsertCompanyMember()
	{
		MemberDTO memberDTO = new MemberDTO();
		long timestamp = System.currentTimeMillis();
		memberDTO.setName("公司个人账号" + timestamp);
		memberDTO.setEmail("公司接收简历邮箱" + timestamp);
		memberDTO.setMobile("13661896754");
		memberDTO.setTelephone("54111111");
		memberDTO.setPosition("高级开发总监" + timestamp);
		memberDTO.setDemand(RecruitDemandEnum.ContactMe.getCode());

		String memberId = memberService.insertMemberInfo(memberDTO);
		System.err.println(memberId);
	}

	@Test
	public void testUpdateCompanyMemberInfo()
	{
		long timestamp = System.currentTimeMillis();
		String memberId = "1443881807232";
		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		System.err.println("original obj: " + memberDTO);
		memberDTO.setDemand(RecruitDemandEnum.NoneContact.getCode());
		memberDTO.setPosition("高级研发总监" + timestamp);
		int count = memberService.updateMemberInfo(memberDTO);
		System.err.println("count： " + count);
		MemberDTO result = memberService.queryMemberInfo(memberId);
		System.err.println(result);
	}

	@Test
	public void testQueryCompanyMemberByMemberId()
	{
		String memberId = "1443881807232";
		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		System.err.println(memberDTO);
	}

	@Test
	public void testDeleteCompanyMember()
	{
		String memberId = "1443881807232";
		int count = memberService.deleteMemberInfo(memberId);
		System.err.println(count);
	}

}
