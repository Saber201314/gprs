package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shlr.gprs.domain.PricePaper;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.PricePaperService;

/**
 * @author xucong
 * @version 创建时间：2017年4月27日 下午9:33:50
 * 
 */
public class PricePaperCache {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Map<Integer, PricePaper> idMap = new HashMap<Integer, PricePaper>();

	public static PricePaperCache getInstance(){
		return PricePaperCacheHolder.pricePaperCache;
	}
	
	static class PricePaperCacheHolder{
		static PricePaperCache pricePaperCache = new PricePaperCache();
	}
	
	public void load() {
		long start = System.currentTimeMillis();
		logger.info("{} initialization started",this.getClass().getSimpleName());
		PricePaperService pricePaperService = WebApplicationContextManager.getApplicationContext().getBean(PricePaperService.class);
		List<PricePaper> list = pricePaperService.listAll();
		for (PricePaper pricePaper : list) {
			Map<Integer, Double> discount = new HashMap<Integer, Double>();
			Map<Integer, Integer> bill = new HashMap<Integer, Integer>();
			String[] items = pricePaper.getItems().split(",");
			for (String item : items)
				try {
					String[] temp = item.split(":");
					discount.put(Integer.valueOf(temp[0]), Double.valueOf(temp[1]));
					if (temp.length == 2) {
						bill.put(Integer.valueOf(temp[0]), 0);
					} else {
						bill.put(Integer.valueOf(temp[0]), Integer.valueOf(temp[2]));
					}
				} catch (Exception localException) {
				}
			pricePaper.setPackageDiscountMap(discount);
			pricePaper.setPackageBillMap(bill);
			idMap.put(pricePaper.getId(), pricePaper);
		}
		long end = System.currentTimeMillis();
		logger.info("{} initialization completed in {} ms ",this.getClass().getSimpleName(),end-start);
	}
	public void updateCache(PricePaper pricePaper){
		Map<Integer, Double> discount = new HashMap<Integer, Double>();
		Map<Integer, Integer> bill = new HashMap<Integer, Integer>();
		String[] items = pricePaper.getItems().split(",");
		for (String item : items)
			try {
				String[] temp = item.split(":");
				discount.put(Integer.valueOf(temp[0]), Double.valueOf(temp[1]));
				if (temp.length == 2) {
					bill.put(Integer.valueOf(temp[0]), 0);
				} else {
					bill.put(Integer.valueOf(temp[0]), Integer.valueOf(temp[2]));
				}
			} catch (Exception e) {
				logger.error("更新报价", e);
			}
		pricePaper.setPackageDiscountMap(discount);
		pricePaper.setPackageBillMap(bill);
		idMap.put(pricePaper.getId(), pricePaper);
	}
}
