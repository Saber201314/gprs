package com.shlr.gprs.vo;

/**
 * @author xucong
 * @version 创建时间：2017年4月2日 下午6:43:00
 * 
 */
public class ResultBaseDO<T> {
	private boolean success = true;
	private T module;
	private String error;
	private String orderId;

	public ResultBaseDO() {
	}

	public ResultBaseDO(T module) {
		this.module = module;
	}

	public T getModule() {
		return this.module;
	}

	public void setModule(T module) {
		this.module = module;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void addError(String errorCode) {
		this.error = errorCode;
		this.success = false;
	}

	public String getError() {
		return this.error;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
