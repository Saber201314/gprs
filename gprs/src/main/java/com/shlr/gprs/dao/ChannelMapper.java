package com.shlr.gprs.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.Channel;

import tk.mybatis.mapper.common.Mapper;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午8:12:35
* 
*/
@MapperScan
public interface ChannelMapper extends Mapper<Channel> {
	Integer updateChannelStatus(@Param("id")Integer id,@Param("status")Integer status);
}
