package com.dophin.enums;

/**
 * 成绩枚举
 * 
 * @author dailiwei
 * 
 */
public enum AchievementEnum {

	Percentage5(1, "前5%"),

	Percentage10(2, "前10%"),

	Percentage30(3, "前30%"),

	Percentage50(4, "前50%"),

	Other(5, "其他");

	private int code;

	private String desc;

	private AchievementEnum(int code, String desc) {
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
	
	public static AchievementEnum getAchievementEnum(int code) {
		AchievementEnum result = null;
		AchievementEnum[] achievementEnums = AchievementEnum.values();
		for (AchievementEnum achievementEnum : achievementEnums) {
			if (code == achievementEnum.getCode()) {
				result = achievementEnum;
				break;
			}
		}

		return result;
	}

	public static String getAchievementDesc(int code) {
		AchievementEnum result = getAchievementEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
