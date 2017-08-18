package com.shlr.gprs.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.shlr.gprs.domain.Callback;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.CallbackService;
import com.shlr.gprs.utils.JSONUtils;
import com.shlr.gprs.utils.okhttp.HttpUtils;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;

import okhttp3.Response;

/**
 * @author Administrator
 */

public class CallbackManager {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private CallbackService callbackService;
	
	private LinkedBlockingQueue<ChargeOrder> callbackTask = new LinkedBlockingQueue<>();
	
	public static CallbackManager getInstance(){
		return CallbackManagerHolder.callbackManager;
	}
	static class CallbackManagerHolder{
		static CallbackManager callbackManager = new CallbackManager();
	}
	
	public void init(){
		callbackService = WebApplicationContextManager.getBean(CallbackService.class);
		ThreadManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						ChargeOrder chargeOrder = callbackTask.take();
						ThreadManager.getInstance().execute(new Runnable() {
							@Override
							public void run() {
								callback(chargeOrder);
							}
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("回调队列异常",e);
					}
				}
			}
		});
	}
	public void addToCallback(ChargeOrder chargeOrder){
		try {
			callbackTask.put(chargeOrder);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void callback(ChargeOrder chargeOrder) {
		Callback callback = new Callback();
		callback.setAccount(chargeOrder.getAccount());
		callback.setMobile(chargeOrder.getMobile());
		callback.setUrl(chargeOrder.getBackUrl());
		callback.setOrderId(chargeOrder.getId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("taskId", String.valueOf(chargeOrder.getId()));
		params.put("orderId", chargeOrder.getAgentOrderId());
		params.put("mobile", chargeOrder.getMobile());
		if(chargeOrder.getChargeStatus().intValue() == 3){
			params.put("status", "5");
			params.put("msg", chargeOrder.getSubmitContent());
		}else{
			params.put("status", String.valueOf(chargeOrder.getChargeStatus()));
			params.put("msg", chargeOrder.getReportContent());
		}
		params.put("reportTime", DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));

		callback.setRequest(JSONUtils.toJsonString(params));
		if(StrUtil.isNotBlank(chargeOrder.getBackUrl())){
			String response = "";
			for (int i = 0; i < 3; i++) {
				try {
					Response execute = OkhttpUtils.getInstance()
							.post(chargeOrder.getBackUrl())
							.params(params, true)
							.execute();
					response = execute.body().string();
					if (StringUtils.isEmpty(response)) {
						throw new Exception("返回内容为空");
					} else {
						callback.setResponse(response);
						if ("ok".equals(response)) {
							break;// 收到ok确认后 不再推送
						}else{
							try {
								Thread.sleep(300000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							continue;
						}
					}
				} catch (Exception e) {
					// 出现异常就重试
					logger.error("callback------>>>>> {} 异常------>>>>>>",chargeOrder.getBackUrl(),e.getMessage());
					try {
						Thread.sleep(300000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					continue;
				}
			}
		}
		callbackService.saveOrUpdate(callback);
	}
}
