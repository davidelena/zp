package com.dophin.enums;

/**
 * 实习天数枚举
 * 
 * @author dailiwei
 * 
 */
public enum InternshipDaysEnum {

	Day5(5, "5天"),

	Day4(4, "4天"),

	Day3(3, "3天"),

	Day2(2, "2天"),

	Day1(1, "1天");

	private int code;

	private String desc;

	private InternshipDaysEnum(int code, String desc) {
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

	public static InternshipDaysEnum getInternshipDaysEnum(int code) {
		InternshipDaysEnum result = null;
		InternshipDaysEnum[] internshipDaysEnums = InternshipDaysEnum.values();
		for (InternshipDaysEnum internshipDaysEnum : internshipDaysEnums) {
			if (code == internshipDaysEnum.getCode()) {
				result = internshipDaysEnum;
				break;
			}
		}

		return result;
	}

	public static String getInternshipDaysDesc(int code) {
		InternshipDaysEnum result = getInternshipDaysEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
