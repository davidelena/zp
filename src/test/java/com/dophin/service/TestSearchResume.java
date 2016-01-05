package com.dophin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
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

import com.dophin.dto.FilterResumeInfoCondition;
import com.dophin.dto.ResumeEducationExpDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.service.biz.SearchResumeInfoService;
import com.dophin.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 企业搜索简历库
 * 
 * @author dailiwei
 * 
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSearchResume extends TestBaseService {

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Test
	public void testShowDate() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(1444233600000l);
		System.err.println(sdf.format(c.getTime()));

		Calendar c2 = Calendar.getInstance();
		c2.set(2015, 9, 18, 23, 59, 59);
		Date validityTime = c2.getTime();
		validityTime = DateUtils.addDays(validityTime, 3);
		System.err.println(sdf.format(validityTime));
	}

	/**
	 * 初始化测试数据
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitResumeInfoData() throws JsonProcessingException {
		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos();
		for (ResumeInfoDTO resumeInfoDTO : resumeInfoDTOs) {
			String result = mapper.writeValueAsString(resumeInfoDTO);

			IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, "resumeinfo",
					resumeInfoDTO.getId(), result);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
	}

	/**
	 * 测试企业搜索简历库
	 */
	@Test
	public void searchResumeInfoService() {
		FilterResumeInfoCondition condition = new FilterResumeInfoCondition();

		SearchResumeInfoService service = new SearchResumeInfoService();
		// condition.setSchoolId(777);
		// condition.setDiploma(3);
		// condition.setGraduationYear(2015);
		// condition.setSex(1);
		// condition.setSearchTxt("同济");
		// condition.setHasWorkExp(true);
		// condition.setMajor(6); //2,6,8,9
		// condition.setDemandCity(503); //501,502,503
		// condition.setDemandIndustry(38); //18,28,38

		List<ResumeInfoDTO> ls = service.searchResumeInfoWithConditions(condition);
		System.err.println("size: " + ls.size());
		for (ResumeInfoDTO item : ls) {
			System.err.println(String.format("%d,%s - %d - %d - %d - %d", item.getId(), item.getResumeName(), item
					.getMemberDTO().getSchoolId(), item.getDiploma(), item.getGraduationYear(), item.getSex()));
		}
	}

	/**
	 * 通过简历基本信息-学校
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeBySchool() throws JsonParseException, JsonMappingException, IOException {

		int schoolId = 776;
		QueryBuilder queryBuilder = QueryBuilders.termQuery("memberDTO.schoolId", schoolId);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getId());
			System.err.println(resumeInfoDTO.getMemberDTO().getUniversityDTO());
		}

	}

	/**
	 * 通过简历基本信息-学历
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeByDiploma() throws JsonParseException, JsonMappingException, IOException {
		int diploma = 4;
		QueryBuilder queryBuilder = QueryBuilders.termQuery("diploma", diploma);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getDiploma());
			System.err.println(resumeInfoDTO.getDiplomaDesc());
		}
	}

	/**
	 * 通过简历基本信息-毕业时间（毕业年份）
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeByGraduationTime() throws JsonParseException, JsonMappingException, IOException {
		Calendar graduationTime = Calendar.getInstance();
		graduationTime.set(2014, 9, 20, 0, 0, 0);
		// 去除时分秒
		graduationTime = DateUtils.truncate(graduationTime, Calendar.DATE);
		// long st = graduationTime.getTime().getTime();

		int graduationYear = graduationTime.get(Calendar.YEAR);
		System.err.println(graduationYear);

		// QueryBuilder queryBuilder = QueryBuilders.termQuery("graduationTime",
		// st);
		QueryBuilder queryBuilder = QueryBuilders.termQuery("graduationYear", graduationYear);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getId());
			System.err.println(sdf.format(resumeInfoDTO.getGraduationTime()));
		}
	}

	/**
	 * 通过简历基本信息-性别
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeBySex() throws JsonParseException, JsonMappingException, IOException {
		int sex = 2;
		QueryBuilder queryBuilder = QueryBuilders.termQuery("sex", sex);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getSex());
			System.err.println(resumeInfoDTO.getSexDesc());
		}
	}

	/**
	 * 通过简历教育经历-专业分类
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeByMajorType() throws JsonParseException, JsonMappingException, IOException {
		int majorType = 9; // 2,6,8,9
		QueryBuilder queryBuilder = QueryBuilders.termQuery("resumeEducationExpDTOs.majorType", majorType);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getId());
			List<Integer> majorTypes = new ArrayList<>();
			for (ResumeEducationExpDTO item : resumeInfoDTO.getResumeEducationExpDTOs()) {
				majorTypes.add(item.getMajorType());
			}

			System.err.println(String.format("%s: %s", resumeInfoDTO.getId(), majorTypes));
		}
	}

	/**
	 * 通过学生求职意向表-期望行业
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeByDemandIndustry() throws JsonParseException, JsonMappingException, IOException {
		QueryBuilder queryBuilder = QueryBuilders.termsQuery("memberDTO.studentInfoDTO.demandIndustries.id",
				new String[] { "16", "26", "18" });

		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getId());
		}
	}

	/**
	 * 通过学生求职意向-期望城市
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testSearchResumeByDemandCity() throws JsonParseException, JsonMappingException, IOException {

		QueryBuilder queryBuilder = QueryBuilders.termsQuery("memberDTO.studentInfoDTO.demandCitys.id", new String[] {
				"501", "2600" });

		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getId());
		}

	}

	/**
	 * 搜索有实习经历的简历
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeByWorkExp() throws JsonParseException, JsonMappingException, IOException {

		QueryBuilder queryBuilder = QueryBuilders.termQuery("resumeWorkExpDTOs.status", 1);
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);
		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getId());
			System.err.println(resumeInfoDTO.getResumeWorkExpDTOs());
		}

	}

	/**
	 * 搜索没有实习经历的简历
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testSearchResumeByWorkNonExp() throws JsonParseException, JsonMappingException, IOException {

		QueryBuilder queryBuilder = QueryBuilders.boolQuery().mustNot(
				QueryBuilders.termQuery("resumeEducationExpDTOs.status", 1));
		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);
		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO.getId());
			System.err.println(resumeInfoDTO.getResumeWorkExpDTOs());
		}

	}

	/**
	 * 通过学校名称，简称，学校类别
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeByFuzzyQueryWithSchool() throws JsonParseException, JsonMappingException, IOException {
		String name = "c9";
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.fuzzyQuery("memberDTO.universityDTO.name", name))
				.should(QueryBuilders.fuzzyQuery("memberDTO.universityDTO.abbreviation", name))
				.should(QueryBuilders.fuzzyQuery("memberDTO.universityDTO.tag", name));

		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO);
			System.err.println(resumeInfoDTO.getMemberDTO().getUniversityDTO().getName());
		}
	}

	/**
	 * 通过学历描述搜索
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void testSearchResumeByFuzzyQueryWithDiphoma() throws JsonParseException, JsonMappingException, IOException {
		String name = "职高";
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.fuzzyQuery("diplomaDesc", name));

		SearchResponse response = elasticSearchUtils.doSearchAction(index, RESUME_INFO, queryBuilder);

		SearchHits searchHits = response.getHits();

		SearchHit[] arr = searchHits.getHits();
		for (SearchHit searchHit : arr) {
			System.err.println(searchHit.getSourceAsString());
			ResumeInfoDTO resumeInfoDTO = mapper.readValue(searchHit.getSourceAsString(), ResumeInfoDTO.class);
			System.err.println(resumeInfoDTO);
			System.err.println(resumeInfoDTO.getMemberDTO().getUniversityDTO().getName());
		}
	}

}
