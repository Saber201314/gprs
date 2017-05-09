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
import com.shlr.gprs.utils.okhttp.HttpUtils;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.shlr.gprs.vo.ResultBaseVO;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Administrator
 */

public class TestChargeTemplate extends ChargeTemplate{
	
	Logger logger=LoggerFactory.getLogger(this.getClass());

	public TestChargeTemplate(int templateId, String templateName, String account, String password, String key) {
		super(templateId, templateName, account, password, key);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResultBaseVO<String> charge(ChargeOrder chargeOrder) {
		ResultBaseVO result = new ResultBaseVO();
		OkHttpClient client=null;
		try {
			client = OkhttpUtils.getInstance().getOkHttpClient();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		String packageCode = getPackageCode(chargeOrder);
//
//	    if (StringUtils.isEmpty(packageCode)) {
//	      result.addError("没有流量包编码");
//	      return result;
//	    }
		
		SimpleDateFormat timessdf=new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat orderidsdf = new SimpleDateFormat("yyyyMMddhhmmssSSS"); 

		String myTime = orderidsdf.format(new Date());
		Random random=new Random();
		
		int randomInt = random.nextInt(10000);
		String randomStr=String.valueOf(randomInt);
		int len=randomStr.length();
		if(len<4){
          for(int i=1; i<=4-len; i++)
        	  randomStr = "0" + randomStr  ;
	    }
		String requestid=myTime+randomStr;
		String url="http://localhost:8081/gprs-new/test";
		String api_key=this.password;
		String timestamp=timessdf.format(new Date());
		String packageid=chargeOrder.getAmount().toString();
		String mobiles=chargeOrder.getMobile();
		String messageid=requestid;
		
		String apisecret = MD5Utils.getMd5(this.key).toLowerCase();
		String sign=MD5Utils.getMd5(api_key+  apisecret +timestamp+mobiles+packageid).toLowerCase();
		Map<String, String> param=new LinkedHashMap<String, String>();
		param.put("appkey", api_key);
		param.put("timestamp", timestamp);
		param.put("packageid", packageid);
		param.put("mobiles", mobiles);
		param.put("messageid", messageid);
		param.put("sign", sign);
		
		
		
		Request request=new Request.Builder()
		.url(url+"?"+HttpUtils.createFromParams( param))
		.get()
		.build();
		
		
		
		Response execute1=null;
		String response ="";
		ChannelLog channelLog = new ChannelLog();
		channelLog.setTemplateId(this.templateId);
		channelLog.setTemplateName(this.templateName);
		channelLog.setMobile(chargeOrder.getMobile());
		channelLog.setOrderId(requestid);
		try {
			execute1 = client.newCall(request).execute();
			response= execute1.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			result.setModule(String.valueOf(chargeOrder.getId()));
			result.setOrderId(chargeOrder.getAgentorderId());
			channelLogService.save(channelLog);
			return result;	
		}
		channelLog.setResponse(response);
		channelLogService.save(channelLog);
		
		JSONObject jsonObject=JSON.parseObject(response);
		Integer code = jsonObject.getInteger("code");
	    String message = jsonObject.getString("message");
	    String orderid = jsonObject.getString("orderid");
		
		if (code == 0) {
			chargeOrder.setChargeTaskId(orderid);
			result.setModule(orderid);
			result.setOrderId(chargeOrder.getAgentorderId());
		} else {
			result.addError(message);
		}

		return result;
	}

	@Override
	public ResultBaseVO<Object> getChargeStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBaseVO<Object> getBalance() {
		// TODO Auto-generated method stub
		return null;
	}

}
