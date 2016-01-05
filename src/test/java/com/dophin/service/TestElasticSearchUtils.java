package com.dophin.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.MemberDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * es工具类
 * 
 * @author dailiwei
 * 
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestElasticSearchUtils {

	private static ObjectMapper mapper = new ObjectMapper();
	private static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();

	@Autowired
	private ConstantsService constantsService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Test
	public void TestIndexMemberInfo() throws JsonProcessingException {
		MemberDTO memberDTO = memberService.queryMemberInfo("1447261522770");
		System.err.println(memberDTO);
		String result = mapper.writeValueAsString(memberDTO);

		IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, "memberinfo",
				memberDTO.getId(), result);
		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());
		System.err.println("created: " + response.isCreated());
	}

	@Test
	public void testQueryToEs() throws JsonParseException, JsonMappingException, IOException {
		QueryBuilder queryBuilder = null;
		SearchResponse searchResponse = elasticSearchUtils.doSearchAction(CommonUtils.ES_INDEX, "resumeinfo",
				queryBuilder);

		System.err.println("Search hits: " + searchResponse.getHits());
		SearchHits searchHits = searchResponse.getHits();
		System.err.println("totol hits: " + searchHits.getTotalHits());
		SearchHit[] hitArr = searchHits.hits();

		System.err.println(hitArr.length);
		for (SearchHit searchHit : hitArr) {
			System.err.println(searchHit.getSourceAsString());
		}
	}

	@Test
	public void testInsertToEs() throws JsonProcessingException {

		UniversityDTO universityDTO = constantsService.queryUniversity(1);
		System.err.println(universityDTO);

		String jsonResult = mapper.writeValueAsString(universityDTO);
		System.err.println(jsonResult);

		IndexResponse response = elasticSearchUtils.doIndexAction("firstportal", "university", 1, jsonResult);

		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());
		System.err.println("created: " + response.isCreated());

	}

	@Test
	public void testUpatetToEs() throws JsonProcessingException, InterruptedException, ExecutionException {
		UniversityDTO universityDTO = constantsService.queryUniversity(2);
		System.err.println(universityDTO);

		String jsonResult = mapper.writeValueAsString(universityDTO);
		System.err.println(jsonResult);

		elasticSearchUtils.doUpdateAction("firstportal", "university", 1, jsonResult);

	}

	@Test
	public void testFuzzyQuerySearch() throws JsonParseException, JsonMappingException, IOException {

		SearchResponse searchResponse = elasticSearchUtils.doFuzzySearchAction("firstportal", "university", "name",
				"矿业");

		System.err.println("Search hits: " + searchResponse.getHits());
		SearchHits searchHits = searchResponse.getHits();
		System.err.println("totol hits: " + searchHits.getTotalHits());
		SearchHit[] hitArr = searchHits.hits();

		System.err.println(hitArr.length);
		for (SearchHit searchHit : hitArr) {

			UniversityDTO universityDTO = mapper.readValue(searchHit.getSourceAsString(), UniversityDTO.class);
			System.err.println(universityDTO + ":" + searchHit.getScore());
		}
	}

	@Test
	public void testBooleanQuerySearch() throws JsonParseException, JsonMappingException, IOException {

		QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.fuzzyQuery("name", "北京"))
				.should(QueryBuilders.fuzzyQuery("name", "北")).should(QueryBuilders.fuzzyQuery("name", "京"));

		SearchResponse searchResponse = elasticSearchUtils.doSearchAction("firstportal", "university", queryBuilder, 0,
				Integer.MAX_VALUE);

		SearchHits searchHits = searchResponse.getHits();
		SearchHit[] hitArr = searchHits.hits();

		System.err.println(hitArr.length);
		for (SearchHit searchHit : hitArr) {

			UniversityDTO universityDTO = mapper.readValue(searchHit.getSourceAsString(), UniversityDTO.class);
			System.err.println(universityDTO + ":" + searchHit.getScore());
		}

		System.err.println("Search hits: " + searchResponse.getHits());
		System.err.println("totol hits: " + searchHits.getTotalHits());
	}

	@Test
	public void testDeleteToEs() throws JsonProcessingException {
		DeleteResponse response = elasticSearchUtils.doDeleteAction("firstportal", "memberinfo", 4);
		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());
	}

	@Test
	public void testNewUpdateResponseToEs() throws JsonProcessingException {
		UniversityDTO universityDTO = constantsService.queryUniversity(1);
		universityDTO.setName(universityDTO.getName() + System.currentTimeMillis());
		System.err.println(universityDTO);

		String jsonResult = mapper.writeValueAsString(universityDTO);
		System.err.println(jsonResult);

		UpdateResponse response = elasticSearchUtils.doUpdateAction("firstportal", "universitytest", 1, jsonResult);
		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());
		System.err.println("created: " + response.isCreated());
	}

	/**
	 * 初始化单个职位
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitRecruitInfoData() throws JsonProcessingException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("ids", "11,14");
		List<RecruitInfoDTO> ls = recruitInfoService.queryRecruitInfos(queryMap);
		for (RecruitInfoDTO recruitInfoDTO : ls) {
			String result = mapper.writeValueAsString(recruitInfoDTO);
			IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, "recruitinfo",
					recruitInfoDTO.getId(), result);
			System.err.println(response);
		}
	}

	@Test
	public void testInitMemberInfoData() throws JsonProcessingException {

		String membertable = "memberinfo";
		String memberId = "1449714511924";
		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		if (memberDTO != null) {
			String result = mapper.writeValueAsString(memberDTO);
			// elasticSearchUtils.doDeleteAction(CommonUtils.ES_INDEX,
			// "memberinfo", memberDTO.getId());
			elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, "memberinfo", memberDTO.getId(), result);
		}
	}

	/**
	 * 删除职位
	 */
	@Test
	public void testDeleteResumeInfoData() {
		int id = recruitInfoService.deleteRecruitInfo(10);
		System.err.println(id);
	}
}
