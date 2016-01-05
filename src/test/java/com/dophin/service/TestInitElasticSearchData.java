package com.dophin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.CompanyInfoDTO;
import com.dophin.dto.EsBulkCondition;
import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.StudentInfoDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestInitElasticSearchData extends TestBaseService {
	@Autowired
	private MemberService memberService;

	@Autowired
	private CompanyMemberService companyMemberService;

	@Autowired
	private StudentMemberService studentMemberService;

	@Autowired
	private ConstantsService constantsService;

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private MemberRecruitService memberRecruitService;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Test
	public void testInitIndexResumeInfos() throws JsonProcessingException {
		List<Integer> resumeIds = Arrays.asList(new Integer[] { 1, 2, 6 });
		String ids = StringUtils.join(resumeIds, ",");
		List<ResumeInfoDTO> ls = resumeInfoService.queryResumeInfosByIds(ids);
		for (ResumeInfoDTO item : ls) {
			String result = mapper.writeValueAsString(item);
			IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, RESUME_INFO, item.getId(),
					result);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
	}

	/**
	 * 初始化3个member对象
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexMemberInfos() throws JsonProcessingException {
		String[] memberIds = new String[] { "1449714511924", "1449761399825" };
		for (String memberId : memberIds) {
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			System.err.println(memberDTO);
			String result = mapper.writeValueAsString(memberDTO);

			IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, MEMBER_INFO,
					memberDTO.getId(), result);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
	}

	/**
	 * 初始化对应的公司对象
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexCompanyInfos() throws JsonProcessingException {
		int[] companyIds = new int[] { 1, 2, 6, 7, 8 };
		for (Integer companyId : companyIds) {
			CompanyInfoDTO companyInfoDTO = companyMemberService.queryCompanyInfo(companyId);
			System.err.println(companyInfoDTO);
			String result = mapper.writeValueAsString(companyInfoDTO);

			IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, COMPANY_INFO,
					companyInfoDTO.getId(), result);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
	}

	/**
	 * 招聘信息
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexRecruitInfos() throws JsonProcessingException {
		int[] recruitIds = new int[] { 35, 36, 38, 39, 40, 41, 42 };
		for (int recruitId : recruitIds) {
			RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(recruitId);
			String result = mapper.writeValueAsString(recruitInfoDTO);

			IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, RECRUIT_INFO,
					recruitInfoDTO.getId(), result);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}

	}

	@Test
	public void testInitIndexRecruitInfoNew() throws JsonProcessingException {
		int id = 3;
		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(id);
		String result = mapper.writeValueAsString(recruitInfoDTO);
		System.err.println(result);
		IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, RECRUIT_INFO, id, result);
		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());
		System.err.println("created: " + response.isCreated());
	}

	/**
	 * 初始化学生用户对象
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexStudentInfos() throws JsonProcessingException {
		int[] studentIds = new int[] { 15 };
		for (Integer studentId : studentIds) {
			StudentInfoDTO studentInfoDTO = studentMemberService.queryStudentInfo(studentId);
			System.err.println(studentInfoDTO);
			String result = mapper.writeValueAsString(studentInfoDTO);

			IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, STUDENT_INFO,
					studentInfoDTO.getId(), result);
			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
	}

	/**
	 * 初始化地域信息
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexGeoAreas() throws JsonProcessingException {
		List<GeoAreaDTO> geoAreaDTOs = constantsService.queryGeoAreas(null);
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		String result = StringUtils.EMPTY;
		for (GeoAreaDTO geoAreaDTO : geoAreaDTOs) {
			result = mapper.writeValueAsString(geoAreaDTO);
			esBulkConditions.add(new EsBulkCondition(geoAreaDTO.getId(), result));
		}
		BulkResponse bulkResponse = elasticSearchUtils.doBulkIndexAction(CommonUtils.ES_INDEX, GEO_AREA,
				esBulkConditions);
		System.err.println(bulkResponse.getItems().length);
		System.err.println(geoAreaDTOs.size());
	}

	/**
	 * 初始化行业分类信息
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexIndustries() throws JsonProcessingException {
		List<IndustryDTO> industryDTOs = constantsService.queryIndustries(null);
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		String result = StringUtils.EMPTY;
		for (IndustryDTO industryDTO : industryDTOs) {
			result = mapper.writeValueAsString(industryDTO);
			esBulkConditions.add(new EsBulkCondition(industryDTO.getId(), result));
		}
		BulkResponse bulkResponse = elasticSearchUtils.doBulkIndexAction(CommonUtils.ES_INDEX, INDUSTRY,
				esBulkConditions);
		System.err.println(bulkResponse.getItems().length);
		System.err.println(industryDTOs.size());
	}

	/**
	 * 初始化职位分类信息
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexPositionTypes() throws JsonProcessingException {
		List<PositionTypeDTO> positionTypeDTOs = constantsService.queryPositionTypes(null);
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		String result = StringUtils.EMPTY;
		for (PositionTypeDTO positionTypeDTO : positionTypeDTOs) {
			result = mapper.writeValueAsString(positionTypeDTO);
			esBulkConditions.add(new EsBulkCondition(positionTypeDTO.getId(), result));
		}
		BulkResponse bulkResponse = elasticSearchUtils.doBulkIndexAction(CommonUtils.ES_INDEX, POSITION_TYPE,
				esBulkConditions);
		System.err.println(bulkResponse.getItems().length);
		System.err.println(positionTypeDTOs.size());
	}

	/**
	 * 初始化大学信息
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitIndexUniversities() throws JsonProcessingException {
		List<UniversityDTO> universityDTOs = constantsService.queryUniversities();
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		String result = StringUtils.EMPTY;
		for (UniversityDTO universityDTO : universityDTOs) {
			result = mapper.writeValueAsString(universityDTO);
			esBulkConditions.add(new EsBulkCondition(universityDTO.getId(), result));
		}
		BulkResponse bulkResponse = elasticSearchUtils.doBulkIndexAction(CommonUtils.ES_INDEX, UNIVERSITY,
				esBulkConditions);
		System.err.println(bulkResponse.getItems().length);
		System.err.println(universityDTOs.size());
	}

	@Test
	public void testInitIndexMemberRecruits() throws JsonProcessingException {
		MemberRecruitDTO memberRecruitDTO = memberRecruitService.queryMemberRecruit(8);
		String result = mapper.writeValueAsString(memberRecruitDTO);
		IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, MEMBER_RECRUIT,
				memberRecruitDTO.getId(), result);
		System.err.println(response.isCreated());
	}
}
