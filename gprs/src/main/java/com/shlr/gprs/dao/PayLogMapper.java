package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.PayLog;

import tk.mybatis.mapper.common.Mapper;

/**
* @author xucong
* @version 创建时间：2017年4月12日 下午9:57:06
* 
*/
@MapperScan
public interface PayLogMapper extends Mapper<PayLog>{

}
