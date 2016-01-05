package com.dophin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeProjectExpDAO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeProjectExpDTO;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumeProjectExpService;

@Service
public class ResumeProjectExpServiceImpl extends BaseService implements ResumeProjectExpService {

	private static final String ES_RESUME_PROJECT_EXP = "resumeprojectexp";

	@Autowired
	private ResumeProjectExpDAO resumeProjectExpDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeProjectExp(ResumeProjectExpDTO resumeProjectExpDTO) {
		resumeProjectExpDAO.insertResumeProjectExp(resumeProjectExpDTO);
		try {
			ResumeProjectExpDTO insertResumeProjectExpDTO = queryResumeProjectExp(resumeProjectExpDTO.getId());
			String result = mapper.writeValueAsString(insertResumeProjectExpDTO);
			doIndexAction(result, insertResumeProjectExpDTO.getId(), ES_RESUME_PROJECT_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeProjectExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeProjectExpDTO.getId();
	}

	@Override
	public int updateResumeProjectExp(ResumeProjectExpDTO resumeProjectExpDTO) {
		int count = resumeProjectExpDAO.updateResumeProjectExp(resumeProjectExpDTO);
		try {
			ResumeProjectExpDTO updatedResumeProjectExpDTO = queryResumeProjectExp(resumeProjectExpDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeProjectExpDTO);
			doUpdateAction(result, updatedResumeProjectExpDTO.getId(), ES_RESUME_PROJECT_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeProjectExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeProjectExp(int id) {
		int resumeId = resumeProjectExpDAO.queryResumeProjectExp(id).getResumeId();	
		int count = resumeProjectExpDAO.deleteResumeProjectExp(id);
		try {
			doDeleteAction(id, ES_RESUME_PROJECT_EXP);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public ResumeProjectExpDTO queryResumeProjectExp(int id) {
		ResumeProjectExpDTO resumeProjectExpDTO = resumeProjectExpDAO.queryResumeProjectExp(id);
		if (resumeProjectExpDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeProjectExpDTO.getResumeId());
			resumeProjectExpDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeProjectExpDTO;
	}

	@Override
	public List<ResumeProjectExpDTO> queryResumeProjectExps(int resumeId) {
		return queryResumeProjectExps(resumeId, false);
	}

	@Override
	public List<ResumeProjectExpDTO> queryResumeProjectExps(int resumeId, boolean isFillResumeInfo) {
		List<ResumeProjectExpDTO> resumeProjectExpDTOs = resumeProjectExpDAO.queryResumeProjectExps(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeProjectExpDTO resumeProjectExpDTO : resumeProjectExpDTOs) {
			if (isFillResumeInfo) {
				if (resumeProjectExpDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeProjectExpDTO.getResumeId());
					resumeProjectExpDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumeProjectExpDTOs;
	}

}
