package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dophin.enums.AchievementEnum;
import com.dophin.enums.EducationEnum;
import com.dophin.enums.SpecialityEnum;

/**
 * 简历-教育经历
 * 
 * @author David.dai
 * 
 */
public class ResumeEducationExpDTO {
	private int id;
	private int resumeId;
	private ResumeInfoDTO resumeInfoDTO;
	private int diploma;
	private String diplomaDesc;
	private String school;
	private String major;
	private int majorType;
	private String majorTypeDesc;
	private int scoreTop;
	private String scoreTopDesc;
	private Date academicStarts;
	private String academicStartsDesc;
	private Date graduationTime;
	private String graduationTimeDesc;
	private Date createTime;
	private Date updateTime;
	private int status;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public ResumeEducationExpDTO() {
		super();
		id = 0;
		resumeId = 0;
		resumeInfoDTO = new ResumeInfoDTO();
		diploma = 0;
		school = "";
		major = "";
		majorType = 0;
		scoreTop = 0;
		academicStarts = new Date();
		academicStartsDesc = "";
		graduationTime = new Date();
		graduationTimeDesc = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public ResumeEducationExpDTO(int id, int resumeId, int diploma, String diplomaDesc, int schoolId, String schoolDesc, String school, String major,
			int majorType, int scoreTop, Date academicStarts, Date graduationTime, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.resumeId = resumeId;
		this.diploma = diploma;
		this.diplomaDesc = diplomaDesc;
		this.school = school;
		this.major = major;
		this.majorType = majorType;
		this.scoreTop = scoreTop;
		this.academicStarts = academicStarts;
		this.graduationTime = graduationTime;
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

	public int getDiploma() {
		return diploma;
	}

	public void setDiploma(int diploma) {
		this.diploma = diploma;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public ResumeInfoDTO getResumeInfoDTO() {
		return resumeInfoDTO;
	}

	public void setResumeInfoDTO(ResumeInfoDTO resumeInfoDTO) {
		this.resumeInfoDTO = resumeInfoDTO;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getMajorType() {
		return majorType;
	}

	public void setMajorType(int majorType) {
		this.majorType = majorType;
	}

	public int getScoreTop() {
		return scoreTop;
	}

	public void setScoreTop(int scoreTop) {
		this.scoreTop = scoreTop;
	}

	public Date getAcademicStarts() {
		return academicStarts;
	}

	public void setAcademicStarts(Date academicStarts) {
		this.academicStarts = academicStarts;
	}

	public String getAcademicStartsDesc() {
		academicStartsDesc = sdf.format(academicStarts);
		return academicStartsDesc;
	}

	public void setAcademicStartsDesc(String academicStartsDesc) {
		this.academicStartsDesc = academicStartsDesc;
	}

	public String getGraduationTimeDesc() {
		graduationTimeDesc = sdf.format(graduationTime);
		return graduationTimeDesc;
	}

	public void setGraduationTimeDesc(String graduationTimeDesc) {
		this.graduationTimeDesc = graduationTimeDesc;
	}

	public Date getGraduationTime() {
		return graduationTime;
	}

	public void setGraduationTime(Date graduationTime) {
		this.graduationTime = graduationTime;
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

	public String getMajorTypeDesc() {
		return SpecialityEnum.getSpecialityDesc(majorType);
	}

	public String getScoreTopDesc() {
		return AchievementEnum.getAchievementDesc(scoreTop);
	}

	@Override
	public String toString() {
		return "ResumeEducationExpDTO [id=" + id + ", resumeId=" + resumeId + ", resumeInfoDTO=" + resumeInfoDTO + ", diploma=" + diploma + ", diplomaDesc="
				+ getDiplomaDesc() + ", school=" + school + ", major=" + major + ", majorType=" + majorType + ", majorTypeDesc=" + getMajorTypeDesc()
				+ ", scoreTop=" + scoreTop + ", scoreTopDesc=" + getScoreTopDesc() + ", academicStarts=" + academicStarts + ", graduationTime="
				+ graduationTime + ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
