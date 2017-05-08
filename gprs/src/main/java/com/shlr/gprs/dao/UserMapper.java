package com.shlr.gprs.dao;

import org.apache.ibatis.annotations.Param;
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
	Integer updatebalance(@Param("id")int id,@Param("money")double money);
	
	Double getBalance(@Param("id")int id);
}	
