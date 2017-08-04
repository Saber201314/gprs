package com.shlr.gprs.manager.charge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.util.StringUtils;

import com.shlr.gprs.cache.ChannelTemplateCodeCache;
import com.shlr.gprs.domain.ChannelLog;
import com.shlr.gprs.domain.ChannelTemplateCode;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.services.ChannelLogService;
import com.shlr.gprs.services.ChannelTemplateCodeService;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.vo.ChargeResponsVO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */

public abstract class ChargeTemplate {
	
	
	
	protected static ChannelLogService channelLogService;
	protected static ChargeOrderService chargeOrderService;
	protected static PayLogService payLogService;
	// protected static PaycardService paycardService;
	// protected static SuitePayLogService suitePayLogService;
	protected static ChannelTemplateCodeService channelTemplateCodeService;
	protected static UserService userService;

	protected String account;
	protected String password;
	protected String key;
	protected String templateName;
	protected int templateId;
	private SimpleDateFormat orderidsdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
	private Random random = new Random();

	static {
		channelLogService = WebApplicationContextManager.getBean(ChannelLogService.class);
		chargeOrderService = WebApplicationContextManager.getBean(ChargeOrderService.class);
		payLogService = WebApplicationContextManager.getBean(PayLogService.class);
		userService = WebApplicationContextManager.getBean(UserService.class);
		channelTemplateCodeService = WebApplicationContextManager.getBean(ChannelTemplateCodeService.class);
	}
	public ChargeTemplate(){
		
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
		Example example = new Example(ChannelTemplateCode.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("templateId", templateId);
		List<ChannelTemplateCode> codeList = channelTemplateCodeService.listByTemplateId(example);
		if ((codeList != null) && (!codeList.isEmpty())) {
			for (ChannelTemplateCode code : codeList) {
				String location = code.getLocation();
				if ((code.getType() == chargeOrder.getType())
						&& (code.getRangeType() == chargeOrder.getRangeType())
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
	public void saveChannelLog(ChargeOrder chargeOrder,String body){
		ChannelLog channelLog = new ChannelLog();
		channelLog.setTemplateId(this.templateId);
		channelLog.setTemplateName(this.templateName);
		channelLog.setMobile(chargeOrder.getMobile());
		channelLog.setOrderId(chargeOrder.getChargeTaskId());
		channelLog.setResponse(body);
		channelLogService.save(channelLog);
	}
	/**
	 * 生成订单号
	 * @return
	 */
	public String genTaskId(){
		String myTime = orderidsdf.format(new Date());
		int randomInt = random.nextInt(10000);
		String randomStr = String.valueOf(randomInt);
		int len = randomStr.length();
		if (len < 4) {
			for (int i = 1; i <= 4 - len; i++)
				randomStr = "0" + randomStr;
		}
		return myTime+randomStr;
	}
	/**
	 * 模板充值方法   子类实现
	 * @param paramChargeOrder
	 * @return
	 */
	public abstract ChargeResponsVO charge(ChargeOrder chargeOrder);
	
	/**
	 * 查询订单状态
	 * @return
	 */
	public abstract ChargeResponsVO getChargeStatus();
	
	/**
	 * 查询余额
	 * @return
	 */
	public abstract ChargeResponsVO getBalance();

	public boolean supportTaketime2() {
		return false;
	}

	public int getTemplateId() {
		return this.templateId;
	}
}
