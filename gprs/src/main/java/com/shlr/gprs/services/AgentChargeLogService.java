package com.shlr.gprs.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.AgentChargeLogMapper;
import com.shlr.gprs.domain.AgentChargeLog;

/**
 * @author Administrator
 */
@Service
public class AgentChargeLogService {
	@Resource
	AgentChargeLogMapper agentChargeLogMapper;
	
	public Integer add(AgentChargeLog agentChargeLog){
		return agentChargeLogMapper.insertSelective(agentChargeLog);
	}
}
