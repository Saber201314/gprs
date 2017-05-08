package com.shlr.gprs.manager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.shlr.gprs.utils.okhttp.HttpUtils;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.shlr.gprs.vo.BackMoneyVO;
import com.shlr.gprs.vo.ResultBaseVO;

import okhttp3.Response;

/**
 * @author Administrator 充值管理
 */

public class ChargeManager {
	static Logger logger=LoggerFactory.getLogger(ChargeManager.class);
	
	static GprsPackageService gprsPackageService;
	static ChargeOrderService chargeOrderService;
	static PayLogService payLogService;
	static CallbackService callbackService;
	static PricePaperService pricePaperService;
	static ChannelTemplateService channelTemplateService;
	static ChannelService channelService;

	public static List<ChannelManager> channelManagerList = new LinkedList<ChannelManager>();
	public static Map<String, ChargeTemplate> chargeTemplateMap = new HashMap<String, ChargeTemplate>();

	public static Boolean moreOperValidMap = false;

	private static class ChargeManagerHolder {
		static ChargeManager chargeManager = new ChargeManager();
	}

	public static ChargeManager getInstance() {

		gprsPackageService = WebApplicationContextManager.getApplicationContext().getBean(GprsPackageService.class);
		chargeOrderService = WebApplicationContextManager.getApplicationContext().getBean(ChargeOrderService.class);
		payLogService = WebApplicationContextManager.getApplicationContext().getBean(PayLogService.class);
		callbackService = WebApplicationContextManager.getApplicationContext().getBean(CallbackService.class);
		pricePaperService = WebApplicationContextManager.getApplicationContext().getBean(PricePaperService.class);
		channelTemplateService = WebApplicationContextManager.getApplicationContext()
				.getBean(ChannelTemplateService.class);
		channelService = WebApplicationContextManager.getApplicationContext().getBean(ChannelService.class);
		return ChargeManagerHolder.chargeManager;
	}

	public void init() throws Exception {
		List<ChannelTemplate> templateList = channelTemplateService.list();
		for (ChannelTemplate template : templateList) {
			try {
				Class cla = Class.forName("com.shlr.gprs.manager.charge.template." + template.getIdentity());
				Constructor cons;
				cons = cla.getConstructor(
						new Class[] { Integer.TYPE, String.class, String.class, String.class, String.class });
				ChargeTemplate service = (ChargeTemplate) cons.newInstance(new Object[] { Integer.valueOf(template.getId()),
						template.getName(), template.getAccount(), template.getPassword(), template.getSign() });
				chargeTemplateMap.put(template.getIdentity(), service);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("\n"+template.getIdentity()+"-------反射异常-------" + e);
			}
		}
		Map<Integer, Integer> amountMap = channelService.qaueryMonthAmount();

		List<Channel> channelList = channelService.list();
		for (Channel channel : channelList) {
			int amount = 0;
			Integer monthAmount = (Integer) amountMap.get(Integer.valueOf(channel.getId()));
			if (monthAmount != null) {
				amount = monthAmount.intValue();
			}
			channelManagerList.add(new ChannelManager(channel, amount));
		}

	}

