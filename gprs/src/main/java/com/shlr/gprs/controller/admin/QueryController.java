package com.shlr.gprs.controller.admin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;  
import org.apache.poi.ss.usermodel.Workbook; 
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
import com.shlr.gprs.cache.UsersCache;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.ChannelResource;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.ChargeReport;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.ChannelResourceService;
import com.shlr.gprs.services.ChargeOderService;
import com.shlr.gprs.services.ChargeReportService;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.DecimalUtils;
import com.shlr.gprs.utils.TimeUtls;
import com.shlr.gprs.vo.PageResultVO;
import com.shlr.gprs.vo.ResultBaseVO;
import com.shlr.gprs.vo.UsersVO;

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
	ChargeOderService chargeOderService;
	@Resource
	PayLogService payLogService;
	
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
		if (!StringUtils.isEmpty(account)) {
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
		if (!StringUtils.isEmpty(submitChannel)) {
			createCriteria.andEqualTo("submitChannel", submitChannel);
		}
		if (StringUtils.isEmpty(cacheFlag)) {
			createCriteria.andEqualTo("cacheFlag", 0);
		}else{
			createCriteria.andEqualTo("cacheFlag", cacheFlag);
		}
		example.setOrderByClause("  option_time desc");
		List<ChargeOrder> listByPage = chargeOderService.listByExampleAndPage(example, pageNo);
		Page<ChargeOrder> page=(Page<ChargeOrder>) listByPage;
		JSONObject result=new JSONObject();
		result.put("allRecord", page.getTotal());
		result.put("allPage", page.getPages());
		result.put("pageNo", page.getPageNum());
		result.put("list", listByPage);
		response.getWriter().print(result.toJSONString());
		return null;
	}
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
			UsersVO userVO = new UsersVO();
			userList = userService.listByCondition(userVO);
			
		} else {
			userList.add(currentUser);
		}
		result.setModule(userList);
		return JSON.toJSONString(result);
	}
	@RequestMapping(value="/query/payLogList.action")
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
		}else if(!StringUtils.isEmpty(account) && type == 1){
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
		LinkedList<PayLog> arrayList = new LinkedList<PayLog>();
		for (PayLog item : listByExampleAndPage) {
			arrayList.add(item);
		}
		model.addAttribute("from", dfrom);
		model.addAttribute("to", dto);
		model.addAttribute("status", Integer.valueOf(status));
		model.addAttribute("account", account);
		model.addAttribute("mobile", mobile);
		model.addAttribute("payLogList",arrayList);
		model.addAttribute("pageNo", page.getPageNum());
		model.addAttribute("allRecord", page.getTotal());
		model.addAttribute("allPage", page.getPages());
		
		return "admin/query/payLogList";
	}
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
		Map NoPayBillMoneyMap = payLogService.selectNoPayBillMoneyByCondition(account, from, to);
		Double factMoney = 0.0D;
		if (!CollectionUtils.isEmpty(NoPayBillMoneyMap)) {
			int payBill = Integer.valueOf(String.valueOf(NoPayBillMoneyMap.get("pay_bill")));
			Double money = (Double) NoPayBillMoneyMap.get("money");
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
		payBillMap.put("account", account);
		payBillMap.put("name", UsersCache.usernameMap.get(account).getName());		
		return JSON.toJSONString(payBillMap);
	}
	@RequestMapping(value="/query/exportExcelChargeLogDatas.action")
	public void exportExcelChargeLogDatas(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to){
		Users currentUser = userService.getCurrentUser(session);
		if(currentUser == null){
			return;
		}
		int type = currentUser.getType();
		String currentAccount = currentUser.getUsername();
		if(type != 1 && type != 2){
			return;
		}
		if(StringUtils.isEmpty(account) || from == null || to == null){
			return;
		}
		Example example=new Example(PayLog.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (type != 1 && StringUtils.isEmpty(account)) {
//			queryChargeOrderDO.setAccount(currentUser.getUsername());
			createCriteria.andEqualTo("account", currentAccount);
		}else{
			createCriteria.andEqualTo("account", account);
		}
		createCriteria.andBetween("optionTime", from, to);
		List<PayLog> listByExample = payLogService.listByExample(example);
		Map<String, Object> bean=new HashMap<String, Object>();
		bean.put("bean", listByExample);
		XLSTransformer transformer = new XLSTransformer();  
        InputStream in=null;  
        OutputStream out=null;  
        String templetePath = request.getServletContext().getRealPath("/")+"/templete/paylog_templete.xlsx";
        String destFileName= "aaaa.xlsx";  
        //设置响应  
        response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);  
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");  
        response.setBufferSize(2048);
        try {  
            in=new BufferedInputStream(new FileInputStream(templetePath));  
            Workbook workbook=transformer.transformXLS(in, bean);  
            out=response.getOutputStream();  
            //将内容写入输出流并把缓存的内容全部发出去  
            workbook.write(out);  
            out.flush();  
        } catch (InvalidFormatException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (in!=null){try {in.close();} catch (IOException e) {}}  
            if (out!=null){try {out.close();} catch (IOException e) {}}  
        }  
		
		
	}
}
