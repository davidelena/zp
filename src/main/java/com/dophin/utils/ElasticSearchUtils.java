package com.dophin.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;

import com.dophin.dto.EsBulkCondition;

/**
 * elasticsearch帮助工具类
 * 
 * @author dailiwei
 * 
 */
public class ElasticSearchUtils
{

	private int port;
	private String host;
	private String clusterName;
	private Settings settings;
	private static Logger logger = Logger.getLogger(ElasticSearchUtils.class);
	private long start = 0;
	private long end = 0;

	public ElasticSearchUtils()
	{
		super();
		host = CommonUtils.ES_HOST;
		port = CommonUtils.ES_PORT;
		clusterName = "firstportal"; // 此处名字不能随意修改，要保持和elasticsearch.yml配置文件中的cluster.name一致，切记否则会出现节点找不到的错误
		settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
	}

	public ElasticSearchUtils(int port, String host, String clusterName)
	{
		super();
		this.port = port;
		this.host = host;
		this.clusterName = clusterName;
		settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
	}

	/**
	 * 执行es index操作（相当于数据库插入或更新操作），每次操作更新es中的version版本
	 * 
	 * @param index
	 *            es中的数据库
	 * @param type
	 *            es中的表
	 * @param id
	 *            es中的主键
	 * @param source
	 *            源json字符串
	 * @return es index操作相应结果
	 */
	public IndexResponse doIndexAction(String index, String type, int id, String source)
	{
		start = System.currentTimeMillis();
		try (@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port)))
		{
			IndexResponse response = client.prepareIndex(index, type, String.valueOf(id)).setSource(source).execute()
					.actionGet();
			end = System.currentTimeMillis();
			logger.info("doIndexAction timecost: " + (end - start));
			return response;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 批量插入es数据操作
	 * 
	 * @param index
	 *            es中的数据库
	 * @param type
	 *            es中的表
	 * @param id
	 *            es中的主键
	 * @param source
	 *            源json字符串
	 * @return
	 */
	public BulkResponse doBulkIndexAction(String index, String type, List<EsBulkCondition> esBulkConditions)
	{

		start = System.currentTimeMillis();
		try (@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port)))
		{
			BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
			IndexRequestBuilder indexRequest = null;
			for (EsBulkCondition esBulkCondition : esBulkConditions)
			{
				indexRequest = client.prepareIndex(index, type).setSource(esBulkCondition.getSource())
						.setId(String.valueOf(esBulkCondition.getId()));
				bulkRequestBuilder.add(indexRequest);
			}

			BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
			return bulkResponse;

		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * es 删除操作
	 * 
	 * @param index
	 *            es中的数据库
	 * @param type
	 *            es中的表
	 * @param id
	 *            es中的主键
	 * @return es delete 操作相应结果
	 */
	public DeleteResponse doDeleteAction(String index, String type, int id)
	{
		start = System.currentTimeMillis();
		try (@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port)))
		{
			DeleteResponse response = client.prepareDelete(index, type, String.valueOf(id)).execute().actionGet();
			end = System.currentTimeMillis();
			logger.info("doDeleteAction timecost: " + (end - start));
			return response;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 批量删除es数据操作
	 * 
	 * @param index
	 *            es中的数据库
	 * @param type
	 *            es中的表
	 * @param esBulkConditions
	 * @return
	 */
	public BulkResponse doBulkDeleteAction(String index, String type, List<EsBulkCondition> esBulkConditions)
	{

		start = System.currentTimeMillis();
		try (@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port)))
		{
			BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
			DeleteRequestBuilder deleteRequest = null;
			for (EsBulkCondition esBulkCondition : esBulkConditions)
			{
				deleteRequest = client.prepareDelete(index, type, String.valueOf(esBulkCondition.getId()));
				bulkRequestBuilder.add(deleteRequest);
			}

			BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
			return bulkResponse;

		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 执行es update操作（相当于数据库更新操作），每次操作更新es中的version版本
	 * 
	 * @param index
	 *            es中的数据库
	 * @param type
	 *            es中的表
	 * @param id
	 *            es中的主键
	 * @param source
	 *            源json字符串
	 * @return es update操作结果
	 */
	public UpdateResponse doUpdateAction(String index, String type, int id, String source)
	{
		start = System.currentTimeMillis();
		try (@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port)))
		{
			UpdateResponse response = client.prepareUpdate(index, type, String.valueOf(id)).setDoc(source).execute()
					.actionGet();
			end = System.currentTimeMillis();
			logger.info("doIndexAction timecost: " + (end - start));
			return response;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 批量更新es数据操作
	 * 
	 * @param index
	 * @param type
	 * @param esBulkConditions
	 * @return
	 */
	public BulkResponse doBulkUpdateAction(String index, String type, List<EsBulkCondition> esBulkConditions)
	{

		start = System.currentTimeMillis();
		try (@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port)))
		{
			BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
			UpdateRequestBuilder updateRequest = null;
			for (EsBulkCondition esBulkCondition : esBulkConditions)
			{
				updateRequest = client.prepareUpdate(index, type, String.valueOf(esBulkCondition.getId())).setDoc(
						esBulkCondition.getSource());
				bulkRequestBuilder.add(updateRequest);
			}

			BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
			return bulkResponse;

		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 执行 es 搜索操作（相当于数据库的搜索操作）
	 * 
	 * @return SearchResponse.getHits()有命中的结果集
	 */
	public SearchResponse doSearchAction(String index, String type, QueryBuilder queryBuilder, int from, int size)
	{
		return doSearchAction(index, type, queryBuilder, null, from, size);
	}

	/**
	 * 执行 es 搜索操作（相当于数据库的搜索操作）
	 * 
	 * @return SearchResponse.getHits()有命中的结果集
	 */
	public SearchResponse doSearchAction(String index, String type, QueryBuilder queryBuilder)
	{
		return doSearchAction(index, type, queryBuilder, 0, Integer.MAX_VALUE);
	}

	/**
	 * 执行es搜索操作可排序
	 */
	public SearchResponse doSearchAction(String index, String type, QueryBuilder queryBuilder, SortBuilder sortBuilder)
	{
		return doSearchAction(index, type, queryBuilder, sortBuilder, 0, Integer.MAX_VALUE);
	}

	/**
	 * 执行 es 搜索操作，带分页（相当于数据库的搜索操作，可以排序）
	 */
	public SearchResponse doSearchAction(String index, String type, QueryBuilder queryBuilder, SortBuilder sortBuilder,
			int from, int size)
	{
		start = System.currentTimeMillis();
		try (@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port)))
		{
			SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type).setFrom(from)
					.setSize(size);
			if (queryBuilder != null)
			{
				searchRequestBuilder = searchRequestBuilder.setQuery(queryBuilder);
			}

			if (sortBuilder != null)
			{
				searchRequestBuilder = searchRequestBuilder.addSort(sortBuilder);
			}

			SearchResponse response = searchRequestBuilder.execute().actionGet();

			end = System.currentTimeMillis();
			logger.info("doIndexAction timecost: " + (end - start));
			return response;
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 执行es 模糊搜索操作
	 * 
	 * @param index
	 *            es数据库
	 * @param type
	 *            es类型
	 * @param field
	 *            es搜索字段
	 * @param searchStr
	 *            es搜索匹配文本
	 * @param from
	 *            分页起始点
	 * @param size
	 *            分页个数
	 * @return 命中符合的搜索结果
	 */
	public SearchResponse doFuzzySearchAction(String index, String type, String field, String searchStr, int from,
			int size)
	{
		QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery(field, searchStr);
		return doSearchAction(index, type, queryBuilder, from, size);
	}

	/**
	 * 执行es 模糊搜索操作
	 * 
	 * @param index
	 *            es数据库
	 * @param type
	 *            es类型
	 * @param field
	 *            es搜索字段
	 * @param searchStr
	 *            es搜索匹配文本
	 * @return 命中符合的搜索结果
	 */
	public SearchResponse doFuzzySearchAction(String index, String type, String field, String searchStr)
	{
		QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery(field, searchStr);
		return doSearchAction(index, type, queryBuilder, 0, Integer.MAX_VALUE);
	}
}