	public ResultBaseVO<Object> charge(ChargeOrder chargeOrder) {
		int ignoreCacheCondition = chargeOrder.getIgnoreCacheCondition();
		// 如果是缓存里面的数据
		if (ignoreCacheCondition == 1) {
			// 可能重新分配了流量包
			int packageId = resignPackageId(chargeOrder);
			// 如果是从缓存里面提交的数据,没有找到合适流流量包，直接返回失败
			if (packageId == 0) {
				updateResult(chargeOrder, false, "已退款");
			}
			// 需要重新分配流量包id
			chargeOrder.setPackageId(packageId);
		}
		GprsPackage gprsPackage = gprsPackageService.findById(Integer.valueOf(chargeOrder.getPackageId()));
		// 验证流量包是否存在
		ResultBaseVO<Object> resultBaseDO = isPackageAvailable(chargeOrder, gprsPackage);
		if (!resultBaseDO.isSuccess()) {
			return resultBaseDO;
		}
		List<PayLog> payLogList = new LinkedList<PayLog>();
		// 验证报价单是否存在
		ResultBaseVO<Object> validateResult = validateAgentPaper(chargeOrder, payLogList, gprsPackage);
		if (!validateResult.isSuccess()) {
			return validateResult;
		}
		// 开始分配充值通道
		resultBaseDO = routeToCharge(chargeOrder);// 如果充值通道直接返回提交失败
		if (!resultBaseDO.isSuccess()) {
			// 获取订单路由状态
			int routeFlag = chargeOrder.getRouteFlag();
			// 如果需要路由
			if (routeFlag == 1) {
				if (failFirstSubmitedOrderRoutable(chargeOrder)) {
					resultBaseDO.addError(null);
					resultBaseDO.setSuccess(true);
				} else {
					return resultBaseDO;
				}
			} else {
				if (ignoreCacheCondition == 0) {
					chargeOrder.setError(resultBaseDO.getError());
					chargeOrderService.saveOrUpdate(chargeOrder);
				} else {
					// 如果是从缓存里面提交的数据,接口返回异常时直接失败退款
					updateResult(chargeOrder, false, "已退款");
				}
				return resultBaseDO;
			}
		}

		if (chargeOrder.getCurRoutePos() > 2) {
			return resultBaseDO;
		}

		if (chargeOrder.getProfit() != null) {
			chargeOrder.setSubmitStatus(1);
			chargeOrderService.saveOrUpdate(chargeOrder);
			return resultBaseDO;
		}

		// 如果是正常提交的数据
		if (ignoreCacheCondition == 0) {
			for (PayLog payLog : payLogList) {
				payLog.setOrderId(chargeOrder.getId());
				payLogService.saveOrUpdate(payLog);
			}
			
		}
		for (PayLog payLog : payLogList) {
			// 如果是提交的数据产生了费用
			if (payLog.getMoney() != 0.0D) {
				if (ignoreCacheCondition == 0) {
					PayManager.getInstance().addToPay(payLog);
				}
				// 更新订单实际支付金额
				Double factMoney = Math.round(payLog.getMoney() * 100) / 100.0;
				chargeOrder.setDiscountMoney(factMoney);
				// 获取基本面值
				Double basicMoney = chargeOrder.getMoney();
				// 获取上游接入方折扣
				Double discount = chargeOrder.getDiscount();
				if (discount != null) {
					// 利润盈余：下游放出时的扣费金额-上游接入时的扣费金额的(基本面值*接入时的折扣)
					Double profit = factMoney - basicMoney * discount;
					// 设置利润盈余
					chargeOrder.setProfit(Math.round(profit * 100) / 100.0);
					payLog.setProfit(Math.round(profit * 100) / 100.0);
				}
			}
		}
		chargeOrderService.saveOrUpdate(chargeOrder);
		return resultBaseDO;

	}

	/**
	 * 验证代理商报价单
	 * 
	 * @param chargeOrder
	 * @param payLogList
	 * @param gprsPackage
	 * @return
	 */
	public ResultBaseVO<Object> validateAgentPaper(ChargeOrder chargeOrder, List<PayLog> payLogList,
			GprsPackage gprsPackage) {
		ResultBaseVO<Object> resultBaseDO = new ResultBaseVO<Object>();

		Users agent = UsersCache.usernameMap.get(chargeOrder.getAccount());

		if (agent.getStatus() == -1) {
			resultBaseDO.addError("用户被锁定！");
			return resultBaseDO;
		}
		if (agent.getValidateTime().getTime() < System.currentTimeMillis()) {
			resultBaseDO.addError("用户已过有效期！");
			return resultBaseDO;
		}
		PricePaper pricePaper = pricePaperService.findById(Integer.valueOf(agent.getPaperId()));
		if (pricePaper == null) {
			resultBaseDO.addError("报价单不存在");
			return resultBaseDO;
		}
		Map<Integer, Double> packageMap = pricePaper.getPackageMap();
		Double discount = packageMap.get(Integer.valueOf(chargeOrder.getPackageId()));
		if (discount == null) {
			resultBaseDO.addError("不支持该流量包");
			return resultBaseDO;
		}

		// 设置是否带票
		Map<Integer, Integer> packageBillMap = pricePaper.getPackageBillMap();
		if (packageBillMap != null) {
			Integer payBill = packageBillMap.get(Integer.valueOf(chargeOrder.getPackageId()));
			chargeOrder.setPayBill(payBill == null ? 0 : payBill);
		}
		double paymoney = gprsPackage.getMoney() * discount.doubleValue() / 10.0D;
		if (agent.getMoney() < paymoney) {
			// 如果提交的是缓存里面的数据
			int ignoreCacheCondition = chargeOrder.getIgnoreCacheCondition();
			if (ignoreCacheCondition == 0) {
				resultBaseDO.addError("余额不足");
				return resultBaseDO;
			}
		}
		if (payLogList != null) {
			PayLog payLog = new PayLog();
			payLog.setUserId(agent.getId());
			payLog.setAccount(agent.getUsername());
			payLog.setAgent(agent.getAgent());
			payLog.setDiscount(discount);
			if (agent.getType() == 1) {
				chargeOrder.setOutDiscount(10D);
			} else {
				chargeOrder.setOutDiscount(discount);
			}
			// 设置代理商订单号码
			payLog.setAgentOrderId(chargeOrder.getAgentorderId());
			payLog.setType(1);
			payLog.setMemo(chargeOrder.getMobile() + "，" + chargeOrder.getAmount() + "M");
			double money = Math.round(paymoney * 100) / 100.0;
			payLog.setMoney(money);

			double balance = agent.getMoney() - money;
			payLog.setBalance(Math.round(balance * 100) / 100.0);

			if (paymoney == 0.0D) {
				payLog.setStatus(1);
			}
			payLogList.add(payLog);
		}

		return resultBaseDO;
	}

