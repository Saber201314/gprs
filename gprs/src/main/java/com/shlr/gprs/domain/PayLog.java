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
	private Double money;//扣费金额
	private Double balance;//余额
	private Integer type;//扣费类型   1 冲扣值  2 充值扣费  3 失败退款
	private String memo;//备注
	private Date optionTime;//扣费时间
	private Integer status = 0;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
