package com.dophin.enums;

/**
 * 学历枚举
 * 
 * @author dailiwei
 * 
 */
public enum EducationEnum {

	Specialty(1, "大专职高"),

	Undergraduate(2, "本科"),

	Master(3, "硕士"),

	Doctor(4, "博士");

	private int code;

	private String desc;

	private EducationEnum(int code, String desc) {
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

	public static EducationEnum getEducationEnum(int code) {
		EducationEnum result = null;
		EducationEnum[] educationEnums = EducationEnum.values();
		for (EducationEnum educationEnum : educationEnums) {
			if (code == educationEnum.getCode()) {
				result = educationEnum;
				break;
			}
		}

		return result;
	}

	public static String getEducationDesc(int code) {
		EducationEnum result = getEducationEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
