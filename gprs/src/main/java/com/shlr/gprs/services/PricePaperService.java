package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.cache.PricePaperCache;
import com.shlr.gprs.dao.PricePaperMapper;
import com.shlr.gprs.domain.PricePaper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 */
@Service
public class PricePaperService implements DruidStatInterceptor{
	
	@Resource
	PricePaperMapper pricePaperMapper;
	
	public PricePaper findByIdWithCache(Integer id){
		return PricePaperCache.getInstance().idMap.get(id);
	}
	public PricePaper findById(Integer id){
		return pricePaperMapper.selectByPrimaryKey(id);
	}
	public Integer saveOrUpdate(PricePaper pricePaper){
		PricePaper findById = findById(pricePaper.getId());
		int num = 0;
		if(findById != null){
			num = pricePaperMapper.updateByPrimaryKeySelective(pricePaper);
		}else{
			num = pricePaperMapper.insertSelective(pricePaper);
		}
		return num;
	}
	public List<PricePaper> listAll(){
		return pricePaperMapper.selectAll();
	}
	public List<PricePaper> listByExampleAndPage(Example example,Integer pageNo){
		return pricePaperMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1 )*30,30));
	}
}
