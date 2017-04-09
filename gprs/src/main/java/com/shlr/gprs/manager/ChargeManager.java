package com.shlr.gprs.manager;

/**
 * @author Administrator
 * 充值管理
 */

public class ChargeManager {
	
	
	
	public static Boolean moreOperValidMap = false;
	
	private static class ChargeManagerHolder{
		static ChargeManager chargeManager = new ChargeManager();
	}
	
	public static ChargeManager getInstance() {
		return ChargeManagerHolder.chargeManager;
	}

}
