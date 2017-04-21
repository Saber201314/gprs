package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.ChannelLogMapper;
import com.shlr.gprs.domain.ChannelLog;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 */

@Service
public class ChannelLogService implements DruidStatInterceptor{
	@Resource
	ChannelLogMapper channelLogMapper;
	
	public List<ChannelLog> listByExampleAndPage(Example example,Integer pageNo){
		return channelLogMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
}
