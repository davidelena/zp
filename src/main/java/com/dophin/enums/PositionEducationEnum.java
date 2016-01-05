package com.dophin.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 职位精准定位学历枚举
 * 
 * @author dailiwei
 * 
 */
public enum PositionEducationEnum {

	NoLimit(1, "不限", Arrays.asList(new EducationEnum[] { EducationEnum.Specialty, EducationEnum.Undergraduate, EducationEnum.Master, EducationEnum.Doctor })),

	Specialty(2, "大专职高", Arrays.asList(new EducationEnum[] { EducationEnum.Specialty })),

	UndergraduatePlus(3, "本科及以上", Arrays.asList(new EducationEnum[] { EducationEnum.Undergraduate, EducationEnum.Master, EducationEnum.Doctor })),

	MasterPlus(4, "硕士及以上", Arrays.asList(new EducationEnum[] { EducationEnum.Master, EducationEnum.Doctor })),

	DoctorPlus(5, "博士", Arrays.asList(new EducationEnum[] { EducationEnum.Doctor }));

	private int code;

	private String desc;

	private List<EducationEnum> educationList;

	private PositionEducationEnum(int code, String desc, List<EducationEnum> educationList) {
		this.code = code;
		this.desc = desc;
		this.educationList = educationList;
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

	public List<EducationEnum> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<EducationEnum> educationList) {
		this.educationList = educationList;
	}
	
	public static PositionEducationEnum getPositionEducationEnum(int code) {
		PositionEducationEnum result = null;
		PositionEducationEnum[] positionEducationEnums = PositionEducationEnum.values();
		for (PositionEducationEnum positionEducationEnum : positionEducationEnums) {
			if (code == positionEducationEnum.getCode()) {
				result = positionEducationEnum;
				break;
			}
		}

		return result;
	}

	public static String getPositionEducationDesc(int code) {
		PositionEducationEnum result = getPositionEducationEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
