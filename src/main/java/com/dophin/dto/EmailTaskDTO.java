package com.dophin.dto;

import java.util.Date;

public class EmailTaskDTO {
	private int id;
	private String mailto;
	private String title;
	private String content;
	private Date createTime;
	private Date updateTime;
	private int status;

	public EmailTaskDTO() {
		super();
		id = 0;
		mailto = "";
		title = "";
		content = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public EmailTaskDTO(int id, String mailto, String title, String content, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.mailto = mailto;
		this.title = title;
		this.content = content;
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

	public String getMailto() {
		return mailto;
	}

	public void setMailto(String mailto) {
		this.mailto = mailto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		return "EmailTaskDTO [id=" + id + ", mailto=" + mailto + ", title=" + title + ", content=" + content + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", status=" + status + "]";
	}

}
