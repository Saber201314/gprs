package com.shlr.gprs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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
	List<PayLog> selectFirstAndLastPayLogByFrom2To(@Param("account")String account,@Param("from")String from,@Param("to")String to);
	
	
	Map selectRemittanceAndConsumeAndRefundByCondition(@Param("account")String account,@Param("from")String from,@Param("to")String to);


	List<Map> selectNoPayBillMoneyByCondition(@Param("account")String account,@Param("from")String from,@Param("to")String to);

}
