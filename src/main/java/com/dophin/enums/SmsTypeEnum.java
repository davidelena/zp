package com.dophin.enums;

public enum SmsTypeEnum
{

	Default(0, "默认"),

	Register(1, "注册"),

	Notification(2, "通知"),

	ResetPassword(3, "重置密码"),

	BindingMobile(4, "绑定手机");

	private int code;

	private String desc;

	private SmsTypeEnum(int code, String desc)
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

}
