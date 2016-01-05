package com.dophin.dto;

/**
 * 过滤我的联系人条件
 * 
 * @author David.dai
 * 
 */
public class FilterMyApplicantCondition {
	/**
	 * 发布职位的人的企业账户id
	 */
	private String memberId;

	/**
	 * 反馈状态
	 */
	private int feedStatus;

	/**
	 * 投递的职位id
	 */
	private int recruitId;

	/**
	 * 学校过滤
	 */
	private int school;

	/**
	 * 证书过滤
	 */
	private int diploma;

	/**
	 * 毕业年份
	 */
	private int graduationYear;

	/**
	 * 性别
	 */
	private int gender;

	/**
	 * 专业分类
	 */
	private int majorType;

	/**
	 * 对应简历的工作经历数量（有工作经历大于0，没有工作经历小于0）
	 */
	private int workExpCount;

	/**
	 * 起始行
	 */
	private int start;

	/**
	 * 总数
	 */
	private int size;

	/**
	 * 分页个数
	 */
	private int pn;

	public FilterMyApplicantCondition() {
		super();
		memberId = "";
		feedStatus = 0;
		recruitId = 0;
		school = 0;
		diploma = 0;
		graduationYear = 0;
		gender = 0;
		majorType = 0;
		workExpCount = -1;
		start = -1;
		size = -1;
		pn = 0;
	}

	public FilterMyApplicantCondition(String memberId, int feedStatus, int recruitId, int school, int diploma, int graduationYear, int gender, int majorType,
			int workExpCount, int start, int size) {
		super();
		this.memberId = memberId;
		this.feedStatus = feedStatus;
		this.recruitId = recruitId;
		this.school = school;
		this.diploma = diploma;
		this.graduationYear = graduationYear;
		this.gender = gender;
		this.majorType = majorType;
		this.start = start;
		this.size = size;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(int feedStatus) {
		this.feedStatus = feedStatus;
	}

	public int getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(int recruitId) {
		this.recruitId = recruitId;
	}

	public int getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(int graduationYear) {
		this.graduationYear = graduationYear;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getMajorType() {
		return majorType;
	}

	public void setMajorType(int majorType) {
		this.majorType = majorType;
	}

	public int getWorkExpCount() {
		return workExpCount;
	}

	public void setWorkExpCount(int workExpCount) {
		this.workExpCount = workExpCount;
	}

	public int getSchool() {
		return school;
	}

	public void setSchool(int school) {
		this.school = school;
	}

	public int getDiploma() {
		return diploma;
	}

	public void setDiploma(int diploma) {
		this.diploma = diploma;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPn() {
		return pn;
	}

	public void setPn(int pn) {
		this.pn = pn;
	}

	@Override
	public String toString() {
		return "FilterMyApplicantCondition [memberId=" + memberId + ", feedStatus=" + feedStatus + ", recruitId=" + recruitId + ", school=" + school
				+ ", diploma=" + diploma + ", graduationYear=" + graduationYear + ", gender=" + gender + ", majorType=" + majorType + ", workExpCount="
				+ workExpCount + ", start=" + start + ", size=" + size + ", pn=" + pn + "]";
	}

}
