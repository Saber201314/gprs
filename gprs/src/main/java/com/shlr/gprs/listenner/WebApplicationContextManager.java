package com.shlr.gprs.listenner;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author xucong
 * @version 创建时间：2017年4月4日 下午4:28:00
 * 
 */
public final class WebApplicationContextManager {
	private static ApplicationContext application = null;

	public static synchronized void init(ServletContext context) {
		if (application == null) {
			application = WebApplicationContextUtils.getWebApplicationContext(context);
		}
	}

	public static synchronized void init(ApplicationContext context) {
		if (application == null) {
			application = context;
		}
	}
	public static ApplicationContext getApplicationContext() {
		return application;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	

}
