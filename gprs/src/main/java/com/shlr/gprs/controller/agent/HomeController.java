package com.shlr.gprs.controller.agent;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.UserService;

@Controller
@RequestMapping("/agent")
public class HomeController {
	@Resource
	UserService userService;
	
	@RequestMapping("/home.action")
	public String home(HttpSession session,Model model){
		Users currentUser = userService.getCurrentUser(session);
		currentUser.setWhiteIp(currentUser.getWhiteIp().replace("%0D%0A", ","));
		model.addAttribute("user", currentUser);
		return "agent/home/home";
	}
}
