package com.shlr.gprs.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.shlr.gprs.dao.PayLogMapper;
import com.shlr.gprs.domain.AgentChargeLog;
import com.shlr.gprs.domain.PricePaper;
import com.shlr.gprs.domain.Users;
import com.shlr.gprs.manager.PayManager;
import com.shlr.gprs.services.AgentChargeLogService;
import com.shlr.gprs.services.PricePaperService;
import com.shlr.gprs.services.UserService;
import com.shlr.gprs.utils.DateConvert;
import com.shlr.gprs.utils.JSONUtils;
import com.shlr.gprs.vo.ChargeOrBuckleVO;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.xiaoleilu.hutool.util.HexUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 */
@Controller
@RequestMapping(value="/admin")
public class UserController {
	@Resource
	UserService userService;
	@Resource
	AgentChargeLogService agentChargeLogService;
	@Resource
	PricePaperService pricePaperService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder){  
	    binder.registerCustomEditor(Date.class, new DateConvert());  
	}
	
	
	
	@RequestMapping("/addAgent.action")
	public String addAgent(Model model){
		List<PricePaper> listAll = pricePaperService.listAll();
		model.addAttribute("paperList", listAll);
		return "admin/agentmanage/addAgent";
	}
	
	@RequestMapping("/editAgent.action")
	public String editAgent(Integer userId,Model model){
		Users user = userService.findById(userId);
		List<PricePaper> listAll = pricePaperService.listAll();
		model.addAttribute("agent", user);
		model.addAttribute("paperList", listAll);
		return "admin/agentmanage/addAgent";
	}
	@RequestMapping("/saveAgent.action")
	@ResponseBody
	public String saveAgent(HttpSession session, @ModelAttribute Users user){
		JSONObject result = new JSONObject();
		Integer num = 0;
		
		user.setType(2);
		if(user.getId() != null && user.getId() > 0){
			num = userService.updateUserByPK(user);
		}else{
			Users currentUser = userService.getCurrentUser(session);
			user.setAgent(currentUser.getUsername());
			num = userService.add(user);
		}
		if(num > 0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		return result.toJSONString();
	}
	@RequestMapping("/checkUsername.action")
	@ResponseBody
	public String checkUsername(String username){
		JSONObject result = new JSONObject();
		boolean validateUsername = userService.validateUsername(username);
		if(validateUsername){
			result.put("success", false);
		}else{
			result.put("success", true);
		}
		return result.toJSONString();
	}
	@RequestMapping("/tochargeAgentBalance.action")
	public String tochargeAgentBalance(Integer userId,Model model){
		Users findById = userService.findById(userId);
		model.addAttribute("agent", findById);
		return "admin/agentmanage/chargeAgentBalance";
	}
	@RequestMapping("/chargeAgentBalance.action")
	@ResponseBody
	public String chargeAgentBalance(HttpSession session,
			AgentChargeLog agentChargeLog){
		JSONObject resut = new JSONObject();
		
		Users currentUser = userService.getCurrentUser(session);
		Users agent = userService.findById(agentChargeLog.getUserId());
		agentChargeLog.setAgent(agent.getAgent());
		agentChargeLog.setAccount(agent.getUsername());
		agentChargeLog.setOptionUser(currentUser.getUsername());
		
		ChargeOrBuckleVO chargeOrBuckleVO = new ChargeOrBuckleVO();
		chargeOrBuckleVO.setUserId(agent.getId());
		chargeOrBuckleVO.setAccount(agentChargeLog.getAccount());
		chargeOrBuckleVO.setAgent(agentChargeLog.getAgent());
		chargeOrBuckleVO.setMoney(agentChargeLog.getMoney());
		chargeOrBuckleVO.setType(1);
		chargeOrBuckleVO.setMemo("为代理商"+agentChargeLog.getAccount()+"冲扣值"+agentChargeLog.getMoney()+"元");
		PayManager.getInstance().addToPay(chargeOrBuckleVO);
		
		Integer add = agentChargeLogService.add(agentChargeLog);
		if(add > 0 ){
			resut.put("success", true);
			resut.put("money", agentChargeLog.getMoney());
		}else{
			resut.put("success", false);
		}
		return resut.toJSONString();
	}
	@RequestMapping("/genApiKey.action")
	@ResponseBody
	public String genApiKey(){
		JSONObject result = new JSONObject();
		
		byte[] encoded = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
		String apikey = HexUtil.encodeHexStr(encoded);
		if(!StringUtils.isEmpty(apikey)){
			result.put("success", true);
			result.put("key", apikey);
		}else{
			result.put("success", false);
		}
		return result.toJSONString();
	}
	
}
