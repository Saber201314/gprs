package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.ChannelTemplate;

import tk.mybatis.mapper.common.Mapper;

/**
* @author xucong
* @version 创建时间：2017年4月6日 下午9:56:12
* 
*/
@MapperScan
public interface ChannelTemplateMapper extends Mapper<ChannelTemplate>{

}
