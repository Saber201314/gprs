package com.shlr.gprs.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.domain.AgentChargeLog;
import com.shlr.gprs.services.AgentChargeLogService;
import com.shlr.gprs.utils.JSONUtils;
import com.xiaoleilu.hutool.util.StrUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class AgentChargeController {
	@Resource
	AgentChargeLogService agentChargeLogService;
	
	@ResponseBody
	@RequestMapping("/getAgentChargeList.action")
	public String getAgentChargeList(@RequestParam(value="pageNo",defaultValue="1") String pageNo,
			@RequestParam(required=false)String account,String from,String to,Integer payType){
		JSONObject result = new JSONObject();
		
		Example example = new Example(AgentChargeLog.class);
		Criteria createCriteria = example.createCriteria();
		if(StrUtil.isNotBlank(account) && !"-1".equals(account) ){
			createCriteria.andEqualTo("account",account);
		}
		if(payType !=null && payType != -1){
			createCriteria.andEqualTo("payType",payType);
		}
		createCriteria.andBetween("optionTime", from, to);
		example.setOrderByClause("option_time desc");
		List<AgentChargeLog> listByEaxmpleAndPage = agentChargeLogService.listByEaxmpleAndPage(example, Integer.valueOf(pageNo));
		Page<AgentChargeLog> page = (Page<AgentChargeLog>) listByEaxmpleAndPage;  
		result.put("list", listByEaxmpleAndPage);
		result.put("total", page.getTotal());
		result.put("pages", page.getPages());
		result.put("pageno", page.getPageNum());
		return JSONUtils.toJsonString(result);
	}
}
