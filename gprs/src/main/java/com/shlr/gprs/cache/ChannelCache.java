package com.shlr.gprs.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.ChannelService;

/**
 * @author xucong
 * @version 创建时间：2017年4月4日 下午8:05:03
 * 
 */
public class ChannelCache {
	public static Map<Integer, Channel> idMap = new HashMap<Integer, Channel>();
	public static List<Map<String,Object>> cacheChannelList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 初始化加载所有通道
	 */
	public static void load() {
		ChannelService channelService = WebApplicationContextManager.getApplicationContext().getBean(ChannelService.class);
		List<Channel> list = channelService.list();
		for (Channel channel : list)
			idMap.put(Integer.valueOf(channel.getId()), channel);
	}
}
