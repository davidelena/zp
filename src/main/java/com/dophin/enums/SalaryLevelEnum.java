package com.dophin.enums;

/**
 * 月薪范围
 * 
 * @author dailiwei
 * 
 */
public enum SalaryLevelEnum {

	Negotiable(1, "面议", -1, -1),

	S1000To2000(2, "1000~2000", 1000, 2000),

	S2000To3000(3, "2000~3000", 2000, 3000),

	S3000To5000(4, "3000~5000", 3000, 5000),

	S5000To8000(5, "5000~8000", 5000, 8000),

	S8000To10000(6, "8000~10000", 8000, 10000),

	S10000To20000(7, "10000~20000", 10000, 20000),

	S20000Plus(8, "20000以上", 20000, 99999);

	private int code;

	private String desc;

	private int top;

	private int bottom;

	private SalaryLevelEnum(int code, String desc, int bottom, int top) {
		this.code = code;
		this.desc = desc;
		this.bottom = bottom;
		this.top = top;
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

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public static SalaryLevelEnum getSalaryLevelEnum(int code) {
		SalaryLevelEnum result = null;
		SalaryLevelEnum[] salaryLevelEnums = SalaryLevelEnum.values();
		for (SalaryLevelEnum salaryLevelEnum : salaryLevelEnums) {
			if (code == salaryLevelEnum.getCode()) {
				result = salaryLevelEnum;
				break;
			}
		}

		return result;
	}

	public static String getSalaryLevelDesc(int code) {
		SalaryLevelEnum result = getSalaryLevelEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
