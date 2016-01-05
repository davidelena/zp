package com.dophin.enums;

public enum SchoolActivityEnum {

	NeedSchoolActivityExp(1, "需要有学校活动经历"),

	NoLimit(2, "不限");

	private int code;

	private String desc;

	private SchoolActivityEnum(int code, String desc) {
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
	
	public static SchoolActivityEnum getSchoolActivityEnum(int code) {
		SchoolActivityEnum result = null;
		SchoolActivityEnum[] schoolActivityEnums = SchoolActivityEnum.values();
		for (SchoolActivityEnum schoolActivityEnum : schoolActivityEnums) {
			if (code == schoolActivityEnum.getCode()) {
				result = schoolActivityEnum;
				break;
			}
		}

		return result;
	}

	public static String getSchoolActivityDesc(int code) {
		SchoolActivityEnum result = getSchoolActivityEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
