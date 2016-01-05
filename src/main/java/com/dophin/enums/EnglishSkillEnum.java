package com.dophin.enums;

/**
 * 英语技能
 * 
 * @author David.dai
 * 
 */
public enum EnglishSkillEnum
{

	CET4(1, "大学英语考试四级"),

	CET6(2, "大学英语考试六级"),

	Professional4(3, "专业四级"),

	Professional8(4, "专业八级"),

	ToYe(5, "托业"),

	TOEFL(6, "TOEFL"),

	GRE(7, "GRE"),

	GMAT(8, "GMAT"),

	IELTS(9, "IELTS"),

	CambridgeBizPrimary(10, "剑桥商务英语初级"),

	CambridgeBizMiddle(11, "剑桥商务英语中级"),

	CambridgeBizSenior(12, "剑桥商务英语高级"),

	CambridgeThresholdExam(13, "剑桥英语入门考试"),

	CambridgePrimaryExam(14, "剑桥初级英语考试"),

	CambridgeFirstDiphomaExam(15, "剑桥第一英语证书考试"),

	NationalCommonExam(16, "全国公共英语等级考试"),

	IntermediateVerbalEnglish(17, "中级口译"),

	SeniorVerbalEnglish(18, "高级口译");

	private int code;

	private String desc;

	private EnglishSkillEnum(int code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static EnglishSkillEnum getEnglishSkillEnum(int code)
	{
		EnglishSkillEnum result = null;
		EnglishSkillEnum[] englishSkillEnums = EnglishSkillEnum.values();
		for (EnglishSkillEnum englishSkillEnum : englishSkillEnums)
		{
			if (code == englishSkillEnum.getCode())
			{
				result = englishSkillEnum;
				break;
			}
		}

		return result;
	}

	public static String getEnglishSkillDesc(int code)
	{
		EnglishSkillEnum result = getEnglishSkillEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
