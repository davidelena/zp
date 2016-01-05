package com.dophin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dophin.dto.CertificateDTO;
import com.dophin.dto.CompanyInfoDTO;
import com.dophin.dto.ComputerSkillDTO;
import com.dophin.dto.EnLangSkillDTO;
import com.dophin.dto.FilterRecruitInfoCondition;
import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.OtherLangSkillDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.ResumeActivityExpDTO;
import com.dophin.dto.ResumeCustomDTO;
import com.dophin.dto.ResumeEducationExpDTO;
import com.dophin.dto.ResumeHobbySpecialDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.ResumeOpusInfoDTO;
import com.dophin.dto.ResumePrizeInfoDTO;
import com.dophin.dto.ResumeProjectExpDTO;
import com.dophin.dto.ResumeSocialNetDTO;
import com.dophin.dto.ResumeSpecialtySkillDTO;
import com.dophin.dto.ResumeWorkExpDTO;
import com.dophin.dto.SmsCodeDTO;
import com.dophin.dto.SmsLogDTO;
import com.dophin.dto.StudentInfoDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.enums.AchievementEnum;
import com.dophin.enums.ApplyProgressEnum;
import com.dophin.enums.ApplyTypeEnum;
import com.dophin.enums.ComputerSkillEnum;
import com.dophin.enums.EducationEnum;
import com.dophin.enums.EnglishSkillEnum;
import com.dophin.enums.FeedStatusEnum;
import com.dophin.enums.GenderEnum;
import com.dophin.enums.InternshipDaysEnum;
import com.dophin.enums.JobBenefitsEnum;
import com.dophin.enums.JobStatusEnum;
import com.dophin.enums.JobTypeEnum;
import com.dophin.enums.MemberSourceEnum;
import com.dophin.enums.OtherLanguageSkillEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.RecommendFrequencyEnum;
import com.dophin.enums.RewardLevelEnum;
import com.dophin.enums.SalaryLevelEnum;
import com.dophin.enums.SkillLevelEnum;
import com.dophin.enums.SmsTypeEnum;
import com.dophin.enums.SocialNetworkEnum;
import com.dophin.enums.SpecialityEnum;
import com.dophin.enums.YesNoEnum;
import com.dophin.service.MemberRecruitService;
import com.dophin.service.MemberService;
import com.dophin.service.ResumeActivityExpService;
import com.dophin.service.ResumeCustomService;
import com.dophin.service.ResumeEducationExpService;
import com.dophin.service.ResumeHobbySpecialService;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.ResumeOpusInfoService;
import com.dophin.service.ResumePrizeInfoService;
import com.dophin.service.ResumeProjectExpService;
import com.dophin.service.ResumeSocialNetService;
import com.dophin.service.ResumeSpecialtySkillService;
import com.dophin.service.ResumeWorkExpService;
import com.dophin.service.SmsService;
import com.dophin.service.StudentMemberService;
import com.dophin.service.biz.SearchRecuritInfoService;
import com.dophin.service.impl.SearchRecruitInfoServiceFulltimeImpl;
import com.dophin.service.impl.SearchRecruitInfoServiceInternshipImpl;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.QiniuUtils;
import com.dophin.utils.VelocityUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

/**
 * 简历类
 * 
 * @author dailiwei
 * 
 */
@Controller
@RequestMapping(value = "/resume")
public class ResumeController extends BaseController {

	private static final String ISO8859_1 = "ISO8859-1";

	private static final String GB2312 = "gb2312";

	private static Logger logger = Logger.getLogger(ResumeController.class);

	private static final long MAX_SIZE = 5242880; // 1024*1024*5

	private static final String DOCX_SUFFIX = ".doc";

	private static final String TEMPLATE = "template";

	@Autowired
	private MemberService memberService;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Autowired
	private StudentMemberService studentMemberService;

	@Autowired
	private ResumeEducationExpService resumeEducationExpService;

	@Autowired
	private ResumeWorkExpService resumeWorkExpService;

	@Autowired
	private ResumeActivityExpService resumeActivityExpService;

	@Autowired
	private ResumeProjectExpService resumeProjectExpService;

	@Autowired
	private ResumeHobbySpecialService resumeHobbySpecialService;

	@Autowired
	private ResumeCustomService resumeCustomService;

	@Autowired
	private ResumePrizeInfoService resumePrizeInfoService;

	@Autowired
	private ResumeOpusInfoService resumeOpusInfoService;

	@Autowired
	private ResumeSocialNetService resumeSocialNetService;

	@Autowired
	private ResumeSpecialtySkillService resumeSpecialtySkillService;

	@Autowired
	private MemberRecruitService memberRecruitService;

	@Autowired
	private SmsService smsService;

	private static final String EMAIL_TYPE = "email";
	private static final String MOBILE_TYPE = "mobile";

	private static SearchRecuritInfoService fulltimeSearchService = new SearchRecruitInfoServiceFulltimeImpl();

	private static SearchRecuritInfoService internshipSearchService = new SearchRecruitInfoServiceInternshipImpl();

	private static final int PAGE_SIZE = 5;

	private static VelocityContext velocityContext = new VelocityContext();

	/**
	 * 工作和实习机会（招全职工作）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/search_joblist", method = RequestMethod.GET)
	public ModelAndView searchJoblist(String id) {
		logger.info("execute search_joblist view..., id: " + id);

		List<ApplyProgressEnum> applyProgressEnums = Arrays.asList(ApplyProgressEnum.values());
		Map<String, List<IndustryDTO>> industryMap = getIndustryMap();
		List<GeoAreaDTO> hotCities = getHotCities();
		List<GeoAreaDTO> otherCities = getOtherCities();
		Map<String, List<GeoAreaDTO>> cityMap = getGeoAreaMap();
		List<SpecialityEnum> specialityEnums = Arrays.asList(SpecialityEnum.values());
		List<PositionEducationEnum> positionEducationEnums = Arrays.asList(PositionEducationEnum.values());
		List<SalaryLevelEnum> salaryLevelEnums = Arrays.asList(SalaryLevelEnum.values());

		ModelAndView mav = new ModelAndView("search_joblist");
		mav.addObject("memberId", id);
		mav.addObject("applyProgressEnums", applyProgressEnums);
		mav.addObject("industryMap", industryMap);
		mav.addObject("cityMap", cityMap);
		mav.addObject("hotCities", hotCities);
		mav.addObject("otherCities", otherCities);
		mav.addObject("specialityEnums", specialityEnums);
		mav.addObject("positionEducationEnums", positionEducationEnums);
		mav.addObject("salaryLevelEnums", salaryLevelEnums);

		return mav;
	}

	/**
	 * 工作和实习机会（招兼职工作）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/search_intern_joblist", method = RequestMethod.GET)
	public ModelAndView searchInternJoblist(String id) {
		logger.info("execute search_intern_joblist view..., id: " + id);

		List<InternshipDaysEnum> internshipDaysEnums = Arrays.asList(InternshipDaysEnum.values());
		Map<String, List<IndustryDTO>> industryMap = getIndustryMap();
		List<GeoAreaDTO> hotCities = getHotCities();
		List<GeoAreaDTO> otherCities = getOtherCities();
		Map<String, List<GeoAreaDTO>> cityMap = getGeoAreaMap();
		List<PositionEducationEnum> positionEducationEnums = Arrays.asList(PositionEducationEnum.values());

		ModelAndView mav = new ModelAndView("search_intern_joblist");
		mav.addObject("memberId", id);
		mav.addObject("internshipDaysEnums", internshipDaysEnums);
		mav.addObject("industryMap", industryMap);
		mav.addObject("cityMap", cityMap);
		mav.addObject("hotCities", hotCities);
		mav.addObject("otherCities", otherCities);
		mav.addObject("positionEducationEnums", positionEducationEnums);

		return mav;
	}

	/**
	 * 我的申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/my_apply", method = RequestMethod.GET)
	public ModelAndView myApply(String id, String type, Integer pn) {
		logger.info(String.format("execute my_apply view..., id: %s, type: %s", id, type));
		id = id == null ? "" : id;
		type = type == null ? "all" : type;
		pn = pn == null ? 1 : pn;

		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		if (memberDTO == null) {
			memberDTO = new MemberDTO();
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", id);
		queryMap.put("applyType", ApplyTypeEnum.Apply.getCode());

		if (!StringUtils.isBlank(type) && !type.equalsIgnoreCase("all")) {
			type = type.equalsIgnoreCase("unnotify") ? String.format("%s,%s", FeedStatusEnum.NonViewed.getCode(),
					FeedStatusEnum.Undetermined.getCode()) : type;
			queryMap.put("feedStatusStr", type);
		}

		int total = memberRecruitService.queryMemberRecruitsForMyApplyCount(queryMap);

		queryMap.put("start", (pn - 1) * 5);
		queryMap.put("size", 5);
		List<MemberRecruitDTO> memberRecruitDTOs = memberRecruitService.queryMemberRecruitsForMyApply(queryMap);
		logger.info("total size: " + total + ", query size: " + memberRecruitDTOs.size());

		String html = CommonUtils.generatePnHtml(total, pn,
				String.format("resume/my_apply?id=%s&type=%s", id, type.equalsIgnoreCase("1,2") ? "unnotify" : type));

		ModelAndView mav = new ModelAndView("my_apply");
		mav.addObject("memberDTO", memberDTO);
		mav.addObject("memberRecruitDTOs", memberRecruitDTOs);
		mav.addObject("paginateHtml", html);
		type = type.equalsIgnoreCase("1,2") ? "unnotify" : type;
		mav.addObject("type", type);

		return mav;
	}

	/**
	 * 账号信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/account_settings", method = RequestMethod.GET)
	public ModelAndView accountSettings(String id) {
		logger.info("execute account_settings view..., id: " + id);
		ModelAndView mav = new ModelAndView("account_settings");
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		if (memberDTO == null) {
			memberDTO = new MemberDTO();
		}

		boolean hasCheckMobile = memberDTO.getIsVerifyCellphone() == 1;
		mav.addObject("memberDTO", memberDTO);
		mav.addObject("pageType", 1);
		mav.addObject("hasCheckMobile", hasCheckMobile);
		return mav;
	}

	/**
	 * 通过邮箱或手机验证账户的密码
	 * 
	 * @param account
	 *            手机或者邮箱账户
	 * @param password
	 *            登录密码
	 * @param checktype
	 *            验证类型（mobile or email）
	 * @return 是否验证通过
	 */
	@ResponseBody
	@RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
	public boolean checkPassword(String account, String password, String checktype) {
		account = StringUtils.isBlank(account) ? "" : account;
		password = StringUtils.isBlank(password) ? "" : password;
		checktype = StringUtils.isBlank(checktype) ? EMAIL_TYPE : checktype;
		logger.info("execute checkPassword, account: " + account + ", password: " + password);
		boolean result = false;

		MemberDTO memberDTO = null;
		if (checktype.equalsIgnoreCase(MOBILE_TYPE)) {
			memberDTO = memberService.queryMemberInfoByMobile(account, MemberSourceEnum.Student.getCode());
		} else {
			memberDTO = memberService.queryMemberInfoByEmail(account, MemberSourceEnum.Student.getCode());
		}

		if (memberDTO == null) {
			return false;
		}

		String digestPassword = DigestUtils.md5Hex(password).trim();
		result = memberDTO.getPassword().trim().equals(digestPassword);

		logger.info("result: " + result);
		return result;
	}

	/**
	 * 检验当前新的邮箱账户是否已经被注册了
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/accountExists", method = RequestMethod.POST)
	public boolean accountExists(String account) {
		logger.info("execute accountExists..." + account);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("email", account);
		queryMap.put("source", MemberSourceEnum.Student.getCode());
		boolean result = memberService.isMemberExists(queryMap);
		logger.info("result: " + result);
		return !result;
	}

	/**
	 * 检验验证码是否正确
	 * 
	 * @param account
	 * @param verfiyCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkVerfiyCode", method = RequestMethod.POST)
	public boolean checkVerfiyCode(String account, String verfiyCode) {
		logger.info(String.format("execute verfiycode action, account: %s, verfiyCode: %s", account, verfiyCode));
		SmsCodeDTO smsCodeDTO = smsService.querySmsCode(account);
		if (smsCodeDTO == null) {
			return false;
		} else {
			return smsCodeDTO.getCode().equalsIgnoreCase(verfiyCode.trim());
		}
	}

	/**
	 * 获取邮件验证码
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getVerfiyCode", method = RequestMethod.POST)
	public Map<String, Object> getVerfiyCode(String account) {
		logger.info("execute getVerfiyCode action...");
		Map<String, Object> resultMap = new HashMap<>();

		if (StringUtils.isBlank(account)) {
			resultMap.put("success", false);
			resultMap.put("message", "新邮箱不能为空");
			return resultMap;
		}

		try {
			String code = CommonUtils.generateRandomSix();
			StrBuilder sb = new StrBuilder();
			sb.appendln("亲爱的用户，您好：");
			sb.appendln(String.format("您的重置新邮箱验证码：【%s】", code));
			String contents = sb.toString();
			sendMail(account, "重置登录邮箱", "重置登录邮箱", contents);

			SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
			smsCodeDTO.setCode(code);
			smsCodeDTO.setMobile(account);

			// 先删后新增
			int count = smsService.deleteSmsCode(account);
			logger.info("delete " + count + " record for account： " + account);

			int count2 = smsService.insertSmsCode(smsCodeDTO);
			logger.info("add " + count2 + " record for account： " + account + ", newId: " + smsCodeDTO.getId());

			resultMap.put("success", true);
			resultMap.put("message", "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 更新或绑定登录邮箱
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveLoginInfoFormData", method = RequestMethod.POST)
	public Map<String, Object> saveLoginInfoFormData(String memberId, String loginMail, String newLoginMail) {
		String info = String.format(
				"execute saveLoginInfoFormData action, memberId: %s, loginMail: %s, newLoginMail: %s", memberId,
				loginMail, newLoginMail);
		logger.info(info);
		Map<String, Object> resultMap = new HashMap<>();

		memberId = StringUtils.isBlank(memberId) ? "" : memberId;
		loginMail = StringUtils.isBlank(loginMail) ? "" : loginMail;
		newLoginMail = StringUtils.isBlank(newLoginMail) ? "" : newLoginMail;

		try {
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			int count = 0;
			if (memberDTO != null) {
				memberDTO.setEmail(newLoginMail);
				count = memberService.updateMemberInfo(memberDTO);
				logger.info("update " + count + " login mail for account: " + loginMail + ", newlogin mail: "
						+ newLoginMail);
			}

			resultMap.put("success", true);
			resultMap.put("message", "");
			resultMap.put("newEmail", count > 0 ? newLoginMail : loginMail);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
			resultMap.put("newEmail", loginMail);
		}

		return resultMap;
	}

	/**
	 * 检验当前新的手机是否已经被注册了
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkMobileExists", method = RequestMethod.POST)
	public boolean checkMobileExists(String account) {
		logger.info("execute checkMobileExists..." + account);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("mobile", account);
		queryMap.put("source", MemberSourceEnum.Student.getCode());
		boolean result = memberService.isMemberExists(queryMap);
		logger.info("result: " + result);
		return !result;
	}

	/**
	 * 获取手机绑定验证码
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMobileVerfiyCode", method = RequestMethod.POST)
	public Map<String, Object> getMobileVerfiyCode(String account) {
		logger.info("execute getMobileVerfiyCode action...");
		Map<String, Object> resultMap = new HashMap<>();

		if (StringUtils.isBlank(account)) {
			resultMap.put("success", false);
			resultMap.put("message", "绑定手机号不能为空");
			return resultMap;
		}

		try {
			String code = CommonUtils.generateRandomSix();

			// 发送验证码短信
			sendSmsCode(account, code, resultMap, SmsTypeEnum.BindingMobile);

			SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
			smsCodeDTO.setCode(code);
			smsCodeDTO.setMobile(account);

			// 先删后新增
			int count = smsService.deleteSmsCode(account);
			logger.info("delete " + count + " record for account： " + account);

			int count2 = smsService.insertSmsCode(smsCodeDTO);
			logger.info("add " + count2 + " record for account： " + account + ", newId: " + smsCodeDTO.getId());

			/*
			 * 插入smslog记录
			 */
			String content = String.format("【欢迎使用第一站】您正在使用第一站，请输入绑定手机短信验证码：%s", code);
			SmsLogDTO smsLogDTO = new SmsLogDTO();
			smsLogDTO.setMobile(account);
			smsLogDTO.setContent(content);
			smsLogDTO.setSmsType(SmsTypeEnum.BindingMobile.getCode());
			smsLogDTO.setStatus(1);

			smsService.insertSmsLog(smsLogDTO);
			logger.info("add 1 smslog record, " + smsLogDTO);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 检验验证码是否正确
	 * 
	 * @param account
	 * @param verfiyCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkMobileVerfiyCode", method = RequestMethod.POST)
	public boolean checkMobileVerfiyCode(String account, String verfiyCode) {
		logger.info(String.format("execute verfiycode action, account: %s, verfiyCode: %s", account, verfiyCode));
		SmsCodeDTO smsCodeDTO = smsService.querySmsCode(account);
		if (smsCodeDTO == null) {
			return false;
		} else {
			return smsCodeDTO.getCode().equalsIgnoreCase(verfiyCode.trim());
		}
	}

