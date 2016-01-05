package com.dophin.enums;

/**
 * 技能熟练程度
 * 
 * @author David.dai
 * 
 */
public enum SkillLevelEnum {

	Master(1, "精通"),

	Skillful(2, "熟悉"),

	General(3, "一般");

	private int code;

	private String desc;

	private SkillLevelEnum(int code, String desc) {
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
	
	public static SkillLevelEnum getSkillLevelEnum(int code)
	{
		SkillLevelEnum result = null;
		SkillLevelEnum[] skillLevelEnums = SkillLevelEnum.values();
		for (SkillLevelEnum skillLevelEnum : skillLevelEnums)
		{
			if (code == skillLevelEnum.getCode())
			{
				result = skillLevelEnum;
				break;
			}
		}

		return result;
	}

	public static String getSkillLevelDesc(int code)
	{
		SkillLevelEnum result = getSkillLevelEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
