package com.dophin.enums;

/**
 * 发布职位时简历转发类型（1-转发到我邮箱，2-仅提醒数量）
 * 
 * @author dailiwei
 * 
 */
public enum RecruitEmailTypeEnum {

	SendToMailbox(1, "简历转发到我邮箱"),

	OnlyToNotifyMeCount(2, "简历不转发到我邮箱，仅提醒数量");

	private int code;
	private String desc;

	private RecruitEmailTypeEnum(int code, String desc) {
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
	
	public static RecruitDemandEnum getRecruitDemandEnum(int code) {
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
