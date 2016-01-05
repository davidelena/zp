package com.dophin.service;

import java.util.List;
import java.util.Map;

import com.dophin.dto.MemberDTO;

public interface MemberService {

	String insertMemberInfo(MemberDTO memberDTO);

	int updateMemberInfo(MemberDTO memberDTO);

	int deleteMemberInfo(String id);

	MemberDTO queryMemberInfo(Map<String, Object> queryMap);

	MemberDTO queryMemberInfoByMobile(String mobile, int source);

	MemberDTO queryMemberInfoByEmail(String email, int source);
	
	List<MemberDTO> queryMemberInfoByConditions(Map<String, Object> queryMap);

	MemberDTO queryMemberInfo(String memberId);

	boolean isMemberExists(Map<String, Object> queryMap);

	boolean isMemberExists(String memberId, int source);
	
	List<MemberDTO> queryCompanyIdBySuffix(Map<String, Object> queryMap);
}
