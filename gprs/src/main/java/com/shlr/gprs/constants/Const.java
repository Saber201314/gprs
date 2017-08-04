package com.shlr.gprs.constants;


/**
* @author xucong
* @version 创建时间：2017年4月4日 下午3:26:05
* 
*/
public class Const {
	public static int init=1;
	
	private static volatile boolean apiSwitch=true;

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
	public final static String[] province = new String[]{"北京","天津","河北","山西","辽宁",
						"吉林","上海","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东",
						"广西","海南","重庆","四川","贵州","云南","西藏","陕西","甘肃","宁夏","青海","新疆",
						"内蒙古","黑龙江"};
	
}
