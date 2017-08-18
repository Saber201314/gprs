package com.shlr.gprs.controller.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.domain.StatisticsChargeOrder;
import com.shlr.gprs.services.StatisticsChargeOrderService;
import com.shlr.gprs.utils.JSONUtils;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;

@Controller
@RequestMapping("/admin")
public class StatisticsController {
	@Resource
	StatisticsChargeOrderService statisticsChargeOrderService;
	
	
	@RequestMapping("/statisticsToday.action")
	@ResponseBody
	public String statisticsToday(){
		JSONObject result = new JSONObject();
		Date now = new Date();
		String start = DateUtil.format(now, "yyyy-MM-dd 00:00:00");
		String end = DateUtil.format(now, "yyyy-MM-dd 23:59:59");
		List<StatisticsChargeOrder> statistics = statisticsChargeOrderService.statistics(start, end, null, null);
		if(CollectionUtil.isNotEmpty(statistics)){
			result.put("success", true);
			result.put("list", statistics);
		}else{
			result.put("success", false);
		}
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/statisticsByCondition.action")
	@ResponseBody
	public String statisticsByCondition(String start,String end,String account,Integer channelId){
		JSONObject result = new JSONObject();
		String maccount = null;
		Integer mchannelId = null;
		if(StrUtil.isNotBlank(account)&& !"-1".equals(account)){
			maccount = account;
		}
		if(channelId != null && channelId > 0){
			mchannelId = channelId;
		}
		List<StatisticsChargeOrder> statistics = statisticsChargeOrderService.statistics(start, end, maccount, mchannelId);
		
		if(CollectionUtil.isNotEmpty(statistics)){
			result.put("success", true);
			result.put("list", statistics);
		}else{
			result.put("success", false);
		}
		return JSONUtils.toJsonString(result);
	}
}
