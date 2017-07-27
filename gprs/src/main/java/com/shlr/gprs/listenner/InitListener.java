package com.shlr.gprs.listenner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.cache.ChannelTemplateCache;
import com.shlr.gprs.cache.ChannelTemplateCodeCache;
import com.shlr.gprs.cache.PricePaperCache;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.manager.PayManager;

public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(InitListener.class);

	public void onApplicationEvent(ContextRefreshedEvent event) {
		long start = System.currentTimeMillis();
		logger.info("GPRS initialization started----------------------");
		
		WebApplicationContextManager.init(event.getApplicationContext());
		
		UsersCache.getInstance().load();
		
		ChannelCache.getInstance().load();
		
		ChannelTemplateCache.getInstance().load();

		PayManager.getInstance().init();
		
		
		PricePaperCache.getInstance().load();
		
//		GprsPackageCache.load();
		
		ChannelTemplateCodeCache.getInstance().load();
		
		ChargeManager.getInstance().init();
		
//		AdvertiseCache.load();

//		SystemSettingCache.load();

//		SuiteCache.load();

//		SuiteOrderManager.getInstance().init();
		long end = System.currentTimeMillis();
		logger.info("GPRS initialization completed in {} ms----------------------",end-start);
	}

}
