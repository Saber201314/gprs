package com.shlr.gprs.cache;

import com.shlr.gprs.domain.ChannelTemplate;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.ChannelTemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelTemplateCache {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static Map<String, ChannelTemplate> identityMap = new HashMap<String, ChannelTemplate>();
	
	public static ChannelTemplateCache getInstance(){
		return ChannelTemplateCacheHolder.channelTemplateCache;
	}
	
	static class ChannelTemplateCacheHolder{
		static ChannelTemplateCache channelTemplateCache = new ChannelTemplateCache();
	}

	public void load() {
		long start = System.currentTimeMillis();
		logger.info("{} initialization started",this.getClass().getSimpleName());
		
		ChannelTemplateService channelTemplateService = WebApplicationContextManager.getApplicationContext()
				.getBean(ChannelTemplateService.class);
		List<ChannelTemplate> templateList = channelTemplateService.list();
		for (ChannelTemplate template : templateList){
			identityMap.put(template.getIdentity(), template);
		}
		long end = System.currentTimeMillis();
		logger.info("{} initialization completed in {} ms ",this.getClass().getSimpleName(),end-start);
	}
}