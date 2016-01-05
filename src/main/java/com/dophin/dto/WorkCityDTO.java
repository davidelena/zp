package com.dophin.dto;

/**
 * 工作城市
 * 
 * @author dailiwei
 * 
 */
public class WorkCityDTO {

	private int cityID;

	private String city;

	private String detailAddress;

	public WorkCityDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkCityDTO(int cityID, String city, String detailAddress) {
		super();
		this.cityID = cityID;
		this.city = city;
		this.detailAddress = detailAddress;
	}

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	@Override
	public String toString() {
		return "WorkCityDTO [cityID=" + cityID + ", city=" + city + ", detailAddress=" + detailAddress + "]";
	}

}