	/**
	 * 重新分配流量包编码
	 * 
	 * @param chargeOrder
	 * @return
	 */
	public Integer resignPackageId(ChargeOrder chargeOrder) {
		Users currentUser = UsersCache.usernameMap.get(chargeOrder.getAccount());
		int packageId = 0;
		List<GprsPackage> packageList = gprsPackageService.getPackageList(currentUser.getId());
		int nCount = 0;
		for (GprsPackage gprsPackage : packageList) {
			if ((gprsPackage.getAmount() != chargeOrder.getAmount()) || (gprsPackage.getType() != chargeOrder.getType())
					|| (gprsPackage.getLocationType() != chargeOrder.getLocationType())
					|| ((!gprsPackage.getLocations().equals("全国"))
							&& (gprsPackage.getLocations().indexOf(chargeOrder.getLocation()) == -1)))
				continue;
			nCount++;
			if (chargeOrder.getRouteFlag() == 1) {
				int curRoutePos = chargeOrder.getCurRoutePos();
				if (curRoutePos == 1 && nCount == 1) {
					packageId = gprsPackage.getId();
					break;
				} else if (curRoutePos == 2 && nCount == 2) {
					packageId = gprsPackage.getId();
					break;
				} else if (curRoutePos == 3 && nCount == 3) {
					packageId = gprsPackage.getId();
					break;
				}
			} else {
				packageId = gprsPackage.getId();
				break;
			}
		}
		return packageId;
	}

	/**
	 * 验证流量包编码是否有效
	 * 
	 * @param chargeOrder
	 * @param gprsPackage
	 * @return
	 */
	public ResultBaseVO<Object> isPackageAvailable(ChargeOrder chargeOrder, GprsPackage gprsPackage) {
		ResultBaseVO<Object> result = new ResultBaseVO<Object>();
		// GprsPackage gprsPackage = gprsPackageService.findById(Integer
		// .valueOf(chargeOrder.getPackageId()));
		if ((gprsPackage == null) || (gprsPackage.getStatus() == -1)) {
			result.addError("流量包不存在");
			return result;
		}
		if (chargeOrder.getType() != gprsPackage.getType()) {
			result.addError("运营商类型错误");
			return result;
		}
		if ((!gprsPackage.getLocations().equals("全国"))
				&& (gprsPackage.getLocations().indexOf(chargeOrder.getLocation()) == -1)) {
			result.addError("流量包不支持该地区");
			return result;
		}
		chargeOrder.setLocationType(gprsPackage.getLocationType());
		chargeOrder.setAmount(gprsPackage.getAmount());
		chargeOrder.setMoney(gprsPackage.getMoney());
		return result;
	}

