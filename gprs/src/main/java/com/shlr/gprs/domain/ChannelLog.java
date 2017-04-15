package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Administrator
 */

@Table(name="g_channel_log")
public class ChannelLog implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 262135087263549837L;
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	private Integer orderId;
	private Integer templateId;
	private String templateName;
	private String mobile;
	private String response;
	private Integer result;
	private Date optionTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Date getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}
	
	
}
