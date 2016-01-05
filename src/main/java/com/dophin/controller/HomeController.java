package com.dophin.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dophin.dto.EmailTaskDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.enums.MemberSourceEnum;
import com.dophin.service.EmailTaskService;
import com.dophin.service.MemberService;
import com.dophin.utils.CommonUtils;

/**
 * 主页访问类
 * 
 * @author dailiwei
 * 
 */
@Controller
public class HomeController {

	private static Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private EmailTaskService emailTaskService;

	private static final int RESETPWD_EXPIRED_TIME = 1000 * 60 * 10;

	private static Map<String, String> mailSiteMap = new HashMap<>();

	static {
		mailSiteMap.put("126.com", "http://www.126.com");
		mailSiteMap.put("163.com", "http://mail.163.com");
		mailSiteMap.put("qq.com", "https://mail.qq.com/");
		mailSiteMap.put("sina.com", "https://mail.sina.com.cn/");
		mailSiteMap.put("aliyun.com", "https://mail.aliyun.com");
		mailSiteMap.put("gmail.com", "https://mail.google.com/");
	}

	/**
	 * 根目录转跳到学生首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		logger.info("execute index redirect view...");
		ModelAndView mav = new ModelAndView("student_index");
		return mav;
	}

	/**
	 * 第一站学生用户首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/student_index", method = RequestMethod.GET)
	public ModelAndView studentIndex() {
		logger.info("execute studentIndex view...");
		ModelAndView mav = new ModelAndView("student_index");
		return mav;
	}

	/**
	 * 第一站企业用户首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/company_index", method = RequestMethod.GET)
	public ModelAndView companyIndex() {
		logger.info("execute companyIndex view...");
		ModelAndView mav = new ModelAndView("company_index");
		return mav;
	}

	/**
	 * 学生注册首页（学生-1，公司-2）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/student_register", method = RequestMethod.GET)
	public ModelAndView studentRegister() {
		logger.info("execute register view...");
		ModelAndView mav = new ModelAndView("student_register");
		mav.addObject("pageType", MemberSourceEnum.Student.getCode());
		return mav;
	}

	/**
	 * 企业注册首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/company_register", method = RequestMethod.GET)
	public ModelAndView companyRegister() {
		logger.info("execute register view...");
		ModelAndView mav = new ModelAndView("company_register");
		mav.addObject("pageType", MemberSourceEnum.Enterprise.getCode());
		return mav;
	}

	/**
	 * 学生用户登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/company_login", method = RequestMethod.GET)
	public ModelAndView companyLogin() {
		logger.info("execute register view...");
		ModelAndView mav = new ModelAndView("company_login");
		mav.addObject("pageType", MemberSourceEnum.Enterprise.getCode());
		return mav;
	}

	/**
	 * 企业登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/student_login", method = RequestMethod.GET)
	public ModelAndView studentLogin() {
		logger.info("execute register view...");
		ModelAndView mav = new ModelAndView("student_login");
		mav.addObject("pageType", MemberSourceEnum.Student.getCode());
		return mav;
	}

	/**
	 * 注册激活页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/register_active", method = RequestMethod.GET)
	public ModelAndView registerActive(String id) {
		logger.info("execute register_active view...");
		MemberDTO memberDTO = memberService.queryMemberInfo(id);
		String suffix = memberDTO.getCompanyEmailSuffix();

		String mailGatewayAddress = "https://mail.qq.com";
		if (mailSiteMap.containsKey(suffix)) {
			mailGatewayAddress = mailSiteMap.get(suffix);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberDTO", memberDTO);
		model.put("mailGatewayAddress", mailGatewayAddress);
		ModelAndView mav = new ModelAndView("register_active", model);
		return mav;
	}

	/**
	 * 企业用户密码重置页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/company_pwd_reset", method = RequestMethod.GET)
	public ModelAndView companyPwdReset() {
		logger.info("execute company_pwd_reset view...");
		ModelAndView mav = new ModelAndView("company_pwd_reset");
		mav.addObject("pageType", MemberSourceEnum.Enterprise.getCode());
		return mav;
	}

	/**
	 * 发送重置密码链接邮件（企业用户）
	 * 
	 * @param account
	 * @param source
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendCompanyPwdResetInfo", method = RequestMethod.POST)
	public Map<String, Object> sendCompanyPwdResetInfo(String account, Integer source) {
		logger.info("execute sendCompanyPwdResetInfo action ...");
		Map<String, Object> resultMap = new HashMap<>();
		try {
			source = source == null ? 0 : source;
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("email", account);
			queryMap.put("source", source);
			MemberDTO memberDTO = memberService.queryMemberInfo(queryMap);

			if (memberDTO == null) {
				logger.info("account does not exists: " + account);
				resultMap.put("success", false);
				resultMap.put("msg", "当前注册邮箱并未注册过第一站，请重新输入");
			} else {
				String content = String.format("http://%s/member/resetPassword?code=%s&tt=%d", CommonUtils.CONTEXT_URL,
						StringUtils.reverse(memberDTO.getMemberId()), System.currentTimeMillis());
				sendResetEmail(account, content);
				resultMap.put("success", true);
				resultMap.put("msg", "");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMap.put("success", false);
			resultMap.put("msg", e.getMessage());
		}

		return resultMap;
	}

	/**
	 * 发送重置密码邮箱
	 * 
	 * @param toAddress
	 * @param content
	 */
	private void sendResetEmail(String toAddress, String content) {
		Email email = new SimpleEmail();
		email.setHostName(CommonUtils.MAIL_SMTP_HOST);
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator(CommonUtils.MAIL_USER, CommonUtils.MAIL_PASSWORD));
		email.setSSLOnConnect(false);
		email.setCharset("UTF-8");
		try {
			email.setFrom(CommonUtils.MAIL_USER);
			email.setSubject("密码重置邮件");
			String contents = "亲爱的用户您好，请点击以下激活链接，请点击以下连接重置您的账号： " + content;
			email.setMsg(contents.toString());
			email.addTo(toAddress);
			email.send();

			// 插入重置密码记录
			EmailTaskDTO emailTaskDTO = new EmailTaskDTO();
			emailTaskDTO.setMailto(toAddress);
			emailTaskDTO.setTitle("密码重置邮件");
			emailTaskDTO.setContent(contents.toString());
			emailTaskService.insertEmailTaskInfo(emailTaskDTO);

		} catch (EmailException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 学生用户密码重置页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/student_pwd_reset", method = RequestMethod.GET)
	public ModelAndView studentPwdReset() {
		logger.info("execute company_pwd_reset view...");
		ModelAndView mav = new ModelAndView("student_pwd_reset");
		mav.addObject("pageType", MemberSourceEnum.Student.getCode());
		return mav;
	}

	@RequestMapping(value = "/companylist", method = RequestMethod.GET)
	public ModelAndView companylist() {
		logger.info("execute companylist view...");
		ModelAndView mav = new ModelAndView("companylist");
		return mav;
	}

	@RequestMapping(value = "/jianli", method = RequestMethod.GET)
	public ModelAndView jianli() {
		logger.info("execute jianli view...");
		ModelAndView mav = new ModelAndView("jianli");
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		logger.info("execute create view...");
		ModelAndView mav = new ModelAndView("create");
		return mav;
	}

	@RequestMapping(value = "/to_reset", method = RequestMethod.GET)
	public ModelAndView toReset() {
		logger.info("execute to_reset view...");
		ModelAndView mav = new ModelAndView("to_reset");
		return mav;
	}

	@RequestMapping(value = "/reset_email_back", method = RequestMethod.GET)
	public ModelAndView resetEmailBack(String id) {
		logger.info("execute reset_email_back view...");
		ModelAndView mav = new ModelAndView("reset_email_back");
		MemberDTO memberDTO = memberService.queryMemberInfo(id);

		if (memberDTO != null) {
			mav.addObject("memberDTO", memberDTO);
		}

		return mav;
	}

	@RequestMapping(value = "/verify_reset_email", method = RequestMethod.GET)
	public ModelAndView verifyResetEmail(String code, long tt) {
		logger.info("execute verify_reset_email view...");
		logger.info(String.format("code: %s, timestamp: %d", code, tt));
		long now = System.currentTimeMillis();
		if (now - tt > RESETPWD_EXPIRED_TIME) {
			ModelAndView mav = new ModelAndView("toReset");
			return mav;
		} else {
			String memberId = StringUtils.reverse(code);
			MemberDTO memberDTO = memberService.queryMemberInfo(memberId);
			ModelAndView mav = new ModelAndView("verify_reset_email");
			mav.addObject("memberDTO", memberDTO);

			return mav;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/doResetPassword2", method = RequestMethod.POST)
	public Map<String, Object> doResetPassword2(String memberid, String new_password) {
		logger.info("execute doResetPassword action...");
		MemberDTO memberDTO = memberService.queryMemberInfo(memberid);
		memberDTO.setPassword(DigestUtils.md5Hex(new_password));
		int count = memberService.updateMemberInfo(memberDTO);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (count > 0) {
			resultMap.put("returnUrl", "home?id=" + memberid);
			resultMap.put("success", true);
			resultMap.put("msg", "");
		} else {
			resultMap.put("returnUrl", "to_reset");
			resultMap.put("success", false);
			resultMap.put("msg", "重置密码失败");
		}
		return resultMap;
	}

	@RequestMapping(value = "test_tab", method = RequestMethod.GET)
	public ModelAndView testTab() {
		ModelAndView mav = new ModelAndView("test_tab");
		return mav;
	}
	
	@RequestMapping(value = "/uploadtest", method = RequestMethod.GET)
	public ModelAndView uploadtest() {
		ModelAndView mav = new ModelAndView("uploadTest");
		return mav;
	}

	/**
	 * 测试QQ快速登录界面
	 */
	@RequestMapping(value = "/testQQLogin", method = RequestMethod.GET)
	public void testQuickLoginForQQ() {

	}

	@RequestMapping(value = "/testWechatLogin", method = RequestMethod.GET)
	public void testQuickLoginForWechat() {

	}
}
