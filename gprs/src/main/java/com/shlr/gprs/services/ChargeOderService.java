package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.ChargeOrderMapper;
import com.shlr.gprs.domain.ChargeOrder;

import tk.mybatis.mapper.entity.Example;

/**
* @author xucong
* @version 创建时间：2017年4月9日 下午10:39:48
* 
*/
@Service
public class ChargeOderService implements DruidStatInterceptor{
	
	@Resource
	ChargeOrderMapper chargeOrderMapper;
	
	public List<ChargeOrder> listByExampleAndPage(Example example,Integer pageNo){
		return chargeOrderMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
		
	}

}