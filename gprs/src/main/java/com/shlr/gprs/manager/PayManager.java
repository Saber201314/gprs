package com.shlr.gprs.manager;

import java.util.Date;
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
import com.shlr.gprs.vo.BackMoneyVO;

/**
 * @author Administrator
 */

public class PayManager {
	
	Logger logger=LoggerFactory.getLogger(PayManager.class);
	
	private static Map<Integer, Double> moneyMap = new HashMap<Integer, Double>();
	private static LinkedBlockingQueue<Object> paytaskList = new LinkedBlockingQueue<Object>();
	
	PayLogService payLogService;
	UserService userService;
	
	public static PayManager getInstance(){
		return PayManagerHolder.payManager;
		
	}
	static class PayManagerHolder{
		static PayManager payManager=new PayManager();
	}
	public void init() {
		payLogService = WebApplicationContextManager.getApplicationContext().getBean(PayLogService.class);
		userService =  WebApplicationContextManager.getApplicationContext().getBean(UserService.class);
		List<Users> list = userService.list();
		for (Users users : list) {
			moneyMap.put(Integer.valueOf(users.getId()),
					Double.valueOf(users.getInitMoney()));
		}
		logger.info("pay manager inited..........");
		ThreadManager.getInstance().execute(new Runnable() {
			public void run() {
				while (true)
					try {
						Object task = PayManager.paytaskList.take();
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
						//System.err.println(e.toString());
					}
			}
		});
	}
	/**
	 * 充值中
	 * 
	 * @param payLog
	 */
	public void pay(PayLog payLog) {
		Double userMoney = Double.valueOf(getUserMoney(payLog.getUserId())
				- payLog.getMoney());
		moneyMap.put(Integer.valueOf(payLog.getUserId()), userMoney);
		userService.updateMoney(payLog.getUserId(), 0.0D - payLog.getMoney());
		// 消费明细充值中的状态：0
		payLog.setStatus(0);
		payLog.setOptionTime(new Date());
		payLog.setMoney(payLog.getMoney());
		payLog.setBalance(Math.round(userMoney * 100) / 100.0);
		payLogService.saveOrUpdate(payLog);
	}
	/**
	 * 充值失败
	 * @param payLog
	 */
	public void backMoney(PayLog payLog) {
		Double userMoney = Double.valueOf(getUserMoney(payLog.getUserId())
				+ payLog.getMoney());
		moneyMap.put(Integer.valueOf(payLog.getUserId()), userMoney);

		// 更新代理商余额
		userService.updateMoney(payLog.getUserId(), payLog.getMoney());

		// 消费明细充值失败的状态：-2
		payLog.setStatus(-2);
		payLogService.saveOrUpdate(payLog);

		// 新增一条记录记为退款状态
		PayLog newPayLog = new PayLog();
		newPayLog.setOrderId(payLog.getOrderId());
		newPayLog.setAgentOrderId(payLog.getAgentOrderId());
		newPayLog.setAccount(payLog.getAccount());
		newPayLog.setAgent(payLog.getAgent());
		newPayLog.setDiscount(payLog.getDiscount());
		Double profit = payLog.getProfit();
		if(profit != null){
			newPayLog.setProfit(0.0D - payLog.getProfit());			
		}
		newPayLog.setMobile(payLog.getMobile());
		newPayLog.setMemo(payLog.getMemo());
		Double money = payLog.getMoney();
		if(money != null){
			newPayLog.setMoney(0.0D - money);			
		}
		newPayLog.setType(payLog.getType());
		newPayLog.setUserId(payLog.getUserId());
		// 消费明细已经退款的状态：-1
		newPayLog.setStatus(-1);
		newPayLog.setOptionTime(new Date());

		// 设置消费明细余额
		newPayLog.setBalance(Math.round(userMoney * 100) / 100.0);
		payLogService.saveOrUpdate(newPayLog);
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
