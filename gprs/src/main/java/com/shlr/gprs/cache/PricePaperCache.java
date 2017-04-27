package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shlr.gprs.domain.PricePaper;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.PricePaperService;

/**
 * @author xucong
 * @version 创建时间：2017年4月27日 下午9:33:50
 * 
 */
public class PricePaperCache {
	public static Map<Integer, PricePaper> idMap = new HashMap<Integer, PricePaper>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void load() {
		PricePaperService pricePaperService = WebApplicationContextManager.getApplicationContext().getBean(PricePaperService.class);
		List<PricePaper> list = pricePaperService.listAll();
		for (PricePaper pricePaper : list) {
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
			String[] items = pricePaper.getItems().split(",");
			for (String item : items)
				try {
					String[] temp = item.split(":");
					map.put(Integer.valueOf(temp[0]), Double.valueOf(temp[1]));
					if (temp.length == 2) {
						map1.put(Integer.valueOf(temp[0]), 0);
					} else {
						map1.put(Integer.valueOf(temp[0]), Integer.valueOf(temp[2]));
					}
				} catch (Exception localException) {
				}
			pricePaper.setPackageMap(map);
			pricePaper.setPackageBillMap(map1);
			idMap.put(Integer.valueOf(pricePaper.getId()), pricePaper);
		}
	}
}
