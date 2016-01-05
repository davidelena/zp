package com.dophin.dto;

import com.dophin.enums.OtherLanguageSkillEnum;

/**
 * 其他语言-技能
 * 
 * @author dailiwei
 * 
 */
public class OtherLangSkillDTO {
	private int id;
	private int name;
	private String nameDesc;
	private String level;

	public OtherLangSkillDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OtherLangSkillDTO(int id, int name, String nameDesc, String level) {
		super();
		this.id = id;
		this.name = name;
		this.nameDesc = nameDesc;
		this.level = level;
	}

	public OtherLangSkillDTO(int name, String level) {
		super();
		this.name = name;
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public String getNameDesc() {
		return OtherLanguageSkillEnum.getOtherLanguageSkillDesc(name);
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "OtherLangSkillDTO [id=" + id + ", name=" + name + ", nameDesc=" + getNameDesc() + ", level=" + level + "]";
	}

}
