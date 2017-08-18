package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.StatisticsChargeOrderMapper;
import com.shlr.gprs.domain.StatisticsChargeOrder;

@Service
public class StatisticsChargeOrderService {
	@Resource
	StatisticsChargeOrderMapper statisticsChargeOrderMapper;
	
	public List<StatisticsChargeOrder> statistics(String start,String end,String account,Integer channelId){
		return statisticsChargeOrderMapper.statisticsChargeOrder(start, end, account, channelId);
	}
}
