package com.dophin.dto;

public class RecruitResumeCountDTO {
	private int recruitId;
	private int resumeCount;

	public RecruitResumeCountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecruitResumeCountDTO(int recruitId, int resumeCount) {
		super();
		this.recruitId = recruitId;
		this.resumeCount = resumeCount;
	}

	public int getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(int recruitId) {
		this.recruitId = recruitId;
	}

	public int getResumeCount() {
		return resumeCount;
	}

	public void setResumeCount(int resumeCount) {
		this.resumeCount = resumeCount;
	}

	@Override
	public String toString() {
		return "RecruitResumeCountDTO [recruitId=" + recruitId + ", resumeCount=" + resumeCount + "]";
	}

}
