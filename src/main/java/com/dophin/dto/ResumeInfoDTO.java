package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dophin.enums.EducationEnum;
import com.dophin.enums.GenderEnum;

/**
 * 简历信息类
 * 
 * @author David.dai
 * 
 */
public class ResumeInfoDTO {
	private int id;
	private String memberId;
	private MemberDTO memberDTO;
	private String resumeName;
	private String avatar;
	private int sex;
	private String sexDesc;
	private String major;
	private int diploma;
	private String diplomaDesc;
	private Date graduationTime;
	private String graduationTimeDesc;
	private int graduationYear;
	private String uploadPath;
	private String uploadPathFile;
	private Date createTime;
	private Date updateTime;
	private int status;
	private List<ResumeEducationExpDTO> resumeEducationExpDTOs;
	private List<ResumeWorkExpDTO> resumeWorkExpDTOs;
	private ResumeSpecialtySkillDTO resumeSpecialtySkillDTO;
	private List<ResumeActivityExpDTO> resumeActivityExpDTOs;
	private List<ResumePrizeInfoDTO> resumePrizeInfoDTOs;
	private List<ResumeProjectExpDTO> resumeProjectExpDTOs;
	private List<ResumeHobbySpecialDTO> resumeHobbySpecialDTOs;
	private List<ResumeSocialNetDTO> resumeSocialNetDTOs;
	private List<ResumeOpusInfoDTO> resumeOpusInfoDTOs;
	private List<ResumeCustomDTO> resumeCustomDTOs;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public ResumeInfoDTO() {
		super();
		id = 0;
		memberId = "";
		memberDTO = new MemberDTO();
		resumeName = "";
		avatar = "";
		sex = 0;
		major = "";
		diploma = 0;
		graduationTime = new Date();
		graduationTimeDesc = "";
		graduationYear = 0;
		uploadPath = "";
		uploadPathFile = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
		resumeEducationExpDTOs = new ArrayList<ResumeEducationExpDTO>();
		resumeWorkExpDTOs = new ArrayList<ResumeWorkExpDTO>();
		resumeActivityExpDTOs = new ArrayList<ResumeActivityExpDTO>();
		resumePrizeInfoDTOs = new ArrayList<ResumePrizeInfoDTO>();
		resumeProjectExpDTOs = new ArrayList<ResumeProjectExpDTO>();
		resumeHobbySpecialDTOs = new ArrayList<ResumeHobbySpecialDTO>();
		resumeSocialNetDTOs = new ArrayList<ResumeSocialNetDTO>();
		resumeOpusInfoDTOs = new ArrayList<ResumeOpusInfoDTO>();
		resumeCustomDTOs = new ArrayList<ResumeCustomDTO>();
	}

