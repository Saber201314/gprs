package com.shlr.gprs.controller.notify;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.utils.okhttp.HttpUtils;

/**
 * @author Administrator
 */

@Controller
public class TestNotify {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/test.notify")
	public void channelnotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String readRequest;
		try {
			readRequest = HttpUtils.readContent(request);

			if (StringUtils.isEmpty(readRequest)) {
				return;
			}

			logger.info(this.getClass() + readRequest);

			JSONObject parseObject = JSON.parseObject(readRequest);

			String appkey = parseObject.getString("appkey");
			String timestamp = parseObject.getString("timestamp");
			String sign = parseObject.getString("sign");

			JSONArray jsonArray = parseObject.getJSONArray("data");

			String orderid = "";
			String code = "";
			String mobile = "";
			String message = "";
			String messageid = "";

			orderid = parseObject.getString("orderid");
			code = parseObject.getString("code");
			message = parseObject.getString("message");

			if ("1".equals(code)) {
				ChargeManager.getInstance().updateResult(1, orderid, true, message);
			} else {
				ChargeManager.getInstance().updateResult(1, orderid, false, message);
			}
			response.getWriter().print("200");

		} catch (Exception e) {
			response.getWriter().print("fail");
			logger.error(this.getClass().toString(), e);
		}

	}
}
