package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.ChannelTemplateCodeMapper;
import com.shlr.gprs.domain.ChannelTemplateCode;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 */
@Service
public class ChannelTemplateCodeService {
	@Resource
	ChannelTemplateCodeMapper channelTemplateCodeMapper;
	
	
	public List<ChannelTemplateCode> list(){
		return channelTemplateCodeMapper.selectAll();
	}
	
	public List<ChannelTemplateCode> listByTemplateId(Example example){
		List<ChannelTemplateCode> selectByExample = channelTemplateCodeMapper.selectByExample(example);
		return selectByExample;
	}
	public Integer save(ChannelTemplateCode channelTemplateCode){
		int insert = channelTemplateCodeMapper.insert(channelTemplateCode);
		return insert;
	}
	public Integer del(List<Integer> ids){
		Integer delTemplateCode = channelTemplateCodeMapper.delTemplateCode(ids);
		return delTemplateCode;
		
	}
}
