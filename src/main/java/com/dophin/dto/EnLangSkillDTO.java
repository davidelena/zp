package com.dophin.dto;

import com.dophin.enums.EnglishSkillEnum;

/**
 * 专业技能-英语
 * 
 * @author dailiwei
 * 
 */
public class EnLangSkillDTO {

	private int id;
	private int name;
	private String nameDesc;
	private String score;

	public EnLangSkillDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnLangSkillDTO(int id, int name, String nameDesc, String score) {
		super();
		this.id = id;
		this.name = name;
		this.nameDesc = nameDesc;
		this.score = score;
	}

	public EnLangSkillDTO(int name, String score) {
		super();
		this.name = name;
		this.score = score;
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
		return EnglishSkillEnum.getEnglishSkillDesc(name);
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "EnLangSkillDTO [id=" + id + ", name=" + name + ", nameDesc=" + getNameDesc() + ", score=" + score + "]";
	}

}
