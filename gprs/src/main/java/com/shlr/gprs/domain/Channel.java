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
 * @version 创建时间：2017年4月4日 下午8:01:28
 * 
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

	@Column(name = "user_id")
	private Integer userId;
	@Column
	private String name;
	@Column
	private String alias;
	@Column(name = "month_limit")
	private Integer monthLimit;
	@Column
	private String memo;
	@Column(name = "option_time")
	private Date optionTime;
	@Column
	private String template;
	@Column
	private String packages;
	@Column
	private Integer status;
	@Transient
	private String templateName;

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

	public Integer getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
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

	public String getTemplateName() {
//		ChannelTemplate channelTemplate = (ChannelTemplate) ChannelTemplateCache.identityMap.get(this.template);
//		if (channelTemplate == null) {
//			return null;
//		}
//
//		return channelTemplate.getName();
		return this.getTemplateName();
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
