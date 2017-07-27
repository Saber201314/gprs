package com.shlr.gprs.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.dao.ChannelMapper;
import com.shlr.gprs.dao.ChargeOrderMapper;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.ChargeOrder;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午8:06:20
* 
*/
@Service
public class ChannelService implements DruidStatInterceptor{
	@Resource
	ChannelMapper channelMapper;
	@Resource
	ChargeOrderMapper chargeOrderMapper;
	
	/**
	 * 查询所有通道
	 * @return
	 */
	public List<Channel> list(){
		return channelMapper.selectAll();
	}
	public List<Channel> listByExampleAndPage(Example example,Integer pageNo){
		return channelMapper.selectByExampleAndRowBounds(example, new PageRowBounds((pageNo-1)*30, 30));
	}
	
	public Channel findById(Integer id){
		return channelMapper.selectByPrimaryKey(id);
	}
	public Integer update(Channel channel){
		return channelMapper.updateByPrimaryKeySelective(channel);
		
	}
	public Map<Integer, Integer> qaueryMonthAmount(){
		Map<Integer, Integer> monthMap = new HashMap<Integer, Integer>();

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DATE, 1);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    
	    List<ChargeOrder> qaueryMonthAmount = chargeOrderMapper.qaueryMonthAmount(calendar.getTime());
	    
	    for (ChargeOrder item : qaueryMonthAmount) {
	      monthMap.put(item.getSubmitChannel(), Integer.valueOf(item.getAmount()));
	    }

	    return monthMap;
	}
}
