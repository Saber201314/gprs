package com.shlr.gprs.vo;

/**
* @author xucong
* @version 创建时间：2017年4月14日 下午10:21:17
* 
*/
public class PayLogFmt {
	public String typefmt(Integer type){
		if (type==1) {
			return "流量充值";
		}else if (type == 2) {
			return "流量池充值";
		}
		return "";
	}
	public String statusfmt(Integer status){
		if (status == 1) {
			return "充值成功";
		}else if (status == 0) {
			return "充值中";
		}else if (status == -1) {
			return "已退款";
		}
		else if (status == -2) {
			return "充值失败";
		}
		return "";
	}
}
