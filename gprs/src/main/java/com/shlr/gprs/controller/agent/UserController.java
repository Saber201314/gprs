package com.shlr.gprs.controller.agent;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */

@Controller
@RequestMapping("/agent")
public class UserController {
	@Resource
	UserService userService;
	
	@RequestMapping(value="")
	public String agentList(HttpSession session,
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNo,
			@RequestParam(value="agent",required=false)String agent){
		Users currentUser = userService.getCurrentUser(session);
		if(currentUser == null){
			return "/index.jsp";		
		}
		int type = currentUser.getType();
		//如果不是系统管理员或者不是总代理
		if (type != 1 && type != 2) {
			return "/index.jsp";
		}
		Example example=new Example(Users.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (StringUtils.isEmpty(agent) && type != 1) {
			createCriteria.andEqualTo("agent", currentUser.getUsername());
		}
		List<Users> listByExampleAndPage = userService.listByExampleAndPage(example, Integer.valueOf(pageNo));
		
		
		
		return null;
	}
}
