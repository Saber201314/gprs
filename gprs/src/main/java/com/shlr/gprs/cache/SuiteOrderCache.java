package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shlr.gprs.domain.SuiteOrder;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.SuiteOrderService;

/**
 * @author Administrator
 */

public class SuiteOrderCache {
	public Map<Integer, SuiteOrder> idMap = new HashMap<Integer, SuiteOrder>();
	public Map<String, List<SuiteOrder>> orderMapByAccount = new HashMap<String, List<SuiteOrder>>();

	public static SuiteOrderCache getInstance(){
		return SuiteOrderCacheHolder.suiteOrderCache;
	}
	static class SuiteOrderCacheHolder{
		static SuiteOrderCache suiteOrderCache=new SuiteOrderCache();
	}
	
	
	public void load() {
		SuiteOrderService suiteOrderService = WebApplicationContextManager.getApplicationContext().getBean(SuiteOrderService.class);

		List<SuiteOrder> orderList = suiteOrderService.list();
		for (SuiteOrder order : orderList) {
			idMap.put(Integer.valueOf(order.getId()), order);

			List list = (List) orderMapByAccount.get(order.getAccount());
			if (list == null) {
				list = new CopyOnWriteArrayList();
				orderMapByAccount.put(order.getAccount(), list);
			}
			list.add(order);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SuiteOrder> getUserOrderList(String account) {
		List orderList = (List) orderMapByAccount.get(account);
		if (orderList == null) {
			orderList = new LinkedList();
		}
		return orderList;
	}
}
