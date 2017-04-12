package com.shlr.gprs.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.ChannelResource;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.domain.ChargeReport;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.ChannelResourceService;
import com.shlr.gprs.services.ChargeOderService;
import com.shlr.gprs.services.ChargeReportService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.vo.ResultBaseVO;
import com.shlr.gprs.vo.UsersVO;

import junit.framework.Assert;
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
	@RequestMapping(value="/query/userListByLevel.action",produces="application/json;charset=utf-8")
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
}
