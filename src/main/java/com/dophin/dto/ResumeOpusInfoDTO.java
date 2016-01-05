package com.dophin.dto;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 简历-作品
 * 
 * @author David.dai
 * 
 */
public class ResumeOpusInfoDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String opusName;
	private String opusUrl;
	private String opusPath;
	private String opusPathFile;
	private Date createTime;
	private Date updateTime;
	private int status;

	public ResumeOpusInfoDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		opusName = "";
		opusUrl = "";
		opusPath = "";
		opusPathFile = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeOpusInfoDTO(int id, int resumeId, String opusName, String opusUrl, String opusPath, Date createTime,
			Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.opusName = opusName;
		this.opusUrl = opusUrl;
		this.opusPath = opusPath;
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

	public String getOpusName() {
		return opusName;
	}

	public void setOpusName(String opusName) {
		this.opusName = opusName;
	}

	public String getOpusUrl() {
		return opusUrl;
	}

	public void setOpusUrl(String opusUrl) {
		this.opusUrl = opusUrl;
	}

	public String getOpusPath() {
		return opusPath;
	}

	public void setOpusPath(String opusPath) {
		this.opusPath = opusPath;
	}

	public String getOpusPathFile() {
		opusPathFile = StringUtils.substringAfterLast(opusPath, "/");
		return opusPathFile;
	}

	public void setOpusPathFile(String opusPathFile) {
		opusPathFile = StringUtils.substringAfterLast(opusPath, "/");
		this.opusPathFile = opusPathFile;
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
		return "ResumeOpusInfoDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO
				+ ", opusName=" + opusName + ", opusUrl=" + opusUrl + ", opusPath=" + opusPath + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
