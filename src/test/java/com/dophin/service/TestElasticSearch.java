package com.dophin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FuzzyLikeThisFieldQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.EsBulkCondition;
import com.dophin.dto.ResumeSocialNetDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TestElasticSearch extends TestBaseService
{

	private static ObjectMapper mapper = new ObjectMapper();
	private static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();

	@Autowired
	private ConstantsService constantsService;

	@Autowired
	private ResumeSocialNetService resumeSocialNetService;

	private static String RESUME_SOCIAL_NET = "resumesocialnet";

	@Test
	public void testBatchInsertToEs() throws JsonProcessingException
	{
		List<ResumeSocialNetDTO> list = resumeSocialNetService.queryResumeSocialNets(19);
		System.err.println(list.size());

		String result = "";
		List<EsBulkCondition> ls = new ArrayList<EsBulkCondition>();
		for (ResumeSocialNetDTO item : list)
		{
			result = mapper.writeValueAsString(item);
			ls.add(new EsBulkCondition(item.getId(), result));
			// IndexResponse response =
			// elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX,
			// "resumesocialnet",
			// item.getId(), result);
		}
		BulkResponse response = elasticSearchUtils.doBulkIndexAction(CommonUtils.ES_INDEX, RESUME_SOCIAL_NET, ls);
	}

	@Test
	public void testBatchUpdateToEs() throws JsonProcessingException
	{
		List<ResumeSocialNetDTO> list = resumeSocialNetService.queryResumeSocialNets(19);
		System.err.println(list.size());

		String result = "";
		List<EsBulkCondition> ls = new ArrayList<EsBulkCondition>();
		for (ResumeSocialNetDTO item : list)
		{
			result = mapper.writeValueAsString(item);
			ls.add(new EsBulkCondition(item.getId(), result));
		}
		BulkResponse response = elasticSearchUtils.doBulkUpdateAction(CommonUtils.ES_INDEX, RESUME_SOCIAL_NET, ls);
	}

	@Test
	public void testBatchDeleteToEs()
	{
		List<ResumeSocialNetDTO> list = resumeSocialNetService.queryResumeSocialNets(19);
		System.err.println(list.size());

		String result = "";
		List<EsBulkCondition> ls = new ArrayList<EsBulkCondition>();
		for (ResumeSocialNetDTO item : list)
		{
			ls.add(new EsBulkCondition(item.getId(), result));
			// IndexResponse response =
			// elasticSearchUtils.doIndexAction(CommonUtils.ES_INDEX,
			// "resumesocialnet",
			// item.getId(), result);
		}
		BulkResponse response = elasticSearchUtils.doBulkDeleteAction(CommonUtils.ES_INDEX, RESUME_SOCIAL_NET, ls);
	}

	/**
	 * 初始化大学常量数据到es搜索引擎中
	 * 
	 * @throws JsonProcessingException
	 */
	@Test
	public void testInitUniversityInfoToEs() throws JsonProcessingException
	{

		UniversityDTO universityDTO = constantsService.queryUniversity(1);
		System.err.println(universityDTO);

		String jsonResult = mapper.writeValueAsString(universityDTO);
		System.err.println(jsonResult);

		IndexResponse response = elasticSearchUtils.doIndexAction("firstportal", "university", 1, jsonResult);

		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());
		System.err.println("created: " + response.isCreated());
	}

	@Test
	public void testInsertToUniversities() throws JsonProcessingException
	{
		List<UniversityDTO> universityDTOs = constantsService.queryUniversities();

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "firstportal").build();
		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				"121.41.72.42", 9300));

		long start = System.currentTimeMillis();
		for (UniversityDTO universityDTO : universityDTOs)
		{
			String jsonResult = mapper.writeValueAsString(universityDTO);
			System.err.println(jsonResult);

			IndexResponse response = client
					.prepareIndex("firstportal", "university", String.valueOf(universityDTO.getId()))
					.setSource(jsonResult).execute().actionGet();

			System.err.println("id: " + response.getId());
			System.err.println("index: " + response.getIndex());
			System.err.println("type: " + response.getType());
			System.err.println("version: " + response.getVersion());
			System.err.println("created: " + response.isCreated());
		}
		long end = System.currentTimeMillis();
		System.err.println(end - start);

		client.close();
	}

	@Test
	public void testUpatetToEs() throws JsonProcessingException, InterruptedException, ExecutionException
	{
		UniversityDTO universityDTO = constantsService.queryUniversity(2);
		System.err.println(universityDTO);

		String jsonResult = mapper.writeValueAsString(universityDTO);
		System.err.println(jsonResult);

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "firstportal").build();
		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				"121.41.72.42", 9300));
		UpdateRequest updateRequest = new UpdateRequest("firstportal", "university", "2").doc(jsonResult);
		client.update(updateRequest).get();

		client.close();
	}

	@Test
	public void testSearchForEs() throws JsonParseException, JsonMappingException, IOException
	{
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "firstportal").build();
		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				"121.41.72.42", 9300));
		SearchResponse searchResponse = client.prepareSearch("firstportal").setTypes("university")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.termQuery("geoId", 300))
				.setFrom(0).setSize(Integer.MAX_VALUE).setExplain(true).execute().actionGet();

		System.err.println("Search hits: " + searchResponse.getHits());
		SearchHits searchHits = searchResponse.getHits();
		SearchHit[] hitArr = searchHits.hits();

		System.err.println(hitArr.length);
		for (SearchHit searchHit : hitArr)
		{
			UniversityDTO universityDTO = mapper.readValue(searchHit.getSourceAsString(), UniversityDTO.class);
			System.err.println(universityDTO);
		}

		client.close();
	}

	/**
	 * 模糊查询
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testSearchForEs2() throws JsonParseException, JsonMappingException, IOException
	{
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "firstportal").build();
		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				"121.41.72.42", 9300));

		FuzzyLikeThisFieldQueryBuilder fuzzyQuery = QueryBuilders.fuzzyLikeThisFieldQuery("name");
		fuzzyQuery.boost(2.0f).maxQueryTerms(10).likeText("北京");

		long start = System.currentTimeMillis();
		SearchResponse searchResponse = client.prepareSearch("firstportal").setTypes("university").setQuery(fuzzyQuery)
				.setFrom(0).setSize(Integer.MAX_VALUE).execute().actionGet();
		long end = System.currentTimeMillis();
		System.err.println("timecost: " + (end - start));

		System.err.println("Search hits: " + searchResponse.getHits());
		SearchHits searchHits = searchResponse.getHits();
		System.err.println("totol hits: " + searchHits.getTotalHits());
		SearchHit[] hitArr = searchHits.hits();

		System.err.println(hitArr.length);
		for (SearchHit searchHit : hitArr)
		{

			UniversityDTO universityDTO = mapper.readValue(searchHit.getSourceAsString(), UniversityDTO.class);
			System.err.println(universityDTO + ":" + searchHit.getScore());
		}

		client.close();
	}

	/**
	 * 1．TermQuery与QueryParser
	 * 单个单词作为查询表达式时，它相当于一个单独的项。如果表达式是由单个单词构成，QueryParser的parse
	 * ()方法会返回一个TermQuery对象。
	 * 如查询表达式为：content:hello，QueryParser会返回一个域为content，值为hello的TermQuery。 Query
	 * query = new TermQuery(“content”, “hello”).
	 * 
	 * 2．RangeQuery与QueryParser QueryParse可以使用[起始TO 终止]或{起始TO
	 * 终止}表达式来构造RangeQuery。 如查询表达式为：time：[20101010 TO 20101210]
	 * ，QueryParser会返回一个域为time，下限为20101010，上限为20101210的RangeQuery。 Term t1 = new
	 * Term(“time”, “20101010”); Term t2 = new Term(“time”, “20101210”); Query
	 * query = new RangeQuery(t1, t2, true);
	 * 
	 * 3．PrefixQuery与QueryParser
	 * 当查询表达式中短语以星号（*）结尾时，QueryParser会创建一个PrefixQuery对象。
	 * 如查询表达式为：content：luc*，则QueryParser会返回一个域为content，值为luc的PrefixQuery. Query
	 * query = new PrefixQuery(luc);
	 * 
	 * 4．BooleanQuery与QueryParser
	 * 当查询表达式中包含多个项时，QueryParser可以方便的构建BooleanQuery。QueryParser使用圆括号分组
	 * ，通过-，+，AND, OR及NOT来指定所生成的Boolean Query。
	 * 
	 * 5．PhraseQuery与QueryParser
	 * 在QueryParser的分析表达式中双引号的若干项会被转换为一个PhraseQuery对象，默认情况下
	 * ，Slop因子为0，可以在表达式中通过~n来指定slop因子的值。 如查询表达式为content：“hello
	 * world”~3，则QueryParser会返回一个域为content，内容为“hello world”，slop为3的短语查询。 Query
	 * query = new PhraseQuery(); query.setSlop(3); query.add(new
	 * Term(“content”, “hello”); query.add(new Term(“content”, “world”);
	 * 
	 * 6. Wildcard与QueryParser
	 * Lucene使用两个标准的通配符号，*代表0或多个字母，？代表0或1个字母。但查询表达式中包含*或？时
	 * ，则QueryParser会返回一个WildcardQuery对象
	 * 。但要注意的是，当*出现在查询表达式的末尾时，会被优化为PrefixQuery；并且查询表达式的首个字符不能是通配符
	 * ，防止用户输入以通配符*为前缀的搜索表达式，导致lucene枚举所有的项而耗费巨大的资源。
	 * 
	 * 6．FuzzyQuery和QueryParser QueryParser通过在某个项之后添加“~”来支持FuzzyQuery类的模糊查询。
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	public void testFuzzyQuerySearch() throws JsonParseException, JsonMappingException, IOException
	{
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "firstportal").build();
		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				CommonUtils.ES_HOST, CommonUtils.ES_PORT));

		QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("name", "矿业");
		long start = System.currentTimeMillis();
		SearchResponse searchResponse = client.prepareSearch("firstportal").setTypes("university")
				.setQuery(queryBuilder).setFrom(0).setSize(Integer.MAX_VALUE).execute().actionGet();
		long end = System.currentTimeMillis();
		System.err.println("timecost: " + (end - start));

		System.err.println("Search hits: " + searchResponse.getHits());
		SearchHits searchHits = searchResponse.getHits();
		System.err.println("totol hits: " + searchHits.getTotalHits());
		SearchHit[] hitArr = searchHits.hits();

		System.err.println(hitArr.length);
		for (SearchHit searchHit : hitArr)
		{

			UniversityDTO universityDTO = mapper.readValue(searchHit.getSourceAsString(), UniversityDTO.class);
			System.err.println(universityDTO + ":" + searchHit.getScore());
		}

		client.close();

		SearchResponse searchResponse2 = client.prepareSearch("firstportal").setTypes("university")
				.setQuery(queryBuilder).setFrom(0).setSize(Integer.MAX_VALUE).execute().actionGet();

	}

	@Test
	public void testBooleanQuerySearch() throws JsonParseException, JsonMappingException, IOException
	{
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "firstportal").build();
		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				"121.41.72.42", 9300));

		QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.fuzzyQuery("name", "北京"))
				.should(QueryBuilders.fuzzyQuery("name", "北")).should(QueryBuilders.fuzzyQuery("name", "京"));

		long start = System.currentTimeMillis();
		SearchResponse searchResponse = client.prepareSearch("firstportal").setTypes("university")
				.setQuery(queryBuilder).setFrom(0).setSize(Integer.MAX_VALUE).execute().actionGet();
		long end = System.currentTimeMillis();
		System.err.println("timecost: " + (end - start));

		SearchHits searchHits = searchResponse.getHits();
		SearchHit[] hitArr = searchHits.hits();

		System.err.println(hitArr.length);
		for (SearchHit searchHit : hitArr)
		{

			UniversityDTO universityDTO = mapper.readValue(searchHit.getSourceAsString(), UniversityDTO.class);
			System.err.println(universityDTO + ":" + searchHit.getScore());
		}

		System.err.println("Search hits: " + searchResponse.getHits());
		System.err.println("totol hits: " + searchHits.getTotalHits());

		client.close();
	}

	@Test
	public void testDeleteSearch()
	{
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "firstportal").build();
		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(
				"121.41.72.42", 9300));

		DeleteResponse response = client.prepareDelete("firstportal", "university", "1").execute().actionGet();
		System.err.println("id: " + response.getId());
		System.err.println("index: " + response.getIndex());
		System.err.println("type: " + response.getType());
		System.err.println("version: " + response.getVersion());

		client.close();
	}
}
