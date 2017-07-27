package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 */
@Table(name="g_agent_charge_log")
public class AgentChargeLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -401689610475912509L;
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	@Column
	private Integer userId;
	@Column
	private String account;
	@Column
	private String agent;
	@Column
	private Double money;
	@Column
	private Double balance;
	@Column
	private Integer payType;
	@Column
	private String optionUser;
	@Column
	private Date optionTime;
	@Column
	private String memo;
	@Column
	private Integer status;
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
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getOptionUser() {
		return optionUser;
	}
	public void setOptionUser(String optionUser) {
		this.optionUser = optionUser;
	}
	public Date getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
