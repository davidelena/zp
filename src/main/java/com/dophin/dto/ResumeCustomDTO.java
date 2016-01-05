package com.dophin.dto;

import java.util.Date;

/**
 * 简历-自定义
 * 
 * @author David.dai
 * 
 */
public class ResumeCustomDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String name;
	private String description;
	private Date createTime;
	private Date updateTime;
	private int status;

	public ResumeCustomDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		name = "";
		description = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeCustomDTO(int id, int resumeId, String name, String description, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.name = name;
		this.description = description;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "ResumeCustomDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", name=" + name + ", description=" + description
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
