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
	private Integer location_type;
	@Column
	private String district;
	@Column
	private String standard;
	@Column(name="channel_id")
	private Integer channel_id;
	@Column(name="in_discount")
	private String in_discount;
	@Column
	private Integer status;
	@Column(name="pay_bill")
	private Integer pay_bill;
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

	public Integer getLocation_type() {
		return location_type;
	}
	public void setLocation_type(Integer location_type) {
		this.location_type = location_type;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannelName() {
		Channel channel = (Channel) ChannelCache.getInstance().idMap.get(this.channel_id);
		if (channel == null) {
			return null;
		}

		return channel.getName();
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getIn_discount() {
		return in_discount;
	}
	public void setIn_discount(String in_discount) {
		this.in_discount = in_discount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPay_bill() {
		return pay_bill;
	}
	public void setPay_bill(Integer pay_bill) {
		this.pay_bill = pay_bill;
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
