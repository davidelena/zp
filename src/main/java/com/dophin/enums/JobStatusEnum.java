package com.dophin.enums;

/**
 * 求职状态
 * 
 * @author dailiwei
 * 
 */
public enum JobStatusEnum {

	Applying(1, "正在求职"),

	NoButWantApply(2, "未求职，但可以看看职位"),

	NoApply(3, "未求职，也不想看职位");

	private int code;

	private String desc;

	private JobStatusEnum(int code, String desc) {
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

	public static JobStatusEnum getJobStatusEnum(int code) {
		JobStatusEnum result = null;
		JobStatusEnum[] jobStatusEnums = JobStatusEnum.values();
		for (JobStatusEnum jobStatusEnum : jobStatusEnums) {
			if (code == jobStatusEnum.getCode()) {
				result = jobStatusEnum;
				break;
			}
		}

		return result;
	}

	public static String getJobStatusEnumDesc(int code) {
		JobStatusEnum result = getJobStatusEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
