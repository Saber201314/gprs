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
import java.util.Random;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
import com.shlr.gprs.services.ChannelService;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.ChargeReportService;
import com.shlr.gprs.services.PayLogService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.DecimalUtils;
import com.shlr.gprs.utils.ExcelUtils;
import com.shlr.gprs.utils.JSONUtils;
import com.shlr.gprs.utils.StrUtils;
import com.shlr.gprs.utils.TimeUtls;
import com.shlr.gprs.vo.PayLogFmt;
import com.xiaoleilu.hutool.util.StrUtil;
import com.shlr.gprs.vo.ChargeResponsVO;

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
	ChannelService channelService;
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
	@ResponseBody
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
		return JSONUtils.toJsonString(result);
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
	@RequestMapping(value="/query/getCurrentChannelList.action")
	@ResponseBody
	public String getCurrentChannelList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		List<Channel> list = channelService.list();
		return JSON.toJSONString(list);
	}
	
	
	
	
	@RequestMapping(value="/layout/getCurrentChannelList.action")
	@ResponseBody
	public String getLayoutCurrentChannelList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		List<Channel> list = channelService.list();
		return JSON.toJSONString(list);
	}
	
	
	
	
	/**
	 * 根据用户级别获取用户列表
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/query/agentList.action")
	public String userListByLevel(HttpSession session,
			@RequestParam(value="username",required=false)String username,
			@RequestParam(value="name",required=false)String name){
		JSONObject result = new JSONObject();
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null) {
			result.put("success", false);
			result.put("msg", "请登录");
			return JSON.toJSONString(result);
		}
		List<Users> userList=new ArrayList<Users>();
		
		if (currentUser.getType() == 1) {
			Example example = new Example(Users.class);
			Criteria createCriteria = example.createCriteria();
			if(!StringUtils.isEmpty(username)){
				createCriteria.andLike("username", "%"+username+"%");
			}
			if(!StringUtils.isEmpty(name)){
				createCriteria.andLike("name", "%"+name+"%");
			}
			
			
			createCriteria.andEqualTo("type", 2);
			example.setOrderByClause(" id desc");
			userList = userService.listByExample(example);
			if(!CollectionUtils.isEmpty(userList)){
				result.put("success", true);
				result.put("list", userList);
			}
		}else{
			result.put("success", false);
			result.put("msg", "您没有权限查看");
		}
		return result.toJSONString();
	}
	@RequestMapping(value="/query/page/agentList.action")
	@ResponseBody
	public String agentList(HttpSession session,
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNo,
			@RequestParam(value="username",required=false)String username,
			@RequestParam(value="name",required=false)String name){
		JSONObject result = new JSONObject();
		List<Users> userList=new ArrayList<Users>();
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser.getType() == 1) {
			Example example = new Example(Users.class);
			Criteria createCriteria = example.createCriteria();
			if(!StringUtils.isEmpty(username)){
				createCriteria.andLike("username", "%"+username+"%");
			}
			if(!StringUtils.isEmpty(name)){
				createCriteria.andLike("name", "%"+name+"%");
			}
			createCriteria.andEqualTo("type", 2);
			example.setOrderByClause(" id desc");
			userList = userService.listByExampleAndPage(example, Integer.valueOf(pageNo));
			Page<Users> page = (Page<Users>) userList;
			if(!CollectionUtils.isEmpty(userList)){
				result.put("success", true);
				result.put("list", userList);
				result.put("total", page.getTotal());
				result.put("pages", page.getPages());
				result.put("pageno", page.getPageNum());
			}
		}else{
			result.put("success", false);
			result.put("msg", "您没有权限查看");
		}
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping(value="/query/page/adminList.action")
	@ResponseBody
	public String adminList(HttpSession session,
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNo,
			@RequestParam(value="username",required=false)String username,
			@RequestParam(value="name",required=false)String name){
		JSONObject result = new JSONObject();
		List<Users> userList=new ArrayList<Users>();
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser.getType() == 1) {
			Example example = new Example(Users.class);
			Criteria createCriteria = example.createCriteria();
			if(!StringUtils.isEmpty(username)){
				createCriteria.andLike("username", "%"+username+"%");
			}
			if(!StringUtils.isEmpty(name)){
				createCriteria.andLike("name", "%"+name+"%");
			}
			createCriteria.andEqualTo("type", 1);
			example.setOrderByClause(" id desc");
			userList = userService.listByExampleAndPage(example, Integer.valueOf(pageNo));
			Page<Users> page = (Page<Users>) userList;
			if(!CollectionUtils.isEmpty(userList)){
				result.put("success", true);
				result.put("list", userList);
				result.put("total", page.getTotal());
				result.put("pages", page.getPages());
				result.put("pageno", page.getPageNum());
			}
		}else{
			result.put("success", false);
			result.put("msg", "您没有权限查看");
		}
		return JSONUtils.toJsonString(result);
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
		payBillMap.put("name", userService.findByUsername(account).getName());		
		return JSON.toJSONString(payBillMap);
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
	
	/*
	 * 回调日志
	 */
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
	
}
