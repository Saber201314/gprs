package com.shlr.gprs.manager;

/**
 * @author Administrator
 * 充值管理
 */

public class ChargeManager {
	
	
	private static class ChargeManagerHolder{
		static ChargeManager chargeManager = new ChargeManager();
	}
	
	public static ChargeManager getInstance() {
		return ChargeManagerHolder.chargeManager;
	}

}
