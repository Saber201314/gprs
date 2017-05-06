package com.shlr.gprs.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.cache.ChannelTemplateCache;
import com.shlr.gprs.cache.ChannelTemplateCodeCache;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.ChannelTemplate;
import com.shlr.gprs.domain.ChannelTemplateCode;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.ChannelService;
import com.shlr.gprs.services.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Controller
@RequestMapping("/admin")
public class ChannelController {
	@Resource
	UserService userService;
	@Resource
	ChannelService channelService;
	
	@RequestMapping(value="/query/channelList.action")
	@ResponseBody
	public String channelList(HttpSession session,
			@RequestParam(value="pageNo",required = false,defaultValue = "1")String pageNo,
			@RequestParam(value="name",required = false)String name,
			@RequestParam(value="alias",required = false)String alias){
		
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		Example example=new Example(Channel.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(name)) {
			createCriteria.andLike("name", "%"+name+"%");
		}
		if (!StringUtils.isEmpty(alias)) {
			createCriteria.andLike("alias", "%"+alias+"%");
		}
		example.setOrderByClause(" id desc");
		List<Channel> listByExampleAndPage = channelService.listByExampleAndPage(example, Integer.valueOf(pageNo));
		Page<Channel> page=(Page<Channel>) listByExampleAndPage;
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		result.put("total", page.getTotal());
		result.put("pages", page.getPages());
		result.put("pageno", page.getPageNum());
		return result.toJSONString();
	}
	@RequestMapping(value="/query/channelTemplateList.action")
	@ResponseBody
	public String channelTemplateList(HttpSession session,
			@RequestParam(value="name",required = false)String name){
		
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		List<ChannelTemplate> list=new ArrayList<>();
		Collection<ChannelTemplate> values = ChannelTemplateCache.identityMap.values();
		for (ChannelTemplate channelTemplate : values) {
			if (!StringUtils.isEmpty(name)) {
				if (channelTemplate.getName().contains(name)) {
					list.add(channelTemplate);
				}
			}else{
				list.addAll(values);
				break;
			}
		}
		Collections.sort(list, new Comparator<ChannelTemplate>() {

			@Override
			public int compare(ChannelTemplate o1, ChannelTemplate o2) {
				// TODO Auto-generated method stub
				return o2.getId().compareTo( o1.getId()) ;
			}

		});
		JSONObject result=new JSONObject();
		result.put("list", list);
		return result.toJSONString();
	}
	@RequestMapping(value="/query/channelTemplateCodeList.action")
	public String channelTemplateCodeList(HttpSession session,
			@RequestParam(value="templateId")Integer templateId,Model model){
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		List<ChannelTemplateCode> list = ChannelTemplateCodeCache.codeMapByChannel.get(templateId);
		model.addAttribute("list", list);
		model.addAttribute("templateId", templateId);
		return "admin/channel/channelTemplateCode";
	}
	
	@RequestMapping(value="/query/publishChannelTemplateCode.action")
	public String publishChannelTemplateCode(HttpSession session,
			@RequestParam(value="templateId")Integer templateId,Model model){
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		List<ChannelTemplateCode> list = ChannelTemplateCodeCache.codeMapByChannel.get(templateId);
		model.addAttribute("templateId", templateId);
		return "admin/channel/addChannelTemplateCode";
	}
}
