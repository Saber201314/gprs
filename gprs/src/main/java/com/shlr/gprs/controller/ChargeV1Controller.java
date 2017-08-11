package com.shlr.gprs.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
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
import com.shlr.gprs.manager.PayManager;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.services.MobileAreaService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.JSONUtils;
import com.shlr.gprs.utils.MD5Utils;
import com.shlr.gprs.utils.MailUtils;
import com.shlr.gprs.utils.MobileUtil;
import com.shlr.gprs.vo.BalanceResponsVO;
import com.shlr.gprs.vo.ChargeResponsVO;
import com.xiaoleilu.hutool.crypto.digest.DigestUtil;
import com.xiaoleilu.hutool.util.StrUtil;

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
		if (!StringUtils.isEmpty(currentUser.getWhiteIp())) {
			String ip = request.getRemoteAddr();
			if (currentUser.getWhiteIp().indexOf(ip) == -1) {
				result.setSuccess(false);
				result.setMsg("ip限制");
				return JSON.toJSONString(result);
			}
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
		chargeOrder.setIp(request.getRemoteAddr());
		// 开始充值
		result = ChargeManager.getInstance().charge(chargeOrder);
		return JSON.toJSONString(result);
	}
	@RequestMapping("/v1/balance.action")
	@ResponseBody
	public String getBalance(String account,String sign) {
		BalanceResponsVO result = new BalanceResponsVO();
		Users currentUser = userService.findByUsername(account);
		if(currentUser == null){
			result.setSuccess(false);
			result.setMsg("账号不存在");
			return JSONUtils.toJsonString(result);
		}
		StringBuffer resign = new StringBuffer();
		resign.append("username=").append(account);
		resign.append("&key=").append(currentUser.getApiKey());
		String md5sign = DigestUtil.md5Hex(resign.toString());
		if (!md5sign.equals(sign.toLowerCase())) {
			result.setSuccess(false);
			result.setMsg("签名不正确");
			return JSON.toJSONString(result);
		}
		result.setBalance(currentUser.getMoney());
		return JSONUtils.toJsonString(result);
	}
}
