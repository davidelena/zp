package com.dophin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.PartnerBindingDAO;
import com.dophin.dto.PartnerBindingDTO;
import com.dophin.service.PartnerBindingService;

@Service
public class ParnerBindingServiceImpl implements PartnerBindingService {

	@Autowired
	private PartnerBindingDAO partnerBindingDAO;

	@Override
	public int insertPartnerBindingInfo(PartnerBindingDTO partnerBindingDTO) {
		partnerBindingDAO.insertPartnerBindingInfo(partnerBindingDTO);
		return partnerBindingDTO.getId();
	}

	@Override
	public int updatePartnerBindingInfo(PartnerBindingDTO partnerBindingDTO) {
		return partnerBindingDAO.updatePartnerBindingInfo(partnerBindingDTO);
	}

	@Override
	public int deletePartnerBindingInfo(Map<String, Object> queryMap) {
		return partnerBindingDAO.deletePartnerBindingInfo(queryMap);
	}

	@Override
	public PartnerBindingDTO queryPartnerBindingInfo(Map<String, Object> queryMap) {
		return partnerBindingDAO.queryPartnerBindingInfo(queryMap);
	}

	@Override
	public PartnerBindingDTO queryPartnerBindingInfo(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return queryPartnerBindingInfo(queryMap);
	}

	@Override
	public PartnerBindingDTO queryPartnerBindingInfo(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return queryPartnerBindingInfo(queryMap);		
	}

	@Override
	public int deletePartnerBindingInfo(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return deletePartnerBindingInfo(queryMap);
	}

	@Override
	public int deletePartnerBindingInfo(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return deletePartnerBindingInfo(queryMap);
	}

}
