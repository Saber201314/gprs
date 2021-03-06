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
 * @version 创建时间：2017年4月4日 下午8:03:22
 * 
 */
@Table(name="g_channel_template")
public class ChannelTemplate implements Serializable{
	
	@Transient
	private static final long serialVersionUID = -491099403357429565L;
	@Id
	@GeneratedValue(generator= "JDBC")
	private Integer id;
	@Column
	private String name;
	@Column
	private String identity;
	@Column
	private Date optionTime;
	@Column
	private String account;
	@Column
	private String password;
	@Column
	private String sign;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public Date getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
