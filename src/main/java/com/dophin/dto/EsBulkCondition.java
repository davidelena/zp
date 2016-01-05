package com.dophin.dto;

/**
 * 批量操作的条件实体
 * 
 * @author David.dai
 * 
 */
public class EsBulkCondition
{
	private int id;
	private String source;

	public EsBulkCondition()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public EsBulkCondition(int id, String source)
	{
		super();
		this.id = id;
		this.source = source;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	@Override
	public String toString()
	{
		return "EsBulkCondition [id=" + id + ", source=" + source + "]";
	}

}
