package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.ChargeOrder;

import tk.mybatis.mapper.common.Mapper;

/**
* @author xucong
* @version 创建时间：2017年4月9日 下午10:38:52
* 
*/
@MapperScan
public interface ChargeOrderMapper extends Mapper<ChargeOrder>{

}
