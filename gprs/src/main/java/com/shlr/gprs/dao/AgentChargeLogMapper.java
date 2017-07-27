package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.AgentChargeLog;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Administrator
 */
@MapperScan
public interface AgentChargeLogMapper extends Mapper<AgentChargeLog> {

}
