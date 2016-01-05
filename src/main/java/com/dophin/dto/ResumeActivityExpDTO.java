package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 简历-活动经历
 * 
 * @author David.dai
 * 
 */
public class ResumeActivityExpDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String activityName;
	private String positionName;
	private Date startTime;
	private String startTimeDesc;
	private Date endTime;
	private String endTimeDesc;
	private String activityDesc;
	private Date createTime;
	private Date updateTime;
	private int status;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public ResumeActivityExpDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		activityName = "";
		positionName = "";
		startTime = new Date();
		startTimeDesc = "";
		endTime = new Date();
		endTimeDesc = "";
		activityDesc = "";
		createTime = new Date();
		updateTime = new Date();
		status = 0;
	}

	public ResumeActivityExpDTO(int id, int resumeId, String activityName, String positionName, Date startTime, Date endTime, String activityDesc,
			Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.activityName = activityName;
		this.positionName = positionName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityDesc = activityDesc;
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

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
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

	@Override
	public String toString() {
		return "ResumeActivityExpDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", activityName=" + activityName
				+ ", positionName=" + positionName + ", startTime=" + startTime + ", endTime=" + endTime + ", activityDesc=" + activityDesc + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
