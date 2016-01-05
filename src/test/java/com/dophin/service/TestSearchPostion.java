package com.dophin.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.InternshipDaysEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.SalaryLevelEnum;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 测试-学生搜索职位（实习或者全职）
 * 
 * @author David.dai
 * 
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSearchPostion extends TestBaseService
{

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private CompanyMemberService companyMemberService;

	@Autowired
	private MemberRecruitService memberRecruitService;

	@Test
	public void testInitRecruitInfoData() throws JsonProcessingException
	{
		List<RecruitInfoDTO> recruitInfoDTOs = recruitInfoService.queryRecruitInfos(new HashMap<String, Object>());
		for (RecruitInfoDTO recruitInfoDTO : recruitInfoDTOs)
		{
			String result = mapper.writeValueAsString(recruitInfoDTO);

			IndexResponse response = elasticSearchUtils.doIndexAction(index, RECRUIT_INFO, recruitInfoDTO.getId(), result);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}

		List<MemberRecruitDTO> memberRecruitDTOs = memberRecruitService.queryMemberRecruits(
				new HashMap<String, Object>(), false);
		for (MemberRecruitDTO memberRecruitDTO : memberRecruitDTOs)
		{

			String result2 = mapper.writeValueAsString(memberRecruitDTO);
			IndexResponse response = elasticSearchUtils.doIndexAction(index, "memberrecruit", memberRecruitDTO.getId(),
					result2);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
		}
	}

	/**
	 * 测试时间戳
	 */
	@Test
	public void testShowDate()
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(1445877602000l);
		System.err.println(sdf.format(c.getTime()));

		Calendar c2 = Calendar.getInstance();
		c2.set(2015, 9, 18, 23, 59, 59);
		Date validityTime = c2.getTime();
		validityTime = DateUtils.addDays(validityTime, 3);
		System.err.println(sdf.format(validityTime));
	}

	@Test
	public void testQueryByMatchAllQuery() throws JsonParseException, JsonMappingException, IOException
	{
		QueryBuilder queryBuilder = null;
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);
		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
		}
	}

	/**
	 * 搜索职位-实习天数
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testQueryByInternshipDays() throws JsonParseException, JsonMappingException, IOException
	{
		int internshipDays = InternshipDaysEnum.Day2.getCode();
		QueryBuilder queryBuilder = QueryBuilders.termQuery("internshipDays", internshipDays);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
			System.err.println(recruitInfoDTO.getInternshipDays());
			System.err.println(recruitInfoDTO.getIntershipDaysDesc());
		}
	}

	/**
	 * 搜索职位-申请进度（截止日期）
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testQueryByValidityTime() throws JsonParseException, JsonMappingException, IOException
	{
		// 假设今天是2015.09.18日
		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 18, 23, 59, 59);
		Date validityTime = c.getTime();
		validityTime = DateUtils.addDays(validityTime, 3);

		// 查找已经当前正在招聘的职位，并且是3天内截止的
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("positionStatus", 1))
				.must(QueryBuilders.rangeQuery("validityTime").lte(validityTime.getTime()));

		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);
		SearchHits searchHits = response.getHits();
		System.err.println(searchHits.getTotalHits());

		RecruitInfoDTO recruitInfoDTO = null;
		SearchHit[] searchHitArr = searchHits.getHits();
		for (SearchHit searchHit : searchHitArr)
		{
			recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
			System.err.println(recruitInfoDTO.getPositionStatusDesc());
			System.err.println(sdf.format(recruitInfoDTO.getValidityTime()));
		}

	}

	/**
	 * 搜索职位-行业
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testQueryByIndustry() throws JsonParseException, JsonMappingException, IOException
	{
		int industryId = 9;
		QueryBuilder queryBuilder = QueryBuilders.termQuery("companyInfoDTO.industry", industryId);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
		}
	}

	/**
	 * 搜索职位-城市
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testQueryByAddressCity() throws JsonParseException, JsonMappingException, IOException
	{
		int addressId = 721; // 700,721,712
		QueryBuilder queryBuilder = QueryBuilders.termQuery("workCityDTO.cityID", addressId);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
			System.err.println(recruitInfoDTO.getWorkAddress());
			System.err.println(recruitInfoDTO.getWorkCityDTO());
		}
	}

	/**
	 * 搜索职位-专业
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testQueryByMajor() throws JsonParseException, JsonMappingException, IOException
	{
		int majorId = 5;
		QueryBuilder queryBuilder = QueryBuilders.termQuery("major", majorId);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
		}
	}

	/**
	 * 搜索职位-学历
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testQueryByDiploma() throws JsonParseException, JsonMappingException, IOException
	{
		int educationalId = PositionEducationEnum.DoctorPlus.getCode();
		QueryBuilder queryBuilder = QueryBuilders.termQuery("educational", educationalId);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
			System.err.println(recruitInfoDTO.getEducational());
			System.err.println(recruitInfoDTO.getEducationalDesc());
		}
	}

	/**
	 * 搜索职位-薪资
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testQueryByDemandSalary() throws JsonParseException, JsonMappingException, IOException
	{
		SalaryLevelEnum sLevelEnum = SalaryLevelEnum.S5000To8000;

		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.rangeQuery("minSalary").gte(sLevelEnum.getBottom()).lte(sLevelEnum.getTop()))
				.should(QueryBuilders.rangeQuery("maxSalary").gte(sLevelEnum.getBottom()).lte(sLevelEnum.getTop()));

		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO.getMinSalary());
			System.err.println(recruitInfoDTO.getMaxSalary());

		}
	}

	/**
	 * 搜索职位-按创建时间倒序
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testQueryByCreateTime() throws JsonParseException, JsonMappingException, IOException
	{
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, null,
				SortBuilders.fieldSort("createTime").order(SortOrder.DESC), 0, Integer.MAX_VALUE);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
			System.err.println(sdf.format(recruitInfoDTO.getCreateTime()));
		}
	}

	/**
	 * 根据公司名称搜索
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testFuzzyQueryWithCompanyName() throws JsonParseException, JsonMappingException, IOException
	{
		String name = "虎扑";
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.fuzzyQuery("companyInfoDTO.name", name))
				.must(QueryBuilders.termQuery("address", 721));

		SearchResponse response = elasticSearchUtils.doSearchAction(index, RECRUIT_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
			System.err.println(recruitInfoDTO.getCompanyInfoDTO());
		}
	}

	/**
	 * 根据职位名称模糊匹配搜索
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testFuzzyQueryWithPositionName() throws JsonParseException, JsonMappingException, IOException
	{
		String name = "职位";
		SearchResponse response = elasticSearchUtils.doFuzzySearchAction(index, RECRUIT_INFO, "positionName", name);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr)
		{
			System.err.println(searchHit.getSourceAsString());
			RecruitInfoDTO recruitInfoDTO = mapper.readValue(searchHit.getSourceAsString(), RecruitInfoDTO.class);
			System.err.println(recruitInfoDTO);
			System.err.println(recruitInfoDTO.getPositionName());
		}
	}

}