	/**
	 * 更新手机绑定验证信息
	 * 
	 * @param memberId
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveMobileInfoFormData", method = RequestMethod.POST)
	public Map<String, Object> saveMobileInfoFormData(String memberId, String mobile, String mobileType) {
		String info = String
				.format("execute saveMobileInfoFormData action, memberId: %s, mobile: %s", memberId, mobile);
		logger.info(info);
		Map<String, Object> resultMap = new HashMap<>();

		memberId = StringUtils.isBlank(memberId) ? "" : memberId;
		mobile = StringUtils.isBlank(mobile) ? "" : mobile;
		mobileType = StringUtils.isBlank(mobileType) ? "" : mobileType;

		try {
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			int count = 0;
			if (memberDTO != null) {
				memberDTO.setMobile(mobile);
				if (mobileType.equalsIgnoreCase("bindings")) {
					memberDTO.setIsVerifyCellphone(1);
				}
				count = memberService.updateMemberInfo(memberDTO);
				logger.info("update " + count + " binding mobile for account: " + mobile);
			}

			resultMap.put("data", mobile);
			resultMap.put("success", true);
			resultMap.put("message", "");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("data", "");
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}

		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = "/savePswdFormData", method = RequestMethod.POST)
	public Map<String, Object> savePswdFormData(String memberId, String newPassword) {
		logger.info("execute savePswdFormData action...");
		memberId = StringUtils.isBlank(memberId) ? "" : memberId;
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			if (memberDTO != null) {
				memberDTO.setPassword(DigestUtils.md5Hex(newPassword));
				int count = memberService.updateMemberInfo(memberDTO);
				logger.info("update " + count + " record for memberId: " + memberId);
			}

			resultMap.put("success", true);
			resultMap.put("message", "");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 通过邮箱账户的密码验证
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkPasswordWithMemberId", method = RequestMethod.POST)
	public boolean checkPasswordWithMemberId(String account, String password) {
		account = StringUtils.isBlank(account) ? "" : account;
		password = StringUtils.isBlank(password) ? "" : password;
		logger.info("execute checkPassword, account: " + account + ", password: " + password);
		boolean result = false;

		MemberDTO memberDTO = memberService.queryMemberInfo(account);
		if (memberDTO != null) {
			String digestPassword = DigestUtils.md5Hex(password).trim();
			result = memberDTO.getPassword().trim().equals(digestPassword);
		}

		logger.info("result: " + result);
		return result;
	}

	/**
	 * 账号绑定
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/account_bindings", method = RequestMethod.GET)
	public ModelAndView accountBingding(String id) {
		logger.info("execute account_binding view..., id: " + id);
		ModelAndView mav = new ModelAndView("account_bindings");
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		if (memberDTO == null) {
			memberDTO = new MemberDTO();
		}

		String account = StringUtils.isBlank(memberDTO.getEmail()) ? memberDTO.getMobile() : memberDTO.getEmail();

		mav.addObject("memberDTO", memberDTO);
		mav.addObject("account", account);
		mav.addObject("pageType", 2);

		return mav;
	}

	/**
	 * 我的简历列表页面
	 * 
	 * @param id
	 * @return 我的简历列表页面
	 */
	@RequestMapping(value = "/my_resume", method = RequestMethod.GET)
	public ModelAndView myResume(String id) {
		logger.info("execute my_resume view...");
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		logger.info("model: " + memberDTO);

		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos(id);

		StudentInfoDTO studentInfoDTO = studentMemberService.queryStudentInfo(id);
		List<JobStatusEnum> jobStatusEnums = Arrays.asList(JobStatusEnum.values());
		List<YesNoEnum> yesNoEnums = Arrays.asList(YesNoEnum.values());
		List<RecommendFrequencyEnum> recommendFrequencyEnums = Arrays.asList(RecommendFrequencyEnum.values());
		List<SalaryLevelEnum> salaryLevelEnums = Arrays.asList(SalaryLevelEnum.values());

		ModelAndView mav = new ModelAndView("my_resume");
		if (memberDTO == null) {
			memberDTO = new MemberDTO();
		}

		String demandIndustryDesc = "";
		String demandCityDesc = "";

		if (studentInfoDTO == null) {
			studentInfoDTO = new StudentInfoDTO();
		} else {
			for (IndustryDTO industryDTO : studentInfoDTO.getDemandIndustries()) {
				demandIndustryDesc += industryDTO.getName() + ",";
			}
			for (GeoAreaDTO geoAreaDTO : studentInfoDTO.getDemandCitys()) {
				demandCityDesc += geoAreaDTO.getName() + ",";
			}
		}

		demandIndustryDesc = StringUtils.substringBeforeLast(demandIndustryDesc, ",");
		demandCityDesc = StringUtils.substringBeforeLast(demandCityDesc, ",");

		List<GeoAreaDTO> hotCities = getHotCities();
		List<GeoAreaDTO> otherCities = getOtherCities();
		Map<String, List<IndustryDTO>> industryMap = getIndustryMap();
		Map<String, List<GeoAreaDTO>> cityMap = getGeoAreaMap();

		mav.addObject("memberDTO", memberDTO);
		mav.addObject("studentInfoDTO", studentInfoDTO);
		mav.addObject("jobStatusEnums", jobStatusEnums);
		mav.addObject("yesNoEnums", yesNoEnums);
		mav.addObject("recommendFrequencyEnums", recommendFrequencyEnums);
		mav.addObject("salaryLevelEnums", salaryLevelEnums);
		mav.addObject("demandIndustryDesc", demandIndustryDesc);
		mav.addObject("demandCityDesc", demandCityDesc);
		mav.addObject("resumelist", resumeInfoDTOs);
		mav.addObject("industryMap", industryMap);
		mav.addObject("cityMap", cityMap);
		mav.addObject("hotCities", hotCities);
		mav.addObject("otherCities", otherCities);

		return mav;
	}

	/**
	 * 删除简历
	 * 
	 * @param resumeId
	 *            简历Id
	 * @return 操作结果
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeInfo", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeInfo(int resumeId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = resumeInfoService.deleteResumeInfo(resumeId);
			logger.info("delete " + count + " resumeinfo for id: " + resumeId);
			resultMap.put("success", true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存学生求职意向
	 * 
	 * @param memberId
	 *            学生账户ID
	 * @param studentId
	 *            学生求职意向表ID
	 * @param hdStuDemandIndustry
	 *            期望行业
	 * @param stuDemandPostion
	 *            期望职位
	 * @param stuDemandType
	 *            期望职位类型
	 * @param hdStuDemandCity
	 * @param stuDemandSalary
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDemandInfo", method = RequestMethod.POST)
	public Map<String, Object> saveDemandInfo(String memberId, Integer studentId, String hdStuDemandIndustry,
			String stuDemandPostion, Integer stuDemandType, String hdStuDemandCity, Integer stuDemandSalary) {
		String info = String
				.format("memberId: %s, studentId: %d, demandIndustry: %s, demandPosition: %s, jobType: %d, demandCity: %s, demandSalary: %s",
						memberId, studentId, hdStuDemandIndustry, stuDemandPostion, stuDemandType, hdStuDemandCity,
						stuDemandSalary);

		stuDemandType = stuDemandType == null ? JobTypeEnum.FullTime.getCode() : stuDemandType;

		logger.info(info);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			StudentInfoDTO studentInfo = null;
			int queryStudentId = studentId;

			if (studentId > 0) {
				studentInfo = studentMemberService.queryStudentInfo(studentId);
				studentInfo.setDemandIndustry(hdStuDemandIndustry);
				studentInfo.setDemandPosition(stuDemandPostion);
				studentInfo.setDemandType(stuDemandType);
				studentInfo.setDemandCity(hdStuDemandCity);
				studentInfo.setDemandSalary(stuDemandSalary);

				int count = studentMemberService.updateStudentInfo(studentInfo);
				logger.info(String.format("update %d studentinfo for id: %d", count, studentId));
			} else {
				studentInfo = new StudentInfoDTO();
				studentInfo.setMemberId(memberId);
				studentInfo.setDemandIndustry(hdStuDemandIndustry);
				studentInfo.setDemandPosition(stuDemandPostion);
				studentInfo.setDemandType(stuDemandType);
				studentInfo.setDemandCity(hdStuDemandCity);
				studentInfo.setDemandSalary(stuDemandSalary);

				int sCount = studentMemberService.insertStudentInfo(studentInfo);
				logger.info(String.format("insert %d studentinfo for id: %d", sCount, studentInfo.getId()));

				queryStudentId = studentInfo.getId();
			}

			// 更新会员信息级联
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			memberDTO.setStudentId(studentInfo.getId());
			int mCount = memberService.updateMemberInfo(memberDTO);
			logger.info(String.format("update %d studentid in memberinfo", mCount));

			resultMap.put("success", true);
			resultMap.put("memberId", memberId);

			StudentInfoDTO newStudent = studentMemberService.queryStudentInfo(queryStudentId);
			String demandIndustryDesc = "";
			String demandCityDesc = "";
			if (newStudent != null) {
				for (IndustryDTO industryDTO : newStudent.getDemandIndustries()) {
					demandIndustryDesc += industryDTO.getName() + ",";
				}
				for (GeoAreaDTO geoAreaDTO : newStudent.getDemandCitys()) {
					demandCityDesc += geoAreaDTO.getName() + ",";
				}
			}

			demandIndustryDesc = StringUtils.substringBeforeLast(demandIndustryDesc, ",");
			demandCityDesc = StringUtils.substringBeforeLast(demandCityDesc, ",");

			resultMap.put("student", newStudent);
			resultMap.put("demandIndustryDesc", demandIndustryDesc);
			resultMap.put("demandCityDesc", demandCityDesc);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
			resultMap.put("student", new StudentInfoDTO());
			resultMap.put("demandIndustryDesc", "");
			resultMap.put("demandCityDesc", "");
		}

		return resultMap;
	}

	/**
	 * 学生系统设置
	 * 
	 * @param memberId
	 *            学生账户ID
	 * @param studentId
	 *            学生表主键ID
	 * @param stuJobStatus
	 *            求职状态
	 * @param stuIsAllowRecommend
	 *            是否接受职位推荐
	 * @param stuIsAllowEmail
	 *            是否接受邮箱推荐
	 * @param stuRecommendEmail
	 *            推荐邮箱
	 * @param stuRecommendFrequency
	 *            推荐频率
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSysInfo", method = RequestMethod.POST)
	public Map<String, Object> saveSysInfo(String memberId, Integer studentId, Integer stuJobStatus,
			Integer stuIsAllowRecommend, Integer stuIsAllowEmail, String stuRecommendEmail,
			Integer stuRecommendFrequency) {
		String info = String
				.format("memberId: %s, studentId: %d, stuJobStatus: %s, stuIsAllowRecommend: %s, stuIsAllowEmail: %d, stuRecommendEmail: %s, stuRecommendFrequency: %s",
						memberId, studentId, stuJobStatus, stuIsAllowRecommend, stuIsAllowEmail, stuRecommendEmail,
						stuRecommendFrequency);
		stuIsAllowRecommend = stuIsAllowRecommend == null ? 0 : stuIsAllowRecommend;
		stuIsAllowEmail = stuIsAllowEmail == null ? 0 : stuIsAllowEmail;
		stuRecommendFrequency = stuRecommendFrequency == null ? 0 : stuRecommendFrequency;
		logger.info(info);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			StudentInfoDTO studentInfo = null;
			int queryStudentId = studentId;
			if (studentId > 0) {
				studentInfo = studentMemberService.queryStudentInfo(studentId);
				studentInfo.setJobStatus(stuJobStatus);
				studentInfo.setAllowRecommend(stuIsAllowRecommend == 1);
				studentInfo.setAllowEmail(stuIsAllowEmail == 1);
				studentInfo.setRecommendEmail(stuRecommendEmail);
				studentInfo.setRecommendFrequency(stuRecommendFrequency);

				int count = studentMemberService.updateStudentInfo(studentInfo);
				logger.info(String.format("update %d studentinfo for id: %d", count, studentId));
			} else {
				studentInfo = new StudentInfoDTO();
				studentInfo.setMemberId(memberId);
				studentInfo.setJobStatus(stuJobStatus);
				studentInfo.setAllowRecommend(stuIsAllowRecommend == 1);
				studentInfo.setAllowEmail(stuIsAllowEmail == 1);
				studentInfo.setRecommendEmail(stuRecommendEmail);
				studentInfo.setRecommendFrequency(stuRecommendFrequency);

				int sCount = studentMemberService.insertStudentInfo(studentInfo);
				logger.info(String.format("insert %d studentinfo for id: %d", sCount, studentInfo.getId()));

				MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
				memberDTO.setStudentId(studentInfo.getId());
				int mCount = memberService.updateMemberInfo(memberDTO);
				logger.info(String.format("update %d studentid in memberinfo", mCount));

				queryStudentId = studentInfo.getId();
			}

			resultMap.put("success", true);
			resultMap.put("memberId", memberId);
			StudentInfoDTO newStudent = studentMemberService.queryStudentInfo(queryStudentId);
			resultMap.put("student", newStudent);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
			resultMap.put("studentInfo", new StudentInfoDTO());
		}

		return resultMap;
	}

	/**
	 * 填写学生账户基本信息
	 * 
	 * @param memberId
	 *            学生账户ID
	 * @param memberName
	 *            学生账户名称
	 * @param memberSchoolId
	 *            所属学校ID
	 * @param memberCommonEmail
	 *            常用联系邮箱
	 * @param memberMobile
	 *            常用联系方式（手机）
	 * @return 相关转跳页面
	 */
	@ResponseBody
	@RequestMapping(value = "/fillBasicInfo", method = RequestMethod.POST)
	public Map<String, Object> fillBasicInfo(String memberId, String memberName, int memberSchoolId,
			String memberCommonEmail, String memberMobile) {
		logger.info(String.format(
				"memberId： %s, memberName: %s, memberSchoolId: %d, memberCommonEmail: %s, memberMobile: %s", memberId,
				memberName, memberSchoolId, memberCommonEmail, memberMobile));

		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		memberDTO.setName(memberName);
		memberDTO.setSchoolId(memberSchoolId);
		memberDTO.setCommonEmail(memberCommonEmail);
		memberDTO.setMobile(memberMobile);
		memberDTO.setHasFillBasic(true);

		int count = memberService.updateMemberInfo(memberDTO);
		logger.info("execute fillBasicInfo action..., affect count: " + count);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		resultMap.put("return_url", String.format("resume/my_resume?id=%s", memberId));

		return resultMap;
	}

	/**
	 * 学生填写基本信息
	 * 
	 * @param id
	 *            学生账户ID
	 * @return 填充基本信息
	 */
	@RequestMapping(value = "/student_info", method = RequestMethod.GET)
	public ModelAndView studentInfo(String id) {
		logger.info("execute student_info view...");
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		if (memberDTO == null) {
			memberDTO = new MemberDTO();
		}
		ModelAndView mav = new ModelAndView("student_info");
		mav.addObject("model", memberDTO);
		mav.addObject("geoAreaList", getUniversityGeoList());

		return mav;
	}

