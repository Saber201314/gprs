package com.shlr.gprs.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shlr.gprs.cache.ChannelTemplateCache;
import com.shlr.gprs.dao.ChannelTemplateMapper;
import com.shlr.gprs.domain.ChannelTemplate;

@Service
public class ChannelTemplateService implements DruidStatInterceptor{
	@Resource
	ChannelTemplateMapper channelTemplateMapper;
	
	public List<ChannelTemplate> list(){
		return channelTemplateMapper.selectAll();
	}
	
	public ChannelTemplate findById(Integer id){
		ChannelTemplate selectByPrimaryKey = channelTemplateMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}
	public Integer update(ChannelTemplate template){
		int updateByPrimaryKeySelective = channelTemplateMapper.updateByPrimaryKeySelective(template);
		return updateByPrimaryKeySelective;
	}
	
}