package com.dophin.dto;

import com.dophin.enums.ComputerSkillEnum;
import com.dophin.enums.SkillLevelEnum;

/**
 * 计算机技能
 * 
 * @author dailiwei
 * 
 */
public class ComputerSkillDTO {
	private int id;
	private int skill;
	private String skillDesc;
	private int skillLevel;
	private String skillLevelDesc;
	private int status;

	public ComputerSkillDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComputerSkillDTO(int id, int skill, String skillDesc, int skillLevel, String skillLevelDesc, int status) {
		super();
		this.id = id;
		this.skill = skill;
		this.skillDesc = skillDesc;
		this.skillLevel = skillLevel;
		this.skillLevelDesc = skillLevelDesc;
		this.status = status;
	}

	public ComputerSkillDTO(int skill, int skillLevel) {
		super();
		this.skill = skill;
		this.skillLevel = skillLevel;
		this.status = 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public String getSkillDesc() {
		return ComputerSkillEnum.getComputerSkillDesc(skill);
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getSkillLevelDesc() {
		return SkillLevelEnum.getSkillLevelDesc(skillLevel);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ComputerSkillDTO [id=" + id + ", skill=" + skill + ", skillDesc=" + getSkillDesc() + ", skillLevel=" + skillLevel + ", skillLevelDesc="
				+ getSkillLevelDesc() + ", status=" + status + "]";
	}

}
