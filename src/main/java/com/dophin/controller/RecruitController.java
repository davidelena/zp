package com.dophin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dophin.dto.CompanyInfoDTO;
import com.dophin.dto.FilterMyApplicantCondition;
import com.dophin.dto.FilterResumeInfoCondition;
import com.dophin.dto.GeoAreaDTO;
import com.dophin.dto.IndustryDTO;
import com.dophin.dto.InformInterviewDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.MemberRecruitDTO;
import com.dophin.dto.MyApplicantDTO;
import com.dophin.dto.PositionTypeDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.dto.RecruitResumeCountDTO;
import com.dophin.dto.ResumeInfoDTO;
import com.dophin.dto.UniversityDTO;
import com.dophin.dto.ValidateDTO;
import com.dophin.enums.AchievementEnum;
import com.dophin.enums.CompanySizeEnum;
import com.dophin.enums.ComputerSkillEnum;
import com.dophin.enums.EducationEnum;
import com.dophin.enums.EnglishSkillEnum;
import com.dophin.enums.GenderEnum;
import com.dophin.enums.InternshipDaysEnum;
import com.dophin.enums.InternshipExpEnum;
import com.dophin.enums.JobBenefitsEnum;
import com.dophin.enums.MemberSourceEnum;
import com.dophin.enums.OtherLanguageSkillEnum;
import com.dophin.enums.PositionEducationEnum;
import com.dophin.enums.PositionSchoolTypeEnum;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.enums.RecruitDemandEnum;
import com.dophin.enums.RecruitEmailTypeEnum;
import com.dophin.enums.SchoolActivityEnum;
import com.dophin.enums.SkillLevelEnum;
import com.dophin.enums.SpecialityEnum;
import com.dophin.service.CompanyMemberService;
import com.dophin.service.ConstantsService;
import com.dophin.service.MemberRecruitService;
import com.dophin.service.MemberService;
import com.dophin.service.RecruitInfoService;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.biz.CompanyBusinessService;
import com.dophin.service.biz.SearchResumeInfoService;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.QiniuUtils;
import com.dophin.utils.QrCodeUtils;
import com.qiniu.http.Response;

/**
 * 招聘类
 * 
 * @author dailiwei
 * 
 */
@Controller
@RequestMapping(value = "/recruit")
public class RecruitController extends BaseController {

	private static final String HTTP = "http://";

	private static final String DEFAULT = "default";

	private static final String NO_APPLICANT = "noapplicant";

	private static final String NO_PUBLISH_JOB = "nopublishjob";

	private static SimpleDateFormat sdf = new SimpleDateFormat(CommonUtils.YYYY_MM_DD_CHINESE);

