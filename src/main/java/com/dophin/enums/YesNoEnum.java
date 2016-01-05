package com.dophin.enums;

/**
 * 判断枚举
 * 
 * @author dailiwei
 * 
 */
public enum YesNoEnum {

	YES(1, "是"),

	NO(0, "否");

	private int code;

	private String desc;

	private YesNoEnum(int code, String desc) {
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
