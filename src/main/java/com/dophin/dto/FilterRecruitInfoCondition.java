package com.dophin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生用户搜索-企业职位信息过滤条件
 * 
 * @author dailiwei
 * 
 */
public class FilterRecruitInfoCondition {

	private String memberId;

	/**
	 * 实习天数
	 */
	private int internshipDays;

	/**
	 * 职位状态
	 */
	private int positionStatus;

	/**
	 * 申请进度
	 */
	private int applyProgress;

	/**
	 * 行业多选
	 */
	private String industries;

	/**
	 * 工作城市多选
	 */
	private String addressCities;

	/**
	 * 专业要求
	 */
	private int major;

	/**
	 * 学历要求
	 */
	private int diploma;

	/**
	 * 薪资要求
	 */
	private int demandSalary;

	/**
	 * 是否为我的收藏
	 */
	private boolean isMyFavorite;

	/**
	 * 是否为最新职位
	 */
	private boolean isLatested;

	/**
	 * 搜索文本
	 */
	private String searchTxt;

	/**
	 * 期望职位
	 */
	private String demandPosition;

	/**
	 * 期望求职类别（全职or实习）
	 */
	private int demandRecruitType;

	/**
	 * 期望城市（3个）
	 */
	private List<Integer> demandCities;

	/**
	 * 期望行业（3个）
	 */
	private List<Integer> demandIndustries;

	/**
	 * 从第几条开始
	 */
	private int from;

	/**
	 * 获取多少条数据
	 */
	private int size;

	/**
	 * 总记录数
	 */
	private int total;

	public FilterRecruitInfoCondition() {
		memberId = "";
		internshipDays = 0;
		positionStatus = 0;
		applyProgress = 0;
		industries = "";
		addressCities = "";
		major = 0;
		diploma = 0;
		demandSalary = 0;
		isMyFavorite = false;
		isLatested = false;
		searchTxt = "";
		demandPosition = "";
		demandRecruitType = 0;
		demandCities = new ArrayList<Integer>();
		demandIndustries = new ArrayList<Integer>();
		from = 0;
		size = Integer.MAX_VALUE;
		total = 0;
	}

	public FilterRecruitInfoCondition(String memberId, int internshipDays, int positionStatus, int applyProgress,
			String industries, String addressCities, int major, int diploma, int demandSalary, boolean isMyFavorite,
			boolean isLatested, String searchTxt, int from, int size, int total) {
		super();
		this.memberId = memberId;
		this.internshipDays = internshipDays;
		this.positionStatus = positionStatus;
		this.applyProgress = applyProgress;
		this.major = major;
		this.diploma = diploma;
		this.demandSalary = demandSalary;
		this.isMyFavorite = isMyFavorite;
		this.isLatested = isLatested;
		this.industries = industries;
		this.addressCities = addressCities;
		this.searchTxt = searchTxt;
		this.from = from;
		this.size = size;
		this.total = total;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getInternshipDays() {
		return internshipDays;
	}

	public void setInternshipDays(int internshipDays) {
		this.internshipDays = internshipDays;
	}

	public int getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(int positionStatus) {
		this.positionStatus = positionStatus;
	}

	public int getApplyProgress() {
		return applyProgress;
	}

	public void setApplyProgress(int applyProgress) {
		this.applyProgress = applyProgress;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getDiploma() {
		return diploma;
	}

	public void setDiploma(int educational) {
		this.diploma = educational;
	}

	public int getDemandSalary() {
		return demandSalary;
	}

	public void setDemandSalary(int demandSalary) {
		this.demandSalary = demandSalary;
	}

	public boolean isMyFavorite() {
		return isMyFavorite;
	}

	public void setMyFavorite(boolean isMyFavorite) {
		this.isMyFavorite = isMyFavorite;
	}

	public boolean isLatested() {
		return isLatested;
	}

	public void setLatested(boolean isLatested) {
		this.isLatested = isLatested;
	}

	public String getSearchTxt() {
		return searchTxt;
	}

	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}

	public String getDemandPosition() {
		return demandPosition;
	}

	public void setDemandPosition(String demandPosition) {
		this.demandPosition = demandPosition;
	}

	public int getDemandRecruitType() {
		return demandRecruitType;
	}

	public void setDemandRecruitType(int demandRecruitType) {
		this.demandRecruitType = demandRecruitType;
	}

	public List<Integer> getDemandCities() {
		return demandCities;
	}

	public void setDemandCities(List<Integer> demandCities) {
		this.demandCities = demandCities;
	}

	public List<Integer> getDemandIndustries() {
		return demandIndustries;
	}

	public void setDemandIndustries(List<Integer> demandIndustries) {
		this.demandIndustries = demandIndustries;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getIndustries() {
		return industries;
	}

	public void setIndustries(String industries) {
		this.industries = industries;
	}

	public String getAddressCities() {
		return addressCities;
	}

	public void setAddressCities(String addressCities) {
		this.addressCities = addressCities;
	}

	@Override
	public String toString() {
		return "FilterRecruitInfoCondition [memberId=" + memberId + ", internshipDays=" + internshipDays
				+ ", positionStatus=" + positionStatus + ", applyProgress=" + applyProgress + ", industries="
				+ industries + ", addressCities=" + addressCities + ", major=" + major + ", diploma=" + diploma
				+ ", demandSalary=" + demandSalary + ", isMyFavorite=" + isMyFavorite + ", isLatested=" + isLatested
				+ ", searchTxt=" + searchTxt + ", demandPosition=" + demandPosition + ", demandRecruitType="
				+ demandRecruitType + ", demandCities=" + demandCities + ", demandIndustries=" + demandIndustries
				+ ", from=" + from + ", size=" + size + ", total=" + total + "]";
	}

}
