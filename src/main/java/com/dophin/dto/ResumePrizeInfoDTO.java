package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dophin.enums.RewardLevelEnum;

/**
 * 简历-所获奖项
 * 
 * @author dailiwei
 * 
 */
public class ResumePrizeInfoDTO
{
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String prizeName;
	private int prizeLevel;
	private String prizeLevelDesc;
	private Date gainTime;
	private String gainTimeDesc;
	private Date createTime;
	private Date updateTime;
	private int status;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public ResumePrizeInfoDTO()
	{
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		prizeName = "";
		prizeLevel = 0;
		gainTime = new Date();
		gainTimeDesc = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumePrizeInfoDTO(int id, int resumeId, String prizeName, int prizeLevel, Date gainTime, Date createTime,
			Date updateTime, int status)
	{
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.prizeName = prizeName;
		this.prizeLevel = prizeLevel;
		this.gainTime = gainTime;
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

	public String getPrizeName()
	{
		return prizeName;
	}

	public void setPrizeName(String prizeName)
	{
		this.prizeName = prizeName;
	}

	public int getPrizeLevel()
	{
		return prizeLevel;
	}

	public void setPrizeLevel(int prizeLevel)
	{
		this.prizeLevel = prizeLevel;
	}

	public Date getGainTime()
	{
		return gainTime;
	}

	public void setGainTime(Date gainTime)
	{
		this.gainTime = gainTime;
	}

	public String getGainTimeDesc()
	{
		gainTimeDesc = sdf.format(gainTime);
		return gainTimeDesc;
	}

	public void setGainTimeDesc(String gainTimeDesc)
	{
		gainTimeDesc = sdf.format(gainTime);
		this.gainTimeDesc = gainTimeDesc;
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

	public String getPrizeLevelDesc()
	{
		return RewardLevelEnum.getgetRewardLevelDesc(prizeLevel);
	}

	@Override
	public String toString()
	{
		return "ResumePrizeInfoDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO
				+ ", prizeName=" + prizeName + ", prizeLevel=" + prizeLevel + ", prizeLevelDesc=" + getPrizeLevelDesc()
				+ ", gainTime=" + gainTime + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status="
				+ status + "]";
	}

}
