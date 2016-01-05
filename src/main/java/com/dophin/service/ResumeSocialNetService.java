package com.dophin.service;

import java.util.List;

import com.dophin.dto.ResumeSocialNetDTO;

/**
 * 简历-社交网络
 * @author David.dai
 *
 */
public interface ResumeSocialNetService
{
	int insertResumeSocialNet(ResumeSocialNetDTO resumeSocialNetDTO);
	
	void batchInsertResumeSocialNet(List<ResumeSocialNetDTO> list);

	int updateResumeSocialNet(ResumeSocialNetDTO resumeSocialNetDTO);
	
	void batchUpdateResumeSocialNet(List<ResumeSocialNetDTO> list);

	int deleteResumeSocialNet(int id);
	
	void batchDeleteResumeSocialNet(List<Integer> list);

	ResumeSocialNetDTO queryResumeSocialNet(int id);

	List<ResumeSocialNetDTO> queryResumeSocialNets(int resumeId);
	
	List<ResumeSocialNetDTO> queryResumeSocialNets(int resumeId, boolean isFillResumeInfo);


}
