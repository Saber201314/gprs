package com.shlr.gprs.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.domain.MobileArea;
import com.shlr.gprs.services.MobileAreaService;

/**
 * @author Administrator
 */
@Controller
public class TestController {
	@Resource
	MobileAreaService mobileAreaService;
	
	@RequestMapping(value="/test")
	@ResponseBody
	public String test(HttpServletRequest request,String tel){
		MobileArea findByMobile = mobileAreaService.findByMobile(tel.substring(0, 7));
		JSONObject result = new JSONObject();
		result.put("province", findByMobile.getProvince());
		result.put("provider", findByMobile.getServiceProvider());
		return result.toJSONString();
	}
}
