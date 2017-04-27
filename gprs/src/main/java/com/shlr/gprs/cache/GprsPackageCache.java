package com.shlr.gprs.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.listenner.WebApplicationContextManager;
import com.shlr.gprs.services.GprsPackageService;

/**
* @author xucong
* @version 创建时间：2017年4月27日 下午9:27:37
* 
*/
public class GprsPackageCache {
	public static Map<Integer, GprsPackage> idMap = new HashMap<Integer, GprsPackage>();

	  public static void load() {
	    GprsPackageService gprsPackageService=(GprsPackageService)WebApplicationContextManager.getApplicationContext().getBean(GprsPackageService.class);
	    List<GprsPackage> list = gprsPackageService.listAll();
	    for (GprsPackage gprsPackage : list)
	      idMap.put(gprsPackage.getId(), gprsPackage);
	  }
}
