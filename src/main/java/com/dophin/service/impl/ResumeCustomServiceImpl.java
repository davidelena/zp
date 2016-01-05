package com.dophin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeCustomDAO;
import com.dophin.dto.ResumeCustomDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.service.ResumeCustomService;
import com.dophin.service.ResumeInfoService;

@Service
public class ResumeCustomServiceImpl extends BaseService implements ResumeCustomService {

	private static final String ES_RESUME_CUSTOM = "resumecustom";

	@Autowired
	private ResumeCustomDAO resumeCustomDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeCustom(ResumeCustomDTO resumeCustomDTO) {
		resumeCustomDAO.insertResumeCustom(resumeCustomDTO);
		try {
			ResumeCustomDTO insertResumeCustomDTO = queryResumeCustom(resumeCustomDTO.getId());
			String result = mapper.writeValueAsString(insertResumeCustomDTO);
			doIndexAction(result, insertResumeCustomDTO.getId(), ES_RESUME_CUSTOM);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeCustomDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeCustomDTO.getId();
	}

	@Override
	public int updateResumeCustom(ResumeCustomDTO resumeCustomDTO) {
		int count = resumeCustomDAO.updateResumeCustom(resumeCustomDTO);
		try {
			ResumeCustomDTO updatedResumeCustomDTO = queryResumeCustom(resumeCustomDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeCustomDTO);
			doUpdateAction(result, updatedResumeCustomDTO.getId(), ES_RESUME_CUSTOM);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeCustomDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeCustom(int id) {
		int resumeId = resumeCustomDAO.queryResumeCustom(id).getResumeId();		
		int count = resumeCustomDAO.deleteResumeCustom(id);
		try {
			doDeleteAction(id, ES_RESUME_CUSTOM);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public ResumeCustomDTO queryResumeCustom(int id) {
		ResumeCustomDTO resumeCustomDTO = resumeCustomDAO.queryResumeCustom(id);
		if (resumeCustomDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeCustomDTO.getResumeId());
			resumeCustomDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeCustomDTO;
	}

	@Override
	public List<ResumeCustomDTO> queryResumeCustoms(int resumeId) {
		return queryResumeCustoms(resumeId, false);
	}

	@Override
	public List<ResumeCustomDTO> queryResumeCustoms(int resumeId, boolean isFillResumeInfo) {
		List<ResumeCustomDTO> resumeCustomDTOs = resumeCustomDAO.queryResumeCustoms(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeCustomDTO resumeCustomDTO : resumeCustomDTOs) {
			if (isFillResumeInfo) {
				if (resumeCustomDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeCustomDTO.getResumeId());
					resumeCustomDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumeCustomDTOs;
	}

}
