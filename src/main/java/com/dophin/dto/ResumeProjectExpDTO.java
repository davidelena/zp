package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简历-项目经历
 * 
 * @author David.dai
 * 
 */
public class ResumeProjectExpDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String projectName;
	private String positionName;
	private Date startTime;
	private String startTimeDesc;
	private Date endTime;
	private String endTimeDesc;
	private String projectDesc;
	private Date createTime;
	private Date updateTime;
	private int status;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public ResumeProjectExpDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		projectName = "";
		positionName = "";
		startTime = new Date();
		startTimeDesc = "";
		endTime = new Date();
		endTimeDesc = "";
		projectDesc = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeProjectExpDTO(int id, int resumeId, String projectName, String positionName, Date startTime, Date endTime, String projectDesc,
			Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.projectName = projectName;
		this.positionName = positionName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.projectDesc = projectDesc;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
		startTimeDesc = sdf.format(startTime);
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

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
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
		return "ResumeProjectExpDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", projectName=" + projectName
				+ ", positionName=" + positionName + ", startTime=" + startTime + ", endTime=" + endTime + ", projectDesc=" + projectDesc + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
