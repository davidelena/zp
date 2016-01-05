package com.dophin.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dao.ResumeSocialNetDAO;
import com.dophin.dto.ResumeSocialNetDTO;
import com.dophin.enums.SocialNetworkEnum;
import com.dophin.service.impl.ResumeSocialNetServiceImpl;
import com.dophin.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestResumeSocialNet extends TestBaseService {

	@Autowired
	private ResumeSocialNetService resumeSocialNetService;

	@Autowired
	private ResumeSocialNetDAO resumeSocialNetDAO;

	private ResumeSocialNetServiceImpl serviceImpl = new ResumeSocialNetServiceImpl();

	@Test
	public void testInsertResumeSocialNet() {
		ResumeSocialNetDTO resumeSocialNetDTO = new ResumeSocialNetDTO();
		resumeSocialNetDTO.setResumeId(2);
		resumeSocialNetDTO.setAccount(SocialNetworkEnum.Douban.getCode());
		resumeSocialNetDTO.setUrl("社交网页网址" + timestamp);

		int id = resumeSocialNetService.insertResumeSocialNet(resumeSocialNetDTO);
		System.err.println(id);
	}

	@Test
	public void testBatchInsert() {
		List<ResumeSocialNetDTO> list = Arrays.asList(new ResumeSocialNetDTO[] {
				new ResumeSocialNetDTO(0, 2, SocialNetworkEnum.Douban.getCode(), "", SocialNetworkEnum.Douban.getDesc() + timestamp, null, null, 1),
				new ResumeSocialNetDTO(0, 2, SocialNetworkEnum.Weibo.getCode(), "", SocialNetworkEnum.Weibo.getDesc() + timestamp, null, null, 2), });

		try {
			int count = resumeSocialNetDAO.batchInsertResumeSocialNet(list);
			System.err.println("affect count: " + count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBatchDelete() {
		List<Integer> ids = Arrays.asList(new Integer[] { 57, 58 });
//		resumeSocialNetDAO.batchDeleteResumeSocialNet(ids);
		
		for (Integer id : ids) {
			int count = resumeSocialNetDAO.deleteResumeSocialNet(id);
		}
	}
	
	@Test
	public void testBatchUpdate() throws SQLException {	
		
//		ComboPooledDataSource dataSource = (ComboPooledDataSource) CommonUtils.getApplicationContext().getBean("dataSource");
//		System.err.println(dataSource.getJdbcUrl());
//		System.err.println(dataSource.getConnection().getClientInfo());
//		List<ResumeSocialNetDTO> list = Arrays.asList(new ResumeSocialNetDTO[] {
//				new ResumeSocialNetDTO(57, 2, SocialNetworkEnum.Douban.getCode(), "", SocialNetworkEnum.Douban.getDesc() + timestamp, null, null, 1),
//				new ResumeSocialNetDTO(58, 2, SocialNetworkEnum.Weibo.getCode(), "", SocialNetworkEnum.Weibo.getDesc() + timestamp, null, null, 2)
//				});
		List<ResumeSocialNetDTO> list = resumeSocialNetService.queryResumeSocialNets(19);
		resumeSocialNetService.batchUpdateResumeSocialNet(list);
	}

	@Test
	public void testQueryResumeSocialNet() throws JsonProcessingException {
		int id = 52;
		ResumeSocialNetDTO resumeSocialNetDTO = resumeSocialNetService.queryResumeSocialNet(id);
		System.err.println(resumeSocialNetDTO);
		System.err.println(resumeSocialNetDTO.getResumeInfoDTO());

		String result = mapper.writeValueAsString(resumeSocialNetDTO);
		System.err.println(result);
		IndexResponse response = elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX, "resumesocialnet", id, result);
		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());
		System.err.println("created: " + response.isCreated());

	}

	@Test
	public void testQueryResumeSocialNets() {
		List<ResumeSocialNetDTO> resumeSocialNetDTOs = resumeSocialNetService.queryResumeSocialNets(1);
		System.err.println(resumeSocialNetDTOs.size());
		for (ResumeSocialNetDTO resumeSocialNetDTO : resumeSocialNetDTOs) {
			System.err.println(resumeSocialNetDTO);
		}
	}

	@Test
	public void testUpdateResumeSocialNet() {
		int id = 8;
		ResumeSocialNetDTO resumeSocialNetDTO = resumeSocialNetService.queryResumeSocialNet(id);
		System.err.println("before: " + resumeSocialNetDTO);

		resumeSocialNetDTO.setAccount(SocialNetworkEnum.Weibo.getCode());
		resumeSocialNetDTO.setUrl("更新后社交网址" + timestamp);
		resumeSocialNetDTO.setResumeId(2);

		int count = resumeSocialNetService.updateResumeSocialNet(resumeSocialNetDTO);
		System.err.println(count);

		ResumeSocialNetDTO result = resumeSocialNetService.queryResumeSocialNet(id);
		System.err.println("after: " + result);
	}

	@Test
	public void testDeleteResumeSocialNet() {
		resumeSocialNetService.deleteResumeSocialNet(52);
	}

}
