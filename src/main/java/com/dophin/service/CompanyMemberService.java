package com.dophin.service;

import java.util.Map;

import com.dophin.dto.CompanyInfoDTO;

public interface CompanyMemberService
{
	int insertCompanyInfo(CompanyInfoDTO companyInfoDTO);
	
	int updateCompanyInfo(CompanyInfoDTO companyInfoDTO);
	
	int deleteCompanyInfo(Map<String, Object> queryMap);
	
	int deleteCompanyInfo(int id);
	
	int deleteCompanyInfo(String memberId);
	
	CompanyInfoDTO queryCompanyInfo(Map<String, Object> queryMap);
	
	CompanyInfoDTO queryCompanyInfo(int id);
	
	CompanyInfoDTO queryCompanyInfo(String memberId);
	
}
