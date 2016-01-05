package com.dophin.enums;

/**
 * 合作类型
 * 
 * @author dailiwei
 * 
 */
public enum PartnerTypeEnum {
	
	QQ(1, "QQ"),

	WeChat(2, "微信");

	private int code;
	private String desc;

	private PartnerTypeEnum(int code, String desc) {
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

}
