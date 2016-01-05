package com.dophin.dao;

import java.util.List;

import com.dophin.dto.ResumeSocialNetDTO;

/**
 * 简历-社交网络
 * 
 * @author David.dai
 * 
 */
public interface ResumeSocialNetDAO {
	
	int insertResumeSocialNet(ResumeSocialNetDTO resumeSocialNetDTO);

	int updateResumeSocialNet(ResumeSocialNetDTO resumeSocialNetDTO);

	int deleteResumeSocialNet(int id);

	ResumeSocialNetDTO queryResumeSocialNet(int id);

	List<ResumeSocialNetDTO> queryResumeSocialNets(int resumeId);

	int batchInsertResumeSocialNet(List<ResumeSocialNetDTO> list);

	void batchUpdateResumeSocialNet(List<ResumeSocialNetDTO> list);

	void batchDeleteResumeSocialNet(List<Integer> list);
	
}
