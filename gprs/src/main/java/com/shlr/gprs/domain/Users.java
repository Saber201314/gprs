package com.shlr.gprs.domain;

import com.shlr.gprs.cache.SuiteOrderCache;
import com.shlr.gprs.manager.PayManager;
import com.suwoit.json.util.StringUtils;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

@Table(name="g_users")
public class Users implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;//编号
	@Column
	private String username;//账号
	@Column
	private String password;//密码
	@Column
	private Integer type;//类型
	@Column
	private String name;//名称
	@Column
	private String phone;//电话
	@Column(name="paper_id")
	private Integer paperId;//报价单ID
	@Column
	private Double money;
	@Column(name="pre_money")
	private Double preMoney;
	@Column(name="validate_time")
	private Date validateTime;//验证时间
	@Column
	private String agent;//代理商
	@Column
	private String limits;
	@Column
	private Integer status;
	@Column
	private String memo;
	@Column
	private String feature;
	@Column(name="option_time")
	private Date optionTime;
	@Column(name="site_name")
	private String siteName;
	@Column(name="site_logo")
	private String siteLogo;
	@Column(name="online_pay")
	private Integer onlinePay;
	@Column
	private String gonggao;
	@Column
	private Integer sms;
	@Column(name="sms_username")
	private String smsUsername;
	@Column(name="sms_password")
	private String smsPassword;
	@Column(name="sms_sign")
	private String smsSign;
	@Column(name="sms_interface")
	private Integer smsInterface;
	@Column
	private Integer compatible;
	@Column(name="api_key")
	private String apiKey;
	@Column(name="api_password")
	private String apiPassword;
	
	@Transient
	private Integer onlinePayPaper;
	@Transient
	private String alipaySellerEmail;
	@Transient
	private String alipayPartner;
	@Transient
	private String alipayKey;
	@Transient
	private String alipayPrivateKey;
	@Transient
	private String alipayAliPublicKey;
	@Transient
	private String whiteIp;
	@Transient
	private List<Integer> limitList;
	@Transient
	private Map<String, String> featureMap = new HashMap<String, String>();
	@Transient
	private String paperName;
	@Transient
	private Integer lastCardId;
	@Transient
	private List<Users> userList;
	@Transient
	private String backUrl;
	@Transient
	private Integer refundType;
	@Transient
	private String refundMobile;
	@Transient
	private Double refundFee;
	@Transient
	private Integer orderId;
	@Transient
	private Logger logger = Logger.getLogger(Users.class);

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public double getMoney() {
		return PayManager.getInstance().getUserMoney(this.id);
	}

	public String getMoneyString() {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		return decimalFormat.format(PayManager.getInstance().getUserMoney(this.id));
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getPreMoney() {
		return this.preMoney;
	}

	public Double getInitMoney() {
		return this.money;
	}

	public void setPreMoney(Double preMoney) {
		this.preMoney = preMoney;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getLimits() {
		return this.limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;

		this.limitList = new ArrayList();
		if (!StringUtils.isEmpty(limits)) {
			String[] strings = limits.split(",");
			for (String temp : strings)
				this.limitList.add(Integer.valueOf(temp));
		}
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getOptionTime() {
		return this.optionTime;
	}

	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}

	public Date getValidateTime() {
		return this.validateTime;
	}

	public void setValidateTime(Date validateTime) {
		this.validateTime = validateTime;
	}

	public String getPaperName() {
		return this.paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public List<Users> getUserList() {
		return this.userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteLogo() {
		return this.siteLogo;
	}

	public void setSiteLogo(String siteLogo) {
		this.siteLogo = siteLogo;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Users)) {
			return false;
		}
		Users users = (Users) obj;
		return getId() == users.getId();
	}

	public String getFeature() {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			Iterator iterator = this.featureMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				if (StringUtils.isEmpty((String) entry.getValue()))
					continue;
				stringBuffer
						.append((String) entry.getKey())
						.append(":")
						.append(URLEncoder.encode((String) entry.getValue(),
								"utf-8")).append(",");
			}
		} catch (Exception e) {
			this.logger.error("", e);
		}
		this.feature =  "".equals( stringBuffer.toString())?null:stringBuffer.toString();
		return this.feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
		if (!StringUtils.isEmpty(feature))
			try {
				String[] strings = feature.split(",");
				for (String string : strings) {
					String[] temp = string.split(":", -1);
					if (!StringUtils.isEmpty(temp[1]))
						this.featureMap.put(temp[0],
								URLDecoder.decode(temp[1], "utf-8"));
				}
			} catch (Exception e) {
				this.logger.error("", e);
			}
	}

	public Integer getLastCardId() {
		String id = (String) this.featureMap.get("last_card_id");
		if (StringUtils.isEmpty(id)) {
			return 0;
		}
		return Integer.valueOf(id).intValue();
	}

	public Integer getOnlinePayPaper() {
		String id = (String) this.featureMap.get("online_pay_paper");
		if (StringUtils.isEmpty(id)) {
			return 0;
		}
		return Integer.valueOf(id).intValue();
	}

	public void setOnlinePayPaper(Integer onlinePayPaper) {
		this.featureMap.put("online_pay_paper", String.valueOf(onlinePayPaper));
	}

	public String getAlipaySellerEmail() {
		return (String) this.featureMap.get("alipay_seller_email");
	}

	public void setAlipaySellerEmail(String alipaySellerEmail) {
		this.featureMap.put("alipay_seller_email", alipaySellerEmail);
	}

	public String getAlipayPartner() {
		return (String) this.featureMap.get("alipay_partener");
	}

	public void setAlipayPartner(String alipayPartner) {
		this.featureMap.put("alipay_partener", alipayPartner);
	}

	public String getAlipayKey() {
		return (String) this.featureMap.get("alipay_key");
	}

	public void setAlipayKey(String alipayKey) {
		this.featureMap.put("alipay_key", alipayKey);
	}

	public String getAlipayPrivateKey() {
		return (String) this.featureMap.get("alipay_private_key");
	}

	public void setAlipayPrivateKey(String alipayPrivateKey) {
		this.featureMap.put("alipay_private_key", alipayPrivateKey);
	}

	public String getAlipayAliPublicKey() {
		return (String) this.featureMap.get("alipay_public_key");
	}

	public void setAlipayAliPublicKey(String alipayAliPublicKey) {
		this.featureMap.put("alipay_public_key", alipayAliPublicKey);
	}

	public void setLastCardId(Integer lastCardId) {
		this.featureMap.put("last_card_id", String.valueOf(lastCardId));
	}

	public String getWhiteIp() {
		return (String) this.featureMap.get("white_ip");
	}

	public void setWhiteIp(String whiteIp) {
		this.featureMap.put("white_ip", whiteIp);
	}

	public Integer getSms() {
		return this.sms;
	}

	public void setSms(Integer sms) {
		this.sms = sms;
	}

	public String getSmsUsername() {
		return this.smsUsername;
	}

	public void setSmsUsername(String smsUsername) {
		this.smsUsername = smsUsername;
	}

	public String getSmsPassword() {
		return this.smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsSign() {
		return this.smsSign;
	}

	public void setSmsSign(String smsSign) {
		this.smsSign = smsSign;
	}

	public Integer getOnlinePay() {
		return this.onlinePay;
	}

	public void setOnlinePay(Integer onlinePay) {
		this.onlinePay = onlinePay;
	}

	public String getGonggao() {
		return this.gonggao;
	}

	public void setGonggao(String gonggao) {
		this.gonggao = gonggao;
	}

	public boolean containsLimit(Integer limit) {
		return this.limitList.contains(Integer.valueOf(limit));
	}

	public Integer getSmsIntegererface() {
		return this.smsInterface;
	}

	public void setSmsIntegererface(Integer smsInterface) {
		this.smsInterface = smsInterface;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public Integer getSuiteSize() {
		List orderList = (List) SuiteOrderCache.getInstance().orderMapByAccount
				.get(this.username);
		if (orderList == null) {
			return 0;
		}

		return orderList.size();
	}

	public static abstract interface FEATURE_MAP_KEY {
		public static final String LAST_CARD_ID = "last_card_id";
		public static final String ONLINE_PAY_PAPER = "online_pay_paper";
		public static final String ALIPAY_SELLER_EMAIL = "alipay_seller_email";
		public static final String ALIPAY_PARTENER = "alipay_partener";
		public static final String ALIPAY_KEY = "alipay_key";
		public static final String ALIPAY_PRIVATE_KEY = "alipay_private_key";
		public static final String ALIPAY_PUBLIC_KEY = "alipay_public_key";
		public static final String WHITE_IP = "white_ip";
	}

	public Integer getCompatible() {
		return compatible;
	}

	public void setCompatible(Integer compatible) {
		this.compatible = compatible;
	}
	
	public String getApiPassword() {
		return apiPassword;
	}

	public void setApiPassword(String apiPassword) {
		this.apiPassword = apiPassword;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getRefundMobile() {
		return refundMobile;
	}

	public void setRefundMobile(String refundMobile) {
		this.refundMobile = refundMobile;
	}

	public Double getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "UserName = "+this.username;
	}
}