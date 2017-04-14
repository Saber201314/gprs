package com.shlr.gprs.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.PayLogMapper;
import com.shlr.gprs.domain.PayLog;

import tk.mybatis.mapper.entity.Example;

/**
* @author xucong
* @version 创建时间：2017年4月12日 下午9:55:20
* 
*/
@Service
public class PayLogService implements DruidStatInterceptor{
	
	@Resource
	PayLogMapper payLogMapper;
	
	/**
	 * 根据条件查询所有PayLog
	 * @param example
	 * @return
	 */
	public List<PayLog> listByExample(Example example){
		return payLogMapper.selectByExample(example);
	}
	
	/**
	 * 根据条件分页查询PayLog
	 * @param example
	 * @param pageNo
	 * @return
	 */
	public List<PayLog> listByExampleAndPage(Example example,Integer pageNo){
		return payLogMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
	
	/**
	 * 根据条件查询第一笔和最后一笔PayLog信息
	 * @param account
	 * @param from
	 * @param to
	 * @return
	 */
	public List<PayLog> selectFirstAndLastPayLogByFrom2To(String account,String from,String to){
		
		return payLogMapper.selectFirstAndLastPayLogByFrom2To(account, from, to);
	}
	/**
	 * 根据条件查询汇款、消费、退款
	 * @param account
	 * @param from
	 * @param to
	 * @return
	 */
	public Map selectRemittanceAndConsumeAndRefundByCondition(String account,String from,String to){
		return payLogMapper.selectRemittanceAndConsumeAndRefundByCondition(account, from, to);
	}
	/**
	 * 根据条件查询不带票金额
	 * @param account
	 * @param from
	 * @param to
	 * @return
	 */
	public Map selectNoPayBillMoneyByCondition(String account,String from,String to){
		return payLogMapper.selectNoPayBillMoneyByCondition(account, from, to);
	}
}
