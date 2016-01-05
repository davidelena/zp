package com.dophin.dao;

import java.util.Map;

import com.dophin.dto.StudentInfoDTO;

/**
 * 学生注册用户DAO
 * @author dailiwei
 *
 */
public interface StudentMemberDAO {
	
	int insertStudentInfo(StudentInfoDTO studentInfoDTO);
	
	int updateStudentInfo(StudentInfoDTO studentInfoDTO);
	
	int deleteStudentInfo(Map<String, Object> queryMap);
	
	StudentInfoDTO queryStudentInfo(Map<String, Object> queryMap);
}
