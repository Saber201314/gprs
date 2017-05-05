package com.shlr.gprs.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.cache.ChannelTemplateCache;
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.domain.Callback;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.ChannelLog;
import com.shlr.gprs.domain.ChannelResource;
import com.shlr.gprs.domain.ChannelTemplate;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.ChargeReport;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.CallbackService;
import com.shlr.gprs.services.ChannelLogService;
import com.shlr.gprs.services.ChannelResourceService;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.ChargeReportService;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.DecimalUtils;
import com.shlr.gprs.utils.StrUtils;
import com.shlr.gprs.utils.TimeUtls;
import com.shlr.gprs.vo.PayLogFmt;
import com.shlr.gprs.vo.ResultBaseVO;

import junit.framework.Assert;
import net.sf.jxls.transformer.XLSTransformer;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
* @author xucong
* @version 创建时间：2017年4月3日 下午7:31:22
* 
*/
@Controller
@RequestMapping("/admin")
public class QueryController {
	
	Logger logger=LoggerFactory.getLogger(QueryController.class);
	
	@Resource
	UserService userService;
	@Resource
	ChargeReportService chargeReportService;
	@Resource
	ChannelResourceService channelResourceService;
	@Resource
	ChargeOrderService chargeOrderService;
	@Resource
	PayLogService payLogService;
	@Resource
	ChannelLogService channelLogService ;
	@Resource
	CallbackService callbackService;
	
