package com.shlr.gprs.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.services.ChannelService;
import com.shlr.gprs.services.ChannelTemplateCodeService;
import com.shlr.gprs.services.ChannelTemplateService;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.services.UserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Controller
@RequestMapping("/admin")
public class ChannelController {
	
	Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Resource
	UserService userService;
	@Resource
	ChannelService channelService;
	@Resource
	GprsPackageService gprsPackageService;
	@Resource
	ChannelTemplateService channelTemplateService;
	@Resource
	ChannelTemplateCodeService channelTemplateCodeService;
	
	/**
	 * 通道列表
	 * @param session
	 * @param pageNo
	 * @param name
	 * @param alias
	 * @return
	 */
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
	/**
	 * 通道模板列表
	 * @param session
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/query/channelTemplateList.action")
	@ResponseBody
	public String channelTemplateList(HttpSession session,
			@RequestParam(value="name",required = false)String name){
		
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		List<ChannelTemplate> list = channelTemplateService.list();
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
	/**
	 * 流量包编码列表
	 * @param session
	 * @param templateId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/query/channelTemplateCodeList.action")
	public String channelTemplateCodeList(HttpSession session,
			@RequestParam(value="templateId")Integer templateId,Model model){
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		Example example=new Example(ChannelTemplateCode.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("templateId", templateId);
		List<ChannelTemplateCode> list = channelTemplateCodeService.listByTemplateId(example);
		model.addAttribute("list", list);
		model.addAttribute("templateId", templateId);
		return "admin/channel/channelTemplateCode";
	}
	/**
	 * 添加流量包编码
	 * @param session
	 * @param templateId
	 * @param model
	 * @return
	 */
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
	/**
	 * 显示通道信息
	 * @param session
	 * @param cid
	 * @return
	 */
	@RequestMapping("/showSingleChannelInfo.action")
	@ResponseBody
	public String showSingleChannelInfo(HttpSession session,@RequestParam(value="id")Integer cid){
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		Channel channel = this.channelService.findById(cid);
		String packCode = channel.getPackages();
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		if(!StringUtils.isEmpty(packCode)){		
			String[] packageArr = packCode.split(",");
			if(packageArr.length>0){
				for(int i = 0;i<packageArr.length;i++){
					Map<String,Object> map = new HashMap<String,Object>();					
					String pack = packageArr[i];
					int firstLen = pack.indexOf(":");
					map.put("id", pack.substring(0, firstLen));
					int lastLen = pack.lastIndexOf(":");					
					map.put("level", pack.substring(lastLen + 1, pack.length()));					
					map.put("discount", pack.substring(firstLen + 1,lastLen));					
					mapList.add(map);
				}				
			}
		}
		List<Map<String,Object>> channelPackgeList = new ArrayList<Map<String,Object>>();
		
		List<GprsPackage> tempPackageList = gprsPackageService.listAll();
		if(!CollectionUtils.isEmpty(mapList)){
			if(!CollectionUtils.isEmpty(tempPackageList)){				
				for(GprsPackage gprsPackage : tempPackageList){
					int packageId = gprsPackage.getId();
					for(Map<String,Object> map : mapList){
						if(packageId == Integer.valueOf(String.valueOf(map.get("id")))){
							map.put("name", gprsPackage.getName());
							Double actualPrice = gprsPackage.getMoney()*Double.valueOf(String.valueOf(map.get("discount")))/10;
							map.put("price", gprsPackage.getMoney());
							map.put("actualPrice", String.valueOf(Math.round(actualPrice*100)/100.0));							
							map.put("channelName",channel.getName());
							channelPackgeList.add(map);
						}
					}
				}			
			}			
		}
		return JSON.toJSONString(channelPackgeList);
	}
	@RequestMapping(value="/editChannel.action")
	public String editChannel(Integer channelId,Model model){
		
		if(channelId !=null && channelId > 0){
			Channel channel = channelService.findById(channelId);
			if(channel != null){
				List<ChannelTemplate> list = channelTemplateService.list();
				model.addAttribute("channel", channel);
				model.addAttribute("templateList", list);
			}
		}
		return "admin/channel/addChannel";
		
	}
	@RequestMapping(value="/saveChannel.action")
	@ResponseBody
	public String saveChannel(@ModelAttribute Channel channel){
		JSONObject result = new JSONObject();
		
		if(channel.getId() > 0){
			Integer update = channelService.update(channel);
			if(update > 0){
				result.put("success", true);
				result.put("msg", "保存成功");
			}else{
				result.put("success", false);
				result.put("msg", "保存失败");
			}
		}else{
			result.put("success", false);
			result.put("msg", "保存失败");
		}
		return result.toJSONString();
	}
	
	/**
	 * 编辑通道模板
	 * @param session
	 * @param cid
	 * @param model
	 * @return
	 */
	@RequestMapping("/editChannelTemplate.action")
	public String editChannelTemplate(HttpSession session,@RequestParam(value="id")Integer cid,
			Model model){
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		ChannelTemplate findById = channelTemplateService.findById(cid);
		model.addAttribute("template", findById);
		return "admin/channel/editChannelTemplate";
	}
	@RequestMapping("/saveChannelTemplate.action")
	@ResponseBody
	public String saveChannelTemplate(HttpSession session,@ModelAttribute ChannelTemplate template){
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null; 
		}
		JSONObject result=new JSONObject();
		if (template.getId() > 0) {
			
			Integer update=0;
			update = channelTemplateService.update(template);
			if(update > 0){
				result.put("success", true);
				result.put("msg", "保存成功");
			}else{
				result.put("success", false);
				result.put("msg", "保存失败");
			}
		}else{
			result.put("success", false);
			result.put("msg", "保存失败");
		}
		return result.toJSONString();
	}
	@RequestMapping("/addTemplateCode.action")
	@ResponseBody
	public String addTemplateCode(HttpSession session,Integer templateId,Integer type,
			Integer locationType,String location,Integer amount,String code){
		Users currentUser = userService.getCurrentUser(session);
		if (currentUser == null || currentUser.getType() != 1) {
			return null;
		}
		ChannelTemplateCode channelTemplateCode=new ChannelTemplateCode();
		channelTemplateCode.setTemplateId(templateId);
		channelTemplateCode.setType(type);
		channelTemplateCode.setRange(locationType);
		channelTemplateCode.setLocation(location);
		channelTemplateCode.setAmount(amount);
		channelTemplateCode.setCode(code);
		Integer save = channelTemplateCodeService.save(channelTemplateCode);
		JSONObject result=new JSONObject();
		if (save>0) {
			result.put("success", true);
			result.put("msg", "添加成功");
		}else{
			result.put("success", false);
			result.put("msg", "添加失败");
		}
		return result.toJSONString();
	}
	@RequestMapping("/delTemplateCode.action")
	@ResponseBody
	public String delTemplateCode(HttpSession session,@RequestParam(value="ids[]")List<Integer> ids){
		JSONObject result=new JSONObject();
		Users currentUser = userService.getCurrentUser(session);
		if ((currentUser == null) || (currentUser.getType() != 1)) {
			result.put("success", false);
			result.put("msg", "请登录");
			return result.toJSONString();
		}
		Integer del=0;
		if( !ids.isEmpty()){
			del = channelTemplateCodeService.del(ids);
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
