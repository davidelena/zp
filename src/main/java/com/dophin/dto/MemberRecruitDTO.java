package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dophin.enums.ApplyTypeEnum;
import com.dophin.enums.FeedStatusEnum;

/**
 * 简历-投递简历
 * 
 * @author David.dai
 * 
 */
public class MemberRecruitDTO
{

	private int id;
	private String memberId;
	private MemberDTO memberDTO;
	private int resumeId;
	private String resumeName;
	private ResumeInfoDTO resumeInfoDTO;
	private int recruitId;
	private String positionName;
	private String companyName;
	private RecruitInfoDTO recruitInfoDTO;
	private int feedStatus;
	private String feedStatusDesc;
	private int applyType;
	private String applyTypeDesc;
	private Date createTime;
	private String createTimeDesc;
	private Date updateTime;
	private String updateTimeDesc;
	private int status;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public MemberRecruitDTO()
	{
		super();
		id = 0;
		memberId = "";
		memberDTO = new MemberDTO();
		resumeId = 0;
		resumeName = "";
		resumeInfoDTO = new ResumeInfoDTO();
		recruitId = 0;
		positionName = "";
		companyName = "";
		recruitInfoDTO = new RecruitInfoDTO();
		feedStatus = 0;
		applyType = 0;
		createTime = new Date();
		updateTime = new Date();
		status = 0;
	}

	public MemberRecruitDTO(int id, String memberId, int resumeId, int recruitId, int feedStatus, int applyType,
			Date createTime, Date updateTime, int status)
	{
		super();
		this.id = id;
		this.memberId = memberId;
		this.resumeId = resumeId;
		this.recruitId = recruitId;
		this.feedStatus = feedStatus;
		this.applyType = applyType;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}

	public MemberRecruitDTO(int id, String memberId, int resumeId, String resumeName, int recruitId,
			String positionName, String companyName, int feedStatus, int applyType, Date createTime, Date updateTime,
			int status)
	{
		super();
		this.id = id;
		this.memberId = memberId;
		this.resumeId = resumeId;
		this.resumeName = resumeName;
		this.recruitId = recruitId;
		this.positionName = positionName;
		this.companyName = companyName;
		this.feedStatus = feedStatus;
		this.applyType = applyType;
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

	public MemberDTO getMemberDTO()
	{
		return memberDTO;
	}

	public void setMemberDTO(MemberDTO memberDTO)
	{
		this.memberDTO = memberDTO;
	}

	public int getResumeId()
	{
		return resumeId;
	}

	public void setResumeId(int resumeId)
	{
		this.resumeId = resumeId;
	}

	public ResumeInfoDTO getResumeInfoDTO()
	{
		return resumeInfoDTO;
	}

	public void setResumeInfoDTO(ResumeInfoDTO resumeInfoDTO)
	{
		this.resumeInfoDTO = resumeInfoDTO;
	}

	public int getRecruitId()
	{
		return recruitId;
	}

	public void setRecruitId(int recruitId)
	{
		this.recruitId = recruitId;
	}

	public RecruitInfoDTO getRecruitInfoDTO()
	{
		return recruitInfoDTO;
	}

	public void setRecruitInfoDTO(RecruitInfoDTO recruitInfoDTO)
	{
		this.recruitInfoDTO = recruitInfoDTO;
	}

	public int getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(int feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public int getApplyType()
	{
		return applyType;
	}

	public void setApplyType(int applyType)
	{
		this.applyType = applyType;
	}

	public String getResumeName()
	{
		return resumeName;
	}

	public void setResumeName(String resumeName)
	{
		this.resumeName = resumeName;
	}

	public String getPositionName()
	{
		return positionName;
	}

	public void setPositionName(String positionName)
	{
		this.positionName = positionName;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
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

	public String getCreateTimeDesc()
	{
		createTimeDesc = sdf.format(createTime);
		return createTimeDesc;
	}

	public String getUpdateTimeDesc()
	{
		updateTimeDesc = sdf.format(updateTime);
		return updateTimeDesc;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getApplyTypeDesc()
	{
		return ApplyTypeEnum.getApplyTypeDesc(applyType);
	}

	public String getFeedStatusDesc()
	{
		return FeedStatusEnum.getFeedStatusDesc(feedStatus);
	}

	@Override
	public String toString()
	{
		return "MemberRecruitDTO [id=" + id + ", memberId=" + memberId + ", resumeId=" + resumeId + ", resumeName="
				+ resumeName + ", recruitId=" + recruitId + ", positionName=" + positionName + ", companyName="
				+ companyName + ", feedStatus=" + feedStatus + ", feedStatusDesc=" + getFeedStatusDesc()
				+ ", applyType=" + applyType + ", applyTypeDesc=" + getApplyTypeDesc() + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
