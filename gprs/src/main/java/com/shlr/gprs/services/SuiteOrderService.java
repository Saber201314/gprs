package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shlr.gprs.dao.SuiteOrderMapper;
import com.shlr.gprs.domain.SuiteOrder;

/**
 * @author Administrator
 */
@Service
public class SuiteOrderService implements DruidStatInterceptor{
	@Resource
	SuiteOrderMapper suiteOrderMapper;
	
	public List<SuiteOrder> list(){
		return suiteOrderMapper.selectAll();
	}
}
