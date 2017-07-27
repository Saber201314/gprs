package com.shlr.gprs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.ChannelTemplateCode;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Administrator
 */
@MapperScan
public interface ChannelTemplateCodeMapper extends Mapper<ChannelTemplateCode>{
	Integer delTemplateCode(@Param("ids")List<Integer> ids);
}
