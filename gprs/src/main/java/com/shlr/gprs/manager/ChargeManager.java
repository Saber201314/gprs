package com.shlr.gprs.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.domain.Callback;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.vo.ResultBaseVO;

/**
 * @author Administrator 充值管理
 */

public class ChargeManager {

	GprsPackageService gprsPackageService = new GprsPackageService();
	ChargeOrderService chargeOrderService = new ChargeOrderService();
	PayLogService payLogService = new PayLogService();

	public static Boolean moreOperValidMap = false;

	private static class ChargeManagerHolder {
		static ChargeManager chargeManager = new ChargeManager();
	}

	public static ChargeManager getInstance() {
		return ChargeManagerHolder.chargeManager;
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
		return null;
	}

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
	public void updateResult(ChargeOrder chargeOrder, Boolean success,String msg){
		if (chargeOrder.getChargeStatus() != 0) {
			return;
		}
		
		chargeOrder.setChargeStatus(success ? 1 : -1);
		chargeOrder.setError(success ? null : msg);
		chargeOrder.setReportTime(System.currentTimeMillis());

		int cacheFlag = chargeOrder.getCacheFlag();
		//这里的作用只是失败缓存中的数据
		if(cacheFlag == 1){
			chargeOrderService.forceToFailOrder(chargeOrder.getId(), cacheFlag, success ? 1 : -1, msg);
		}else{
			//非缓存数据
			String chargeTaskId = chargeOrder.getChargeTaskId();
			if(StringUtils.isEmpty(chargeTaskId)){
				chargeTaskId = String.valueOf(chargeOrder.getId());
			}
			chargeOrderService.updateChargeStatus(chargeTaskId, chargeOrder.getSubmitTemplate(), success ? 1 : -1, msg);							
		}
		if (!success){	
			//如果充值失败..			
			PayLog payLog = new PayLog();
			payLog.setType(Integer.valueOf(1));
			payLog.setOrderId(Integer.valueOf(chargeOrder.getId()));
			payLog.setStatus(Integer.valueOf(0));
			List<PayLog> payLogList = payLogService.listByExample(payLog);
			for (PayLog item : payLogList) {
				PayManager.getInstance().addToPay(item);
			}
		} else {
			//充值成功....
			PayLog payLog = new PayLog();
			payLog.setType(Integer.valueOf(1));
			payLog.setOrderId(Integer.valueOf(chargeOrder.getId()));
			payLog.setStatus(Integer.valueOf(0));
			List<PayLog> payLogList = payLogService.listByExample(payLog);
			for (PayLog item : payLogList) {
				//充值成功
				PayManager.getInstance().chargeSuccess(item);
			}						
		}		
		//回调订单状态
		if (!StringUtils.isEmpty(chargeOrder.getBackUrl())) {
			callback(chargeOrder);
		}		
	}
	public void callback(ChargeOrder chargeOrder){
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
		params.put("reportTime",
				String.valueOf(chargeOrder.getReportTime()));

		String query = "",response="";
	}
}
