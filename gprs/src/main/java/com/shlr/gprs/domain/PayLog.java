package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
* @author xucong
* @version 创建时间：2017年4月12日 下午9:36:26
* 
*/
@Table(name="g_pay_log")
public class PayLog implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -6230754722536760688L;
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	private Integer userId;//用户
	private String account;//用户名
	private String agent;//代理商等级
	private Integer orderId;//订单号
	private String agentOrderId;//代理商订单号
	private Double money;//扣费金额
	private Double balance;//余额
	private Double discount;//折扣
	private Double profit;//利润
	private Integer type;//流量类型
	private String memo;//备注
	private Date optionTime;//扣费时间
	private Integer status;//操作状态  1:'充值成功',0:'充值中',-1:'已退款',-2:'充值失败'
	@Transient
	private String mobile;//手机号
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}
	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}
	/**
	 * @return the orderId
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the agentOrderId
	 */
	public String getAgentOrderId() {
		return agentOrderId;
	}
	/**
	 * @param agentOrderId the agentOrderId to set
	 */
	public void setAgentOrderId(String agentOrderId) {
		this.agentOrderId = agentOrderId;
	}
	/**
	 * @return the money
	 */
	public Double getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	/**
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	/**
	 * @return the profit
	 */
	public Double getProfit() {
		return profit;
	}
	/**
	 * @param profit the profit to set
	 */
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the optionTime
	 */
	public Date getOptionTime() {
		return optionTime;
	}
	/**
	 * @param optionTime the optionTime to set
	 */
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
