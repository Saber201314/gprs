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
import org.springframework.web.context.ContextLoaderListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.constants.Const;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.JSONUtils;
import com.shlr.gprs.utils.MobileUtil;
import com.shlr.gprs.utils.SecurityCode;
import com.shlr.gprs.utils.SecurityImageUtil;
import com.shlr.gprs.vo.ChargeResponsVO;
import com.suwoit.json.util.StringUtils;


/**
* @author xucong
* @version 创建时间：2017年4月2日 下午8:30:23
* 入口控制器
*/
@Controller
public class PortalController {
	
	private String mobile;
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
	}/**
	 * 登录
	 * @param username
	 * @param password
	 * @param securityCode
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login.action")
	@ResponseBody
	public String login(@RequestParam("username")String username,
			@RequestParam("password")String password,
			@RequestParam("securityCode")String securityCode
			,HttpSession session) {
		JSONObject result=new JSONObject();
		if (StringUtils.isEmpty(username)) {
			
			result.put("success", false);
			result.put("error_msg", "请输入用户名");
			return result.toJSONString();
		}
		if (StringUtils.isEmpty(password)) {
			result.put("success", false);
			result.put("error_msg", "请输入密码");
			return result.toJSONString();
		}	
		if (StringUtils.isEmpty(securityCode)) {
			result.put("success", false);
			result.put("error_msg", "请输入验证码");
			return result.toJSONString();
		}
		String code = (String) session.getAttribute(SESSION_SECURITY_CODE);
		if ((code == null)
				|| (!code.toLowerCase().equals(securityCode.toLowerCase()))) {
			result.put("success", false);
			result.put("error_msg", "验证码不正确");
			return result.toJSONString();
		}
		Users users =userService.findByUsernameAndPassword(username, password);
		if (users==null) {
			result.put("success", false);
			result.put("error_msg", "用户名或密码错误");
			return result.toJSONString();
		}
		session.setAttribute("user", users);
		
		if (users.getType() == 1) {
			result.put("success", true);
			result.put("url", "/n_index.jsp");
			return result.toJSONString();
		}
		if (users.getType() == 2) {
			result.put("success", true);
			result.put("url", "/n_index.jsp");
			return result.toJSONString();
		}
		return result.toJSONString();
	}
	/**
	 * 获取手机号信息
	 * @return
	 */
	@RequestMapping(value="/getMobileInfo.action",produces="application/json")
	@ResponseBody
	public String getMobileInfo() {
		JSONObject result = new JSONObject();
		if (MobileUtil.isNotMobileNO(this.mobile)) {
			result.put("success",false);
			result.put("msg","号码为空");
			return result.toJSONString();
		}
		result.put("type", Integer.valueOf(MobileUtil.checkType(this.mobile)));
		result.put("location", MobileUtil.getAddress(this.mobile));
		return JSONUtils.toJsonString(result);
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
	@ResponseBody
	public String exit(HttpSession session) {
		session.setAttribute("user", null);
		JSONObject result=new JSONObject();
		result.put("url", "login.jsp");
		return result.toJSONString();
	}
	
	@RequestMapping(value="/admin/layout/changePassword.action")
	@ResponseBody
	public String changePassword(HttpServletResponse response, HttpSession session,
			@RequestParam("password")String password,
			@RequestParam("newPassword")String newPassword,
			@RequestParam("repeatPassword")String repeatPassword) throws IOException {
		Users currentUser = userService.getCurrentUser(session);
		ChargeResponsVO result=new ChargeResponsVO();
		if (currentUser == null) {
			result.setSuccess(false);
			result.setMsg("请登录");
			return JSONUtils.toJsonString(result);
		}
		if (!currentUser.getPassword().equals(password)) {
			result.setSuccess(false);
			result.setMsg("原密码不正确");
			return JSONUtils.toJsonString(result);
		}
		
		if (!newPassword.equals(repeatPassword)) {
			result.setSuccess(false);
			result.setMsg("两次密码不一致");
			return JSONUtils.toJsonString(result);
		}
		
		currentUser.setPassword(newPassword);
		Integer updateUserByPK = userService.updateUserByPK(currentUser);
		
		if (updateUserByPK > 0) {
			session.setAttribute("user", currentUser);
			result.setSuccess(true);
			result.setMsg("修改成功");
		}
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/api_status")
	@ResponseBody
	public String setSwitchStatus(@RequestParam(value="status",required=false)String switchstatus){
		String result="";
		if (!StringUtils.isEmpty(switchstatus)) {
			if (Const.isApiSwitch()) {
				Const.setApiSwitch(false);
			}else{
				Const.setApiSwitch(true);
			}
		}
		if (Const.isApiSwitch()) {
			result= " 接口状态 ：开启"; 
		}else{
			result= " 接口状态 ：关闭"; 
		}
		return result;
	}
}
