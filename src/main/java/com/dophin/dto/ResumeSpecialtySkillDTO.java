package com.dophin.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 简历-专业技能
 * 
 * @author David.dai
 * 
 */
public class ResumeSpecialtySkillDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private String enLangSkill;
	private List<EnLangSkillDTO> enLangSkillDTOs;
	private String otherLangSkill;
	private List<OtherLangSkillDTO> otherLangSkillDTOs;
	private String computerSkill;
	private List<ComputerSkillDTO> computerSkillDTOs;
	private String certificate;
	private List<CertificateDTO> certificateDTOs;
	private Date createTime;
	private Date updateTime;
	private int status;

	public ResumeSpecialtySkillDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		enLangSkill = "";
		enLangSkillDTOs = new ArrayList<>();
		otherLangSkill = "";
		otherLangSkillDTOs = new ArrayList<>();
		computerSkill = "";
		computerSkillDTOs = new ArrayList<>();
		certificate = "";
		certificateDTOs = new ArrayList<>();
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeSpecialtySkillDTO(int id, int resumeId, String enLangSkill, String otherLangSkill, String computerSkill, String certificate, Date createTime,
			Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.enLangSkill = enLangSkill;
		this.otherLangSkill = otherLangSkill;
		this.computerSkill = computerSkill;
		this.certificate = certificate;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	public ResumeInfoDTO getResumeInfoDTO() {
		return resumeInfoDTO;
	}

	public void setResumeInfoDTO(ResumeInfoDTO resumeInfoDTO) {
		this.resumeInfoDTO = resumeInfoDTO;
	}

	public String getEnLangSkill() {
		return enLangSkill;
	}

	public void setEnLangSkill(String enLangSkill) {
		this.enLangSkill = enLangSkill;
	}

	public String getOtherLangSkill() {
		return otherLangSkill;
	}

	public void setOtherLangSkill(String otherLangSkill) {
		this.otherLangSkill = otherLangSkill;
	}

	public String getComputerSkill() {
		return computerSkill;
	}

	public void setComputerSkill(String computerSkill) {
		this.computerSkill = computerSkill;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public List<EnLangSkillDTO> getEnLangSkillDTOs() {
		return enLangSkillDTOs;
	}

	public void setEnLangSkillDTOs(List<EnLangSkillDTO> enLangSkillDTOs) {
		this.enLangSkillDTOs = enLangSkillDTOs;
	}

	public List<OtherLangSkillDTO> getOtherLangSkillDTOs() {
		return otherLangSkillDTOs;
	}

	public void setOtherLangSkillDTOs(List<OtherLangSkillDTO> otherLangSkillDTOs) {
		this.otherLangSkillDTOs = otherLangSkillDTOs;
	}

	public List<ComputerSkillDTO> getComputerSkillDTOs() {
		return computerSkillDTOs;
	}

	public void setComputerSkillDTOs(List<ComputerSkillDTO> computerSkillDTOs) {
		this.computerSkillDTOs = computerSkillDTOs;
	}

	public List<CertificateDTO> getCertificateDTOs() {
		return certificateDTOs;
	}

	public void setCertificateDTOs(List<CertificateDTO> certificateDTOs) {
		this.certificateDTOs = certificateDTOs;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResumeSpecialtySkillDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", enLangSkill=" + enLangSkill
				+ ", otherLangSkill=" + otherLangSkill + ", computerSkill=" + computerSkill + ", certificate=" + certificate + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
