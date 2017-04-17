package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.dao.CallBackMapper;
import com.shlr.gprs.domain.Callback;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 */

@Service
public class CallbackService {
	@Resource
	CallBackMapper callBackMapper;
	public List<Callback> listByExampleAndPage(Example example,Integer pageNo){
		return callBackMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
}
