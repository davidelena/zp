package com.dophin.enums;

/**
 * 申请进度枚举
 * 
 * @author dailiwei
 * 
 */
public enum ApplyProgressEnum
{

	InToday(1, 0, "今天截止"),

	In3Days(2, 3, "三天内截止"),

	In7Days(3, 7, "七天内截止"),

	In7PlusDays(4, 7, "七天以上截止");

	private int code;

	private int days;

	private String desc;

	private ApplyProgressEnum(int code, int days, String desc)
	{
		this.code = code;
		this.days = days;
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

	public int getDays()
	{
		return days;
	}

	public void setDays(int days)
	{
		this.days = days;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static ApplyProgressEnum getApplyProgressEnum(int code)
	{
		ApplyProgressEnum result = null;
		ApplyProgressEnum[] applyProgressEnums = ApplyProgressEnum.values();
		for (ApplyProgressEnum applyProgressEnum : applyProgressEnums)
		{
			if (code == applyProgressEnum.getCode())
			{
				result = applyProgressEnum;
				break;
			}
		}

		return result;
	}

	public static String getApplyProgressDesc(int code)
	{
		ApplyProgressEnum result = getApplyProgressEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
