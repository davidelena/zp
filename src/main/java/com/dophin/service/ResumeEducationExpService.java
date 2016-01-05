package com.dophin.service;

import java.util.List;
import java.util.Map;

import com.dophin.dto.ResumeEducationExpDTO;

/**
 * 简历-教育经历
 * 
 * @author dailiwei
 * 
 */
public interface ResumeEducationExpService {

	int insertResumeEducationExp(ResumeEducationExpDTO resumeEducationExpDTO);

	int updateResumeEducationExp(ResumeEducationExpDTO resumeEducationExpDTO);

	int deleteResumeEducationExp(int id);

	ResumeEducationExpDTO queryResumeEducationExp(int id);

	List<ResumeEducationExpDTO> queryResumeEducationExps(int resumeId);

	/**
	 * 查询当前简历下的所有教育经历
	 * 
	 * @param resumeId
	 *            简历ID
	 * @param isFillResumeInfo
	 *            是否填充简历信息，单独查询教育经历列表时候需要填充，如果是简历信息里面需要连带查询 为了防止循环依赖，需要增加该标志位
	 * @return
	 */
	List<ResumeEducationExpDTO> queryResumeEducationExps(int resumeId, boolean isFillResumeInfo);
	
	List<Integer> queryResumeIdByMajorType(Map<String, Object> queryMap);
}
