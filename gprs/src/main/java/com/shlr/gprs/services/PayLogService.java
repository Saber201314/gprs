package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.PayLogMapper;
import com.shlr.gprs.domain.PayLog;

import tk.mybatis.mapper.entity.Example;

/**
* @author xucong
* @version 创建时间：2017年4月12日 下午9:55:20
* 
*/
@Service
public class PayLogService implements DruidStatInterceptor{
	
	@Resource
	PayLogMapper payLogMapper;
	
	public List<PayLog> listByExampleAndPage(Example example,Integer pageNo){
		return payLogMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
		
	}
}
