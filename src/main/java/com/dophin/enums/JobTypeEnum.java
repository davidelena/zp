package com.dophin.enums;

/**
 * 职位类型（1-全职，2-实习）
 * 
 * @author dailiwei
 * 
 */
public enum JobTypeEnum {

	/**
	 * 全职
	 */
	FullTime(1, "全职"),

	/**
	 * 实习
	 */
	Internship(2, "实习");

	private int code;

	private String desc;

	private JobTypeEnum(int code, String desc) {
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

	public static JobTypeEnum getJobTypeEnum(int code) {
		JobTypeEnum result = null;
		JobTypeEnum[] jobTypeEnums = JobTypeEnum.values();
		for (JobTypeEnum jobTypeEnum : jobTypeEnums) {
			if (code == jobTypeEnum.getCode()) {
				result = jobTypeEnum;
				break;
			}
		}

		return result;
	}

	public static String getJobTypeDesc(int code) {
		JobTypeEnum result = getJobTypeEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
