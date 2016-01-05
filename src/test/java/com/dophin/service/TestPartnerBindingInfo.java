package com.dophin.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.PartnerBindingDTO;
import com.dophin.enums.PartnerTypeEnum;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPartnerBindingInfo extends TestBaseService {

	private int id = 5;
	private String memberId = "1444270188141";

	@Autowired
	private PartnerBindingService partnerBindingService;

	@Test
	public void testInsertPartnerBindingInfo() {
		PartnerBindingDTO partnerBindingDTO = new PartnerBindingDTO();
		partnerBindingDTO.setMemberId(String.valueOf(System.currentTimeMillis()));
		partnerBindingDTO.setAccessToken(DigestUtils.md5Hex("helloworld"));
		partnerBindingDTO.setTokenExpire(System.currentTimeMillis());
		partnerBindingDTO.setValid(true);
		partnerBindingDTO.setPartnerType(PartnerTypeEnum.QQ.getCode());
		partnerBindingDTO.setPartnerId(DigestUtils.md5Hex("hellopartner"));
		partnerBindingDTO.setCreateTime(new Date());
		partnerBindingDTO.setUpdateTime(new Date());
		partnerBindingDTO.setStatus(1);

		int id = partnerBindingService.insertPartnerBindingInfo(partnerBindingDTO);
		System.err.println("newId: " + id);
	}

	@Test
	public void testUpdatePartnerBindingInfo() {
		
		PartnerBindingDTO partnerBindingDTOById = partnerBindingService.queryPartnerBindingInfo(id);
		PartnerBindingDTO partnerBindingDTOByMemberId = partnerBindingService.queryPartnerBindingInfo(memberId);
		
		partnerBindingDTOById.setPartnerId(DigestUtils.md5Hex("更新后的内容"+System.currentTimeMillis()));
		partnerBindingDTOById.setValid(false);
		partnerBindingDTOById.setPartnerType(PartnerTypeEnum.WeChat.getCode());
		
		
		partnerBindingDTOByMemberId.setPartnerId(DigestUtils.md5Hex("更新后的内容"+System.currentTimeMillis()));
		partnerBindingDTOByMemberId.setValid(false);
		partnerBindingDTOByMemberId.setPartnerType(PartnerTypeEnum.WeChat.getCode());
		
		int count1 = partnerBindingService.updatePartnerBindingInfo(partnerBindingDTOById);
		int count2 = partnerBindingService.updatePartnerBindingInfo(partnerBindingDTOByMemberId);
		
		System.err.println(count1);
		System.err.println(count2);
		
	}

	@Test
	public void testDeletePartnerBindingInfo() {
		int deleteById = 0;
		int deleteByMemberId = 0;

		deleteById = partnerBindingService.deletePartnerBindingInfo(id);
		deleteByMemberId = partnerBindingService.deletePartnerBindingInfo(memberId);
		System.err.println("deleteById: " + deleteById);
		System.err.println("deleteByMemberId: " + deleteByMemberId);
	}

	@Test
	public void testQueryPartnerBindingInfo() {

		PartnerBindingDTO partnerBindingDTOById = partnerBindingService.queryPartnerBindingInfo(id);
		PartnerBindingDTO partnerBindingDTOByMemberId = partnerBindingService.queryPartnerBindingInfo(memberId);
		System.err.println(partnerBindingDTOById);
		System.err.println(partnerBindingDTOByMemberId);
	}

}
