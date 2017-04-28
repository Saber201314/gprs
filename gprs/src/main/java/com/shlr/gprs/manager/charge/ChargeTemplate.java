package com.shlr.gprs.manager.charge;

import java.util.List;

import org.springframework.util.StringUtils;

import com.shlr.gprs.cache.ChannelTemplateCodeCache;
import com.shlr.gprs.domain.ChannelTemplateCode;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.services.ChannelLogService;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.vo.ResultBaseVO;

/**
 * @author Administrator
 */

public abstract class ChargeTemplate {
	protected static ChannelLogService channelLogService;
	protected static ChargeOrderService chargeOrderService;
	protected static PayLogService payLogService;
	// protected static PaycardService paycardService;
	// protected static SuitePayLogService suitePayLogService;
	// protected static ChannelTemplateCodeService channelTemplateCodeService;
	protected static UserService userService;

	protected String account;
	protected String password;
	protected String key;
	protected String templateName;
	protected int templateId;

	static {
		channelLogService = WebApplicationContextManager.getApplicationContext().getBean(ChannelLogService.class);
		chargeOrderService = WebApplicationContextManager.getApplicationContext().getBean(ChargeOrderService.class);
		payLogService = WebApplicationContextManager.getApplicationContext().getBean(PayLogService.class);
		userService = WebApplicationContextManager.getApplicationContext().getBean(UserService.class);
	}

	public ChargeTemplate(int templateId, String templateName, String account, String password, String key) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.account = account;
		this.password = password;
		this.key = key;
	}

	protected void updateResult(String taskId, boolean success, String msg) {
		ChargeManager.getInstance().updateResult(this.templateId, taskId, success, msg);
	}

	protected String getPackageCode(ChargeOrder chargeOrder) {
		List<ChannelTemplateCode> codeList = ChannelTemplateCodeCache.codeMapByChannel
				.get(Integer.valueOf(this.templateId));
		if ((codeList != null) && (!codeList.isEmpty())) {
			for (ChannelTemplateCode code : codeList) {
				String location = code.getLocation();

				if ((code.getType() == chargeOrder.getType())
						&& (code.getLocationType() == chargeOrder.getLocationType())
						&& (code.getAmount() == chargeOrder.getAmount())) {
					if (!StringUtils.isEmpty(location)) {
						if (location.equals(chargeOrder.getLocation())) {
							return code.getCode();
						}
						continue;
					}
					return code.getCode();
				}
			}
		}
		return null;
	}
	/**
	 * 模板充值方法   子类实现
	 * @param paramChargeOrder
	 * @return
	 */
	public abstract ResultBaseVO<String> charge(ChargeOrder paramChargeOrder);
	
	/**
	 * 查询订单状态
	 * @return
	 */
	public abstract ResultBaseVO<Object> getChargeStatus();
	
	/**
	 * 查询余额
	 * @return
	 */
	public abstract ResultBaseVO<Object> getBalance();

	public boolean supportTaketime2() {
		return false;
	}

	public int getTemplateId() {
		return this.templateId;
	}
}
