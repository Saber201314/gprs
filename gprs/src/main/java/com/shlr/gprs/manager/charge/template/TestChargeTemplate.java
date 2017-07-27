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
			result.setSuccess(false);
			result.setMsg("没有流量包编码");
			return result;
		}
		SimpleDateFormat timessdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String requestid = super.genTaskId();
		String url = "http://localhost:8081/gprs-new/test";
		String api_key = this.password;
		String timestamp = timessdf.format(new Date());
		String packageid = chargeOrder.getAmount().toString();
		String mobiles = chargeOrder.getMobile();
		String messageid = requestid;

		String apisecret = MD5Utils.getMd5(this.key).toLowerCase();
		String sign = MD5Utils.getMd5(api_key + apisecret + timestamp + mobiles + packageid).toLowerCase();
		
		HttpParams params = new HttpParams();
		params.put("appkey", api_key);
		params.put("timestamp", timestamp);
		params.put("packageid", packageid);
		params.put("mobiles", mobiles);
		params.put("messageid", messageid);
		params.put("sign", sign);

		Response response = null;
		try {
			response = OkhttpUtils.getInstance()
				.get(url)
				.params(params)
				.execute();
		} catch (IOException e1) {
			e1.printStackTrace();
			chargeOrder.setChargeStatus(1);
			chargeOrder.setError("提交未知");
		}
		String body = null;
		try {
			body = response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!StrUtil.isEmpty(body)){
			JSONObject jsonObject = JSON.parseObject(body);
			Integer code = jsonObject.getInteger("code");
			String message = jsonObject.getString("message");
			String orderid = jsonObject.getString("orderid");
			
			if (code == 0) {
				chargeOrder.setChargeTaskId(orderid);
				result.setOrderId(chargeOrder.getAgentOrderId());
			} else {
				result.setSuccess(false);
				result.setMsg(message);
			}
			ChannelLog channelLog = new ChannelLog();
			channelLog.setTemplateId(this.templateId);
			channelLog.setTemplateName(this.templateName);
			channelLog.setMobile(chargeOrder.getMobile());
			channelLog.setOrderId(chargeOrder.getChargeTaskId());
			channelLog.setResponse(body);
			channelLogService.save(channelLog);
		}else{
			
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
