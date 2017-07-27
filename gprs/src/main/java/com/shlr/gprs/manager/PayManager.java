package com.shlr.gprs.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.vo.BackMoneyVO;

/**
 * @author Administrator
 */

public class PayManager {
	
	Logger logger=LoggerFactory.getLogger(PayManager.class);
	
	
	private ConcurrentHashMap<Integer, Double> moneyMap = new ConcurrentHashMap<Integer, Double>();
	private LinkedBlockingQueue<Object> paytaskList = new LinkedBlockingQueue<Object>();
	
	PayLogService payLogService;
	UserService userService;
	
	public static PayManager getInstance(){
		return PayManagerHolder.payManager;
		
	}
	static class PayManagerHolder{
		static PayManager payManager=new PayManager();
	}
	public void init() {
		long start = System.currentTimeMillis();
		logger.info("{} initialization started",this.getClass().getSimpleName());
		
		payLogService = WebApplicationContextManager.getApplicationContext().getBean(PayLogService.class);
		userService =  WebApplicationContextManager.getApplicationContext().getBean(UserService.class);
		List<Users> list = userService.list();
		for (Users users : list) {
			moneyMap.put(users.getId(), users.getMoney());
		}
		ThreadManager.getInstance().execute(new Runnable() {
			public void run() {
				while (true)
					try {
						Object task = paytaskList.take();
						if ((task instanceof PayLog)) {
							PayManager.getInstance().pay((PayLog) task);
							continue;
						}
						if ((task instanceof BackMoneyVO)) {
							PayManager.getInstance().backMoney(((BackMoneyVO) task)
									.getPayLog());
							continue;
						}
					} catch (Exception e) {
						logger.error("PayManager", e);
					}
			}
		});
		
		long end = System.currentTimeMillis();
		logger.info("{} initialization completed in {} ms ",this.getClass().getSimpleName(),end-start);
	}
	/**
	 * 充值扣费
	 * 
	 * @param payLog
	 */
	@Transactional
	public void pay(PayLog payLog) {
		Double userMoney = updateAgentBalance(payLog.getUserId(), 0.0D - payLog.getMoney());
		payLog.setBalance(Math.round(userMoney * 100) / 100.0);
		payLogService.saveOrUpdate(payLog);
	}
	/**
	 * 充值退款
	 * @param payLog
	 */
	@Transactional
	public void backMoney(PayLog payLog) {
		// 更新代理商余额
		Double userMoney = updateAgentBalance(payLog.getUserId(), payLog.getMoney());
		// 设置消费明细余额
		payLog.setBalance(Math.round(userMoney * 100) / 100.0);
		payLogService.saveOrUpdate(payLog);
	}
	public void putAgentToMap(Users users){
		moneyMap.put(users.getId(), users.getMoney());
	}
	public Double getUserMoney(int userId) {
		Double money = (Double) moneyMap.get(Integer.valueOf(userId));
		if (money != null) {
			return money;
		}
		return null;
	}
	public synchronized Double updateAgentBalance(Integer userId,Double money){
		Double userMoney=null;
		Integer updateMoney = userService.updateMoney(userId, money);
		if(updateMoney > 0){
			userMoney= Double.valueOf(moneyMap.get(userId) + money);
			moneyMap.put(userId, userMoney);
		}
		return userMoney;
	}
	//验证余额是否满足扣费
	public synchronized boolean validBalance(Integer id,Double money){
		Double userMoney = getUserMoney(id);
		if(userMoney != null){
			if (userMoney < money) {
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
}
