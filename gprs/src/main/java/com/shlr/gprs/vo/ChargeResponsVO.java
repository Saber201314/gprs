package com.shlr.gprs.vo;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xucong
 * @version 创建时间：2017年4月2日 下午6:43:00
 * 
 */
public class ChargeResponsVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7603954760433953462L;
	private Boolean success = true;
	private String msg;
	private String taskId;
	private String orderId;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean isSuccess(){
		return this.success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	
}
