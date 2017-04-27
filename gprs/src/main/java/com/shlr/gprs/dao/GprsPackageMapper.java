package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.GprsPackage;

import tk.mybatis.mapper.common.Mapper;

/**
* @author xucong
* @version 创建时间：2017年4月27日 下午9:30:35
* 
*/
@MapperScan
public interface GprsPackageMapper extends Mapper<GprsPackage>{

}
