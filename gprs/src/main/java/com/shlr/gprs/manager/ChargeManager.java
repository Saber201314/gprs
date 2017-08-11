package com.shlr.gprs.manager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.tomcat.jni.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.domain.Callback;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.ChannelTemplate;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.PricePaper;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.manager.charge.ChargeTemplate;
import com.shlr.gprs.services.CallbackService;
import com.shlr.gprs.services.ChannelService;
import com.shlr.gprs.services.ChannelTemplateService;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.PricePaperService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.okhttp.HttpUtils;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.shlr.gprs.vo.BackMoneyVO;
import com.shlr.gprs.vo.ChargeResponsVO;
import com.xiaoleilu.hutool.util.StrUtil;

import okhttp3.Response;

/**
 * @author Administrator 充值管理
 */

public class ChargeManager {
	private Logger logger=LoggerFactory.getLogger(ChargeManager.class);
	
	private UserService userService;
	private GprsPackageService gprsPackageService;
	private ChargeOrderService chargeOrderService;
	private PayLogService payLogService;
	private CallbackService callbackService;
	private PricePaperService pricePaperService;
	private ChannelTemplateService channelTemplateService;
	private ChannelService channelService;

	private List<Channel> channelList = new ArrayList<Channel>();
	private Map<String, ChargeTemplate> chargeTemplateMap = new HashMap<String, ChargeTemplate>();
	
	private LinkedBlockingQueue<ChargeOrder> ordertaskList = new LinkedBlockingQueue<ChargeOrder>();

	public static Boolean moreOperValidMap = false;

	private static class ChargeManagerHolder {
		static ChargeManager chargeManager = new ChargeManager();
	}

	public static ChargeManager getInstance() {
		
		return ChargeManagerHolder.chargeManager;
	}
	
