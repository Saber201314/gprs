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
import com.shlr.gprs.vo.ChargeOrBuckleVO;

/**
 * @author Administrator
 */

public class PayManager {
	
	Logger logger=LoggerFactory.getLogger(PayManager.class);
	
	
	private ConcurrentHashMap<Integer, Double> moneyMap = new ConcurrentHashMap<Integer, Double>();
	private LinkedBlockingQueue<PayLog> paytaskList = new LinkedBlockingQueue<PayLog>();
	
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
						PayLog task = paytaskList.take();
						if (task.getType() == 2) {//充值扣费
							PayManager.getInstance().pay((PayLog) task);
							continue;
						}
						if (task.getType() == 3) {//失败退款
							PayManager.getInstance().backMoney((BackMoneyVO)task);
							continue;
						}
						if (task.getType() == 1) {//充扣值
							PayManager.getInstance().chargeOrBuckle((ChargeOrBuckleVO)task);
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
	//添加到队列扣费
	public void addToPay(PayLog payLog){
		try {
			paytaskList.put(payLog);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("添加扣费队列失败",e);
		}
	}
	/**
	 * 充值扣费
	 * 
	 * @param payLog
	 */
	@Transactional
	private void pay(PayLog payLog) {
		Double balance = updateAgentBalance(payLog.getUserId(), payLog.getMoney());
		payLog.setBalance(balance);
		payLogService.saveOrUpdate(payLog);
	}
	/**
	 * 充值退款
	 * @param payLog
	 */
	@Transactional
	private void backMoney(BackMoneyVO payLog) {
		// 更新代理商余额
		Double balance = updateAgentBalance(payLog.getUserId(), payLog.getMoney());
		// 设置消费明细余额
		payLog.setBalance(balance);
		payLogService.saveOrUpdate(payLog);
	}
	/**
	 * 充扣值
	 * @param payLog
	 */
	@Transactional
	private void chargeOrBuckle(ChargeOrBuckleVO payLog) {
		// 更新代理商余额
		Double balance = updateAgentBalance(payLog.getUserId(), payLog.getMoney());
		// 设置消费明细余额
		payLog.setBalance(balance);
		payLogService.saveOrUpdate(payLog);
	}
	
	
	public void putAgentToMap(Users users){
		synchronized (moneyMap) {
			boolean containsKey = moneyMap.containsKey(users.getId());
			if(!containsKey){
				moneyMap.put(users.getId(), users.getMoney());
			}
		}
	}
	//获取用户余额
	private Double getUserMoney(int userId) {
		Double money = null;
		synchronized (moneyMap) {
			money = (Double) moneyMap.get(Integer.valueOf(userId));
			if (money != null) {
				return money;
			}
		}
		return null;
	}
	private Double updateAgentBalance(Integer userId,Double money){
		Double balance=null;
		Integer result = userService.updateMoney(userId, money);
		if(result > 0){
			balance= getUserMoney(userId) + money;
			moneyMap.put(userId, balance);
		}
		return balance;
	}
	//验证余额是否满足扣费
	public boolean validBalance(Integer id,Double money){
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
