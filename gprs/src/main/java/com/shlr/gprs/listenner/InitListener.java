package com.shlr.gprs.listenner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.cache.ChannelTemplateCache;
import com.shlr.gprs.cache.ChannelTemplateCodeCache;
import com.shlr.gprs.cache.GprsPackageCache;
import com.shlr.gprs.cache.PricePaperCache;
import com.shlr.gprs.cache.SuiteOrderCache;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.manager.PayManager;

public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

	Logger logger = LoggerFactory.getLogger(InitListener.class);

	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("初始化");
		WebApplicationContextManager.init(event.getApplicationContext());
		UsersCache.load();
		logger.debug("UserCache inited");
		ChannelCache.load();
		logger.debug("ChannelCache inited");
		ChannelTemplateCache.load();
		logger.debug("ChannelTemplateCache inited");
		PayManager.getInstance().init();
		logger.debug("PayManager inited");
		SuiteOrderCache.getInstance().load();
		logger.debug("SuiteOrderCache inited");
		PricePaperCache.load();
		logger.debug("PricePaperCache inited");
		GprsPackageCache.load();
		logger.debug("GprsPackageCache inited");
		ChannelTemplateCodeCache.load();
		logger.debug("ChannelTemplateCodeCache inited");
		try {
			ChargeManager.getInstance().init();
			logger.debug("ChargeManager inited");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		AdvertiseCache.load();

//		SystemSettingCache.load();

//		SuiteCache.load();

//		SuiteOrderManager.getInstance().init();

	}

}
