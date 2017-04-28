package com.shlr.gprs.vo;

import com.shlr.gprs.domain.PayLog;

/**
 * @author Administrator
 */

public class BackMoneyVO {
	private PayLog payLog;

	public BackMoneyVO(PayLog payLog) {
		this.payLog = payLog;
	}

	public PayLog getPayLog() {
		return this.payLog;
	}

	public void setPayLog(PayLog payLog) {
		this.payLog = payLog;
	}
}
