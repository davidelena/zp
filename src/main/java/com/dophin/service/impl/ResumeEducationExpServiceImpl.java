package com.dophin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeEducationExpDAO;
import com.dophin.dto.ResumeEducationExpDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.service.ResumeEducationExpService;
import com.dophin.service.ResumeInfoService;

@Service
public class ResumeEducationExpServiceImpl extends BaseService implements ResumeEducationExpService {

	private static final String ES_RESUME_EDUCATION_EXP = "resumeeducationexp";

	@Autowired
	private ResumeEducationExpDAO resumeEducationExpDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeEducationExp(ResumeEducationExpDTO resumeEducationExpDTO) {
		resumeEducationExpDAO.insertResumeEducationExp(resumeEducationExpDTO);
		try {
			ResumeEducationExpDTO insertResumeEducationExpDTO = queryResumeEducationExp(resumeEducationExpDTO.getId());
			String result = mapper.writeValueAsString(insertResumeEducationExpDTO);
			doIndexAction(result, insertResumeEducationExpDTO.getId(), ES_RESUME_EDUCATION_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeEducationExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeEducationExpDTO.getId();
	}

	@Override
	public int updateResumeEducationExp(ResumeEducationExpDTO resumeEducationExpDTO) {
		int count = resumeEducationExpDAO.updateResumeEducationExp(resumeEducationExpDTO);
		try {
			ResumeEducationExpDTO updatedResumeEducationExpDTO = queryResumeEducationExp(resumeEducationExpDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeEducationExpDTO);
			doUpdateAction(result, updatedResumeEducationExpDTO.getId(), ES_RESUME_EDUCATION_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeEducationExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeEducationExp(int id) {
		int resumeId = resumeEducationExpDAO.queryResumeEducationExp(id).getResumeId();		
		int count = resumeEducationExpDAO.deleteResumeEducationExp(id);
		try {
			doDeleteAction(id, ES_RESUME_EDUCATION_EXP);			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public ResumeEducationExpDTO queryResumeEducationExp(int id) {
		ResumeEducationExpDTO resumeEducationExpDTO = resumeEducationExpDAO.queryResumeEducationExp(id);
		if (resumeEducationExpDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeEducationExpDTO.getResumeId());
			resumeEducationExpDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeEducationExpDTO;
	}

	@Override
	public List<ResumeEducationExpDTO> queryResumeEducationExps(int resumeId) {
		return queryResumeEducationExps(resumeId, false);
	}

	@Override
	public List<ResumeEducationExpDTO> queryResumeEducationExps(int resumeId, boolean isFillResumeInfo) {
		List<ResumeEducationExpDTO> resumeEducationExpDTOs = resumeEducationExpDAO.queryResumeEducationExps(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeEducationExpDTO resumeEducationExpDTO : resumeEducationExpDTOs) {
			if (isFillResumeInfo) {
				if (resumeEducationExpDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeEducationExpDTO.getResumeId());
					resumeEducationExpDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumeEducationExpDTOs;
	}

	@Override
	public List<Integer> queryResumeIdByMajorType(Map<String, Object> queryMap)
	{
		return resumeEducationExpDAO.queryResumeIdByMajorType(queryMap);
	}

}
