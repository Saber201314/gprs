package com.shlr.gprs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.shlr.gprs.cache.ChannelCache;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午7:59:24
* 
*/
@Table(name="g_channel_resource")
public class ChannelResource implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -2249033698491485534L;
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	@Column
	private String merchant;
	@Column(name="location_type")
	private Integer locationType;
	@Column
	private String district;
	@Column
	private String standard;
	@Column(name="channel_id")
	private Integer channelId;
	@Column(name="in_discount")
	private String inDiscount;
	@Column
	private Integer status;
	@Column(name="pay_bill")
	private Integer payBill;
	@Column
	private Integer policy;
	@Column
	private String message;
	@Transient
	private String channelName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Integer getLocationType() {
		return locationType;
	}
	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		Channel channel = (Channel) ChannelCache.idMap.get(Integer
				.valueOf(this.channelId));
		if (channel == null) {
			return null;
		}

		return channel.getName();
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getInDiscount() {
		return inDiscount;
	}
	public void setInDiscount(String inDiscount) {
		this.inDiscount = inDiscount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayBill() {
		return payBill;
	}
	public void setPayBill(Integer payBill) {
		this.payBill = payBill;
	}
	public Integer getPolicy() {
		return policy;
	}
	public void setPolicy(Integer policy) {
		this.policy = policy;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
