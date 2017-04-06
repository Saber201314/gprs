package com.shlr.gprs.cache;

import com.shlr.gprs.domain.ChannelTemplate;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.ChannelTemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelTemplateCache {
	public static Map<String, ChannelTemplate> identityMap = new HashMap<String, ChannelTemplate>();

	public static void load() {
		ChannelTemplateService channelTemplateService = WebApplicationContextManager.getApplicationContext()
				.getBean(ChannelTemplateService.class);
		List<ChannelTemplate> templateList = channelTemplateService.list();
		for (ChannelTemplate template : templateList)
			identityMap.put(template.getIdentity(), template);
	}
}