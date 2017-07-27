package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Administrator
 */
@Table(name="g_price_paper")
public class PricePaper implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -4019122186743364396L;
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	private Integer userId;
	private String name;
	private String alias;
	private String memo;
	private Date optionTime;
	private Integer status;
	private String items;
	private Integer routable;
	@Transient
	private Map<Integer, Double> packageDiscountMap;
	@Transient
	private Map<Integer, Integer> packageBillMap;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
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
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public Integer getRoutable() {
		return routable;
	}
	public void setRoutable(Integer routable) {
		this.routable = routable;
	}
	public Map<Integer, Double> getPackageDiscountMap() {
		return packageDiscountMap;
	}
	public void setPackageDiscountMap(Map<Integer, Double> packageDiscountMap) {
		this.packageDiscountMap = packageDiscountMap;
	}
	public Map<Integer, Integer> getPackageBillMap() {
		return packageBillMap;
	}
	public void setPackageBillMap(Map<Integer, Integer> packageBillMap) {
		this.packageBillMap = packageBillMap;
	}
}
