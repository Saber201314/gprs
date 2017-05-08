package com.shlr.gprs.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.ChargeOrderMapper;
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
	
	public List<ChargeOrder> listByExampleAndPage(Example example,Integer pageNo){
		return chargeOrderMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
	
	public List<ChargeOrder> selectOneByExample(String taskId,Integer templateId){
		Example example=new Example(ChargeOrder.class,true,false);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("chargeTaskId", taskId);
		createCriteria.andEqualTo("submitTemplate", templateId);
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
	
	public Integer forceToFailOrder(Integer id, Integer cacheFlag, Integer status,	String error){
		
		return chargeOrderMapper.forceToFailOrder(id, cacheFlag, status, error, new Date().getTime());
		
	}
	public Integer updateChargeStatus(String id, Integer templateId, Integer status,String error){
		
		return chargeOrderMapper.updateChargeStatus(status, error, new Date().getTime(), id, templateId);
		
	}

}
