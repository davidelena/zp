package com.dophin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeWorkExpDAO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeWorkExpDTO;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumeWorkExpService;

@Service
public class ResumeWorkExpServiceImpl extends BaseService implements ResumeWorkExpService {

	private static final String ES_RESUME_WORK_EXP = "resumeworkexp";

	@Autowired
	private ResumeWorkExpDAO resumeWorkExpDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeWorkExp(ResumeWorkExpDTO resumeWorkExpDTO) {
		resumeWorkExpDAO.insertResumeWorkExp(resumeWorkExpDTO);
		try {
			ResumeWorkExpDTO insertResumeWorkExpDTO = queryResumeWorkExp(resumeWorkExpDTO.getId());
			String result = mapper.writeValueAsString(insertResumeWorkExpDTO);
			doIndexAction(result, insertResumeWorkExpDTO.getId(), ES_RESUME_WORK_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeWorkExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeWorkExpDTO.getId();
	}

	@Override
	public int updateResumeWorkExp(ResumeWorkExpDTO resumeWorkExpDTO) {
		int count = resumeWorkExpDAO.updateResumeWorkExp(resumeWorkExpDTO);
		try {
			ResumeWorkExpDTO updatedResumeWorkExpDTO = queryResumeWorkExp(resumeWorkExpDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeWorkExpDTO);
			doUpdateAction(result, updatedResumeWorkExpDTO.getId(), ES_RESUME_WORK_EXP);
			
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeWorkExpDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeWorkExp(int id) {
		int resumeId = resumeWorkExpDAO.queryResumeWorkExp(id).getResumeId();
		int count = resumeWorkExpDAO.deleteResumeWorkExp(id);
		try {
			doDeleteAction(id, ES_RESUME_WORK_EXP);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public ResumeWorkExpDTO queryResumeWorkExp(int id) {
		ResumeWorkExpDTO resumeWorkExpDTO = resumeWorkExpDAO.queryResumeWorkExp(id);
		if (resumeWorkExpDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeWorkExpDTO.getResumeId());
			resumeWorkExpDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeWorkExpDTO;
	}

	@Override
	public List<ResumeWorkExpDTO> queryResumeWorkExps(int resumeId) {
		return queryResumeWorkExps(resumeId, false);
	}

	@Override
	public List<ResumeWorkExpDTO> queryResumeWorkExps(int resumeId, boolean isFillResumeInfo) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("resumeId", resumeId);
		List<ResumeWorkExpDTO> resumeWorkExpDTOs = resumeWorkExpDAO.queryResumeWorkExps(queryMap);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeWorkExpDTO resumeWorkExpDTO : resumeWorkExpDTOs) {
			if (isFillResumeInfo) {
				if (resumeWorkExpDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeWorkExpDTO.getResumeId());
					resumeWorkExpDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}
		return resumeWorkExpDTOs;
	}

}
