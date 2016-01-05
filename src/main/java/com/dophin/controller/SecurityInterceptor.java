package com.dophin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dophin.dto.AuthCompanyException;
import com.dophin.dto.AuthStudentException;

/**
 * 登录设置拦截器（根据登录用户的session是否存在进行请求转发）
 * 
 * @author dailiwei
 * 
 */
public class SecurityInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(SecurityInterceptor.class);
	private List<String> excludeUrls = new ArrayList<String>();

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		boolean isCompanyLogin = false;
		String requestUrl = request.getRequestURI();
		isCompanyLogin = requestUrl.contains("recruit");
		logger.info(String.format("requestUrl: %s", requestUrl));
		for (String url : excludeUrls) {
			if (requestUrl.contains(url)) {
				return true;
			}
		}

		HttpSession session = request.getSession();
		if (session.getAttribute("memberDTO") == null) {
			if (isCompanyLogin) {
				throw new AuthCompanyException();
			} else {
				throw new AuthStudentException();
			}
		} else {
			return true;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}
}
