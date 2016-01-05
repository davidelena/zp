package com.dophin.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dophin.enums.AchievementEnum;
import com.dophin.enums.ComputerSkillEnum;
import com.dophin.enums.EnglishSkillEnum;
import com.dophin.enums.InternshipDaysEnum;
import com.dophin.enums.InternshipExpEnum;
import com.dophin.enums.InvitePostEnum;
import com.dophin.enums.JobBenefitsEnum;
import com.dophin.enums.JobTypeEnum;
import com.dophin.enums.OtherLanguageSkillEnum;
import com.dophin.enums.PositionAvailableEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.PositionSchoolTypeEnum;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.enums.RecruitDemandEnum;
import com.dophin.enums.SchoolActivityEnum;
import com.dophin.enums.SpecialityEnum;

/**
 * 企业招聘发布职位
 * 
 * @author David.dai
 * 
 */
public class RecruitInfoDTO {

	private int id;

	private String memberId;

	private MemberDTO memberDTO;

	private int companyId;

	private CompanyInfoDTO companyInfoDTO;

	private int recruitType;

	private String recruitTypeDesc;

	private int positionType;

	private String positionTypeDesc;

	private String positionName;

	private String departmentName;

	private String postDuty;

	private String workAddress;

	private WorkCityDTO workCityDTO;

	private int minSalary;

	private int maxSalary;

	private String salaryDesc;

	private boolean negotiable;

	private String importantRemark;

	private String importantRemarkDesc;

	private List<JobBenefitsEnum> importantRemarkList;

	private int needNum;

	private String acceptEmail;

	private int emailType;

	private String emailTypeDesc;

	private Date validityTime;

	private String validityTimeDesc;

	private String validityTimeDesc2;

	private String validityTimeDisplay;

	private String positionStatusDesc;

	private int school;

	private String schoolDesc;

	private int major;

	private String majorDesc;

	private int educational;

	private String educationalDesc;

	private int score;

	private String scoreDesc;

	private int internshipDays;

	private String intershipDaysDesc;

	private int internshipExp;

	private String intershipExpDesc;

	private int activityExp;

	private String activityExpDesc;

	private String skill;

	private int skillLevel;

	private String skillDesc;

	private List<ComputerSkillEnum> skillList;

	private String english;

	private String englishDesc;

	private List<EnglishSkillEnum> englishSkillList;

	private String otherLanguage;

	private String otherLanguageDesc;

	private List<OtherLanguageSkillEnum> otherLanguageSkillList;

	private List<MemberRecruitDTO> memberRecruitDTOs;

	private int positionAvailable;

	private String positionAvailableDesc;

	private int invitePost;

	private String invitePostDesc;

	private String otherClaim;

	private Date createTime;

	private String createTimeDisplay;

	private Date updateTime;

	private int positionStatus;

	private int resumeCount;

	private int status;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");

