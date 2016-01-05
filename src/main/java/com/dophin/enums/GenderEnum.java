package com.dophin.enums;

public enum GenderEnum
{

	Male(1, "男"),

	Female(2, "女");

	private int code;
	private String desc;

	private GenderEnum(int code, String desc)
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

	public static GenderEnum getGenderEnum(int code)
	{
		GenderEnum result = null;
		GenderEnum[] genderEnums = GenderEnum.values();
		for (GenderEnum genderEnum : genderEnums)
		{
			if (code == genderEnum.getCode())
			{
				result = genderEnum;
				break;
			}
		}

		return result;
	}

	public static String getGenderDesc(int code)
	{
		GenderEnum result = getGenderEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
