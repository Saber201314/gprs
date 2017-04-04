package com.shlr.gprs.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.shlr.gprs.dao.ChargeReportMapper;
import com.shlr.gprs.domain.ChargeReport;

/**
* @author xucong
* @version 创建时间：2017年4月4日 下午2:28:27
* 
*/
@Service
public class ChargeReportService {
	
	@Resource
	ChargeReportMapper chargeReportMapper;
	
	public List<ChargeReport> queryCurDayList(){
		List<ChargeReport> queryCurDayList = chargeReportMapper.queryCurDayList();
		
		if (!CollectionUtils.isEmpty(queryCurDayList)) {
			Float resumePrice=0.00F,remainPrice = 0.00F;
			for (ChargeReport chargeReport : queryCurDayList) {
				resumePrice+=Float.valueOf(chargeReport.getResumePrice());
				remainPrice+=Float.valueOf(chargeReport.getRemainPrice());
			}
			ChargeReport chargeReport=new ChargeReport();
			chargeReport.setAccount("合计");
			chargeReport.setResumePrice(String.valueOf(resumePrice));
			chargeReport.setRemainPrice(String.valueOf(remainPrice));
			queryCurDayList.add(chargeReport);
			
		}
		
		return queryCurDayList;
		
	}
}
