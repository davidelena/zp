package com.dophin.enums;

/**
 * 企业个人用户设置招聘需求枚举
 * 
 * @author David.dai
 * 
 */
public enum RecruitDemandEnum {

	ContactMe(1, "可以电话联系我，和我说明招聘情况"),

	ContactMeAtWorkDays(2, "可以在工作日和我联系，说明招聘情况"),

	ContactMeAtWeekend(3, "可以在周末和我联系，说明招聘情况"),

	NoneContact(4, "我不需要了解，请第一站工作人员不要电话联系我");

	private int code;

	private String desc;

	private RecruitDemandEnum(int code, String desc) {
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
	
	public static RecruitDemandEnum getRecruitDemandEnum(int code)
	{
		RecruitDemandEnum result = null;
		RecruitDemandEnum[] recruitDemandEnums = RecruitDemandEnum.values();
		for (RecruitDemandEnum recruitDemandEnum : recruitDemandEnums) {
			if (code == recruitDemandEnum.getCode()) {
				result = recruitDemandEnum;
				break;
			}
		}

		return result;
	}

	public static String getRecruitDemandDesc(int code) {
		RecruitDemandEnum result = getRecruitDemandEnum(code);
		return result == null ? "" : result.getDesc();
	}
}
