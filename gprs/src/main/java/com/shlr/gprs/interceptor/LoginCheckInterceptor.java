package com.shlr.gprs.interceptor;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.UserService;

/**
 * @author Administrator
 */

public class LoginCheckInterceptor implements HandlerInterceptor{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	UserService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		Users currentUser = service.getCurrentUser(request.getSession());
		response.setCharacterEncoding("UTF-8");
		if(currentUser==null){
			if(requestURI.endsWith(".jsp")){
				response.sendRedirect("/login.jsp");
			}else if(requestURI.endsWith(".action")){
				//判断请求是否ajax
				if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
					
					response.setStatus(403);
					response.setHeader("Content-Type", "application/json;charset=UTF-8");
					PrintWriter writer = response.getWriter();
					JSONObject result = new JSONObject();
					result.put("success", false);
					result.put("msg", "请登录");
					writer.write(result.toJSONString());
				}else{
					response.sendRedirect("/login.jsp");
				}
			}
			
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
