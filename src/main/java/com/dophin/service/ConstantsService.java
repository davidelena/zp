package com.dophin.service;

import java.util.List;
import java.util.Map;

import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.UniversityDTO;

/**
 * 常量服务
 * @author David.dai
 *
 */
public interface ConstantsService
{
	List<UniversityDTO> queryUniversities();
	
	List<UniversityDTO> queryUniversities(Integer geoId);
	
	List<UniversityDTO> queryUniversities(Map<String, Object> queryMap);
	
	UniversityDTO queryUniversity(int id);
	
	/**
	 * parentId=0是省份
	 * @param queryMap
	 * @return
	 */
	List<GeoAreaDTO> queryGeoAreas(Map<String, Object> queryMap);
	
	List<GeoAreaDTO> queryGeoAreas(int parentId);
	
	GeoAreaDTO queryGeoArea(int id);
	
	List<IndustryDTO> queryIndustries(Map<String, Object> queryMap);
	
	List<IndustryDTO> queryIndustries(int parentId);
	
	IndustryDTO queryIndustry(int id);
	
	List<PositionTypeDTO> queryPositionTypes(Map<String, Object> queryMap);
	
	List<PositionTypeDTO> querySecondPositionTypes();
	
	List<PositionTypeDTO> queryThirdPositionTypes();
	
	PositionTypeDTO queryPositionType(int id);
}
