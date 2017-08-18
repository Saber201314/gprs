package com.shlr.gprs.domain;

public class StatisticsChargeOrder {
	private String status;
	private Integer num;
	private Double payMoney;
	private Double disCountMoney;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public Double getDisCountMoney() {
		return disCountMoney;
	}
	public void setDisCountMoney(Double disCountMoney) {
		this.disCountMoney = disCountMoney;
	}
	
}
