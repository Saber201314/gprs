package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author xucong
 * @version 创建时间：2017年4月27日 下午9:11:25
 * 
 */
@Table(name = "g_gprs_package")
public class GprsPackage implements Serializable, Comparable<GprsPackage> {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -2157343875027661620L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	private String name;
	private String alias;
	private Integer amount;
	private Double money;
	private Integer type;
	private String memo;
	private Date optionTime;
	private Integer rangeType;
	private String locations;
	private Integer status;
	@Transient
	private Double discount;
	@Transient
	private Double paymoney;
	@Transient
	private Double level;
	@Transient
	private Integer nSel;
	@Transient
	private List<String> channelNames;
	@Transient
	private String channelName;
	@Transient
	private Integer nBill;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the money
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
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
	 * @param memo
	 *            the memo to set
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
	 * @param optionTime
	 *            the optionTime to set
	 */
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}


	public Integer getRangeType() {
		return rangeType;
	}

	public void setRangeType(Integer rangeType) {
		this.rangeType = rangeType;
	}

	/**
	 * @return the locations
	 */
	public String getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(String locations) {
		this.locations = locations;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return the paymoney
	 */
	public Double getPaymoney() {
		return paymoney;
	}

	/**
	 * @param paymoney
	 *            the paymoney to set
	 */
	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
	}

	/**
	 * @return the level
	 */
	public Double getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(Double level) {
		this.level = level;
	}

	/**
	 * @return the nSel
	 */
	public Integer getnSel() {
		return nSel;
	}

	/**
	 * @param nSel
	 *            the nSel to set
	 */
	public void setnSel(Integer nSel) {
		this.nSel = nSel;
	}

	/**
	 * @return the channelNames
	 */
	public List<String> getChannelNames() {
		return channelNames;
	}

	/**
	 * @param channelNames
	 *            the channelNames to set
	 */
	public void setChannelNames(List<String> channelNames) {
		this.channelNames = channelNames;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName
	 *            the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the nBill
	 */
	public Integer getnBill() {
		return nBill;
	}

	/**
	 * @param nBill
	 *            the nBill to set
	 */
	public void setnBill(Integer nBill) {
		this.nBill = nBill;
	}

	@Override
	public int compareTo(GprsPackage o) {
		if (getType() != o.getType()) {
			return getType() > o.getType() ? 1 : -1;
		}
		if (getAmount() != o.getAmount()) {
			return getAmount() > o.getAmount() ? 1 : -1;
		}

		return 0;
	}
}
