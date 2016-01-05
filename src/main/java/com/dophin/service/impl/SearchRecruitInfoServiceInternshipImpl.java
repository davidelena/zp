package com.dophin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.dophin.dto.FilterRecruitInfoCondition;
import com.dophin.enums.ApplyTypeEnum;
import com.dophin.enums.JobTypeEnum;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.enums.SalaryLevelEnum;
import com.dophin.service.biz.SearchRecuritInfoService;
import com.dophin.utils.CommonUtils;

/**
 * 学生-搜索实习职位
 * 
 * @author dailiwei
 * 
 */
public class SearchRecruitInfoServiceInternshipImpl extends SearchRecuritInfoService {

	@Override
	protected BoolQueryBuilder getQueryBuilder(FilterRecruitInfoCondition condition) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
				QueryBuilders.termQuery("recruitType", JobTypeEnum.Internship.getCode()));

		// 必须为招聘中的职位positionStatus=1
		queryBuilder = queryBuilder
				.must(QueryBuilders.termQuery("positionStatus", PositionStatusEnum.Online.getCode()));

		// 满足期望求职类别
		if (condition.getDemandRecruitType() > 0) {
			queryBuilder = queryBuilder
					.should(QueryBuilders.termQuery("recruitType", condition.getDemandRecruitType()));
		}

		// 满足期望行业
		if (condition.getDemandIndustries().size() > 0) {
			queryBuilder = queryBuilder.should(QueryBuilders.termsQuery("companyInfoDTO.industry",
					condition.getDemandIndustries()));
		}

		// 满足期望城市
		if (condition.getDemandCities().size() > 0) {
			queryBuilder = queryBuilder.should(QueryBuilders.termsQuery("workCityDTO.cityID",
					condition.getDemandCities()));
		}

		// 满足期望月薪
		if (condition.getDemandSalary() > 0) {
			SalaryLevelEnum sLevelEnum = SalaryLevelEnum.getSalaryLevelEnum(condition.getDemandSalary());

			queryBuilder = queryBuilder
					.should(QueryBuilders
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

		if (condition.getInternshipDays() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("internshipDays", condition.getInternshipDays()));
		}

		if (!StringUtils.isBlank(condition.getIndustries())) {
			queryBuilder = queryBuilder.must(QueryBuilders.termsQuery("companyInfoDTO.industry",
					CommonUtils.toIntList(condition.getIndustries())));
		}

		if (!StringUtils.isBlank(condition.getAddressCities())) {
			queryBuilder = queryBuilder.must(QueryBuilders.termsQuery("workCityDTO.cityID",
					CommonUtils.toIntList(condition.getAddressCities())));
		}

		if (condition.getDiploma() > 0) {
			queryBuilder = queryBuilder.must(QueryBuilders.termQuery("educational", condition.getDiploma()));
		}

		if (condition.isMyFavorite()) {
			QueryBuilder qb = QueryBuilders.termQuery("applyType", ApplyTypeEnum.Favorite.getCode());
			SearchResponse response = elasticSearchUtils.doSearchAction(CommonUtils.ES_INDEX, "memberrecruit", qb);
			SearchHit[] searchHits = response.getHits().getHits();
			List<Integer> idList = new ArrayList<Integer>();
			for (SearchHit searchHit : searchHits) {
				int recruitId = NumberUtils.toInt(searchHit.getSource().get("recruitId").toString(), 0);
				if (!idList.contains(recruitId)) {
					idList.add(recruitId);
				}
			}

			logger.info("idList：" + idList);
			queryBuilder = queryBuilder.must(QueryBuilders.termsQuery("id", idList));
		}

		return queryBuilder;
	}

}
