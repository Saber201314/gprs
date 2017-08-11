package com.shlr.gprs.vo;

/*
 * 
 */
public class BalanceResponsVO {
	private Boolean success = true;
	private String msg;
	private Double balance = 0D;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
