package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.shlr.gprs.cache.ChannelTemplateCache;

/**
 * @author xucong
 * @version 创建时间：2017年4月4日 下午8:01:28
 * 通道类
 */
@Table(name = "g_channel")
public class Channel implements Serializable {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -8888534731398170087L;
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;
	@JSONField(name="userId")
	@Column(name = "user_id")
	private Integer user_id;
	@Column
	private String name;
	@Column
	private String alias;
	@JSONField(name="monthLimit")
	@Column(name = "month_limit")
	private Integer month_limit;
	@Column
	private String memo;
	@JSONField(name="optionTime")
	@Column(name = "option_time")
	private Date option_time;
	@Column
	private String template;
	@Column
	private String packages;
	@Column
	private Integer status;
	@Transient
	private Double inDiscount;
	@Transient
	private String templateName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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

	public Integer getMonth_limit() {
		return month_limit;
	}

	public void setMonth_limit(Integer month_limit) {
		this.month_limit = month_limit;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getOption_time() {
		return option_time;
	}

	public void setOption_time(Date option_time) {
		this.option_time = option_time;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Double getInDiscount() {
		return inDiscount;
	}

	public void setInDiscount(Double inDiscount) {
		this.inDiscount = inDiscount;
	}

	public String getTemplateName() {
		ChannelTemplate channelTemplate = (ChannelTemplate) ChannelTemplateCache.identityMap.get(this.template);
		if (channelTemplate == null) {
			return null;
		}
		return channelTemplate.getName();
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
