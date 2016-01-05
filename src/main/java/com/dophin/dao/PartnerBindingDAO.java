package com.dophin.dao;

import java.util.Map;

import com.dophin.dto.PartnerBindingDTO;

/**
 * 合作商DAO
 * @author dailiwei
 *
 */
public interface PartnerBindingDAO {
	
	int insertPartnerBindingInfo(PartnerBindingDTO partnerBindingDTO);
	
	int updatePartnerBindingInfo(PartnerBindingDTO partnerBindingDTO);
	
	int deletePartnerBindingInfo(Map<String, Object> queryMap);
	
	PartnerBindingDTO queryPartnerBindingInfo(Map<String, Object> queryMap);
	
	PartnerBindingDTO queryPartnerBindingDTO(String memberId);
	
	PartnerBindingDTO queryPartnerBindingDTO(int id);
	
}
