package com.shlr.gprs.manager.charge.template;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.domain.ChannelLog;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.manager.charge.ChargeTemplate;
import com.shlr.gprs.utils.MD5Utils;
import com.shlr.gprs.utils.okhttp.HttpParams;
import com.shlr.gprs.utils.okhttp.HttpUtils;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.shlr.gprs.vo.ChargeResponsVO;
import com.xiaoleilu.hutool.util.StrUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Administrator
 */

public class TestChargeTemplate extends ChargeTemplate {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public TestChargeTemplate(int templateId, String templateName, String account, String password, String key) {
		super(templateId, templateName, account, password, key);
		// TODO Auto-generated constructor stub
	}
	@Override
	public ChargeResponsVO charge(ChargeOrder chargeOrder) {
		ChargeResponsVO result = new ChargeResponsVO();
		String packageCode = getPackageCode(chargeOrder);
		if (StringUtils.isEmpty(packageCode)) {
			chargeOrder.setChargeStatus(3);
			chargeOrder.setSubmitTime(new Date());
			chargeOrder.setSubmitContent("未找到流量包编码");
			chargeOrder.setError("未找到流量包编码");
			result.setSuccess(false);
			result.setMsg("没有流量包编码");
			return result;
		}
		String requestid = super.genTaskId();
		String url = "http://118.89.103.198:8080/gprs-new/test.charge";
		HttpParams params = new HttpParams();
		params.put("mobiles", chargeOrder.getMobile());
		params.put("callbackurl", "http://58.246.140.150/get.notify");
		params.put("agentOrderId", requestid);
		Response response = null;
		String body = null;
		try {
			response = OkhttpUtils.getInstance()
				.get(url)
				.params(params)
				.execute();
			body = response.body().string();
			chargeOrder.setSubmitTime(new Date());
			if(!StrUtil.isEmpty(body)){
				JSONObject jsonObject = JSON.parseObject(body);
				Integer code = jsonObject.getInteger("code");
				String message = jsonObject.getString("message");
				String orderid = jsonObject.getString("orderid");
				String agentOrderId = jsonObject.getString("agentOrderId");
				if (code == 0) {
					chargeOrder.setChargeTaskId(agentOrderId);
					chargeOrder.setUpOrderId(orderid);
					chargeOrder.setChargeStatus(2);
					chargeOrder.setSubmitContent(message);
				} else {
					chargeOrder.setChargeStatus(3);
					chargeOrder.setSubmitContent(message);
					result.setSuccess(false);
					result.setMsg(message);
				}
				super.saveChannelLog(chargeOrder,body);
			}else{
				chargeOrder.setChargeStatus(1);
				chargeOrder.setError("提交未知");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			chargeOrder.setChargeStatus(1);
			chargeOrder.setSubmitTime(new Date());
			chargeOrder.setSubmitContent(e1.getMessage());
			chargeOrder.setError("提交未知");
		}
		
		return result;
	}

	@Override
	public ChargeResponsVO getChargeStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargeResponsVO getBalance() {
		// TODO Auto-generated method stub
		return null;
	}

}
