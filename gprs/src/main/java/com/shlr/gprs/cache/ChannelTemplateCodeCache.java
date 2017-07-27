package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shlr.gprs.domain.ChannelTemplateCode;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.ChannelTemplateCodeService;

/**
 * @author Administrator
 */

public class ChannelTemplateCodeCache {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static Map<Integer, List<ChannelTemplateCode>> codeMapByChannel = new HashMap<Integer, List<ChannelTemplateCode>>();

	public static ChannelTemplateCodeCache getInstance(){
		return ChannelTemplateCodeCacheHolder.channelTemplateCodeCache;
		
	}
	
	static class ChannelTemplateCodeCacheHolder{
		static ChannelTemplateCodeCache channelTemplateCodeCache = new ChannelTemplateCodeCache();
	}
	
	public void load() {
		long start = System.currentTimeMillis();
		logger.info("{} initialization started",this.getClass().getSimpleName());
		
		ChannelTemplateCodeService channelTemplateCodeService = WebApplicationContextManager.getApplicationContext().getBean(ChannelTemplateCodeService.class);

		List<ChannelTemplateCode> codeList = channelTemplateCodeService.list();
		for (ChannelTemplateCode code : codeList) {
			List<ChannelTemplateCode> list =  codeMapByChannel.get(Integer.valueOf(code.getTemplateId()));
			if (list == null) {
				list = new LinkedList<ChannelTemplateCode>();
				codeMapByChannel.put(Integer.valueOf(code.getTemplateId()), list);
			}
			list.add(code);
		}
		
		long end = System.currentTimeMillis();
		logger.info("{} initialization completed in {} ms ",this.getClass().getSimpleName(),end-start);
	}

}