	/**
	 * 验证手机合法性
	 * 
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isMobileValid", method = RequestMethod.POST)
	public boolean isMobileValid(String mobile) {
		boolean result = CommonUtils.checkMobile(mobile);
		logger.info("result: " + result);
		return result;
	}

	/**
	 * 大学列表加载
	 * 
	 * @param proviceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadUniversity", method = RequestMethod.POST)
	public Map<String, Object> loadUniversity(Integer proviceId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("geoId", proviceId);
		List<UniversityDTO> ls = constantsService.queryUniversities(queryMap);

		resultMap.put("success", 1);
		resultMap.put("list", ls);
		return resultMap;
	}

	/**
	 * 简历预览
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/resume_info_view", method = RequestMethod.GET)
	public ModelAndView resumeInfoView(String id, Integer rsid) {
		id = StringUtils.isBlank(id) ? "" : id;
		rsid = rsid == null ? 0 : rsid;
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(rsid);

		memberDTO = memberDTO == null ? new MemberDTO() : memberDTO;
		resumeInfoDTO = resumeInfoDTO == null ? new ResumeInfoDTO() : resumeInfoDTO;

		ModelAndView mav = new ModelAndView("resume_info_view");
		mav.addObject("memberInfo", memberDTO);
		mav.addObject("resumeInfo", resumeInfoDTO);

		return mav;
	}

	/**
	 * 创建中文简历
	 * 
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createChineseResume", method = RequestMethod.POST)
	public Map<String, Object> createChineseResume(String memberId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResumeInfoDTO resumeInfoDTO = new ResumeInfoDTO();
		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		resumeInfoDTO.setResumeName(String.format("%s的简历", memberDTO.getName()));
		resumeInfoDTO.setMemberId(memberId);
		int count = resumeInfoService.insertResumeInfo(resumeInfoDTO);
		logger.info("insert " + count + " resume for memberId: " + memberId);

		resultMap.put("success", true);
		resultMap.put("memberId", memberId);
		resultMap.put("resumeId", resumeInfoDTO.getId());

		return resultMap;
	}

	/**
	 * 学生简历详情
	 * 
	 * @param id
	 * @param rsid
	 * @return
	 */
	@RequestMapping(value = "/online_resume", method = RequestMethod.GET)
	public ModelAndView resumeInfo(String id, Integer rsid) {
		logger.info("execute resume_info view...");

		rsid = rsid == null ? 0 : rsid;
		id = id == null ? "" : id;
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		memberDTO = memberDTO == null ? new MemberDTO() : memberDTO;
		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(rsid);
		resumeInfoDTO = resumeInfoDTO == null ? new ResumeInfoDTO() : resumeInfoDTO;
		List<GenderEnum> genderEnums = Arrays.asList(GenderEnum.values());
		List<EducationEnum> educationEnums = Arrays.asList(EducationEnum.values());
		List<SpecialityEnum> specialityEnums = Arrays.asList(SpecialityEnum.values());
		List<AchievementEnum> achievementEnums = Arrays.asList(AchievementEnum.values());
		List<RewardLevelEnum> rewardLevelEnums = Arrays.asList(RewardLevelEnum.values());
		List<SocialNetworkEnum> socialNetworkEnums = Arrays.asList(SocialNetworkEnum.values());
		List<EnglishSkillEnum> englishSkillEnums = Arrays.asList(EnglishSkillEnum.values());
		List<OtherLanguageSkillEnum> otherLanguageSkillEnums = Arrays.asList(OtherLanguageSkillEnum.values());
		List<SkillLevelEnum> skillLevelEnums = Arrays.asList(SkillLevelEnum.values());

		String socialNetEditIds = "";
		List<Integer> socialNetEditIdLs = new ArrayList<>();
		for (ResumeSocialNetDTO socialNetDTO : resumeInfoDTO.getResumeSocialNetDTOs()) {
			socialNetEditIdLs.add(socialNetDTO.getId());
		}
		socialNetEditIds = StringUtils.join(socialNetEditIdLs, ",");

		ResumeSpecialtySkillDTO tempObj = new ResumeSpecialtySkillDTO();

		List<EnLangSkillDTO> enLangSkillDTOs = resumeInfoDTO.getResumeSpecialtySkillDTO() == null ? tempObj
				.getEnLangSkillDTOs() : resumeInfoDTO.getResumeSpecialtySkillDTO().getEnLangSkillDTOs();
		int resumeSpecialtySkillEnSize = enLangSkillDTOs.size();

		List<OtherLangSkillDTO> otherLangSkillDTOs = resumeInfoDTO.getResumeSpecialtySkillDTO() == null ? tempObj
				.getOtherLangSkillDTOs() : resumeInfoDTO.getResumeSpecialtySkillDTO().getOtherLangSkillDTOs();
		int resumeSpecialtySkillOtherSize = otherLangSkillDTOs.size();

		List<ComputerSkillDTO> computerSkillDTOs = resumeInfoDTO.getResumeSpecialtySkillDTO() == null ? tempObj
				.getComputerSkillDTOs() : resumeInfoDTO.getResumeSpecialtySkillDTO().getComputerSkillDTOs();
		int resumeSpecialtySkillComputerSize = computerSkillDTOs.size();

		List<CertificateDTO> certificateDTOs = resumeInfoDTO.getResumeSpecialtySkillDTO() == null ? tempObj
				.getCertificateDTOs() : resumeInfoDTO.getResumeSpecialtySkillDTO().getCertificateDTOs();
		int resumeSpecialtySkillCertificateSize = certificateDTOs.size();

		Map<String, List<ComputerSkillEnum>> computerSkillMap = getComputerSkillMap();

		boolean hasAttachmentFile = !StringUtils.isBlank(resumeInfoDTO.getUploadPath());

		// ModelAndView mav = new ModelAndView("resume_info");
		ModelAndView mav = new ModelAndView("online_resume");
		mav.addObject("memberInfo", memberDTO);
		mav.addObject("resumeInfo", resumeInfoDTO);
		mav.addObject("genderEnums", genderEnums);
		mav.addObject("educationEnums", educationEnums);
		mav.addObject("specialityEnums", specialityEnums);
		mav.addObject("achievementEnums", achievementEnums);
		mav.addObject("rewardLevelEnums", rewardLevelEnums);
		mav.addObject("socialNetworkEnums", socialNetworkEnums);
		mav.addObject("englishSkillEnums", englishSkillEnums);
		mav.addObject("otherLanguageSkillEnums", otherLanguageSkillEnums);
		mav.addObject("computerSkillMap", computerSkillMap);
		mav.addObject("skillLevelEnums", skillLevelEnums);
		mav.addObject("geoAreaList", getUniversityGeoList());
		mav.addObject("resumeEducationExpSize", resumeInfoDTO.getResumeEducationExpDTOs().size());
		mav.addObject("resumeWorkExpSize", resumeInfoDTO.getResumeWorkExpDTOs().size());
		mav.addObject("resumeActivityExpSize", resumeInfoDTO.getResumeActivityExpDTOs().size());
		mav.addObject("resumeProjectExpSize", resumeInfoDTO.getResumeProjectExpDTOs().size());
		mav.addObject("resumeHobbySpecialSize", resumeInfoDTO.getResumeHobbySpecialDTOs().size());
		mav.addObject("resumeCustomSize", resumeInfoDTO.getResumeCustomDTOs().size());
		mav.addObject("resumePrizeInfoSize", resumeInfoDTO.getResumePrizeInfoDTOs().size());
		mav.addObject("resumeSocialNetSize", resumeInfoDTO.getResumeSocialNetDTOs().size());
		mav.addObject("resumeOpusInfoSize", resumeInfoDTO.getResumeOpusInfoDTOs().size());
		mav.addObject("socialNetEditIds", socialNetEditIds);
		mav.addObject("resumeSpecialtySkillEnSize", resumeSpecialtySkillEnSize);
		mav.addObject("resumeSpecialtySkillOtherSize", resumeSpecialtySkillOtherSize);
		mav.addObject("resumeSpecialtySkillComputerSize", resumeSpecialtySkillComputerSize);
		mav.addObject("resumeSpecialtySkillCertSize", resumeSpecialtySkillCertificateSize);
		mav.addObject("hasAttachmentFile", hasAttachmentFile);

		return mav;
	}

