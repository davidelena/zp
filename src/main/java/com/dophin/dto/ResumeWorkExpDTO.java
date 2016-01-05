package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简历-实习和工作经历
 * 
 * @author David.dai
 * 
 */
public class ResumeWorkExpDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String companyName;
	private String positionName;
	private Date startTime;
	private String startTimeDesc;
	private Date endTime;
	private String endTimeDesc;
	private String workDescription;
	private Date createTime;
	private Date updateTime;
	private int status;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public ResumeWorkExpDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		companyName = "";
		positionName = "";
		startTime = new Date();
		startTimeDesc = "";
		endTime = new Date();
		endTimeDesc = "";
		workDescription = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeWorkExpDTO(int id, int resumeId, String companyName, String positionName, Date startTime, Date endTime, String workDescription,
			Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.companyName = companyName;
		this.positionName = positionName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.workDescription = workDescription;
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

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	public ResumeInfoDTO getResumeInfoDTO() {
		return resumeInfoDTO;
	}

	public void setResumeInfoDTO(ResumeInfoDTO resumeInfoDTO) {
		this.resumeInfoDTO = resumeInfoDTO;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStartTimeDesc() {
		startTimeDesc = sdf.format(startTime);
		return startTimeDesc;
	}

	public void setStartTimeDesc(String startTimeDesc) {
		endTimeDesc = sdf.format(endTime);
		this.startTimeDesc = startTimeDesc;
	}

	public String getEndTimeDesc() {
		endTimeDesc = sdf.format(endTime);
		return endTimeDesc;
	}

	public void setEndTimeDesc(String endTimeDesc) {
		endTimeDesc = sdf.format(endTime);
		this.endTimeDesc = endTimeDesc;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
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
		return "ResumeWorkExpDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", companyName=" + companyName
				+ ", positionName=" + positionName + ", startTime=" + startTime + ", endTime=" + endTime + ", workDescription=" + workDescription
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
