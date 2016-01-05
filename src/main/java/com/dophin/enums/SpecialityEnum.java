package com.dophin.enums;

/**
 * 学校专业枚举
 * 
 * @author dailiwei
 * 
 */
public enum SpecialityEnum {

	Management(1, "管理学"),

	Economics(2, "经济学"),

	Engineer(3, "工学"),

	Science(4, "经济学"),

	Literature(5, "文学"),

	History(6, "历史学"),

	Agriculture(7, "农学"),

	Philosophy(8, "哲学"),

	Education(9, "教育学"),

	Art(10, "艺术学"),

	Law(11, "法学"),

	MedicalScience(12, "医学"),

	NoLimit(13, "不限");

	private int code;

	private String desc;

	private SpecialityEnum(int code, String desc) {
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
	
	public static SpecialityEnum getSpecialityEnum(int code) {
		SpecialityEnum result = null;
		SpecialityEnum[] specialityEnums = SpecialityEnum.values();
		for (SpecialityEnum specialityEnum : specialityEnums) {
			if (code == specialityEnum.getCode()) {
				result = specialityEnum;
				break;
			}
		}

		return result;
	}

	public static String getSpecialityDesc(int code) {
		SpecialityEnum result = getSpecialityEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
