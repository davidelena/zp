package com.dophin.enums;

/**
 * 
 * @author dailiwei
 * 
 */
public enum RecommendFrequencyEnum {

	EveryDay(1, "每天推荐一次"),

	Every3Day(2, "每三天推荐一次"),

	Every7Day(3, "每七天推荐一次"),

	None(4, "不推荐");

	private int code;

	private String desc;

	private RecommendFrequencyEnum(int code, String desc) {
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

	public static RecommendFrequencyEnum getRecommendFrequencyEnum(int code) {
		RecommendFrequencyEnum result = null;
		RecommendFrequencyEnum[] recommendFrequencyEnums = RecommendFrequencyEnum.values();
		for (RecommendFrequencyEnum recommendFrequencyEnum : recommendFrequencyEnums) {
			if (code == recommendFrequencyEnum.getCode()) {
				result = recommendFrequencyEnum;
				break;
			}
		}

		return result;
	}

	public static String getRecommendFrequencyEnumDesc(int code) {
		RecommendFrequencyEnum result = getRecommendFrequencyEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