	/**
	 * 保存简历名称
	 * 
	 * @param resumeId
	 * @param resumeName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeNameInfo", method = RequestMethod.POST)
	public Map<String, Object> saveResumeNameInfo(Integer resumeId, String resumeName) {
		logger.info("execute saveResumeNameInfo action...");
		logger.info(String.format("resumeId: %d, resumeName: %s", resumeId, resumeName));
		resumeId = resumeId == null ? 0 : resumeId;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
		if (resumeInfoDTO != null) {
			resumeInfoDTO.setResumeName(resumeName);
			int count = resumeInfoService.updateResumeInfo(resumeInfoDTO);
			logger.info(String.format("update %d record for resumename with id: %d", count, resumeId));
			resultMap.put("success", true);
			resultMap.put("message", "");
			resultMap.put("resumeName", resumeInfoDTO.getResumeName());
		} else {
			resultMap.put("success", false);
			resultMap.put("message", "");
			resultMap.put("resumeName", "");
		}

		return resultMap;
	}

	/**
	 * 保存简历基本信息
	 * 
	 * @param memberId
	 * @param resumeId
	 * @param resumeMemberName
	 * @param resumeName
	 * @param resumeGender
	 * @param resumeSchool
	 * @param resumeMajor
	 * @param resumeDiploma
	 * @param resumeGraduationTime
	 * @param resumeEmail
	 * @param resumeMobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBasicInfo", method = RequestMethod.POST)
	public Map<String, Object> saveBasicInfo(String memberId, Integer resumeId, String resumeMemberName,
			String resumeName, Integer resumeGender, Integer resumeSchool, String resumeMajor, Integer resumeDiploma,
			String resumeGraduationTime, String resumeEmail, String resumeMobile) {
		memberId = memberId == null ? "" : memberId;
		resumeId = resumeId == null ? 0 : resumeId;

		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		memberDTO.setName(resumeMemberName);
		memberDTO.setSex(resumeGender);
		memberDTO.setCommonEmail(resumeEmail);
		memberDTO.setMobile(resumeMobile);
		memberDTO.setSchoolId(resumeSchool);

		int count = memberService.updateMemberInfo(memberDTO);
		logger.info("update " + count + " memberinfo for id: " + memberId);

		int count2 = 0;
		ResumeInfoDTO resumeInfo = null;
		if (resumeId > 0) {
			resumeInfo = resumeInfoService.queryResumeInfo(resumeId);
			resumeInfo.setResumeName(resumeName);
			resumeInfo.setSex(resumeGender);
			resumeInfo.setMajor(resumeMajor);
			resumeInfo.setDiploma(resumeDiploma);
			try {
				Date date = DateUtils.parseDate(resumeGraduationTime, CommonUtils.YYYY_MM_DD_DATA);
				resumeInfo.setGraduationTime(date);
				resumeInfo.setGraduationTimeDesc(resumeGraduationTime);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}

			count2 = resumeInfoService.updateResumeInfo(resumeInfo);
			logger.info("update " + count2 + " resumeInfo for id: " + resumeInfo.getId());
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ResumeInfoDTO resultInfo = count2 > 0 ? resumeInfo : resumeInfoService.queryResumeInfo(resumeId);
		resultMap.put("success", 1);
		resultMap.put("resumeInfo", resultInfo);

		return resultMap;
	}

	/**
	 * 上传保存简历头像
	 * 
	 * @param file
	 * @param resumeId
	 * @param hdEditId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadHeadPic", method = RequestMethod.POST)
	public Map<String, Object> uploadHeadPic(@RequestParam("headPicFile") CommonsMultipartFile file, Integer resumeId,
			HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resumeId = resumeId == null ? 0 : resumeId;
		boolean flag = false;
		String message = StringUtils.EMPTY;

		// 上传文件后编辑该记录opusUrl
		logger.info(String.format("resumeId: %d, filename: %s", resumeId, file.getOriginalFilename()));
		String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		logger.info("fileName: " + fileName + ", fileExt: " + fileExt);

		fileName = String.format("headpic_%d_%s.%s", resumeId, fileName, fileExt);

		List<String> validSuffixList = Arrays.asList(new String[] { "jpg", "png", "jpeg", "gif" });

		if (!validSuffixList.contains(fileExt)) {
			message = "图片格式不正确（jgp,png,gif）";
			resultMap.put("success", flag);
			resultMap.put("message", message);
			return resultMap;

		} else if (file.getSize() > MAX_SIZE) {
			message = "图片大小限制为5M，请重新上传";
			resultMap.put("success", flag);
			resultMap.put("message", message);
			return resultMap;

		} else {
			try {
				Response response = QiniuUtils.uploadFile(file.getBytes(), fileName, QiniuUtils.getToken(fileName));
				logger.info("bodyString: " + response.bodyString());
			} catch (QiniuException e) {
				logger.error(e.getMessage(), e);
			}

			String headpicPath = String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, fileName);
			int count = 0;
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			if (resumeInfoDTO != null) {
				resumeInfoDTO.setAvatar(headpicPath);
				count = resumeInfoService.updateResumeInfo(resumeInfoDTO);
				logger.info("update " + count + " headpic for id: " + resumeId);
			}

			flag = true;
			resultMap.put("success", flag);
			resultMap.put("message", message);
			resultMap.put("url", headpicPath);
		}

		return resultMap;

	}

	/**
	 * 保存教育经历信息
	 * 
	 * @param resumeId
	 * @param resumeEduExpId
	 * @param resumeEduExpDiploma
	 * @param resumeEduExpSchool
	 * @param resumeEduExpMajor
	 * @param resumeEduExpMajorType
	 * @param resumeEduExpAcademicStarts
	 * @param resumeEduExpGraduationTime
	 * @param resumeEduExpScoreTop
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeEduExp", method = RequestMethod.POST)
	public Map<String, Object> saveResumeEduExp(Integer resumeId, Integer resumeEduExpId, Integer resumeEduExpDiploma,
			String resumeEduExpSchool, String resumeEduExpMajor, Integer resumeEduExpMajorType,
			String resumeEduExpAcademicStarts, String resumeEduExpGraduationTime, Integer resumeEduExpScoreTop) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumeEduExpId = resumeEduExpId == null ? 0 : resumeEduExpId;
		try {
			logger.info("execute saveResumeEduExp action...");
			ResumeEducationExpDTO resumeEducationExp = null;
			if (resumeEduExpId > 0) {
				// 更新
				resumeEducationExp = resumeEducationExpService.queryResumeEducationExp(resumeEduExpId);
			} else {
				// 添加
				resumeEducationExp = new ResumeEducationExpDTO();
			}

			resumeEducationExp.setResumeId(resumeId);
			resumeEducationExp.setDiploma(resumeEduExpDiploma);
			resumeEducationExp.setSchool(resumeEduExpSchool);
			resumeEducationExp.setMajor(resumeEduExpMajor);
			resumeEducationExp.setMajorType(resumeEduExpMajorType);
			Date academicStarts = null;
			try {
				academicStarts = DateUtils.parseDate(resumeEduExpAcademicStarts, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				academicStarts = new Date();
				logger.error(e.getMessage(), e);
			}

			Date graduationTime = null;
			try {
				graduationTime = DateUtils.parseDate(resumeEduExpGraduationTime, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				graduationTime = new Date();
				logger.error(e.getMessage(), e);
			}
			resumeEducationExp.setAcademicStarts(academicStarts);
			resumeEducationExp.setGraduationTime(graduationTime);
			resumeEducationExp.setScoreTop(resumeEduExpScoreTop);

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumeEduExpId > 0) {
				count = resumeEducationExpService.updateResumeEducationExp(resumeEducationExp);
				logger.info("update " + count + " item for id: " + resumeEducationExp.getId());

				appendHtmlContent(sb, resumeEducationExp);
				html = sb.toString();

			} else {
				count = resumeEducationExpService.insertResumeEducationExp(resumeEducationExp);
				logger.info("insert " + count + " item for id: " + resumeEducationExp.getId());

				sb = new StringBuilder(String.format("<div id='edu_exp_%d' class='div_boder' val='%d'>",
						resumeEducationExp.getId(), resumeEducationExp.getId()));
				appendHtmlContent(sb, resumeEducationExp);
				sb.append("</div>");
				html = sb.toString();

			}

			logger.info("html: " + html);
			ResumeEducationExpDTO result = count > 0 ? resumeEducationExp : resumeEducationExpService
					.queryResumeEducationExp(resumeEduExpId);
			int resumeEducationExpSize = resumeEducationExpService.queryResumeEducationExps(resumeId).size();

			resultMap.put("success", true);
			resultMap.put("resumeEducationExp", result);
			resultMap.put("resumeEducationExpHtml", html);
			resultMap.put("resumeEducationExpSize", resumeEducationExpSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumeEducationExp", new ResumeEducationExpDTO());
			resultMap.put("resumeEducationExpHtml", "");
		}

		return resultMap;
	}

	/**
	 * 添加html内容
	 * 
	 * @param sb
	 * @param resumeEducationExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeEducationExpDTO resumeEducationExp) {
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkEdit_%d' class='aLinkEdit' onclick='editResumeEduExp(%d)'>编辑</a>",
						resumeEducationExp.getId(), resumeEducationExp.getId()));
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkDelete_%d' class='aLinkDelete ml6' onclick='deleteResumeEduExp(%d)'>删除</a>",
						resumeEducationExp.getId(), resumeEducationExp.getId()));
		sb.append(String
				.format("<input type='hidden' id='edu_exp_detail_%d' diploma='%d' diplomaDesc='%s' school='%s' major='%s' majorType='%d' majorTypeDesc='%s' academicStartsDesc='%s' graduationTimeDesc='%s' scoreTop='%d' scoreTopDesc='%s' value='%d' />",
						resumeEducationExp.getId(), resumeEducationExp.getDiploma(),
						resumeEducationExp.getDiplomaDesc(), resumeEducationExp.getSchool(),
						resumeEducationExp.getMajor(), resumeEducationExp.getMajorType(),
						resumeEducationExp.getMajorTypeDesc(), resumeEducationExp.getAcademicStartsDesc(),
						resumeEducationExp.getGraduationTimeDesc(), resumeEducationExp.getScoreTop(),
						resumeEducationExp.getScoreTopDesc(), resumeEducationExp.getId()));
		sb.append("<div>");
		sb.append(String.format("%s<span class=\"mr3\"></span>", resumeEducationExp.getSchool()));
		sb.append(String.format("%s~%s<span class=\"mr3\"></span>", resumeEducationExp.getAcademicStartsDesc(),
				resumeEducationExp.getGraduationTimeDesc()));
		sb.append(String.format("%s<span class=\"mr3\"></span>", resumeEducationExp.getDiplomaDesc()));
		sb.append(String.format("%s<span class=\"mr3\"></span>", resumeEducationExp.getMajor()));
		sb.append(String.format("%s<span class=\"mr3\"></span>", resumeEducationExp.getMajorTypeDesc()));
		sb.append(String.format("%s<span class=\"mr3\"></span>", resumeEducationExp.getScoreTopDesc()));
		sb.append("</div>");

		// sb.append(String.format("<span>学历/学位：%s</span><br />",
		// resumeEducationExp.getDiplomaDesc()));
		// sb.append(String.format("<span>学校名称：%s</span><br />",
		// resumeEducationExp.getSchool()));
		// sb.append(String.format("<span>专业名称：%s</span><br />",
		// resumeEducationExp.getMajor()));
		// sb.append(String.format("<span>专业分类：%s</span><br />",
		// resumeEducationExp.getMajorTypeDesc()));
		// sb.append(String.format("<span>入学时间：%s</span><br />",
		// resumeEducationExp.getAcademicStartsDesc()));
		// sb.append(String.format("<span>毕业时间：%s</span><br />",
		// resumeEducationExp.getGraduationTimeDesc()));
		// sb.append(String.format("<span>成绩排名：%s</span><br />",
		// resumeEducationExp.getScoreTopDesc()));
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除教育经历信息条目
	 * 
	 * @param resumeWorkExpId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeEduExp", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeEduExp(Integer resumeEduExpId, Integer resumeId) {
		logger.info("execute deleteResumeEduExp action for id: " + resumeEduExpId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int count = resumeEducationExpService.deleteResumeEducationExp(resumeEduExpId);
			int resumeEducationExpSize = resumeEducationExpService.queryResumeEducationExps(resumeId).size();
			logger.info("delete " + count + " item for id: " + resumeEduExpId);
			resultMap.put("success", true);
			resultMap.put("resumeEducationExpSize", resumeEducationExpSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存实习与工作经历
	 * 
	 * @param resumeWorkExpId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeWorkExp", method = RequestMethod.POST)
	public Map<String, Object> saveResumeWorkExp(Integer resumeWorkExpId, Integer resumeId,
			String resumeWorkExpCompanyName, String resumeWorkExpPositionName, String resumeWorkExpStartTime,
			String resumeWorkExpEndTime, String resumeWorkExpDesc) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumeWorkExpId = resumeWorkExpId == null ? 0 : resumeWorkExpId;
		try {
			logger.info("execute saveResumeEduExp action...");
			ResumeWorkExpDTO resumeWorkExp = null;
			if (resumeWorkExpId > 0) {
				// 更新
				resumeWorkExp = resumeWorkExpService.queryResumeWorkExp(resumeWorkExpId);
			} else {
				// 添加
				resumeWorkExp = new ResumeWorkExpDTO();
			}

			resumeWorkExp.setResumeId(resumeId);
			resumeWorkExp.setCompanyName(resumeWorkExpCompanyName);
			resumeWorkExp.setPositionName(resumeWorkExpPositionName);
			resumeWorkExp.setWorkDescription(resumeWorkExpDesc);
			Date workExpStartTime = null;
			try {
				workExpStartTime = DateUtils.parseDate(resumeWorkExpStartTime, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				workExpStartTime = new Date();
				logger.error(e.getMessage(), e);
			}

			Date workExpEndTime = null;
			try {
				workExpEndTime = DateUtils.parseDate(resumeWorkExpEndTime, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				workExpEndTime = new Date();
				logger.error(e.getMessage(), e);
			}
			resumeWorkExp.setStartTime(workExpStartTime);
			resumeWorkExp.setEndTime(workExpEndTime);

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumeWorkExpId > 0) {
				count = resumeWorkExpService.updateResumeWorkExp(resumeWorkExp);
				logger.info("update " + count + " item for id: " + resumeWorkExp.getId());

				appendHtmlContent(sb, resumeWorkExp);
				html = sb.toString();

			} else {
				count = resumeWorkExpService.insertResumeWorkExp(resumeWorkExp);
				logger.info("insert " + count + " item for id: " + resumeWorkExp.getId());

				sb = new StringBuilder(String.format("<div id='work_exp_%d' class='div_boder'>", resumeWorkExp.getId()));
				appendHtmlContent(sb, resumeWorkExp);
				sb.append("</div>");
				html = sb.toString();

			}

			logger.info("html: " + html);
			int resumeWorkExpSize = resumeWorkExpService.queryResumeWorkExps(resumeId).size();
			ResumeWorkExpDTO result = count > 0 ? resumeWorkExp : resumeWorkExpService
					.queryResumeWorkExp(resumeWorkExpId);

			resultMap.put("success", true);
			resultMap.put("resumeWorkExp", result);
			resultMap.put("resumeWorkExpHtml", html);
			resultMap.put("resumeWorkExpSize", resumeWorkExpSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumeWorkExp", new ResumeWorkExpDTO());
			resultMap.put("resumeWorkExpHtml", "");
			resultMap.put("resumeWorkExpSize", 0);
		}

		return resultMap;
	}

	/**
	 * 返回实习与工作经历动态html
	 * 
	 * @param sb
	 * @param resumeWorkExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeWorkExpDTO resumeWorkExp) {
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkEdit_%d' class='aLinkEdit' onclick='editResumeWorkExp(%d)'>编辑</a>",
						resumeWorkExp.getId(), resumeWorkExp.getId()));
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkDelete_%d' class='aLinkDelete ml6' onclick='deleteResumeWorkExp(%d)'>删除</a>",
						resumeWorkExp.getId(), resumeWorkExp.getId()));
		sb.append(String
				.format("<input type='hidden' id='work_exp_detail_%d' companyName='%s' positionName='%s' startTimeDesc='%s' endTimeDesc='%s' workDescription='%s' value='%d' />",
						resumeWorkExp.getId(), resumeWorkExp.getCompanyName(), resumeWorkExp.getPositionName(),
						resumeWorkExp.getStartTimeDesc(), resumeWorkExp.getEndTimeDesc(),
						resumeWorkExp.getWorkDescription(), resumeWorkExp.getId()));
		sb.append(String.format("<span>公司名称：%s</span><br />", resumeWorkExp.getCompanyName()));
		sb.append(String.format("<span>职位名称：%s</span><br />", resumeWorkExp.getPositionName()));
		sb.append(String.format("<span>开始时间：%s</span><br />", resumeWorkExp.getStartTimeDesc()));
		sb.append(String.format("<span>结束时间：%s</span><br />", resumeWorkExp.getEndTimeDesc()));
		sb.append(String.format("<span><div>工作描述：%s</div></span>", resumeWorkExp.getWorkDescription()));
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除实习与工作经历
	 * 
	 * @param resumeWorkExpId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeWorkExp", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeWorkExp(Integer resumeWorkExpId, Integer resumeId) {
		logger.info("execute deleteResumeWorkExp action for id: " + resumeWorkExpId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int count = resumeWorkExpService.deleteResumeWorkExp(resumeWorkExpId);
			int resumeWorkExpSize = resumeWorkExpService.queryResumeWorkExps(resumeId).size();
			logger.info("delete " + count + " item for id: " + resumeWorkExpId);
			resultMap.put("success", true);
			resultMap.put("resumeWorkExpSize", resumeWorkExpSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存校园活动经历
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeActivityExp", method = RequestMethod.POST)
	public Map<String, Object> saveResumeActivityExp(Integer resumeActivityExpId, Integer resumeId,
			String resumeActivityExpName, String resumeActivityExpPositionName, String resumeActivityExpStartTime,
			String resumeActivityExpEndTime, String resumeActivityExpDesc) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumeActivityExpId = resumeActivityExpId == null ? 0 : resumeActivityExpId;
		try {
			logger.info("execute saveResumeActivityExp action...");
			ResumeActivityExpDTO resumeActivityExp = null;
			if (resumeActivityExpId > 0) {
				// 更新
				resumeActivityExp = resumeActivityExpService.queryResumeActivityExp(resumeActivityExpId);
			} else {
				// 添加
				resumeActivityExp = new ResumeActivityExpDTO();
			}

			resumeActivityExp.setResumeId(resumeId);
			resumeActivityExp.setActivityName(resumeActivityExpName);
			resumeActivityExp.setPositionName(resumeActivityExpPositionName);
			resumeActivityExp.setActivityDesc(resumeActivityExpDesc);
			Date activityExpStartTime = null;
			try {
				activityExpStartTime = DateUtils.parseDate(resumeActivityExpStartTime, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				activityExpStartTime = new Date();
				logger.error(e.getMessage(), e);
			}

			Date activityExpEndTime = null;
			try {
				activityExpEndTime = DateUtils.parseDate(resumeActivityExpEndTime, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				activityExpEndTime = new Date();
				logger.error(e.getMessage(), e);
			}
			resumeActivityExp.setStartTime(activityExpStartTime);
			resumeActivityExp.setEndTime(activityExpEndTime);

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumeActivityExpId > 0) {
				count = resumeActivityExpService.updateResumeActivityExp(resumeActivityExp);
				logger.info("update " + count + " item for id: " + resumeActivityExp.getId());

				appendHtmlContent(sb, resumeActivityExp);
				html = sb.toString();

			} else {
				count = resumeActivityExpService.insertResumeActivityExp(resumeActivityExp);
				logger.info("insert " + count + " item for id: " + resumeActivityExp.getId());

				sb = new StringBuilder(String.format("<div id='activity_exp_%d' class='div_boder'>",
						resumeActivityExp.getId()));
				appendHtmlContent(sb, resumeActivityExp);
				sb.append("</div>");
				html = sb.toString();

			}

			logger.info("html: " + html);
			int resumeActivityExpSize = resumeActivityExpService.queryResumeActivityExps(resumeId).size();
			ResumeActivityExpDTO result = count > 0 ? resumeActivityExp : resumeActivityExpService
					.queryResumeActivityExp(resumeActivityExpId);
			resultMap.put("success", true);
			resultMap.put("resumeActivityExp", result);
			resultMap.put("resumeActivityExpHtml", html);
			resultMap.put("resumeActivityExpSize", resumeActivityExpSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumeActivityExp", new ResumeWorkExpDTO());
			resultMap.put("resumeActivityExpHtml", "");
			resultMap.put("resumeActivityExpSize", 0);
		}

		return resultMap;
	}

	/**
	 * 添加html内容
	 * 
	 * @param sb
	 * @param resumeEducationExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeActivityExpDTO resumeActivityExp) {
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkEdit_%d' class='aLinkEdit' onclick='editResumeActivityExp(%d)'>编辑</a>",
						resumeActivityExp.getId(), resumeActivityExp.getId()));
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkDelete_%d' class='aLinkDelete ml6' onclick='deleteResumeActivityExp(%d)'>删除</a>",
						resumeActivityExp.getId(), resumeActivityExp.getId()));
		sb.append(String
				.format("<input type='hidden' id='activity_exp_detail_%d' activityName='%s' positionName='%s' startTimeDesc='%s' endTimeDesc='%s' activityDesc='%s' value='%d' />",
						resumeActivityExp.getId(), resumeActivityExp.getActivityName(),
						resumeActivityExp.getPositionName(), resumeActivityExp.getStartTimeDesc(),
						resumeActivityExp.getEndTimeDesc(), resumeActivityExp.getActivityDesc(),
						resumeActivityExp.getId()));
		sb.append(String.format("<span>组织或活动名称：%s</span><br />", resumeActivityExp.getActivityName()));
		sb.append(String.format("<span>职位名称：%s</span><br />", resumeActivityExp.getPositionName()));
		sb.append(String.format("<span>开始时间：%s</span><br />", resumeActivityExp.getStartTimeDesc()));
		sb.append(String.format("<span>结束时间：%s</span><br />", resumeActivityExp.getEndTimeDesc()));
		sb.append(String.format("<span><div>工作描述：%s</div></span>", resumeActivityExp.getActivityDesc()));
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除校园活动经历
	 * 
	 * @param resumeActivityExpId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeActivityExp", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeActivityExp(Integer resumeActivityExpId, Integer resumeId) {
		logger.info("execute deleteResumeActivityExp action for id: " + resumeActivityExpId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int count = resumeActivityExpService.deleteResumeActivityExp(resumeActivityExpId);
			int resumeActivityExpSize = resumeActivityExpService.queryResumeActivityExps(resumeId).size();
			logger.info("delete " + count + " item for id: " + resumeActivityExpId);
			resultMap.put("success", true);
			resultMap.put("resumeActivityExpSize", resumeActivityExpSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存项目信息
	 * 
	 * @param resumeProjectExpId
	 * @param resumeId
	 * @param resumeProjectExpName
	 * @param resumeProjectExpPositionName
	 * @param resumeProjectExpStartTime
	 * @param resumeProjectExpEndTime
	 * @param resumeProjectExpDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeProjectExp", method = RequestMethod.POST)
	public Map<String, Object> saveResumeProjectExp(Integer resumeProjectExpId, Integer resumeId,
			String resumeProjectExpName, String resumeProjectExpPositionName, String resumeProjectExpStartTime,
			String resumeProjectExpEndTime, String resumeProjectExpDesc) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumeProjectExpId = resumeProjectExpId == null ? 0 : resumeProjectExpId;
		try {
			logger.info("execute saveResumeProjectExp action...");
			ResumeProjectExpDTO resumeProjectExp = null;
			if (resumeProjectExpId > 0) {
				// 更新
				resumeProjectExp = resumeProjectExpService.queryResumeProjectExp(resumeProjectExpId);
			} else {
				// 添加
				resumeProjectExp = new ResumeProjectExpDTO();
			}

			resumeProjectExp.setResumeId(resumeId);
			resumeProjectExp.setProjectName(resumeProjectExpName);
			resumeProjectExp.setPositionName(resumeProjectExpPositionName);
			resumeProjectExp.setProjectDesc(resumeProjectExpDesc);
			Date activityExpStartTime = null;
			try {
				activityExpStartTime = DateUtils.parseDate(resumeProjectExpStartTime, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				activityExpStartTime = new Date();
				logger.error(e.getMessage(), e);
			}

			Date activityExpEndTime = null;
			try {
				activityExpEndTime = DateUtils.parseDate(resumeProjectExpEndTime, CommonUtils.YYYY_MM_DD_DATA);
			} catch (ParseException e) {
				activityExpEndTime = new Date();
				logger.error(e.getMessage(), e);
			}
			resumeProjectExp.setStartTime(activityExpStartTime);
			resumeProjectExp.setEndTime(activityExpEndTime);

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumeProjectExpId > 0) {
				count = resumeProjectExpService.updateResumeProjectExp(resumeProjectExp);
				logger.info("update " + count + " item for id: " + resumeProjectExp.getId());

				appendHtmlContent(sb, resumeProjectExp);
				html = sb.toString();

			} else {
				count = resumeProjectExpService.insertResumeProjectExp(resumeProjectExp);
				logger.info("insert " + count + " item for id: " + resumeProjectExp.getId());

				sb = new StringBuilder(String.format("<div id='project_exp_%d' class='div_boder'>",
						resumeProjectExp.getId()));
				appendHtmlContent(sb, resumeProjectExp);
				sb.append("</div>");
				html = sb.toString();

			}

			logger.info("html: " + html);
			int resumeProjectExpSize = resumeProjectExpService.queryResumeProjectExps(resumeId).size();
			ResumeProjectExpDTO result = count > 0 ? resumeProjectExp : resumeProjectExpService
					.queryResumeProjectExp(resumeProjectExpId);
			resultMap.put("success", true);
			resultMap.put("resumeProjectExp", result);
			resultMap.put("resumeProjectExpHtml", html);
			resultMap.put("resumeProjectExpSize", resumeProjectExpSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumeProjectExp", new ResumeProjectExpDTO());
			resultMap.put("resumeProjectExpHtml", "");
			resultMap.put("resumeProjectExpSize", 0);
		}

		return resultMap;
	}

	/**
	 * 添加html内容
	 * 
	 * @param sb
	 * @param resumeEducationExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeProjectExpDTO resumeProjectExp) {
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkEdit_%d' class='aLinkEdit' onclick='editResumeProjectExp(%d)'>编辑</a>",
						resumeProjectExp.getId(), resumeProjectExp.getId()));
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkDelete_%d' class='aLinkDelete ml6' onclick='deleteResumeProjectExp(%d)'>删除</a>",
						resumeProjectExp.getId(), resumeProjectExp.getId()));
		sb.append(String
				.format("<input type='hidden' id='project_exp_detail_%d' projectName='%s' positionName='%s' startTimeDesc='%s' endTimeDesc='%s' projectDesc='%s' value='%d' />",
						resumeProjectExp.getId(), resumeProjectExp.getProjectName(),
						resumeProjectExp.getPositionName(), resumeProjectExp.getStartTimeDesc(),
						resumeProjectExp.getEndTimeDesc(), resumeProjectExp.getProjectDesc(), resumeProjectExp.getId()));
		sb.append(String.format("<span>项目名称：%s</span><br />", resumeProjectExp.getProjectName()));
		sb.append(String.format("<span>职位名称：%s</span><br />", resumeProjectExp.getPositionName()));
		sb.append(String.format("<span>开始时间：%s</span><br />", resumeProjectExp.getStartTimeDesc()));
		sb.append(String.format("<span>结束时间：%s</span><br />", resumeProjectExp.getEndTimeDesc()));
		sb.append(String.format("<span><div>工作描述：%s</div></span>", resumeProjectExp.getProjectDesc()));
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除校园活动经历
	 * 
	 * @param resumeProjectExpId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeProjectExp", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeProjectExp(Integer resumeProjectExpId, Integer resumeId) {
		logger.info("execute deleteResumeProjectExp action for id: " + resumeProjectExpId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int count = resumeProjectExpService.deleteResumeProjectExp(resumeProjectExpId);
			int resumeProjectExpSize = resumeProjectExpService.queryResumeProjectExps(resumeId).size();
			logger.info("delete " + count + " item for id: " + resumeProjectExpId);
			resultMap.put("success", true);
			resultMap.put("resumeProjectExpSize", resumeProjectExpSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存爱好特长
	 * 
	 * @param resumeHobbySpecialId
	 * @param resumeId
	 * @param resumeHobbySpecialName
	 * @param resumeHobbySpecialDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeHobbySpecialExp", method = RequestMethod.POST)
	public Map<String, Object> saveResumeHobbySpecialExp(Integer resumeHobbySpecialId, Integer resumeId,
			String resumeHobbySpecialName, String resumeHobbySpecialDesc) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumeHobbySpecialId = resumeHobbySpecialId == null ? 0 : resumeHobbySpecialId;
		try {
			logger.info("execute saveResumeHobbySpecialExp action...");
			ResumeHobbySpecialDTO resumeHobbySpecial = null;
			if (resumeHobbySpecialId > 0) {
				// 更新
				resumeHobbySpecial = resumeHobbySpecialService.queryResumeHobbySpecial(resumeHobbySpecialId);
			} else {
				// 添加
				resumeHobbySpecial = new ResumeHobbySpecialDTO();
			}

			resumeHobbySpecial.setResumeId(resumeId);
			resumeHobbySpecial.setSpecialName(resumeHobbySpecialName);
			resumeHobbySpecial.setSpecialDesc(resumeHobbySpecialDesc);

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumeHobbySpecialId > 0) {
				count = resumeHobbySpecialService.updateResumeHobbySpecial(resumeHobbySpecial);
				logger.info("update " + count + " item for id: " + resumeHobbySpecial.getId());

				appendHtmlContent(sb, resumeHobbySpecial);
				html = sb.toString();

			} else {
				count = resumeHobbySpecialService.insertResumeHobbySpecial(resumeHobbySpecial);
				logger.info("insert " + count + " item for id: " + resumeHobbySpecial.getId());

				sb = new StringBuilder(String.format("<div id='hobby_special_%d' class='div_boder'>",
						resumeHobbySpecial.getId()));
				appendHtmlContent(sb, resumeHobbySpecial);
				sb.append("</div>");
				html = sb.toString();
			}

			logger.info("html: " + html);
			int resumeHobbySpecialSize = resumeHobbySpecialService.queryResumeHobbySpecials(resumeId).size();
			ResumeHobbySpecialDTO result = count > 0 ? resumeHobbySpecial : resumeHobbySpecialService
					.queryResumeHobbySpecial(resumeHobbySpecialId);
			resultMap.put("success", true);
			resultMap.put("resumeHobbySpecial", result);
			resultMap.put("resumeHobbySpeciaHtml", html);
			resultMap.put("resumeHobbySpecialSize", resumeHobbySpecialSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumeHobbySpecial", new ResumeHobbySpecialDTO());
			resultMap.put("resumeHobbySpeciaHtml", "");
			resultMap.put("resumeHobbySpecialSize", 0);
		}

		return resultMap;
	}

	/**
	 * 添加html内容
	 * 
	 * @param sb
	 * @param resumeEducationExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeHobbySpecialDTO resumeHobbySpecial) {
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkEdit_%d' class='aLinkEdit' onclick='editResumeHobbySpecialExp(%d)'>编辑</a>",
						resumeHobbySpecial.getId(), resumeHobbySpecial.getId()));
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkDelete_%d' class='aLinkDelete ml6' onclick='deleteResumeHobbySpecialExp(%d)'>删除</a>",
						resumeHobbySpecial.getId(), resumeHobbySpecial.getId()));
		sb.append(String.format(
				"<input type='hidden' id='hobby_special_detail_%d' specialName='%s' specialDesc='%s' value='%d' />",
				resumeHobbySpecial.getId(), resumeHobbySpecial.getSpecialName(), resumeHobbySpecial.getSpecialDesc(),
				resumeHobbySpecial.getId()));
		sb.append(String.format("<span>爱好特长名称：%s</span><br />", resumeHobbySpecial.getSpecialName()));
		sb.append(String.format("<span>爱好特长描述：%s</span><br />", resumeHobbySpecial.getSpecialDesc()));
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除爱好特长经历
	 * 
	 * @param resumeHobbySpecialId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeHobbySpecialExp", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeHobbySpecialExp(Integer resumeHobbySpecialId, Integer resumeId) {
		logger.info("execute deleteResumeHobbySpecialExp action for id: " + resumeHobbySpecialId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int count = resumeHobbySpecialService.deleteResumeHobbySpecial(resumeHobbySpecialId);
			int resumeHobbySpecialSize = resumeHobbySpecialService.queryResumeHobbySpecials(resumeId).size();
			logger.info("delete " + count + " item for id: " + resumeHobbySpecialId);
			resultMap.put("success", true);
			resultMap.put("resumeHobbySpecialSize", resumeHobbySpecialSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存自定义板块
	 * 
	 * @param resumeCustomId
	 * @param resumeId
	 * @param resumeCustomName
	 * @param resumeCustomDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeCustom", method = RequestMethod.POST)
	public Map<String, Object> saveResumeCustom(Integer resumeCustomId, Integer resumeId, String resumeCustomName,
			String resumeCustomDesc) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumeCustomId = resumeCustomId == null ? 0 : resumeCustomId;
		try {
			logger.info("execute saveResumeCustom action...");
			ResumeCustomDTO resumeCustom = null;
			if (resumeCustomId > 0) {
				// 更新
				resumeCustom = resumeCustomService.queryResumeCustom(resumeCustomId);
			} else {
				// 添加
				resumeCustom = new ResumeCustomDTO();
			}

			resumeCustom.setResumeId(resumeId);
			resumeCustom.setName(resumeCustomName);
			resumeCustom.setDescription(resumeCustomDesc);

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumeCustomId > 0) {
				count = resumeCustomService.updateResumeCustom(resumeCustom);
				logger.info("update " + count + " item for id: " + resumeCustom.getId());

				appendHtmlContent(sb, resumeCustom);
				html = sb.toString();

			} else {
				count = resumeCustomService.insertResumeCustom(resumeCustom);
				logger.info("insert " + count + " item for id: " + resumeCustom.getId());

				sb = new StringBuilder(String.format("<div id='hobby_custom_%d' class='div_boder'>",
						resumeCustom.getId()));
				appendHtmlContent(sb, resumeCustom);
				sb.append("</div>");
				html = sb.toString();

			}

			logger.info("html: " + html);
			int resumeCustomSize = resumeCustomService.queryResumeCustoms(resumeId).size();
			ResumeCustomDTO result = count > 0 ? resumeCustom : resumeCustomService.queryResumeCustom(resumeCustomId);
			resultMap.put("success", true);
			resultMap.put("resumeCustom", result);
			resultMap.put("resumeCustomHtml", html);
			resultMap.put("resumeCustomSize", resumeCustomSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumeCustom", new ResumeCustomDTO());
			resultMap.put("resumeCustomHtml", "");
			resultMap.put("resumeCustomSize", 0);
		}

		return resultMap;
	}

	/**
	 * 添加html内容
	 * 
	 * @param sb
	 * @param resumeEducationExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeCustomDTO resumeCustom) {
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkEdit_%d' class='aLinkEdit' onclick='editResumeCustom(%d)'>编辑</a>",
						resumeCustom.getId(), resumeCustom.getId()));
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkDelete_%d' class='aLinkDelete ml6' onclick='deleteResumeCustom(%d)'>删除</a>",
						resumeCustom.getId(), resumeCustom.getId()));
		sb.append(String.format(
				"<input type='hidden' id='hobby_custom_detail_%d' name='%s' description='%s' value='%d' />",
				resumeCustom.getId(), resumeCustom.getName(), resumeCustom.getDescription(), resumeCustom.getId()));
		sb.append(String.format("<span>自定义板块名称：%s</span><br />", resumeCustom.getName()));
		sb.append(String.format("<span>自定义板块描述：%s</span><br />", resumeCustom.getDescription()));
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除自定义板块经历
	 * 
	 * @param resumeCustomId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeCustom", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeCustom(Integer resumeCustomId, Integer resumeId) {
		logger.info("execute deleteResumeCustom action for id: " + resumeCustomId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int count = resumeCustomService.deleteResumeCustom(resumeCustomId);
			int resumeCustomSize = resumeCustomService.queryResumeCustoms(resumeId).size();
			logger.info("delete " + count + " item for id: " + resumeCustomId);
			resultMap.put("success", true);
			resultMap.put("resumeCustomSize", resumeCustomSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存所获奖励
	 * 
	 * @param resumePrizeInfoId
	 * @param resumeId
	 * @param resumePrizeName
	 * @param resumeCustomDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumePrizeInfo", method = RequestMethod.POST)
	public Map<String, Object> saveResumePrizeInfo(Integer resumePrizeInfoId, Integer resumeId, String resumePrizeName,
			Integer resumePrizeLevel, String resumeGainTime) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumePrizeInfoId = resumePrizeInfoId == null ? 0 : resumePrizeInfoId;
		try {
			logger.info("execute saveResumePrizeInfo action...");
			ResumePrizeInfoDTO resumePrizeInfo = null;
			if (resumePrizeInfoId > 0) {
				// 更新
				resumePrizeInfo = resumePrizeInfoService.queryResumePrizeInfo(resumePrizeInfoId);
			} else {
				// 添加
				resumePrizeInfo = new ResumePrizeInfoDTO();
			}

			resumePrizeInfo.setResumeId(resumeId);
			resumePrizeInfo.setPrizeName(resumePrizeName);
			resumePrizeInfo.setPrizeLevel(resumePrizeLevel);
			try {
				Date gainTime = DateUtils.parseDate(resumeGainTime, CommonUtils.YYYY_MM_DD_DATA);
				resumePrizeInfo.setGainTime(gainTime);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumePrizeInfoId > 0) {
				count = resumePrizeInfoService.updateResumePrizeInfo(resumePrizeInfo);
				logger.info("update " + count + " item for id: " + resumePrizeInfo.getId());

				appendHtmlContent(sb, resumePrizeInfo);
				html = sb.toString();

			} else {
				count = resumePrizeInfoService.insertResumePrizeInfo(resumePrizeInfo);
				logger.info("insert " + count + " item for id: " + resumePrizeInfo.getId());

				sb = new StringBuilder(String.format("<div id='hobby_prizeinfo_%d' class='div_boder'>",
						resumePrizeInfo.getId()));
				appendHtmlContent(sb, resumePrizeInfo);
				sb.append("</div>");
				html = sb.toString();

			}

			logger.info("html: " + html);
			int resumePrizeInfoSize = resumePrizeInfoService.queryResumePrizeInfos(resumeId).size();
			ResumePrizeInfoDTO result = count > 0 ? resumePrizeInfo : resumePrizeInfoService
					.queryResumePrizeInfo(resumePrizeInfoId);
			resultMap.put("success", true);
			resultMap.put("resumePrizeInfo", result);
			resultMap.put("resumePrizeInfoHtml", html);
			resultMap.put("resumePrizeInfoSize", resumePrizeInfoSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumePrizeInfo", new ResumeCustomDTO());
			resultMap.put("resumePrizeInfoHtml", "");
			resultMap.put("resumePrizeInfoSize", 0);
		}

		return resultMap;
	}

	/**
	 * 添加html内容
	 * 
	 * @param sb
	 * @param resumeEducationExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumePrizeInfoDTO resumePrizeInfo) {
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkEdit_%d' class='aLinkEdit' onclick='editResumePrizeInfo(%d)'>编辑</a>",
						resumePrizeInfo.getId(), resumePrizeInfo.getId()));
		sb.append(String
				.format("<a href='javascript:void(0)' id='aLinkDelete_%d' class='aLinkDelete ml6' onclick='deletePrizeInfo(%d)'>删除</a>",
						resumePrizeInfo.getId(), resumePrizeInfo.getId()));
		sb.append(String
				.format("<input type='hidden' id='hobby_prizeinfo_detail_%d' prizeName='%s' prizeLevel='%d' prizeLevelDesc='%s' gainTimeDesc='%s' value='%d' />",
						resumePrizeInfo.getId(), resumePrizeInfo.getPrizeName(), resumePrizeInfo.getPrizeLevel(),
						resumePrizeInfo.getPrizeLevelDesc(), resumePrizeInfo.getGainTimeDesc(), resumePrizeInfo.getId()));
		sb.append(String.format("<span>奖项名称：%s</span><br />", resumePrizeInfo.getPrizeName()));
		sb.append(String.format("<span>奖项级别：%s</span><br />", resumePrizeInfo.getPrizeLevelDesc()));
		sb.append(String.format("<span>获奖时间：%s</span><br />", resumePrizeInfo.getGainTimeDesc()));
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除所获奖励经历
	 * 
	 * @param resumePrizeInfoId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePrizeInfo", method = RequestMethod.POST)
	public Map<String, Object> deletePrizeInfo(Integer resumePrizeInfoId, Integer resumeId) {
		logger.info("execute deletePrizeInfo action for id: " + resumePrizeInfoId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int count = resumePrizeInfoService.deleteResumePrizeInfo(resumePrizeInfoId);
			int resumePrizeInfoSize = resumePrizeInfoService.queryResumePrizeInfos(resumeId).size();
			logger.info("delete " + count + " item for id: " + resumePrizeInfoId);
			resultMap.put("success", true);
			resultMap.put("resumePrizeInfoSize", resumePrizeInfoSize);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存作品上传文件
	 * 
	 * @param file
	 * @param hdEditId
	 * @param resumeId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadOpusFile", method = RequestMethod.POST)
	public Map<String, Object> uploadOpusFile(@RequestParam("file") CommonsMultipartFile file, Integer hdEditId,
			Integer resumeId, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		hdEditId = hdEditId == null ? 0 : hdEditId;
		resumeId = resumeId == null ? 0 : resumeId;
		boolean flag = false;
		String message = StringUtils.EMPTY;
		String html = StringUtils.EMPTY;

		// 上传文件后编辑该记录opusUrl
		logger.info(String.format("hdEditId: %d, filename: %s", hdEditId, file.getOriginalFilename()));
		String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		logger.info("fileName: " + fileName + ", fileExt: " + fileExt);

		fileName = String.format("opus_%d_%d_%s.%s", hdEditId, resumeId, fileName, fileExt);

		List<String> validSuffixList = Arrays.asList(new String[] { "zip", "rar" });

		if (!validSuffixList.contains(fileExt)) {
			message = "上传的格式不正确，上传文件必须为rar或者zip";
			resultMap.put("success", flag);
			resultMap.put("message", message);
			return resultMap;

		} else if (file.getSize() > MAX_SIZE) {
			message = "上传的文件大小限制为5M，请重新上传";
			resultMap.put("success", flag);
			resultMap.put("message", message);
			return resultMap;

		} else {
			try {
				Response response = QiniuUtils.uploadFile(file.getBytes(), fileName, QiniuUtils.getToken(fileName));
				logger.info("bodyString: " + response.bodyString());
			} catch (QiniuException e) {
				logger.error(e.getMessage(), e);
			}

			String opusPath = String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, fileName);
			int count = 0;
			ResumeOpusInfoDTO resumeOpusInfo = resumeOpusInfoService.queryResumeOpusInfo(hdEditId);
			if (resumeOpusInfo != null) {
				resumeOpusInfo.setOpusPath(opusPath);
				count = resumeOpusInfoService.updateResumeOpusInfo(resumeOpusInfo);
				logger.info("update " + count + " opus for id: " + hdEditId);
				StringBuilder sb = new StringBuilder();
				appendHtmlContent(sb, resumeOpusInfo, fileName);
				html = sb.toString();
			}

			flag = true;
			resultMap.put("success", flag);
			resultMap.put("message", message);
			resultMap.put("resumeOpusInfoHtml", html);
		}

		return resultMap;
	}

	/**
	 * 保存作品
	 * 
	 * @param file
	 *            上传的作品
	 * @param resumeOpusInfoId
	 *            作品id
	 * @param resumeId
	 *            简历id
	 * @param resumeOpusInfoName
	 *            作品名称
	 * @param resumeOpusInfoUrl
	 *            作品url
	 * @return 保存响应作品
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeOpusInfo", method = RequestMethod.POST)
	public Map<String, Object> saveResumeOpusInfo(Integer resumeOpusInfoId, Integer resumeId,
			String resumeOpusInfoName, String resumeOpusInfoUrl) {
		Map<String, Object> resultMap = new HashMap<>();
		resumeId = resumeId == null ? 0 : resumeId;
		resumeOpusInfoId = resumeOpusInfoId == null ? 0 : resumeOpusInfoId;
		try {
			logger.info("execute saveResumeOpusInfo action...");
			ResumeOpusInfoDTO resumeOpusInfo = null;
			if (resumeOpusInfoId > 0) {
				// 更新
				resumeOpusInfo = resumeOpusInfoService.queryResumeOpusInfo(resumeOpusInfoId);
			} else {
				// 添加
				resumeOpusInfo = new ResumeOpusInfoDTO();
			}

			resumeOpusInfo.setResumeId(resumeId);
			resumeOpusInfo.setOpusName(resumeOpusInfoName);
			resumeOpusInfo.setOpusUrl(resumeOpusInfoUrl);

			StringBuilder sb = new StringBuilder();
			String html = "";
			int count = 0;
			if (resumeOpusInfoId > 0) {
				count = resumeOpusInfoService.updateResumeOpusInfo(resumeOpusInfo);
				logger.info("update " + count + " item for id: " + resumeOpusInfo.getId());

				appendHtmlContent(sb, resumeOpusInfo);
				html = sb.toString();

			} else {
				count = resumeOpusInfoService.insertResumeOpusInfo(resumeOpusInfo);
				logger.info("insert " + count + " item for id: " + resumeOpusInfo.getId());

				sb = new StringBuilder(String.format("<div id='opusinfo_%d' class='div_boder' val='%d'>",
						resumeOpusInfo.getId(), resumeOpusInfo.getId()));
				appendHtmlContent(sb, resumeOpusInfo);
				sb.append("</div>");
				html = sb.toString();

			}

			logger.info("html: " + html);
			int resumeOpusInfoSize = resumeOpusInfoService.queryResumeOpusInfos(resumeId).size();
			ResumeOpusInfoDTO result = count > 0 ? resumeOpusInfo : resumeOpusInfoService
					.queryResumeOpusInfo(resumeOpusInfoId);
			resultMap.put("success", true);
			resultMap.put("resumeOpusInfo", result);
			resultMap.put("resumeOpusInfoHtml", html);
			resultMap.put("resumeOpusInfoSize", resumeOpusInfoSize);

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("resumeOpusInfo", new ResumeCustomDTO());
			resultMap.put("resumeOpusInfoHtml", "");
			resultMap.put("resumeOpusInfoSize", 0);
		}

		return resultMap;
	}

	/**
	 * 添加作品用html
	 * 
	 * @param sb
	 * @param resumeOpusInfo
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeOpusInfoDTO resumeOpusInfo) {
		appendHtmlContent(sb, resumeOpusInfo, null);
	}

	/**
	 * 添加html内容
	 * 
	 * @param sb
	 * @param resumeEducationExp
	 */
	private void appendHtmlContent(StringBuilder sb, ResumeOpusInfoDTO resumeOpusInfo, String fileName) {
		sb.append(String.format("<a href='javascript:void(0)' id='aLinkEdit_%d' "
				+ "class='aLinkEdit' onclick='editResumeOpusInfo(%d)'>编辑</a>", resumeOpusInfo.getId(),
				resumeOpusInfo.getId()));
		sb.append(String.format("<a href='javascript:void(0)' id='aLinkDelete_%d' "
				+ "class='aLinkDelete ml6' onclick='deleteResumeOpusInfo(%d)'>删除</a>", resumeOpusInfo.getId(),
				resumeOpusInfo.getId()));
		sb.append(String
				.format("<input type='hidden' id='opusinfo_detail_%d' opusName='%s' opusUrl='%s' opusPath='%s' value='%d' opusPathFile='%s' />",
						resumeOpusInfo.getId(), resumeOpusInfo.getOpusName(), resumeOpusInfo.getOpusUrl(),
						resumeOpusInfo.getOpusPath(), resumeOpusInfo.getId(), resumeOpusInfo.getOpusPathFile()));
		sb.append(String.format("<span>作品名称：%s</span><br />", resumeOpusInfo.getOpusName()));
		sb.append(String.format("<span>作品链接：%s</span><br />", resumeOpusInfo.getOpusUrl()));

		if (!StringUtils.isBlank(fileName)) {
			sb.append(String.format("<span>作品下载：<a class=\"opusfile_path\" href=\"%s\">%s</a></span><br />",
					resumeOpusInfo.getOpusPath(), fileName));
		} else {
			sb.append("<div id=\"resumeOpusFileUpload\" class=\"upload opusfile_upload\">");
			sb.append("<form id=\"opusUploadForm\" enctype=\"multipart/form-data\" class=\"opusfile_upload_form uploadForm\"><div>");
			sb.append("<input type=\"file\" name=\"file\" id=\"resumeOpusUploadFile\" />");
			sb.append(String.format(
					"<input type=\"button\" id=\"btnOpusUpload\" onclick=\"uploadOpusFile(%d)\" value=\"上传\">",
					resumeOpusInfo.getId()));
			sb.append("<span>上传作品（大小不超过5M，压缩文件形式）</span>");
			sb.append("<div id=\"errorMsg\" class=\"opusfile_upload_errormsg\"></div>");
			sb.append("</div></form></div>");
		}
		sb.append("<hr style=\"width: auto; border-color: #9c9c9c;\" />");
	}

