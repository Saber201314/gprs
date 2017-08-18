package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.ChannelService;

/**
 * @author xucong
 * @version 创建时间：2017年4月4日 下午8:05:03
 * 
 */
public class ChannelCache {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public Map<Integer, Channel> idMap = new HashMap<Integer, Channel>();
	
	public ConcurrentHashMap<String, Boolean> cacheCondition = new ConcurrentHashMap<String, Boolean>();
	
	public static ChannelCache getInstance(){
		return ChannelCacheHolder.channelCache;
	}
	
	static class ChannelCacheHolder{
		static ChannelCache channelCache = new ChannelCache();
	}
	
	/**
	 * 初始化加载所有通道
	 */
	public  void load() {
		long start = System.currentTimeMillis();
		logger.info("{} initialization started",this.getClass().getSimpleName());
		
		ChannelService channelService = WebApplicationContextManager.getApplicationContext().getBean(ChannelService.class);
		List<Channel> list = channelService.list();
		for (Channel channel : list){
			idMap.put(channel.getId(), channel);
			if(channel.getStatus() == -1){
				cacheCondition.put(String.valueOf(channel.getId()), true);
			}
		}
		long end = System.currentTimeMillis();
		logger.info("{} initialization completed in {} ms ",this.getClass().getSimpleName(),end-start);
	}
	public void updateCache(Channel channel){
		idMap.put(channel.getId(), channel);
	}
	
}