	public void init() {
		long start = System.currentTimeMillis();
		logger.info("{} initialization started",this.getClass().getSimpleName());
		
		userService = WebApplicationContextManager.getBean(UserService.class);
		gprsPackageService = WebApplicationContextManager.getBean(GprsPackageService.class);
		chargeOrderService = WebApplicationContextManager.getBean(ChargeOrderService.class);
		payLogService = WebApplicationContextManager.getBean(PayLogService.class);
		callbackService = WebApplicationContextManager.getBean(CallbackService.class);
		pricePaperService = WebApplicationContextManager.getBean(PricePaperService.class);
		channelTemplateService = WebApplicationContextManager.getBean(ChannelTemplateService.class);
		channelService = WebApplicationContextManager.getBean(ChannelService.class);
		
		//初始化所有通道模板
		List<ChannelTemplate> templateList = channelTemplateService.list();
		for (ChannelTemplate template : templateList) {
			try {
				Class cla = Class.forName("com.shlr.gprs.manager.charge.template." + template.getIdentity());
				Constructor cons ;
				cons = cla.getConstructor(
						new Class[] { Integer.TYPE, String.class, String.class, String.class, String.class });
				ChargeTemplate service = (ChargeTemplate) cons.newInstance(new Object[] { Integer.valueOf(template.getId()),
						template.getName(), template.getAccount(), template.getPassword(), template.getSign() });
				chargeTemplateMap.put(template.getIdentity(), service);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(template.getIdentity()+"-------反射异常-------" , e);
			}
		}
		List<Channel> list = channelService.list();
		if(!CollectionUtils.isEmpty(list)){
			channelList.addAll(list);
		}
		
		ThreadManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						//订单队列 获取订单
						ChargeOrder chargeOrder = ordertaskList.take();
						Integer paystatus = chargeOrder.getPaystatus();
						if(paystatus == 0){//未扣费订单
							Users agent = UsersCache.getInstance().getByAccount(chargeOrder.getAccount());
							boolean validBalance = PayManager.getInstance().validBalance(agent.getId(), chargeOrder.getPayMoney());
							if(validBalance){//余额足够扣费
								PayLog payLog = new PayLog();
								payLog.setUserId(agent.getId());
								payLog.setAccount(chargeOrder.getAccount());
								payLog.setAgent(agent.getAgent());
								payLog.setMemo("订单号"+chargeOrder.getId() + "手机号"+chargeOrder.getMobile()+"充值流量"+chargeOrder.getAmount()+"M");
								payLog.setMoney(0.0D - chargeOrder.getPayMoney());
								payLog.setType(2);
								PayManager.getInstance().addToPay(payLog);
								chargeOrder.setPaystatus(1);
							}else{
								chargeOrder.setCacheFlag(1);
								chargeOrder.setError("代理商余额不足");
								chargeOrderService.saveOrUpdate(chargeOrder);
								continue;
							}
						}
						ConcurrentHashMap<String, Boolean> cacheCondition = ChannelCache.getInstance().cacheCondition;
						// 根据缓存条件缓存订单
						if(ChannelCache.getInstance().cacheCondition.containsKey(chargeOrder.getLocation())){
							//符合地区缓存
							chargeOrder.setCacheFlag(1);
							chargeOrder.setError(chargeOrder.getLocation()+"-地区缓存");
							chargeOrderService.saveOrUpdate(chargeOrder);
						}else if(ChannelCache.getInstance().cacheCondition.containsKey(chargeOrder.getSubmitChannel().toString())){
							//符合通道缓存
							chargeOrder.setCacheFlag(1);
							chargeOrder.setError(chargeOrder.getSubmitChannel()+"-通道缓存");
							chargeOrderService.saveOrUpdate(chargeOrder);
						}else{
							//清楚缓存标记
							chargeOrder.setCacheFlag(0);
							chargeOrder.setError("");
							//执行充值
							ThreadManager.getInstance().execute(new Runnable() {
								@Override
								public void run() {
									realCharge(chargeOrder);
								}
							});
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		long end = System.currentTimeMillis();
		logger.info("{} initialization completed in {} ms ",this.getClass().getSimpleName(),end-start);
	}
	//添加充值队列
	public void addToCharge(ChargeOrder chargeOrder){
		try {
			ordertaskList.put(chargeOrder);
		} catch (InterruptedException e) {
			logger.error("添加充值队列异常", e);
		}
	}
	public ChargeResponsVO charge(ChargeOrder chargeOrder) {
		ChargeResponsVO result=new ChargeResponsVO();
		//匹配报价  分包
		result = matchingPricePackageId(chargeOrder);
		if(!result.isSuccess()){
			return result;
		}
		// 匹配充值通道
		result = matchingChannelToCharge(chargeOrder);// 如果充值通道直接返回提交失败
		if(!result.isSuccess()){
			return result;
		}
		addToCharge(chargeOrder);
		result.setMsg("提交成功");
		result.setTaskId(chargeOrder.getId().toString());
		result.setOrderId(chargeOrder.getAgentOrderId());
		return result;
	}
	/**
	 * 根据用户的报价单匹配流量包ID
	 * 
	 * @param chargeOrder
	 * @return
	 */
	public ChargeResponsVO matchingPricePackageId(ChargeOrder chargeOrder) {
		ChargeResponsVO result = new ChargeResponsVO();
		Users agent = userService.findByUsername(chargeOrder.getAccount());
		
		PricePaper pricePaper = pricePaperService.findByIdWithCache(agent.getPaperId());
		if(pricePaper == null){
			result.setSuccess(false);
			result.setMsg("报价单不存在");
			return result;
		}
		List<GprsPackage> packageList = new ArrayList<GprsPackage>();
		BigDecimal b3 = new BigDecimal("10");
		String[] items = pricePaper.getItems().split(",");
		for (String item : items) {
			String[] temp = item.split(":");
			GprsPackage gprsPackage = gprsPackageService.findById(Integer.valueOf(temp[0]));
			if ((gprsPackage != null) && (gprsPackage.getStatus() == 0)) {
				BigDecimal discount = new BigDecimal(temp[1]);
				gprsPackage.setDiscount(discount.doubleValue());
				BigDecimal money = new BigDecimal(gprsPackage.getMoney());
				gprsPackage.setPaymoney(money.multiply(discount).divide(b3).doubleValue());
				packageList.add(gprsPackage);
			}
		}
		GprsPackage gprs = null;
		int nCount = 0;
		for (GprsPackage gprsPackage : packageList) {
			if ((gprsPackage.getAmount() != chargeOrder.getAmount()) 
					|| (gprsPackage.getType() != chargeOrder.getType())
					|| (gprsPackage.getRangeType() != chargeOrder.getRangeType())
					|| ((!gprsPackage.getLocations().equals("全国"))
							&& (gprsPackage.getLocations().indexOf(chargeOrder.getLocation()) == -1)))
				continue;
			nCount++;
			if (chargeOrder.getRouteFlag() == 1) {//路由不可用
				int curRoutePos = chargeOrder.getCurRoutePos();
				if (curRoutePos == 1 && nCount == 1) {
					gprs = gprsPackage;
					break;
				} else if (curRoutePos == 2 && nCount == 2) {
					gprs = gprsPackage;
					break;
				} else if (curRoutePos == 3 && nCount == 3) {
					gprs = gprsPackage;
					break;
				}
			} else {
				gprs = gprsPackage;
				break;
			}
		}
		if(gprs != null){
			chargeOrder.setPackageId(gprs.getId());
			if (gprs.getStatus() == -1) {
				result.setSuccess(false);
				result.setMsg("流量包不存在");
			}else if (chargeOrder.getType() != gprs.getType()) {
				result.setSuccess(false);
				result.setMsg("不支持运营商类型");
			}else if ((!gprs.getLocations().equals("全国"))
					&& (gprs.getLocations().indexOf(chargeOrder.getLocation()) == -1)) {
				result.setSuccess(false);
				result.setMsg("流量包不支持该地区");
			}
			chargeOrder.setMoney(gprs.getMoney());
			// 设置是否带票
			Integer paybill = pricePaper.getPackageBillMap().get(chargeOrder.getPackageId());
			chargeOrder.setPayBill(paybill == null ? 0 : paybill);
			//设置外放折扣
			Double outDiscount = pricePaper.getPackageDiscountMap().get(Integer.valueOf(chargeOrder.getPackageId()));
			chargeOrder.setOutDiscount(outDiscount == null ? 0 : outDiscount);
			Double paymoney = gprs.getPaymoney();
			chargeOrder.setPayMoney(paymoney);
		}else{
			result.setSuccess(false);
			result.setMsg("报价没有可用的流量包");
		}
		return result;
	}


	private ChargeResponsVO matchingChannelToCharge(ChargeOrder chargeOrder) {
		ChargeResponsVO result = new ChargeResponsVO();

		LinkedList<Channel> matchChannel = new LinkedList<>();
		for (Channel channel : channelList) {
			String packages = channel.getPackages();
			if(StrUtil.isNotEmpty(packages)){
				String[] packageItem = packages.split(",");
				for (String item : packageItem) {
					if(StrUtil.isEmpty(item)){
						continue;
					}
					String[] pack = item.split(":");
					Integer packId = Integer.valueOf(pack[0]);
					Double inDiscount = Double.valueOf(pack[1]);
					Integer priority = Integer.valueOf(pack[2]);
					if(packId  == chargeOrder.getPackageId() ){
						channel.setInDiscount(inDiscount);
						matchChannel.add(channel);
					}
				}
			}
		}
		//根据折扣排序   优先接入折扣较低的通道
		Collections.sort(matchChannel,new Comparator<Channel>() {
			@Override
			public int compare(Channel o1, Channel o2) {
				// TODO Auto-generated method stub
				return o1.getInDiscount().compareTo(o2.getInDiscount());
			}
		});
		Channel channel = null;
		if (CollectionUtils.isEmpty(matchChannel)) {
			result.setSuccess(false);
			result.setMsg("没有可用的通道资源");
			return result;
		}else{
			channel = matchChannel.getFirst();//匹配上游折扣最低的一个通道
		}
		ChargeTemplate template = (ChargeTemplate) chargeTemplateMap.get(channel.getTemplate());
		if (template == null) {
			result.setSuccess(false);
			result.setMsg("没有可用的通道资源");
			return result;
		}
		//设置提交通道
		chargeOrder.setSubmitChannel(channel.getId());
		//设置提交模板
		chargeOrder.setSubmitTemplate(template.getTemplateId());
		// 上游接入的折扣
		chargeOrder.setInDiscount(channel.getInDiscount());
		// 上游折后价
		chargeOrder.setDiscountMoney(chargeOrder.getMoney()*chargeOrder.getInDiscount()/10);
		chargeOrder.setProfit(chargeOrder.getPayMoney()-chargeOrder.getDiscountMoney());
		chargeOrderService.saveOrUpdate(chargeOrder);
		return result;
	}
	public void realCharge(ChargeOrder chargeOrder){
		Channel channel = ChannelCache.getInstance().idMap.get(chargeOrder.getSubmitChannel());
		ChargeTemplate template = chargeTemplateMap.get(channel.getTemplate());
		ChargeResponsVO result = template.charge(chargeOrder);
		if(!result.isSuccess()){
			if(chargeOrder.getChargeStatus() != 1){
				BackMoneyVO backMoneyVO = new BackMoneyVO();
				Users byAccount = UsersCache.getInstance().getByAccount(chargeOrder.getAccount());
				backMoneyVO.setUserId(byAccount.getId());
				backMoneyVO.setAccount(byAccount.getUsername());
				backMoneyVO.setAgent(byAccount.getAgent());
				backMoneyVO.setType(3);
				backMoneyVO.setMoney(chargeOrder.getPayMoney());
				backMoneyVO.setMemo("为订单号"+chargeOrder.getId()+"手机号"+chargeOrder.getMobile()+"失败返还"+chargeOrder.getPayMoney()+"元");
				PayManager.getInstance().addToPay(backMoneyVO);
			}
		}
		chargeOrderService.saveOrUpdate(chargeOrder);
	}
	private void updateOrderStatus(ChargeOrder chargeOrder, Boolean isSuccess, String msg) {
		chargeOrder.setChargeStatus(isSuccess ? 4 : 5);
		chargeOrder.setReportContent(msg);
		chargeOrder.setReportTime(new Date());

		Integer updateChargeStatus = chargeOrderService.updateChargeStatus(chargeOrder.getChargeTaskId(),
				chargeOrder.getSubmitTemplate(),chargeOrder.getReportTime(),
				chargeOrder.getChargeStatus(), chargeOrder.getReportContent());
		
		if(updateChargeStatus > 0 && !isSuccess && chargeOrder.getPaystatus() == 1){
			BackMoneyVO backMoneyVO = new BackMoneyVO();
			backMoneyVO.setAccount(chargeOrder.getAccount());
			backMoneyVO.setUserId(UsersCache.getInstance().getByAccount(chargeOrder.getAccount()).getId());
			backMoneyVO.setAgent(UsersCache.getInstance().getByAccount(chargeOrder.getAccount()).getAgent());
			backMoneyVO.setType(3);
			backMoneyVO.setMoney(chargeOrder.getPayMoney());
			backMoneyVO.setMemo("订单号"+chargeOrder.getId()+"手机号"+chargeOrder.getMobile()+"失败退款"+chargeOrder.getPayMoney());
			PayManager.getInstance().addToPay(backMoneyVO);
		}
		// 回调订单状态
		if (!StringUtils.isEmpty(chargeOrder.getBackUrl())) {
			CallbackManager.getInstance().addToCallback(chargeOrder);
		}
	}
	public void updateResult(int templateId, String taskId, boolean isSuccess, String reportContent) {
		List<ChargeOrder> orderlist = chargeOrderService.findOneByTaskIdAndTemplateId(taskId, templateId);
		if (orderlist.isEmpty()) {
			return;
		}
		ChargeOrder chargeOrder = (ChargeOrder) orderlist.get(0);
		// 更新订单状态
		updateOrderStatus(chargeOrder, isSuccess, reportContent);
	}
	public void terminaOrder(ChargeOrder chargeOrder){
		chargeOrder.setChargeStatus(3);
		chargeOrder.setSubmitTime(new Date());
		chargeOrder.setSubmitContent("手动终止");
		Integer saveOrUpdate = chargeOrderService.saveOrUpdate(chargeOrder);
		if(saveOrUpdate > 0 && chargeOrder.getPaystatus() == 1){
			BackMoneyVO backMoneyVO = new BackMoneyVO();
			backMoneyVO.setAccount(chargeOrder.getAccount());
			backMoneyVO.setUserId(UsersCache.getInstance().getByAccount(chargeOrder.getAccount()).getId());
			backMoneyVO.setAgent(UsersCache.getInstance().getByAccount(chargeOrder.getAccount()).getAgent());
			backMoneyVO.setType(3);
			backMoneyVO.setMoney(chargeOrder.getPayMoney());
			backMoneyVO.setMemo("订单号"+chargeOrder.getId()+"手机号"+chargeOrder.getMobile()+"失败退款"+chargeOrder.getPayMoney());
			PayManager.getInstance().addToPay(backMoneyVO);
		}
		// 回调订单状态
		if (!StringUtils.isEmpty(chargeOrder.getBackUrl())) {
			CallbackManager.getInstance().addToCallback(chargeOrder);
		}
	}

	/**
	 * 充值失败路由
	 * 
	 * @param chargeOrder
	 * @return
	 */
	public boolean failChargedOrderRoutable(ChargeOrder chargeOrder) {
		// 订单提交时间超过6个小时，不可以再路由
		if (failchargedOrderExpirable(chargeOrder)) {
			return false;
		}
		int curRoutePos = chargeOrder.getCurRoutePos();
		int packageId = 0;
		if (curRoutePos == 1) {
			chargeOrder.setCurRoutePos(2);
			packageId = chargeOrder.getPackageId2();
			if (packageId != 0) {
				chargeOrder.setPackageId(packageId);
				// 设置路由可搜索
				chargeOrder.setRouteStatus(1);
				chargeOrderService.saveOrUpdate(chargeOrder);
				return true;
			}
		} else if (curRoutePos == 2) {
			// 如果无须第三次路由
			if (chargeOrder.getRouteTimes() != 2) {
				chargeOrder.setCurRoutePos(3);
				packageId = chargeOrder.getPackageId3();
				if (packageId != 0) {
					chargeOrder.setPackageId(packageId);
					// 设置路由可搜索
					chargeOrder.setRouteStatus(1);
					chargeOrderService.saveOrUpdate(chargeOrder);
					return true;
				}
			}
		}
		chargeOrder.setRouteFlag(0);
		return false;
	}

	/**
	 * 提交失败路由
	 * 
	 * @param chargeOrder
	 * @param resultBaseDO
	 */
	private boolean failFirstSubmitedOrderRoutable(ChargeOrder chargeOrder) {
		int curRoutePos = chargeOrder.getCurRoutePos();
		if (curRoutePos == 1) {
			chargeOrder.setCurRoutePos(2);
			// 将当前流量包设置为第二个流量包
			chargeOrder.setPackageId(chargeOrder.getPackageId2());
			// 开启路由可搜索
			chargeOrder.setRouteStatus(1);
//			chargeOrder.setSubmitStatus(1);
			return true;
		} else if (curRoutePos == 2) {
			chargeOrder.setCurRoutePos(3);
			if (chargeOrder.getRouteTimes() == 2) {
				// 如果只配置了2个流量包，第二次路由失败，路由结束，关闭路由，直接返回失败
				chargeOrder.setRouteFlag(0);
				chargeOrderService.saveOrUpdate(chargeOrder);
				updateOrderStatus(chargeOrder, false, "已退款");
			} else {
				int packageId = chargeOrder.getPackageId3();
				if (packageId != 0) {
					// 将当前流量包设置为第三个流量包
					chargeOrder.setPackageId(packageId);
					chargeOrder.setRouteStatus(1);
					chargeOrderService.saveOrUpdate(chargeOrder);
				}
			}
		} else if (curRoutePos == 3) {
			// 路由结束，关闭路由，直接返回失败
			chargeOrder.setRouteFlag(0);
			chargeOrderService.saveOrUpdate(chargeOrder);
			updateOrderStatus(chargeOrder, false, "已退款");
		}
		return false;
	}

	/**
	 * 如果订单提交时间超过6个小时
	 * 
	 * @param chargeOrder
	 * @return
	 */
	public boolean failchargedOrderExpirable(ChargeOrder chargeOrder) {
		long optionDateTime = chargeOrder.getOptionTime().getTime();
		long curDateTime = new Date().getTime();
		long diff = curDateTime - optionDateTime;
		long day = diff / (24 * 60 * 60 * 1000);
		long hour = (diff / (60 * 60 * 1000) - day * 24);
		if (hour > 6) {
			return true;
		}
		return false;
	}

	
}
