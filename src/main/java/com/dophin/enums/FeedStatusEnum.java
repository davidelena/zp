package com.dophin.enums;

/**
 * 反馈状态枚举
 * 
 * @author dailiwei
 * 
 */
public enum FeedStatusEnum
{

	NonViewed(1, "未查看"),

	Undetermined(2, "待定"),

	InformInterview(3, "通知面试"),

	Inappropriate(4, "不合适"),

	All(5, "全部");

	private int code;

	private String desc;

	private FeedStatusEnum(int code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static FeedStatusEnum getFeedStatusEnum(int code)
	{
		FeedStatusEnum result = null;
		FeedStatusEnum[] feedStatusEnums = FeedStatusEnum.values();
		for (FeedStatusEnum feedStatusEnum : feedStatusEnums)
		{
			if (code == feedStatusEnum.getCode())
			{
				result = feedStatusEnum;
				break;
			}
		}

		return result;
	}

	public static String getFeedStatusDesc(int code)
	{
		FeedStatusEnum result = getFeedStatusEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
