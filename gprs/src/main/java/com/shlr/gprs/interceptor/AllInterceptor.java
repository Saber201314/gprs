package com.shlr.gprs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
* @author xucong
* @version 创建时间：2017年4月3日 下午7:07:00
* 
*/
@Component
public class AllInterceptor implements HandlerInterceptor   {

	Logger logger=LoggerFactory.getLogger(AllInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		switch (requestURI) {
		case "/WEB-INF/common/404.html":
			break;
		default:
			logger.info(" User-Agent  {}",request.getHeader("user-agent"));
			logger.info(" 访问地址-----  {}         来路地址  {} ",requestURI,request.getRemoteAddr());
			break;
		}
		
		
		
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
