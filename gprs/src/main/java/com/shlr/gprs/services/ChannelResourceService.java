package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.ChannelResourceMapper;
import com.shlr.gprs.domain.ChannelResource;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午7:57:32
* 
*/
@Service
public class ChannelResourceService {
	
	@Resource
	ChannelResourceMapper channelResourceMapper;
	
	public List<ChannelResource> queryList(){
		
		return channelResourceMapper.querylist();
		
	}

}
