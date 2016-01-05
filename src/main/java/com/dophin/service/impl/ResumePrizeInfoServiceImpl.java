package com.dophin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumePrizeInfoDAO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumePrizeInfoDTO;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumePrizeInfoService;

@Service
public class ResumePrizeInfoServiceImpl extends BaseService implements ResumePrizeInfoService {

	private static final String ES_RESUME_PRIZE_INFO = "resumeprizeinfo";

	@Autowired
	private ResumePrizeInfoDAO resumePrizeInfoDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumePrizeInfo(ResumePrizeInfoDTO resumePrizeInfoDTO) {
		resumePrizeInfoDAO.insertResumePrizeInfo(resumePrizeInfoDTO);
		try {
			ResumePrizeInfoDTO insertResumePrizeInfoDTO = queryResumePrizeInfo(resumePrizeInfoDTO.getId());
			String result = mapper.writeValueAsString(insertResumePrizeInfoDTO);
			doIndexAction(result, insertResumePrizeInfoDTO.getId(), ES_RESUME_PRIZE_INFO);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumePrizeInfoDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumePrizeInfoDTO.getId();
	}

	@Override
	public int updateResumePrizeInfo(ResumePrizeInfoDTO resumePrizeInfoDTO) {
		int count = resumePrizeInfoDAO.updateResumePrizeInfo(resumePrizeInfoDTO);
		try {
			ResumePrizeInfoDTO updatedResumePrizeInfoDTO = queryResumePrizeInfo(resumePrizeInfoDTO.getId());
			String result = mapper.writeValueAsString(updatedResumePrizeInfoDTO);
			doUpdateAction(result, updatedResumePrizeInfoDTO.getId(), ES_RESUME_PRIZE_INFO);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumePrizeInfoDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumePrizeInfo(int id) {
		int resumeId = resumePrizeInfoDAO.queryResumePrizeInfo(id).getResumeId();
		int count = resumePrizeInfoDAO.deleteResumePrizeInfo(id);
		try {
			doDeleteAction(id, ES_RESUME_PRIZE_INFO);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public ResumePrizeInfoDTO queryResumePrizeInfo(int id) {
		ResumePrizeInfoDTO resumePrizeInfoDTO = resumePrizeInfoDAO.queryResumePrizeInfo(id);
		if (resumePrizeInfoDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumePrizeInfoDTO.getResumeId());
			resumePrizeInfoDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumePrizeInfoDTO;
	}

	@Override
	public List<ResumePrizeInfoDTO> queryResumePrizeInfos(int resumeId) {
		return queryResumePrizeInfos(resumeId, false);
	}

	@Override
	public List<ResumePrizeInfoDTO> queryResumePrizeInfos(int resumeId, boolean isFillResumeInfo) {
		List<ResumePrizeInfoDTO> resumePrizeInfoDTOs = resumePrizeInfoDAO.queryResumePrizeInfos(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumePrizeInfoDTO resumePrizeInfoDTO : resumePrizeInfoDTOs) {
			if (isFillResumeInfo) {
				if (resumePrizeInfoDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumePrizeInfoDTO.getResumeId());
					resumePrizeInfoDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumePrizeInfoDTOs;
	}

}
