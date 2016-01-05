package com.dophin.service.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.dophin.dto.FilterResumeInfoCondition;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 企业用户搜索简历服务
 * 
 * @author David.dai
 * 
 */
public class SearchResumeInfoService {
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
	protected static String index = CommonUtils.ES_INDEX;
	protected static String reusme_type = "resumeinfo";
	protected static ObjectMapper mapper = new ObjectMapper();
	protected static Logger logger = Logger.getLogger(SearchResumeInfoService.class);

	public List<ResumeInfoDTO> searchResumeInfoWithConditions(FilterResumeInfoCondition condition) {
		List<ResumeInfoDTO> resumeInfoDTOs = new ArrayList<ResumeInfoDTO>();
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		SortBuilder sortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
		queryBuilder = queryBuilder.must(QueryBuilders.termQuery("status", 1));// 必须为1的，为2的表示简历简历已经被忽略了F

		if (condition.getSchoolId() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("memberDTO.schoolId", condition.getSchoolId()));
		}

		if (condition.getDiploma() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("diploma", condition.getDiploma()));
		}

		if (condition.getGraduationYear() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("graduationYear", condition.getGraduationYear()));
		}

		if (condition.getSex() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("sex", condition.getSex()));
		}

		if (condition.getMajor() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("resumeEducationExpDTOs.majorType",
					condition.getMajor()));
		}

		if (condition.isHasWorkExp() != null) {
			if (condition.isHasWorkExp()) {
				queryBuilder = queryBuilder.must(QueryBuilders.termQuery("resumeWorkExpDTOs.status", 1));
			}
		}

		if (!StringUtils.isBlank(condition.getDemandIndustries())) {
			queryBuilder = queryBuilder.must(QueryBuilders.termsQuery("memberDTO.studentInfoDTO.demandIndustries.id",
					CommonUtils.toIntList(condition.getDemandIndustries())));
		}

		if (!StringUtils.isBlank(condition.getDemandCities())) {
			queryBuilder = queryBuilder.must(QueryBuilders.termsQuery("memberDTO.studentInfoDTO.demandCitys.id",
					CommonUtils.toIntList(condition.getDemandCities())));
		}

		if (!StringUtils.isBlank(condition.getSearchTxt())) {
			String searchTxt = condition.getSearchTxt();
			queryBuilder = queryBuilder.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.fuzzyQuery("memberDTO.universityDTO.name", searchTxt))
					.should(QueryBuilders.fuzzyQuery("memberDTO.universityDTO.abbreviation", searchTxt))
					.should(QueryBuilders.fuzzyQuery("memberDTO.universityDTO.tag", searchTxt))
					.should(QueryBuilders.fuzzyQuery("diplomaDesc", searchTxt))
					.should(QueryBuilders.fuzzyQuery("resumeEducationExpDTOs.school", searchTxt))
					.should(QueryBuilders.fuzzyQuery("resumeEducationExpDTOs.majorTypeDesc", searchTxt)));
		}

		try {
			SearchResponse response = elasticSearchUtils.doSearchAction(index, reusme_type, queryBuilder,
					condition.getFrom(), condition.getSize());
			SearchHits searchHits = response.getHits();
			condition.setTotal((int) searchHits.getTotalHits());
			SearchHit[] searchHitArr = searchHits.getHits();
			for (SearchHit searchHit : searchHitArr) {
				ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
				if (resumeInfoDTO != null) {
					resumeInfoDTOs.add(resumeInfoDTO);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return resumeInfoDTOs;
	}
}
