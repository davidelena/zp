package com.dophin.dto;

import java.util.Date;

import com.dophin.enums.SocialNetworkEnum;

/**
 * 简历-作品
 * 
 * @author David.dai
 * 
 */
public class ResumeSocialNetDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private int account;
	private String accountDesc;
	private String url;
	private Date createTime;
	private Date updateTime;
	private int status;

	public ResumeSocialNetDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		account = 0;
		url = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeSocialNetDTO(int id, int resumeId, int account, String accountDesc, String url, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.account = account;
		this.accountDesc = accountDesc;
		this.url = url;
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

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getAccountDesc() {
		return SocialNetworkEnum.getSocialNetworkDesc(account);
	}

	@Override
	public String toString() {
		return "ResumeSocialNetDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", account=" + account + ", accountDesc="
				+ getAccountDesc() + ", url=" + url + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
