package com.dophin.service;

import java.util.Map;

import com.dophin.dto.PartnerBindingDTO;

public interface PartnerBindingService {

	int insertPartnerBindingInfo(PartnerBindingDTO partnerBindingDTO);
	
	int updatePartnerBindingInfo(PartnerBindingDTO partnerBindingDTO);
	
	int deletePartnerBindingInfo(Map<String, Object> queryMap);
	
	int deletePartnerBindingInfo(String memberId);
	
	int deletePartnerBindingInfo(int id);
	
	PartnerBindingDTO queryPartnerBindingInfo(Map<String, Object> queryMap);
	
	PartnerBindingDTO queryPartnerBindingInfo(String memberId);
	
	PartnerBindingDTO queryPartnerBindingInfo(int id);
	
}
