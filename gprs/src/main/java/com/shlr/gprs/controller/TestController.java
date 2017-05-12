package com.shlr.gprs.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.shlr.gprs.utils.okhttp.HttpUtils;

/**
 * @author Administrator
 */
@Controller
public class TestController {
	
	@RequestMapping(value="/test")
	public String test(HttpServletRequest request,Model model){
		String readContent = HttpUtils.readContent(request);
		System.out.println(readContent);
		model.addAttribute("packageObj", "123");
		return "success";
	}
}
