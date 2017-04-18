package com.shlr.gprs.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.shlr.gprs.domain.PricePaper;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Administrator
 */
@MapperScan
public interface PricePaperMapper extends Mapper<PricePaper> {

}
