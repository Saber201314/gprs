package com.shlr.gprs.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.MobileUtil;
import com.shlr.gprs.utils.SecurityCode;
import com.shlr.gprs.utils.SecurityImageUtil;
import com.shlr.gprs.vo.ResultBaseDO;
import com.suwoit.json.util.StringUtils;

import jdk.nashorn.internal.ir.annotations.Reference;

/**
* @author xucong
* @version 创建时间：2017年4月2日 下午8:30:23
* 入口控制器
*/
@Controller
public class PortalController {

	
	private String securityCode;
	private String mobile;
	private ResultBaseDO<Object> result;
	private ByteArrayInputStream imageStream;
	
	@Resource
	UserService userService;
	
	private String SESSION_SECURITY_CODE="SESSION_SECURITY_CODE";
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 */
	@RequestMapping("/getSecurityCode.action")
	public void getSecurityCode(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,Model model) {
		String securityCode = SecurityCode.getSecurityCode();
		

		this.imageStream = SecurityImageUtil
				.getImageAsInputStream(securityCode);
		session.setAttribute(SESSION_SECURITY_CODE, securityCode);
		response.setContentType("image/jpeg");
		response.setBufferSize(2048);
		ServletOutputStream outputStream=null;
		byte[] data = new byte[1024];
		int len=-1;
		try {
			outputStream = response.getOutputStream();
			while((len=(imageStream.read(data)))!=-1){
				outputStream.write(data);
			}
			outputStream.flush();
			outputStream.close();
			imageStream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取手机号信息
	 * @return
	 */
	@RequestMapping(value="/getMobileInfo.action",produces="application/json")
	@ResponseBody
	public String getMobileInfo() {
		this.result = new ResultBaseDO();
		if (MobileUtil.isNotMobileNO(this.mobile)) {
			this.result.addError("号码为空。");
			return "success";
		}
		Map map = new HashMap();
		map.put("type", Integer.valueOf(MobileUtil.checkType(this.mobile)));
		map.put("location", MobileUtil.getAddress(this.mobile));
		this.result.setModule(map);
		return "success";
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public String userListByLevel() {
//		this.result = new ResultBaseDO();
//		Users currentUser = UserService.getCurrentUser();
//		if (currentUser == null) {
//			this.result.addError("请登录");
//			return "success";
//		}
//		if (currentUser.getType() == 1) {
//			QueryUserDO queryUserDO = new QueryUserDO();
//			queryUserDO.setAgent("admin");
//			this.userList = this.userService.queryList(queryUserDO);
//		} else {
//			this.userList = new ArrayList();
//			this.userList.add(currentUser);
//		}
//		List parentList = this.userList;
//		if (DefineCollectionUtil.isNotEmpty(parentList)) {
//			parentList = enrichAgentList(parentList);
//		}
//		this.result.setModule(this.userList);
//		return "success";
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private List<Users> enrichAgentList(List<Users> parentAgentList) {
//		if (DefineCollectionUtil.isEmpty(parentAgentList)) {
//			return null;
//		}
//		List accoutList = new ArrayList();
//		Map parentMap = new HashMap();
//		for (Users user : parentAgentList) {
//			user.setUserList(null);
//			accoutList.add(user.getUsername());
//			parentMap.put(user.getUsername(), user);
//		}
//		QueryUserDO queryUsersDO = new QueryUserDO();
//		queryUsersDO.setAgentList(accoutList);
//		List<Users> list = this.userService.queryList(queryUsersDO);
//		for (Users user : list) {
//			Users agent = (Users) parentMap.get(user.getAgent());
//			List tempList = agent.getUserList();
//			if (tempList == null) {
//				tempList = new ArrayList();
//				agent.setUserList(tempList);
//			}
//			tempList.add(user);
//		}
//		return list;
//	}
//
	/**
	 * 退出系统
	 * @param session
	 * @return
	 */
	@RequestMapping("/exit.action")
	public String exit(HttpSession session) {
		session.setAttribute("user", null);
		return "index";
	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public String changePassword() {
//		this.result = new ResultBaseDO();
//		Users currentUser = UserService.getCurrentUser();
//		if (currentUser == null) {
//			this.result.addError("请登录");
//			return "success";
//		}
//		if (!this.newPassword.equals(this.repeatPassword)) {
//			this.result.addError("两次密码不一致");
//			return "success";
//		}
//		if (!currentUser.getPassword().equals(this.password)) {
//			this.result.addError("原密码不正确");
//			return "success";
//		}
//		currentUser.setPassword(this.newPassword);
//		this.userService.saveOrUpdate(currentUser);
//
//		Map session = ActionContext.getContext().getSession();
//		session.put("user", currentUser);
//
//		return "success";
//	}
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param securityCode
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login.action")
	public String login(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("securityCode")String securityCode
			,HttpSession session) {
		if (StringUtils.isEmpty(username)) {
			session.setAttribute("error_msg", "请输入用户名");
			return "index";
		}
		if (StringUtils.isEmpty(password)) {
			session.setAttribute("error_msg", "请输入密码");
			return "index";
		}	
		if (StringUtils.isEmpty(securityCode)) {
			session.setAttribute("error_msg", "请输入验证码！");
			return "index";
		}
		String code = (String) session.getAttribute(SESSION_SECURITY_CODE);
		if ((code == null)
				|| (!code.toLowerCase().equals(securityCode.toLowerCase()))) {
			session.setAttribute("error_msg", "验证码不正确！");
			return "index";
		}
		Users users =userService.findByUsernameAndPassword(username, password);
		if (users==null) {
			session.setAttribute("error_msg", "用户名或密码错误");
			return "index";
		}
		session.setAttribute("user", users);
		if (users.getType() == 1) {
			return "redirect:/admin/layout/shouye.jsp";
		}
		if (users.getType() == 2) {
			return "redirect:/agent/layout/shouye.jsp";
		}
		return "success";
	}
}
