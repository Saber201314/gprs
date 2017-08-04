package com.shlr.gprs.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.cache.PricePaperCache;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.constants.Const;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.MobileArea;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.services.MobileAreaService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.MD5Utils;
import com.shlr.gprs.utils.MailUtils;
import com.shlr.gprs.utils.MobileUtil;
import com.shlr.gprs.vo.ChargeResponsVO;
import com.xiaoleilu.hutool.crypto.digest.DigestUtil;

/**
 * @author xucong
 * @version 创建时间：2017年4月27日 下午8:54:09
 * 
 */
@Controller
public class ChargeV1Controller {
	@Resource
	UserService userService;
	@Resource
	GprsPackageService gprsPackageService;
	@Resource
	ChargeOrderService chargeOrderService;
	@Resource
	MobileAreaService mobileAreaService;

	@RequestMapping(value="/v1/charge.action")
	@ResponseBody
	public String charge(HttpServletRequest request, HttpSession session, 
			String username, String sign,
			String mobile, Integer amount, Integer range,@RequestParam(required=false)String backUrl, String orderId) {
		ChargeResponsVO result = new ChargeResponsVO();
		if (!Const.isApiSwitch()) {
			result.setSuccess(false);
			result.setMsg("接口维护中,请稍后访问");
			return JSON.toJSONString(result);
		}
		if (MobileUtil.isNotMobileNO(mobile)) {
			result.setSuccess(false);
			result.setMsg("手机号码不正确");
			return JSON.toJSONString(result);
		}
		if (amount == null || amount  == 0) {
			result.setSuccess(false);
			result.setMsg("流量包大小不正确");
			return JSON.toJSONString(result);
		}
		Users currentUser = userService.findByUsername(username);

		if ((currentUser == null) || (currentUser.getType() != 2)) {
			result.setSuccess(false);
			result.setMsg("用户名不存在");
			return JSON.toJSONString(result);
		}
		if (!StringUtils.isEmpty(currentUser.getWhiteIp())) {
			String ip = request.getRemoteAddr();
			if (currentUser.getWhiteIp().indexOf(ip) == -1) {
				result.setSuccess(false);
				result.setMsg("ip限制");
				return JSON.toJSONString(result);
			}
		}
		StringBuffer resign = new StringBuffer();
		resign.append("username=").append(username);
		resign.append("&mobile=").append(mobile);
		resign.append("&amount=").append(amount);
		resign.append("&range=").append(range);
		resign.append("&key=").append(currentUser.getApiKey());
		String md5sign = DigestUtil.md5Hex(resign.toString());
		if (!md5sign.equals(sign.toLowerCase())) {
			result.setSuccess(false);
			result.setMsg("签名不正确");
			return JSON.toJSONString(result);
		}
		MobileArea findByMobile = mobileAreaService.findByMobile(mobile.substring(0, 7));
		String location = "";
		String catNmae = "";
		if(findByMobile != null){
			location = findByMobile.getProvince();
			catNmae = findByMobile.getServiceProvider();
		}
		if (StringUtils.isEmpty(location)) {
			result.setSuccess(false);
			result.setMsg("号码查询归属地失败");
			return JSON.toJSONString(result);
		}
		if (currentUser.getStatus() == -1) {
			result.setSuccess(false);
			result.setMsg("用户被锁定！");
			return JSON.toJSONString(result);
		}
		if (currentUser.getValidateTime().getTime() < System.currentTimeMillis()) {
			result.setSuccess(false);
			result.setMsg("用户已过有效期！");
			return JSON.toJSONString(result);
		}
		
		ChargeOrder chargeOrder = new ChargeOrder();
		chargeOrder.setAccount(currentUser.getUsername());//代理商账号
		chargeOrder.setMobile(mobile);//手机号
		chargeOrder.setType(MobileUtil.checkType(catNmae));//运营商
		chargeOrder.setRangeType(range);//流量类型
		chargeOrder.setLocation(location);//归属地
		chargeOrder.setSubmitType(5);//接口充值
		chargeOrder.setAmount(amount);//流量包大小
		chargeOrder.setBackUrl(backUrl);//回调地址
		chargeOrder.setAgentOrderId(orderId);//下游订单号
		chargeOrder.setOptionTime(new Date());
//		List<GprsPackage> packageList = gprsPackageService.getPackageList(currentUser.getId());
//		// 获取用户的报价单是否需要路由
//		Integer routable = PricePaperCache.idMap.get(currentUser.getPaperId()).getRoutable();
//		// 路由次数
//		Integer routeTime = 0;
//		for (GprsPackage gprsPackage : packageList) {
//			if ((gprsPackage.getAmount() != amount) || (gprsPackage.getType() != chargeOrder.getType())
//					|| (gprsPackage.getLocationType() != chargeOrder.getLocationType())
//					|| ((!gprsPackage.getLocations().equals("全国"))
//							&& (gprsPackage.getLocations().indexOf(chargeOrder.getLocation()) == -1)))
//				continue;
//			routeTime++;
//			if (routeTime == 1) {
//				packageId = gprsPackage.getId();
//			}
//			if (routable == 0) {
//				// 如果不需要路由，取第一流量包，直接返回
//				break;
//			} else {
//				if (routeTime == 1) {
//					chargeOrder.setPackageId1(gprsPackage.getId());
//				} else if (routeTime == 2) {
//					chargeOrder.setPackageId2(gprsPackage.getId());
//				} else if (routeTime == 3) {
//					chargeOrder.setPackageId3(gprsPackage.getId());
//					// 只路由3次，如果路由次数达到3次，则直接返回
//					break;
//				}
//			}
//		}
//		if (routeTime > 1) {
//			// 设置需要路由
//			chargeOrder.setRouteFlag(1);
//			// 设置路由次数
//			chargeOrder.setRouteTimes(routeTime);
//			// 设置当前路由位置
//			chargeOrder.setCurRoutePos(1);
//		}
		// 匹配报价
		result = ChargeManager.getInstance().charge(chargeOrder);
		return JSON.toJSONString(result);
	}
}
