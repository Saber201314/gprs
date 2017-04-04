package com.shlr.gprs.domain;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午2:42:49
* 
*/
public class ChargeReport {
	private String account;
	private String name;
	private String resumePrice;
	private String remainPrice;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResumePrice() {
		return resumePrice;
	}
	public void setResumePrice(String resumePrice) {
		this.resumePrice = resumePrice;
	}
	public String getRemainPrice() {
		return remainPrice;
	}
	public void setRemainPrice(String remainPrice) {
		this.remainPrice = remainPrice;
	}
	
}
