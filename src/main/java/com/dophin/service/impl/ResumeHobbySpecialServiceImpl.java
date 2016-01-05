package com.dophin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeHobbySpecialDAO;
import com.dophin.dto.ResumeHobbySpecialDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.service.ResumeHobbySpecialService;
import com.dophin.service.ResumeInfoService;

@Service
public class ResumeHobbySpecialServiceImpl extends BaseService implements ResumeHobbySpecialService {

	private static final String ES_RESUME_HOBBY_SPECIAL = "resumehobbyspecial";

	@Autowired
	private ResumeHobbySpecialDAO resumeHobbySpecialDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeHobbySpecial(ResumeHobbySpecialDTO resumeHobbySpecialDTO) {
		resumeHobbySpecialDAO.insertResumeHobbySpecial(resumeHobbySpecialDTO);
		try {
			ResumeHobbySpecialDTO insertResumeHobbySpecialDTO = queryResumeHobbySpecial(resumeHobbySpecialDTO.getId());
			String result = mapper.writeValueAsString(insertResumeHobbySpecialDTO);
			doIndexAction(result, insertResumeHobbySpecialDTO.getId(), ES_RESUME_HOBBY_SPECIAL);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeHobbySpecialDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeHobbySpecialDTO.getId();
	}

	@Override
	public int updateResumeHobbySpecial(ResumeHobbySpecialDTO resumeHobbySpecialDTO) {
		int count = resumeHobbySpecialDAO.updateResumeHobbySpecial(resumeHobbySpecialDTO);
		try {
			ResumeHobbySpecialDTO updatedResumeHobbySpecialDTO = queryResumeHobbySpecial(resumeHobbySpecialDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeHobbySpecialDTO);
			doUpdateAction(result, updatedResumeHobbySpecialDTO.getId(), ES_RESUME_HOBBY_SPECIAL);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeHobbySpecialDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeHobbySpecial(int id) {
		int resumeId = resumeHobbySpecialDAO.queryResumeHobbySpecial(id).getResumeId();		
		int count = resumeHobbySpecialDAO.deleteResumeHobbySpecial(id);
		try {
			doDeleteAction(id, ES_RESUME_HOBBY_SPECIAL);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public ResumeHobbySpecialDTO queryResumeHobbySpecial(int id) {
		ResumeHobbySpecialDTO resumeHobbySpecialDTO = resumeHobbySpecialDAO.queryResumeHobbySpecial(id);
		if (resumeHobbySpecialDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeHobbySpecialDTO.getResumeId());
			resumeHobbySpecialDTO.setResumeInfoDTO(resumeInfoDTO);
		}

		return resumeHobbySpecialDTO;
	}

	@Override
	public List<ResumeHobbySpecialDTO> queryResumeHobbySpecials(int resumeId) {
		return queryResumeHobbySpecials(resumeId, false);
	}

	@Override
	public List<ResumeHobbySpecialDTO> queryResumeHobbySpecials(int resumeId, boolean isFillResumeInfo) {
		List<ResumeHobbySpecialDTO> resumeHobbySpecialDTOs = resumeHobbySpecialDAO.queryResumeHobbySpecials(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeHobbySpecialDTO resumeHobbySpecialDTO : resumeHobbySpecialDTOs) {
			if (isFillResumeInfo) {
				if (resumeHobbySpecialDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeHobbySpecialDTO.getResumeId());
					resumeHobbySpecialDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumeHobbySpecialDTOs;
	}

}
