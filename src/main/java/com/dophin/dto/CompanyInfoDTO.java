package com.dophin.dto;

import java.util.Date;

import com.dophin.enums.CompanySizeEnum;

/**
 * 企业信息
 * 
 * @author David.dai
 * 
 */
public class CompanyInfoDTO {
	private int id;
	private String memberId;
	private String name;
	private int industry;
	private String industryDesc;
	private int headerQuartersId;
	private String headerQuarters;
	private String detailAddress;

	/**
	 * logo路径
	 */
	private String logo;
	private String officialWebsite;
	private int scale;
	private String scaleDesc;
	private String synopsis;
	private String product;
	private String achievements;
	private String wechat;
	private String weibo;
	private String seniorExecutiveDesc;
	private Date createTime;
	private Date updateTime;
	private int status;

	public CompanyInfoDTO() {
		super();
		id = 0;
		memberId = "";
		name = "";
		industry = 0;
		industryDesc = "";
		headerQuartersId = 0;
		headerQuarters = "";
		detailAddress = "";
		logo = "";
		officialWebsite = "";
		scale = 0;
		scaleDesc = "";
		synopsis = "";
		product = "";
		achievements = "";
		wechat = "";
		weibo = "";
		seniorExecutiveDesc = "";
		createTime = new Date();
		updateTime = new Date();
		status = 1;
	}

	public CompanyInfoDTO(int id, String memberId, String name, int industry, String industryDesc, int headerQuartersId, String headerQuarters, String logo,
			String officialWebsite, int scale, String scaleDesc, String synopsis, String product, String achievements, String wechat, String weibo,
			String seniorExecutiveDesc) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.name = name;
		this.industry = industry;
		this.industryDesc = industryDesc;
		this.headerQuartersId = headerQuartersId;
		this.headerQuarters = headerQuarters;
		this.logo = logo;
		this.officialWebsite = officialWebsite;
		this.scale = scale;
		this.scaleDesc = scaleDesc;
		this.synopsis = synopsis;
		this.product = product;
		this.achievements = achievements;
		this.wechat = wechat;
		this.weibo = weibo;
		this.seniorExecutiveDesc = seniorExecutiveDesc;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndustry() {
		return industry;
	}

	public void setIndustry(int industry) {
		this.industry = industry;
	}

	public String getIndustryDesc() {
		return industryDesc;
	}

	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}

	public int getHeaderQuartersId() {
		return headerQuartersId;
	}

	public void setHeaderQuartersId(int headerQuartersId) {
		this.headerQuartersId = headerQuartersId;
	}

	public String getHeaderQuarters() {
		return headerQuarters;
	}

	public void setHeaderQuarters(String headerQuarters) {
		this.headerQuarters = headerQuarters;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAchievements() {
		return achievements;
	}

	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getSeniorExecutiveDesc() {
		return seniorExecutiveDesc;
	}

	public void setSeniorExecutiveDesc(String seniorExecutiveDesc) {
		this.seniorExecutiveDesc = seniorExecutiveDesc;
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

	public String getScaleDesc() {
		return CompanySizeEnum.getCompanySizeDesc(scale);
	}

	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", memeberId=" + memberId + ", name=" + name + ", industry=" + industry + ", industryDesc=" + getIndustryDesc()
				+ ", headerQuartersId=" + headerQuartersId + ", headerQuarters=" + headerQuarters + ", detailAddress=" + detailAddress + ", logo=" + logo
				+ ", officialWebsite=" + officialWebsite + ", scale=" + scale + ", scaleDesc=" + getScaleDesc() + ", synopsis=" + synopsis + ", product="
				+ product + ", achievements=" + achievements + ", wechat=" + wechat + ", weibo=" + weibo + ", seniorExecutiveDesc=" + seniorExecutiveDesc
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
