package com.shlr.gprs.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.shlr.gprs.cache.ChannelCache;

/**
* @author xucong
* @version 创建时间：2017年4月9日 下午2:53:32
* 
*/
@Table(name="g_charge_order")
public class ChargeOrder {
	
	@Id
	@GeneratedValue(generator="JDBC")
	private Integer id;
	
	private String account;//代理商账号
	
	private String mobile;//手机号
	
	private Integer type;//运营商类型   1 移动 2联通 3电信
	
	private Integer range;//流量类型 0 全国 1省内     (我们放给下游是1和2，加了1，根据上游自行减1)
	
	private String location;//地区 
	
	private Integer packageId;// 流量包ID
	
	private Integer amount;//包大小
	
	private Double money;//基础面值
	
	private Double discountMoney;//折扣后的价格
	
	private Double profit;//利润
	
	private Integer payBill;//是否带票  0 带 1 不带
	
	private Double inDiscount;//接入折扣
	
	private Double outDiscount;//放出折扣
	
	private Double payMoney;//扣费金额
	
	private Date optionTime;//提交时间
	
	private Integer takeTime;//
	
	private Integer submitType;//提交类型     1 代理上直冲   2充值卡充值  3支付宝充值  4批量充值   5接口充值  
	
	private Date submitTime;//提交时间
	
	private Integer submitChannel;//提交通道
	
	private Integer submitTemplate;//提交模板
	
	private String chargeTaskId;// 订单号
	
	private Integer chargeStatus;//充值状态  1 未知  2 提交成功  3 提交失败  4 充值成功  5 充值失败
	
	private Date reportTime;//报告时间
	
	private String submitContent;//报告内容
	
	private String reportContent;//报告内容
	
	private String memo;//备注
	
	private String error;//错误信息
	
	private Integer batchId;//
	
	private Integer paystatus;//支付状态
	
	private Double hongbao;//
	
	private String thirdInfo;//第三方信息
	
	private String backUrl;//回调地址
	
	private String agentOrderId;//下游代理商地单号
	
	private Integer cacheFlag;//缓存标志  1 缓存中 0 正常数据
	
	private String upOrderId;//上游订单号  
	
	private Integer reqStatus;//主动查询请求类别
	
	@Column(name="refund_flag")
	private Integer refundFlag;//退款标记  0 未退款 1 已退款
	
	@Column(name="route_flag")
	private Integer routeFlag = 0;//是否路由     0  不路由  1路由
	
	@Column(name="route_status")
	private Integer routeStatus = 0;//路由状态，0：不可被搜索，1：当前可以被搜索
	
	@Column(name="route_times")
	private Integer routeTimes;//路由次数
	
	@Column(name="cur_route_pos")
	private Integer curRoutePos = 0 ;//当前路由位置
	
	@Column(name="package_id1")
	private Integer packageId1;//第一个包id
	
	@Column(name="package_id2")
	private Integer packageId2;//第二个包id
	
	@Column(name="package_id3")
	private Integer packageId3;//第三个包id
	
	@Transient
	private String channelName;//通道名称
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return 获取代理商账号
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return 运营商类型
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	
	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the packageId
	 */
	public Integer getPackageId() {
		return packageId;
	}

	/**
	 * @param packageId the packageId to set
	 */
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the money
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * @return the discountMoney
	 */
	public Double getDiscountMoney() {
		return discountMoney;
	}

