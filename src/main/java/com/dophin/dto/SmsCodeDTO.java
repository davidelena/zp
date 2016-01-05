package com.dophin.dto;

import java.util.Date;

import com.dophin.enums.SmsTypeEnum;

/**
 * 短信验证码
 * 
 * @author dailiwei
 * 
 */
public class SmsCodeDTO {
	private int id;
	private String mobile;
	private String code;
	private int status;
	private Date createTime;
	private Date updateTime;

	public SmsCodeDTO() {
		super();
		id = 0;
		mobile = "";
		code = "";
		status = SmsTypeEnum.Default.getCode();
		createTime = new Date();
		updateTime = new Date();
	}

	public SmsCodeDTO(int id, String mobile, String code, int status, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.mobile = mobile;
		this.code = code;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
		return "SmsCode [id=" + id + ", mobile=" + mobile + ", code=" + code + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}

}
