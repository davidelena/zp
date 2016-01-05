package com.dophin.enums;

/**
 * 用户操作枚举
 * 
 * @author dailiwei
 * 
 */
public enum ApplyTypeEnum {

	Default(0, "默认"),

	Apply(1, "申请"),

	Favorite(2, "收藏");

	private int code;

	private String desc;

	private ApplyTypeEnum(int code, String desc) {
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

	public static ApplyTypeEnum getApplyTypeEnum(int code) {
		ApplyTypeEnum result = null;
		ApplyTypeEnum[] applyStatusEnums = ApplyTypeEnum.values();
		for (ApplyTypeEnum applyStatusEnum : applyStatusEnums) {
			if (code == applyStatusEnum.getCode()) {
				result = applyStatusEnum;
				break;
			}
		}

		return result;
	}

	public static String getApplyTypeDesc(int code) {
		ApplyTypeEnum result = getApplyTypeEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
