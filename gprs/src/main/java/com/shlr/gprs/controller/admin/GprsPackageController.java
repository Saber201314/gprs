package com.shlr.gprs.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
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
		createCriteria.andEqualTo("status", 0);
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
	@ResponseBody
	public String addPackage(HttpSession session,
			@RequestParam(value="name")String name,
			@RequestParam(value="alias")String alias,
			@RequestParam(value="amount")Integer amount,
			@RequestParam(value="money")Double money,
			@RequestParam(value="type")Integer type,
			@RequestParam(value="locationType")Integer locationType,
			@RequestParam(value="all",required=false)String all,
			@RequestParam(value="provinces")List<String> provinces,
			@RequestParam(value="memo",required=false)String memo){
		JSONObject result=new JSONObject();
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			result.put("success", false);
			result.put("msg", "请登录");
			return result.toJSONString();
		}
		GprsPackage gprsPackage=new GprsPackage();
		gprsPackage.setName(name);
		gprsPackage.setAlias(alias);
		gprsPackage.setAmount(amount);
		gprsPackage.setMoney(money);
		gprsPackage.setType(type);
		gprsPackage.setLocationType(locationType);
		if(!StringUtils.isEmpty(all)&&"全国".equals(all)){
			gprsPackage.setLocations(all);
		}else{
			StringBuilder builder=new StringBuilder();
			for (String string : provinces) {
				builder.append(string).append(",");
			}
			gprsPackage.setLocations(builder.toString());
		}
		gprsPackage.setMemo(memo);
		Integer add = gprsPackageService.add(gprsPackage);
		if (add == 1) {
			result.put("success", true);
			result.put("msg", "添加成功");
		}else{
			result.put("success", false);
			result.put("msg", "添加失败");
		}
		return result.toJSONString();
	}
	@RequestMapping("/delPackage.action")
	@ResponseBody
	public String delPackage(HttpSession session,@RequestParam(value="ids[]")List<Integer> ids){
		JSONObject result=new JSONObject();
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			result.put("success", false);
			result.put("msg", "请登录");
			return result.toJSONString();
		}
		Integer del=0;
		if( !ids.isEmpty()){
			del = gprsPackageService.del(ids);
		}
		if (del > 0) {
			result.put("success", true);
			result.put("msg", "删除成功");
		}else{
			result.put("success", false);
			result.put("msg", "删除失败");
		}
		return result.toJSONString();
	}
}
