package com.dophin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dophin.dao.ConstantsDAO;
import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.service.ConstantsService;

@Service
public class ConstantsServiceImpl implements ConstantsService
{
	@Autowired
	private ConstantsDAO constantsDAO;

	@Override
	public List<UniversityDTO> queryUniversities()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		return constantsDAO.queryUniversities(queryMap);
	}

	@Override
	public UniversityDTO queryUniversity(int id)
	{
		return constantsDAO.queryUniversity(id);
	}

	@Override
	public List<GeoAreaDTO> queryGeoAreas(Map<String, Object> queryMap)
	{
		return constantsDAO.queryGeoAreas(queryMap);
	}

	@Override
	public GeoAreaDTO queryGeoArea(int id)
	{
		return constantsDAO.queryGeoArea(id);
	}

	@Override
	public List<IndustryDTO> queryIndustries(Map<String, Object> queryMap)
	{
		return constantsDAO.queryIndustries(queryMap);
	}

	@Override
	public IndustryDTO queryIndustry(int id)
	{
		return constantsDAO.queryIndustry(id);
	}

	@Override
	public List<PositionTypeDTO> queryPositionTypes(Map<String, Object> queryMap)
	{
		return constantsDAO.queryPositionTypes(queryMap);
	}

	@Override
	public PositionTypeDTO queryPositionType(int id)
	{
		return constantsDAO.queryPositionType(id);
	}

	@Override
	public List<IndustryDTO> queryIndustries(int parentId)
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", parentId);
		return queryIndustries(queryMap);
	}

	@Override
	public List<GeoAreaDTO> queryGeoAreas(int parentId)
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parentId", parentId);
		return queryGeoAreas(queryMap);
	}

	@Override
	public List<PositionTypeDTO> querySecondPositionTypes()
	{
		return constantsDAO.querySecondPositionTypes();
	}

	@Override
	public List<PositionTypeDTO> queryThirdPositionTypes()
	{
		return constantsDAO.queryThirdPositionTypes();
	}

	@Override
	public List<UniversityDTO> queryUniversities(Map<String, Object> queryMap)
	{
		return constantsDAO.queryUniversities(queryMap);
	}

	@Override
	public List<UniversityDTO> queryUniversities(Integer geoId)
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("geoId", geoId);
		return constantsDAO.queryUniversities(queryMap);
	}

}
