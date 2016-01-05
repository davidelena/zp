package com.dophin.service;

import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;

import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestBaseService
{
	private long start = 0;
	private long end = 0;
	protected long timestamp = System.currentTimeMillis();
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
	protected static String index = CommonUtils.ES_INDEX;
	protected static String RECRUIT_INFO = "recruitinfo";
	protected static String RESUME_INFO = "resumeinfo";
	protected static final String MEMBER_INFO = "memberinfo";
	protected static final String COMPANY_INFO = "companyinfo";
	protected static final String STUDENT_INFO = "studentinfo";
	
	protected static final String GEO_AREA = "geoarea";
	protected static final String INDUSTRY = "industry";
	protected static final String POSITION_TYPE = "positiontype";
	protected static final String UNIVERSITY = "university";
	protected static final String MEMBER_RECRUIT = "memberrecruit";
	protected static ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception
	{
		start = System.currentTimeMillis();
	}

	@After
	public void tearDown() throws Exception
	{
		end = System.currentTimeMillis();
		System.err.println("time: " + (end - start));
	}

}
