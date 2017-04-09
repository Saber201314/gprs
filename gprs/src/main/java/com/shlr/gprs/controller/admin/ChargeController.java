package com.shlr.gprs.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.manager.ChargeManager;

@Controller
@RequestMapping(value="/admin")
public class ChargeController {
	
	
	
	
	/**
	 * 获取当前操作状态
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/getCurrentOperationStatus.action")
	public String getCurrentOperationStatus(HttpServletResponse response) throws IOException {
		JSONObject result=new JSONObject();
		result.put("cache", ChargeManager.moreOperValidMap);
		response.getWriter().print(result.toJSONString());
		return null;
	}
}
