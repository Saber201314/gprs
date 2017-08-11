package com.shlr.gprs.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.ChargeReport;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午2:36:18
* 
*/
@MapperScan
public interface ChargeReportMapper {
	List<ChargeReport> queryCurDayList();
}