	/**
	 * 删除自定义板块经历
	 * 
	 * @param resumeOpusInfoId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeOpusInfo", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeOpusInfo(Integer resumeOpusInfoId, Integer resumeId, String opusFile,
			HttpServletRequest request) {
		logger.info("execute deleteResumeOpusInfo action for id: " + resumeOpusInfoId);
		Map<String, Object> resultMap = new HashMap<>();
		try {
			QiniuUtils.deleteFile(opusFile);
			int count = resumeOpusInfoService.deleteResumeOpusInfo(resumeOpusInfoId);
			int resumeOpusInfoSize = resumeOpusInfoService.queryResumeOpusInfos(resumeId, false).size();

			logger.info("delete " + count + " item for id: " + resumeOpusInfoId);
			resultMap.put("success", true);
			resultMap.put("resumeOpusInfoSize", resumeOpusInfoSize);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
		}

		return resultMap;
	}

	/**
	 * 保存社交网络
	 * 
	 * @param resumeId
	 * @param delIds
	 * @param editInfos
	 * @param addInfos
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeSocialNet", method = RequestMethod.POST)
	public Map<String, Object> saveResumeSocialNet(Integer resumeId, String delIds, String editInfos, String addInfos) {
		logger.info("execute saveResumeSocialNet action...");
		logger.info(String.format("resumeId: %d, delIds: %s, editInfos: %s, addInfos: %s", resumeId, delIds, editInfos,
				addInfos));

		// 批量删除社交网络
		List<Integer> deleteIdList = new ArrayList<Integer>();
		String[] delIdArr = StringUtils.split(delIds, ",");
		for (String delId : delIdArr) {
			deleteIdList.add(NumberUtils.toInt(delId));
		}
		resumeSocialNetService.batchDeleteResumeSocialNet(deleteIdList);

		// 批量更新社交网络
		String[] editInfoArr = StringUtils.split(editInfos, ",");
		int updateSocialNetId = 0;
		int updateSocialNetAccount = 0;
		String updateSocialNetUrl = "";
		ResumeSocialNetDTO resumeSocialNetDTO = new ResumeSocialNetDTO();
		List<ResumeSocialNetDTO> editList = new ArrayList<ResumeSocialNetDTO>();
		for (String editInfo : editInfoArr) {
			String[] item = StringUtils.split(editInfo, "-");
			updateSocialNetId = item.length >= 0 ? NumberUtils.toInt(item[0], 0) : 0;
			updateSocialNetAccount = item.length > 1 ? NumberUtils.toInt(item[1], 0) : 0;
			updateSocialNetUrl = item.length > 2 ? item[2] : "";
			resumeSocialNetDTO = resumeSocialNetService.queryResumeSocialNet(updateSocialNetId);
			if (resumeSocialNetDTO != null) {
				resumeSocialNetDTO.setAccount(updateSocialNetAccount);
				resumeSocialNetDTO.setUrl(updateSocialNetUrl);
				editList.add(resumeSocialNetDTO);
			}
		}
		resumeSocialNetService.batchUpdateResumeSocialNet(editList);

		// 批量插入社交网络
		String[] addInfoArr = StringUtils.split(addInfos, ",");
		int addSocialNetAccount = 0;
		String addSocialNetUrl = "";
		List<ResumeSocialNetDTO> list = new ArrayList<ResumeSocialNetDTO>();
		for (String addInfo : addInfoArr) {
			String[] item = StringUtils.split(addInfo, "-");
			addSocialNetAccount = item.length > 0 ? NumberUtils.toInt(item[0], 0) : 0;
			addSocialNetUrl = item.length > 1 ? item[1] : "";

			ResumeSocialNetDTO addResumeSocialNetDTO = new ResumeSocialNetDTO();
			addResumeSocialNetDTO.setResumeId(resumeId);
			addResumeSocialNetDTO.setAccount(addSocialNetAccount);
			addResumeSocialNetDTO.setUrl(addSocialNetUrl);

			list.add(addResumeSocialNetDTO);
		}
		resumeSocialNetService.batchInsertResumeSocialNet(list);

		List<ResumeSocialNetDTO> resultSocialNetDTOs = resumeSocialNetService.queryResumeSocialNets(resumeId);
		List<Integer> hdSocialNetEditIds = new ArrayList<Integer>();

		StringBuilder sbEditHtml = new StringBuilder();
		for (ResumeSocialNetDTO item : resultSocialNetDTOs) {
			hdSocialNetEditIds.add(item.getId());

			sbEditHtml.append(String.format("<li id=\"socialNetRow_%d\">", item.getId()));
			sbEditHtml
					.append("<img style=\"padding-left: 10px;\" src=\"images_zp/graduate.png\" alt=\"社交网络\"><select id=\"resumeSocialNetAccount\" name=\"resumeSocialNetAccount\" class=\"mn\">");
			for (SocialNetworkEnum socialNetworkEnum : SocialNetworkEnum.values()) {
				sbEditHtml.append(String.format("<option value=\"%d\" %s}>%s</option>", socialNetworkEnum.getCode(),
						(socialNetworkEnum.getCode() == item.getAccount() ? "selected=\"selected\"" : ""),
						socialNetworkEnum.getDesc()));
			}
			sbEditHtml.append("</select>");
			String tempHtml = String
					.format("<img src=\"images_zp/major.png\" alt=\"链接地址\"><input type=\"text\" placeholder=\"请输入链接，如：www.zhihu.com\" id=\"resumeSocialNetUrl\" name=\"resumeSocialNetUrl\" class=\"mn socialnet_url\" style=\"width: 270px;\" value=\"%s\" />",
							item.getUrl());
			sbEditHtml.append(tempHtml);
			String tempHtmlTail = String
					.format("<a style=\"display: inline; margin-left: 15px;\" href=\"javascript:void(0)\" "
							+ "class=\"aLinkDelete aLinkSnDelete\" onclick=\"deleteResumeSocialNet('%d');\">删除</a></li>",
							item.getId());
			sbEditHtml.append(tempHtmlTail);
		}

		StringBuilder sbViewHtml = new StringBuilder();

		for (ResumeSocialNetDTO item : resultSocialNetDTOs) {
			sbViewHtml.append(String.format("<div id=\"hobby_socialnet_%d\" class=\"div_boder\">", item.getId()));
			String tempHtml = String
					.format("<input type=\"hidden\" id=\"hobby_socialnet_detail_%d\" account=\"%d\" accountDesc=\"%s\" url=\"%s\" value=\"%d\" />",
							item.getId(), item.getAccount(), item.getAccountDesc(), item.getUrl(), item.getId());
			sbViewHtml.append(tempHtml);
			sbViewHtml.append(String.format("<span>社交网络账号：%s</span><br />", item.getAccountDesc()));
			sbViewHtml.append(String.format(
					"<span>链接：%s</span><br /> </span><hr style=\"width: auto; border-color: #9c9c9c;\" /></div>",
					item.getUrl()));
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("resumeSocialNetEditHtml", sbEditHtml.toString());
		resultMap.put("resumeSocialNetViewHtml", sbViewHtml.toString());
		resultMap.put("hdSocialNetEditIds", StringUtils.join(hdSocialNetEditIds, ","));
		resultMap.put("resumeSocialNetSize", resultSocialNetDTOs.size());

		return resultMap;
	}

	/**
	 * 保存英语技能
	 * 
	 * @param resumeId
	 * @param delIds
	 * @param editInfos
	 * @param addInfos
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeSpecialtySkillEn", method = RequestMethod.POST)
	public Map<String, Object> saveResumeSpecialtySkillEn(Integer resumeId, String infoArgs) {
		logger.info("execute saveResumeSocialNet action...");
		logger.info(String.format("resumeId: %d, infos: %s", resumeId, infoArgs));
		List<String> updateSpecialtySkillEnList = new ArrayList<String>();

		// 批量更新英语技能
		String[] infoArr = StringUtils.split(infoArgs, ",");
		int updateSpecialtySkillEnName = 0;
		String updateSpecialtySkillEnScore = "";
		for (String info : infoArr) {
			String[] item = StringUtils.split(info, "-");
			updateSpecialtySkillEnName = item.length > 0 ? NumberUtils.toInt(item[0], 0) : 0;
			updateSpecialtySkillEnScore = item.length > 1 ? item[1] : "";
			updateSpecialtySkillEnList.add(String.format("%d-%s", updateSpecialtySkillEnName,
					updateSpecialtySkillEnScore));
		}

		// 更新英语技能
		String updateStr = StringUtils.join(updateSpecialtySkillEnList, ",");
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySkillService
				.queryResumeSpecialtySkillByResumeId(resumeId);
		if (!StringUtils.isBlank(infoArgs)) {
			if (resumeSpecialtySkillDTO != null) {
				resumeSpecialtySkillDTO.setEnLangSkill(updateStr);
				int count = resumeSpecialtySkillService.updateResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("update " + count + " record...");
			} else {
				resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
				resumeSpecialtySkillDTO.setResumeId(resumeId);
				resumeSpecialtySkillDTO.setEnLangSkill(updateStr);
				int count = resumeSpecialtySkillService.insertResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("insert " + count + " record...");
				resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkillByResumeId(resumeId);
			}
		} else {
			resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
		}

		if (resumeSpecialtySkillDTO != null && resumeSpecialtySkillDTO.getId() > 0) {
			resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkillByResumeId(resumeId);
		}
		List<EnLangSkillDTO> enLangSkillDTOs = resumeSpecialtySkillDTO.getEnLangSkillDTOs();

		StringBuilder sbEditHtml = new StringBuilder();
		int editIndex = 0;
		for (EnLangSkillDTO item : enLangSkillDTOs) {
			sbEditHtml.append(String.format("<tr id=\"specialtySkillEnRow_%d\" class=\"spsken\">", editIndex));
			String tempHtml1 = "<td class=\"wd250\"><img src=\"images_zp/graduate.png\" alt=\"毕业年份\"><select id=\"resumeSpecialtySkillEnName\" name=\"resumeSpecialtySkillEnName\" "
					+ "class=\"mn specialtyskill_en_select\">";
			sbEditHtml.append(tempHtml1);
			for (EnglishSkillEnum englishSkillEnum : EnglishSkillEnum.values()) {
				sbEditHtml.append(String.format("<option value=\"%d\" %s}>%s</option>", englishSkillEnum.getCode(),
						(englishSkillEnum.getCode() == item.getName() ? "selected=\"selected\"" : ""),
						englishSkillEnum.getDesc()));
			}
			sbEditHtml.append("</select></td>");
			String tempHtml2 = String
					.format("<td class=\"wd235\"><img src=\"images_zp/major.png\" alt=\"专业\"><input type=\"text\" placeholder=\"请输入考试成绩\" id=\"resumeSpecialtySkillEnScore\" "
							+ "name=\"resumeSpecialtySkillEnScore\" value=\"%s\" "
							+ "class=\"mn specialtyskill_en_txt\" /></td>", item.getScore());
			sbEditHtml.append(tempHtml2);
			String tempHtml3 = String.format("<td class=\"wd50\"><a href=\"javascript:;\" "
					+ " class=\"aLinkDelete aLinkSpSkillEnDel\" onclick=\"deleteResumeSpecialtySkillEn('%d');\">"
					+ " <img src=\"images_zp/close_16.png\" width=\"16\" height=\"16\" alt=\"删除\" /></a></td></tr>",
					editIndex);
			sbEditHtml.append(tempHtml3);
			editIndex++;
		}

		int viewIndex = 0;
		StringBuilder sbViewHtml = new StringBuilder();
		String editBtnHtml = "<a href=\"javascript:;\" class=\"aLinkEdit\" onclick=\"editResumeSpecialtySkillEn()\">"
				+ "<img src=\"images_zp/pencil (2).png\" width=\"16\" height=\"16\" alt=\"编辑\"></a>";
		sbViewHtml.append(editBtnHtml);
		for (EnLangSkillDTO item : enLangSkillDTOs) {
			sbViewHtml.append(String.format("<div id=\"specialtyskill_en_%d\" class=\"div_boder\">", viewIndex));
			String tempHtml = String.format("<input type=\"hidden\" id=\"specialtyskill_en_detail_%d\" "
					+ "id=\"%d\" name=\"%d\" nameDesc=\"%s\" score=\"%s\" value=\"%d\" />", viewIndex, viewIndex,
					item.getName(), item.getNameDesc(), item.getScore(), viewIndex);
			sbViewHtml.append(tempHtml);
			sbViewHtml.append(String.format("<span>英语考试名称：%s</span><br />", item.getNameDesc()));
			sbViewHtml.append(String.format("<span>考试成绩：%s</span><br /></div>", item.getScore()));
			viewIndex++;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("resumeSpecialtySkillEnEditHtml", sbEditHtml.toString());
		resultMap.put("resumeSpecialtySkillEnViewHtml", sbViewHtml.toString());
		resultMap.put("resumeSpecialtySkillEnSize", enLangSkillDTOs.size());

		return resultMap;
	}

	/**
	 * 保存其他语言技能
	 * 
	 * @param resumeId
	 * @param infoArgs
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeSpecialtySkillOther", method = RequestMethod.POST)
	public Map<String, Object> saveResumeSpecialtySkillOther(Integer resumeId, String infoArgs) {
		logger.info("execute saveResumeSpecialtySkillOther action...");
		logger.info(String.format("resumeId: %d, infos: %s", resumeId, infoArgs));
		List<String> updateSpecialtySkillOtherList = new ArrayList<String>();

		// 批量英语技能
		String[] infoArr = StringUtils.split(infoArgs, ",");
		int updateSpecialtySkillOtherName = 0;
		String updateSpecialtySkillOtherLevel = "";
		for (String info : infoArr) {
			String[] item = StringUtils.split(info, "-");
			updateSpecialtySkillOtherName = item.length > 0 ? NumberUtils.toInt(item[0], 0) : 0;
			updateSpecialtySkillOtherLevel = item.length > 1 ? item[1] : "";
			updateSpecialtySkillOtherList.add(String.format("%d-%s", updateSpecialtySkillOtherName,
					updateSpecialtySkillOtherLevel));
		}

		// 更新其他语言技能
		String updateStr = StringUtils.join(updateSpecialtySkillOtherList, ",");
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySkillService
				.queryResumeSpecialtySkillByResumeId(resumeId);
		if (!StringUtils.isBlank(infoArgs)) {
			if (resumeSpecialtySkillDTO != null) {
				resumeSpecialtySkillDTO.setOtherLangSkill(updateStr);
				int count = resumeSpecialtySkillService.updateResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("update " + count + " record...");
			} else {
				resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
				resumeSpecialtySkillDTO.setResumeId(resumeId);
				resumeSpecialtySkillDTO.setOtherLangSkill(updateStr);
				int count = resumeSpecialtySkillService.insertResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("insert " + count + " record...");
			}
		} else {
			resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
		}

		if (resumeSpecialtySkillDTO != null && resumeSpecialtySkillDTO.getId() > 0) {
			resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkillByResumeId(resumeId);
		}
		List<OtherLangSkillDTO> otherLangSkillDTOs = resumeSpecialtySkillDTO.getOtherLangSkillDTOs();

		StringBuilder sbEditHtml = new StringBuilder();
		int editIndex = 0;
		for (OtherLangSkillDTO item : otherLangSkillDTOs) {
			sbEditHtml.append(String.format("<tr id=\"specialtySkillOtherRow_%d\" class=\"spskother\">", editIndex));
			sbEditHtml
					.append("<td class=\"wd250\"><img src=\"images_zp/graduate.png\" alt=\"毕业年份\"><select id=\"resumeSpecialtySkillOtherName\" name=\"resumeSpecialtySkillOtherName\" "
							+ "class=\"mn specialtyskill_other_select\">");
			for (OtherLanguageSkillEnum otherLanguageSkillEnum : OtherLanguageSkillEnum.values()) {
				sbEditHtml.append(String.format("<option value=\"%d\" %s}>%s</option>", otherLanguageSkillEnum
						.getCode(),
						(otherLanguageSkillEnum.getCode() == item.getName() ? "selected=\"selected\"" : ""),
						otherLanguageSkillEnum.getDesc()));
			}
			sbEditHtml.append("</select></td>");
			String tempHtml = String
					.format("<td class=\"wd235\"><img src=\"images_zp/major.png\" alt=\"掌握程度\" /><input type=\"text\" id=\"resumeSpecialtySkillOtherLevel\" name=\"resumeSpecialtySkillOtherLevel\" placeholder=\"掌握程度\" "
							+ "value=\"%s\" class=\"mn specialtyskill_other_txt\" /></td>", item.getLevel());
			sbEditHtml.append(tempHtml);
			String delBtnHtml = String
					.format("<td class=\"wd50\"><a href=\"javascript:;\" class=\"aLinkDelete aLinkSpSkillOtherDel\" "
							+ "onclick=\"deleteResumeSpecialtySkillOther('%d');\"><img src=\"images_zp/close_16.png\" width=\"16\" height=\"16\" "
							+ "alt=\"删除\" /></a></td></tr>", editIndex);
			sbEditHtml.append(delBtnHtml);
			editIndex++;
		}

		int viewIndex = 0;
		StringBuilder sbViewHtml = new StringBuilder();
		String editBtnHtml = "<a href=\"javascript:;\" class=\"aLinkEdit\" onclick=\"editResumeSpecialtySkillOther()\"><img src=\"images_zp/pencil (2).png\" width=\"16\" height=\"16\" alt=\"编辑\"></a>";
		sbViewHtml.append(editBtnHtml);
		for (OtherLangSkillDTO item : otherLangSkillDTOs) {
			sbViewHtml.append(String.format("<div id=\"specialtyskill_other_%d\" class=\"div_boder\">", viewIndex));
			String tempHtml = String.format("<input type=\"hidden\" id=\"specialtyskill_other_detail_%d\" "
					+ "id=\"%d\" name=\"%d\" nameDesc=\"%s\" level=\"%s\" value=\"%d\" />", viewIndex, viewIndex,
					item.getName(), item.getNameDesc(), item.getLevel(), viewIndex);
			sbViewHtml.append(tempHtml);
			sbViewHtml.append(String.format("<span>其他语言名称：%s</span><br />", item.getNameDesc()));
			sbViewHtml.append(String.format("<span>其他语言掌握程度：%s</span><br /> </span></div>", item.getLevel()));
			viewIndex++;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("resumeSpecialtySkillOtherEditHtml", sbEditHtml.toString());
		resultMap.put("resumeSpecialtySkillOtherViewHtml", sbViewHtml.toString());
		resultMap.put("resumeSpecialtySkillOtherSize", otherLangSkillDTOs.size());

		return resultMap;
	}

	/**
	 * 保存计算机技能
	 * 
	 * @param resumeId
	 * @param infoArgs
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeSpecialtySkillComputer", method = RequestMethod.POST)
	public Map<String, Object> saveResumeSpecialtySkillComputer(Integer resumeId, String infoArgs) {
		logger.info("execute saveResumeSpecialtySkillComputer action...");
		logger.info(String.format("resumeId: %d, infos: %s", resumeId, infoArgs));
		List<String> updateSpecialtySkillComputerList = new ArrayList<String>();
		Map<String, List<ComputerSkillEnum>> computerSkillMap = getComputerSkillMap();

		// 批量计算机技能
		String[] infoArr = StringUtils.split(infoArgs, ",");
		int updateSpecialtySkillCpSkill = 0;
		String updateSpecialtySkillCpSkillLevel = "";
		for (String info : infoArr) {
			String[] item = StringUtils.split(info, "-");
			updateSpecialtySkillCpSkill = item.length > 0 ? NumberUtils.toInt(item[0], 0) : 0;
			updateSpecialtySkillCpSkillLevel = item.length > 1 ? item[1] : "";
			updateSpecialtySkillComputerList.add(String.format("%d-%s", updateSpecialtySkillCpSkill,
					updateSpecialtySkillCpSkillLevel));
		}

		// 更新计算机技能
		String updateStr = StringUtils.join(updateSpecialtySkillComputerList, ",");
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySkillService
				.queryResumeSpecialtySkillByResumeId(resumeId);
		if (!StringUtils.isBlank(infoArgs)) {
			if (resumeSpecialtySkillDTO != null) {
				resumeSpecialtySkillDTO.setComputerSkill(updateStr);
				int count = resumeSpecialtySkillService.updateResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("update " + count + " record...");
			} else {
				resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
				resumeSpecialtySkillDTO.setResumeId(resumeId);
				resumeSpecialtySkillDTO.setComputerSkill(updateStr);
				int count = resumeSpecialtySkillService.insertResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("insert " + count + " record...");
			}
		} else {
			resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
		}

		if (resumeSpecialtySkillDTO != null && resumeSpecialtySkillDTO.getId() > 0) {
			resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkillByResumeId(resumeId);
		}
		List<ComputerSkillDTO> computerSkillDTOs = resumeSpecialtySkillDTO.getComputerSkillDTOs();

		StringBuilder sbEditHtml = new StringBuilder();
		int editIndex = 0;
		for (ComputerSkillDTO item : computerSkillDTOs) {
			sbEditHtml.append(String.format("<tr id=\"specialtySkillComputerRow_%d\" class=\"spskcomputer\">",
					editIndex));
			sbEditHtml.append("<td class=\"wd250\"><img src=\"images_zp/graduate.png\" alt=\"计算机技能\">"
					+ "<select id=\"resumeSpecialtySkillComputerSkill\" name=\"resumeSpecialtySkillComputerSkill\" "
					+ "class=\"mn specialtyskill_computer_select\">");

			for (Entry<String, List<ComputerSkillEnum>> kv : computerSkillMap.entrySet()) {
				sbEditHtml.append(String.format("<optgroup label=\"%s\">", kv.getKey()));
				for (ComputerSkillEnum computerSkillEnum : kv.getValue()) {
					sbEditHtml.append(String.format("<option value=\"%d\" %s>%s</option>", computerSkillEnum.getCode(),
							(computerSkillEnum.getCode() == item.getSkill() ? "selected=selected" : ""),
							computerSkillEnum.getDesc()));
				}
				sbEditHtml.append("</optgroup>");
			}

			sbEditHtml.append("</select></td>");
			String tempHtml = "<td class=\"wd235\"><select id=\"resumeSpecialtySkillComputerSkillLevel\" name=\"resumeSpecialtySkillComputerSkillLevel\" class=\"mn specialtyskill_computer_select\">";
			sbEditHtml.append(tempHtml);
			for (SkillLevelEnum computerSkillEnum : SkillLevelEnum.values()) {
				sbEditHtml.append(String.format("<option value=\"%d\" %s>%s</option>", computerSkillEnum.getCode(),
						(computerSkillEnum.getCode() == item.getSkillLevel() ? "selected=selected" : ""),
						computerSkillEnum.getDesc()));
			}
			sbEditHtml.append("</select><td class=\"wd50\">");
			sbEditHtml.append(String.format("<a href=\"javascript:;\" class=\"aLinkDelete aLinkSpSkillComputerDel\" "
					+ "onclick=\"deleteResumeSpecialtySkillComputer('%d');\">删除</a></td></tr>", editIndex));
			editIndex++;
		}

		int viewIndex = 0;
		StringBuilder sbViewHtml = new StringBuilder();
		String editBtnHtml = "<a href=\"javascript:;\" class=\"aLinkEdit\" onclick=\"editResumeSpecialtySkillComputer()\"><img src=\"images_zp/pencil (2).png\" width=\"16\" height=\"16\" alt=\"编辑\"></a>";
		sbViewHtml.append(editBtnHtml);
		for (ComputerSkillDTO item : computerSkillDTOs) {
			sbViewHtml.append(String.format("<div id=\"specialtyskill_computer_%d\" class=\"div_boder\">", viewIndex));
			String tempHtml = String
					.format("<input type=\"hidden\" id=\"specialtyskill_computer_detail_%d\" "
							+ "id=\"%d\" skill=\"%d\" skillDesc=\"%s\" skillLevel=\"%d\" skillLevelDesc=\"%s\" value=\"%d\" />",
							viewIndex, item.getId(), item.getSkill(), item.getSkillDesc(), item.getSkillLevel(),
							item.getSkillLevelDesc(), viewIndex);
			sbViewHtml.append(tempHtml);
			sbViewHtml.append(String.format("<span>计算机技能：%s</span><br />", item.getSkillDesc()));
			sbViewHtml.append(String.format("<span>计算机熟练程度说明：%s</span><br /> </span></div>", item.getSkillLevelDesc()));
			viewIndex++;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("resumeSpecialtySkillComputerEditHtml", sbEditHtml.toString());
		resultMap.put("resumeSpecialtySkillComputerViewHtml", sbViewHtml.toString());
		resultMap.put("resumeSpecialtySkillComputerSize", computerSkillDTOs.size());

		return resultMap;
	}

	/**
	 * 获取生成的计算机技能Html
	 * 
	 * @return
	 */
	private String getComputerDropdown() {
		StringBuilder sb = new StringBuilder();
		Map<String, List<ComputerSkillEnum>> computerSkillMap = getComputerSkillMap();
		for (Entry<String, List<ComputerSkillEnum>> kv : computerSkillMap.entrySet()) {
			sb.append(String.format("<optgroup label=\"%s\">", kv.getKey()));
			for (ComputerSkillEnum computerSkillEnum : kv.getValue()) {
				sb.append(String.format("<option value=\"%d\">%s</option>", computerSkillEnum.getCode(),
						computerSkillEnum.getDesc()));
			}
			sb.append("</optgroup>");
		}

		return sb.toString();
	}

