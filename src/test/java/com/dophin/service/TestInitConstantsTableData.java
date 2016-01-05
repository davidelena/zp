package com.dophin.service;

import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 初始化长量表数据至ElasticeSearch存储
 * 
 * @author David.dai
 * 
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestInitConstantsTableData
{
	private static ObjectMapper mapper = new ObjectMapper();
	private static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
	private static String INIT_INDEX = CommonUtils.ES_INDEX;

	@Autowired
	private ConstantsService constantsService;

	/**
	 * 初始化大学常量数据到es搜索引擎中
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitUniversityInfoToEs() throws JsonProcessingException
	{

		List<UniversityDTO> universityDTOs = constantsService.queryUniversities();

		for (UniversityDTO universityDTO : universityDTOs)
		{
			String jsonResult = mapper.writeValueAsString(universityDTO);
			System.err.println(jsonResult);

			IndexResponse response = elasticSearchUtils.doIndexAction(INIT_INDEX, "university", universityDTO.getId(),
					jsonResult);

			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
	}

	/**
	 * 初始化行业分析数据到es搜索引擎中
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndustryInfoToEs() throws JsonProcessingException
	{
		List<IndustryDTO> industryDTOs = constantsService.queryIndustries(new HashMap<String, Object>());

		for (IndustryDTO industryDTO : industryDTOs)
		{
			String jsonResult = mapper.writeValueAsString(industryDTO);
			System.err.println(jsonResult);

			IndexResponse response = elasticSearchUtils.doIndexAction(INIT_INDEX, "industry", industryDTO.getId(),
					jsonResult);

			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
	}

	/**
	 * 初始化地域分析数据到es搜索引擎中
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitGeoAreaInfoToEs() throws JsonProcessingException
	{
		List<GeoAreaDTO> geoAreaDTOs = constantsService.queryGeoAreas(new HashMap<String, Object>());

		for (GeoAreaDTO geoAreaDTO : geoAreaDTOs)
		{
			String jsonResult = mapper.writeValueAsString(geoAreaDTO);
			System.err.println(jsonResult);

			IndexResponse response = elasticSearchUtils.doIndexAction(INIT_INDEX, "geoarea", geoAreaDTO.getId(),
					jsonResult);

			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}

		System.err.println(geoAreaDTOs.size());
	}

	/**
	 * 初始化职位分类数据到es搜索引擎中
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitPositionTypeInfoToEs() throws JsonProcessingException
	{
		List<PositionTypeDTO> positionTypeDTOs = constantsService.queryPositionTypes(new HashMap<String, Object>());

		for (PositionTypeDTO positionTypeDTO : positionTypeDTOs)
		{
			String jsonResult = mapper.writeValueAsString(positionTypeDTO);
			System.err.println(jsonResult);

			IndexResponse response = elasticSearchUtils.doIndexAction(INIT_INDEX, "positiontype",
					positionTypeDTO.getId(), jsonResult);

			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}

		System.err.println(positionTypeDTOs.size());
	}

	/**
	 * 删除脏数据
	 *//*
	@Test
	public void deleteDirtyData()
	{
		for (int i = 0; i < 400; i++)
		{
			DeleteResponse response = elasticSearchUtils.doDeleteAction(INIT_INDEX, "geoarea", (i + 1));
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
		}

	}*/
}