	public RecruitInfoDTO() {
		super();
		id = 0;
		memberId = "";
		companyId = 0;
		recruitType = 0;
		positionType = 0;
		positionName = "";
		departmentName = "";
		postDuty = "";
		minSalary = 0;
		maxSalary = 0;
		negotiable = false;
		importantRemark = "";
		importantRemarkDesc = "";
		needNum = 0;
		acceptEmail = "";
		emailType = 0;
		workAddress = "";
		workCityDTO = new WorkCityDTO();
		validityTime = new Date();
		validityTimeDesc = "";
		positionStatus = 0;
		school = 0;
		major = 0;
		educational = 0;
		score = 0;
		internshipExp = 0;
		activityExp = 0;
		skill = "";
		skillLevel = 0;
		skillDesc = "";
		english = "";
		englishDesc = "";
		otherLanguage = "";
		otherLanguageDesc = "";
		positionAvailable = 0;
		invitePost = 0;
		otherClaim = "";
		resumeCount = 0;
		memberRecruitDTOs = new ArrayList<MemberRecruitDTO>();
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public RecruitInfoDTO(int id, String memberId, int companyId, int recruitType, int positionType,
			String positionName, String departmentName, String postDuty, String workAddress, Date appointTime,
			int minSalary, int maxSalary, boolean negotiable, String importantRemark, String importantRemarkDesc,
			int needNum, String acceptEmail, int emailType, Date validityTime, int positionStatus, int school,
			int major, int educational, int score, int internshipDays, int internshipExp, int activityExp,
			String skill, String english, String otherLanguage, int positionAvailable, int invitePost,
			String otherClaim, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.companyId = companyId;
		this.recruitType = recruitType;
		this.positionType = positionType;
		this.positionName = positionName;
		this.departmentName = departmentName;
		this.postDuty = postDuty;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.negotiable = negotiable;
		this.importantRemark = importantRemark;
		this.importantRemarkDesc = importantRemarkDesc;
		this.needNum = needNum;
		this.acceptEmail = acceptEmail;
		this.emailType = emailType;
		this.validityTime = validityTime;
		this.workAddress = workAddress;
		this.positionStatus = positionStatus;
		this.school = school;
		this.major = major;
		this.educational = educational;
		this.score = score;
		this.internshipDays = internshipDays;
		this.internshipExp = internshipExp;
		this.activityExp = activityExp;
		this.skill = skill;
		this.english = english;
		this.otherLanguage = otherLanguage;
		this.positionAvailable = positionAvailable;
		this.invitePost = invitePost;
		this.otherClaim = otherClaim;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public CompanyInfoDTO getCompanyInfoDTO() {
		return companyInfoDTO;
	}

	public void setCompanyInfoDTO(CompanyInfoDTO companyInfoDTO) {
		this.companyInfoDTO = companyInfoDTO;
	}

	public int getRecruitType() {
		return recruitType;
	}

	public void setRecruitType(int recruitType) {
		this.recruitType = recruitType;
	}

	public int getPositionType() {
		return positionType;
	}

	public void setPositionType(int positionType) {
		this.positionType = positionType;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPostDuty() {
		return postDuty;
	}

	public void setPostDuty(String postDuty) {
		this.postDuty = postDuty;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public WorkCityDTO getWorkCityDTO() {
		return workCityDTO;
	}

	public void setWorkCityDTO(WorkCityDTO workCityDTO) {
		this.workCityDTO = workCityDTO;
	}

	public int getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}

	public int getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(int maxSalary) {
		this.maxSalary = maxSalary;
	}

	public boolean getIsNegotiable() {
		return negotiable;
	}

	public void setIsNegotiable(boolean negotiable) {
		this.negotiable = negotiable;
	}

	public String getImportantRemark() {
		return importantRemark;
	}

	public void setImportantRemark(String importantRemark) {
		this.importantRemark = importantRemark;
	}

	public String getImportantRemarkDesc() {
		return importantRemarkDesc;
	}

	public void setImportantRemarkDesc(String importantRemarkDesc) {
		this.importantRemarkDesc = importantRemarkDesc;
	}

	public String getPositionAvailableDesc() {
		return PositionAvailableEnum.getPositionAvailableDesc(positionAvailable);
	}

	public String getInvitePostDesc() {
		return InvitePostEnum.getInvitePostDesc(invitePost);
	}

	public List<JobBenefitsEnum> getImportantRemarkList() {
		return importantRemarkList;
	}

	public void setImportantRemarkList(List<JobBenefitsEnum> importantRemarkList) {
		this.importantRemarkList = importantRemarkList;
	}

	public int getNeedNum() {
		return needNum;
	}

	public void setNeedNum(int needNum) {
		this.needNum = needNum;
	}

	public String getAcceptEmail() {
		return acceptEmail;
	}

	public void setAcceptEmail(String acceptEmail) {
		this.acceptEmail = acceptEmail;
	}

	public int getEmailType() {
		return emailType;
	}

	public void setEmailType(int emailType) {
		this.emailType = emailType;
	}

	public Date getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(Date validityTime) {
		this.validityTime = validityTime;
	}

	public void setValidityTimeDesc(String validityTimeDesc) {
		String result = sdf.format(validityTime);
		this.validityTimeDesc = result;
	}

	public String getValidityTimeDesc() {
		String result = sdf.format(validityTime);
		validityTimeDesc = result;
		return validityTimeDesc;
	}

	public String getValidityTimeDesc2() {
		String result = sdf2.format(validityTime);
		validityTimeDesc2 = result;
		return validityTimeDesc2;
	}

	public void setValidityTimeDesc2(String validityTimeDesc2) {
		this.validityTimeDesc2 = validityTimeDesc2;
	}

	public int getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(int positionStatus) {
		this.positionStatus = positionStatus;
	}

	public int getSchool() {
		return school;
	}

	public void setSchool(int school) {
		this.school = school;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getEducational() {
		return educational;
	}

	public void setEducational(int educational) {
		this.educational = educational;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getInternshipDays() {
		return internshipDays;
	}

	public void setInternshipDays(int internshipDays) {
		this.internshipDays = internshipDays;
	}

	public int getInternshipExp() {
		return internshipExp;
	}

	public void setInternshipExp(int internshipExp) {
		this.internshipExp = internshipExp;
	}

	public int getActivityExp() {
		return activityExp;
	}

	public void setActivityExp(int activityExp) {
		this.activityExp = activityExp;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public List<ComputerSkillEnum> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<ComputerSkillEnum> skillList) {
		this.skillList = skillList;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public List<EnglishSkillEnum> getEnglishSkillList() {
		return englishSkillList;
	}

	public void setEnglishSkillList(List<EnglishSkillEnum> englishSkillList) {
		this.englishSkillList = englishSkillList;
	}

	public String getOtherLanguage() {
		return otherLanguage;
	}

	public void setOtherLanguage(String otherLanguage) {
		this.otherLanguage = otherLanguage;
	}

	public List<OtherLanguageSkillEnum> getOtherLanguageSkillList() {
		return otherLanguageSkillList;
	}

	public void setOtherLanguageSkillList(List<OtherLanguageSkillEnum> otherLanguageSkillList) {
		this.otherLanguageSkillList = otherLanguageSkillList;
	}

	public String getSkillDesc() {
		return skillDesc;
	}

	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
	}

	public String getEnglishDesc() {
		return englishDesc;
	}

	public void setEnglishDesc(String englishDesc) {
		this.englishDesc = englishDesc;
	}

	public String getOtherLanguageDesc() {
		return otherLanguageDesc;
	}

	public void setOtherLanguageDesc(String otherLanguageDesc) {
		this.otherLanguageDesc = otherLanguageDesc;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	public int getPositionAvailable() {
		return positionAvailable;
	}

	public void setPositionAvailable(int positionAvailable) {
		this.positionAvailable = positionAvailable;
	}

	public int getInvitePost() {
		return invitePost;
	}

	public void setInvitePost(int invitePost) {
		this.invitePost = invitePost;
	}

	public String getOtherClaim() {
		return otherClaim;
	}

	public void setOtherClaim(String otherClaim) {
		this.otherClaim = otherClaim;
	}

	public List<MemberRecruitDTO> getMemberRecruitDTOs() {
		return memberRecruitDTOs;
	}

	public void setMemberRecruitDTOs(List<MemberRecruitDTO> memberRecruitDTOs) {
		this.memberRecruitDTOs = memberRecruitDTOs;
	}

	public String getValidityTimeDisplay() {
		validityTimeDisplay = sdf3.format(validityTime);
		return validityTimeDisplay;
	}

	public String getCreateTimeDisplay() {
		createTimeDisplay = sdf3.format(createTime);
		return createTimeDisplay;
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

	public String getRecruitTypeDesc() {
		return JobTypeEnum.getJobTypeDesc(recruitType);
	}

	public void setPositionTypeDesc(String positionTypeDesc) {
		this.positionTypeDesc = positionTypeDesc;
	}

	/**
	 * 职位类别
	 * 
	 * @return
	 */
	public String getPositionTypeDesc() {
		return positionTypeDesc;
	}

	public String getEmailTypeDesc() {
		return RecruitDemandEnum.getRecruitDemandDesc(emailType);
	}

	public String getPositionStatusDesc() {
		return PositionStatusEnum.getPositionStatusDesc(positionStatus);
	}

	public String getSchoolDesc() {
		return PositionSchoolTypeEnum.getPositionSchoolTypeDesc(school);
	}

	public String getMajorDesc() {
		return SpecialityEnum.getSpecialityDesc(major);
	}

	public String getEducationalDesc() {
		return PositionEducationEnum.getPositionEducationDesc(educational);
	}

	public String getScoreDesc() {
		return AchievementEnum.getAchievementDesc(score);
	}

	public String getIntershipDaysDesc() {
		return InternshipDaysEnum.getInternshipDaysDesc(internshipDays);
	}

	public String getIntershipExpDesc() {
		return InternshipExpEnum.getInternshipExpDesc(internshipExp);
	}

	public String getActivityExpDesc() {
		return SchoolActivityEnum.getSchoolActivityDesc(activityExp);
	}

	public int getResumeCount() {
		return resumeCount;
	}

	public void setResumeCount(int resumeCount) {
		this.resumeCount = resumeCount;
	}

	public String getSalaryDesc() {
		salaryDesc = String.format("%s~%s", minSalary, maxSalary);
		return salaryDesc;
	}

	@Override
	public String toString() {
		return "RecruitInfoDTO [id=" + id + ", memberId=" + memberId + ", companyId=" + companyId + ", recruitType="
				+ recruitType + ", recruitTypeDesc=" + getRecruitTypeDesc() + ", positionType=" + positionType
				+ ", positionTypeDesc=" + getPositionTypeDesc() + ", positionName=" + positionName
				+ ", departmentName=" + departmentName + ", postDuty=" + postDuty + ", workCity=" + workCityDTO
				+ ", workAddress=" + workAddress + ", minSalary=" + minSalary + ", maxSalary=" + maxSalary
				+ ", isNegotiable=" + negotiable + ", importantRemark=" + importantRemark + ", importantRemarkList="
				+ importantRemarkList + ", needNum=" + needNum + ", acceptEmail=" + acceptEmail + ", emailType="
				+ emailType + ", emailTypeDesc=" + getEmailTypeDesc() + ", validityTime=" + validityTime
				+ ", positionStatusDesc=" + getPositionStatusDesc() + ", school=" + school + ", schoolDesc="
				+ getSchoolDesc() + ", major=" + major + ", majorDesc=" + getMajorDesc() + ", educational="
				+ educational + ", educationalDesc=" + getEducationalDesc() + ", score=" + score + ", scoreDesc="
				+ getScoreDesc() + ", internshipDays=" + internshipDays + ", internshipDaysDesc="
				+ getIntershipDaysDesc() + ", intershipExp=" + internshipExp + ", intershipExpDesc="
				+ getIntershipExpDesc() + ", activityExp=" + activityExp + ", activityExpDesc=" + getActivityExpDesc()
				+ ", skill=" + skill + ", skillList=" + skillList + ", english=" + english + ", englishSkillList="
				+ englishSkillList + ", otherLanguage=" + otherLanguage + ", otherLanguageSkillList="
				+ otherLanguageSkillList + ", positionAvailable=" + positionAvailable + ", positionAvailableDesc="
				+ getActivityExpDesc() + ", invitePost=" + invitePost + ", invitePostDesc=" + getInvitePostDesc()
				+ ", otherClaim=" + otherClaim + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", positionStatus=" + positionStatus + ", resumeCount=" + resumeCount + ", status=" + status
				+ ", salaryDesc" + getSalaryDesc() + "]";
	}
}
