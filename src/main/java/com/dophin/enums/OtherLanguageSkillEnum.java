package com.dophin.enums;

/**
 * 其他语言技能
 * 
 * @author Administrator
 * 
 */
public enum OtherLanguageSkillEnum {

	Japanese(1, "日语"),

	French(2, "法语"),

	German(3, "德语"),

	Russian(4, "俄语"),

	Korean(5, "韩语"),

	Spanish(6, "西班牙语"),

	Portuguese(7, "葡萄牙语"),

	Arabic(8, "阿拉伯语"),

	Italian(9, "意大利语"),

	Chinese(10, "中文普通话"),

	Cantonese(11, "粤语"),

	ShanghaiDialect(12, "上海话"),

	Taiwanese(13, "闽南语"),

	NorthKorean(14, "朝鲜语");

	private int code;

	private String desc;

	private OtherLanguageSkillEnum(int code, String desc) {
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

	public static OtherLanguageSkillEnum getOtherLanguageSkillEnum(int code) {
		OtherLanguageSkillEnum result = null;
		OtherLanguageSkillEnum[] otherLanguageSkillEnums = OtherLanguageSkillEnum.values();
		for (OtherLanguageSkillEnum otherLanguageSkillEnum : otherLanguageSkillEnums) {
			if (code == otherLanguageSkillEnum.getCode()) {
				result = otherLanguageSkillEnum;
				break;
			}
		}

		return result;
	}

	public static String getOtherLanguageSkillDesc(int code) {
		OtherLanguageSkillEnum result = getOtherLanguageSkillEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