	private static Logger logger = Logger.getLogger(RecruitController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private ConstantsService constantsService;

	@Autowired
	private CompanyMemberService companyMemberService;

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Autowired
	private MemberRecruitService memberRecruitService;

	@Autowired
	private CompanyBusinessService companyBusinessService;

	private SearchResumeInfoService searchResumeInfoService = new SearchResumeInfoService();

	private static final int PAGE_SIZE = 5;

	private static final String COMPANY_NAME_ERROR_ALERT = "请输入公司名称";
	private static final String COMPANY_INDUSTRY_ERROR_ALERT = "请选择公司行业";
	private static final String COMPANY_HEADQUARTERS_ERROR_ALERT = "请选择公司总部城市";
	private static final String COMPANY_SCALE_ERROR_ALERT = "请选择公司规模";
	private static final String COMPANY_SYNOPSIS_ERROR_ALERT = "请填写公司简介";
	private static final String SALARY_ERROR_ALERT = "请输入月薪（最低与最高范围都需要）";
	private static final String MEMBER_CONTACT_ERROR_ALERT = "必选填一项联系方式（手机或固定电话）";

	/**
	 * 上传Logo至七牛服务器
	 * 
	 * @param newFile
	 * @param memberId
	 * @param companyId
	 * @param filename
	 */
	private String uploadFileToQiniu(File newFile, String memberId, Integer companyId, String filename) {
		String qiniuFilename = String.format("logo_cp%d-%s", companyId, filename);
		// 上传过说明有更新文件用最新的更新文件
		if (newFile.exists()) {
			qiniuFilename = String.format("logo_cp%d-%s", companyId, filename);
			try (FileInputStream fis = new FileInputStream(newFile)) {
				byte[] fileBytes = new byte[fis.available()];
				fis.read(fileBytes);
				Response response = QiniuUtils.uploadFile(fileBytes, qiniuFilename, QiniuUtils.getToken(qiniuFilename));
				logger.info("bodyString: " + response.bodyString());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return qiniuFilename;
	}

	/**
	 * 填写公司信息第一步
	 * 
	 * @param memberId
	 * @param companyId
	 * @param companyName
	 * @param hdCompanyIndustry
	 * @param hdCompanyHeaderQuartersId
	 * @param companyHeaderQuarters
	 * @param companyOfficialWebSite
	 * @param companyScale
	 * @param companySynopsis
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCompanyBasicInfoStep1", method = RequestMethod.POST)
	public Map<String, Object> saveCompanyBasicInfoStep1(String memberId, Integer companyId, String companyName,
			Integer hdCompanyIndustry, Integer hdCompanyHeaderQuartersId, String companyHeaderQuarters,
			String companyDetailAddress, String companyOfficialWebSite, Integer companyScale, String companySynopsis,
			String filename, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		CompanyInfoDTO companyInfo = null;
		hdCompanyIndustry = hdCompanyIndustry == null ? 0 : hdCompanyIndustry;
		hdCompanyHeaderQuartersId = hdCompanyHeaderQuartersId == null ? 0 : hdCompanyHeaderQuartersId;
		companyScale = companyScale == null ? 0 : companyScale;

		List<ValidateDTO> ls = new ArrayList<ValidateDTO>();
		if (StringUtils.isEmpty(companyName)) {
			ls.add(new ValidateDTO("companyName-error", COMPANY_NAME_ERROR_ALERT));
		}
		if (hdCompanyIndustry == 0) {
			ls.add(new ValidateDTO("companyIndustry-error", COMPANY_INDUSTRY_ERROR_ALERT));
		}
		if (hdCompanyHeaderQuartersId == 0) {
			ls.add(new ValidateDTO("companyHeaderQuarters-error", COMPANY_HEADQUARTERS_ERROR_ALERT));
		}
		if (companyScale == 0) {
			ls.add(new ValidateDTO("companyScale-error", COMPANY_SCALE_ERROR_ALERT));
		}
		if (StringUtils.isEmpty(companySynopsis)) {
			ls.add(new ValidateDTO("companySynopsis-error", COMPANY_SYNOPSIS_ERROR_ALERT));
		}

		if (ls.size() > 0) {
			resultMap.put("success", false);
			resultMap.put("msgdata", ls);
		} else {
			try {
				String path = request.getServletContext().getRealPath("/");
				logger.info("request path: " + path);
				String clientIp = getRemoteHost(request);
				clientIp = StringUtils.replace(clientIp, ".", "_");

				String parentPath = path + File.separator + "companylogo";
				logger.info("parentPath: " + parentPath);
				// 命名方式logo_m{memberid}_cp{companyid}_{filename}.xxx
				String newFileName = String.format("logo_cp%s-%s", clientIp, filename);

				// 将文件名称更新到对应的company信息中
				String newFilepath = parentPath + File.separator + newFileName;
				File newFile = new File(newFilepath);
				if (companyId > 0) {
					String qiniuFilename = uploadFileToQiniu(newFile, memberId, companyId, filename);

					companyInfo = companyMemberService.queryCompanyInfo(companyId);
					companyInfo.setName(companyName);
					companyInfo.setIndustry(hdCompanyIndustry);
					companyInfo.setHeaderQuartersId(hdCompanyHeaderQuartersId);
					GeoAreaDTO geoAreaDTO = constantsService.queryGeoArea(hdCompanyHeaderQuartersId);
					companyInfo.setHeaderQuarters(geoAreaDTO.getName());
					companyInfo.setDetailAddress(companyDetailAddress);
					companyInfo.setOfficialWebsite(companyOfficialWebSite);
					companyInfo.setScale(companyScale);
					companyInfo.setSynopsis(companySynopsis);
					companyInfo.setLogo(String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, qiniuFilename));

					int count = companyMemberService.updateCompanyInfo(companyInfo);
					logger.info(String.format("update %d companyinfo for id: %d", count, companyId));

				} else {
					companyInfo = new CompanyInfoDTO();
					companyInfo.setMemberId(memberId);
					companyInfo.setName(companyName);
					companyInfo.setIndustry(hdCompanyIndustry);
					companyInfo.setHeaderQuartersId(hdCompanyHeaderQuartersId);
					GeoAreaDTO geoAreaDTO = constantsService.queryGeoArea(hdCompanyHeaderQuartersId);
					companyInfo.setHeaderQuarters(geoAreaDTO.getName());
					companyInfo.setDetailAddress(companyDetailAddress);
					companyInfo.setOfficialWebsite(companyOfficialWebSite);
					companyInfo.setScale(companyScale);
					companyInfo.setSynopsis(companySynopsis);

					int cCount = companyMemberService.insertCompanyInfo(companyInfo);
					logger.info(String.format("insert %d companyinfo for id: %d", cCount, companyInfo.getId()));

					String qiniuFilename = uploadFileToQiniu(newFile, memberId, companyInfo.getId(), filename);
					// 更新公司Logo路径
					CompanyInfoDTO companyInfoDTO = companyMemberService.queryCompanyInfo(companyInfo.getId());
					if (companyInfoDTO != null) {
						companyInfoDTO.setLogo(String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, qiniuFilename));
						int count = companyMemberService.updateCompanyInfo(companyInfoDTO);
						logger.info("upload " + count + " companylogo for newid: " + companyId);
					}
				}

				MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
				memberDTO.setCompanyId(companyInfo.getId());
				int mCount = memberService.updateMemberInfo(memberDTO);
				logger.info(String.format("update %d companyid in memberinfo", mCount));

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			resultMap.put("success", true);
			resultMap.put("msgdata", new ArrayList<ValidateDTO>());
		}

		return resultMap;
	}

	/**
	 * 填写公司信息第二步
	 * 
	 * @param memberId
	 * @param companyId
	 * @param companyProduct
	 * @param companyAchievements
	 * @param companyWeibo
	 * @param companySeniorExecutiveDesc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCompanyBasicInfoStep2", method = RequestMethod.POST)
	public Map<String, Object> saveCompanyBasicInfoStep2(String memberId, Integer companyId, String companyProduct,
			String companyAchievements, String companyWeibo, String companySeniorExecutiveDesc) {
		CompanyInfoDTO companyInfo = companyMemberService.queryCompanyInfo(companyId);

		if (companyInfo != null && companyInfo.getId() > 0) {
			companyInfo.setProduct(companyProduct);
			companyInfo.setAchievements(companyAchievements);
			companyInfo.setWeibo(companyWeibo);
			companyInfo.setSeniorExecutiveDesc(companySeniorExecutiveDesc.trim());

			int count = companyMemberService.updateCompanyInfo(companyInfo);
			logger.info(String.format("update %d companyinfo for id: %d", count, companyId));
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		return resultMap;
	}

	/**
	 * 填写公司信息第三步（个人信息）
	 * 
	 * @param memberId
	 * @param memberName
	 * @param hdMemberSex
	 * @param memberTelephone
	 * @param memberMobile
	 * @param memberDemand
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCompanyMemberInfo", method = RequestMethod.POST)
	public Map<String, Object> saveCompanyMemberInfo(String memberId, String memberName, String memberCommonEmail,
			Integer hdMemberSex, String memberTelephone, String memberMobile, String positionName, Integer memberDemand) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (StringUtils.isBlank(memberTelephone) && StringUtils.isBlank(memberMobile)) {
			resultMap.put("success", false);
			resultMap.put("message", MEMBER_CONTACT_ERROR_ALERT);
		} else {
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			if (memberDTO != null) {
				memberDTO.setName(memberName);
				memberDTO.setSex(hdMemberSex);
				memberDTO.setTelephone(memberTelephone);
				memberDTO.setMobile(memberMobile);
				memberDTO.setCommonEmail(memberCommonEmail);
				memberDTO.setPosition(positionName);
				memberDTO.setHasFillBasic(true);
				memberDTO.setDemand(memberDemand);

				int count = memberService.updateMemberInfo(memberDTO);
				logger.info(String.format("update %d memberinfo for id: %s", count, memberId));
			}
			resultMap.put("success", true);
			resultMap.put("message", "");
		}

		return resultMap;
	}

	/**
	 * 企业基本信息step1
	 * 
	 * @param id
	 *            企业账户ID
	 * @return 企业基本信息step1
	 */
	@RequestMapping(value = "/company_info_step1", method = RequestMethod.GET)
	public ModelAndView companyInfoStep1(String id) {
		logger.info("execute company_info_step1 view...");
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		CompanyInfoDTO companyInfoDTO = companyMemberService.queryCompanyInfo(memberDTO.getCompanyId());

		if (companyInfoDTO == null) {
			companyInfoDTO = new CompanyInfoDTO();
		}

		List<CompanySizeEnum> companySizeEnums = Arrays.asList(CompanySizeEnum.values());
		List<GeoAreaDTO> hotCities = getHotCities();
		List<GeoAreaDTO> otherCities = getOtherCities();
		Map<String, List<IndustryDTO>> industryMap = getIndustryMap();
		Map<String, List<GeoAreaDTO>> cityMap = getGeoAreaMap();

		boolean isFirstGuy = memberDTO.isFirst();

		String companyLogo = companyInfoDTO.getLogo();
		companyLogo = StringUtils.substringAfterLast(companyLogo, "-");

		ModelAndView mav = new ModelAndView("company_info_step1");
		mav.addObject("companyInfo", companyInfoDTO);
		mav.addObject("memberId", id);
		mav.addObject("memberDTO", memberDTO);
		mav.addObject("companySizeEnums", companySizeEnums);
		mav.addObject("industryMap", industryMap);
		mav.addObject("cityMap", cityMap);
		mav.addObject("hotCities", hotCities);
		mav.addObject("otherCities", otherCities);
		mav.addObject("isFirst", isFirstGuy);
		mav.addObject("companyLogo", companyLogo);

		return mav;
	}

	/**
	 * 企业基本信息step2
	 * 
	 * @param id
	 *            企业账户id
	 * @return 企业基本信息step2
	 */
	@RequestMapping(value = "/company_info_step2", method = RequestMethod.GET)
	public ModelAndView companyInfoStep2(String id) {
		logger.info("execute company_info_step2 view...");

		// 一定要通过先插member=>companyid=>company的顺序，否则第二个注册用户是在company_info表里找不到相关记录的
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		CompanyInfoDTO companyInfoDTO = null;
		if (memberDTO != null) {
			companyInfoDTO = companyMemberService.queryCompanyInfo(memberDTO.getCompanyId());
		}

		if (companyInfoDTO == null) {
			companyInfoDTO = new CompanyInfoDTO();
		}

		boolean isFirstGuy = memberDTO.isFirst();

		ModelAndView mav = new ModelAndView("company_info_step2");
		mav.addObject("companyInfo", companyInfoDTO);
		mav.addObject("memberId", id);
		mav.addObject("isFirst", isFirstGuy);

		return mav;
	}

	/**
	 * 企业基本信息step3
	 * 
	 * @param id
	 *            企业账户id
	 * @return 企业基本信息step3
	 */
	@RequestMapping(value = "/company_member_info", method = RequestMethod.GET)
	public ModelAndView companyMemberInfo(String id) {
		logger.info("execute company_member_info view...");

		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		List<RecruitDemandEnum> recruitDemandEnums = Arrays.asList(RecruitDemandEnum.values());

		ModelAndView mav = new ModelAndView("company_member_info");
		mav.addObject("memberInfo", memberDTO);
		mav.addObject("recruitDemandEnums", recruitDemandEnums);

		return mav;
	}

	/**
	 * 上传企业Logo
	 * 
	 * @param file
	 *            文件上传logo
	 * @param companyId
	 *            公司id
	 * @param request
	 *            Http请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadCompanyLogo", method = RequestMethod.POST)
	public Map<String, Object> uploadCompanyLogo(@RequestParam("file") CommonsMultipartFile file, String logoMemberId,
			Integer logoCompanyId, HttpServletRequest request) {

		String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

		String path = request.getServletContext().getRealPath("/");
		logger.info("request path: " + path);
		String clientIp = getRemoteHost(request);
		clientIp = StringUtils.replace(clientIp, ".", "_");

		String parentPath = path + File.separator + "companylogo";
		logger.info("parentPath: " + parentPath);
		// 命名方式logo_m{memberid}_cp{companyid}_{filename}.xxx
		String newFileName = String.format("logo_cp%s-%s.%s", clientIp, fileName, fileExt);

		// 将文件名称更新到对应的company信息中
		String newFilepath = parentPath + File.separator + newFileName;
		File newFile = new File(newFilepath);
		try {
			file.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
			logger.error(e.getMessage(), e);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		resultMap.put("imgUrl", String.format("companylogo/%s", newFileName));
		resultMap.put("filename", file.getOriginalFilename());
		return resultMap;
	}

	/**
	 * 上传企业微信
	 * 
	 * @param file
	 *            文件
	 * @param wechatMemberId
	 *            企业账户ID
	 * @param wechatCompanyId
	 *            企业信息表ID
	 * @param request
	 * @return 刷新当前页面
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadCompanyWechat", method = RequestMethod.POST)
	public Map<String, Object> uploadCompanyWechat(@RequestParam("file") CommonsMultipartFile file,
			String wechatMemberId, Integer wechatCompanyId, HttpServletRequest request) {
		String fileName = StringUtils.substringBeforeLast(file.getOriginalFilename(), ".");
		String fileExt = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

		String path = request.getServletContext().getRealPath("/");
		logger.info("request path: " + path);

		String parentPath = path + File.separator + "companywechat";
		logger.info("parentPath: " + parentPath);
		// 命名方式wechat_m{memberid}_cp{companyid}_{filename}.xxx
		String newFileName = String.format("wechat_cp%d_%s.%s", wechatCompanyId, fileName, fileExt);

		try {
			Response response = QiniuUtils.uploadFile(file.getBytes(), newFileName, QiniuUtils.getToken(newFileName));
			logger.info("bodyString: " + response.bodyString());

			// 将文件名称更新到对应的company信息中
			CompanyInfoDTO companyInfo = companyMemberService.queryCompanyInfo(wechatCompanyId);
			if (companyInfo != null && companyInfo.getId() > 0) {
				companyInfo.setWechat(String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, newFileName));
				int count = companyMemberService.updateCompanyInfo(companyInfo);
				logger.info(String.format("update %d companyinfo for id: %d", count, wechatCompanyId));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		CompanyInfoDTO latestCompanyInfo = companyMemberService.queryCompanyInfo(wechatCompanyId);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		resultMap.put("imgUrl", latestCompanyInfo.getWechat());
		return resultMap;
	}

	/**
	 * 公司预览页面
	 * 
	 * @param id
	 *            公司ID
	 * @return 公司预览页面
	 */
	@RequestMapping(value = "/company_view", method = RequestMethod.GET)
	public ModelAndView companyView(String id, Integer cid) {
		logger.info(String.format("execute company_view view, id: %s, cid: %d", id, cid));
		id = id == null ? "" : id;
		cid = cid == null ? 0 : cid;

		MemberDTO memberInfo = memberService.queryMemberInfo(id);
		CompanyInfoDTO companyInfo = null;
		List<RecruitInfoDTO> recruitInfoDTOs = null;

		if (memberInfo != null) {
			if (memberInfo.getSource() == MemberSourceEnum.Enterprise.getCode()) {
				companyInfo = companyMemberService.queryCompanyInfo(memberInfo.getCompanyId());
			} else if (memberInfo.getSource() == MemberSourceEnum.Student.getCode()) {
				if (cid > 0) {
					companyInfo = companyMemberService.queryCompanyInfo(cid);
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("companyId", cid);
					queryMap.put("positionStatus", PositionStatusEnum.Online.getCode());
					queryMap.put("start", 0);
					queryMap.put("size", 5);

					recruitInfoDTOs = recruitInfoService.queryRecruitInfos(queryMap);
				} else {
					companyInfo = new CompanyInfoDTO();
					recruitInfoDTOs = new ArrayList<RecruitInfoDTO>();
				}
			}
		} else {
			memberInfo = new MemberDTO();
			companyInfo = new CompanyInfoDTO();
		}

		ModelAndView mav = new ModelAndView("company_view");
		mav.addObject("companyInfo", companyInfo);
		mav.addObject("memberInfo", memberInfo);
		mav.addObject("recruitInfos", recruitInfoDTOs);

		return mav;
	}

	/**
	 * 发布或编辑职位（基本信息）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/job_basic_info", method = RequestMethod.GET)
	public ModelAndView jobBasicInfo(String id, Integer rid) {
		logger.info("execute job_basic_info view...");
		logger.info(String.format("id: %s, rid: %d", id, rid));

		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(rid);
		if (recruitInfoDTO == null) {
			recruitInfoDTO = new RecruitInfoDTO();
		}

		List<RecruitEmailTypeEnum> recruitEmailTypeEnums = Arrays.asList(RecruitEmailTypeEnum.values());
		List<JobBenefitsEnum> jobBenefitsEnums = Arrays.asList(JobBenefitsEnum.values());
		Map<String, List<PositionTypeDTO>> positionTypeMap = getPositionTypeMap();
		Map<String, List<GeoAreaDTO>> cityMap = getGeoAreaMap();
		List<GeoAreaDTO> hotCities = getHotCities();
		List<GeoAreaDTO> otherCities = getOtherCities();

		ModelAndView mav = new ModelAndView("job_basic_info");
		mav.addObject("recruitInfo", recruitInfoDTO);
		mav.addObject("memberId", id);
		mav.addObject("recruitEmailTypeEnums", recruitEmailTypeEnums);
		mav.addObject("jobBenefitsEnums", jobBenefitsEnums);
		mav.addObject("positionTypeMap", positionTypeMap);
		mav.addObject("cityMap", cityMap);
		mav.addObject("hotCities", hotCities);
		mav.addObject("otherCities", otherCities);

		return mav;
	}

	/**
	 * 保存职位基本信息
	 * 
	 * @param hdRecruitId
	 * @param hdMemberId
	 * @param recruitType
	 * @param hdPositionType
	 * @param positionName
	 * @param departmentName
	 * @param postDuty
	 * @param hdWorkCity
	 * @param detailAddress
	 * @param minSalary
	 * @param maxSalary
	 * @param chkNegotiable
	 * @param hdImportantRemark
	 * @param needNum
	 * @param acceptEmail
	 * @param emailType
	 * @param validityTime
	 * @param saveType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveJobBasicInfo", method = RequestMethod.POST)
	public Map<String, Object> saveJobBasicInfo(Integer hdRecruitId, String hdMemberId, Integer recruitType,
			Integer hdPositionType, String positionName, String departmentName, String postDuty, Integer hdWorkCity,
			String detailAddress, Integer minSalary, Integer maxSalary, Integer chkNegotiable,
			String hdImportantRemark, Integer needNum, String acceptEmail, Integer emailType, String validityTime,
			Integer saveType) {

		logger.info("execute saveJobBasicInfo action...");
		logger.info("saveType: " + saveType);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		minSalary = minSalary == null ? -1 : minSalary;
		maxSalary = maxSalary == null ? -1 : maxSalary;

		if (minSalary < 0 || maxSalary < 0) {
			resultMap.put(SUCCESS, false);
			resultMap.put(MESSAGE, SALARY_ERROR_ALERT);
		} else {
			try {
				int recruitId = hdRecruitId == null ? 0 : hdRecruitId;
				hdMemberId = StringUtils.isBlank(hdMemberId) ? "" : hdMemberId;

				RecruitInfoDTO recruitInfoDTO = null;
				if (recruitId > 0) {
					recruitInfoDTO = recruitInfoService.queryRecruitInfo(recruitId);

					recruitInfoDTO.setMemberId(hdMemberId);
					MemberDTO memberDTO = memberService.queryMemberInfo(hdMemberId);
					if (memberDTO != null) {
						recruitInfoDTO.setCompanyId(memberDTO.getCompanyId());
					}
					recruitInfoDTO.setRecruitType(recruitType);
					recruitInfoDTO.setPositionType(hdPositionType);
					recruitInfoDTO.setPositionName(positionName);
					recruitInfoDTO.setDepartmentName(departmentName);
					recruitInfoDTO.setPostDuty(postDuty);
					if (hdWorkCity == null) {
						hdWorkCity = 0;
					}
					recruitInfoDTO.setWorkAddress(String.format("%d-%s", hdWorkCity, detailAddress));
					recruitInfoDTO.setMinSalary(minSalary);
					recruitInfoDTO.setMaxSalary(maxSalary);
					if (chkNegotiable == null) {
						chkNegotiable = 0;
					}
					recruitInfoDTO.setIsNegotiable(chkNegotiable == 1);
					recruitInfoDTO.setImportantRemark(hdImportantRemark);
					recruitInfoDTO.setNeedNum(needNum);
					recruitInfoDTO.setAcceptEmail(acceptEmail);
					recruitInfoDTO.setEmailType(emailType);
					recruitInfoDTO.setPositionStatus(PositionStatusEnum.Draft.getCode());
					try {
						Date vtDate = DateUtils.parseDate(validityTime, CommonUtils.YYYY_MM_DD_DATA);
						recruitInfoDTO.setValidityTime(vtDate);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}

					int count = recruitInfoService.updateRecruitInfo(recruitInfoDTO);
					logger.info(String.format("update %d recruitinfo for id: %d", count, recruitId));

				} else {
					recruitInfoDTO = new RecruitInfoDTO();
					recruitInfoDTO.setMemberId(hdMemberId);
					MemberDTO memberDTO = memberService.queryMemberInfo(hdMemberId);
					if (memberDTO != null) {
						recruitInfoDTO.setCompanyId(memberDTO.getCompanyId());
					}
					recruitInfoDTO.setRecruitType(recruitType);
					recruitInfoDTO.setPositionType(hdPositionType);
					recruitInfoDTO.setPositionName(positionName);
					recruitInfoDTO.setDepartmentName(departmentName);
					recruitInfoDTO.setPostDuty(postDuty);
					if (hdWorkCity == null) {
						hdWorkCity = 0;
					}
					recruitInfoDTO.setWorkAddress(String.format("%d-%s", hdWorkCity, detailAddress));
					recruitInfoDTO.setMinSalary(minSalary);
					recruitInfoDTO.setMaxSalary(maxSalary);
					if (chkNegotiable == null) {
						chkNegotiable = 0;
					}
					recruitInfoDTO.setIsNegotiable(chkNegotiable == 1);
					recruitInfoDTO.setImportantRemark(hdImportantRemark);
					recruitInfoDTO.setNeedNum(needNum);
					recruitInfoDTO.setAcceptEmail(acceptEmail);
					recruitInfoDTO.setEmailType(emailType);
					recruitInfoDTO.setPositionStatus(PositionStatusEnum.Draft.getCode());

					try {
						Date vtDate = DateUtils.parseDate(validityTime, CommonUtils.YYYY_MM_DD_DATA);
						recruitInfoDTO.setValidityTime(vtDate);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}

					int count = recruitInfoService.insertRecruitInfo(recruitInfoDTO);
					recruitId = recruitInfoDTO.getId();
					logger.info(String.format("insert %d recruitinfo for id: %d", count, recruitInfoDTO.getId()));
				}

				String redirectUrl = "";
				if (saveType == 1) {
					redirectUrl = String.format("recruit/my_joblist?id=%s&type=%d", hdMemberId,
							PositionStatusEnum.Draft.getCode());
				} else {
					redirectUrl = String.format("recruit/job_precise_orientation?id=%s&rid=%s", hdMemberId, recruitId);
				}

				resultMap.put(SUCCESS, true);
				resultMap.put(MESSAGE, "");
				resultMap.put("url", redirectUrl);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				resultMap.put(SUCCESS, false);
				resultMap.put("message", e.getMessage());
				resultMap.put("url", "");
			}
		}

		return resultMap;
	}

	/**
	 * 发布或编辑职位（精准定向职位要求）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/job_precise_orientation", method = RequestMethod.GET)
	public ModelAndView jobPreciseOrientation(String id, Integer rid) {
		logger.info("execute job_precise_orientation view...");
		logger.info(String.format("id: %s, rid: %d", id, rid));
		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(rid);
		if (recruitInfoDTO == null) {
			recruitInfoDTO = new RecruitInfoDTO();
		}

		List<PositionSchoolTypeEnum> positionSchoolTypeEnums = PositionSchoolTypeEnum.getNonC9List();
		List<PositionEducationEnum> positionEducationEnums = Arrays.asList(PositionEducationEnum.values());
		List<SpecialityEnum> specialityEnums = Arrays.asList(SpecialityEnum.values());
		List<AchievementEnum> achievementEnums = Arrays.asList(AchievementEnum.values());
		List<InternshipDaysEnum> internshipDaysEnums = Arrays.asList(InternshipDaysEnum.values());
		List<InternshipExpEnum> internshipExpEnums = Arrays.asList(InternshipExpEnum.values());
		List<SchoolActivityEnum> schoolActivityEnums = Arrays.asList(SchoolActivityEnum.values());
		List<SkillLevelEnum> skillLevelEnums = Arrays.asList(SkillLevelEnum.values());
		List<EnglishSkillEnum> englishSkillEnums = Arrays.asList(EnglishSkillEnum.values());
		List<OtherLanguageSkillEnum> otherLanguageSkillEnums = Arrays.asList(OtherLanguageSkillEnum.values());
		Map<String, List<ComputerSkillEnum>> computerSkillMap = getComputerSkillMap();

		ModelAndView mav = new ModelAndView("job_precise_orientation");
		mav.addObject("recruitInfo", recruitInfoDTO);
		mav.addObject("memberId", id);
		mav.addObject("positionSchoolTypeEnums", positionSchoolTypeEnums);
		mav.addObject("positionEducationEnums", positionEducationEnums);
		mav.addObject("specialityEnums", specialityEnums);
		mav.addObject("achievementEnums", achievementEnums);
		mav.addObject("internshipDaysEnums", internshipDaysEnums);
		mav.addObject("internshipExpEnums", internshipExpEnums);
		mav.addObject("schoolActivityEnums", schoolActivityEnums);
		mav.addObject("computerSkillMap", computerSkillMap);
		mav.addObject("skillLevelEnums", skillLevelEnums);
		mav.addObject("englishSkillEnums", englishSkillEnums);
		mav.addObject("otherLanguageSkillEnums", otherLanguageSkillEnums);

		return mav;
	}

	/**
	 * 保存职位定向信息
	 * 
	 * @param hdRecruitId
	 * @param hdMemberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savePreciseOrientation", method = RequestMethod.POST)
	public ModelAndView savePreciseOrientation(Integer hdRecruitId, String hdMemberId, Integer targetSchool,
			Integer targetEducational, Integer targetMajor, Integer targetScore, Integer targetIternshipDays,
			Integer targetSchoolActivity, String hdTargetComputerSkill, Integer targetSkillLevel,
			String hdTargetEnglishLang, String hdTargetOtherLang, String targetOtherClaim) {
		logger.info("execute savePreciseOrientation action...");

		int recruitId = hdRecruitId == null ? 0 : hdRecruitId;
		logger.info("recruitId: " + recruitId);
		hdMemberId = StringUtils.isBlank(hdMemberId) ? "" : hdMemberId;

		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(hdRecruitId);
		if (recruitInfoDTO != null) {
			recruitInfoDTO.setSchool(targetSchool);
			recruitInfoDTO.setEducational(targetEducational);
			recruitInfoDTO.setMajor(targetMajor);
			recruitInfoDTO.setScore(targetScore);
			targetIternshipDays = targetIternshipDays == null ? 0 : targetIternshipDays;
			recruitInfoDTO.setInternshipDays(targetIternshipDays);
			recruitInfoDTO.setActivityExp(targetSchoolActivity);
			recruitInfoDTO.setSkill(hdTargetComputerSkill);
			recruitInfoDTO.setSkillLevel(targetSkillLevel);
			recruitInfoDTO.setEnglish(hdTargetEnglishLang);
			recruitInfoDTO.setOtherLanguage(hdTargetOtherLang);
			recruitInfoDTO.setOtherClaim(targetOtherClaim);
			recruitInfoDTO.setPositionStatus(PositionStatusEnum.Online.getCode());

			int count = recruitInfoService.updateRecruitInfo(recruitInfoDTO);
			logger.info("update " + count + " record for id: " + recruitId);
		}

		String redirectUrl = String.format("redirect:my_joblist?id=%s&type=%d", hdMemberId,
				PositionStatusEnum.Online.getCode());
		ModelAndView mav = new ModelAndView(redirectUrl);
		return mav;
	}

	/**
	 * 职位预览
	 * 
	 * @param rid
	 * @return
	 */
	@RequestMapping(value = "/job_view", method = RequestMethod.GET)
	public ModelAndView jobView(String id, Integer rid) {
		rid = rid == null ? 0 : rid;
		id = id == null ? "" : id;
		logger.info("execute job_view view..., id: " + rid);
		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(rid);
		if (recruitInfoDTO == null) {
			recruitInfoDTO = new RecruitInfoDTO();
		}

		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		List<ResumeInfoDTO> resumeInfoDTOs = resumeInfoService.queryResumeInfos(id);

		ModelAndView mav = new ModelAndView("job_view");
		mav.addObject("recruitInfo", recruitInfoDTO);
		mav.addObject("memberDTO", memberDTO);
		mav.addObject("resumeInfos", resumeInfoDTOs);

		return mav;
	}

	/**
	 * 我职位列表
	 * 
	 * @return 职位列表
	 */
	@RequestMapping(value = "/my_joblist", method = RequestMethod.GET)
	public ModelAndView myJobList(String id, Integer type, Integer pn, String range) {
		logger.info("execute my_joblist view...");
		logger.info(String.format("id: %s, type: %d", id, type));

		id = id == null ? "" : id;
		range = range == null ? "" : range;
		type = type == null ? PositionStatusEnum.All.getCode() : type;
		pn = pn == null ? 1 : pn;

		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		if (memberDTO == null) {
			memberDTO = new MemberDTO();
		}

		List<RecruitInfoDTO> recruitInfoDTOs = null;

		Map<String, Object> queryMap = new HashMap<>();
		int total = 0;

		boolean hasRecruit = recruitInfoService.queryRecruitInfosCount(queryMap) > 0;

		if (type != PositionStatusEnum.All.getCode()) {
			queryMap.put("positionStatus", type);
		}

		if (range.equalsIgnoreCase("cp")) {
			// 当前用户所属公司的所有职位列表
			queryMap.put("companyId", memberDTO.getCompanyId());
			total = recruitInfoService.queryRecruitInfosCount(queryMap);

			queryMap.put("start", (pn - 1) * 5);
			queryMap.put("size", 5);
			recruitInfoDTOs = recruitInfoService.queryRecruitInfos(queryMap, false);

		} else {
			// 当前用户发布的职位列表
			queryMap.put("memberId", id);
			total = recruitInfoService.queryRecruitInfosCount(queryMap);

			queryMap.put("start", (pn - 1) * 5);
			queryMap.put("size", 5);
			recruitInfoDTOs = recruitInfoService.queryRecruitInfos(queryMap, false);
		}

		Map<Integer, RecruitResumeCountDTO> resumeCountMap = memberRecruitService.queryJobResumeApplyCountForMap(null);
		int resumeCount = 0;
		for (RecruitInfoDTO recruitInfoDTO : recruitInfoDTOs) {
			resumeCount = resumeCountMap.containsKey(recruitInfoDTO.getId()) ? resumeCountMap.get(
					recruitInfoDTO.getId()).getResumeCount() : 0;
			recruitInfoDTO.setResumeCount(resumeCount);
		}

		String url = type != PositionStatusEnum.All.getCode() ? String.format("recruit/my_joblist?id=%s&type=%d", id,
				type) : String.format("recruit/my_joblist?id=%s", id);

		if (range.equalsIgnoreCase("cp")) {
			url = url + "&range=cp";
		}

		String pagniateHtml = CommonUtils.generatePnHtml(total, pn, url);

		ModelAndView mav = new ModelAndView("my_joblist");
		mav.addObject("recruitInfoDTOs", recruitInfoDTOs);
		mav.addObject("memberDTO", memberDTO);
		mav.addObject("aLinkId", String.format("aLink_%d", type));
		mav.addObject("type", type);
		mav.addObject("range", range);
		mav.addObject("paginateHtml", pagniateHtml);
		mav.addObject("hasRecruit", hasRecruit);

		return mav;
	}

	/**
	 * 线上职位下线
	 * 
	 * @param rid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/offlineRecruitInfo", method = RequestMethod.POST)
	public Map<String, Object> offlineRecruitInfo(Integer rid) {
		logger.info("exeute offlineRecruitInfo action for rid: " + rid);
		rid = rid == null ? 0 : rid;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(rid);
			recruitInfoDTO.setPositionStatus(PositionStatusEnum.Offline.getCode());

			int count = recruitInfoService.updateRecruitInfo(recruitInfoDTO);
			logger.info("update " + count + " recruitinfo for rid: " + rid);

			resultMap.put("success", true);
			resultMap.put(MESSAGE, "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 删除未发布职位
	 * 
	 * @param rid
	 *            职位id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteRecruitInfo", method = RequestMethod.POST)
	public Map<String, Object> deleteRecruitInfo(Integer rid, String memberId, Integer type, String range) {
		logger.info("exeute deleteRecruitInfo action for rid: " + rid);
		rid = rid == null ? 0 : rid;
		memberId = memberId == null ? "" : memberId;
		type = type == null ? 0 : type;
		range = range == null ? "" : range;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = recruitInfoService.deleteRecruitInfo(rid);
			logger.info("delete " + count + " recruitinfo for rid: " + rid);

			resultMap.put("success", true);
			resultMap.put(MESSAGE, "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 复制为新职位
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/copytoNewRecruitInfo", method = RequestMethod.POST)
	public Map<String, Object> copytoNewRecruitInfo(String id, Integer rid) {
		logger.info("exeute copytoNewRecruitInfo action for rid: " + rid);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(rid);
			RecruitInfoDTO newRecruitInfoDTO = new RecruitInfoDTO();

			newRecruitInfoDTO.setMemberId(recruitInfoDTO.getMemberId());
			newRecruitInfoDTO.setCompanyId(recruitInfoDTO.getCompanyId());
			newRecruitInfoDTO.setRecruitType(recruitInfoDTO.getRecruitType());
			newRecruitInfoDTO.setPositionType(recruitInfoDTO.getPositionType());
			newRecruitInfoDTO.setPositionName(recruitInfoDTO.getPositionName());
			newRecruitInfoDTO.setDepartmentName(recruitInfoDTO.getDepartmentName());
			newRecruitInfoDTO.setPostDuty(recruitInfoDTO.getPostDuty());
			newRecruitInfoDTO.setWorkAddress(recruitInfoDTO.getWorkAddress());
			newRecruitInfoDTO.setMinSalary(recruitInfoDTO.getMinSalary());
			newRecruitInfoDTO.setMaxSalary(recruitInfoDTO.getMaxSalary());
			newRecruitInfoDTO.setIsNegotiable(recruitInfoDTO.getIsNegotiable());
			newRecruitInfoDTO.setImportantRemark(recruitInfoDTO.getImportantRemark());
			newRecruitInfoDTO.setNeedNum(recruitInfoDTO.getNeedNum());
			newRecruitInfoDTO.setAcceptEmail(recruitInfoDTO.getAcceptEmail());
			newRecruitInfoDTO.setEmailType(recruitInfoDTO.getEmailType());
			newRecruitInfoDTO.setPositionStatus(recruitInfoDTO.getPositionStatus());
			newRecruitInfoDTO.setValidityTime(recruitInfoDTO.getValidityTime());

			newRecruitInfoDTO.setSchool(recruitInfoDTO.getSchool());
			newRecruitInfoDTO.setEducational(recruitInfoDTO.getEducational());
			newRecruitInfoDTO.setMajor(recruitInfoDTO.getMajor());
			newRecruitInfoDTO.setScore(recruitInfoDTO.getScore());
			newRecruitInfoDTO.setInternshipDays(recruitInfoDTO.getInternshipDays());
			newRecruitInfoDTO.setActivityExp(recruitInfoDTO.getActivityExp());
			newRecruitInfoDTO.setSkill(recruitInfoDTO.getSkill());
			newRecruitInfoDTO.setSkillLevel(recruitInfoDTO.getSkillLevel());
			newRecruitInfoDTO.setEnglish(recruitInfoDTO.getEnglish());
			newRecruitInfoDTO.setOtherLanguage(recruitInfoDTO.getOtherLanguage());
			newRecruitInfoDTO.setOtherClaim(recruitInfoDTO.getOtherClaim());
			newRecruitInfoDTO.setPositionStatus(recruitInfoDTO.getPositionStatus());

			int count = recruitInfoService.insertRecruitInfo(newRecruitInfoDTO);
			logger.info("insert " + count + " recruitinfo for id: " + newRecruitInfoDTO.getId());

			resultMap.put("success", true);
			resultMap.put("url", String.format("recruit/job_basic_info?id=%s&rid=%d", id, newRecruitInfoDTO.getId()));
			resultMap.put(MESSAGE, "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("url", "");
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 公司的职位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/company_joblist", method = RequestMethod.GET)
	public ModelAndView companyJobList() {
		logger.info("execute company_joblist view...");
		ModelAndView mav = new ModelAndView("company_joblist");
		return mav;
	}

	/**
	 * 简历库
	 * 
	 * @return
	 */
	@RequestMapping(value = "/resume_database", method = RequestMethod.GET)
	public ModelAndView resumeDatabase(String id) {
		logger.info("execute resume_database view...");

		Map<String, List<UniversityDTO>> universityMap = getUniversityMap();
		List<EducationEnum> educationEnums = Arrays.asList(EducationEnum.values());
		List<Integer> graduationYears = new ArrayList<Integer>();
		Calendar c = Calendar.getInstance();
		for (int year = 1988; year <= c.get(Calendar.YEAR); year++) {
			graduationYears.add(year);
		}

		List<SpecialityEnum> specialityEnums = Arrays.asList(SpecialityEnum.values());
		List<InternshipExpEnum> internshipExpEnums = Arrays.asList(InternshipExpEnum.values());
		List<GenderEnum> genderEnums = Arrays.asList(GenderEnum.values());
		Map<String, List<IndustryDTO>> industryMap = getIndustryMap();
		List<GeoAreaDTO> hotCities = getHotCities();
		List<GeoAreaDTO> otherCities = getOtherCities();
		Map<String, List<GeoAreaDTO>> cityMap = getGeoAreaMap();
		List<RecruitInfoDTO> recruitInfoDTOs = recruitInfoService.queryRecruitInfoMemberId(id, false);

		ModelAndView mav = new ModelAndView("resume_database");
		mav.addObject("memberId", id);
		mav.addObject("universityMap", universityMap);
		mav.addObject("educationEnums", educationEnums);
		mav.addObject("graduationYears", graduationYears);
		mav.addObject("specialityEnums", specialityEnums);
		mav.addObject("internshipExpEnums", internshipExpEnums);
		mav.addObject("genderEnums", genderEnums);
		mav.addObject("industryMap", industryMap);
		mav.addObject("cityMap", cityMap);
		mav.addObject("hotCities", hotCities);
		mav.addObject("otherCities", otherCities);
		mav.addObject("recruitInfoDTOs", recruitInfoDTOs);
		mav.addObject("geoAreaList", getUniversityGeoList());

		return mav;
	}

	/**
	 * 我的申请人
	 * 
	 * @return
	 */
	@RequestMapping(value = "/my_applicant", method = RequestMethod.GET)
	public ModelAndView myApplicant(String id) {
		logger.info("execute my_applicant view...");
		id = id == null ? "" : id;
		List<RecruitInfoDTO> maPositionList = recruitInfoService.queryRecruitInfoMemberId(id, false);
		List<PositionSchoolTypeEnum> maSchoolList = PositionSchoolTypeEnum.getNonC9List();
		PositionEducationEnum[] maDiplomaList = PositionEducationEnum.values();
		List<Integer> maGraduationYearList = new ArrayList<Integer>();
		Calendar c = Calendar.getInstance();
		for (int year = 1988; year <= c.get(Calendar.YEAR); year++) {
			maGraduationYearList.add(year);
		}
		SpecialityEnum[] maMajorList = SpecialityEnum.values();
		InternshipExpEnum[] maWorkExpList = InternshipExpEnum.values();
		GenderEnum[] maGenderList = GenderEnum.values();

		FilterMyApplicantCondition condition = new FilterMyApplicantCondition();
		condition.setMemberId(id);
		int total = companyBusinessService.queryMyApplicantInfos(condition).size();

		condition.setStart(0);
		condition.setSize(5);
		List<MyApplicantDTO> myApplicantDTOs = companyBusinessService.queryMyApplicantInfos(condition);
		String paginateHtml = CommonUtils.generateMyApplicantPnHtml(total, 1);

		String pageType = "";
		List<RecruitInfoDTO> ls = recruitInfoService.queryRecruitInfoMemberId(id, false);

		if (ls.size() == 0) {
			// 当前企业用户未发布过职位
			pageType = NO_PUBLISH_JOB;
		} else if (myApplicantDTOs.size() == 0) {
			// 当前发布过职位但是没有收到任何简历
			pageType = NO_APPLICANT;
		} else {
			pageType = DEFAULT;
		}

		logger.info(pageType);

		ModelAndView mav = new ModelAndView("my_applicant");
		mav.addObject("maPositionList", maPositionList);
		mav.addObject("maSchoolList", maSchoolList);
		mav.addObject("maDiplomaList", maDiplomaList);
		mav.addObject("maGraduationYearList", maGraduationYearList);
		mav.addObject("maMajorList", maMajorList);
		mav.addObject("maWorkExpList", maWorkExpList);
		mav.addObject("maGenderList", maGenderList);
		mav.addObject("paginateHtml", paginateHtml);
		mav.addObject("myApplicantDTOs", myApplicantDTOs);
		mav.addObject("memberId", id);
		mav.addObject("pageType", pageType);

		return mav;
	}

	/**
	 * 加载我的申请人
	 * 
	 * @param id
	 * @param filterFeedStatus
	 * @param pn
	 * @param recruitId
	 * @param schoolFilter
	 * @param diplomaFilter
	 * @param graduationYear
	 * @param major
	 * @param workExpFilter
	 * @param gender
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadMyApplicantResult", method = RequestMethod.POST)
	public Map<String, Object> loadMyApplicantResult(String id, Integer filterFeedStatus, Integer pn,
			Integer filterRecruitId, Integer filterSchool, Integer filterDiploma, Integer filterGraduationYear,
			Integer filterMajor, Integer filterWorkExp, Integer filterGender) {
		logger.info("execute loadMyApplicantResult action...");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		id = id == null ? "" : id;
		filterFeedStatus = filterFeedStatus == null ? 0 : filterFeedStatus;
		pn = filterFeedStatus == null ? 0 : pn;
		filterRecruitId = filterRecruitId == null ? 0 : filterRecruitId;
		filterSchool = filterSchool == null ? 0 : filterSchool;
		filterDiploma = filterDiploma == null ? 0 : filterDiploma;
		filterGraduationYear = filterGraduationYear == null ? 0 : filterGraduationYear;
		filterMajor = filterMajor == null ? 0 : filterMajor;
		filterWorkExp = filterWorkExp == null ? 0 : filterWorkExp;
		filterGender = filterGender == null ? 0 : filterGender;

		try {
			FilterMyApplicantCondition condition = new FilterMyApplicantCondition();
			condition.setMemberId(id);
			condition.setRecruitId(filterRecruitId);
			condition.setSchool(filterSchool);
			condition.setDiploma(filterDiploma);
			condition.setGraduationYear(filterGraduationYear);
			condition.setMajorType(filterMajor);
			condition.setWorkExpCount(filterWorkExp);
			condition.setGender(filterGender);
			condition.setFeedStatus(filterFeedStatus);

			int total = companyBusinessService.queryMyApplicantInfos(condition).size();
			condition.setStart((pn - 1) * 5);
			condition.setSize(5);

			List<MyApplicantDTO> resultLs = companyBusinessService.queryMyApplicantInfos(condition);

			String schoolName = "";
			String majorName = "";
			String diplomaDesc = "";
			StringBuilder sb = new StringBuilder();
			for (MyApplicantDTO item : resultLs) {
				sb.append("<li>");
				sb.append(String
						.format("<input type=\"hidden\" id=\"hd_%d\" memberId=\"%s\" resumeId=\"%d\" recruitId=\"%d\" val=\"%d\" />",
								item.getId(), item.getMemberId(), item.getResumeId(), item.getRecruitId(), item.getId()));
				sb.append(String
						.format("<label class=\"coSP_checkbox-checkOne\"> <input id=\"chk_%d\" class=\"pointer chk\" type=\"checkbox\" val=\"%d\" /> <i></i></label>",
								item.getId(), item.getId()));
				sb.append(String.format(
						"<div class=\"coSP_resume-img\"><img width=\"50\" height=\"50\" src=\"%s\" /></div>",
						item.getResumeAvatar()));
				sb.append("<div class=\"coSP_resume-info\">");
				sb.append(String.format("<h4 class=\"coSP_read\">%s</h4>", item.getMemberName()));
				sb.append(String.format("<span class=\"coSP_resume-text\">应聘职位：%s</span><br />", item.getRecruitName()));
				schoolName = StringUtils.isEmpty(item.getSchoolName()) ? "缺省" : item.getSchoolName();
				majorName = StringUtils.isEmpty(item.getMajorName()) ? "缺省" : item.getMajorName();
				diplomaDesc = StringUtils.isEmpty(item.getDiplomaDesc()) ? "缺省" : item.getDiplomaDesc();
				sb.append(String.format("<span class=\"coSP_resume-text\">%s | %s | %s</span></div>", schoolName,
						majorName, diplomaDesc));
				sb.append("<div class=\"coSP_links\">");
				sb.append(String
						.format("<span class=\"coSP_resume_refuse\"><a href=\"javascript:;\" onclick=\"viewResume(%d)\" class=\"coSP_look\">查看简历</a></span>",
								item.getResumeId()));
				sb.append(String
						.format("<span class=\"coSP_resume_refuse\"><a href=\"javascript:;\" class=\"coSP_download\" onclick=\"downloadResume(%d)\">下载简历</a></span>",
								item.getResumeId()));

				if (item.getFeedStatus() < 3) {
					sb.append(String
							.format("<a href=\"javascript:;\" onclick=\"informInterview(%d, %s)\" class=\"coSP_resume_notice\" title=\"发送短信邀请面试\">通知面试</a>",
									item.getId(), id));
					sb.append(String
							.format("<a href=\"javascript:;\" onclick=\"updateMemberRecruit(%d,4)\" class=\"coSP_resume_refuse\">不合适</a>",
									item.getId()));
					sb.append(String
							.format("<a href=\"javascript:;\" onclick=\"updateMemberRecruit(%d,2)\" class=\"coSP_resume_caninterview\">待定</a>",
									item.getId()));
				}

				sb.append("</div></li>");
			}

			String resultHtml = sb.toString();
			String paginateHtml = StringUtils.isEmpty(resultHtml) ? "" : CommonUtils.generateMyApplicantPnHtml(total,
					pn);

			resultMap.put("success", true);
			resultMap.put(MESSAGE, "");
			resultMap.put("resultHtml", resultHtml);
			resultMap.put("paginateHtml", paginateHtml);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put(MESSAGE, e.getMessage());
			resultMap.put("paginateHtml", "");
			resultMap.put("resultHtml", "");
		}

		return resultMap;
	}

	/**
	 * 更新为不合适或者待定
	 * 
	 * @param memberId
	 * @param resumeId
	 * @param recruitId
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberRecruit", method = RequestMethod.POST)
	public Map<String, Object> updateMemberRecruit(Integer id, Integer status) {
		logger.info("execute updateMemberRecruit action...");
		id = id == null ? 0 : id;

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MemberRecruitDTO memberRecruitDTO = memberRecruitService.queryMemberRecruit(id);
			if (memberRecruitDTO != null) {
				memberRecruitDTO.setFeedStatus(status);
				int count = memberRecruitService.updateMemberRecruit(memberRecruitDTO);
				logger.info("update " + count + " record for member_recruit");
			}

			resultMap.put("success", true);
			resultMap.put(MESSAGE, "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 批量更新为不合适或者待定
	 * 
	 * @param memberRecruitArgs
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/batchUpdateMemberRecruit", method = RequestMethod.POST)
	public Map<String, Object> batchUpdateMemberRecruit(String memberRecruitIds, Integer status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("ids", memberRecruitIds);
			List<MemberRecruitDTO> ls = memberRecruitService.queryMemberRecruits(queryMap, false);

			for (MemberRecruitDTO memberRecruitDTO : ls) {
				memberRecruitDTO.setFeedStatus(status);
				int count = memberRecruitService.updateMemberRecruit(memberRecruitDTO);
				logger.info("execute " + count + " member_recruit...");
			}

			resultMap.put("success", true);
			resultMap.put(MESSAGE, "");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 批量删除member_recruit
	 * 
	 * @param memberRecruitIds
	 *            需要删除id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/batchDeleteMemberRecruit", method = RequestMethod.POST)
	public Map<String, Object> batchDeleteMemberRecruit(String memberRecruitIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("ids", memberRecruitIds);
			List<MemberRecruitDTO> ls = memberRecruitService.queryMemberRecruits(queryMap, false);

			for (MemberRecruitDTO memberRecruitDTO : ls) {
				int count = memberRecruitService.deleteMemberRecruit(memberRecruitDTO.getId());
				logger.info("execute delete " + count + " member_recruit action...");
			}

			resultMap.put("success", true);
			resultMap.put(MESSAGE, "");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put(MESSAGE, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 通知面试页
	 * 
	 * @param id
	 *            当前登录人员的ID（即当前企业用户ID）
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/inform_interview", method = RequestMethod.GET)
	public ModelAndView informInterview(String id, Integer mrid) {
		id = id == null ? "" : id;
		mrid = mrid == null ? 0 : mrid;
		ModelAndView mav = new ModelAndView("inform_interview");

		MemberRecruitDTO memberRecruitDTO = memberRecruitService.queryMemberRecruit(mrid, false);

		if (memberRecruitDTO != null) {
			MemberDTO memberDTO = memberService.queryMemberInfo(memberRecruitDTO.getMemberId());
			RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(memberRecruitDTO.getRecruitId());
			InformInterviewDTO informInterviewDTO = new InformInterviewDTO();
			if (memberDTO != null) {
				informInterviewDTO.setMemberId(memberDTO.getMemberId());
				informInterviewDTO.setMemberName(memberDTO.getName());
				informInterviewDTO.setMemberEmail(memberDTO.getCommonEmail());
			}

			mav.addObject("informInterviewDTO", informInterviewDTO);
			mav.addObject("recruitInfoDTO", recruitInfoDTO);
			mav.addObject("memberId", id);

		} else {
			mav.addObject("informInterviewDTO", new InformInterviewDTO());
			mav.addObject("recruitInfoDTO", new RecruitInfoDTO());
			mav.addObject("memberId", id);
		}

		return mav;
	}

	/**
	 * 保存通知面试信息入库（发送通知面试通知信息）
	 * 
	 * @param interviewMemberId
	 * @param interviewMemberName
	 * @param interviewMemberMail
	 * @param interviewDate
	 * @param interviewTime
	 * @param interviewAddress
	 * @param interviewContactPerson
	 * @param interviewContactPhone
	 * @param interviewContent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveInformInterviewData", method = RequestMethod.POST)
	public Map<String, Object> saveInformInterviewData(Integer recruitId, String hdPubMemberId,
			String interviewMemberId, String interviewMemberName, String interviewMemberMail, String interviewDate,
			String interviewTime, String interviewAddress, String interviewContactPerson, String interviewContactPhone,
			String interviewContent) {
		Map<String, Object> resultMap = new HashMap<>();
		recruitId = recruitId == null ? 0 : recruitId;
		hdPubMemberId = hdPubMemberId == null ? "" : hdPubMemberId;
		RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(recruitId);

		Date interviewDt = null;
		String emailInterviewDt = interviewDate;
		try {
			interviewDt = DateUtils.parseDate(interviewDate, CommonUtils.YYYY_MM_DD_DATA);
			emailInterviewDt = sdf.format(interviewDt);
		} catch (ParseException e1) {
			logger.error(e1.getMessage(), e1);
		}

		try {
			String toAddress = interviewMemberMail;
			String subject = "面试通知";
			String title = "面试通知";
			StrBuilder sb = new StrBuilder();
			sb.appendln(String.format("%s，您好：", interviewMemberName));
			sb.appendln(String.format("恭喜您通过了【%s】的简历筛选阶段，请于【%s %s】到下面的面试地点参加面试。", recruitInfoDTO.getPositionName(),
					emailInterviewDt, interviewTime));
			sb.appendln(String.format("面试地点：%s", interviewAddress));
			sb.appendln(String.format("联系人：%s", interviewContactPerson));
			sb.appendln(String.format("联系方式：%s", interviewContactPhone));
			sb.appendln(String.format("面试内容简介：%s", interviewContent));

			String contents = sb.toString();
			sendMail(toAddress, subject, title, contents);

			InformInterviewDTO informInterviewDTO = new InformInterviewDTO();
			informInterviewDTO.setMemberId(interviewMemberId);
			informInterviewDTO.setMemberName(interviewMemberName);
			informInterviewDTO.setMemberEmail(interviewMemberMail);
			informInterviewDTO.setInterviewDate(interviewDt);
			informInterviewDTO.setDetailTime(interviewTime);
			informInterviewDTO.setInterviewAddress(interviewAddress);
			informInterviewDTO.setContactPerson(interviewContactPerson);
			informInterviewDTO.setContactPhone(interviewContactPhone);
			informInterviewDTO.setInterviewContent(interviewContent);

			companyBusinessService.informInterview(informInterviewDTO);

			resultMap.put("success", true);
			resultMap.put(MESSAGE, "");
			resultMap.put("returnUrl", String.format("recruit/my_applicant?id=%s", hdPubMemberId));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put(MESSAGE, "");
			resultMap.put("returnUrl", "");
		}

		return resultMap;
	}

	/**
	 * 搜索简历库
	 * 
	 * @param memberId
	 * @param filterUniversity
	 * @param filterDiploma
	 * @param filterGraduationYear
	 * @param filterMajorType
	 * @param filterInternshipExp
	 * @param filterGender
	 * @param filterIndustries
	 * @param filterGeos
	 * @param filterSearchtxt
	 * @param pageStart
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchResumeInfos", method = RequestMethod.POST)
	public Map<String, Object> searchResumeInfos(String memberId, Integer filterUniversity, Integer filterDiploma,
			Integer filterGraduationYear, Integer filterMajorType, Integer filterInternshipExp, Integer filterGender,
			String filterIndustries, String filterGeos, String filterSearchtxt, Integer pageStart) {
		memberId = StringUtils.isBlank(memberId) ? "" : memberId;
		filterUniversity = filterUniversity == null ? 0 : filterUniversity;
		filterDiploma = filterDiploma == null ? 0 : filterDiploma;
		filterGraduationYear = filterGraduationYear == null ? 0 : filterGraduationYear;
		filterMajorType = filterMajorType == null ? 0 : filterMajorType;
		filterInternshipExp = filterInternshipExp == null ? 0 : filterInternshipExp;
		filterGender = filterGender == null ? 0 : filterGender;
		filterIndustries = filterIndustries == null ? "" : filterIndustries;
		filterGeos = filterGeos == null ? "" : filterGeos;
		filterSearchtxt = StringUtils.isBlank(filterSearchtxt) ? "" : filterSearchtxt;
		pageStart = pageStart == null ? 1 : pageStart;
		int from = pageStart - 1;

		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("execute searchResult action");
		String searchCondition = String
				.format("memberId: %s, filterUniversity: %d, filterDiploma: %d, filterGraduationYear: %d, filterMajorType: %d,"
						+ " filterInternshipExp: %d, filterGender: %d, filterIndustries: %s, filterGeos: %s, filterSearchtxt: %s, "
						+ " pageStart: %s", memberId, filterUniversity, filterDiploma, filterGraduationYear,
						filterMajorType, filterInternshipExp, filterGender, filterIndustries, filterGeos,
						filterSearchtxt, filterSearchtxt);
		logger.info("search condition: " + searchCondition);
		int total = 0;

		List<ResumeInfoDTO> ls = new ArrayList<ResumeInfoDTO>();
		FilterResumeInfoCondition condition = new FilterResumeInfoCondition();
		condition.setSchoolId(filterUniversity);
		condition.setDiploma(filterDiploma);
		condition.setGraduationYear(filterGraduationYear);
		condition.setMajor(filterMajorType);
		condition.setHasWorkExp(filterInternshipExp == 1);
		condition.setSex(filterGender);
		condition.setDemandIndustries(filterIndustries);
		condition.setDemandCities(filterGeos);
		condition.setSearchTxt(filterSearchtxt);
		condition.setFrom(from * PAGE_SIZE);
		condition.setSize(PAGE_SIZE);

		ls = searchResumeInfoService.searchResumeInfoWithConditions(condition);
		total = condition.getTotal();

		String resultHtml = drawResumeInfoHtml(ls, memberId);
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
	 * 重绘简历HTML
	 * 
	 * @param ls
	 * @param memberId
	 * @return
	 */
	public String drawResumeInfoHtml(List<ResumeInfoDTO> ls, String memberId) {

		StringBuilder sb = new StringBuilder();
		for (ResumeInfoDTO item : ls) {
			sb.append(String
					.format("<li><label class=\"coSR_checkbox-checkOne\">"
							+ "<input id=\"chk_%d\" type=\"checkbox\" val=\"%d\" memberId=\"%s\" resumeId=\"%d\" class=\"chk\"> <i></i></label>",
							item.getId(), item.getId(), item.getMemberId(), item.getId()));
			sb.append("<div class=\"coSR_resume-img\">");
			sb.append(String.format("<img width=\"50\" height=\"50\" src=\"%s\"></div>", item.getAvatar()));
			sb.append("<div class=\"coSR_resume-info\">");
			sb.append(String.format("<h4 class=\"coSR_read\">%s</h4><br />", item.getResumeName()));

			if (!StringUtils.isEmpty(item.getMajor())) {
				sb.append(String.format("<span class=\"coSR_resume-text\">%s| %s | %s </span>", item.getMemberDTO()
						.getSchoolDesc(), item.getMajor(), item.getDiplomaDesc()));
			} else {
				sb.append(String.format("<span class=\"coSR_resume-text\">%s | %s </span>", item.getMemberDTO()
						.getSchoolDesc(), item.getDiplomaDesc()));
			}

			sb.append("</div>");
			sb.append("<div class=\"coSR_links\">");
			sb.append(String.format("<span class=\"coSR_resume_refuse\">"
					+ "<a href=\"resume/resume_info_view?id=%s&rsid=%d\" class=\"coSR_look\">查看简历</a></span>",
					memberId, item.getId()));
			sb.append(String
					.format("<span class=\"coSR_resume_refuse\"><a href=\"javascript:;\" onclick=\"downloadResume(%d)\" class=\"coSR_download\">下载简历</a></span>",
							item.getId()));
			sb.append("</div></li>");
		}

		return sb.toString();
	}

	/**
	 * 对相关人员发送职位邀请
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/inviteGuysForJob", method = RequestMethod.POST)
	public Map<String, Object> inviteGuysForJob(String memberIds, Integer recruitId) {
		logger.info("execute inviteGuysForJob action...");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (recruitId > 0 && !StringUtils.isBlank(memberIds)) {
				RecruitInfoDTO recruitInfoDTO = recruitInfoService.queryRecruitInfo(recruitId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("memberIds", memberIds);
				List<MemberDTO> memberDTOs = memberService.queryMemberInfoByConditions(queryMap);
				for (MemberDTO memberDTO : memberDTOs) {
					String toAddress = memberDTO.getCommonEmail();
					String subject = "投递职位邀请";
					String title = "投递职位邀请";
					StrBuilder sb = new StrBuilder();
					sb.appendln(String.format("%s，您好：", memberDTO.getName()));
					sb.appendln(String.format("您在第一站填写简历信息比较符合如下职位，推荐给该职位【%s】给您", recruitInfoDTO.getPositionName()));
					sb.appendln(String.format("欢迎登录第一站：%s/recruit/job_view?id=%s&rid=%d", CommonUtils.CONTEXT_URL,
							memberDTO.getMemberId(), recruitId));

					String contents = sb.toString();
					sendMail(toAddress, subject, title, contents);
				}
				resultMap.put("success", true);
				resultMap.put("message", "已发送邀请邮件！");
			} else {
				resultMap.put("success", true);
				resultMap.put("message", "");
			}
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 执行忽略简历操作（将简历状态更新为2）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ignoreResumeInfo", method = RequestMethod.POST)
	public Map<String, Object> ignoreResumeInfo(String resumeIds) {
		logger.info("execute ignoreResumeInfo action...");
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put("ids", resumeIds);
			List<ResumeInfoDTO> ls = resumeInfoService.queryResumeInfos(queryMap);
			for (ResumeInfoDTO item : ls) {
				item.setStatus(2);
				int count = resumeInfoService.updateResumeInfo(item);
				logger.info("ignore " + count + " for id: " + item.getId());
			}

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
	 * 生成二维码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/generateQrcode", method = RequestMethod.POST)
	public Map<String, Object> generateQrcode(HttpServletRequest request, Integer cpid) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		cpid = cpid == null ? 0 : cpid;
		CompanyInfoDTO companyInfoDTO = companyMemberService.queryCompanyInfo(cpid);
		if (companyInfoDTO == null) {
			resultMap.put(SUCCESS, false);
			resultMap.put("url", "");
			resultMap.put("message", "recruitInfo is null");
			return resultMap;
		} else {
			String path = request.getServletContext().getRealPath("/");
			logger.info("request path: " + path);

			String parentPath = path + File.separator + "qrcode";
			logger.info("parentPath: " + parentPath);

			String website = StringUtils.replace(companyInfoDTO.getOfficialWebsite(), ".", "-");

			if (StringUtils.contains(companyInfoDTO.getOfficialWebsite(), HTTP)) {
				website = StringUtils.replace(website, HTTP, "");
			}

			String fileName = String.format("companyshare_%d_%s.jpg", cpid, website);
			// 将文件名称更新到对应的company信息中
			String newFilepath = parentPath + File.separator + fileName;

			if (!QiniuUtils.isExists(fileName, "companyshare_")) {
				String siteUrl = companyInfoDTO.getOfficialWebsite();
				if (!StringUtils.contains(siteUrl, HTTP)) {
					siteUrl = HTTP + siteUrl;
				}
				boolean flag = QrCodeUtils.generateQrcode(siteUrl, fileName, parentPath);
				if (flag) {
					try {
						File file = new File(newFilepath);
						byte[] bytes = FileUtils.readFileToByteArray(file);
						Response resp = QiniuUtils.uploadFile(bytes, fileName, QiniuUtils.getToken(fileName));
						logger.info("bodyString: " + resp.bodyString());

						resultMap.put("url", String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, fileName));
						resultMap.put(SUCCESS, true);

						// 删除临时文件
						file.delete();

					} catch (IOException e) {
						logger.error(e.getMessage(), e);
						resultMap.put(SUCCESS, false);
						resultMap.put("url", "");
						resultMap.put("message", e.getMessage());
					}
				}

			} else {
				resultMap.put("url", String.format("%s/%s", CommonUtils.QINIU_FILE_DOMAIN, fileName));
				resultMap.put(SUCCESS, true);
				resultMap.put("message", "");
			}

			return resultMap;
		}
	}
}
