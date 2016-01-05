package com.dophin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.CompanyMemberDAO;
import com.dophin.dao.ConstantsDAO;
import com.dophin.dto.CompanyInfoDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.service.CompanyMemberService;
import com.dophin.service.RecruitInfoService;

@Service
public class CompanyMemberServiceImpl extends BaseService implements CompanyMemberService {

	@Autowired
	private CompanyMemberDAO companyMemberDAO;

	@Autowired
	private ConstantsDAO constantsDAO;

	@Autowired
	private RecruitInfoService recruitInfoService;
	
	@Override
	public int insertCompanyInfo(CompanyInfoDTO companyInfoDTO) {
		companyMemberDAO.insertCompanyInfo(companyInfoDTO);
		try {
			// 流程上（先注册企业账户后填写企业进本信息，才能发布职位所以这边不需要及时更新es搜索库的信息）
			CompanyInfoDTO insertCompanyInfoDTO = queryCompanyInfo(companyInfoDTO.getId());
			String result = mapper.writeValueAsString(insertCompanyInfoDTO);
			doIndexAction(result, companyInfoDTO.getId(), ES_COMPANY_INFO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return companyInfoDTO.getId();
	}

	@Override
	public int updateCompanyInfo(CompanyInfoDTO companyInfoDTO) {

		int count = companyMemberDAO.updateCompanyInfo(companyInfoDTO);

		// 此处一定要查询更新湾最新的CompanyInfo存入es索引库中
		CompanyInfoDTO updatedCompanyInfoDTO = queryCompanyInfo(companyInfoDTO.getId());

		try {

			String result = mapper.writeValueAsString(updatedCompanyInfoDTO);
			doUpdateAction(result, updatedCompanyInfoDTO.getId(), ES_COMPANY_INFO);

			logger.info("execute recruitinfo action... for companyid: " + companyInfoDTO.getId());
			List<RecruitInfoDTO> recruitInfoDTOs = recruitInfoService.queryOnlineRecruitInfoByCompanyId(companyInfoDTO
					.getId());
			String recruitInfoJsonStr = StringUtils.EMPTY;	
						
			for (RecruitInfoDTO recruitInfoDTO : recruitInfoDTOs) {
				recruitInfoJsonStr = mapper.writeValueAsString(recruitInfoDTO);
				doUpdateAction(recruitInfoJsonStr, recruitInfoDTO.getId(), ES_RECRUIT_INFO);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return count;
	}

	@Override
	public int deleteCompanyInfo(Map<String, Object> queryMap) {
		return companyMemberDAO.deleteCompanyInfo(queryMap);
	}

	
	@Override
	public int deleteCompanyInfo(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return deleteCompanyInfo(queryMap);
	}

	@Override
	public int deleteCompanyInfo(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return deleteCompanyInfo(queryMap);
	}

	@Override
	public CompanyInfoDTO queryCompanyInfo(Map<String, Object> queryMap) {
		CompanyInfoDTO companyInfoDTO = companyMemberDAO.queryCompanyInfo(queryMap);
		if (companyInfoDTO != null) {
			companyInfoDTO.setSynopsis(companyInfoDTO.getSynopsis().trim());
			IndustryDTO industryDTO = constantsDAO.queryIndustry(companyInfoDTO.getIndustry());
			companyInfoDTO.setIndustryDesc(industryDTO.getName());
		}
		return companyInfoDTO;
	}

	@Override
	public CompanyInfoDTO queryCompanyInfo(int id) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("id", id);
		return queryCompanyInfo(queryMap);
	}

	@Override
	public CompanyInfoDTO queryCompanyInfo(String memberId) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		return queryCompanyInfo(queryMap);
	}

}
