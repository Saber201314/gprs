package com.shlr.gprs.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.services.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class GprsPackageController {
	@Resource
	UserService userService;
	@Resource
	GprsPackageService gprsPackageService;
	
	@RequestMapping(value="/packageList.action")
	@ResponseBody
	public String packageList(HttpSession session,
			@RequestParam(value="pageNo",required=false,defaultValue="1")String pageNo,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="alias",required=false)String alias,
			@RequestParam(value="amount",required=false)String amount,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="locationType",required=false)String locationType){
		JSONObject result=new JSONObject();
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			return "redirct:/login.jsp";
		}
		Example example=new Example(GprsPackage.class, false, true);
		Criteria createCriteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(name)){
			createCriteria.andLike("name", "%"+name+"%");
		}
		if(!StringUtils.isEmpty(alias)){
			createCriteria.andLike("alias", "%"+alias+"%");
		}
		if(!StringUtils.isEmpty(amount)){
			createCriteria.andEqualTo("amount", amount);
		}
		if(!StringUtils.isEmpty(type)&&!"0".equals(type)){
			createCriteria.andEqualTo("type", type);
		}
		if(!StringUtils.isEmpty(locationType)&&!"0".equals(locationType)){
			createCriteria.andEqualTo("locationType", locationType);
		}
		example.setOrderByClause(" option_time desc");
		List<GprsPackage> listByPage = gprsPackageService.listByPage(example, Integer.valueOf(pageNo));
		Page<GprsPackage> page=(Page<GprsPackage>) listByPage;
		result.put("list", listByPage);
		result.put("total", page.getTotal());
		result.put("pages", page.getPages());
		result.put("pageno", page.getPageNum());
		return result.toJSONString();
	}
	@RequestMapping("/addPackage.action")
	public String addPackage(){
		return null;
		
	}
}
