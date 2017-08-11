package com.shlr.gprs.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.services.ChargeOrderService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.JSONUtils;
import com.shlr.gprs.vo.ChargeResponsVO;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin")
public class ChargeOrderController {
	
	
	@Resource
	UserService userService;
	@Resource
	ChargeOrderService chargeOrderService;
	
	/**
	 * 充值记录
	 * @param request
	 * @param response
	 * @param session
	 * @param pageNo
	 * @param account
	 * @param mobile
	 * @param location
	 * @param from
	 * @param to
	 * @param type
	 * @param amount
	 * @param locationType
	 * @param submitStatus
	 * @param submitChannel
	 * @param cacheFlag
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/chargeOrderList.action")
	@ResponseBody
	public String chargeOrderList(HttpServletRequest request, HttpSession session,
			@RequestParam(value="pageNo")Integer pageNo,
			@RequestParam(value="account",required=false)String account,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="location",required=false)String location,
			@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="amount",required=false)Integer amount,
			@RequestParam(value="rangeType",required=false)String rangeType,
			@RequestParam(value="chargeStatus",required=false)String chargeStatus,
			@RequestParam(value="submitChannel",required=false)String submitChannel) throws IOException{
		
		Example example = new Example(ChargeOrder.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(account)&&!"-1".equals(account)) {
			createCriteria.andEqualTo("account", account);
		}
		if (!StringUtils.isEmpty(mobile)) {
			createCriteria.andEqualTo("mobile", mobile);
		}
		if (!StringUtils.isEmpty(location)&&!"请选择".equals(location)) {
			createCriteria.andEqualTo("location", location);
		}
		if (!StringUtils.isEmpty(from)&&!StringUtils.isEmpty(to)) {
			createCriteria.andBetween("optionTime", from, to);
		}
		if (!StringUtils.isEmpty(type)&& 0 != type) {
			createCriteria.andEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(amount)&& 0 != amount) {
			createCriteria.andEqualTo("amount", amount);
		}
		if (!StringUtils.isEmpty(rangeType)&&!"-1".equals(rangeType)) {
			createCriteria.andEqualTo("rangeType", rangeType);
		}
		if (!StringUtils.isEmpty(chargeStatus)&&!"-1".equals(chargeStatus)) {
			createCriteria.andEqualTo("chargeStatus", chargeStatus);
		}
		if (!StringUtils.isEmpty(submitChannel)&&!"-1".equals(submitChannel)) {
			createCriteria.andEqualTo("submitChannel", submitChannel);
		}
		createCriteria.andEqualTo("cacheFlag", 0);
		createCriteria.andGreaterThan("chargeStatus", 0);
		example.setOrderByClause("submit_time desc");
		List<ChargeOrder> listByPage = chargeOrderService.listByExampleAndPage(example, pageNo);
		Page<ChargeOrder> page=(Page<ChargeOrder>) listByPage;
		JSONObject result=new JSONObject();
		result.put("pages", page.getPages());
		result.put("total", page.getTotal());
		result.put("pageno", page.getPageNum());
		result.put("list", listByPage);
		return JSONUtils.toJsonString(result);
	}
	
	@RequestMapping(value="/chargeOrderCacheList.action")
	@ResponseBody
	public String chargeOrderCacheList(HttpSession session,
			@RequestParam(value="pageNo",defaultValue="1")String pageNo,
			@RequestParam(value="account",required=false)String account,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="location",required=false)String location,
			@RequestParam(value="from",required=false)String from,
			@RequestParam(value="to",required=false)String to,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="amount",required=false)Integer amount,
			@RequestParam(value="rangeType",required=false)String rangeType,
			@RequestParam(value="submitStatus",required=false)String submitStatus,
			@RequestParam(value="submitChannel",required=false)String submitChannel,
			@RequestParam(value="cacheFlag",required=false)String cacheFlag) throws IOException{
		Example example = new Example(ChargeOrder.class,true,false);
		Criteria createCriteria = example.createCriteria();
		if (!StringUtils.isEmpty(mobile)) {
			createCriteria.andEqualTo("mobile", mobile);
		}
		if (!StringUtils.isEmpty(location)&&!"请选择".equals(location)) {
			createCriteria.andEqualTo("location", location);
		}
		if (!StringUtils.isEmpty(from)&&!StringUtils.isEmpty(to)) {
			createCriteria.andBetween("optionTime", from, to);
		}
		if (!StringUtils.isEmpty(type)&& 0 != type) {
			createCriteria.andEqualTo("type", type);
		}
		if (!StringUtils.isEmpty(amount)&& 0 != amount) {
			createCriteria.andEqualTo("amount", amount);
		}
		if (!StringUtils.isEmpty(rangeType)&&!"-1".equals(rangeType)) {
			createCriteria.andEqualTo("rangeType", rangeType);
		}
		createCriteria.andEqualTo("cacheFlag", 1);
		example.setOrderByClause(" option_time desc,id desc");
		List<ChargeOrder> listByExampleAndPage = chargeOrderService.listByExampleAndPage(example, Integer.valueOf(pageNo));
		Page<ChargeOrder> page = (Page<ChargeOrder>) listByExampleAndPage;
		JSONObject result=new JSONObject();
		result.put("list", listByExampleAndPage);
		result.put("pages", page.getPages());
		result.put("total", page.getTotal());
		result.put("pageno", page.getPageNum());
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/reMatchPackage.action")
	@ResponseBody
	public String reMatchPackage(@RequestParam(value="ids[]")List<Integer> ids){
		JSONObject result = new JSONObject();
		if(!CollectionUtils.isEmpty(ids)){
			Integer updateCacheFlag = chargeOrderService.updateCacheFlag(ids);
			if(updateCacheFlag == ids.size()){
				Example example = new Example(ChargeOrder.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andIn("id", ids);
				List<ChargeOrder> listByExample = chargeOrderService.listByExample(example);
				for (ChargeOrder chargeOrder : listByExample) {
					ChargeResponsVO chargeResult = ChargeManager.getInstance().charge(chargeOrder);
					if(!chargeResult.isSuccess()){
						chargeOrder.setCacheFlag(1);
						chargeOrder.setError(chargeResult.getMsg());
						chargeOrderService.saveOrUpdate(chargeOrder);
					}
				}
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		return JSONUtils.toJsonString(result);
	}
	@RequestMapping("/stopTask.action")
	@ResponseBody
	public String stopTask(@RequestParam(value="ids[]")List<Integer> ids){
		JSONObject result = new JSONObject();
		if(!CollectionUtils.isEmpty(ids)){
			Integer updateCacheFlag = chargeOrderService.updateCacheFlag(ids);
			if(updateCacheFlag == ids.size()){
				Example example = new Example(ChargeOrder.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andIn("id", ids);
				List<ChargeOrder> listByExample = chargeOrderService.listByExample(example);
				for (ChargeOrder chargeOrder : listByExample) {
					ChargeManager.getInstance().terminaOrder(chargeOrder);
				}
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		return JSONUtils.toJsonString(result);
	}
	public String statisticsChargeOrder(String start,String end,
			String agent,Integer channelId){
		
		
		
		return agent;
	}
}
