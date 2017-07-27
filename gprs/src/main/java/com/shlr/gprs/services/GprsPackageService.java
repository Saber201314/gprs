package com.shlr.gprs.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.dao.GprsPackageMapper;
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.PricePaper;
import com.shlr.gprs.domain.Users;

import tk.mybatis.mapper.entity.Example;

/**
 * @author xucong
 * @version 创建时间：2017年4月27日 下午9:16:30
 * 
 */
@Service
public class GprsPackageService implements DruidStatInterceptor {
	@Resource
	UserService userService;
	@Resource
	PricePaperService pricePaperService;
	@Resource
	GprsPackageMapper gprsPackageMapper;

	public Integer add(GprsPackage gprsPackage){
		return gprsPackageMapper.insertSelective(gprsPackage);
	}
	public Integer update(GprsPackage gprsPackage){
		return gprsPackageMapper.updateByPrimaryKey(gprsPackage);
	}
	public Integer del(List<Integer> ids){
		return gprsPackageMapper.delPackage(ids);
		
	}
	public List<GprsPackage> getPackageListByPaperId(Integer paperId) {
		
		PricePaper pricePaper = pricePaperService.findById(paperId);
		if (pricePaper == null) {
			return null;
		}
		List<GprsPackage> packageList = new ArrayList<GprsPackage>();
		BigDecimal b3 = new BigDecimal("10");
		String[] items = pricePaper.getItems().split(",");
		for (String item : items) {
			String[] temp = item.split(":");
			GprsPackage gprsPackage = findById(Integer.valueOf(temp[0]));
			if ((gprsPackage != null) && (gprsPackage.getStatus() == 0)) {
				BigDecimal discount = new BigDecimal(temp[1]);

				gprsPackage.setDiscount(discount.doubleValue());

				BigDecimal money = new BigDecimal(gprsPackage.getMoney());

				gprsPackage.setPaymoney(money.multiply(discount).divide(b3).doubleValue());
				packageList.add(gprsPackage);
			}
		}
		Collections.sort(packageList);
		return packageList;
	}
	public List<GprsPackage> listAll(){
		return gprsPackageMapper.selectAll();
	}
	public List<GprsPackage> listByPage(Example example,Integer pageNo,Integer pageSize){
		List<GprsPackage> selectByExampleAndRowBounds = gprsPackageMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*pageSize, pageSize));
		return selectByExampleAndRowBounds;
	}
	
	public GprsPackage findById(Integer id){
		return gprsPackageMapper.selectByPrimaryKey(id);
	}
}
