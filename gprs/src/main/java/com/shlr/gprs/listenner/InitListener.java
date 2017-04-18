package com.shlr.gprs.listenner;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.cache.ChannelTemplateCache;
import com.shlr.gprs.cache.SuiteOrderCache;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.manager.PayManager;


public class InitListener implements  ApplicationListener<ContextRefreshedEvent>    {
	
	Logger logger=LoggerFactory.getLogger(InitListener.class);
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("初始化");
		WebApplicationContextManager.init(event.getApplicationContext());
		UsersCache.load();
		logger.info("UserCache inited");
		ChannelCache.load();
		logger.info("ChannelCache inited");
		ChannelTemplateCache.load();
		logger.info("ChannelTemplateCache inited");
		PayManager.getInstance().init();

		SuiteOrderCache.getInstance().load();
		
	}
	
	



	
}
