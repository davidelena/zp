package com.dophin.dto;

import java.util.Date;

/**
 * 简历-爱好特长
 * 
 * @author David.dai
 * 
 */
public class ResumeHobbySpecialDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String specialName;
	private String specialDesc;
	private Date createTime;
	private Date updateTime;
	private int status;

	public ResumeHobbySpecialDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		specialName = "";
		specialDesc = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeHobbySpecialDTO(int id, int resumeId, String specialName, String specialDesc, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.specialName = specialName;
		this.specialDesc = specialDesc;
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

	public ResumeInfoDTO getResumeInfoDTO() {
		return resumeInfoDTO;
	}

	public void setResumeInfoDTO(ResumeInfoDTO resumeInfoDTO) {
		this.resumeInfoDTO = resumeInfoDTO;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getSpecialDesc() {
		return specialDesc;
	}

	public void setSpecialDesc(String specialDesc) {
		this.specialDesc = specialDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return updateTime;
	}

	public void setEndTime(Date endTime) {
		this.updateTime = endTime;
	}

	@Override
	public String toString() {
		return "ResumeHobbySpecialDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", specialName=" + specialName
				+ ", specialDesc=" + specialDesc + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
