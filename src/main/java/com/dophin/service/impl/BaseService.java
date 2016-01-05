package com.dophin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;

import com.dophin.dto.EsBulkCondition;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 业务基类
 * 
 * @author dailiwei
 * 
 */
public class BaseService
{

	protected static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();

	protected static ObjectMapper mapper = new ObjectMapper();

	protected static Logger logger = Logger.getLogger(BaseService.class);

	protected static final String ES_COMPANY_INFO = "companyinfo";

	protected static final String ES_RECRUIT_INFO = "recruitinfo";

	protected static final String ES_STUDENT_INFO = "studentinfo";

	protected static final String ES_RESUME_INFO = "resumeinfo";

	/**
	 * 执行索引到elasticsearch的操作
	 * 
	 * @param resultJson
	 *            序列化完成的json结果
	 * @param id
	 *            es中的记录主键id
	 * @param type
	 *            类型
	 */
	protected void doIndexAction(String resultJson, int id, String type)
	{
		IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, type, id, resultJson);
		logger.info(String.format("%d is created: %s", id, response.isCreated()));
	}

	/**
	 * 执行更新索引到elasticsearch操作
	 * 
	 * @param resultJson
	 *            序列化完成的json结果
	 * @param id
	 *            es中的记录主键id
	 * @param type
	 *            类型
	 */
	protected void doUpdateAction(String resultJson, int id, String type)
	{
		elasticSearchUtils.doUpdateAction(CommonUtils.ES_INDEX, type, id, resultJson);
		logger.info(String.format("%d is updated", id));
	}

	/**
	 * 执行删除索引到elasticsearch操作
	 * 
	 * @param id
	 *            es中的记录主键id
	 * @param type
	 *            类型
	 */
	protected void doDeleteAction(int id, String type)
	{
		DeleteResponse response = elasticSearchUtils.doDeleteAction(CommonUtils.ES_INDEX, type, id);
		logger.info(String.format("%d is deleted: %s", id, response.isFound()));
	}

	/**
	 * 对简历进行更新
	 * 
	 * @throws JsonProcessingException
	 */
	protected void updateResumeEsIndex(ResumeInfoDTO resumeInfoDTO) throws JsonProcessingException
	{
		String result = mapper.writeValueAsString(resumeInfoDTO);
		doIndexAction(result, resumeInfoDTO.getId(), ES_RESUME_INFO);
	}

	/**
	 * 对简历信息进行批量的更新
	 * 
	 * @param resumeInfoDTOs
	 * @throws JsonProcessingException
	 */
	protected void batchUpdateResumeEsIndex(List<ResumeInfoDTO> resumeInfoDTOs) throws JsonProcessingException
	{
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		String result = "";
		for (ResumeInfoDTO resumeInfoDTO : resumeInfoDTOs)
		{
			result = mapper.writeValueAsString(resumeInfoDTO);
			esBulkConditions.add(new EsBulkCondition(resumeInfoDTO.getId(), result));
		}
		elasticSearchUtils.doBulkIndexAction(CommonUtils.ES_INDEX, ES_RESUME_INFO, esBulkConditions);
	}
}
