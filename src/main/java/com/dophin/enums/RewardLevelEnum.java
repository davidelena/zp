package com.dophin.enums;

/**
 * 
 * @author dailiwei
 * 
 */
public enum RewardLevelEnum {

	DepartmentLevel(1, "院级"),

	SchoolLevel(2, "校级"),

	CityLevel(3, "市级"),

	ProvinceLevel(4, "省级"),

	NationalLevel(5, "国家级"),

	InternationalLevel(6, "国际级");

	private int code;

	private String desc;

	private RewardLevelEnum(int code, String desc) {
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
	
	public static RewardLevelEnum getRewardLevelEnum(int code) {
		RewardLevelEnum result = null;
		RewardLevelEnum[] rewardLevelEnums = RewardLevelEnum.values();
		for (RewardLevelEnum rewardLevelEnum : rewardLevelEnums) {
			if (code == rewardLevelEnum.getCode()) {
				result = rewardLevelEnum;
				break;
			}
		}

		return result;
	}

	public static String getgetRewardLevelDesc(int code) {
		RewardLevelEnum result = getRewardLevelEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
