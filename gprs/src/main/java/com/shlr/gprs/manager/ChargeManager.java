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
				Class<?> cla = Class.forName("com.shlr.gprs.manager.charge.template." + template.getIdentity());
				Constructor cons ;
				cons = cla.getConstructor(
						new Class[] { Integer.class, String.class, String.class, String.class, String.class });
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
								// 设置代理商订单号码
								payLog.setType(2);
								payLog.setMemo("订单号"+chargeOrder.getId() + "手机号"+chargeOrder.getMobile()+"充值流量"+chargeOrder.getAmount()+"M");
								payLog.setMoney(chargeOrder.getPayMoney());
								PayManager.getInstance().pay(payLog);
								chargeOrder.setPaystatus(1);
							}else{
								chargeOrder.setCacheFlag(1);
								chargeOrder.setError("代理商余额不足");
								chargeOrderService.saveOrUpdate(chargeOrder);
								continue;
							}
						}
						// 根据缓存条件缓存订单
						if(ChannelCache.getInstance().cacheCondition.contains(chargeOrder.getLocation())){
							//符合地区缓存
							chargeOrder.setCacheFlag(1);
							chargeOrder.setError(chargeOrder.getLocation()+"-地区缓存");
							chargeOrderService.saveOrUpdate(chargeOrder);
						}else if(ChannelCache.getInstance().cacheCondition.contains(chargeOrder.getSubmitChannel())){
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
		// 开始分配充值通道
		result = matchingChannelToCharge(chargeOrder);// 如果充值通道直接返回提交失败
		if(!result.isSuccess()){
			return result;
		}
		addToCharge(chargeOrder);
		result.setSuccess(true);
		result.setMsg("提交成功");
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
					|| (gprsPackage.getRange() != chargeOrder.getRange())
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
			//通道禁用 直接跳过
			if(channel.getStatus() == -1){
				continue;
			}
			String packages = channel.getPackages();
			if(StrUtil.isNotEmpty(packages)){
				String[] packageItem = packages.split(",");
				for (String item : packageItem) {
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
		return result;
	}
	public void realCharge(ChargeOrder chargeOrder){
		Channel channel = ChannelCache.getInstance().idMap.get(chargeOrder.getSubmitChannel());
		ChargeTemplate template = chargeTemplateMap.get(channel.getTemplate());
		ChargeResponsVO result = template.charge(chargeOrder);
		if(result.isSuccess()){
			chargeOrder.setChargeStatus(2);
			chargeOrder.setSubmitContent(result.getMsg());
		}else{
			chargeOrder.setChargeStatus(3);
			chargeOrder.setSubmitContent(result.getMsg());
		}
		chargeOrderService.saveOrUpdate(chargeOrder);
	}
	private void updateResult(ChargeOrder chargeOrder, Boolean isSuccess, String msg) {
		chargeOrder.setChargeStatus(isSuccess ? 1 : -1);
		chargeOrder.setError(isSuccess ? null : msg);
		chargeOrder.setReportTime(new Date());

		int cacheFlag = chargeOrder.getCacheFlag();
		// 这里的作用只是失败缓存中的数据
		if (cacheFlag == 1) {
			chargeOrderService.forceToFailOrder(chargeOrder.getId(), cacheFlag, isSuccess ? 1 : -1, msg);
		} else {
			// 非缓存数据
			String chargeTaskId = chargeOrder.getChargeTaskId();
			if (StringUtils.isEmpty(chargeTaskId)) {
				chargeTaskId = String.valueOf(chargeOrder.getId());
			}
			chargeOrderService.updateChargeStatus(chargeTaskId, chargeOrder.getSubmitTemplate(), isSuccess ? 1 : -1, msg);
		}
		if (!isSuccess) {
			// 如果充值失败..
			PayLog payLog = new PayLog();
			payLog.setType(Integer.valueOf(1));
			payLog.setStatus(Integer.valueOf(0));
			PayLog payLog2 = payLogService.findOne(payLog);
		} else {
			// 充值成功....
			PayLog payLog = new PayLog();
			payLog.setType(Integer.valueOf(1));
			payLog.setStatus(Integer.valueOf(0));
			PayLog payLog2 = payLogService.findOne(payLog);
		}
		// 回调订单状态
		if (!StringUtils.isEmpty(chargeOrder.getBackUrl())) {
			callback(chargeOrder);
		}
	}

	public void updateResult(int templateId, String taskId, boolean isSuccess, String msg) {
		List<ChargeOrder> orderlist = chargeOrderService.selectOneByExample(taskId, templateId);
		if (orderlist.isEmpty()) {
			return;
		}
		ChargeOrder chargeOrder = (ChargeOrder) orderlist.get(0);
//		if (!isSuccess) {
//			// 如果订单已经开启了路由
//			int routeFlag = chargeOrder.getRouteFlag();
//			if (routeFlag == 1) {
//				if (failChargedOrderRoutable(chargeOrder)) {
//					return;
//				}
//			}
//		}
		// 更新订单状态
		updateResult(chargeOrder, isSuccess, msg);
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
				updateResult(chargeOrder, false, "已退款");
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
			updateResult(chargeOrder, false, "已退款");
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

	public void callback(ChargeOrder chargeOrder) {
		Callback callback = new Callback();
		callback.setAccount(chargeOrder.getAccount());
		callback.setMobile(chargeOrder.getMobile());
		callback.setUrl(chargeOrder.getBackUrl());
		callback.setOrderId(chargeOrder.getId());

		Map<String, String> params = new HashMap<String, String>();
		params.put("taskId", String.valueOf(chargeOrder.getId()));
		params.put("orderId", chargeOrder.getAgentOrderId());
		params.put("mobile", chargeOrder.getMobile());
		params.put("actualPrice", String.valueOf(chargeOrder.getDiscountMoney()));
		params.put("status", String.valueOf(chargeOrder.getChargeStatus()));
		params.put("error", chargeOrder.getError());
		params.put("reportTime", String.valueOf(chargeOrder.getReportTime()));

		String query = "";
		query = HttpUtils.createFromParams(params);
		callback.setRequest(query);
		callbackService.saveOrUpdate(callback);
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
	}
}
