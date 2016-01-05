package com.dophin.enums;

/**
 * 会员类型枚举（1-学生用户，2-企业用户）
 * 
 * @author dailiwei
 * 
 */
public enum MemberSourceEnum {

	Student(1),

	Enterprise(2);

	private int code;

	private MemberSourceEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
