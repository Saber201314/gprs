package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.cache.PricePaperCache;
import com.shlr.gprs.dao.PricePaperMapper;
import com.shlr.gprs.domain.PricePaper;

/**
 * @author Administrator
 */
@Service
public class PricePaperService implements DruidStatInterceptor{
	
	@Resource
	PricePaperMapper pricePaperMapper;
	
	public PricePaper findById(Integer id){
		return PricePaperCache.idMap.get(id);
	}
	public List<PricePaper> listAll(){
		return pricePaperMapper.selectAll();
		
	}
}
