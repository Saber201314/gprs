package com.shlr.gprs.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.PayLogMapper;
import com.shlr.gprs.domain.PayLog;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
* @author xucong
* @version 创建时间：2017年4月12日 下午9:55:20
* 
*/
@Service
public class PayLogService implements DruidStatInterceptor{
	
	@Resource
	PayLogMapper payLogMapper;
	
	
	public Integer update(PayLog payLog){
		return payLogMapper.updateByPrimaryKeySelective(payLog);
	}
	public Integer saveOrUpdate(PayLog payLog){
		PayLog selectByPrimaryKey = payLogMapper.selectByPrimaryKey(payLog.getId());
		int num;
		if (selectByPrimaryKey==null) {
			num = payLogMapper.insertSelective(payLog);
		}else{
			num = payLogMapper.updateByPrimaryKeySelective(payLog);
		}
		return num;
	}
	/**
	 * 根据条件分页查询PayLog,
	 * @param example
	 * @return
	 */
	public List<PayLog> listByExample(Example example){
		
		
		return payLogMapper.selectByExample(example);
	}
	
	public PayLog findOne(PayLog payLog){
		return payLogMapper.selectOne(payLog);
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
	public List<Map> selectNoPayBillMoneyByCondition(String account,String from,String to){
		return payLogMapper.selectNoPayBillMoneyByCondition(account, from, to);
	}
}
