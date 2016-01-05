package com.dophin.dto;

import com.dophin.enums.FeedStatusEnum;

/**
 * 申请状态反馈类型
 * 
 * @author David.dai
 * 
 */
public class ApplyFeedBackDTO
{
	private int recruitId;

	private String positionName;

	private String companyName;

	private int resumeId;

	private String resumeName;

	private int feedStatus;

	private String feedStatusDesc;

	public ApplyFeedBackDTO()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public ApplyFeedBackDTO(int recruitId, String recruitPositionName, String companyName, int resumeId,
			String resumeName, int feedStatus, String feedStatusDesc, int status)
	{
		super();
		this.recruitId = recruitId;
		this.positionName = recruitPositionName;
		this.companyName = companyName;
		this.resumeId = resumeId;
		this.resumeName = resumeName;
		this.feedStatus = feedStatus;
		this.feedStatusDesc = feedStatusDesc;
	}

	public int getRecruitId()
	{
		return recruitId;
	}

	public void setRecruitId(int recruitId)
	{
		this.recruitId = recruitId;
	}

	public String getRecruitPositionName()
	{
		return positionName;
	}

	public void setRecruitPositionName(String recruitPositionName)
	{
		this.positionName = recruitPositionName;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public int getResumeId()
	{
		return resumeId;
	}

	public void setResumeId(int resumeId)
	{
		this.resumeId = resumeId;
	}

	public String getResumeName()
	{
		return resumeName;
	}

	public void setResumeName(String resumeName)
	{
		this.resumeName = resumeName;
	}

	public int getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(int feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public String getFeedStatusDesc()
	{
		return FeedStatusEnum.getFeedStatusDesc(feedStatus);
	}

	@Override
	public String toString()
	{
		return "ApplyFeedBackDTO [recruitId=" + recruitId + ", positionName=" + positionName + ", companyName="
				+ companyName + ", resumeId=" + resumeId + ", feedStatus=" + feedStatus + ", feedStatusDesc="
				+ getFeedStatusDesc() + "]";
	}

}
