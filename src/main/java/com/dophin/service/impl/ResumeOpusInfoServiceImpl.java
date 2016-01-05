package com.dophin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeOpusInfoDAO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeOpusInfoDTO;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumeOpusInfoService;

@Service
public class ResumeOpusInfoServiceImpl extends BaseService implements ResumeOpusInfoService {

	private static final String ES_RESUME_OPUS_INFO = "resumeopusinfo";

	@Autowired
	private ResumeOpusInfoDAO resumeOpusInfoDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeOpusInfo(ResumeOpusInfoDTO resumeOpusInfoDTO) {
		resumeOpusInfoDAO.insertResumeOpusInfo(resumeOpusInfoDTO);
		try {
			ResumeOpusInfoDTO insertResumeOpusInfoDTO = queryResumeOpusInfo(resumeOpusInfoDTO.getId());
			String result = mapper.writeValueAsString(insertResumeOpusInfoDTO);
			doIndexAction(result, insertResumeOpusInfoDTO.getId(), ES_RESUME_OPUS_INFO);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeOpusInfoDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeOpusInfoDTO.getId();
	}

	@Override
	public int updateResumeOpusInfo(ResumeOpusInfoDTO resumeOpusInfoDTO) {
		int count = resumeOpusInfoDAO.updateResumeOpusInfo(resumeOpusInfoDTO);
		try {
			ResumeOpusInfoDTO updatedResumeOpusInfoDTO = queryResumeOpusInfo(resumeOpusInfoDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeOpusInfoDTO);
			doUpdateAction(result, updatedResumeOpusInfoDTO.getId(), ES_RESUME_OPUS_INFO);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeOpusInfoDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeOpusInfo(int id) {
		int resumeId = resumeOpusInfoDAO.queryResumeOpusInfo(id).getResumeId();		
		int count = resumeOpusInfoDAO.deleteResumeOpusInfo(id);
		try {
			doDeleteAction(id, ES_RESUME_OPUS_INFO);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;

	}

	@Override
	public ResumeOpusInfoDTO queryResumeOpusInfo(int id) {
		ResumeOpusInfoDTO resumeOpusInfoDTO = resumeOpusInfoDAO.queryResumeOpusInfo(id);
		if (resumeOpusInfoDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeOpusInfoDTO.getResumeId());
			resumeOpusInfoDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeOpusInfoDTO;
	}

	@Override
	public List<ResumeOpusInfoDTO> queryResumeOpusInfos(int resumeId) {
		return queryResumeOpusInfos(resumeId, false);
	}

	@Override
	public List<ResumeOpusInfoDTO> queryResumeOpusInfos(int resumeId, boolean isFillResumeInfo) {
		List<ResumeOpusInfoDTO> resumeOpusInfoDTOs = resumeOpusInfoDAO.queryResumeOpusInfos(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeOpusInfoDTO resumeOpusInfoDTO : resumeOpusInfoDTOs) {
			if (isFillResumeInfo) {
				if (resumeOpusInfoDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeOpusInfoDTO.getResumeId());
					resumeOpusInfoDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumeOpusInfoDTOs;
	}

}
