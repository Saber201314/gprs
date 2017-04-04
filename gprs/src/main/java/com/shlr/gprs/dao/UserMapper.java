package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.Users;

import tk.mybatis.mapper.common.Mapper;


/**
* @author xucong
* @version 创建时间：2017年4月3日 下午2:51:15
* 
*/
@MapperScan
public interface UserMapper extends Mapper<Users> {
	
}
