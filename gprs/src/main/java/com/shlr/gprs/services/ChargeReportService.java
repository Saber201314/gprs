package com.shlr.gprs.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shlr.gprs.dao.ChargeReportMapper;
import com.shlr.gprs.domain.ChargeReport;
import com.xiaoleilu.hutool.date.DateUtil;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午2:28:27
* 
*/
@Service
public class ChargeReportService implements DruidStatInterceptor{
	
	@Resource
	ChargeReportMapper chargeReportMapper;
	
	/**
	 * 查询当天充值报告
	 * @return
	 */
	public List<ChargeReport> queryCurDayList(){
		Date date = new Date();
		String start = DateUtil.format(date, "yyyy-MM-dd 00:00:00");
		String end = DateUtil.format(date, "yyyy-MM-dd 23:59:59");
		List<ChargeReport> queryCurDayList = chargeReportMapper.queryCurDayList(start,end);
		
		if (!CollectionUtils.isEmpty(queryCurDayList)) {
			Double resumePrice=0.00D,remainPrice = 0.00D;
			for (ChargeReport chargeReport : queryCurDayList) {
				Double temp =  0D - Double.valueOf(chargeReport.getResumePrice());
				chargeReport.setResumePrice(temp.toString());
				resumePrice+= temp;
				remainPrice+= 0D - Double.valueOf(chargeReport.getRemainPrice());
			}
			ChargeReport chargeReport=new ChargeReport();
			chargeReport.setAccount("合计");
			chargeReport.setName("total");
			chargeReport.setResumePrice(String.valueOf(resumePrice));
			chargeReport.setRemainPrice(String.valueOf(remainPrice));
			queryCurDayList.add(chargeReport);
		}
		Collections.sort(queryCurDayList, new Comparator<ChargeReport>() {
			@Override
			public int compare(ChargeReport o1, ChargeReport o2) {
				// TODO Auto-generated method stub
				return Double.valueOf(o2.getResumePrice()).compareTo(Double.valueOf(o1.getResumePrice()));
			}
		});
		return queryCurDayList;
		
	}
}
