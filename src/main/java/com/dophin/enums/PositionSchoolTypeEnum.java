package com.dophin.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 职位精准定位-学校
 * 
 * @author dailiwei
 * 
 */
public enum PositionSchoolTypeEnum {

	SchoolType985(1, "985院校", "985", 1),

	SchoolType211(2, "211院校", "211", 1),

	SchoolTypeFirst(3, "一本院校", "", 1),

	SchoolTypeSecond(4, "二本院校", "", 2),

	SchoolTypeThird(5, "三本院校", "", 3),

	SchoolTypeSpecialty(6, "专科职高", "", 4),

	SchoolTypeOverseas(7, "港澳台和海外高校", "", 5),

	SchoolTypeNoLimit(8, "不限", "", 0),

	SchoolTypeC9(9, "C9高校", "c9", 0);

	private int code;

	/**
	 * 类型描述文本
	 */
	private String desc;

	/**
	 * 211或985,all表示两者皆是
	 */
	private String tag;

	/**
	 * 学校等级分类 1,2,3本，4专科，5-海外高校
	 */
	private int type;

	private PositionSchoolTypeEnum(int code, String desc, String tag, int type) {
		this.code = code;
		this.desc = desc;
		this.tag = tag;
		this.type = type;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static PositionSchoolTypeEnum getPositionSchoolTypeEnum(int code) {
		PositionSchoolTypeEnum result = null;
		PositionSchoolTypeEnum[] positionSchoolTypeEnums = PositionSchoolTypeEnum.values();
		for (PositionSchoolTypeEnum positionSchoolTypeEnum : positionSchoolTypeEnums) {
			if (code == positionSchoolTypeEnum.getCode()) {
				result = positionSchoolTypeEnum;
				break;
			}
		}

		return result;
	}

	public static String getPositionSchoolTypeDesc(int code) {
		PositionSchoolTypeEnum result = getPositionSchoolTypeEnum(code);
		return result == null ? "" : result.getDesc();
	}

	public static List<PositionSchoolTypeEnum> getNonC9List() {
		List<PositionSchoolTypeEnum> ls = new ArrayList<>();
		PositionSchoolTypeEnum[] positionSchoolTypeEnums = PositionSchoolTypeEnum.values();
		for (PositionSchoolTypeEnum positionSchoolTypeEnum : positionSchoolTypeEnums) {
			if (positionSchoolTypeEnum.getCode() < 9) {
				ls.add(positionSchoolTypeEnum);
			}
		}
		return ls;
	}

}
