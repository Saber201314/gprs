package com.shlr.gprs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 */

public class ExceptionHandler implements HandlerExceptionResolver {
	
	Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		StringBuffer requestURL = request.getRequestURL();
		logger.error(""+requestURL, ex);
		return null;
	}

}
