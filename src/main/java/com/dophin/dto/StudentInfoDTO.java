package com.dophin.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dophin.enums.JobStatusEnum;
import com.dophin.enums.JobTypeEnum;
import com.dophin.enums.RecommendFrequencyEnum;
import com.dophin.enums.SalaryLevelEnum;

/**
 * 学生注册用户（求职意向+系统设置）
 * 
 * @author dailiwei
 * 
 */
public class StudentInfoDTO {

	private int id;
	private String memberId;
	private String demandPosition;
	private int demandType;
	private String demandTypeDesc;
	private String demandIndustry;
	private List<IndustryDTO> demandIndustries;
	private String demandCity;
	private List<GeoAreaDTO> demandCitys;
	private int demandSalary;
	private String demandSalaryDesc;
	private boolean allowRecommend;
	private boolean allowEmail;
	private int jobStatus;
	private String jobStatusDesc;
	private String recommendEmail;
	private int recommendFrequency;
	private String recommendFrequencyDesc;
	private Date createTime;
	private Date updateTime;
	private int status;

	public StudentInfoDTO() {
		super();
		id = 0;
		memberId = "";
		demandPosition = "";
		demandType = 0;
		demandTypeDesc = "";
		demandIndustry = "";
		demandIndustries = new ArrayList<>();
		demandCity = "";
		demandCitys = new ArrayList<>();
		demandSalary = 0;
		demandSalaryDesc = "";
		allowRecommend = false;
		allowEmail = false;
		jobStatus = 0;
		jobStatusDesc = "";
		recommendEmail = "";
		recommendFrequency = 0;
		recommendFrequencyDesc = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public StudentInfoDTO(int id, String memberId, String demandPosition, int demandType, String demandIndustry, String demandCity, int demandSalary,
			boolean allowRecommend, boolean allowEmail, int jobStatus, String recommendEmail, int recommendFrequency, Date createTime, Date updateTime,
			int status) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.demandPosition = demandPosition;
		this.demandType = demandType;
		this.demandIndustry = demandIndustry;
		this.demandCity = demandCity;
		this.demandSalary = demandSalary;
		this.allowRecommend = allowRecommend;
		this.allowEmail = allowEmail;
		this.jobStatus = jobStatus;
		this.recommendEmail = recommendEmail;
		this.recommendFrequency = recommendFrequency;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDemandPosition() {
		return demandPosition;
	}

	public void setDemandPosition(String demandPosition) {
		this.demandPosition = demandPosition;
	}

	public int getDemandType() {
		return demandType;
	}

	public void setDemandType(int demandType) {
		this.demandType = demandType;
	}

	public String getDemandTypeDesc() {
		return JobTypeEnum.getJobTypeDesc(demandType);
	}

	public String getDemandIndustry() {
		return demandIndustry;
	}

	public void setDemandIndustry(String demandIndustry) {
		this.demandIndustry = demandIndustry;
	}

	public List<IndustryDTO> getDemandIndustries() {
		return demandIndustries;
	}

	public void setDemandIndustries(List<IndustryDTO> demandIndustries) {
		this.demandIndustries = demandIndustries;
	}

	public String getDemandCity() {
		return demandCity;
	}

	public void setDemandCity(String demandCity) {
		this.demandCity = demandCity;
	}

	public List<GeoAreaDTO> getDemandCitys() {
		return demandCitys;
	}

	public void setDemandCitys(List<GeoAreaDTO> demandCitys) {
		this.demandCitys = demandCitys;
	}

	public int getDemandSalary() {
		return demandSalary;
	}

	public void setDemandSalary(int demandSalary) {
		this.demandSalary = demandSalary;
	}

	public String getDemandSalaryDesc() {
		return SalaryLevelEnum.getSalaryLevelDesc(demandSalary);
	}

	public boolean isAllowRecommend() {
		return allowRecommend;
	}

	public void setAllowRecommend(boolean allowRecommend) {
		this.allowRecommend = allowRecommend;
	}

	public boolean isAllowEmail() {
		return allowEmail;
	}

	public void setAllowEmail(boolean allowEmail) {
		this.allowEmail = allowEmail;
	}

	public int getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getJobStatusDesc() {
		return JobStatusEnum.getJobStatusEnumDesc(jobStatus);
	}

	public String getRecommendEmail() {
		return recommendEmail;
	}

	public void setRecommendEmail(String recommendEmail) {
		this.recommendEmail = recommendEmail;
	}

	public int getRecommendFrequency() {
		return recommendFrequency;
	}

	public void setRecommendFrequency(int recommendFrequency) {
		this.recommendFrequency = recommendFrequency;
	}

	public String getRecommendFrequencyDesc() {
		return RecommendFrequencyEnum.getRecommendFrequencyEnumDesc(recommendFrequency);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "StudentInfoDTO [id=" + id + ", memeberId=" + memberId + ", demandPosition=" + demandPosition + ", demandType=" + demandType
				+ ", demandTypeDesc=" + getDemandSalaryDesc() + ", demandIndustry=" + demandIndustry + ", demandIndustries=" + demandIndustries
				+ ", demandCity=" + demandCity + ", demandCitys=" + demandCitys + ", demandSalary=" + demandSalary + ", demandSalaryDesc="
				+ getDemandSalaryDesc() + ", isAllowRecommend=" + allowRecommend + ", isAllowEmail=" + allowEmail + ", jobStatus=" + jobStatus
				+ ", jobStatusDesc=" + getJobStatusDesc() + ", recommendEmail=" + recommendEmail + ", recommendFrequency=" + recommendFrequency
				+ ", recommendFrequencyDesc=" + getRecommendFrequencyDesc() + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status="
				+ status + "]";
	}

}
