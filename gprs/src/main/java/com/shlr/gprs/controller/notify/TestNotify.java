package com.shlr.gprs.controller.notify;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.constants.LogEnum;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.utils.HttpUtil;
import com.shlr.gprs.utils.okhttp.HttpUtils;

/**
 * @author Administrator
 */

@Controller
public class TestNotify {

	Logger logger = LoggerFactory.getLogger(LogEnum.NOTIFY);

	@RequestMapping(value="/get.notify",method=RequestMethod.GET)
	@ResponseBody
	public String channelnotify(HttpServletRequest request) throws IOException {
		try{
			Map<String, String> param = new HashMap<String, String>();
			request.setCharacterEncoding("UTF-8");
			String queryString = request.getQueryString();
			Enumeration<String> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String name = parameterNames.nextElement();
				String value = request.getParameter(name);
				param.put(name, value);
			}
			logger.info("{} <<<<<=====  {}   {}",request.getRemoteAddr(),request.getRequestURI(),queryString);
			String code = param.get("code");
			String orderId = param.get("orderId");
			String msg = param.get("msg");
			if ("0".equals(code)) {
				ChargeManager.getInstance().updateResult(1, orderId, true, code+":"+msg);
			} else if("1".equals(code)) {
				ChargeManager.getInstance().updateResult(1, orderId, false, code+":"+msg);
			}
		} catch (Exception e) {
			logger.error(this.getClass().toString(), e);
		}
		return "200";

	}
	@RequestMapping(value="/post.notify",method=RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String notify(HttpServletRequest request) throws IOException {
		try{
			String jsonString = HttpUtil.getJsonString(request);
			if(jsonString  == null){
				return null;
			}
			logger.info("{} <<<<<=====  {}   {}",request.getRemoteAddr(),request.getRequestURI(),jsonString);
//			JSONObject jsonbody = JSON.parseObject(body);
			//[{"TaskID":652602,"Mobile":"15010316298","Status":4,"ReportTime":"2015-12-20T16:35:54.3","ReportCode":"2：成功"}]
			JSONArray parseArray = JSON.parseArray(jsonString);
			
			System.out.println(jsonString);
//			for (int i = 0 ,size = parseArray.size() ; i < size; i++) {
//				JSONObject jsonObject = parseArray.getJSONObject(i);
//				Integer code = jsonObject.getInteger("code");
//				String orderId = jsonObject.getString("orderId");
//				String msg = jsonObject.getString("msg");
//				if ("0".equals(code)) {
//					ChargeManager.getInstance().updateResult(1, orderId, true, code+":"+msg);
//				} else if("1".equals(code)) {
//					ChargeManager.getInstance().updateResult(1, orderId, false, code+":"+msg);
//				}
//			}

		} catch (Exception e) {
			logger.error(this.getClass().toString(), e);
		}
		return "ok";
	}
}
