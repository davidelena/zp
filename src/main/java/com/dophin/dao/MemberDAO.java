package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.MemberDTO;

public interface MemberDAO {
	
	int insertMemberInfo(MemberDTO memberDTO);
	
	int updateMemberInfo(MemberDTO memberDTO);
	
	int deleteMemberInfo(String id);
	
	MemberDTO queryMemberInfo(Map<String, Object> queryMap);
	
	List<MemberDTO> queryMemberInfoByConditions(Map<String, Object> queryMap);
	
	int isMemberExists(Map<String, Object> queryMap);
	
	List<MemberDTO> queryCompanyIdBySuffix(Map<String, Object> queryMap);
}
