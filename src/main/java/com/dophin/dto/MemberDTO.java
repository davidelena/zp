package com.dophin.dto;

import java.util.Date;

import com.dophin.enums.GenderEnum;
import com.dophin.enums.RecruitDemandEnum;

/**
 * 汇源注册信息
 * 
 * @author dailiwei
 * 
 */
public class MemberDTO {
	/**
	 * 主键id
	 */
	private int id;
	/**
	 * 会员id（时间戳自动生成）
	 */
	private String memberId;
	/**
	 * 会员昵称
	 */
	private String name;

	/**
	 * 注册邮箱
	 */
	private String email;

	/**
	 * 常用邮箱
	 */
	private String commonEmail;

	/**
	 * 会员密码
	 */
	private String password;
	/**
	 * 会员手机
	 */
	private String mobile;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 企业注册用户职位
	 */
	private String position;

	/**
	 * 企业id
	 */
	private int companyId;

	/**
	 * 公司信息
	 */
	private CompanyInfoDTO companyInfoDTO;

	/**
	 * 学生信息id
	 */
	private int studentId;

	/**
	 * 学生信息
	 */
	private StudentInfoDTO studentInfoDTO;

	/**
	 * 需求
	 */
	private int demand;

	/**
	 * 需求说明
	 */
	private String demandDesc;

	/**
	 * 企业用户性别
	 */
	private int sex;

	/**
	 * 企业用户性别描述
	 */
	private String sexDesc;

	/**
	 * 会员类型（1-学生，2-企业）
	 */
	private int source;

	/**
	 * 会员学校ID（针对学生用户）
	 */
	private int schoolId;

	/**
	 * 会员学校描述（针对学生用户）
	 */
	private String schoolDesc;

	/**
	 * 会员学校，可以自己填写
	 */
	private String school;

	/**
	 * 会员学校
	 */
	private UniversityDTO universityDTO;

	/**
	 * 是否验证完email（1-验证，0-没有验证）
	 */
	private int isVerifyEmail;

	/**
	 * 是否验证完email（1-验证，0-没有验证）
	 */
	private int isVerifyCellphone;

	/**
	 * 地域代码
	 */
	private String areaCode;

	/**
	 * QQ
	 */
	private String qq;

	/**
	 * 微信
	 */
	private String wechat;

	/**
	 * 微博
	 */
	private String weibo;

	/**
	 * 扩展字段
	 */
	private String extension;

	/**
	 * 恭喜邮箱后缀（用来判定是否是第一个企业用户）
	 */
	private String companyEmailSuffix;

	/**
	 * 是否填写过基本信息
	 */
	private boolean hasFillBasic;

	/**
	 * 是否是第一个企业用户
	 */
	private boolean isFirst;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 记录状态（1-有效，0无效）
	 */
	private int status;

	public MemberDTO() {
		super();
		id = 0;
		memberId = String.valueOf(System.currentTimeMillis());
		name = "";
		email = "";
		commonEmail = "";
		password = "";
		mobile = "";
		telephone = "";
		position = "";
		companyId = 0;
		companyInfoDTO = new CompanyInfoDTO();
		studentId = 0;
		studentInfoDTO = new StudentInfoDTO();
		source = 0;
		schoolId = 0;
		school = "";
		universityDTO = new UniversityDTO();
		isVerifyEmail = 0;
		isVerifyCellphone = 0;
		areaCode = "";
		qq = "";
		wechat = "";
		weibo = "";
		demand = 0;
		demandDesc = "";
		sex = 0;
		sexDesc = "";
		extension = "";
		companyEmailSuffix = "";
		schoolDesc = "";
		hasFillBasic = false;
		isFirst = false;
		createTime = new Date();
		updateTime = new Date();
		status = 1;

	}