	/**
	 * @param discountMoney the discountMoney to set
	 */
	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}

	/**
	 * @return the profit
	 */
	public Double getProfit() {
		return profit;
	}

	/**
	 * @param profit the profit to set
	 */
	public void setProfit(Double profit) {
		this.profit = profit;
	}

	/**
	 * @return the payBill
	 */
	public Integer getPayBill() {
		return payBill;
	}

	/**
	 * @param payBill the payBill to set
	 */
	public void setPayBill(Integer payBill) {
		this.payBill = payBill;
	}

	/**
	 * @return the optionTime
	 */
	public Date getOptionTime() {
		return optionTime;
	}

	/**
	 * @param optionTime the optionTime to set
	 */
	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}

	/**
	 * @return the takeTime
	 */
	public Integer getTakeTime() {
		return takeTime;
	}

	/**
	 * @param takeTime the takeTime to set
	 */
	public void setTakeTime(Integer takeTime) {
		this.takeTime = takeTime;
	}

	/**
	 * @return the submitType
	 */
	public Integer getSubmitType() {
		return submitType;
	}

	/**
	 * @param submitType the submitType to set
	 */
	public void setSubmitType(Integer submitType) {
		this.submitType = submitType;
	}

	/**
	 * @return the submitTime
	 */
	public Date getSubmitTime() {
		return submitTime;
	}

	/**
	 * @param submitTime the submitTime to set
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * @return the submitChannel
	 */
	public Integer getSubmitChannel() {
		return submitChannel;
	}

	/**
	 * @param submitChannel the submitChannel to set
	 */
	public void setSubmitChannel(Integer submitChannel) {
		this.submitChannel = submitChannel;
	}

	/**
	 * @return the submitTemplate
	 */
	public Integer getSubmitTemplate() {
		return submitTemplate;
	}

	/**
	 * @param submitTemplate the submitTemplate to set
	 */
	public void setSubmitTemplate(Integer submitTemplate) {
		this.submitTemplate = submitTemplate;
	}


	/**
	 * @return the chargeTaskId
	 */
	public String getChargeTaskId() {
		return chargeTaskId;
	}

	/**
	 * @param chargeTaskId the chargeTaskId to set
	 */
	public void setChargeTaskId(String chargeTaskId) {
		this.chargeTaskId = chargeTaskId;
	}

	/**
	 * @return the chargeStatus
	 */
	public Integer getChargeStatus() {
		return chargeStatus;
	}

	/**
	 * @param chargeStatus the chargeStatus to set
	 */
	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}


	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		Channel channel = (Channel) ChannelCache.getInstance().idMap.get(Integer
				.valueOf(this.submitChannel));
		if (channel == null) {
			return null;
		}

		return channel.getName();
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the batchId
	 */
	public Integer getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public String getSubmitContent() {
		return submitContent;
	}

	public void setSubmitContent(String submitContent) {
		this.submitContent = submitContent;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	/**
	 * @return the paystatus
	 */
	public Integer getPaystatus() {
		return paystatus;
	}

	/**
	 * @param paystatus the paystatus to set
	 */
	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}

	/**
	 * @return the hongbao
	 */
	public Double getHongbao() {
		return hongbao;
	}

	/**
	 * @param hongbao the hongbao to set
	 */
	public void setHongbao(Double hongbao) {
		this.hongbao = hongbao;
	}

	/**
	 * @return the thirdInfo
	 */
	public String getThirdInfo() {
		return thirdInfo;
	}

	/**
	 * @param thirdInfo the thirdInfo to set
	 */
	public void setThirdInfo(String thirdInfo) {
		this.thirdInfo = thirdInfo;
	}

	/**
	 * @return the backUrl
	 */
	public String getBackUrl() {
		return backUrl;
	}

	/**
	 * @param backUrl the backUrl to set
	 */
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	
	public String getAgentOrderId() {
		return agentOrderId;
	}

	public void setAgentOrderId(String agentOrderId) {
		this.agentOrderId = agentOrderId;
	}

	/**
	 * @return the cacheFlag
	 */
	public Integer getCacheFlag() {
		return cacheFlag;
	}

	/**
	 * @param cacheFlag the cacheFlag to set
	 */
	public void setCacheFlag(Integer cacheFlag) {
		this.cacheFlag = cacheFlag;
	}


	public String getUpOrderId() {
		return upOrderId;
	}

	public void setUpOrderId(String upOrderId) {
		this.upOrderId = upOrderId;
	}

	/**
	 * @return the reqStatus
	 */
	public Integer getReqStatus() {
		return reqStatus;
	}

	/**
	 * @param reqStatus the reqStatus to set
	 */
	public void setReqStatus(Integer reqStatus) {
		this.reqStatus = reqStatus;
	}

	/**
	 * @return the refundFlag
	 */
	public Integer getRefundFlag() {
		return refundFlag;
	}

	/**
	 * @param refundFlag the refundFlag to set
	 */
	public void setRefundFlag(Integer refundFlag) {
		this.refundFlag = refundFlag;
	}



	public Double getInDiscount() {
		return inDiscount;
	}

	public void setInDiscount(Double inDiscount) {
		this.inDiscount = inDiscount;
	}

	/**
	 * @return the outDiscount
	 */
	public Double getOutDiscount() {
		return outDiscount;
	}

	/**
	 * @param outDiscount the outDiscount to set
	 */
	public void setOutDiscount(Double outDiscount) {
		this.outDiscount = outDiscount;
	}

	/**
	 * @return the routeFlag
	 */
	public Integer getRouteFlag() {
		return routeFlag;
	}

	/**
	 * @param routeFlag the routeFlag to set
	 */
	public void setRouteFlag(Integer routeFlag) {
		this.routeFlag = routeFlag;
	}

	/**
	 * @return the routeStatus
	 */
	public Integer getRouteStatus() {
		return routeStatus;
	}

	/**
	 * @param routeStatus the routeStatus to set
	 */
	public void setRouteStatus(Integer routeStatus) {
		this.routeStatus = routeStatus;
	}

	/**
	 * @return the routeTimes
	 */
	public Integer getRouteTimes() {
		return routeTimes;
	}

	/**
	 * @param routeTimes the routeTimes to set
	 */
	public void setRouteTimes(Integer routeTimes) {
		this.routeTimes = routeTimes;
	}

	/**
	 * @return the curRoutePos
	 */
	public Integer getCurRoutePos() {
		return curRoutePos;
	}

	/**
	 * @param curRoutePos the curRoutePos to set
	 */
	public void setCurRoutePos(Integer curRoutePos) {
		this.curRoutePos = curRoutePos;
	}

	/**
	 * @return the packageId1
	 */
	public Integer getPackageId1() {
		return packageId1;
	}

	/**
	 * @param packageId1 the packageId1 to set
	 */
	public void setPackageId1(Integer packageId1) {
		this.packageId1 = packageId1;
	}

	/**
	 * @return the packageId2
	 */
	public Integer getPackageId2() {
		return packageId2;
	}

	/**
	 * @param packageId2 the packageId2 to set
	 */
	public void setPackageId2(Integer packageId2) {
		this.packageId2 = packageId2;
	}

	/**
	 * @return the packageId3
	 */
	public Integer getPackageId3() {
		return packageId3;
	}

	/**
	 * @param packageId3 the packageId3 to set
	 */
	public void setPackageId3(Integer packageId3) {
		this.packageId3 = packageId3;
	}
}
