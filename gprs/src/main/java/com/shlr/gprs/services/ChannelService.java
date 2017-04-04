package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.ChannelMapper;
import com.shlr.gprs.domain.Channel;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午8:06:20
* 
*/
@Service
public class ChannelService {
	@Resource
	ChannelMapper channelMapper;
	public List<Channel> list(){
		return channelMapper.selectAll();
		
	}
}
