package com.dophin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.RecruitResumeCountDTO;
import com.dophin.enums.ApplyTypeEnum;
import com.dophin.enums.FeedStatusEnum;
import com.dophin.service.biz.StudentBusinessService;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMemberRecruitService extends TestBaseService
{
	@Autowired
	private MemberRecruitService memberRecruitService;

	@Autowired
	private StudentBusinessService studentBusinessService;

	private static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();

	@Test
	public void testMemberRecruitEs()
	{
		QueryBuilder qb = QueryBuilders.termQuery("applyType", ApplyTypeEnum.Favorite.getCode());
		SearchResponse response = elasticSearchUtils.doSearchAction(CommonUtils.ES_INDEX, "memberrecruit", qb);
		SearchHit[] searchHits = response.getHits().getHits();
		List<Integer> idList = new ArrayList<Integer>();
		for (SearchHit searchHit : searchHits)
		{
			System.err.println(searchHit.getSource());
			int recruitId = NumberUtils.toInt(searchHit.getSource().get("recruitId").toString(), 0);
			if (!idList.contains(recruitId))
			{
				idList.add(recruitId);
			}
		}

		Integer[] idArr = new Integer[idList.size()];
		idList.toArray(idArr);

		System.err.println(Arrays.toString(idArr));

	}
	
	@Test
	public void testBatchInsertMemberRecruit()
	{
		for (int i = 0; i < 22; i++)
		{
			testInsertMemberRecruit();
		}
	}

	@Test
	public void testInsertMemberRecruit()
	{
		int[] resumeIds = new int[] { 19, 20, 21 };
		int[] recruitIds = new int[] { 9, 22 };
		int[] feedStatusArr = new int[] { FeedStatusEnum.Inappropriate.getCode(),
				FeedStatusEnum.InformInterview.getCode(), FeedStatusEnum.NonViewed.getCode(),
				FeedStatusEnum.Undetermined.getCode() };
		Random r = new Random();

		MemberRecruitDTO memberRecruitDTO = new MemberRecruitDTO();
		memberRecruitDTO.setMemberId("1446052088148");
		memberRecruitDTO.setResumeId(resumeIds[r.nextInt(resumeIds.length)]);
		memberRecruitDTO.setRecruitId(recruitIds[r.nextInt(recruitIds.length)]);
		memberRecruitDTO.setFeedStatus(feedStatusArr[r.nextInt(feedStatusArr.length)]);
		memberRecruitDTO.setApplyType(ApplyTypeEnum.Apply.getCode());

		int id = memberRecruitService.insertMemberRecruit(memberRecruitDTO);
		System.err.println(id);
	}

	@Test
	public void testQueryMemberRecruit()
	{
//		List<MemberRecruitDTO> ls = memberRecruitService.queryMemberRecruits("1441704227484", false);
//		for (MemberRecruitDTO item : ls)
//		{
//			System.err.println(item);
//		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", "1449714511924");
		queryMap.put("recruitId", 11);
		queryMap.put("applyType", ApplyTypeEnum.Favorite.getCode());
		MemberRecruitDTO memberRecruitDTO = memberRecruitService.queryMemberRecruitByConditions(queryMap);
		System.err.println(memberRecruitDTO);
	}

	@Test
	public void testUpdateMemberRecruit()
	{
		MemberRecruitDTO memberRecruitDTO = memberRecruitService.queryMemberRecruit(7);
		System.err.println("before: " + memberRecruitDTO);
		memberRecruitDTO.setMemberId("1441704227484");
		memberRecruitDTO.setResumeId(1);
		memberRecruitDTO.setRecruitId(1);
		memberRecruitDTO.setFeedStatus(FeedStatusEnum.NonViewed.getCode());
		memberRecruitDTO.setApplyType(ApplyTypeEnum.Favorite.getCode());

		int count = memberRecruitService.updateMemberRecruit(memberRecruitDTO);
		System.err.println(count);

		MemberRecruitDTO resultDto = memberRecruitService.queryMemberRecruit(7);
		System.err.println("after: " + resultDto);
		System.err.println("after: " + resultDto.getMemberDTO());
		System.err.println("after: " + resultDto.getResumeInfoDTO());
		System.err.println("after: " + resultDto.getRecruitInfoDTO());
	}

	@Test
	public void testDeleteMemberRecruit()
	{
		int count = memberRecruitService.deleteMemberRecruit(1);
		System.err.println(count);
	}

	@Test
	public void testQueryConditionsForMyApply()
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", "1446052088148");
		queryMap.put("feedStatusStr",
				String.format("%s,%s", FeedStatusEnum.NonViewed.getCode(), FeedStatusEnum.Undetermined.getCode()));
		queryMap.put("applyType", ApplyTypeEnum.Apply.getCode());
		List<MemberRecruitDTO> ls = memberRecruitService.queryMemberRecruitsForMyApply(queryMap);

		for (MemberRecruitDTO item : ls)
		{
			System.err.println(item);
		}
	}

	@Test
	public void testQueryByConditions() throws JsonProcessingException
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		List<MemberRecruitDTO> ls = memberRecruitService.queryMemberRecruits(queryMap, false);
		System.err.println(ls.size());
		String result = "";
		for (MemberRecruitDTO item : ls)
		{
			System.err.println(item);

			// 添加索引
			// result = mapper.writeValueAsString(item);
			// IndexResponse response =
			// elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX,
			// "memberrecruit",
			// item.getId(), result);
			// System.err.println(response.getId());
			// System.err.println(response.getIndex());
			// System.err.println(response.getType());
			// System.err.println(response.getVersion());
			// System.err.println(response.isCreated());
		}
	}

	@Test
	public void testQueryByRecruitId()
	{
		List<MemberRecruitDTO> ls = memberRecruitService.queryMemberRecruitsByRecruitId(4, true);
		System.err.println(ls.size());
		for (MemberRecruitDTO item : ls)
		{
			System.err.println(item);
		}
	}

	@Test
	public void testQueryJobResumeApplyCount()
	{
		Map<Integer, RecruitResumeCountDTO> resultMap = memberRecruitService.queryJobResumeApplyCountForMap(null);
		System.err.println(resultMap.size());
		for (Entry<Integer, RecruitResumeCountDTO> item : resultMap.entrySet())
		{
			System.err.println(item.getValue());
		}

	}

}