	public ResumeInfoDTO(int id, String memberId, String resumeName, String avatar, int sex, String major, int diploma,
			Date graduationTime, int graducationYear, String uploadPath, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.resumeName = resumeName;
		this.avatar = avatar;
		this.sex = sex;
		this.major = major;
		this.diploma = diploma;
		this.graduationTime = graduationTime;
		this.graduationYear = graducationYear;
		this.uploadPath = uploadPath;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public MemberDTO getMemberDTO() {
		return memberDTO;
	}

	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getDiploma() {
		return diploma;
	}

	public void setDiploma(int diploma) {
		this.diploma = diploma;
	}

	public Date getGraduationTime() {
		return graduationTime;
	}

	public void setGraduationTime(Date graduationTime) {
		this.graduationTime = graduationTime;
	}

	public String getGraduationTimeDesc() {
		String result = sdf.format(graduationTime);
		graduationTimeDesc = result;
		return graduationTimeDesc;
	}

	public void setGraduationTimeDesc(String graducationTimeDesc) {
		String result = sdf.format(graduationTime);
		this.graduationTimeDesc = result;
	}

	public int getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(int graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
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

	public String getDiplomaDesc() {
		return EducationEnum.getEducationDesc(diploma);
	}

	public String getSexDesc() {
		return GenderEnum.getGenderDesc(sex);
	}

	public List<ResumeEducationExpDTO> getResumeEducationExpDTOs() {
		return resumeEducationExpDTOs;
	}

	public void setResumeEducationExpDTOs(List<ResumeEducationExpDTO> resumeEducationExpDTOs) {
		this.resumeEducationExpDTOs = resumeEducationExpDTOs;
	}

	public List<ResumeWorkExpDTO> getResumeWorkExpDTOs() {
		return resumeWorkExpDTOs;
	}

	public void setResumeWorkExpDTOs(List<ResumeWorkExpDTO> resumeWorkExpDTOs) {
		this.resumeWorkExpDTOs = resumeWorkExpDTOs;
	}

	public ResumeSpecialtySkillDTO getResumeSpecialtySkillDTO() {
		return resumeSpecialtySkillDTO;
	}

	public void setResumeSpecialtySkillDTO(ResumeSpecialtySkillDTO resumeSpecialtySkillDTO) {
		this.resumeSpecialtySkillDTO = resumeSpecialtySkillDTO;
	}

	public List<ResumeActivityExpDTO> getResumeActivityExpDTOs() {
		return resumeActivityExpDTOs;
	}

	public void setResumeActivityExpDTOs(List<ResumeActivityExpDTO> resumeActivityExpDTOs) {
		this.resumeActivityExpDTOs = resumeActivityExpDTOs;
	}

	public List<ResumePrizeInfoDTO> getResumePrizeInfoDTOs() {
		return resumePrizeInfoDTOs;
	}

	public void setResumePrizeInfoDTOs(List<ResumePrizeInfoDTO> resumePrizeInfoDTOs) {
		this.resumePrizeInfoDTOs = resumePrizeInfoDTOs;
	}

	public List<ResumeProjectExpDTO> getResumeProjectExpDTOs() {
		return resumeProjectExpDTOs;
	}

	public void setResumeProjectExpDTOs(List<ResumeProjectExpDTO> resumeProjectExpDTOs) {
		this.resumeProjectExpDTOs = resumeProjectExpDTOs;
	}

	public List<ResumeHobbySpecialDTO> getResumeHobbySpecialDTOs() {
		return resumeHobbySpecialDTOs;
	}

	public void setResumeHobbySpecialDTOs(List<ResumeHobbySpecialDTO> resumeHobbySpecialDTOs) {
		this.resumeHobbySpecialDTOs = resumeHobbySpecialDTOs;
	}

	public List<ResumeSocialNetDTO> getResumeSocialNetDTOs() {
		return resumeSocialNetDTOs;
	}

	public void setResumeSocialNetDTOs(List<ResumeSocialNetDTO> resumeSocialNetDTOs) {
		this.resumeSocialNetDTOs = resumeSocialNetDTOs;
	}

	public List<ResumeOpusInfoDTO> getResumeOpusInfoDTOs() {
		return resumeOpusInfoDTOs;
	}

	public void setResumeOpusInfoDTOs(List<ResumeOpusInfoDTO> resumeOpusInfoDTOs) {
		this.resumeOpusInfoDTOs = resumeOpusInfoDTOs;
	}

	public List<ResumeCustomDTO> getResumeCustomDTOs() {
		return resumeCustomDTOs;
	}

	public void setResumeCustomDTOs(List<ResumeCustomDTO> resumeCustomDTOs) {
		this.resumeCustomDTOs = resumeCustomDTOs;
	}

	public String getUploadPathFile() {
		uploadPathFile = uploadPath;
		if (uploadPath.contains("/")) {
			uploadPathFile = StringUtils.substringAfterLast(uploadPath, "/");
		}
		return uploadPathFile;
	}

	@Override
	public String toString() {
		return "ResumeInfoDTO [id=" + id + ", memberId=" + memberId + ", memberDTO=" + memberDTO + ", resumeName="
				+ resumeName + ", avatar=" + avatar + ", sex=" + sex + ", major=" + major + ", diploma=" + diploma
				+ ", diplomaDesc=" + getDiplomaDesc() + ", graduationTime=" + graduationTime + ", uploadPath="
				+ uploadPath + ", uploadPathFile=" + getUploadPathFile() + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + "]";
	}
}
