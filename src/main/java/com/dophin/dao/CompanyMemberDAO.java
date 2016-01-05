package com.dophin.dao;

import java.util.Map;

import com.dophin.dto.CompanyInfoDTO;

public interface CompanyMemberDAO
{
	int insertCompanyInfo(CompanyInfoDTO companyInfoDTO);
	
	int updateCompanyInfo(CompanyInfoDTO companyInfoDTO);
	
	int deleteCompanyInfo(Map<String, Object> queryMap);
	
	CompanyInfoDTO queryCompanyInfo(Map<String, Object> queryMap);
}
