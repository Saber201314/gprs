package com.shlr.gprs.constants;


/**
* @author xucong
* @version 创建时间：2017年4月4日 下午3:26:05
* 
*/
public class Const {
	public static int init=1;
	
	private static volatile boolean apiSwitch=false;

	public static boolean isApiSwitch() {
		return apiSwitch;
	}

	public synchronized static void setApiSwitch(boolean apiSwitch) {
		Const.apiSwitch = apiSwitch;
	}
	
	
	private static Integer orderid=1;

	public synchronized static Integer getOrderid() {
		return orderid++;
	}

	public  static void setOrderid(Integer orderid) {
		Const.orderid = orderid;
	}
	
}