	public MemberDTO(int id, String memberId, String name, String email, String commonEmail, String password,
			String mobile, String telephone, String position, int campanyId, int studentId, int sex, int demand,
			int source, int schoolId, String school, int isVerifyEmail, int isVerifyCellphone, String areaCode,
			String qq, String wechat, String weibo, String extension, String companyEmailSuffix, boolean hasFillBasic,
			boolean isFirst, Date createTime, Date updateTime, int status) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.name = name;
		this.email = email;
		this.commonEmail = commonEmail;
		this.password = password;
		this.mobile = mobile;
		this.telephone = telephone;
		this.position = position;
		this.companyId = campanyId;
		this.studentId = studentId;
		this.sex = sex;
		this.demand = demand;
		this.source = source;
		this.schoolId = schoolId;
		this.school = school;
		this.isVerifyEmail = isVerifyEmail;
		this.isVerifyCellphone = isVerifyCellphone;
		this.areaCode = areaCode;
		this.qq = qq;
		this.wechat = wechat;
		this.weibo = weibo;
		this.extension = extension;
		this.companyEmailSuffix = companyEmailSuffix;
		this.hasFillBasic = hasFillBasic;
		this.isFirst = isFirst;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCommonEmail() {
		return commonEmail;
	}

	public void setCommonEmail(String commonEmail) {
		this.commonEmail = commonEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchoolDesc() {
		return schoolDesc;
	}

	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int campanyId) {
		this.companyId = campanyId;
	}

	public CompanyInfoDTO getCompanyInfoDTO() {
		return companyInfoDTO;
	}

	public void setCompanyInfoDTO(CompanyInfoDTO companyInfoDTO) {
		this.companyInfoDTO = companyInfoDTO;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public StudentInfoDTO getStudentInfoDTO() {
		return studentInfoDTO;
	}

	public void setStudentInfoDTO(StudentInfoDTO studentInfoDTO) {
		this.studentInfoDTO = studentInfoDTO;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchool() {
		return universityDTO.getName();
	}

	public UniversityDTO getUniversityDTO() {
		return universityDTO;
	}

	public void setUniversityDTO(UniversityDTO universityDTO) {
		this.universityDTO = universityDTO;
	}

	public int getIsVerifyEmail() {
		return isVerifyEmail;
	}

	public void setIsVerifyEmail(int isVerifyEmail) {
		this.isVerifyEmail = isVerifyEmail;
	}

	public int getIsVerifyCellphone() {
		return isVerifyCellphone;
	}

	public void setIsVerifyCellphone(int isVerifyCellphone) {
		this.isVerifyCellphone = isVerifyCellphone;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getDemand() {
		return demand;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}

	public String getDemandDesc() {
		return RecruitDemandEnum.getRecruitDemandDesc(demand);
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getSexDesc() {
		return GenderEnum.getGenderDesc(sex);
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getCompanyEmailSuffix() {
		return companyEmailSuffix;
	}

	public void setCompanyEmailSuffix(String companyEmailSuffix) {
		this.companyEmailSuffix = companyEmailSuffix;
	}

	public boolean isHasFillBasic() {
		return hasFillBasic;
	}

	public void setHasFillBasic(boolean hasFillBasic) {
		this.hasFillBasic = hasFillBasic;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
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
		return "MemberDTO [id=" + id + ", memberId=" + memberId + ", name=" + name + ", email=" + email
				+ ", commonEmail=" + commonEmail + ", password=" + password + ", mobile=" + mobile + ", telephone="
				+ telephone + ", position=" + position + ", campanyId=" + companyId + ", campany=" + companyInfoDTO
				+ ", studentId=" + studentId + ", student=" + studentInfoDTO + ", sex=" + sex + ", demand=" + demand
				+ ", demandDesc=" + getDemandDesc() + ", source=" + source + ", schoolId=" + schoolId + ", school="
				+ getSchool() + ", isVerifyEmail=" + isVerifyEmail + ", isVerifyCellphone=" + isVerifyCellphone
				+ ", areaCode=" + areaCode + ", qq=" + qq + ", wechat=" + wechat + ", weibo=" + weibo + ", extension="
				+ extension + ", hasFillBasic=" + hasFillBasic + ", companyEmailSuffix=" + companyEmailSuffix
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", status=" + status + "]";
	}

}
