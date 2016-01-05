package com.dophin.enums;

/**
 * 实习经历枚举
 * 
 * @author dailiwei
 * 
 */
public enum InternshipExpEnum
{

	NeedInternshipExp(1, "需要有实习经历"),

	NoLimit(2, "不限");

	private int code;

	private String desc;

	private InternshipExpEnum(int code, String desc)
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

	public static InternshipExpEnum getInternshipExpEnum(int code)
	{
		InternshipExpEnum result = null;
		InternshipExpEnum[] internshipExpEnums = InternshipExpEnum.values();
		for (InternshipExpEnum internshipExpEnum : internshipExpEnums)
		{
			if (code == internshipExpEnum.getCode())
			{
				result = internshipExpEnum;
				break;
			}
		}

		return result;
	}

	public static String getInternshipExpDesc(int code)
	{
		InternshipExpEnum result = getInternshipExpEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
