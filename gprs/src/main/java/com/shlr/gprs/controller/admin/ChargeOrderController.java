package com.shlr.gprs.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.manager.CallbackManager;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.manager.ThreadManager;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.ExcelUtils;
import com.shlr.gprs.utils.JSONUtils;
import com.shlr.gprs.utils.TimeUtls;
import com.shlr.gprs.vo.ChargeResponsVO;
import com.xiaoleilu.hutool.util.StrUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class ChargeOrderController {
	
	
	@Resource
	UserService userService;
	@Resource
	ChargeOrderService chargeOrderService;
	
	/**
	 * 充值记录
	 * @param request
	 * @param response
	 * @param session
	 * @param pageNo
	 * @param account
	 * @param mobile
	 * @param location
	 * @param from
	 * @param to
	 * @param type
	 * @param amount
	 * @param locationType
	 * @param submitStatus
	 * @param submitChannel
	 * @param cacheFlag
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/chargeOrderList.action")
	@ResponseBody
	public String chargeOrderList(HttpServletRequest request, HttpSession session,
			@RequestParam(value="pageNo")Integer pageNo,
			@RequestParam(value="account",required=false)String account,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="location",required=false)String location,
			@RequestParam(value="start",required=false)String start,
			@RequestParam(value="end",required=false)String end,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="amount",required=false)Integer amount,
			@RequestParam(value="rangeType",required=false)String rangeType,
			@RequestParam(value="chargeStatus",required=false)String chargeStatus,
			@RequestParam(value="submitChannel",required=false)String submitChannel) throws IOException{
		
		Example example = new Example(ChargeOrder.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(account)&&!"-1".equals(account)) {
			createCriteria.andEqualTo("account", account);
		}
		if (!StringUtils.isEmpty(mobile)) {
			createCriteria.andEqualTo("mobile", mobile);
		}
		if (!StringUtils.isEmpty(location)&&!"请选择".equals(location)) {
			createCriteria.andEqualTo("location", location);
		}
		if (!StringUtils.isEmpty(start)&&!StringUtils.isEmpty(end)) {
			createCriteria.andBetween("optionTime", start, end);
		}
		if (!StringUtils.isEmpty(type)&& 0 != type) {
			createCriteria.andEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(amount)&& 0 != amount) {
			createCriteria.andEqualTo("amount", amount);
		}
		if (!StringUtils.isEmpty(rangeType)&&!"-1".equals(rangeType)) {
			createCriteria.andEqualTo("rangeType", rangeType);
		}
		if (!StringUtils.isEmpty(chargeStatus)&&!"-1".equals(chargeStatus)) {
			createCriteria.andEqualTo("chargeStatus", chargeStatus);
		}
		if (!StringUtils.isEmpty(submitChannel)&&!"-1".equals(submitChannel)) {
			createCriteria.andEqualTo("submitChannel", submitChannel);
		}
		createCriteria.andEqualTo("cacheFlag", 0);
		createCriteria.andGreaterThan("chargeStatus", 0);
		example.setOrderByClause("submit_time desc");
		List<ChargeOrder> listByPage = chargeOrderService.listByExampleAndPage(example, pageNo);
		Page<ChargeOrder> page=(Page<ChargeOrder>) listByPage;
		JSONObject result=new JSONObject();
		result.put("pages", page.getPages());
		result.put("total", page.getTotal());
		result.put("pageno", page.getPageNum());
		result.put("list", listByPage);
		return JSONUtils.toJsonString(result);
	}
	
	@RequestMapping(value="/chargeOrderCacheList.action")
	@ResponseBody
	public String chargeOrderCacheList(HttpSession session,
			@RequestParam(value="pageNo",defaultValue="1")String pageNo,
			@RequestParam(value="account",required=false)String account,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="location",required=false)String location,
			@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="amount",required=false)Integer amount,
			@RequestParam(value="rangeType",required=false)String rangeType,
			@RequestParam(value="submitStatus",required=false)String submitStatus,
			@RequestParam(value="submitChannel",required=false)String submitChannel,
			@RequestParam(value="cacheFlag",required=false)String cacheFlag) throws IOException{
		Example example = new Example(ChargeOrder.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(mobile)) {
			createCriteria.andEqualTo("mobile", mobile);
		}
		if (!StringUtils.isEmpty(location)&&!"请选择".equals(location)) {
			createCriteria.andEqualTo("location", location);
		}
		if (!StringUtils.isEmpty(from)&&!StringUtils.isEmpty(to)) {
			createCriteria.andBetween("optionTime", from, to);
		}
		if (!StringUtils.isEmpty(type)&& 0 != type) {
			createCriteria.andEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(amount)&& 0 != amount) {
			createCriteria.andEqualTo("amount", amount);
		}
		if (!StringUtils.isEmpty(rangeType)&&!"-1".equals(rangeType)) {
			createCriteria.andEqualTo("rangeType", rangeType);
		}
		createCriteria.andEqualTo("cacheFlag", 1);
		example.setOrderByClause(" option_time desc,id desc");
		List<ChargeOrder> listByExampleAndPage = chargeOrderService.listByExampleAndPage(example, Integer.valueOf(pageNo));
		Page<ChargeOrder> page = (Page<ChargeOrder>) listByExampleAndPage;
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		result.put("pages", page.getPages());
		result.put("total", page.getTotal());
		result.put("pageno", page.getPageNum());
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/reMatchPackage.action")
	@ResponseBody
	public String reMatchPackage(@RequestParam(value="ids[]")List<Integer> ids){
		JSONObject result = new JSONObject();
		if(!CollectionUtils.isEmpty(ids)){
			Integer updateCacheFlag = chargeOrderService.updateCacheFlag(ids);
			if(updateCacheFlag == ids.size()){
				Example example = new Example(ChargeOrder.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andIn("id", ids);
				List<ChargeOrder> listByExample = chargeOrderService.listByExample(example);
				ThreadManager.getInstance().execute(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						for (ChargeOrder chargeOrder : listByExample) {
							ChargeResponsVO chargeResult = ChargeManager.getInstance().charge(chargeOrder);
							if(!chargeResult.isSuccess()){
								chargeOrder.setCacheFlag(1);
								chargeOrder.setError(chargeResult.getMsg());
								chargeOrderService.saveOrUpdate(chargeOrder);
							}
						}
					}
				});
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/stopTask.action")
	@ResponseBody
	public String stopTask(@RequestParam(value="ids[]")List<Integer> ids){
		JSONObject result = new JSONObject();
		if(!CollectionUtils.isEmpty(ids)){
			Integer updateCacheFlag = chargeOrderService.updateCacheFlag(ids);
			if(updateCacheFlag == ids.size()){
				Example example = new Example(ChargeOrder.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andIn("id", ids);
				List<ChargeOrder> listByExample = chargeOrderService.listByExample(example);
				for (ChargeOrder chargeOrder : listByExample) {
					ChargeManager.getInstance().terminaOrder(chargeOrder);
				}
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/callbackTask.action")
	@ResponseBody
	public String callbackTask(@RequestParam(value="ids[]")List<Integer> ids){
		JSONObject result = new JSONObject();
		if(!CollectionUtils.isEmpty(ids)){
			Example example = new Example(ChargeOrder.class);
			Criteria createCriteria = example.createCriteria();
			createCriteria.andIn("id", ids);
			List<ChargeOrder> listByExample = chargeOrderService.listByExample(example);
			for (ChargeOrder item : listByExample) {
				CallbackManager.getInstance().addToCallback(item);
			}
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping(value="/exportExcelChargeOrderDatas.action")
	public void exportExcelChargeLogDatas(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value="account",required=false)String account,
			@RequestParam(value="start",required=false)String start,
			@RequestParam(value="end",required=false)String end,
			@RequestParam(value="chargeStatus",required=false)String chargeStatus,
			@RequestParam(value="submitChannel",required=false)String submitChannel) {
		Example example = new Example(ChargeOrder.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(account)&&!"-1".equals(account)) {
			createCriteria.andEqualTo("account", account);
		}
//		if (!StringUtils.isEmpty(mobile)) {
//			createCriteria.andEqualTo("mobile", mobile);
//		}
//		if (!StringUtils.isEmpty(location)&&!"请选择".equals(location)) {
//			createCriteria.andEqualTo("location", location);
//		}
		if (!StringUtils.isEmpty(start)&&!StringUtils.isEmpty(end)) {
			createCriteria.andBetween("optionTime", start, end);
		}
//		if (!StringUtils.isEmpty(type)&& 0 != type) {
//			createCriteria.andEqualTo("type", type);
//		}
//		if (!StringUtils.isEmpty(amount)&& 0 != amount) {
//			createCriteria.andEqualTo("amount", amount);
//		}
//		if (!StringUtils.isEmpty(rangeType)&&!"-1".equals(rangeType)) {
//			createCriteria.andEqualTo("rangeType", rangeType);
//		}
		if (!StringUtils.isEmpty(chargeStatus)&&!"-1".equals(chargeStatus)) {
			createCriteria.andEqualTo("chargeStatus", chargeStatus);
		}
		if (!StringUtils.isEmpty(submitChannel)&&!"-1".equals(submitChannel)) {
			createCriteria.andEqualTo("submitChannel", submitChannel);
		}
		createCriteria.andEqualTo("cacheFlag", 0);
		createCriteria.andGreaterThan("chargeStatus", 0);
		example.setOrderByClause("submit_time desc");
		List<ChargeOrder> listByExample = chargeOrderService.listByExample(example);
		
		SXSSFWorkbook wb = new SXSSFWorkbook(100); 
		Sheet sheet = wb.createSheet();     //工作表对象  
	    Row nRow = sheet.createRow(0);      //行对象  
	    CellStyle style=wb.createCellStyle();//单元格样式
	       
	    ExcelUtils.initPoiRowStyle(wb, sheet, style);
	    
	    String[] titles=new String[]{"订单编号","代理商","手机号","号码类型","流量类型",
	    		"流量值","基础价格","状态","订单生成时间","提交时间","回调时间","充值通道","接入","折后价","外放","扣费金额","是否带票"};
	    for (int i = 0; i < titles.length; i++) {
	    	Cell c = nRow.createCell(i);
	    	c.setCellValue(titles[i]);
	    	c.setCellStyle(style);
	    	sheet.setColumnWidth(i, 25*256);
		}
	    
	    CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式	
	    cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    
		for (int j = 0, size = listByExample.size(); j < size; j++) {
			ChargeOrder chargeOrder = listByExample.get(j);
			Row row = sheet.createRow(j+1);  
			//订单编号  
		    Cell c0 = row.createCell(0);    
		    c0.setCellValue(chargeOrder.getId());
		    c0.setCellStyle(cellStyle);
		    //代理商
		    Cell c1 = row.createCell(1); 
		    c1.setCellValue(chargeOrder.getAccount());
		    c1.setCellStyle(cellStyle);
		    //手机号
		    Cell c2 = row.createCell(2); 
		    c2.setCellValue(chargeOrder.getMobile());
		    c2.setCellStyle(cellStyle);
		    //号码类型
		    Cell c3 = row.createCell(3);   
		    Integer mtype = chargeOrder.getType();
		    String typeStr = "";
		    if(mtype == 1){
		    	typeStr = "移动";
		    }else if(mtype == 2){
		    	typeStr = "联通";
		    }else{
		    	typeStr = "电信";
		    }
		    c3.setCellValue(typeStr);
		    c3.setCellStyle(cellStyle);
		    
		    //流量类型
		    Integer mrangeType = chargeOrder.getRangeType();
		    String rangeStr = "";
		    if(mrangeType == 0){
		    	rangeStr = "全国流量";
		    }else{
		    	rangeStr = "省内流量";
		    }
		    Cell c4 = row.createCell(4);   
		    c4.setCellValue(rangeStr);
		    c4.setCellStyle(cellStyle);
		    //流量值
		    Cell c5 = row.createCell(5);   
		    c5.setCellValue(chargeOrder.getAmount());
		    c5.setCellStyle(cellStyle);
		    //基础价格
		    Cell c6 = row.createCell(6);   
		    c6.setCellValue(chargeOrder.getMoney());
		    c6.setCellStyle(cellStyle);
		    //状态
		    Cell c7 = row.createCell(7);   
		    Integer mChargeStatus = chargeOrder.getChargeStatus();
		    String statusStr = "";
		    if(mChargeStatus == 2){
		    	statusStr = "提交成功";
		    }else if(mChargeStatus == 3){
		    	statusStr = "提交失败";
		    }else if(mChargeStatus == 4){
		    	statusStr = "充值成功";
		    }else if(mChargeStatus == 5){
		    	statusStr = "充值失败";
		    }
		    c7.setCellValue(statusStr);
		    c7.setCellStyle(cellStyle);
		    //订单生成时间
		    Cell c8 = row.createCell(8);
		    if(chargeOrder.getOptionTime() == null){
		    	c8.setCellValue("");
		    }else{
		    	c8.setCellValue(TimeUtls.date2StrByDefault(chargeOrder.getOptionTime()));
		    }
		    c8.setCellStyle(cellStyle);
		    //提交时间
		    Cell c9 = row.createCell(9);
		    if(chargeOrder.getSubmitTime() == null){
		    	c9.setCellValue("");
		    }else{
		    	c9.setCellValue(TimeUtls.date2StrByDefault(chargeOrder.getSubmitTime()));
		    }
		    c9.setCellStyle(cellStyle);
		    //提交时间
		    Cell c10 = row.createCell(10);
		    if(chargeOrder.getReportTime() == null){
		    	c10.setCellValue("");
		    }else{
		    	c10.setCellValue(TimeUtls.date2StrByDefault(chargeOrder.getReportTime()));
		    }
		    c10.setCellStyle(cellStyle);
		    
		    //通道
		    Cell c11 = row.createCell(11);   
		    c11.setCellValue(chargeOrder.getChannelName());
		    c11.setCellStyle(cellStyle);
		    //接入
		    Cell c12 = row.createCell(12);   
		    c12.setCellValue(chargeOrder.getInDiscount());
		    c12.setCellStyle(cellStyle);
		    //折后价
		    Cell c13 = row.createCell(13);   
		    c13.setCellValue(chargeOrder.getDiscountMoney());
		    c13.setCellStyle(cellStyle);
		    //外放
		    Cell c14 = row.createCell(14);   
		    c14.setCellValue(chargeOrder.getOutDiscount());
		    c14.setCellStyle(cellStyle);
		    //扣费金额
		    Cell c15 = row.createCell(15);   
		    c15.setCellValue(chargeOrder.getPayMoney());
		    c15.setCellStyle(cellStyle);
		    //是否带票
		    Cell c16 = row.createCell(16);  
		    Integer payBill = chargeOrder.getPayBill();
		    String payBillStr = "";
		    if(payBill == 0){
		    	payBillStr = "不带票";
		    }else if(payBill == 1){
		    	payBillStr = "带票";
		    }
		    c16.setCellValue(payBillStr);
		    c16.setCellStyle(cellStyle);
		}
		String filename="充值记录.xlsx";
		try {
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"iso-8859-1")  );  
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charest=UTF-8");  
	        response.setBufferSize(2048);
			OutputStream os=response.getOutputStream();
			wb.write(os);
			wb.close();
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
