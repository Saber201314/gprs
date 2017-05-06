package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.shlr.gprs.domain.ChannelTemplateCode;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.ChannelTemplateCodeService;

/**
 * @author Administrator
 */

public class ChannelTemplateCodeCache {
	public static Map<Integer, List<ChannelTemplateCode>> codeMapByChannel = new HashMap<Integer, List<ChannelTemplateCode>>();

	public static void load() {
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
	}

}
