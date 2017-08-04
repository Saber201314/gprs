package com.shlr.gprs.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	Integer forceToFailOrder(@Param("id")Integer id,
			@Param("cache_flag")Integer cache_flag,
			@Param("charge_status")Integer charge_status,
			@Param("error")String error,
			@Param("report_time")Long report_time);
	Integer updateChargeStatus(
			@Param("charge_status")Integer charge_status,
			@Param("report_content")String report_content,
			@Param("report_time")Date report_time,
			@Param("charge_task_id")String charge_task_id,
			@Param("submit_template")Integer submit_template);
	
	Integer updateCacheFlag(@Param("ids")List<Integer> ids);
	
	List<ChargeOrder> qaueryMonthAmount(@Param("submit_time")Date submit_time);
	
}
