package com.dophin.enums;

/**
 * 职位操作枚举
 * 
 * @author dailiwei
 * 
 */
public enum PositionOperationEnum {

	Apply(1, "申请"),

	Collect(2, "收藏");

	private int code;

	private String desc;

	private PositionOperationEnum(int code, String desc) {
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