	/**
	 * 获取计算机技能名称
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getComputerSkillDropdownHtml", method = RequestMethod.GET)
	public Map<String, Object> getComputerSkillDropdownHtml() {
		String html = getComputerDropdown();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		resultMap.put("getComputerSkillDropdownHtml", html);
		return resultMap;
	}

	/**
	 * 专业技能-证书
	 * 
	 * @param resumeId
	 * @param infoArgs
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveResumeSpecialtySkillCert", method = RequestMethod.POST)
	public Map<String, Object> saveResumeSpecialtySkillCert(Integer resumeId, String infoArgs) {
		logger.info("execute saveResumeSpecialtySkillCert action...");
		logger.info(String.format("resumeId: %d, infos: %s", resumeId, infoArgs));
		List<String> updateSpecialtySkillCertList = new ArrayList<String>();

		// 批量证书
		String[] infoArr = StringUtils.split(infoArgs, ",");
		String updateSpecialtySkillCertName = "";
		for (String info : infoArr) {
			String[] item = StringUtils.split(info, "-");
			updateSpecialtySkillCertName = item.length > 0 ? item[0] : "";
			updateSpecialtySkillCertList.add(updateSpecialtySkillCertName);
		}

		// 更新证书
		String updateStr = StringUtils.join(updateSpecialtySkillCertList, ",");
		ResumeSpecialtySkillDTO resumeSpecialtySkillDTO = resumeSpecialtySkillService
				.queryResumeSpecialtySkillByResumeId(resumeId);
		if (!StringUtils.isBlank(infoArgs)) {
			if (resumeSpecialtySkillDTO != null) {
				resumeSpecialtySkillDTO.setCertificate(updateStr);
				int count = resumeSpecialtySkillService.updateResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("update " + count + " record...");
			} else {
				resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
				resumeSpecialtySkillDTO.setResumeId(resumeId);
				resumeSpecialtySkillDTO.setCertificate(updateStr);
				int count = resumeSpecialtySkillService.insertResumeSpecialtySkill(resumeSpecialtySkillDTO);
				logger.info("insert " + count + " record...");
			}
		} else {
			resumeSpecialtySkillDTO = new ResumeSpecialtySkillDTO();
		}

		if (resumeSpecialtySkillDTO != null && resumeSpecialtySkillDTO.getId() > 0) {
			resumeSpecialtySkillDTO = resumeSpecialtySkillService.queryResumeSpecialtySkillByResumeId(resumeId);
		}
		List<CertificateDTO> certificateDTOs = resumeSpecialtySkillDTO.getCertificateDTOs();

		StringBuilder sbEditHtml = new StringBuilder();
		int editIndex = 0;
		for (CertificateDTO item : certificateDTOs) {
			sbEditHtml.append(String.format("<tr id=\"specialtySkillCertRow_%d\" class=\"spskcert\">", editIndex));
			String tempHtml = String
					.format("<td class=\"wd250\"><img src=\"images_zp/major.png\" alt=\"证书名称\" />"
							+ "<input type=\"text\" id=\"resumeSpecialtySkillCertName\" name=\"resumeSpecialtySkillCertName\" placeholder=\"证书名称\" "
							+ "value=\"%s\" class=\"mn specialtyskill_cert_txt\" /></td>", item.getDiploma());
			sbEditHtml.append(tempHtml);
			sbEditHtml
					.append(String
							.format("<td class=\"wd35\"><a href=\"javascript:;\" class=\"aLinkDelete aLinkSpSkillCertDel\" "
									+ "onclick=\"deleteResumeSpecialtySkillCert('%d');\"><img src=\"images_zp/close_16.png\" width=\"16\" height=\"16\" alt=\"删除\" />"
									+ "</a></td></tr>", editIndex));
			editIndex++;
		}

		int viewIndex = 0;
		StringBuilder sbViewHtml = new StringBuilder();
		String editBtnHtml = "<a href=\"javascript:;\" class=\"aLinkEdit\" onclick=\"editResumeSpecialtySkillCert()\"><img src=\"images_zp/pencil (2).png\" width=\"16\" height=\"16\" alt=\"编辑\"></a>";
		sbViewHtml.append(editBtnHtml);
		for (CertificateDTO item : certificateDTOs) {
			sbViewHtml.append(String.format("<div id=\"specialtyskill_cert_%d\" class=\"div_boder\">", viewIndex));
			String tempHtml = String.format("<input type=\"hidden\" id=\"specialtyskill_cert_detail_%d\" "
					+ "id=\"%d\" diploma=\"%s\" value=\"%d\" />", viewIndex, viewIndex, item.getDiploma(), viewIndex);
			sbViewHtml.append(tempHtml);
			sbViewHtml.append(String.format("<span>证书名称：%s</span><br /></div>", item.getDiploma()));
			viewIndex++;
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("resumeSpecialtySkillCertEditHtml", sbEditHtml.toString());
		resultMap.put("resumeSpecialtySkillCertViewHtml", sbViewHtml.toString());
		resultMap.put("resumeSpecialtySkillCertSize", certificateDTOs.size());

		return resultMap;
	}

	/**
	 * 发送简历
	 * 
	 * @param memberId
	 * @param recruitId
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendResumeInfo", method = RequestMethod.POST)
	public Map<String, Object> sendResumeInfo(String memberId, Integer recruitId, Integer resumeId) {
		logger.info(String.format("memberId: %s, recruitId: %d, resumeId: %d", memberId, recruitId, resumeId));
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("memberId", memberId);
			queryMap.put("recruitId", recruitId);
			queryMap.put("resumeId", resumeId);

			int count = memberRecruitService.deleteMemberRecruitByCondition(queryMap);
			logger.info("delete " + count + " record for member_recruit_info");

			MemberRecruitDTO memberRecruitDTO = new MemberRecruitDTO();
			memberRecruitDTO.setMemberId(memberId);
			memberRecruitDTO.setRecruitId(recruitId);
			memberRecruitDTO.setResumeId(resumeId);
			memberRecruitDTO.setApplyType(ApplyTypeEnum.Apply.getCode());
			memberRecruitDTO.setFeedStatus(FeedStatusEnum.NonViewed.getCode());

			int count2 = memberRecruitService.insertMemberRecruit(memberRecruitDTO);
			logger.info("insert " + count2 + " record for member_recruit_info");

			resultMap.put("success", true);
			resultMap.put("message", "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", true);
			resultMap.put("message", "");
		}

		return resultMap;
	}

	/**
	 * 搜索全职的职位列表
	 * 
	 * @param filterApplyProgress
	 * @param filterIndustries
	 * @param filterGeoareas
	 * @param filterMajor
	 * @param filterDiploma
	 * @param filterSalary
	 * @param filterFavorite
	 * @param filterLastested
	 * @param filterSearchtxt
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchJobList", method = RequestMethod.POST)
	public Map<String, Object> searchJobList(String memberId, Integer filterInternshipDays,
			Integer filterApplyProgress, String filterIndustries, String filterGeoareas, Integer filterMajor,
			Integer filterDiploma, Integer filterSalary, Boolean filterFavorite, Boolean filterLastested,
			String filterSearchtxt, Integer filterType, Integer pageStart) {

		memberId = StringUtils.isBlank(memberId) ? "" : memberId;
		filterInternshipDays = filterInternshipDays == null ? 0 : filterInternshipDays;
		filterApplyProgress = filterApplyProgress == null ? 0 : filterApplyProgress;
		filterIndustries = filterIndustries == null ? "" : filterIndustries;
		filterGeoareas = filterGeoareas == null ? "" : filterGeoareas;
		filterMajor = filterMajor == null ? 0 : filterMajor;
		filterDiploma = filterDiploma == null ? 0 : filterDiploma;
		filterSalary = filterSalary == null ? 0 : filterSalary;
		filterFavorite = filterFavorite == null ? false : filterFavorite;
		filterLastested = filterLastested == null ? false : filterLastested;
		filterSearchtxt = StringUtils.isBlank(filterSearchtxt) ? "" : filterSearchtxt;
		filterType = filterType == null ? 0 : filterType;
		pageStart = pageStart == null ? 1 : pageStart;
		int from = pageStart - 1;

		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("execute searchResult action");
		String searchCondition = String
				.format("memberId: %s, filterInternshipDays: %d, filterApplyProgress: %d, filterIndustries: %s, filterGeoareas: %s,"
						+ " filterMajor: %d, filterDiploma: %d, filterSalary: %d, filterFavorite: %s, filterLastested: %s, "
						+ " filterSearchtxt: %s", memberId, filterInternshipDays, filterApplyProgress,
						filterIndustries, filterGeoareas, filterMajor, filterDiploma, filterSalary, filterFavorite,
						filterLastested, filterSearchtxt);
		logger.info("search condition: " + searchCondition);
		int total = 0;

		List<RecruitInfoDTO> ls = new ArrayList<RecruitInfoDTO>();

		if (filterType == JobTypeEnum.FullTime.getCode()) {

			FilterRecruitInfoCondition condition = new FilterRecruitInfoCondition();
			condition.setApplyProgress(filterApplyProgress);
			condition.setIndustries(filterIndustries);
			condition.setAddressCities(filterGeoareas);
			condition.setMajor(filterMajor);
			condition.setDiploma(filterDiploma);
			condition.setDemandSalary(filterSalary);
			condition.setMyFavorite(filterFavorite);
			condition.setLatested(filterLastested);
			condition.setSearchTxt(filterSearchtxt);
			condition.setFrom(from * PAGE_SIZE);
			condition.setSize(PAGE_SIZE);

			ls = fulltimeSearchService.searchRecruitInfoWithConditions(condition);
			total = condition.getTotal();
			if (ls.size() == 0) {
				condition.setFrom(0);
				condition.setSize(Integer.MAX_VALUE);
				ls = fulltimeSearchService.searchRecruitInfoWithConditions(condition);
			}
		} else {

			FilterRecruitInfoCondition condition = new FilterRecruitInfoCondition();
			condition.setInternshipDays(filterInternshipDays);
			condition.setIndustries(filterIndustries);
			condition.setAddressCities(filterGeoareas);
			condition.setDiploma(filterDiploma);
			condition.setMyFavorite(filterFavorite);
			condition.setLatested(filterLastested);
			condition.setSearchTxt(filterSearchtxt);
			condition.setFrom(from * PAGE_SIZE);
			condition.setSize(PAGE_SIZE);

			ls = internshipSearchService.searchRecruitInfoWithConditions(condition);
			total = condition.getTotal();

			if (ls.size() == 0) {
				condition.setFrom(0);
				condition.setSize(Integer.MAX_VALUE);
				ls = internshipSearchService.searchRecruitInfoWithConditions(condition);
			}
		}

		String resultHtml = drawRecruitInfoHtml(ls, memberId, filterType);
		String paginateHtml = CommonUtils.generateSearchPnHtml(total, pageStart);

		try {
			resultMap.put("resultHtml", resultHtml);
			resultMap.put("paginateHtml", ls.size() == 0 ? "" : paginateHtml);
			resultMap.put(SUCCESS, true);
			resultMap.put(MESSAGE, true);
		} catch (Exception e) {
			resultMap.put("resultHtml", "");
			resultMap.put("paginateHtml", "");
			resultMap.put(SUCCESS, false);
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 重绘职位HTML
	 * 
	 * @param ls
	 * @param memberId
	 * @param filterType
	 * @return
	 */
	public String drawRecruitInfoHtml(List<RecruitInfoDTO> ls, String memberId, Integer filterType) {
		String liCss = filterType == JobTypeEnum.FullTime.getCode() ? "stuJLF_line1" : "stuJLI_line1";
		String imgCss = filterType == JobTypeEnum.FullTime.getCode() ? "stuJLF_duitang" : "stuJLI_duitang";
		String divCss = filterType == JobTypeEnum.FullTime.getCode() ? "stuJLF_jobInfo" : "stuJLI_jobInfo";
		String subliCss = filterType == JobTypeEnum.FullTime.getCode() ? "stuJLF_condition" : "stuJLI_condition";
		String viewBtnCss = filterType == JobTypeEnum.FullTime.getCode() ? "stuJLF_checkOut" : "stuJLI_checkOut";
		String favoriteBtnCss = filterType == JobTypeEnum.FullTime.getCode() ? "stuJLF_collect" : "stuJLI_collect";

		List<Integer> memberFavoriteRecruitIds = new ArrayList<Integer>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("memberId", memberId);
		queryMap.put("applyType", ApplyTypeEnum.Favorite.getCode());
		List<MemberRecruitDTO> memberRecruitDTOs = memberRecruitService.queryMemberRecruits(queryMap, false);
		for (MemberRecruitDTO memberRecruitDTO : memberRecruitDTOs) {
			memberFavoriteRecruitIds.add(memberRecruitDTO.getRecruitId());
		}

		StringBuilder sb = new StringBuilder();
		CompanyInfoDTO company = null;
		for (RecruitInfoDTO item : ls) {
			company = item.getCompanyInfoDTO();
			sb.append(String.format("<li class=\"%s mt6\">", liCss));
			sb.append(String.format("<img src=\"%s\" alt=\"%s\" class=\"%s img_style\" />", company == null ? ""
					: company.getLogo(), company == null ? "" : company.getName(), imgCss));
			sb.append(String.format("<div class=\"%s\">", divCss));
			sb.append(String.format("<ul><li><h5>%s</h5><h5>%s</h5>%s发布</li>",
					company == null ? "" : company.getName(), item.getPositionName(), item.getCreateTimeDisplay()));
			sb.append(String.format("<li class=\"%s\">", subliCss));
			sb.append(String.format("<span><img src=\"images_zp/flag_1.png\" alt=\"位置\" />%s</span>", item
					.getWorkCityDTO().getCity()));
			sb.append(String.format("<span><img src=\"images_zp/money_yen.png\" alt=\"月薪\" />%s</span>",
					item.getSalaryDesc()));
			sb.append(String.format("<span><img src=\"images_zp/world.png\" alt=\"互联网\" />%s</span><br/>",
					item.getPositionTypeDesc()));
			for (JobBenefitsEnum jobBenefitsEnum : item.getImportantRemarkList()) {
				sb.append(String.format("<span class=\"wa\">%s</span>", jobBenefitsEnum.getDesc()));
			}
			sb.append("<br />");
			sb.append(String.format("<a target=\"_blank\" href=\"recruit/job_view?id=%s&rid=%d\" class=\"%s\">查看</a>",
					memberId, item.getId(), viewBtnCss));

			if (memberFavoriteRecruitIds.contains(item.getId())) {
				sb.append(String.format("<a href=\"javascript:;\" onclick=\"favoriteJob(%d,0)\""
						+ " class=\"%s\">取消收藏</a></li>", item.getId(), favoriteBtnCss));
			} else {
				sb.append(String.format("<a href=\"javascript:;\" onclick=\"favoriteJob(%d,1)\""
						+ " class=\"%s\">收藏</a></li>", item.getId(), favoriteBtnCss));
			}
			sb.append("</ul></div></li>");
		}

		return sb.toString();
	}

