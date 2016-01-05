package com.dophin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeActivityExpDAO;
import com.dophin.dto.ResumeActivityExpDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.service.ResumeActivityExpService;
import com.dophin.service.ResumeInfoService;

@Service
public class ResumeActivityExpServiceImpl extends BaseService implements ResumeActivityExpService {

	private static final String ES_RESUME_ACTIVITY_EXP = "resumeactivityexp";

	@Autowired
	private ResumeActivityExpDAO resumeActivityExpDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeActivityExp(ResumeActivityExpDTO resumeActivityExpDTO) {
		resumeActivityExpDAO.insertResumeActivityExp(resumeActivityExpDTO);
		try {
			ResumeActivityExpDTO insertResumeActivityExpDTO = queryResumeActivityExp(resumeActivityExpDTO.getId());
			String result = mapper.writeValueAsString(insertResumeActivityExpDTO);
			doIndexAction(result, insertResumeActivityExpDTO.getId(), ES_RESUME_ACTIVITY_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeActivityExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return resumeActivityExpDTO.getId();
	}

	@Override
	public int updateResumeActivityExp(ResumeActivityExpDTO resumeActivityExpDTO) {
		int count = resumeActivityExpDAO.updateResumeActivityExp(resumeActivityExpDTO);
		try {
			ResumeActivityExpDTO updatedResumeActivityExpDTO = queryResumeActivityExp(resumeActivityExpDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeActivityExpDTO);
			doUpdateAction(result, updatedResumeActivityExpDTO.getId(), ES_RESUME_ACTIVITY_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeActivityExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeActivityExp(int id) {
		int resumeId = resumeActivityExpDAO.queryResumeActivityExp(id).getResumeId();	
		int count = resumeActivityExpDAO.deleteResumeActivityExp(id);
		try {
			doDeleteAction(id, ES_RESUME_ACTIVITY_EXP);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public ResumeActivityExpDTO queryResumeActivityExp(int id) {
		ResumeActivityExpDTO resumeActivityExpDTO = resumeActivityExpDAO.queryResumeActivityExp(id);
		if (resumeActivityExpDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(id);
			resumeActivityExpDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeActivityExpDTO;
	}

	@Override
	public List<ResumeActivityExpDTO> queryResumeActivityExps(int resumeId) {
		return queryResumeActivityExps(resumeId, false);
	}

	@Override
	public List<ResumeActivityExpDTO> queryResumeActivityExps(int resumeId, boolean isFillResumeInfo) {
		List<ResumeActivityExpDTO> resumeActivityExpDTOs = resumeActivityExpDAO.queryResumeActivityExps(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeActivityExpDTO resumeActivityExpDTO : resumeActivityExpDTOs) {
			if (isFillResumeInfo) {
				if (resumeActivityExpDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeActivityExpDTO.getResumeId());
					resumeActivityExpDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumeActivityExpDTOs;
	}

}
