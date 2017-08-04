package com.shlr.gprs.manager;

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
import com.shlr.gprs.utils.okhttp.HttpUtils;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;

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
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
	
	public void callback(ChargeOrder chargeOrder) {
		Callback callback = new Callback();
		callback.setAccount(chargeOrder.getAccount());
		callback.setMobile(chargeOrder.getMobile());
		callback.setUrl(chargeOrder.getBackUrl());
		callback.setOrderId(chargeOrder.getId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("taskId", String.valueOf(chargeOrder.getId()));
		params.put("outTradeNo", chargeOrder.getAgentOrderId());
		params.put("mobile", chargeOrder.getMobile());
		params.put("status", String.valueOf(chargeOrder.getChargeStatus()));
		params.put("reportTime", String.valueOf(chargeOrder.getReportTime()));

		String query = "";
		query = HttpUtils.createFromParams(params);
		callback.setRequest(query);
		if(null != chargeOrder.getBackUrl() && !"".equals(chargeOrder.getBackUrl())){
			ThreadManager.getInstance().execute(new Runnable() {
				@Override
				public void run() {
					String response = "";
					int count = 0;
					do {
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
								}
							}
						} catch (Exception e) {
							// 出现异常就重试
							logger.error("callback------>>>>> {} 异常------>>>>>>",chargeOrder.getBackUrl(),e.getMessage());
							count++;
							try {
								Thread.sleep(300000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							continue;
						}
					} while (count < 5);
				}
			});
		}
		callbackService.saveOrUpdate(callback);
	}
}
