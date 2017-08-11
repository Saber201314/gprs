package com.shlr.gprs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Table(name = "g_users")
public class Users implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;// 编号
	@Column
	private String username;// 账号
	@Column
	private String password;// 密码
	@Column
	private Integer type;// 类型
	@Column
	private String name;// 名称
	@Column
	private String phone;// 电话
	@Column(name = "paper_id")
	private Integer paperId;// 报价单ID
	@Column
	private Double money;
	@Column(name = "pre_money")
	private Double preMoney;
	@Column(name = "validate_time")
	private Date validateTime;// 验证时间
	@Column
	private String agent;// 代理商
	@Column
	private String limits;
	@Column
	private Integer status;
	@Column
	private String memo;
	@Column
	private String feature;
	@Column(name = "option_time")
	private Date optionTime;
	@Column(name = "site_name")
	private String siteName;
	@Column(name = "site_logo")
	private String siteLogo;
	@Column(name = "online_pay")
	private Integer onlinePay;
	@Column
	private String gonggao;
	@Column
	private Integer sms;
	@Column(name = "sms_username")
	private String smsUsername;
	@Column(name = "sms_password")
	private String smsPassword;
	@Column(name = "sms_sign")
	private String smsSign;
	@Column(name = "sms_interface")
	private Integer smsInterface;
	@Column
	private String apiKey;
	@Column
	private String whiteIp;

	@Transient
	private PricePaper paper;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getPreMoney() {
		return preMoney;
	}

	public void setPreMoney(Double preMoney) {
		this.preMoney = preMoney;
	}

	public Date getValidateTime() {
		return validateTime;
	}

	public void setValidateTime(Date validateTime) {
		this.validateTime = validateTime;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Date getOptionTime() {
		return optionTime;
	}

	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteLogo() {
		return siteLogo;
	}

	public void setSiteLogo(String siteLogo) {
		this.siteLogo = siteLogo;
	}

	public Integer getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(Integer onlinePay) {
		this.onlinePay = onlinePay;
	}

	public String getGonggao() {
		return gonggao;
	}

	public void setGonggao(String gonggao) {
		this.gonggao = gonggao;
	}

	public Integer getSms() {
		return sms;
	}

	public void setSms(Integer sms) {
		this.sms = sms;
	}

	public String getSmsUsername() {
		return smsUsername;
	}

	public void setSmsUsername(String smsUsername) {
		this.smsUsername = smsUsername;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsSign() {
		return smsSign;
	}

	public void setSmsSign(String smsSign) {
		this.smsSign = smsSign;
	}

	public Integer getSmsInterface() {
		return smsInterface;
	}

	public void setSmsInterface(Integer smsInterface) {
		this.smsInterface = smsInterface;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getWhiteIp() {
		return whiteIp;
	}

	public void setWhiteIp(String whiteIp) {
		this.whiteIp = whiteIp;
	}

	public PricePaper getPaper() {
		return paper;
	}

	public void setPaper(PricePaper paper) {
		this.paper = paper;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.username;
	}
}