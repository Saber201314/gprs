package com.shlr.gprs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Administrator
 */
@Table(name="g_channel_template_code")
public class ChannelTemplateCode implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 8930558167277716169L;
	
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	@Column(name="template_id")
	private Integer templateId;
	@Column(name="type")
	private Integer type;
	@Column(name="location_type")
	private Integer locationType;
	@Column(name="location")
	private String location;
	@Column(name="amount")
	private Integer amount;
	@Column(name="code")
	private String code;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
