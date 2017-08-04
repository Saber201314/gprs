package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.AgentChargeLogMapper;
import com.shlr.gprs.domain.AgentChargeLog;

import tk.mybatis.mapper.entity.Example;

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
	
	public List<AgentChargeLog> listByEaxmpleAndPage(Example example,Integer pageNo){
		List<AgentChargeLog> selectByExampleAndRowBounds = agentChargeLogMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1) * 30, 30));
		return selectByExampleAndRowBounds;
	}
}
