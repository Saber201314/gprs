package com.shlr.gprs.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.ChannelResource;

import tk.mybatis.mapper.common.Mapper;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午11:30:59
* 
*/
@MapperScan
public interface ChannelResourceMapper extends Mapper<ChannelResource>{
	/**
	 * 查询所有通道资源
	 * @return
	 */
	List<ChannelResource> querylist();
}
