package com.dophin.dto;

import java.util.Date;

import com.dophin.enums.SmsTypeEnum;

/**
 * 短信日志
 * 
 * @author dailiwei
 * 
 */
public class SmsLogDTO {
	private int id;
	private String mobile;
	private String content;
	private int smsType;
	private Date createTime;
	private Date updateTime;
	private int status;

	public SmsLogDTO() {
		super();
		id = 0;
		mobile = "";
		content = "";
		smsType = SmsTypeEnum.Default.getCode();
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public SmsLogDTO(int id, String mobile, String content, int smsType, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.mobile = mobile;
		this.content = content;
		this.smsType = smsType;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSmsType() {
		return smsType;
	}

	public void setSmsType(int smsType) {
		this.smsType = smsType;
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
		return "SmsLogDTO [id=" + id + ", mobile=" + mobile + ", content=" + content + ", smsType=" + smsType + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", status=" + status + "]";
	}

}