	private ResultBaseVO<Object> routeToCharge(ChargeOrder chargeOrder) {
		ResultBaseVO<Object> result = new ResultBaseVO<Object>();

		Map availableMap = new TreeMap();
		Integer priority;
		for (ChannelManager channelManager : channelManagerList) {
			priority = channelManager.getPriority(chargeOrder.getPackageId());
			if (priority == null) {
				continue;
			}
			Channel channel = channelService.findById(Integer.valueOf(channelManager.getChannelId()));
			if ((channel == null) || (channel.getStatus() == -1)) {
				continue;
			}
			if ((channel.getMonth_limit() != 0)
					&& (channelManager.getTotalAmount() + chargeOrder.getAmount() > channel.getMonth_limit())) {
				continue;
			}
			availableMap.put(priority, channelManager);
		}
		if (availableMap.size() == 0) {
			result.addError("没有可用的通道资源");
			return result;
		}
		Collection<ChannelManager> managerList = availableMap.values();

		ResultBaseVO<?> chargeResult = null;

		// 循环匹配上游通道
		for (ChannelManager manager : managerList) {
			Channel channel = channelService.findById(Integer.valueOf(manager.getChannelId()));
			ChargeTemplate template = (ChargeTemplate) chargeTemplateMap.get(channel.getTemplate());
			if (template == null) {
				continue;
			}

			if ((chargeOrder.getTakeTime() == 1) && (!template.supportTaketime2())) {
				continue;
			}

			int ignoreCacheCondition = chargeOrder.getIgnoreCacheCondition();

			// 上游接入的折扣
			Double discount = manager.getDiscount(chargeOrder.getPackageId());
			chargeOrder.setDiscount(discount / 10);

			// 如果是正常提交的订单
			if (ignoreCacheCondition == 0) {
				// 设置了缓存，则按照缓存的通道和省份，选择性的缓存数据
				if (!CollectionUtils.isEmpty(ChannelCache.cacheChannelList)) {
					for (Map<String, Object> map : ChannelCache.cacheChannelList) {
						String channelName = String.valueOf(map.get("channel"));
						if (!StringUtils.isEmpty(channelName)) {
							if (channelName.equals(channel.getName())) {
								chargeOrder.setCacheFlag(1);
								chargeOrderService.saveOrUpdate(chargeOrder);
								return result;
							}
						}
						String location = String.valueOf(map.get("province"));
						if (!StringUtils.isEmpty(location)) {
							if (location.equals(chargeOrder.getLocation())) {
								chargeOrder.setCacheFlag(1);
								chargeOrderService.saveOrUpdate(chargeOrder);
								return result;
							}
						}

					}
				}
			} else {
				// 否则如果是从缓存中提交的订单，则忽略已经设置的缓存条件，直接提交到通道
				chargeOrder.setCacheFlag(0);
				chargeOrder.setSubmitChannel(channel.getId());
				chargeOrder.setSubmitTemplate(template.getTemplateId());
			}

			chargeOrderService.saveOrUpdate(chargeOrder);
			// 开始充值
			chargeResult = template.charge(chargeOrder);

			// 如果提交成功
			if (chargeResult.isSuccess()) {
				manager.addAmount(chargeOrder.getAmount());

				chargeOrder.setSubmitChannel(channel.getId());
				chargeOrder.setSubmitTemplate(template.getTemplateId());
				chargeOrder.setSubmitStatus(1);
				chargeOrder.setSubmitTime(new Date());

				chargeOrder.setChargeTaskId(String.valueOf(chargeResult.getModule()));

				return result;
				// 如果提交失败
			} else {
				chargeOrder.setSubmitChannel(channel.getId());
				chargeOrder.setSubmitTemplate(template.getTemplateId());
				// 如果从缓存里面提交出来的数据
				if (ignoreCacheCondition == 1) {
					chargeOrder.setSubmitStatus(1);
					chargeOrder.setSubmitTime(new Date());
					chargeOrder.setChargeTaskId(String.valueOf(chargeOrder.getId()));
				}
			}
			if (managerList.size() == 1) {
				result.addError(chargeResult.getError());
				return result;
			}
			break;
		}

		result.addError(chargeResult.getError());
		return result;
	}

