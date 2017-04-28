package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.ChannelTemplateCodeMapper;
import com.shlr.gprs.domain.ChannelTemplateCode;

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
}
