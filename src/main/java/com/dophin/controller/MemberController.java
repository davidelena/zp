package com.dophin.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dophin.dto.MemberDTO;
import com.dophin.dto.SmsCodeDTO;
import com.dophin.dto.SmsLogDTO;
import com.dophin.enums.MemberSourceEnum;
import com.dophin.enums.SmsTypeEnum;
import com.dophin.service.EmailTaskService;
import com.dophin.service.MemberService;
import com.dophin.service.SmsService;
import com.dophin.utils.CaptchaUtils;
import com.dophin.utils.CommonUtils;

/**
 * 用户类
 * 
 * @author dale
 * 
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	private static Logger logger = Logger.getLogger(MemberController.class);
	private int width = 100;
	private int height = 35;

	/**
	 * 验证码失效时间5分钟
	 */
	private static long EXIPRED_TIME = 1000 * 60 * 5;

	private static int SESSION_EXPIRED_TIME = 3600;

	@Autowired
	private MemberService memberService;

	@Autowired
	private EmailTaskService emailTaskService;

	@Autowired
	private SmsService smsService;

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Map<String, Object> logout(HttpServletRequest request) {
		logger.info("execute logout action...");
		Map<String, Object> resultMap = new HashMap<>();

		try {
			HttpSession currentSession = request.getSession();
			MemberDTO memberDTO = (MemberDTO) currentSession.getAttribute("memberDTO");
			String url = memberDTO.getSource() == MemberSourceEnum.Enterprise.getCode() ? "company_login"
					: "student_login";

			currentSession.removeAttribute("memberDTO");

			resultMap.put("success", true);
			resultMap.put("message", "");
			resultMap.put("url", url);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("message", "");
			resultMap.put("url", "student_login");
		}

		return resultMap;
	}

	/**
	 * 个人邮箱列表
	 */
	private static List<String> personalEmailList = Arrays.asList(new String[] { "126.com", "163.com", "qq.com",
			"sina.com", "aliyun.com", "gmail.com" });

	/**
	 * 注册邮箱或手机（针对学生用户）
	 * 
	 * @param account
	 *            注册邮箱或手机（针对学生用户）
	 * @param password
	 *            注册密码
	 * @param type
	 *            账户类型
	 * @param code
	 *            验证码
	 * @param request
	 *            http请求
	 * @return 注册返回信息
	 */
	@ResponseBody
	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public Map<String, Object> doRegister(String account, String password, int type, String code, String accountType,
			HttpServletRequest request) {
		logger.info("execute doRegister action...");
		logger.info(String.format("account: %s, password: %s, type: %d, accountType: %s, code: %s, ", account,
				password, type, accountType, code));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		MemberDTO memberDTO = new MemberDTO();
		if (accountType.equalsIgnoreCase(EMAIL)) {

			String companyEmailSuffix = account.contains("@") ? StringUtils.substringAfterLast(account, "@") : "";
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("source", type);
			queryMap.put("companyEmailSuffix", companyEmailSuffix);
			boolean isFirst = !memberService.isMemberExists(queryMap);

			if (type == MemberSourceEnum.Enterprise.getCode() && personalEmailList.contains(companyEmailSuffix)) {
				resultMap.put("returnUrl", "");
				resultMap.put("memberId", "");
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "邮箱格式不正确，企业用户请输入公司邮箱");

			} else {
				// 邮箱注册
				memberDTO.setName(String.format("%s的用户", account));
				memberDTO.setPassword(DigestUtils.md5Hex(password));
				memberDTO.setEmail(account);
				memberDTO.setCommonEmail(account);
				memberDTO.setSource(type);
				memberDTO.setIsVerifyEmail(0);
				memberDTO.setIsVerifyCellphone(0);
				memberDTO.setCompanyEmailSuffix(companyEmailSuffix);
				memberDTO.setFirst(isFirst);

				// 创建激活url
				String memberId = memberService.insertMemberInfo(memberDTO);
				String md5MemberEmail = DigestUtils.md5Hex(account);

				String content = String.format("http://%s/member/validate?id=%s&em=%s&timestamp=%s",
						CommonUtils.CONTEXT_URL, memberId, md5MemberEmail, System.currentTimeMillis());

				// 发送激活邮件，跳转到登录界面
				sendActivationMail(account, content);

				if (type == MemberSourceEnum.Enterprise.getCode()) {
					Map<String, Object> queryMap2 = new HashMap<String, Object>();
					queryMap2.put("companyEmailSuffix", companyEmailSuffix);
					queryMap2.put("source", type);
					List<MemberDTO> ls = memberService.queryCompanyIdBySuffix(queryMap2);
					if (ls.size() > 0) {
						int companyId = ls.get(0).getCompanyId();
						MemberDTO memberDTO2 = memberService.queryMemberInfo(memberId);
						memberDTO2.setCompanyId(companyId);
						memberService.updateMemberInfo(memberDTO2);						
					}
				}

				resultMap.put("returnUrl", "register_active?id=" + memberId);
				resultMap.put("memberId", memberId);
				resultMap.put(SUCCESS, true);
				resultMap.put(MSG, "");
			}

		} else {
			// 手机注册
			SmsCodeDTO smsCodeDTO = smsService.querySmsCode(account);
			long sendTime = smsCodeDTO.getCreateTime().getTime();
			long now = System.currentTimeMillis();

			if (now - sendTime > EXIPRED_TIME) {
				resultMap.put("returnUrl", "");
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "短信验证码过期，请重新发送");

				return resultMap;
			} else if (!code.equalsIgnoreCase(smsCodeDTO.getCode())) {
				resultMap.put("returnUrl", "");
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "短信验证码不正确，请重新输入");
			} else {
				// 手机注册
				memberDTO.setName(String.format("%s的用户", account));
				memberDTO.setPassword(DigestUtils.md5Hex(password));
				memberDTO.setMobile(account);
				memberDTO.setSource(type);
				memberDTO.setIsVerifyEmail(0);
				memberDTO.setIsVerifyCellphone(1);

				String memberId = memberService.insertMemberInfo(memberDTO);
				logger.info("add new member: " + memberId);

				HttpSession currentSession = request.getSession();
				logger.info("currentSession: " + currentSession.getId());
				currentSession.setAttribute("memberDTO", memberDTO);
				currentSession.setMaxInactiveInterval(SESSION_EXPIRED_TIME);

				resultMap.put("returnUrl", "resume/student_info?id=" + memberId);
				resultMap.put("memberId", memberId);
				resultMap.put(SUCCESS, true);
				resultMap.put(MSG, "");
			}
		}

		return resultMap;
	}

	/**
	 * 获取短信验证码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSmsCode", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getSmsCode(String account, String accountType, Boolean isReset) {

		Map<String, Object> resultMap = new HashMap<>();
		isReset = isReset == null ? false : isReset;
		accountType = accountType == null ? MOBILE : accountType;
		SmsTypeEnum smsTypeEnum = SmsTypeEnum.Register;
		logger.info("account: " + account + ", accoutType: " + accountType + ", smsType: " + smsTypeEnum);

		// 是否为重置选项
		if (isReset) {
			if (StringUtils.isBlank(account)) {
				resultMap.put("isaccount", true);
				resultMap.put("success", 0);
				resultMap.put(MSG, "请输入注册邮箱或手机，获取重置验证码");
				return resultMap;
			} else {
				if (accountType.equalsIgnoreCase(EMAIL) && !CommonUtils.checkEmail(account)) {
					resultMap.put("isaccount", true);
					resultMap.put("success", 0);
					resultMap.put(MSG, "请输入有效的邮箱地址，例如：david@diyizhan.com");
					return resultMap;

				} else if (accountType.equalsIgnoreCase(MOBILE) && !CommonUtils.checkMobile(account)) {
					resultMap.put("isaccount", true);
					resultMap.put("success", 0);
					resultMap.put(MSG, "请输入正确的手机");
					return resultMap;
				}
			}
			smsTypeEnum = SmsTypeEnum.ResetPassword;
		}

		try {
			/*
			 * 发送短信验证码
			 */
			String code = CommonUtils.generateRandomSix();
			if (accountType.equalsIgnoreCase(MOBILE)) {
				sendSmsCode(account, code, resultMap, smsTypeEnum);
			} else {
				String subject = "重置密码";
				String title = "重置密码验证码";
				String contents = String.format("%s的用户您好，您现在正在重置您的密码，请在页面上输入这串验证码【%s】，点击找回密码完成重置操作", account, code);
				sendMail(account, subject, title, contents);
			}

			/*
			 * 查询当前手机的记录是否存在存在则更新成-1
			 */
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put(MOBILE, account);
			boolean isExists = smsService.isSmsCodeExists(queryMap);

			if (isExists) {
				int id = smsService.querySmsCode(queryMap).getId();
				int count = smsService.deleteSmsCode(id);
				logger.info(String.format("delete %d record, id: %d", count, id));
			}

			SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
			smsCodeDTO.setCode(code);
			smsCodeDTO.setMobile(account);
			smsCodeDTO.setStatus(SmsTypeEnum.Register.getCode());

			smsService.insertSmsCode(smsCodeDTO);
			logger.info("add 1 smscode code record, " + smsCodeDTO);

			/*
			 * 插入smslog记录
			 */
			String content = isReset ? String.format("【重置密码】您正在重置你的第一站密码，请输入短信验证码：%s", code) : String.format(
					"【欢迎使用第一站】您正在注册第一站，请输入短信验证码：%s", code);
			int smsType = smsTypeEnum.getCode();
			SmsLogDTO smsLogDTO = new SmsLogDTO();
			smsLogDTO.setMobile(account);
			smsLogDTO.setContent(content);
			smsLogDTO.setSmsType(smsType);
			smsLogDTO.setStatus(1);

			smsService.insertSmsLog(smsLogDTO);
			logger.info("add 1 smslog record, " + smsLogDTO);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put(SUCCESS, 0);
			resultMap.put(MSG, e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 登录账号
	 * 
	 * @param email
	 *            注册邮箱
	 * @param password
	 *            密码
	 * @param code
	 *            验证码
	 * @param request
	 *            http请求
	 * @return 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public Map<String, Object> doLogin(String account, String password, String code, String accountType,
			Integer source, HttpServletRequest request) {
		logger.info("execute doLogin action...");
		logger.info(String.format("account: %s, password: %s, accountType: %s, source: %d, verifyCode: %s", account,
				password, accountType, source, code));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		MemberDTO memberDTO = null;
		source = source == null ? source = 0 : source;
		queryMap.put("source", source);

		if (accountType.equalsIgnoreCase(EMAIL)) {

			if (!CommonUtils.checkEmail(account)) {
				resultMap.put("returnUrl", "");
				resultMap.put(SUCCESS, false);
				resultMap.put("isaccount", true);
				resultMap.put(MSG, "邮箱格式不正确，请重新输入，例如：david@diyizhan.com");
				logger.info("account: " + account + " is not exists...");
				return resultMap;
			}

			queryMap.put(EMAIL, account);
			memberDTO = memberService.queryMemberInfoByEmail(account, source);

			// 当前用户不存在
			if (memberDTO == null) {
				resultMap.put("returnUrl", "");
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "用户名或密码不正确");
				logger.info("email account: " + account + " is not exists...");
				return resultMap;
			}

			// 当前用户未激活邮箱
			if (memberDTO.getIsVerifyEmail() == 0) {
				resultMap.put("returnUrl", "");
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "注册邮箱还未激活");
				return resultMap;
			}
		} else {

			if (!CommonUtils.checkMobile(account)) {
				resultMap.put("returnUrl", "");
				resultMap.put(SUCCESS, false);
				resultMap.put("isaccount", true);
				resultMap.put(MSG, "手机格式不正确，请重新输入");
				logger.info("mobile account: " + account + " is not exists...");
				return resultMap;
			}

			queryMap.put(MOBILE, account);
			memberDTO = memberService.queryMemberInfoByMobile(account, source);

			if (memberDTO == null) {
				resultMap.put("returnUrl", "");
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "用户名或密码不正确");
				logger.info("account: " + account + " is not exists...");
				return resultMap;
			}
		}

		if (memberDTO != null && DigestUtils.md5Hex(password).equalsIgnoreCase(memberDTO.getPassword())) {
			logger.info("account: " + account + ", passord is correct...");
			// 将登陆信息存在session全局共享
			HttpSession currentSession = request.getSession();
			logger.info("currentSession: " + currentSession.getId());
			currentSession.setAttribute("memberDTO", memberDTO);
			currentSession.setMaxInactiveInterval(SESSION_EXPIRED_TIME);

			// 判断是学生还是企业用户（查看是否填充了基本信息，没有填写需要填写）
			if (memberDTO.getSource() == MemberSourceEnum.Student.getCode()) {
				if (memberDTO.isHasFillBasic()) {
					resultMap.put("returnUrl", "resume/search_joblist?id=" + memberDTO.getMemberId());
					resultMap.put(SUCCESS, true);
					resultMap.put(MSG, "");
				} else {
					resultMap.put("returnUrl", "resume/student_info?id=" + memberDTO.getMemberId());
					resultMap.put(SUCCESS, true);
					resultMap.put(MSG, "");
				}
			} else if (memberDTO.getSource() == MemberSourceEnum.Enterprise.getCode()) {
				if (memberDTO.isHasFillBasic()) {
					resultMap.put("returnUrl", "recruit/company_view?id=" + memberDTO.getMemberId());
					resultMap.put(SUCCESS, true);
					resultMap.put(MSG, "");
				} else {
					resultMap.put("returnUrl", "recruit/company_info_step1?id=" + memberDTO.getMemberId());
					resultMap.put(SUCCESS, true);
					resultMap.put(MSG, "");
				}
			}
		} else {
			logger.info("passwor is not correct, account: " + account + ", password: " + password);
			resultMap.put("returnUrl", "");
			resultMap.put(SUCCESS, false);
			resultMap.put(MSG, "用户名或密码不正确");
		}

		return resultMap;
	}

	/**
	 * 验证注册邮箱或手机是否已经存在
	 * 
	 * @param account
	 *            注册邮箱或手机
	 * @return 账户是否存在
	 */
	@ResponseBody
	@RequestMapping(value = "/accountExists", method = RequestMethod.POST)
	public boolean accountExists(String account, String accountType, Integer pageType) {
		logger.info("execute accountExists..." + account);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (accountType.equalsIgnoreCase(EMAIL)) {
			queryMap.put(EMAIL, account);
		} else {
			queryMap.put(MOBILE, account);
		}
		queryMap.put("source", pageType);
		boolean result = memberService.isMemberExists(queryMap);
		logger.info("result: " + result);
		return !result;
	}

	/**
	 * 验证验证码是否正确
	 * 
	 * @param code
	 *            验证码
	 * @param request
	 *            http请求
	 * @return 是否验证成功
	 */
	@ResponseBody
	@RequestMapping(value = "/validateCode", method = RequestMethod.POST)
	public boolean validateCode(@RequestParam("code") String code, HttpServletRequest request) {
		logger.info("execute validateCode..." + code);
		Object verifyCode = request.getSession().getAttribute("verifyCode");
		String currentCode = StringUtils.EMPTY;
		if (verifyCode == null) {
			return false;
		} else {
			currentCode = verifyCode.toString();
		}
		logger.info("result: " + currentCode);

		boolean isValid = code.equalsIgnoreCase(currentCode);
		return isValid;
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 *            http请求
	 * @param response
	 *            http相应
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
		logger.info("execute verfiyCode action...");
		String randomStr = RandomStringUtils.random(4, CaptchaUtils.codeSequence).toLowerCase();

		CaptchaUtils captcha = new CaptchaUtils().generate(width, height, randomStr);

		HttpSession session = request.getSession();
		session.setAttribute("verifyCode", randomStr);

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos;
		try {
			sos = response.getOutputStream();
			ImageIO.write(captcha.getImage(), "jpeg", sos);
			sos.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 重新发送注册激活邮件
	 * 
	 * @param memberId
	 *            账户id
	 * @param email
	 *            注册邮箱
	 * @return 是否成功
	 */
	@ResponseBody
	@RequestMapping(value = "/resend", method = RequestMethod.POST)
	public Map<String, Object> resend(String memberId, String email) {

		logger.info("execute resend action...");
		String md5MemberEmail = DigestUtils.md5Hex(email);
		String content = String.format("http://%s/member/validate?id=%s&em=%s&timestamp=%s",
				CommonUtils.CONTEXT_URL, memberId, md5MemberEmail, System.currentTimeMillis());

		sendActivationMail(email, content);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(SUCCESS, 1);
		return resultMap;
	}

	/**
	 * 发送账户激活邮件
	 * 
	 * @param toAddress
	 *            收件人地址
	 * @param content
	 *            邮件内容
	 */
	private void sendActivationMail(String toAddress, String content) {
		String contents = "亲爱的用户您好，请点击以下激活链接，激活您的账号, " + String.format("\t激活地址：%s", content);
		String subject = "注册激活邮件";
		String title = "邮箱激活邮件";
		sendMail(toAddress, subject, title, contents);
	}

	/**
	 * 检验激活邮件
	 * 
	 * @param id
	 *            用户id
	 * @param em
	 *            注册邮箱
	 * @param timestamp
	 *            时间戳（失效时间5分钟）
	 * @param request
	 *            http相应
	 * @return 转跳相应用户界面
	 */
	@ResponseBody
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ModelAndView validate(String id, String em, long timestamp, HttpServletRequest request) {
		logger.info("execute validate action...");
		logger.info(String.format("id: %s, em: %s, timestamp: %s", id, em, timestamp));
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		String md5Email = DigestUtils.md5Hex(memberDTO.getEmail());

		long current = System.currentTimeMillis();
		// 超过五分钟则重新返回注册界面, 删除失效的账号
		if (current - timestamp > EXIPRED_TIME) {
			memberDTO.setStatus(-1);
			memberService.updateMemberInfo(memberDTO);
			ModelAndView mav = new ModelAndView("redirect:/register");
			return mav;
		}

		if (md5Email.equalsIgnoreCase(em)) {
			memberDTO.setIsVerifyEmail(1);
			memberDTO.setStatus(1);
			memberService.updateMemberInfo(memberDTO);

			HttpSession currentSession = request.getSession();
			logger.info("currentSession: " + currentSession.getId());
			currentSession.setAttribute("memberDTO", memberDTO);
			currentSession.setMaxInactiveInterval(SESSION_EXPIRED_TIME);

			ModelAndView mav = null;
			if (memberDTO.getSource() == MemberSourceEnum.Student.getCode()) {
				mav = new ModelAndView("redirect:/resume/student_info?id=" + memberDTO.getMemberId());

			} else {
				mav = new ModelAndView("redirect:/recruit/company_info_step1?id=" + memberDTO.getMemberId());
			}

			return mav;
		} else {
			memberDTO.setStatus(-1);
			memberService.updateMemberInfo(memberDTO);
			ModelAndView mav = new ModelAndView("redirect:/register");
			return mav;
		}
	}

	/**
	 * 根据验证码重置连接
	 * 
	 * @param account
	 * @param source
	 * @param accountType
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetStudentPassword", method = RequestMethod.POST)
	public Map<String, Object> resetStudentPassword(String account, Integer source, String accountType, String code) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		source = source == null ? 0 : source;
		logger.info("execute resetStudentPassword password...");
		logger.info(String.format("account: %s, accountType: %s, source: %d, code: %s", account, accountType, source,
				code));
		SmsCodeDTO smsCodeDTO = smsService.querySmsCode(account);
		if (smsCodeDTO != null) {
			long now = System.currentTimeMillis();
			long codeTime = smsCodeDTO.getCreateTime().getTime();

			if (now - codeTime > EXIPRED_TIME) {
				resultMap.put("success", false);
				resultMap.put("isaccount", false);
				resultMap.put(MSG, "当前验证码已失效，请重新获取验证码");
			} else {
				if (smsCodeDTO.getCode().equalsIgnoreCase(code)) {
					String password = "123456";
					if (accountType.equalsIgnoreCase(EMAIL)) {
						MemberDTO memberDTO = memberService.queryMemberInfoByEmail(account, source);
						if (memberDTO != null) {
							memberDTO.setPassword(DigestUtils.md5Hex(password));
							int count = memberService.updateMemberInfo(memberDTO);
							logger.info("update password for email: " + account + ", affecetd count: " + count);
							sendMail(account, "重置密码", "重置密码-新密码",
									String.format("【重置密码】您重置后的默认初始密码为【%s】，请注意保管，尽快登录系统修改密码", password));
						}

					} else {
						MemberDTO memberDTO = memberService.queryMemberInfoByMobile(account, source);
						if (memberDTO != null) {
							memberDTO.setPassword(DigestUtils.md5Hex(password));
							int count = memberService.updateMemberInfo(memberDTO);
							logger.info("update password for mobile: " + account + ", affecetd count: " + count);
							sendSmsCode(account, password, resultMap, SmsTypeEnum.Notification);
						}

						sendSmsCode(account, password, resultMap, SmsTypeEnum.Notification);
					}

					resultMap.put("success", true);
					resultMap.put("isaccount", false);
					resultMap.put(MSG, "");
				} else {
					resultMap.put("success", false);
					resultMap.put("isaccount", false);
					resultMap.put(MSG, "验证码不正确，请重新输入");
				}
			}

		} else {
			resultMap.put("success", false);
			resultMap.put("isaccount", true);
			resultMap.put(MSG, "您输入的注册邮箱/手机不存在，请重新输入");
		}

		return resultMap;
	}

	/**
	 * 更新密码
	 * 
	 * @param memberId
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public Map<String, Object> updatePassword(String memberId, String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			if (memberDTO != null) {
				memberDTO.setPassword(DigestUtils.md5Hex(password));
				int count = memberService.updateMemberInfo(memberDTO);
				logger.info("update " + count + " memberInfo for id: " + memberId);

			}
			resultMap.put("success", true);
			resultMap.put("msg", "");

		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("msg", e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 修改密码
	 * 
	 * @param memberId
	 *            企业账户ID
	 * @param password
	 *            企业账户密码
	 * @return 检查密码是否正确
	 */
	@ResponseBody
	@RequestMapping(value = "/resetPassword", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView resetPassword(String code, Long tt) {
		ModelAndView mav = null;
		String memberId = StringUtils.reverse(code);
		String newPassword = "123456";

		tt = tt == null ? 0 : tt;
		long now = System.currentTimeMillis();
		if (now - tt > EXIPRED_TIME) {
			logger.info("reset password has expired...");
			mav = new ModelAndView("/student_login");
			return mav;
		}

		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		if (memberDTO == null) {
			logger.info("current memberinfo does not existed...");
			mav = new ModelAndView("/student_login");
			return mav;
		}

		int source = memberDTO.getSource();
		if (source == MemberSourceEnum.Enterprise.getCode()) {
			logger.info("execute checkPassword, memberId: " + memberId + ", newPassword: " + newPassword);
			try {
				memberDTO.setPassword(DigestUtils.md5Hex(newPassword));
				int count = memberService.updateMemberInfo(memberDTO);
				logger.info("update " + count + " memberInfo for id: " + memberId);
				String modelAndView = source == MemberSourceEnum.Student.getCode() ? "student_login" : "company_login";
				mav = new ModelAndView(modelAndView);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				mav = new ModelAndView("company_pwd_reset");
			}

		} else {
			String modelAndView = source == MemberSourceEnum.Student.getCode() ? "student_login" : "company_login";
			mav = new ModelAndView(modelAndView);
		}

		return mav;
	}

	/**
	 * 检验密码是否正确
	 * 
	 * @param memberId
	 *            企业账户ID
	 * @param password
	 *            企业账户密码
	 * @return 检查密码是否正确
	 */
	@ResponseBody
	@RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
	public boolean checkPassword(String memberId, String password) {
		logger.info("execute checkPassword, memberId: " + memberId + ", password: " + password);
		boolean result = false;

		MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
		String digestPassword = DigestUtils.md5Hex(password).trim();
		result = memberDTO.getPassword().trim().equals(digestPassword);

		logger.info("result: " + result);
		return result;
	}

	/**
	 * 搜索热门职位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search_job_list", method = RequestMethod.GET)
	public ModelAndView searchJobList() {
		ModelAndView mav = new ModelAndView("search_job_list");
		logger.info("execute search_job_list view...");
		return mav;
	}
}
