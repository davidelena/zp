package com.dophin.dto;


/**
 * 行业分类
 * 
 * @author David.dai
 * 
 */
public class IndustryDTO
{
	private int id;
	private int parentId;
	private String name;
	private int status;

	public IndustryDTO()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public IndustryDTO(int id, int parentId, String name, int status)
	{
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.status = status;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getParentId()
	{
		return parentId;
	}

	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return "IndustryDTO [id=" + id + ", parentId=" + parentId + ", name="
				+ name + ", status=" + status + "]";
	}

}
