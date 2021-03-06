package com.shlr.gprs.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.UserService;

/**
* @author xucong
* @version 创建时间：2017年4月3日 下午7:07:00
* 
*/
public class AllInterceptor implements HandlerInterceptor   {

	Logger logger=LoggerFactory.getLogger(AllInterceptor.class);
	@Resource
	UserService service;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("[{}] - [{}] - [{}] ",request.getRemoteAddr(),request.getMethod(),request.getRequestURI());
		logger.info("User-Agent :{}",request.getHeader("User-Agent"));
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
