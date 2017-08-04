package com.shlr.gprs.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 */
@Table(name="g_mobile_area")
public class MobileArea implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8917078540081511447L;

	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	
	private String phone;
	
	private String province;
	
	private String city;
	
	private String serviceProvider;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	
}	
