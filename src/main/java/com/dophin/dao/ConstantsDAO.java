package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.UniversityDTO;

public interface ConstantsDAO
{
	List<UniversityDTO> queryUniversities(Map<String, Object> queryMap);
	
	UniversityDTO queryUniversity(int id);
	
	List<GeoAreaDTO> queryGeoAreas(Map<String, Object> queryMap);
	
	GeoAreaDTO queryGeoArea(int id);
	
	List<IndustryDTO> queryIndustries(Map<String, Object> queryMap);
	
	IndustryDTO queryIndustry(int id);
	
	List<PositionTypeDTO> queryPositionTypes(Map<String, Object> queryMap);
	
	List<PositionTypeDTO> querySecondPositionTypes();
	
	List<PositionTypeDTO> queryThirdPositionTypes();
	
	PositionTypeDTO queryPositionType(int id);
}
