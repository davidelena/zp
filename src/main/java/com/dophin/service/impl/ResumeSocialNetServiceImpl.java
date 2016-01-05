package com.dophin.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ResumeSocialNetDAO;
import com.dophin.dto.EsBulkCondition;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeSocialNetDTO;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumeSocialNetService;
import com.dophin.utils.CommonUtils;

@Service
public class ResumeSocialNetServiceImpl extends BaseService implements ResumeSocialNetService {

	private static final String ES_RESUME_SOCIAL_NET = "resumesocialnet";

	@Autowired
	private ResumeSocialNetDAO resumeSocialNetDAO;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Override
	public int insertResumeSocialNet(ResumeSocialNetDTO resumeSocialNetDTO) {
		resumeSocialNetDAO.insertResumeSocialNet(resumeSocialNetDTO);
		try {
			ResumeSocialNetDTO insertResumeSocialNetDTO = queryResumeSocialNet(resumeSocialNetDTO.getId());
			String result = mapper.writeValueAsString(insertResumeSocialNetDTO);
			doIndexAction(result, insertResumeSocialNetDTO.getId(), ES_RESUME_SOCIAL_NET);

			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(insertResumeSocialNetDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resumeSocialNetDTO.getId();
	}

	@Override
	public void batchInsertResumeSocialNet(List<ResumeSocialNetDTO> list) {
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		String result = "";
		Set<Integer> resumeIds = new HashSet<Integer>();
		try {
			for (ResumeSocialNetDTO resumeSocialNetDTO : list) {
				resumeSocialNetDAO.insertResumeSocialNet(resumeSocialNetDTO);

				ResumeSocialNetDTO insertResumeSocialNetDTO = queryResumeSocialNet(resumeSocialNetDTO.getId());
				result = mapper.writeValueAsString(insertResumeSocialNetDTO);
				esBulkConditions.add(new EsBulkCondition(resumeSocialNetDTO.getId(), result));

				resumeIds.add(insertResumeSocialNetDTO.getResumeId());
			}
			if (esBulkConditions.size() > 0) {
				elasticSearchUtils.doBulkIndexAction(CommonUtils.ES_INDEX, ES_RESUME_SOCIAL_NET, esBulkConditions);
			}

			String queryIds = StringUtils.join(resumeIds, ",");
			if (!StringUtils.isBlank(queryIds)) {
				List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfosByIds(queryIds);
				batchUpdateResumeEsIndex(resumeInfoDTOs);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void batchUpdateResumeSocialNet(List<ResumeSocialNetDTO> list) {
		try {
			List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
			String result = "";
			Set<Integer> resumeIds = new HashSet<Integer>();
			List<ResumeSocialNetDTO> updateResumeSocialNetDTOs = new ArrayList<>();
			for (ResumeSocialNetDTO resumeSocialNetDTO : list) {
				updateResumeSocialNetDTOs.add(resumeSocialNetDTO);
				ResumeSocialNetDTO updatedResumeSocialNetDTO = queryResumeSocialNet(resumeSocialNetDTO.getId());
				result = mapper.writeValueAsString(updatedResumeSocialNetDTO);
				esBulkConditions.add(new EsBulkCondition(resumeSocialNetDTO.getId(), result));

				resumeIds.add(updatedResumeSocialNetDTO.getResumeId());
			}

			// 批量更新社交网络记录
			if (updateResumeSocialNetDTOs.size() > 0) {
				resumeSocialNetDAO.batchUpdateResumeSocialNet(updateResumeSocialNetDTOs);
			}

			// 批量更新社交网络es索引
			if (esBulkConditions.size() > 0) {
				elasticSearchUtils.doBulkUpdateAction(CommonUtils.ES_INDEX, ES_RESUME_SOCIAL_NET, esBulkConditions);
			}

			String queryIds = StringUtils.join(resumeIds, ",");
			if (!StringUtils.isBlank(queryIds)) {
				List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfosByIds(queryIds);
				batchUpdateResumeEsIndex(resumeInfoDTOs);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public int updateResumeSocialNet(ResumeSocialNetDTO resumeSocialNetDTO) {
		int count = resumeSocialNetDAO.updateResumeSocialNet(resumeSocialNetDTO);
		try {
			ResumeSocialNetDTO updatedResumeSocialNetDTO = queryResumeSocialNet(resumeSocialNetDTO.getId());
			String result = mapper.writeValueAsString(updatedResumeSocialNetDTO);
			doUpdateAction(result, updatedResumeSocialNetDTO.getId(), ES_RESUME_SOCIAL_NET);

			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(updatedResumeSocialNetDTO.getResumeId());
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteResumeSocialNet(int id) {
		int resumeId = resumeSocialNetDAO.queryResumeSocialNet(id).getResumeId();
		int count = resumeSocialNetDAO.deleteResumeSocialNet(id);
		try {
			doDeleteAction(id, ES_RESUME_SOCIAL_NET);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			updateResumeEsIndex(resumeInfoDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}

	@Override
	public void batchDeleteResumeSocialNet(List<Integer> list) {
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		Set<Integer> resumeIds = new HashSet<Integer>();
		List<Integer> delIds = new ArrayList<>();
		try {
			for (Integer id : list) {
				ResumeSocialNetDTO socialNetDTO = resumeSocialNetDAO.queryResumeSocialNet(id);
				if (socialNetDTO != null) {
					int resumeId = socialNetDTO.getResumeId();
					resumeIds.add(resumeId);
				}

				esBulkConditions.add(new EsBulkCondition(id, ""));
				delIds.add(id);
			}

			// 批量删除社交网络记录
			if (delIds.size() > 0) {
				resumeSocialNetDAO.batchDeleteResumeSocialNet(delIds);
			}

			// 批量删除社交网络es索引
			if (esBulkConditions.size() > 0) {
				elasticSearchUtils.doBulkDeleteAction(CommonUtils.ES_INDEX, ES_RESUME_SOCIAL_NET, esBulkConditions);
			}

			String queryIds = StringUtils.join(resumeIds, ",");
			if (!StringUtils.isBlank(queryIds)) {
				List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfosByIds(queryIds);
				batchUpdateResumeEsIndex(resumeInfoDTOs);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public ResumeSocialNetDTO queryResumeSocialNet(int id) {
		ResumeSocialNetDTO resumeSocialNetDTO = resumeSocialNetDAO.queryResumeSocialNet(id);
		if (resumeSocialNetDTO != null) {
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeSocialNetDTO.getResumeId());
			resumeSocialNetDTO.setResumeInfoDTO(resumeInfoDTO);
		}
		return resumeSocialNetDTO;
	}

	@Override
	public List<ResumeSocialNetDTO> queryResumeSocialNets(int resumeId) {
		return queryResumeSocialNets(resumeId, false);
	}

	@Override
	public List<ResumeSocialNetDTO> queryResumeSocialNets(int resumeId, boolean isFillResumeInfo) {
		List<ResumeSocialNetDTO> resumeSocialNetDTOs = resumeSocialNetDAO.queryResumeSocialNets(resumeId);
		ResumeInfoDTO resumeInfoDTO = null;
		for (ResumeSocialNetDTO resumeSocialNetDTO : resumeSocialNetDTOs) {
			if (isFillResumeInfo) {
				if (resumeSocialNetDTO != null) {
					resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeSocialNetDTO.getResumeId());
					resumeSocialNetDTO.setResumeInfoDTO(resumeInfoDTO);
				}
			}
		}

		return resumeSocialNetDTOs;
	}

}
