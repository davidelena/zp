package com.dophin.service;

import java.util.Map;

import com.dophin.dto.StudentInfoDTO;

public interface StudentMemberService {
	
	int insertStudentInfo(StudentInfoDTO studentInfoDTO);
	
	int updateStudentInfo(StudentInfoDTO studentInfoDTO);
	
	int deleteStudentInfo(Map<String, Object> queryMap);
	
	int deleteStudentInfo(int id);
	
	int deleteStudentInfo(String memberId);
	
	StudentInfoDTO queryStudentInfo(Map<String, Object> queryMap);
	
	StudentInfoDTO queryStudentInfo(int id);
	
	StudentInfoDTO queryStudentInfo(String memberId);
}
