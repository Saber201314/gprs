package com.shlr.gprs.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.AgentChargeOrder;
import com.shlr.gprs.domain.ChargeOrder;

import tk.mybatis.mapper.common.Mapper;

/**
* @author xucong
* @version 创建时间：2017年4月9日 下午10:38:52
* 
*/
@MapperScan
public interface AgentChargeOrderMapper extends Mapper<AgentChargeOrder>{
	
	
}
