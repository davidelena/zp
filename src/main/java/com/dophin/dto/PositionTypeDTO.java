package com.dophin.dto;

/**
 * 职位类别
 * 
 * @author David.dai
 * 
 */
public class PositionTypeDTO
{
	private int id;
	private int parentId;
	private int subParentId;
	private String name;
	private int status;

	public PositionTypeDTO()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public PositionTypeDTO(int id, int parentId, int subParentId, String name,
			int status)
	{
		super();
		this.id = id;
		this.parentId = parentId;
		this.subParentId = subParentId;
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

	public int getSubParentId()
	{
		return subParentId;
	}

	public void setSubParentId(int subParentId)
	{
		this.subParentId = subParentId;
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
		return "PositionType [id=" + id + ", parentId=" + parentId
				+ ", subParentId=" + subParentId + ", name=" + name
				+ ", status=" + status + "]";
	}

}
