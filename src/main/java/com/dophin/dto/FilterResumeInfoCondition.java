package com.dophin.dto;

/**
 * 企业用户搜索-学生简历信息过滤条件
 * 
 * @author David.dai
 * 
 */
public class FilterResumeInfoCondition {
	private int schoolId;

	private int diploma;

	private int graduationYear;

	private int major;

	private Boolean hasWorkExp;

	private int sex;

	private String demandIndustries;

	private String demandCities;

	private String searchTxt;

	private int from;

	private int size;

	private int total;

	public FilterResumeInfoCondition() {
		super();
		schoolId = 0;
		diploma = 0;
		graduationYear = 0;
		major = 0;
		hasWorkExp = null;
		sex = 0;
		demandIndustries = "";
		demandCities = "";
		searchTxt = "";
		total = 0;
		from = 0;
		size = 0;
	}

	public FilterResumeInfoCondition(int schoolId, int diploma, int graduationYear, int major, boolean hasWorkExp,
			int sex, String demandIndustries, String demandCities, String searchTxt) {
		super();
		this.schoolId = schoolId;
		this.diploma = diploma;
		this.graduationYear = graduationYear;
		this.major = major;
		this.hasWorkExp = hasWorkExp;
		this.sex = sex;
		this.demandIndustries = demandIndustries;
		this.demandCities = demandCities;
		this.searchTxt = searchTxt;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getDiploma() {
		return diploma;
	}

	public void setDiploma(int diploma) {
		this.diploma = diploma;
	}

	public int getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(int graduationYear) {
		this.graduationYear = graduationYear;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public Boolean isHasWorkExp() {
		return hasWorkExp;
	}

	public void setHasWorkExp(Boolean hasWorkExp) {
		this.hasWorkExp = hasWorkExp;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getDemandIndustries() {
		return demandIndustries;
	}

	public void setDemandIndustries(String demandIndustries) {
		this.demandIndustries = demandIndustries;
	}

	public String getDemandCities() {
		return demandCities;
	}

	public void setDemandCities(String demandCities) {
		this.demandCities = demandCities;
	}

	public String getSearchTxt() {
		return searchTxt;
	}

	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	@Override
	public String toString() {
		return "FilterResumeInfoCondition [schoolId=" + schoolId + ", diploma=" + diploma + ", graduationYear="
				+ graduationYear + ", major=" + major + ", hasWorkExp=" + hasWorkExp + ", sex=" + sex
				+ ", demandIndustries=" + demandIndustries + ", demandCities=" + demandCities + ", searchTxt="
				+ searchTxt + ", from=" + from + ", size=" + size + ", total=" + total + "]";
	}

}
