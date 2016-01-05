package com.dophin.dto;

/**
 * 大学
 * 
 * @author David.dai
 * 
 */
public class UniversityDTO {
	private int id;
	private int geoId;
	private String geo;
	private String name;
	private String abbreviation;
	private String tag;
	private int type;
	private int status;

	public UniversityDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UniversityDTO(int id, int geoId, String geo, String name, String abbreviation, String tag, int type, int status) {
		super();
		this.id = id;
		this.geoId = geoId;
		this.geo = geo;
		this.name = name;
		this.abbreviation = abbreviation;
		this.tag = tag;
		this.type = type;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGeoId() {
		return geoId;
	}

	public void setGeoId(int geoId) {
		this.geoId = geoId;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UniversityDTO [id=" + id + ", geoId=" + geoId + ", geo=" + geo + ", name=" + name + ", abbreviation=" + abbreviation + ", tag=" + tag
				+ ", type=" + type + ", status=" + status + "]";
	}

}
