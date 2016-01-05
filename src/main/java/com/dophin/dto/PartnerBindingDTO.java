package com.dophin.dto;

import java.util.Date;

/**
 * 登录第三方合作商
 * 
 * @author dailiwei
 * 
 */
public class PartnerBindingDTO {

	private int id;
	private String memberId;
	private String partnerId;
	private String accessToken;
	private long tokenExpire;
	private boolean isValid;
	private int partnerType;
	private Date createTime;
	private Date updateTime;
	private int status;

	public PartnerBindingDTO() {
		super();
		id = 0;
		memberId = "";
		partnerId = "";
		accessToken = "";
		tokenExpire = 0;
		isValid = false;
		partnerType = 0;
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public PartnerBindingDTO(int id, String memberId, String partnerId, String accessToken, long tokenExpire, boolean isValid, int partnerType,
			Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.partnerId = partnerId;
		this.accessToken = accessToken;
		this.tokenExpire = tokenExpire;
		this.isValid = isValid;
		this.partnerType = partnerType;
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

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getTokenExpire() {
		return tokenExpire;
	}

	public void setTokenExpire(long tokenExpire) {
		this.tokenExpire = tokenExpire;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(int partnerType) {
		this.partnerType = partnerType;
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
		return "PartnerBindingDTO [id=" + id + ", memberId=" + memberId + ", partnerId=" + partnerId + ", accessToken=" + accessToken + ", tokenExpire="
				+ tokenExpire + ", isValid=" + isValid + ", partnerType=" + partnerType + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", status=" + status + "]";
	}

}
