package com.shlr.gprs.domain;

import com.shlr.gprs.cache.ChannelCache;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午7:59:24
* 
*/
public class ChannelResource {
	private Integer id;
	private String merchant;
	private String district;
	private Integer locationType;
	private String standard;
	private Integer channelId;
	private String channelName;
	private String inDiscount;
	private Integer status;
	private Integer payBill;
	private Integer policy;
	private String message;
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
