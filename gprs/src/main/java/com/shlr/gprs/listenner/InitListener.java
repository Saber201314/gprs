package com.shlr.gprs.listenner;


import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.cache.UsersCache;


public class InitListener implements  ApplicationListener<ContextRefreshedEvent>    {

	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("初始化");
		WebApplicationContextManager.init(event.getApplicationContext());
		UsersCache.load();
		System.out.println("UserCache inited");
		ChannelCache.load();
		System.out.println("ChannelCache inited");

		
	}
	
	



	
}
