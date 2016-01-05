package com.dophin.enums;

/**
 * 公司规模
 * 
 * @author David.dai
 * 
 */
public enum CompanySizeEnum {

	Size1To50(1, "1-50人"),

	Size51To200(2, "51-200人"),

	Size201To500(3, "201-500人"),

	Size501To1000(4, "501-1000人"),

	Size1000To5000(5, "1001-5000人"),

	Size5000Plus(6, "5000人以上");

	private int code;

	private String desc;

	private CompanySizeEnum(int code, String desc) {
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
	
	public static CompanySizeEnum getCompanySizeEnum(int code) {
		CompanySizeEnum result = null;
		CompanySizeEnum[] companySizeEnums = CompanySizeEnum.values();
		for (CompanySizeEnum companySizeEnum : companySizeEnums) {
			if (code == companySizeEnum.getCode()) {
				result = companySizeEnum;
				break;
			}
		}

		return result;
	}

	public static String getCompanySizeDesc(int code) {
		CompanySizeEnum result = getCompanySizeEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
