package com.dophin.enums;

public enum PositionAvailableEnum
{
	ToAll(1, "职位对所有人可见"),

	ToSuitableGuy(2, "职位仅对适合该职位的学生");

	private int code;
	private String desc;

	private PositionAvailableEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static PositionAvailableEnum getPositionAvailableEnum(int code)
	{
		PositionAvailableEnum result = null;
		PositionAvailableEnum[] positionAvailableEnums = PositionAvailableEnum.values();
		for (PositionAvailableEnum positionAvailableEnum : positionAvailableEnums)
		{
			if (code == positionAvailableEnum.getCode())
			{
				result = positionAvailableEnum;
				break;
			}
		}

		return result;
	}

	public static String getPositionAvailableDesc(int code)
	{
		PositionAvailableEnum result = getPositionAvailableEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
