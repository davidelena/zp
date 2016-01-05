package com.dophin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestConstantsService extends TestBaseService
{

	@Autowired
	private ConstantsService constantsService;
	
	@Test
	public void getUniversitiesByGeo()
	{
		List<UniversityDTO> ls = constantsService.queryUniversities(300);
		for (UniversityDTO item : ls)
		{
			System.err.println(item);
		}
		System.err.println(ls.size());
	}
	
	@Test
	public void testUniversityMap()
	{
		Map<String, List<UniversityDTO>> resultMap = getUniversityMap();
		for (Entry<String, List<UniversityDTO>> kv : resultMap.entrySet())
		{
			System.err.println("=========" + kv.getKey() + "=============");
			for (UniversityDTO item : kv.getValue())
			{
				System.err.println(item);
			}
		}
	}

	private Map<String, List<UniversityDTO>> getUniversityMap()
	{
		Map<String, List<UniversityDTO>> resultMap = new HashMap<String, List<UniversityDTO>>();
		List<UniversityDTO> ls = constantsService.queryUniversities();
		for (UniversityDTO item : ls)
		{
			if (!resultMap.containsKey(item.getName()))
			{
				resultMap.put(item.getName(), new ArrayList<UniversityDTO>());
			}

			resultMap.get(item.getName()).add(item);
		}

		return resultMap;
	}

	@Test
	public void getPositionTypeTest()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 0);
		queryMap.put("subParentId", 0);
		List<PositionTypeDTO> ls = constantsService.queryPositionTypes(queryMap);
		Map<Integer, String> parentMap = new HashMap<Integer, String>();
		Map<Integer, String> subParentMap = new HashMap<Integer, String>();

		for (PositionTypeDTO parentItem : ls)
		{
			if (!parentMap.containsKey(parentItem.getId()))
			{
				parentMap.put(parentItem.getId(), parentItem.getName());
			}
		}

		List<PositionTypeDTO> secondLs = constantsService.querySecondPositionTypes();
		for (PositionTypeDTO subParentItem : secondLs)
		{
			if (!parentMap.containsKey(subParentItem.getId()))
			{
				subParentMap.put(subParentItem.getId(), subParentItem.getName());
			}
		}

		Map<String, List<PositionTypeDTO>> resultMap = new TreeMap<String, List<PositionTypeDTO>>();
		List<PositionTypeDTO> thirdLs = constantsService.queryThirdPositionTypes();
		String parentItemName = "";
		String subParentItemName = "";
		String key = "";
		for (PositionTypeDTO item : thirdLs)
		{
			parentItemName = parentMap.containsKey(item.getParentId()) ? parentMap.get(item.getParentId()) : "";
			subParentItemName = subParentMap.containsKey(item.getSubParentId()) ? subParentMap.get(item
					.getSubParentId()) : "";
			key = String.format("%s-%s", parentItemName, subParentItemName);

			if (!resultMap.containsKey(key))
			{
				resultMap.put(key, new ArrayList<PositionTypeDTO>());
			}

			resultMap.get(key).add(item);
		}

		for (Entry<String, List<PositionTypeDTO>> kv : resultMap.entrySet())
		{
			System.err.println("==============" + kv.getKey() + "=================");
			for (PositionTypeDTO item : kv.getValue())
			{
				System.err.println(item);
			}
		}
	}

	@Test
	public void testQueryUniversities()
	{
		List<UniversityDTO> ls = constantsService.queryUniversities();
		for (UniversityDTO item : ls)
		{
			System.err.println(item);
		}
		System.err.println(ls.size());
	}

	@Test
	public void testQueryUniversity()
	{
		UniversityDTO item = constantsService.queryUniversity(3);
		System.err.println(item);
	}

	@Test
	public void testQueryGeoAreas()
	{

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 0);
		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(queryMap);
		for (GeoAreaDTO item : ls)
		{
			System.err.println(item);
		}
		System.err.println(ls.size());
	}

	@Test
	public void testQueryCityWithParent()
	{

		Map<String, List<GeoAreaDTO>> resultMap = new HashMap<>();
		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(0);
		for (GeoAreaDTO item : ls)
		{
			if (!resultMap.containsKey(item.getName()))
			{
				if (!item.isHotCity())
				{
					resultMap.put(item.getName(), new ArrayList<GeoAreaDTO>());
				}
			}
			List<GeoAreaDTO> childLs = constantsService.queryGeoAreas(item.getId());
			for (GeoAreaDTO geoAreaDTO : childLs)
			{

				if (!geoAreaDTO.isHotCity())
				{
					resultMap.get(item.getName()).add(geoAreaDTO);
				}
			}
		}

		for (Entry<String, List<GeoAreaDTO>> kv : resultMap.entrySet())
		{
			System.err.println(kv.getKey());
			for (GeoAreaDTO geoAreaDTO : kv.getValue())
			{
				System.err.println(geoAreaDTO);
			}
		}
	}

	@Test
	public void testGetHotCitys()
	{
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("isHotCity", 0);
		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(queryMap);
		for (GeoAreaDTO item : ls)
		{
			System.err.println(item);
		}
		System.err.println(ls.size());
	}

	@Test
	public void testQueryGeoAreasFilterCityIds()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("cityIds", "300,400,2600");
		List<GeoAreaDTO> ls = constantsService.queryGeoAreas(queryMap);
		for (GeoAreaDTO item : ls)
		{
			System.err.println(item);
		}
		System.err.println(ls.size());
	}

	@Test
	public void testQueryGeoArea()
	{
		GeoAreaDTO item = constantsService.queryGeoArea(501);
		System.err.println(item);

		GeoAreaDTO item2 = constantsService.queryGeoArea(2600);
		System.err.println(item2);
	}

	@Test
	public void testQueryIndustries()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 19);
		List<IndustryDTO> ls = constantsService.queryIndustries(queryMap);
		for (IndustryDTO item : ls)
		{
			System.err.println(item);
		}
		System.err.println(ls.size());
	}

	@Test
	public void testQueryIndustriesWithParent()
	{
		Map<String, List<IndustryDTO>> resultMap = new HashMap<>();
		List<IndustryDTO> ls = constantsService.queryIndustries(0);
		for (IndustryDTO item : ls)
		{
			if (!resultMap.containsKey(item.getName()))
			{
				resultMap.put(item.getName(), new ArrayList<IndustryDTO>());
			}
			List<IndustryDTO> childLs = constantsService.queryIndustries(item.getId());
			for (IndustryDTO industryDTO : childLs)
			{
				resultMap.get(item.getName()).add(industryDTO);
			}
		}

		for (Entry<String, List<IndustryDTO>> kv : resultMap.entrySet())
		{
			System.err.println(kv.getKey());
			for (IndustryDTO industryDTO : kv.getValue())
			{
				System.err.println(industryDTO);
			}
		}
	}

	@Test
	public void testQueryIndustriesFilterIds()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("industryIds", "18,28,38");
		List<IndustryDTO> ls = constantsService.queryIndustries(queryMap);
		for (IndustryDTO item : ls)
		{
			System.err.println(item);
		}
		System.err.println(ls.size());
	}

	@Test
	public void testQueryIndustry()
	{
		IndustryDTO item = constantsService.queryIndustry(18);
		System.err.println(item);
	}

	@Test
	public void testPositionType()
	{
		PositionTypeDTO positionTypeDTO = constantsService.queryPositionType(88);
		System.err.println(positionTypeDTO);
	}

	@Test
	public void testPositionTypeByParent()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 0);
		queryMap.put("subParentId", 0);
		List<PositionTypeDTO> positionTypeDTOs = constantsService.queryPositionTypes(queryMap);
		for (PositionTypeDTO item : positionTypeDTOs)
		{
			System.err.println(item);
		}
	}

	@Test
	public void testPositionTypeBySubParent()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 91);
		queryMap.put("subParentId", 0);
		List<PositionTypeDTO> positionTypeDTOs = constantsService.queryPositionTypes(queryMap);
		for (PositionTypeDTO item : positionTypeDTOs)
		{
			System.err.println(item);
		}
	}

	@Test
	public void testPositionTypes()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", 91);
		queryMap.put("subParentId", 110);
		List<PositionTypeDTO> positionTypeDTOs = constantsService.queryPositionTypes(queryMap);
		for (PositionTypeDTO item : positionTypeDTOs)
		{
			System.err.println(item);
		}
	}

}
