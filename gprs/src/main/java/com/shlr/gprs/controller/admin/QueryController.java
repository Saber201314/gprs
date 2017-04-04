package com.shlr.gprs.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.shlr.gprs.domain.ChargeReport;
import com.shlr.gprs.domain.Users;
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
	@Resource
	UserService userService;
	@Resource
	ChargeReportService chargeReportService;
	
	@RequestMapping(value="/layout/home.action")
	public void home(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException{
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser==null) {
			response.sendRedirect("/index.jsp");
			return;
		}
		int type = currentUser.getType();
		//如果不是系统管理员或者不是总代理
		if (type != 1 && type != 2) {
			response.sendRedirect("/index.jsp");
			return;
		}
		List<ChargeReport> reportList  = chargeReportService
				.queryCurDayList();	
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("gonggao", currentUser.getGonggao());
//		this.reportMapList.add(map);
		response.getWriter().print(JSON.toJSONString(reportList));
	}
	public void getChannelResource(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser==null|| currentUser.getType()!=1) {
			response.sendRedirect("/index.jsp");
			return;
		}
	}
}
