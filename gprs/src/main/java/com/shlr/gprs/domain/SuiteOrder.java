package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Administrator
 */
@Table(name="g_suite_order")
public class SuiteOrder implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -6389282002527737240L;
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	private String account;
	private String agent;
	private Integer suiteId;
	private String suiteName;
	private Integer type;
	private Integer locationType;
	private String locations;
	private Integer amount;
	private Integer clearMonthEnd;
	private Integer validate;
	private Double money;
	private Integer amountUsed;
	private Date optionTime;
	private Date beginTime;
	private Date availableTime;
	private Integer autoCharge;
	private Integer status;
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
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Integer getSuiteId() {
		return suiteId;
	}
	public void setSuiteId(Integer suiteId) {
		this.suiteId = suiteId;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getLocationType() {
		return locationType;
	}
	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
	}
	public String getLocations() {
		return locations;
	}
	public void setLocations(String locations) {
		this.locations = locations;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getClearMonthEnd() {
		return clearMonthEnd;
	}
	public void setClearMonthEnd(Integer clearMonthEnd) {
		this.clearMonthEnd = clearMonthEnd;
	}
	public Integer getValidate() {
		return validate;
	}
	public void setValidate(Integer validate) {
		this.validate = validate;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getAmountUsed() {
		return amountUsed;
	}
	public void setAmountUsed(Integer amountUsed) {
		this.amountUsed = amountUsed;
	}
	public Date getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getAvailableTime() {
		return availableTime;
	}
	public void setAvailableTime(Date availableTime) {
		this.availableTime = availableTime;
	}
	public Integer getAutoCharge() {
		return autoCharge;
	}
	public void setAutoCharge(Integer autoCharge) {
		this.autoCharge = autoCharge;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
