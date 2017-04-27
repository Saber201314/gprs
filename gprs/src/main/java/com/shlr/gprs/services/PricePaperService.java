package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.PricePaperMapper;
import com.shlr.gprs.domain.PricePaper;

/**
 * @author Administrator
 */
@Service
public class PricePaperService implements DruidStatInterceptor{
	
	@Resource
	PricePaperMapper pricePaperMapper;
	
	public PricePaper selectOneByPK(Object pk){
		return pricePaperMapper.selectByPrimaryKey(pk);
	}
	public List<PricePaper> listAll(){
		return pricePaperMapper.selectAll();
		
	}
}
