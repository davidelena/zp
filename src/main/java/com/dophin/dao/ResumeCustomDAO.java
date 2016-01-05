package com.dophin.dao;

import java.util.List;

import com.dophin.dto.ResumeCustomDTO;

/**
 * 简历-自定义板块
 * @author dailiwei
 *
 */
public interface ResumeCustomDAO {

	int insertResumeCustom(ResumeCustomDTO resumeCustomDTO);

	int updateResumeCustom(ResumeCustomDTO resumeCustomDTO);

	int deleteResumeCustom(int id);

	ResumeCustomDTO queryResumeCustom(int id);

	List<ResumeCustomDTO> queryResumeCustoms(int resumeId);
	
}
