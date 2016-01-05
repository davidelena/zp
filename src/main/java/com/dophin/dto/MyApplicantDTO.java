package com.dophin.dto;

import com.dophin.enums.EducationEnum;

/**
 * 我的申请人列表
 * 
 * @author David.dai
 * 
 */
public class MyApplicantDTO {
	private int id;
	private String memberId;
	private String memberName;
	private int recruitId;
	private String recruitName;
	private String deliverTime;
	private int resumeId;
	private String resumeAvatar;
	private int workExpCount;
	private int feedStatus;
	private int schoolId;
	private String schoolName;
	private String majorName;
	private int diploma;
	private String diplomaDesc;
	private int graduationYear;
	private int sex;

	public MyApplicantDTO() {
		super();
		id = 0;
		memberId = "";
		memberName = "";
		recruitId = 0;
		recruitName = "";
		deliverTime = "";
		resumeId = 0;
		resumeAvatar = "";
		workExpCount = 0;
		feedStatus = 0;
		schoolId = 0;
		schoolName = "";
		majorName = "";
		diploma = 0;
		diplomaDesc = "";
		graduationYear = 0;
		sex = 0;
	}

	public MyApplicantDTO(int id, String memberId, String memberName, int recruitId, String recruitName,
			String deliverTime, int resumeId, String resumeAvatar, int workExpCount, int feedStatus, int schoolId,
			String schoolName, String majorName, int diploma, int graduationYear, int sex) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.memberName = memberName;
		this.recruitId = recruitId;
		this.recruitName = recruitName;
		this.deliverTime = deliverTime;
		this.resumeId = resumeId;
		this.resumeAvatar = resumeAvatar;
		this.workExpCount = workExpCount;
		this.feedStatus = feedStatus;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.majorName = majorName;
		this.diploma = diploma;
		this.graduationYear = graduationYear;
		this.sex = sex;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(int recruitId) {
		this.recruitId = recruitId;
	}

	public String getRecruitName() {
		return recruitName;
	}

	public void setRecruitName(String recruitName) {
		this.recruitName = recruitName;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	public String getResumeAvatar() {
		return resumeAvatar;
	}

	public void setResumeAvatar(String resumeAvatar) {
		this.resumeAvatar = resumeAvatar;
	}

	public int getWorkExpCount() {
		return workExpCount;
	}

	public void setWorkExpCount(int workExpCount) {
		this.workExpCount = workExpCount;
	}

	public int getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(int feedStatus) {
		this.feedStatus = feedStatus;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public int getDiploma() {
		return diploma;
	}

	public void setDiploma(int diploma) {
		this.diploma = diploma;
	}

	public int getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(int graduationYear) {
		this.graduationYear = graduationYear;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getDiplomaDesc() {
		diplomaDesc = EducationEnum.getEducationDesc(diploma);
		return diplomaDesc;
	}

	@Override
	public String toString() {
		return "MyApplicantDTO [memberId=" + memberId + ", memberName=" + memberName + ", recruitId=" + recruitId
				+ ", recruitName=" + recruitName + ", deliverTime=" + deliverTime + ", resumeId=" + resumeId
				+ ", resumeAvatar=" + resumeAvatar + ", workExpCount=" + workExpCount + ", feedStatus=" + feedStatus
				+ ", schoolId=" + schoolId + ", schoolName=" + schoolName + ", majorName=" + majorName + ", diploma="
				+ diploma + ", graduationYear=" + graduationYear + ", sex=" + sex + "]";
	}

}
