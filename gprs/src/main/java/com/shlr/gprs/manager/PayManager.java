package com.shlr.gprs.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;

/**
 * @author Administrator
 */

public class PayManager {
	
	Logger logger=LoggerFactory.getLogger(PayManager.class);
	
	private static Map<Integer, Double> moneyMap = new HashMap<Integer, Double>();
	private static LinkedBlockingQueue<Object> paytaskList = new LinkedBlockingQueue<Object>();
	
	PayLogService payLogService = new PayLogService();
	
	public static PayManager getInstance(){
		return PayManagerHolder.payManager;
		
	}
	static class PayManagerHolder{
		static PayManager payManager=new PayManager();
	}
	public void init() {
		UserService userService = (UserService) WebApplicationContextManager.getApplicationContext().getBean(UserService.class);
		List<Users> list = userService.list();
		for (Users users : list) {
			moneyMap.put(Integer.valueOf(users.getId()),
					Double.valueOf(users.getInitMoney()));
		}
		logger.info("pay manager inited..........");
	}
	public void addToPay(Object pay) {
		try {
			paytaskList.put(pay);
		} catch (InterruptedException e) {
			logger.error("error to add paytaskList ", e);
		}
	}
	public void chargeSuccess(PayLog payLog){
		payLog.setStatus(1);
		payLogService.update(payLog);
	}
	
	public double getUserMoney(int userId) {
		Double money = (Double) moneyMap.get(Integer.valueOf(userId));
		if (money == null) {
			return 0.0D;
		}
		return money.doubleValue();
	}
}
