package com.shlr.gprs.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.ChannelResource;
import com.shlr.gprs.domain.ChargeReport;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.ChannelResourceService;
import com.shlr.gprs.services.ChargeReportService;
import com.shlr.gprs.services.UserService;

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
//			response.sendRedirect("/index.jsp");
			result.put("islogin", "-1");
			response.getWriter().print(JSON.toJSONString(result));
			return null;
		}
		int type = currentUser.getType();
		//如果不是系统管理员或者不是总代理
		if (type != 1 && type != 2) {
//			response.sendRedirect("/index.jsp");
			result.put("islogin", "-1");
			response.getWriter().print(JSON.toJSONString(result));
			return null;
		}
		List<ChargeReport> reportList  = chargeReportService
				.queryCurDayList();	
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("gonggao", currentUser.getGonggao());
//		this.reportMapList.add(map);
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
	@RequestMapping(value="/layout/getCurrentChannelList.action",produces="application/json;charset=utf-8")
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
}
