package com.shlr.gprs.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.AgentChargeOrderMapper;
import com.shlr.gprs.dao.ChargeOrderMapper;
import com.shlr.gprs.domain.AgentChargeOrder;
import com.shlr.gprs.domain.ChargeOrder;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
* @author xucong
* @version 创建时间：2017年4月9日 下午10:39:48
* 
*/
@Service
public class ChargeOrderService implements DruidStatInterceptor{
	
	@Resource
	ChargeOrderMapper chargeOrderMapper;
	@Resource
	AgentChargeOrderMapper agentChargeOrderMapper;
	
	public List<ChargeOrder> listByExampleAndPage(Example example,Integer pageNo){
		return chargeOrderMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
	
	public List<AgentChargeOrder> listAgentByExampleAndPage(Example example,Integer pageNo){
		return agentChargeOrderMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
	
	public List<ChargeOrder> listByExample(Example example){
		return chargeOrderMapper.selectByExample(example);
	}
	public List<ChargeOrder> findOneByTaskIdAndTemplateId(String taskId,Integer templateId){
		Example example=new Example(ChargeOrder.class,true,false);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("chargeTaskId", taskId);
		createCriteria.andEqualTo("submitTemplate", templateId);
		createCriteria.andEqualTo("chargeStatus", 2);
		List<ChargeOrder> selectByExample = chargeOrderMapper.selectByExample(example);
		return selectByExample;
		
	}
	
	public Integer saveOrUpdate(ChargeOrder chargeOrder){
		ChargeOrder order = chargeOrderMapper.selectByPrimaryKey(chargeOrder.getId());
		int result;
		if (order == null) {
			result=chargeOrderMapper.insertSelective(chargeOrder);
		}else{
			result=chargeOrderMapper.updateByPrimaryKeySelective(chargeOrder);
		}
		return result;
	}
	public Integer updateCacheFlag(List<Integer> ids){
		return chargeOrderMapper.updateCacheFlag(ids);
	}
	//更新充值状态
	public Integer updateChargeStatus(String id, Integer templateId,Date reportTime, Integer status,String error){
		
		return chargeOrderMapper.updateChargeStatus(status, error, reportTime, id, templateId);
		
	}

}
