package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shlr.gprs.dao.MobileAreaMapper;
import com.shlr.gprs.domain.MobileArea;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */
@Service
public class MobileAreaService {
	@Resource
	MobileAreaMapper mobileAreaMapper;
	
	
	public MobileArea findByMobile(String mobile){
		Example example = new Example(MobileArea.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("phone",mobile);
		List<MobileArea> selectByExample = mobileAreaMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(selectByExample)&& selectByExample.size()== 1){
			return selectByExample.get(0);
		}
		return null;
	}
}
