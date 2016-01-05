package com.dophin.enums;

/**
 * 职位状态枚举
 * 
 * @author dailiwei
 * 
 */
public enum PositionStatusEnum {

	Online(1, "正在招募职位"),

	Offline(2, "已下线职位"),

	Draft(3, "未发布"),

	All(4, "全部");

	private int code;

	private String desc;

	private PositionStatusEnum(int code, String desc) {
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

	public static PositionStatusEnum getPositionStatusEnum(int code) {
		PositionStatusEnum result = null;
		PositionStatusEnum[] positionStatusEnums = PositionStatusEnum.values();
		for (PositionStatusEnum positionStatusEnum : positionStatusEnums) {
			if (code == positionStatusEnum.getCode()) {
				result = positionStatusEnum;
				break;
			}
		}

		return result;
	}

	public static String getPositionStatusDesc(int code) {
		PositionStatusEnum result = getPositionStatusEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
