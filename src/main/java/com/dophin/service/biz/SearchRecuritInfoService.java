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

import com.dophin.dto.FilterRecruitInfoCondition;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.enums.SalaryLevelEnum;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 学生用户搜索职位服务
 * 
 * @author dailiwei
 * 
 */
public abstract class SearchRecuritInfoService {

	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
	protected static String index = CommonUtils.ES_INDEX;
	protected static String recruit_type = "recruitinfo";
	protected static String member_recruit_type = "memberrecruit";
	protected static ObjectMapper mapper = new ObjectMapper();
	protected static Logger logger = Logger.getLogger(SearchRecuritInfoService.class);

	protected abstract BoolQueryBuilder getQueryBuilder(FilterRecruitInfoCondition condition);

	/**
	 * 根据学生意向搜索
	 * 
	 * @param condition
	 * @return
	 */
	public List<RecruitInfoDTO> searchWithDemandConditions(FilterRecruitInfoCondition condition) {
		List<RecruitInfoDTO> recruitInfoDTOs = new ArrayList<RecruitInfoDTO>();
		// 必须为招聘中的职位positionStatus=1
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
				QueryBuilders.termQuery("positionStatus", PositionStatusEnum.Online.getCode()));

		// 满足期望求职类别
		if (condition.getDemandRecruitType() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("recruitType", condition.getDemandRecruitType()));
		}

		// 满足期望行业
		if (condition.getDemandIndustries().size() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termsQuery("companyInfoDTO.industry",
					condition.getDemandIndustries()));
		}

		// 满足期望城市
		if (condition.getDemandCities().size() > 0) {
			queryBuilder = queryBuilder
					.must(QueryBuilders.termsQuery("workCityDTO.cityID", condition.getDemandCities()));
		}

		// 满足期望月薪
		if (condition.getDemandSalary() > 0) {
			SalaryLevelEnum sLevelEnum = SalaryLevelEnum.getSalaryLevelEnum(condition.getDemandSalary());

			queryBuilder = queryBuilder
					.must(QueryBuilders
							.boolQuery()
							.should(QueryBuilders.rangeQuery("minSalary").gte(sLevelEnum.getBottom())
									.lte(sLevelEnum.getTop()))
							.should(QueryBuilders.rangeQuery("maxSalary").gte(sLevelEnum.getBottom())
									.lte(sLevelEnum.getTop())));
		}

		// 满足期望求职职位名称
		if (!StringUtils.isBlank(condition.getDemandPosition())) {
			queryBuilder = queryBuilder.must(QueryBuilders.fuzzyQuery("positionName", condition.getDemandPosition()));
		}

		try {
			SearchResponse response = elasticSearchUtils.doSearchAction(index, recruit_type, queryBuilder,
					condition.getFrom(), condition.getSize());
			SearchHits searchHits = response.getHits();
			condition.setTotal((int) searchHits.getTotalHits());

			SearchHit[] searchHitArr = searchHits.getHits();
			for (SearchHit searchHit : searchHitArr) {
				logger.info("searchHit Score: " + searchHit.getScore());
				RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
				if (recruitInfoDTO != null) {
					recruitInfoDTOs.add(recruitInfoDTO);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return recruitInfoDTOs;
	}

	/**
	 * 根据搜索条件搜索
	 * 
	 * @param condition
	 * @return
	 */
	public List<RecruitInfoDTO> searchRecruitInfoWithConditions(FilterRecruitInfoCondition condition) {
		List<RecruitInfoDTO> recruitInfoDTOs = new ArrayList<RecruitInfoDTO>();

		BoolQueryBuilder queryBuilder = null;
		SortBuilder sortBuilder = null;

		queryBuilder = getQueryBuilder(condition);

		if (condition.isLatested()) {
			sortBuilder = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
		}

		if (!StringUtils.isBlank(condition.getSearchTxt())) {
			queryBuilder = queryBuilder.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.fuzzyQuery("companyInfoDTO.name", condition.getSearchTxt()))
					.should(QueryBuilders.fuzzyQuery("positionName", condition.getSearchTxt())));
		}

		try {
			SearchResponse response = elasticSearchUtils.doSearchAction(index, recruit_type, queryBuilder, sortBuilder,
					condition.getFrom(), condition.getSize());
			SearchHits searchHits = response.getHits();
			condition.setTotal((int) searchHits.getTotalHits());

			SearchHit[] searchHitArr = searchHits.getHits();
			for (SearchHit searchHit : searchHitArr) {
				// System.err.println(searchHit.getScore());
				RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
				if (recruitInfoDTO != null) {
					recruitInfoDTOs.add(recruitInfoDTO);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return recruitInfoDTOs;
	}
}
