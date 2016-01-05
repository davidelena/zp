package com.dophin.dto;

import java.util.Date;

/**
 * 通知面试实体
 * 
 * @author David.dai
 * 
 */
public class InformInterviewDTO
{
	private int id;
	private String memberId;
	private String memberName;
	private String memberEmail;
	private Date interviewDate;
	private String detailTime;
	private String interviewAddress;
	private String contactPerson;
	private String contactPhone;
	private String interviewContent;
	private Date createTime;
	private Date updateTime;
	private int status;

	public InformInterviewDTO()
	{
		super();
		id = 0;
		memberId = "";
		memberName = "";
		memberEmail = "";
		interviewDate = new Date();
		detailTime = "";
		interviewAddress = "";
		contactPerson = "";
		contactPhone = "";
		interviewContent = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public InformInterviewDTO(int id, String memberId, String memberName, String memberEmail, Date interviewDate,
			String detailTime, String interviewAddress, String contactPerson, String contactPhone,
			String interviewContent, Date createTime, Date updateTime, int status)
	{
		super();
		this.id = id;
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberEmail = memberEmail;
		this.interviewDate = interviewDate;
		this.detailTime = detailTime;
		this.interviewAddress = interviewAddress;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
		this.interviewContent = interviewContent;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getMemberId()
	{
		return memberId;
	}

	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}

	public String getMemberName()
	{
		return memberName;
	}

	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}

	public String getMemberEmail()
	{
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail)
	{
		this.memberEmail = memberEmail;
	}

	public Date getInterviewDate()
	{
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate)
	{
		this.interviewDate = interviewDate;
	}

	public String getDetailTime()
	{
		return detailTime;
	}

	public void setDetailTime(String detailTime)
	{
		this.detailTime = detailTime;
	}

	public String getInterviewAddress()
	{
		return interviewAddress;
	}

	public void setInterviewAddress(String interviewAddress)
	{
		this.interviewAddress = interviewAddress;
	}

	public String getContactPerson()
	{
		return contactPerson;
	}

	public void setContactPerson(String contactPerson)
	{
		this.contactPerson = contactPerson;
	}

	public String getContactPhone()
	{
		return contactPhone;
	}

	public void setContactPhone(String contactPhone)
	{
		this.contactPhone = contactPhone;
	}

	public String getInterviewContent()
	{
		return interviewContent;
	}

	public void setInterviewContent(String interviewContent)
	{
		this.interviewContent = interviewContent;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	@Override
	public String toString()
	{
		return "InformInterviewDTO [id=" + id + ", memberId=" + memberId + ", memberEmail=" + memberEmail
				+ ", interviewDate=" + interviewDate + ", detailTime=" + detailTime + ", interviewAddress="
				+ interviewAddress + ", contactPerson=" + contactPerson + ", contactPhone=" + contactPhone
				+ ", interviewContent=" + interviewContent + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", status=" + status + "]";
	}

}
