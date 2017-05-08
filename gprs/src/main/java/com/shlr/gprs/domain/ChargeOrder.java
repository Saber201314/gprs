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
	
	@Column(name="location_type")
	private Integer locationType;//流量类型 0 全国 1省内     (我们放给下游是1和2，加了1，根据上游自行减1)
	
	private String location;//地区 
	
	@Column(name="package_id")
	private Integer packageId;// 流量包ID
	
	private Integer amount;//包大小
	
	private Double money;//基础面值
	
	@Column(name="discount_money")
	private Double discountMoney;//折扣后的价格
	
	private Double profit;//利润
	
	@Column(name="pay_bill")
	private Integer payBill;//是否带票  0 带 1 不带
	
	@Column(name="option_time")
	private Date optionTime;//提交时间
	
	@Column(name="take_time")
	private Integer takeTime;//
	
	@Column(name="submit_type")
	private Integer submitType;//提交类型     1 代理上直冲   2充值卡充值  3支付宝充值  4批量充值   5接口充值  
	
	@Column(name="submit_time")
	private Date submitTime;//提交时间
	
	@Column(name="submit_channel")
	private Integer submitChannel;//提交通道
	
	@Column(name="submit_template")
	private Integer submitTemplate;//提交模板
	
	@Column(name="submit_status")
	private Integer submitStatus = 0 ;//提交状态  1 充值中 0 未提交
	
	@Column(name="charge_task_id")
	private String chargeTaskId;// 订单号
	
	@Column(name="charge_status")
	private Integer chargeStatus = 0;//充值状态  1 充值成功 -1 充值失败  0 未充值
	
	
	
	@Column(name="report_time")
	private Long reportTime;//报告时间
	
	@Transient
	private String channelName;//通道名称
	
	@Column
	private String memo;//备注
	
	private String error;//错误信息
	
	@Column(name="batch_id")
	private Integer batchId;//
	
	private Integer report;//是否收到充值回复  
	
	@Column(name="paymoney")
	private Double paymoney;//
	
	private Integer paystatus;//
	
	private Double hongbao;//
	
	@Column(name="thirdInfo")
	private String thirdInfo;//第三方信息
	
	@Column(name="backUrl")
	private String backUrl;//回调地址
	
	@Column(name="order_id")
	private String agentorderId;//下游代理商地单号
	
	@Column(name="cache_flag")
	private Integer cacheFlag;//缓存标志  1 缓存中 0 正常数据
	
	@Column(name="order_req_no")
	private String orderReqNo;//上游订单号  
	
	@Column(name="order_req_no1")//上游订单号1  未使用
	private String orderReqNo1;
	
	@Column(name="req_status")
	private Integer reqStatus;//主动查询请求类别
	
	@Column(name="refund_flag")
	private Integer refundFlag;//退款标记  0 未退款 1 已退款
	
	@Transient
	private Double discount;//接入折扣
	
	@Transient
	private Integer ignoreCacheCondition;//忽略缓存条件  1 缓存提交  0  正常提交
	
	@Transient
	private Double outDiscount;//放出折扣
	
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
	private int ignoreRouteCondition;//忽略路由条件

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

	/**
	 * @return 流量类型
	 */
	public Integer getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
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
	 * @return the submitStatus
	 */
	public Integer getSubmitStatus() {
		return submitStatus;
	}

	/**
	 * @param submitStatus the submitStatus to set
	 */
	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
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

	/**
	 * @return the reportTime
	 */
	public Long getReportTime() {
		return reportTime;
	}

	/**
	 * @param reportTime the reportTime to set
	 */
	public void setReportTime(Long reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		Channel channel = (Channel) ChannelCache.idMap.get(Integer
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

	/**
	 * @return the report
	 */
	public Integer getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(Integer report) {
		this.report = report;
	}

	/**
	 * @return the paymoney
	 */
	public Double getPaymoney() {
		return paymoney;
	}

	/**
	 * @param paymoney the paymoney to set
	 */
	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
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

	/**
	 * @return the agentorderId
	 */
	public String getAgentorderId() {
		return agentorderId;
	}

	/**
	 * @param agentorderId the agentorderId to set
	 */
	public void setAgentorderId(String agentorderId) {
		this.agentorderId = agentorderId;
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

	/**
	 * @return the orderReqNo
	 */
	public String getOrderReqNo() {
		return orderReqNo;
	}

	/**
	 * @param orderReqNo the orderReqNo to set
	 */
	public void setOrderReqNo(String orderReqNo) {
		this.orderReqNo = orderReqNo;
	}

	/**
	 * @return the orderReqNo1
	 */
	public String getOrderReqNo1() {
		return orderReqNo1;
	}

	/**
	 * @param orderReqNo1 the orderReqNo1 to set
	 */
	public void setOrderReqNo1(String orderReqNo1) {
		this.orderReqNo1 = orderReqNo1;
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

	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return the ignoreCacheCondition
	 */
	public Integer getIgnoreCacheCondition() {
		return ignoreCacheCondition;
	}

	/**
	 * @param ignoreCacheCondition the ignoreCacheCondition to set
	 */
	public void setIgnoreCacheCondition(Integer ignoreCacheCondition) {
		this.ignoreCacheCondition = ignoreCacheCondition;
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

	/**
	 * @return the ignoreRouteCondition
	 */
	public int getIgnoreRouteCondition() {
		return ignoreRouteCondition;
	}

	/**
	 * @param ignoreRouteCondition the ignoreRouteCondition to set
	 */
	public void setIgnoreRouteCondition(int ignoreRouteCondition) {
		this.ignoreRouteCondition = ignoreRouteCondition;
	}
	
	
	
	
	
	
}
