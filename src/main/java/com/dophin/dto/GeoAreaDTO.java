package com.dophin.dto;

/**
 * 地域
 * 
 * @author David.dai
 * 
 */
public class GeoAreaDTO
{
	private int id;
	private int parentId;
	private String name;
	private boolean isHotCity;
	private int status;

	public GeoAreaDTO()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public GeoAreaDTO(int id, int parentId, String name, boolean isHotCity,
			int status)
	{
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.isHotCity = isHotCity;
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

	public boolean isHotCity()
	{
		return isHotCity;
	}

	public void setHotCity(boolean isHotCity)
	{
		this.isHotCity = isHotCity;
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
		return "GeoAreaDTO [id=" + id + ", parentId=" + parentId + ", name="
				+ name + ", isHotCity=" + isHotCity + ", status=" + status
				+ "]";
	}

}
