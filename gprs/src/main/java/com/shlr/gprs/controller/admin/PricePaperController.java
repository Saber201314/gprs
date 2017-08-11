package com.shlr.gprs.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.cache.ChannelCache;
import com.shlr.gprs.cache.PricePaperCache;
import com.shlr.gprs.domain.Channel;
import com.shlr.gprs.domain.GprsPackage;
import com.shlr.gprs.domain.PricePaper;
import com.shlr.gprs.services.GprsPackageService;
import com.shlr.gprs.services.PricePaperService;
import com.shlr.gprs.utils.JSONUtils;
import com.xiaoleilu.hutool.util.StrUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class PricePaperController {
	
	@Resource
	PricePaperService pricePaperService;
	@Resource
	GprsPackageService gprsPackageService;
	
	@RequestMapping("/getPricePaperList.action")
	@ResponseBody
	public String getPricePaperList(
			@RequestParam(defaultValue="1",required=false)String pageNo,
			@RequestParam(required=false)String name,
			@RequestParam(required=false)String alias){
		JSONObject result = new JSONObject();
		Example example = new Example(PricePaper.class);
		Criteria createCriteria = example.createCriteria();
		if(StrUtil.isNotBlank(name)){
			createCriteria.andLike("name", "%"+name+"%");
		}
		if(StrUtil.isNotBlank(alias)){
			createCriteria.andLike("alias", "%"+alias+"%");
		}
		example.setOrderByClause("option_time desc");
		List<PricePaper> listByExampleAndPage = pricePaperService.listByExampleAndPage(example, Integer.valueOf(pageNo));
		Page<PricePaper> page = (Page<PricePaper>) listByExampleAndPage;
		result.put("list", listByExampleAndPage);
		result.put("total", page.getTotal());
		result.put("pages", page.getPages());
		result.put("pageno", page.getPageNum());
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/editPricePaper.action")
	public String editPricePaper(Integer id,Model model){
		
		PricePaper findById = pricePaperService.findById(id);
		model.addAttribute("pricepaper", findById);
		return "admin/costmanage/addPricePaper";
	}
	
	@RequestMapping(value="/savePricePaper.action")
	@ResponseBody
	public String saveChannel(@ModelAttribute PricePaper pricePaper){
		JSONObject result = new JSONObject();
		Integer num = pricePaperService.saveOrUpdate(pricePaper);
		if(num > 0){
			PricePaperCache.getInstance().updateCache(pricePaper);
			result.put("success", true);
			result.put("msg", "保存成功");
		}else{
			result.put("success", false);
			result.put("msg", "保存失败");
		}
		return result.toJSONString();
	}
	
	@RequestMapping("/showPircePaperInfo.action")
	@ResponseBody
	public String showPircePaperInfo(Integer id){
		PricePaper pricePaper = PricePaperCache.getInstance().idMap.get(id);
		List<JSONObject> priceList = new ArrayList<JSONObject>();
		
		String items = pricePaper.getItems();
		String[] split = items.split(",");
		for (String item : split) {
			if(StrUtil.isBlank(item)){
				continue;
			}
			JSONObject packObj = new JSONObject();
			String[] pPack = item.split(":");
			String packId = pPack[0];
			String outDiscount = pPack[1];
			String payBill = pPack[2];
			GprsPackage gprsPack = gprsPackageService.findById(Integer.valueOf(packId));
			
			packObj.put("id", gprsPack.getId());
			packObj.put("name", gprsPack.getName());
			packObj.put("outDiscount", outDiscount);
			packObj.put("payBill", payBill);
			packObj.put("", "");
			
			Collection<Channel> values = ChannelCache.getInstance().idMap.values();
			for (Channel channel : values) {
				String packages = channel.getPackages();
				String[] cPack = packages.split(",");
				for (String string : cPack) {
					if(StrUtil.isBlank(string)){
						continue;
					}
					String[] cItem = string.split(":");
					String cPackId = cItem[0];
					String inDiscount = cItem[1];
					String priority = cItem[2];
					if(cPackId.equals(packId)){
						packObj.put("inDiscount", inDiscount);
						packObj.put("channelName", channel.getName());
					}
				}
			}
			priceList.add(packObj);
		}
		return JSONUtils.toJsonString(priceList);
	}
}
