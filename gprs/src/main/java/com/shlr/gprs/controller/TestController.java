package com.shlr.gprs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.Page;

/**
 * @author Administrator
 */
@Controller
public class TestController {
	@RequestMapping(value="/test")
	public String test(Model model){
		
		Page<Object> page=new Page<Object>();
		page.setTotal(100L);
		model.addAttribute("page", page);
		
		
		return "success";
	}
}
