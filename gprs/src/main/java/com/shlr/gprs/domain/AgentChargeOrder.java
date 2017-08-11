package com.shlr.gprs.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.shlr.gprs.cache.ChannelCache;

/**
* @author xucong
* @version 创建时间：2017年4月9日 下午2:53:32
* 
*/
@Table(name="g_charge_order")
public class AgentChargeOrder {
	
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	@JSONField(serialize=false)
	private String account;
	
	private String mobile;//手机号
	
	private Integer type;//运营商类型   1 移动 2联通 3电信
	
	private Integer rangeType;//流量类型 0 全国 1省内     (我们放给下游是1和2，加了1，根据上游自行减1)
	
	private String location;//地区 
	
	private Integer amount;//包大小
	
	private Double payMoney;//扣费金额
	
	private Date optionTime;//订单生成时间
	
	private Integer chargeStatus;//充值状态  1 未知  2 提交成功  3 提交失败  4 充值成功  5 充值失败
	
	private String reportContent;//报告内容
	
	private String agentOrderId;//下游代理商地单号
	
	@JSONField(serialize=false)
	private Integer cacheFlag = 0;//缓存标志  1 缓存中 0 正常数据

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRangeType() {
		return rangeType;
	}

	public void setRangeType(Integer rangeType) {
		this.rangeType = rangeType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Date getOptionTime() {
		return optionTime;
	}

	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}

	public Integer getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public String getAgentOrderId() {
		return agentOrderId;
	}

	public void setAgentOrderId(String agentOrderId) {
		this.agentOrderId = agentOrderId;
	}

	public Integer getCacheFlag() {
		return cacheFlag;
	}

	public void setCacheFlag(Integer cacheFlag) {
		this.cacheFlag = cacheFlag;
	}
}
