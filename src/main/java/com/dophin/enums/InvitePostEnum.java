package com.dophin.enums;

public enum InvitePostEnum
{
	InviteSuitableGuy(1, "邀请符合要求的同学投递"),

	NonInvite(2, "不邀请投递");

	private int code;

	private String desc;

	private InvitePostEnum(int code, String desc)
	{
		this.code=code;
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
	
	public static InvitePostEnum getInvitePostEnum(int code)
	{
		InvitePostEnum result = null;
		InvitePostEnum[] invitePostEnums = InvitePostEnum.values();
		for (InvitePostEnum invitePostEnum : invitePostEnums)
		{
			if (code == invitePostEnum.getCode())
			{
				result = invitePostEnum;
				break;
			}
		}

		return result;
	}

	public static String getInvitePostDesc(int code)
	{
		InvitePostEnum result = getInvitePostEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