	/**
	 * 主页数据
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value="/layout/home.action")
	public String home(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException{
		Users currentUser = userService.getCurrentUser(session);
		Map<String, Object> result=new LinkedHashMap<String, Object>();
		if (currentUser==null) {
			result.put("islogin", "-1");
			response.getWriter().print(JSON.toJSONString(result));
			return null;
		}
		int type = currentUser.getType();
		//如果不是系统管理员或者不是总代理
		if (type != 1 && type != 2) {
			result.put("islogin", "-1");
			response.getWriter().print(JSON.toJSONString(result));
			return null;
		}
		List<ChargeReport> reportList  = chargeReportService.queryCurDayList();
		Float resume=0.00F ,remain = 0.00F;
		for (ChargeReport chargeReport : reportList) {
			resume +=Float.valueOf(chargeReport.getResumePrice()); 
			remain +=Float.valueOf(chargeReport.getRemainPrice()); 
		}
		
		ChargeReport total=new ChargeReport();
		total.setAccount("合计");
		total.setResumePrice(String.valueOf(resume));
		total.setRemainPrice(String.valueOf(remain));
		reportList.add(total);
		result.put("islogin", "1");
		result.put("data", reportList);
		response.getWriter().print(JSON.toJSONString(result));
		return null;
	}
	
	/**
	 * 获取通道资源
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping("/layout/getChannelResource.action")
	public String getChannelResource(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser==null|| currentUser.getType()!=1) {
			return "redirect:/index.jsp";
		}
		List<ChannelResource> queryList = channelResourceService.queryList();
		response.getWriter().println(JSON.toJSONString(queryList));
		return null;
	}
	
	/**
	 * 获取当前通道列表
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException 
	 */
	@RequestMapping(value="/query/getCurrentChannelList.action",produces="application/json;charset=utf-8")
	public String getCurrentChannelList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		response.setContentType("application/json;charset=utf-8");
		Collection<Channel> channelList = ChannelCache.idMap.values();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			logger.error(" getCurrentChannelList ", e);
		}
		if (writer!=null) {
			writer.println(JSON.toJSONString(channelList));
		}
		return null;
	}
	
	
	
	
	@RequestMapping(value="/layout/getCurrentChannelList.action",produces="application/json;charset=utf-8")
	public String getLayoutCurrentChannelList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		response.setContentType("application/json;charset=utf-8");
		Collection<Channel> channelList = ChannelCache.idMap.values();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			logger.error(" getCurrentChannelList ", e);
		}
		if (writer!=null) {
			writer.println(JSON.toJSONString(channelList));
		}
		return null;
	}
	
	
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
	public String chargeOrderList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value="pageNo")Integer pageNo,@RequestParam(value="account",required=false)String account,
			@RequestParam(value="mobile",required=false)String mobile,@RequestParam(value="location",required=false)String location,
			@RequestParam(value="from",required=false)String from,@RequestParam(value="to",required=false)String to,
			@RequestParam(value="type",required=false)Integer type,@RequestParam(value="amount",required=false)Integer amount,
			@RequestParam(value="locationType",required=false)String locationType,@RequestParam(value="submitStatus",required=false)String submitStatus,
			@RequestParam(value="submitChannel",required=false)String submitChannel,@RequestParam(value="cacheFlag",required=false)String cacheFlag) throws IOException{
		
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			return "index";
		}
		
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
		if (!StringUtils.isEmpty(from)&&!StringUtils.isEmpty(to)) {
			createCriteria.andBetween("optionTime", from, to);
		}
		if (!StringUtils.isEmpty(type)&& 0 != type) {
			createCriteria.andEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(amount)&& 0 != amount) {
			createCriteria.andEqualTo("amount", amount);
		}
		if (!StringUtils.isEmpty(locationType)&&!"0".equals(locationType)) {
			createCriteria.andEqualTo("locationType", locationType);
		}
		if (!StringUtils.isEmpty(submitStatus)&&!"-1".equals(submitStatus)) {
			if ("0".equals(submitStatus)) {
				createCriteria.andEqualTo("submitStatus", 0);
			}else if ("1".equals(submitStatus)) {
				createCriteria.andEqualTo("submitStatus", 1);
				createCriteria.andEqualTo("chargeStatus", 0);
			}else if ("2".equals(submitStatus)) {
				createCriteria.andEqualTo("submitStatus", 1);
				createCriteria.andEqualTo("chargeStatus", 1);
			}else if ("3".equals(submitStatus)) {
				createCriteria.andEqualTo("submitStatus", 1);
				createCriteria.andEqualTo("chargeStatus", -1);
			}
		}
		if (!StringUtils.isEmpty(submitChannel)&&!"-1".equals(submitChannel)) {
			createCriteria.andEqualTo("submitChannel", submitChannel);
		}
		if (StringUtils.isEmpty(cacheFlag)) {
			createCriteria.andEqualTo("cacheFlag", 0);
		}else{
			createCriteria.andEqualTo("cacheFlag", cacheFlag);
		}
		example.setOrderByClause("  option_time desc");
		List<ChargeOrder> listByPage = chargeOrderService.listByExampleAndPage(example, pageNo);
		Page<ChargeOrder> page=(Page<ChargeOrder>) listByPage;
		JSONObject result=new JSONObject();
		result.put("pages", page.getPages());
		result.put("total", page.getTotal());
		result.put("pageno", page.getPageNum());
		result.put("list", listByPage);
		response.getWriter().print(result.toJSONString());
		return null;
	}
	@RequestMapping(value="/chargeOrderCacheList.action")
	public String chargeOrderCacheList(HttpServletResponse response,HttpSession session,
			@RequestParam(value="pageNo")Integer pageNo,@RequestParam(value="account",required=false)String account,
			@RequestParam(value="mobile",required=false)String mobile,@RequestParam(value="location",required=false)String location,
			@RequestParam(value="from",required=false)String from,@RequestParam(value="to",required=false)String to,
			@RequestParam(value="type",required=false)Integer type,@RequestParam(value="amount",required=false)Integer amount,
			@RequestParam(value="locationType",required=false)String locationType,@RequestParam(value="submitStatus",required=false)String submitStatus,
			@RequestParam(value="submitChannel",required=false)String submitChannel,@RequestParam(value="cacheFlag",required=false)String cacheFlag) throws IOException{
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			return "redirect:/index.jsp";
		}
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
		if (!StringUtils.isEmpty(locationType)&&!"0".equals(locationType)) {
			createCriteria.andEqualTo("locationType", locationType);
		}
		createCriteria.andEqualTo("cacheFlag", 1);
		example.setOrderByClause("  id desc");
		List<ChargeOrder> listByExampleAndPage = chargeOrderService.listByExampleAndPage(example, 1);
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		response.getWriter().print(result.toJSONString());
		return null;
	}
	/**
	 * 根据用户级别获取用户列表
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query/userListByLevel.action")
	public String userListByLevel(HttpSession session){
		ResultBaseVO<Object> result=new ResultBaseVO<Object>();
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null) {
			result.addError("请登录");
			return JSON.toJSONString(result);
		}
		
		List<Users> userList=new ArrayList<Users>();
		if (currentUser.getType() == 1) {
			userList = userService.listByCondition(new Example(Users.class));
			
		} else {
			userList.add(currentUser);
		}
		result.setModule(userList);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 消费记录
	 * @param session
	 * @param pageNo
	 * @param mobile
	 * @param account
	 * @param from
	 * @param to
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/query/payLogList.action")
	@ResponseBody
	public String payLogList(HttpSession session,
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNo,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="account",required=false)String account,
			@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,
			@RequestParam(value="status",required=false,defaultValue="-3")String status,
			Model model){
		Users currentUser = userService.getCurrentUser(session);
		if(currentUser == null){
			return "index";		
		}
		int type = currentUser.getType();
		//如果不是系统管理员或者不是总代理
		if (type != 1 && type != 2) {
			return "index";			
		}
		Example example=new Example(PayLog.class,true,false);
		Criteria createCriteria = example.createCriteria();
		Calendar calendar=Calendar.getInstance();
		Date dfrom = new Date();
		Date dto = new Date();
		if (StringUtils.isEmpty(from)) {
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			dfrom = calendar.getTime();
		}else{
			dfrom=TimeUtls.timeStr2DateByDefault(from);
		}
		if (StringUtils.isEmpty(to)) {
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.SECOND, -1);
			dto=calendar.getTime();
		}else{
			dto=TimeUtls.timeStr2DateByDefault(to);
		}
		createCriteria.andBetween("optionTime", dfrom, dto);
		if (StringUtils.isEmpty(account) && type != 1) {
			createCriteria.andEqualTo("account", currentUser.getUsername());
		}else if(!StringUtils.isEmpty(account)&& !"-1".equals(account)  && type == 1){
			createCriteria.andEqualTo("account", account);
		}
		if (!StringUtils.isEmpty(status)&&!"-3".equals(status)) {
			createCriteria.andEqualTo("status", status);
		}
		if (!StringUtils.isEmpty(mobile)) {
			createCriteria.andLike("memo", mobile+"%");
		}
		example.setOrderByClause(" option_time desc");
		
		List<PayLog> listByExampleAndPage = payLogService.listByExampleAndPage(example, Integer.valueOf(pageNo) );
		Page<PayLog> page=(Page<PayLog>) listByExampleAndPage;
		
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		result.put("total", page.getTotal());
		result.put("pages", page.getPages());
		result.put("pageno", page.getPageNum());
		
		return result.toJSONString();
	}
	
	
	/**
	 * 显示账单信息
	 * @param session
	 * @param account
	 * @param from
	 * @param to
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query/showPayBillInfo.action")
	public String showPayBillInfo(HttpSession session,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to) {
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null) {
			return "index";
		}
		int type = currentUser.getType();
		// 如果不是系统管理员或者不是总代理
		if (type != 1 && type != 2) {
			return "index";
		}
		if (StringUtils.isEmpty(account) || from == null || to == null) {
			return "success";
		}
		List<PayLog> selectFirstAndLastPayLogByFrom2To = payLogService.selectFirstAndLastPayLogByFrom2To(account, from,
				to);
		Map<String, Object> payBillMap=new LinkedHashMap<String, Object>();
		
		if (!CollectionUtils.isEmpty(selectFirstAndLastPayLogByFrom2To)) {
			for (int i= 0; i < selectFirstAndLastPayLogByFrom2To.size(); i++) {
				if (i == 0) {
					payBillMap.put("startBalance", selectFirstAndLastPayLogByFrom2To.get(i).getBalance());
					payBillMap.put("startDate", TimeUtls.date2StrByDefault(selectFirstAndLastPayLogByFrom2To.get(i).getOptionTime()));
				} 
				if (i == 1) {
					payBillMap.put("endBalance", selectFirstAndLastPayLogByFrom2To.get(i).getBalance());
					payBillMap.put("endDate",TimeUtls.date2StrByDefault(selectFirstAndLastPayLogByFrom2To.get(i).getOptionTime()));
				} 
			}
		}
		Map RemittanceAndConsumeAndRefundMap = payLogService.selectRemittanceAndConsumeAndRefundByCondition(account, from,to);
		if (!CollectionUtils.isEmpty(RemittanceAndConsumeAndRefundMap)) {
			payBillMap.put("remittance", RemittanceAndConsumeAndRefundMap.get("remittance"));//充值
			  payBillMap.put("consume", RemittanceAndConsumeAndRefundMap.get("consume"));//消费
			  payBillMap.put("refund", RemittanceAndConsumeAndRefundMap.get("refund"));//退款
		}
		Double remittance = (Double)payBillMap.get("remittance");
		Double startBalance = (Double)payBillMap.get("startBalance");
		Double consume = (Double)payBillMap.get("consume");
		Double endBalance = (Double)payBillMap.get("endBalance");
		
		
		StringBuffer buff = new StringBuffer();
		if (remittance == null) {
			remittance = 0.0D;
		}
		buff.append("￥" + remittance).append(" + ");

		if (startBalance == null) {
			startBalance = 0.0D;
		}
		buff.append("￥" + startBalance).append(" - ");

		if (consume == null) {
			consume = 0.0D;
		}
		buff.append("￥" + consume).append(" - ");

		if (endBalance == null) {
			endBalance = 0.0D;
		}
		Double realbalance= (remittance + startBalance  - consume);
		realbalance=DecimalUtils.round(realbalance, 2);
		
		buff.append("￥" + realbalance+"(实际余额)  "); 
		Double finalresult=remittance + startBalance  - consume -   realbalance  ;
		Double diff = remittance + startBalance  - consume -   endBalance        ;
		
		finalresult=DecimalUtils.round(finalresult, 2);
		diff=DecimalUtils.round(diff, 2);
		
		buff.append(" =   ￥ ").append(finalresult +" \n").append("差异 = ￥" + diff+"   \n系统余额  = "+endBalance   );
		
		payBillMap.put("diff", buff.toString());
		
		if(diff == 0.0D){
			payBillMap.put("status", 1);
		}else{
			payBillMap.put("status", 0);
		}
		List<Map> NoPayBillMoneyMap = payLogService.selectNoPayBillMoneyByCondition(account, from, to);
		Double factMoney = 0.0D;
		if (!CollectionUtils.isEmpty(NoPayBillMoneyMap)) {
			for(int i = 0;i<NoPayBillMoneyMap.size();i++){
				int payBill = Integer.valueOf(String.valueOf(NoPayBillMoneyMap.get(i).get("pay_bill")));
				Double money = (Double) NoPayBillMoneyMap.get(i).get("money");
				if (money != null) {
					factMoney += money;
				}
				payBillMap.put("factMoney", factMoney);
				if (payBill == 1) {
					payBillMap.put("payBill", money);
					Double refund = (Double) payBillMap.get("refund");
					if (refund != null) {
						money = Math.round((money - refund) * 100) / 100.0;
						payBillMap.put("factPayBill", money);
					} else {
						payBillMap.put("factPayBill", money);
					}
				} else {
					payBillMap.put("unPayBill", money);
				}
			}
			
		}
		payBillMap.put("account", account);
		payBillMap.put("name", UsersCache.usernameMap.get(account).getName());		
		return JSON.toJSONString(payBillMap);
	}
	
	/**
	 * 导出消费明细Excel
	 * @param request
	 * @param response
	 * @param session
	 * @param account
	 * @param from
	 * @param to
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/query/exportExcelChargeLogDatas.action")
	public void exportExcelChargeLogDatas(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to) throws UnsupportedEncodingException{
		Users currentUser = userService.getCurrentUser(session);
		if(currentUser == null){
			return;
		}
		int type = currentUser.getType();
		String currentAccount = currentUser.getUsername();
		if(type != 1 && type != 2){
			return;
		}
		if(from == null || to == null){
			return;
		}
		Example example=new Example(PayLog.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (type != 1 && StringUtils.isEmpty(account)) {
			createCriteria.andEqualTo("account", currentAccount);
		}else{
			createCriteria.andEqualTo("account", account);
		}
		createCriteria.andBetween("optionTime", from, to);
		int pageNo=0;
		int allPages=0;
		long start=System.currentTimeMillis();
		SXSSFWorkbook wb = new SXSSFWorkbook(100); 
		Sheet sheet = wb.createSheet();     //工作表对象  
	    Row nRow = sheet.createRow(0);      //行对象  
	    CellStyle style=wb.createCellStyle();
	       
	    initPoiRowStyle(wb, sheet, style);
	    
	    String[] titles=new String[]{"代理商","类别","流水号","扣费金额（元）","当前余额（元）","折扣","代理商订单号","手机号","流量值","扣费时间","操作状态"};
	    for (int i = 0; i < titles.length; i++) {
	    	Cell c = nRow.createCell(i);
	    	c.setCellValue(titles[i]);
	    	c.setCellStyle(style);
		}
	    
		List<PayLog> bufferList=new ArrayList<PayLog>();
		PayLogFmt fmt=new PayLogFmt();
		boolean flag=true;
		do {
			pageNo++;
			List<PayLog> listByExample = payLogService.listByExample(example,pageNo,10000);
			allPages=((Page<PayLog>)listByExample).getPages();
			bufferList.addAll(listByExample);
			int size = bufferList.size();
			for (int i = 0; i < size ; i++) {
				PayLog item = bufferList.get(i);
				Row row = sheet.createRow((pageNo-1)*10000+i+1);        //行对象  
			    Cell cc0 = row.createCell(0);    
			    cc0.setCellValue(item.getAccount());
			    Cell cc1 = row.createCell(1);   
			    cc1.setCellValue(fmt.typefmt(item.getType()));
			    Cell cc2 = row.createCell(2);   
			    cc2.setCellValue(item.getOrderId());
			    Cell cc3 = row.createCell(3);   
			    cc3.setCellValue(item.getMoney());
			    Cell cc4 = row.createCell(4);   
			    cc4.setCellValue(item.getBalance());
			    Cell cc5 = row.createCell(5);   
			    cc5.setCellValue(item.getDiscount());
			    Cell cc6 = row.createCell(6);
			    cc6.setCellValue(item.getAgentOrderId());
			    Cell cc7 = row.createCell(7);  
			    String[] split = item.getMemo().split("，");
			    cc7.setCellValue(split[0]);
			    Cell cc8 = row.createCell(8);   
			    cc8.setCellValue(split[1]);
			    Cell cc9 = row.createCell(9); 
			    cc9.setCellValue(TimeUtls.date2StrByDefault(item.getOptionTime()));
			    Cell cc10 = row.createCell(10); 
			    cc10.setCellValue(fmt.statusfmt(item.getStatus()));
			}
			bufferList.clear();
			if (pageNo==allPages) {
				flag=false;
			}
		} while (flag);
		System.out.println(System.currentTimeMillis()-start);
		String filename="消费明细记录.xlsx";
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		Map<String, Object> bean=new HashMap<String, Object>();
//		bean.put("bean", listByExample);
//		bean.put("fmt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//		bean.put("paylogfmt", new PayLogFmt());
//		XLSTransformer transformer = new XLSTransformer();  
//        InputStream in=null;  
//        OutputStream out=null;  
//        String templetePath = request.getServletContext().getRealPath("/")+"/templete/paylog_templete.xlsx";
//        String destFileName= "流量充值记录.xlsx";  
//        //设置响应  
//        response.setHeader("Content-Disposition", "attachment;filename=" + new String(destFileName.getBytes(),"iso-8859-1")  );  
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charest=UTF-8");  
//        response.setBufferSize(2048);
//        try {  
//            in=new BufferedInputStream(new FileInputStream(templetePath));  
//            long start=System.currentTimeMillis();
//            Workbook workbook=transformer.transformXLS(in, bean); 
//            long end=System.currentTimeMillis();
//            System.out.println((end-start));
//            out=response.getOutputStream();  
//            //将内容写入输出流并把缓存的内容全部发出去  
//            workbook.write(out);  
//            out.flush();  
//        } catch (InvalidFormatException e) {  
//            e.printStackTrace();  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        } finally {  
//            if (in!=null){try {in.close();} catch (IOException e) {}}  
//            if (out!=null){try {out.close();} catch (IOException e) {}}  
//        }  
		
		
	}
	
	
	/**
	 * 通知提交日志
	 * @param session
	 * @param mobile
	 * @param pageNo
	 * @param from
	 * @param to
	 * @param templateId
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/query/channelLogList.action")
	@ResponseBody
	public String channelLogList(HttpSession session,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="pageNo",required=false)String pageNo,
			@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,
			@RequestParam(value="submitChannel",required=false)String templateId,
			Model model) throws UnsupportedEncodingException{
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			return "index";
		}
		Example example=new Example(ChannelLog.class, true, false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(mobile)) {
			createCriteria.andEqualTo("mobile", mobile);
		}
		Date dfrom = null;
		Date dto = null;
		if (!StringUtils.isEmpty(from)) {
			dfrom =TimeUtls.timeStr2Date(from,"yyyy-MM-dd");
			createCriteria.andGreaterThanOrEqualTo("optionTime", dfrom);
		}
		if (!StringUtils.isEmpty(to)) {
			dto=TimeUtls.timeStr2Date(to,"yyyy-MM-dd");
			createCriteria.andLessThanOrEqualTo("optionTime", dto);
		}
		if (!StringUtils.isEmpty(templateId)&&!"-1".equals(templateId)) {
			createCriteria.andEqualTo("templateId", Integer.valueOf(templateId)-2);
		}
		example.setOrderByClause(" id desc ");
		List<ChannelLog> listByExampleAndPage = channelLogService.listByExampleAndPage(example, Integer.valueOf(pageNo) );
		Page<ChannelLog> page=(Page<ChannelLog>) listByExampleAndPage;
		
		for (ChannelLog channelLog : listByExampleAndPage) {
			channelLog.setResponse(StrUtils.ascii2native(channelLog.getResponse()));
		}
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		result.put("pages", page.getPages());
		result.put("total", page.getTotal());
		result.put("pageno", page.getPageNum());
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping(value="/query/callbackList.action")
	@ResponseBody
	public String callbackList(HttpSession session,
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNo,
			@RequestParam(value="account",required=false)String account,
			@RequestParam(value="mobile",required=false)String mobile,
			Model model){
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			return "index";
		}
		
		Example example=new Example(Callback.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(mobile)) {
			createCriteria.andEqualTo("mobile", mobile);
			model.addAttribute("mobile", mobile);
		}
		if (!StringUtils.isEmpty(account) && !"-1".equals(account)) {
			createCriteria.andEqualTo("account", account);
			model.addAttribute("account", account);
		}
		example.setOrderByClause(" id desc");
		List<Callback> listByExampleAndPage = callbackService.listByExampleAndPage(example, Integer.valueOf(pageNo));
		Page<Callback> page= (Page<Callback>) listByExampleAndPage;
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		result.put("pages", page.getPages());
		result.put("total", page.getTotal());
		result.put("pageno", page.getPageNum());
		return result.toJSONString();
	}
	
	
	/**
	 * 初始化Excel单元格样式
	 * @param wb
	 * @param sheet
	 * @param style
	 */
	private void initPoiRowStyle(SXSSFWorkbook wb,Sheet sheet,CellStyle style){		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式	
		Font font = wb.createFont();
		font.setColor(HSSFFont.COLOR_NORMAL);
		font.setFontName("黑体"); //字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度		
		style.setFont(font);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		
	}




}