	/**
	 * 收藏职位
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/favoriteJob", method = RequestMethod.POST)
	public Map<String, Object> favoriteJob(String memberId, Integer recruitId, Integer type) {
		logger.info(String.format("memberId: %s, recruitId: %d, type: %d", memberId, recruitId, type));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 1-收藏，0-取消收藏
			if (type == 1) {
				MemberRecruitDTO memberRecruitDTO = new MemberRecruitDTO();
				memberRecruitDTO.setMemberId(memberId);
				memberRecruitDTO.setRecruitId(recruitId);
				memberRecruitDTO.setApplyType(ApplyTypeEnum.Favorite.getCode());

				int count = memberRecruitService.insertMemberRecruit(memberRecruitDTO);
				logger.info("insert " + count + " member_recruit record...");
			} else {
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("memberId", memberId);
				queryMap.put("recruitId", recruitId);
				queryMap.put("applyType", ApplyTypeEnum.Favorite.getCode());

				MemberRecruitDTO memberRecruitDTO = memberRecruitService.queryMemberRecruitByConditions(queryMap);
				if (memberRecruitDTO != null) {
					int count = memberRecruitService.deleteMemberRecruit(memberRecruitDTO.getId());
					logger.info("delete " + count + " member_recruit record...");
				}
			}

			resultMap.put(SUCCESS, true);
			resultMap.put(MESSAGE, "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put(SUCCESS, false);
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 删除简历附件
	 * 
	 * @param resumeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResumeAttachment", method = RequestMethod.POST)
	public Map<String, Object> deleteResumeAttachment(Integer resumeId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resumeId = resumeId == null ? 0 : resumeId;
		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
		if (resumeInfoDTO != null) {
			String key = resumeInfoDTO.getUploadPathFile();
			QiniuUtils.deleteFile(key);

			resumeInfoDTO.setUploadPath(StringUtils.EMPTY);
			int count = resumeInfoService.updateResumeInfo(resumeInfoDTO);
			logger.info("delete " + count + " resumeattachment for id: " + resumeId);

		}
		resultMap.put(SUCCESS, true);
		resultMap.put(MESSAGE, "");
		return resultMap;
	}

	/**
	 * 上传简历附件
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadResumeAttachment", method = RequestMethod.POST)
	public Map<String, Object> uploadResumeAttachment(@RequestParam("resumeAttachment") CommonsMultipartFile file,
			Integer resumeId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		logger.info("fileName: " + fileName + ", fileExt: " + fileExt + ", fileSize: " + file.getSize());

		String uploadName = String.format("rs_attachment_%d_%s.%s", resumeId, fileName, fileExt);

		String message = "";
		boolean flag = false;
		List<String> validSuffixList = Arrays.asList(new String[] { "jpg", "doc", "docx", "pdf", "txt" });
		if (!validSuffixList.contains(fileExt)) {
			message = "简历附件格式不正确，附件文件必须为以下几种类型（jpg，doc，docx，pdf，txt）";
			resultMap.put(SUCCESS, flag);
			resultMap.put(MESSAGE, message);
			return resultMap;

		} else if (file.getSize() > MAX_SIZE) {
			message = "简历附件文件大小限制为5M，请重新上传";
			resultMap.put(SUCCESS, flag);
			resultMap.put(MESSAGE, message);
			return resultMap;

		} else {
			try {
				Response response = QiniuUtils.uploadFile(file.getBytes(), uploadName, QiniuUtils.getToken(uploadName));
				logger.info("bodyString: " + response.bodyString());
				flag = true;
			} catch (QiniuException e) {
				logger.error(e.getMessage(), e);
			}
			String attachmentUrl = String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, uploadName);
			ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
			if (resumeInfoDTO != null) {
				resumeInfoDTO.setUploadPath(attachmentUrl);
				int count = resumeInfoService.updateResumeInfo(resumeInfoDTO);
				logger.info("update resume attachement path, count: " + count + ", resumeId: " + resumeId);
			}

			resultMap.put(SUCCESS, flag);
			resultMap.put(MESSAGE, message);
			resultMap.put("attachmentUrl", attachmentUrl);
			resultMap.put("uploadName", uploadName);
		}

		return resultMap;
	}

	/**
	 * 批量下载简历
	 */
	@ResponseBody
	@RequestMapping(value = "/batchDownloadResume", method = { RequestMethod.POST, RequestMethod.GET })
	public void batchDownloadResume(HttpServletRequest request, HttpServletResponse response, String ids) {
		logger.info("execute batchDownloadResume start action...");
		ids = StringUtils.isBlank(ids) ? "" : ids;
		ids = StringUtils.replace(ids, "_", ",");
		String path = request.getServletContext().getRealPath("/");
		logger.info("request contextPath: " + path);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("ids", ids);

		String downloadFilepath = "";
		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos(queryMap);
		List<String> downloadFilenameLs = new ArrayList<String>();
		List<File> downloadFileLs = new ArrayList<File>();

		for (ResumeInfoDTO item : resumeInfoDTOs) {
			downloadFilepath = path + File.separator + TEMPLATE + File.separator + item.getResumeName() + DOCX_SUFFIX;
			downloadFilenameLs.add(downloadFilepath);
			String downloadPath = path + File.separator + TEMPLATE + File.separator;

			try {
				VelocityUtils.initVmData(velocityContext, item);
				VelocityUtils.createDoc(velocityContext, downloadPath, VelocityUtils.VM_TEMPLATE, downloadFilepath);

				File file = new File(downloadFilepath);
				if (file.exists()) {
					downloadFileLs.add(file);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		try {
			String zipName = "简历集合_" + System.currentTimeMillis() + ".zip";
			zipName = new String(zipName.getBytes(GB2312), ISO8859_1);
			// 将 文件流写入到前端浏览器
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + zipName);
			try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
				zipFile(downloadFileLs, "", zipOut);
				zipOut.flush();

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("execute batchDownloadResume end action...");
	}

	/**
	 * 压缩文件
	 * 
	 * @param subs
	 * @param baseName
	 * @param zos
	 * @throws IOException
	 */
	private void zipFile(List<File> subs, String baseName, ZipOutputStream zos) throws IOException {
		for (File file : subs) {
			File f = file;
			zos.putNextEntry(new ZipEntry(baseName + f.getName()));
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			int r = 0;
			while ((r = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, r);
			}
			fis.close();
		}
	}

	/**
	 * 下载简历
	 * 
	 * @param request
	 * @param response
	 * @param resumeId
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadResume", method = { RequestMethod.POST, RequestMethod.GET })
	public void downloadResume(HttpServletRequest request, HttpServletResponse response, Integer resumeId) {
		logger.info("execute downloadResume action...");
		ResumeInfoDTO resumeInfoDTO = resumeInfoService.queryResumeInfo(resumeId);
		String path = request.getServletContext().getRealPath("/");
		logger.info("request contextPath: " + path);
		String downloadFilepath = "";
		if (resumeInfoDTO != null) {
			downloadFilepath = path + File.separator + TEMPLATE + File.separator + resumeInfoDTO.getResumeName()
					+ DOCX_SUFFIX;
			String downloadPath = path + File.separator + TEMPLATE + File.separator;

			try {
				VelocityUtils.initVmData(velocityContext, resumeInfoDTO);
				VelocityUtils.createDoc(velocityContext, downloadPath, VelocityUtils.VM_TEMPLATE, downloadFilepath);
				try (FileInputStream fis = new FileInputStream(downloadFilepath)) {
					byte[] dataBytes = new byte[fis.available()];
					fis.read(dataBytes);

					String fileName = resumeInfoDTO.getResumeName() + DOCX_SUFFIX;
					fileName = new String(fileName.getBytes(GB2312), ISO8859_1);
					// 将 文件流写入到前端浏览器
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Content-disposition", "attachment;filename=" + fileName);
					ServletOutputStream sops = response.getOutputStream();
					sops.write(dataBytes);

					// 删除生成的临时文件
					File file = new File(downloadFilepath);
					if (file.exists()) {
						file.delete();
						logger.info("delete temp file...");
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	}
}