	public void updateResult(ChargeOrder chargeOrder, Boolean success, String msg) {
		if (chargeOrder.getChargeStatus() != 0) {
			return;
		}

		chargeOrder.setChargeStatus(success ? 1 : -1);
		chargeOrder.setError(success ? null : msg);
		chargeOrder.setReportTime(System.currentTimeMillis());

		int cacheFlag = chargeOrder.getCacheFlag();
		// 这里的作用只是失败缓存中的数据
		if (cacheFlag == 1) {
			chargeOrderService.forceToFailOrder(chargeOrder.getId(), cacheFlag, success ? 1 : -1, msg);
		} else {
			// 非缓存数据
			String chargeTaskId = chargeOrder.getChargeTaskId();
			if (StringUtils.isEmpty(chargeTaskId)) {
				chargeTaskId = String.valueOf(chargeOrder.getId());
			}
			chargeOrderService.updateChargeStatus(chargeTaskId, chargeOrder.getSubmitTemplate(), success ? 1 : -1, msg);
		}
		if (!success) {
			// 如果充值失败..
			PayLog payLog = new PayLog();
			payLog.setType(Integer.valueOf(1));
			payLog.setOrderId(Integer.valueOf(chargeOrder.getId()));
			payLog.setStatus(Integer.valueOf(0));
			List<PayLog> payLogList = payLogService.listByExample(payLog);
			for (PayLog item : payLogList) {
				PayManager.getInstance().addToPay(new BackMoneyVO(item));
			}
		} else {
			// 充值成功....
			PayLog payLog = new PayLog();
			payLog.setType(Integer.valueOf(1));
			payLog.setOrderId(Integer.valueOf(chargeOrder.getId()));
			payLog.setStatus(Integer.valueOf(0));
			List<PayLog> payLogList = payLogService.listByExample(payLog);
			for (PayLog item : payLogList) {
				// 充值成功
				PayManager.getInstance().chargeSuccess(item);
			}
		}
		// 回调订单状态
		if (!StringUtils.isEmpty(chargeOrder.getBackUrl())) {
			callback(chargeOrder);
		}
	}

	public void updateResult(int templateId, String taskId, boolean success, String msg) {
		ChargeOrder queryChargeOrderDO = new ChargeOrder();
		queryChargeOrderDO.setChargeTaskId(taskId);
		queryChargeOrderDO.setSubmitTemplate(Integer.valueOf(templateId));
		List<ChargeOrder> orderlist = chargeOrderService.selectOneByExample(taskId, templateId);
		if (orderlist.isEmpty()) {
			return;
		}
		ChargeOrder chargeOrder = (ChargeOrder) orderlist.get(0);
		if (chargeOrder.getChargeStatus() != 0) {
			return;
		}

		if (!success) {
			// 如果订单已经开启了路由
			int routeFlag = chargeOrder.getRouteFlag();
			if (routeFlag == 1) {
				if (failChargedOrderRoutable(chargeOrder)) {
					return;
				}
			}
		}
		// 更新订单状态
		updateResult(chargeOrder, success, msg);
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
			chargeOrder.setSubmitStatus(1);
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
		params.put("orderId", chargeOrder.getAgentorderId());
		params.put("mobile", chargeOrder.getMobile());
		params.put("actualPrice", String.valueOf(chargeOrder.getDiscountMoney()));
		params.put("status", String.valueOf(chargeOrder.getChargeStatus()));
		params.put("error", chargeOrder.getError());
		params.put("reportTime", String.valueOf(chargeOrder.getReportTime()));

		String query = "", response = "";
		query = HttpUtils.createFromParams(params);
		callback.setRequest(query);
		for (int i = 0; i < 5; i++) {
			try {
				Response execute = OkhttpUtils.getInstance()
						.post(chargeOrder.getBackUrl())
						.params(params, true)
						.execute();
				response = execute.body().string();
				if (StringUtils.isEmpty(response)) {
					continue;
				} else {
					callback.setResponse(response);
					if ("ok".equals(response)) {
						chargeOrder.setReport(1);
						if (chargeOrder.getCacheFlag() == 1) {
							chargeOrder.setCacheFlag(0);
						}
						break;// 收到ok确认后 不再推送
					}

				}
			} catch (Exception e) {
				// 出现异常就重试
				continue;
			}
		}
		chargeOrderService.saveOrUpdate(chargeOrder);
		callbackService.saveOrUpdate(callback);
	}
}
