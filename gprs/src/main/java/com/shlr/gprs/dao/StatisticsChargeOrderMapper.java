package com.shlr.gprs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.StatisticsChargeOrder;


@MapperScan
public interface StatisticsChargeOrderMapper {
	List<StatisticsChargeOrder> statisticsChargeOrderByOptionTime(
			@Param("start")String start,@Param("end")String end);
}
