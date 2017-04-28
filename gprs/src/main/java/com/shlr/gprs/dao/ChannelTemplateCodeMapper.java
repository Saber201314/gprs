package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.ChannelTemplateCode;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Administrator
 */
@MapperScan
public interface ChannelTemplateCodeMapper extends Mapper<ChannelTemplateCode>{

}
