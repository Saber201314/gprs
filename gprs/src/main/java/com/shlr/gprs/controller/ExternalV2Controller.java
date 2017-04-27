package com.shlr.gprs.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.shlr.gprs.cache.PricePaperCache;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.utils.MD5Utils;
import com.shlr.gprs.utils.MobileUtil;
import com.shlr.gprs.vo.ResultBaseVO;

/**
 * @author xucong
 * @version 创建时间：2017年4月27日 下午8:54:09
 * 
 */
@Controller
public class ExternalV2Controller {
	@Resource
	GprsPackageService gprsPackageService;

	public String charge(HttpServletRequest request, HttpSession session, String username, String password,
			String mobile, Integer amount, Integer range, String backUrl, String orderId) {
		ResultBaseVO<Object> result = new ResultBaseVO<Object>();
		if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(password))) {
			result.addError("用户名或密码为空");
			return "success";
		}
		if (MobileUtil.isNotMobileNO(mobile)) {
			result.addError("手机号码不正确");
			return "success";
		}
		if (amount == 0) {
			result.addError("流量包大小不正确");
			return "success";
		}
		Users currentUser = UsersCache.usernameMap.get(username);

		if ((currentUser == null) || (currentUser.getType() != 2)) {
			result.addError("用户名不存在");
			return "success";
		}
		if (!StringUtils.isEmpty(currentUser.getWhiteIp())) {
			String ip = request.getRemoteAddr();
			if (currentUser.getWhiteIp().indexOf(ip) == -1) {
				result.addError("ip限制");
				return "success";
			}
		}
		String sign = MD5Utils.getMd5(currentUser.getUsername() + currentUser.getPassword() + mobile);
		if (!sign.equals(password.toLowerCase())) {
			result.addError("密码不正确");
			return "success";
		}
		String location = MobileUtil.getAddress(mobile);
		if (StringUtils.isEmpty(location)) {
			result.addError("号码查询归属地失败");
			return "success";
		}
		ChargeOrder chargeOrder = new ChargeOrder();
		chargeOrder.setAccount(currentUser.getUsername());//代理商账号
		chargeOrder.setType(MobileUtil.checkType(mobile));//运营商
		chargeOrder.setLocation(location);//归属地
		chargeOrder.setMobile(mobile);//手机号哦
		chargeOrder.setLocationType(range + 1);//流量类型
		chargeOrder.setSubmitType(5);//接口充值
		chargeOrder.setTakeTime(0);

		chargeOrder.setAmount(amount);//流量包大小
		chargeOrder.setBackUrl(backUrl);//回调地址
		chargeOrder.setAgentorderId(orderId);//下游订单号
		chargeOrder.setIgnoreCacheCondition(0);//正常提交
		Integer packageId = 0;
		List<GprsPackage> packageList = gprsPackageService.getPackageList(currentUser.getId());
		// 获取用户的报价单是否需要路由
		Integer routable = PricePaperCache.idMap.get(currentUser.getPaperId()).getRoutable();
		// 路由次数
		Integer routeTime = 0;
		for (GprsPackage gprsPackage : packageList) {
			if ((gprsPackage.getAmount() != amount) || (gprsPackage.getType() != chargeOrder.getType())
					|| (gprsPackage.getLocationType() != chargeOrder.getLocationType())
					|| ((!gprsPackage.getLocations().equals("全国"))
							&& (gprsPackage.getLocations().indexOf(chargeOrder.getLocation()) == -1)))
				continue;
			routeTime++;
			if (routeTime == 1) {
				packageId = gprsPackage.getId();
			}
			if (routable == 0) {
				// 如果不需要路由，取第一流量包，直接返回
				break;
			} else {
				if (routeTime == 1) {
					chargeOrder.setPackageId1(gprsPackage.getId());
				} else if (routeTime == 2) {
					chargeOrder.setPackageId2(gprsPackage.getId());
				} else if (routeTime == 3) {
					chargeOrder.setPackageId3(gprsPackage.getId());
					// 只路由3次，如果路由次数达到3次，则直接返回
					break;
				}
			}
		}
		if (routeTime > 1) {
			// 设置需要路由
			chargeOrder.setRouteFlag(1);
			// 设置路由次数
			chargeOrder.setRouteTimes(routeTime);
			// 设置当前路由位置
			chargeOrder.setCurRoutePos(1);
		}
		// 如果第一包id存在
		if (packageId == 0) {
			chargeOrder.setError("没有可用的流量包");
			result.addError("没有可用的流量包");
		} else {
			chargeOrder.setPackageId(packageId);
			result = ChargeManager.getInstance().charge(chargeOrder);
			if (!result.isSuccess()) {
				chargeOrder.setError(result.getError());
			}
		}
		return "";
	}
}
