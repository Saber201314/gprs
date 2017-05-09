package com.shlr.gprs.manager.charge.template;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.shlr.gprs.cache.ChannelTemplateCache;
import com.shlr.gprs.domain.ChannelLog;
import com.shlr.gprs.domain.ChannelTemplate;
import com.shlr.gprs.domain.ChargeOrder;
import com.shlr.gprs.manager.ChargeManager;
import com.shlr.gprs.manager.ThreadManager;
import com.shlr.gprs.manager.charge.ChargeTemplate;
import com.shlr.gprs.utils.XMLParser;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.shlr.gprs.vo.ResultBaseVO;

/**
* @author xucong
* @version 创建时间：2017年4月30日 下午7:59:10
* 
*/
public class NeiMengGuChargeTemplate extends ChargeTemplate{
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static long tokenValidRangTime = 0L;
	private static String token = "";
	
	public NeiMengGuChargeTemplate(int templateId, String templateName,
			String account, String password, String key) {
		super(templateId, templateName, account, password, key);
	}
	
	public String SHA256Hex(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(decript.getBytes());
			byte[] messageDigest = digest.digest();

			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return "";
	}	
	
	public String getRequestTokenXml(String appKey,String appSecret){	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); 
		StringBuffer sb = new StringBuffer();
		sb.append("<Request>");
		String strDate = df.format(new Date());
		sb.append(" <Datetime>"+strDate+"</Datetime>");
		sb.append(" <Authorization>");
		sb.append("  <AppKey>"+appKey+"</AppKey>");		
		sb.append("  <Sign>"+SHA256Hex(appKey+strDate+appSecret)+"</Sign>");					
		sb.append(" </Authorization>");	
		sb.append("</Request>");	
		
		return sb.toString();
	}
	
	public String getRequestChargeXml(String orderId,String mobile,String packageCode){		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); 
		StringBuffer sb = new StringBuffer();
		sb.append("<Request>");
		String strDate = df.format(new Date());
		sb.append(" <Datetime>"+strDate+"</Datetime>");
		sb.append(" <ChargeData>");
		sb.append("  <Mobile>" + mobile + "</Mobile>");		
		sb.append("  <ProductId>" + packageCode + "</ProductId>");	
		sb.append("  <SerialNum>" + orderId + "</SerialNum>");	
		sb.append(" </ChargeData>");	
		sb.append("</Request>");
		return sb.toString();
	}
		
	public String doPostByXml(String url, String xml, int connectTimeout, int readTimeout) throws Exception{
	    String ctype = "text/xml;charset=utf-8";
	    byte[] content = new byte[0];
	    if (xml != null) {
	      content = xml.getBytes("utf-8");
	    }
	    //WebUtils.doPost(url, ctype, content, connectTimeout, readTimeout)
	    return null;
	}
	
	public String doAuthorizationPostByXml(String url, String xml,
			int connectTimeout, int readTimeout, String prepend,String afterward)
			throws IOException {
		String ctype = "text/xml;charset=UTF-8";
		byte[] content = new byte[0];
		if (xml != null) {
			content = xml.getBytes("utf-8");
		}
		//WebUtils.doAuthorizationPost(url, ctype, content, connectTimeout, readTimeout,prepend,afterward,0)
		return null;
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResultBaseVO<String> charge(ChargeOrder chargeOrder) {
		ResultBaseVO result = new ResultBaseVO();

		String packageCode = getPackageCode(chargeOrder);

		if (StringUtils.isEmpty(packageCode)) {
			result.addError("没有流量包编码");
			return result;
		}

		String response = null;
		
	    long curDateTime = System.currentTimeMillis();
	    //如果token不等于空 或者 有效时间范围超过25分钟
	    if(StringUtils.isEmpty(token) || (tokenValidRangTime !=0 && (curDateTime-tokenValidRangTime)/(1000 * 60)>25)){
			try {
				response = doPostByXml(
						"http://www.nm.10086.cn/flowplat/auth.html",
						getRequestTokenXml(this.account,this.key), 100000,
						100000);
				
				Map tokenMap = XMLParser.Dom2Map(response);
				response = String.valueOf(tokenMap.get("Authorization"));
				int leftPos = response.lastIndexOf("=");
				token = response.substring(leftPos + 1,response.length() - 1);
				tokenValidRangTime = curDateTime;			
			} catch (Exception e) {
				token = "";
		        logger.error("内蒙古移动接口：获取token异常", e);
		        result.addError("获取token异常");
		        return result;
			}						
	    }
	    
		ChannelLog channelLog = new ChannelLog();
		channelLog.setTemplateId(this.templateId);
		channelLog.setTemplateName(this.templateName);
		channelLog.setMobile(chargeOrder.getMobile());
//		channelLog.setOrderId(chargeOrder.getId());
		try {
//			if (ChargeManager.analysisOrderIdExisted(chargeOrder.getId())) {
//				return result;
//			}
			String reqChargeXml = getRequestChargeXml(String.valueOf(chargeOrder.getId()),chargeOrder.getMobile(),packageCode);	
			
			response = doAuthorizationPostByXml("http://www.nm.10086.cn/flowplat/boss/charge.html",reqChargeXml,
					100000, 100000,token,SHA256Hex(reqChargeXml+this.key));
			
			if (StringUtils.isEmpty(response)) {
				result.setModule(chargeOrder.getId());
				result.setOrderId(chargeOrder.getAgentorderId());
				return result;
			}
			
			Map retMap = XMLParser.Dom2Map(response);				
			if(!CollectionUtils.isEmpty(retMap)){
				channelLog.setResponse(response);
//				channelLogService.saveOrUpdate(channelLog);	
				response = String.valueOf(retMap.get("ChargeData"));
				int leftPos = response.lastIndexOf("=");
				response = response.substring(leftPos + 1,response.length()-1);	
				
				result.setModule(response);
				result.setOrderId(chargeOrder.getAgentorderId());								
			}else {
				result.addError("通道返回错误，请联系客服人员！");
			}						
		} catch (IOException e) {
			logger.error("内蒙古移动接口：充值接口异常", e);
			/*result.setModule(String.valueOf(chargeOrder.getId()));
			result.setOrderId(chargeOrder.getOrderId());
			channelLogService.saveOrUpdate(channelLog);
			return result;	*/
			channelLog.setResponse("接口异常");
			result.addError("接口异常");
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public ResultBaseVO<Object> getChargeStatus() {
		ThreadManager.getInstance().execute(new Runnable() {
			public void run() {
				ChannelTemplate template = ChannelTemplateCache.identityMap
						.get("NeiMengGuChargeTemplate");				
				List<Map<String,Object>> list = null;//chargeOrderService.getUnProcessOrder(template.getId());
				if(!CollectionUtils.isEmpty(list)){
					String response = null;					
					for(Map<String,Object> map : list){					
					    long curDateTime = System.currentTimeMillis();
					    //如果token不等于空 或者 有效时间范围超过25分钟
					    if(StringUtils.isEmpty(token) || (tokenValidRangTime !=0 && (curDateTime-tokenValidRangTime)/(1000 * 60)>25)){
							try {
								response = doPostByXml(
										"http://www.nm.10086.cn/flowplat/auth.html",
										getRequestTokenXml(template.getAccount(),template.getSign()), 100000,
										100000);
								
								Map tokenMap = XMLParser.Dom2Map(response);
								response = String.valueOf(tokenMap.get("Authorization"));
								int leftPos = response.lastIndexOf("=");
								token = response.substring(leftPos + 1,response.length() - 1);
								tokenValidRangTime = curDateTime;			
							} catch (Exception e) {
								token = "";
						        logger.error("内蒙古移动接口：获取token异常", e);
							}						
					    }
					    try {
							  Thread.sleep(1000);	
					    	  String orderId = String.valueOf(map.get("charge_task_id"));
//						      response = WebUtils.doAuthorizationGet("http://www.nm.10086.cn/flowplat/chargeRecords/" + orderId + ".html",
//						    		  "",token,SHA256Hex(template.getSign()));						      
						      if(StringUtils.isEmpty(response)){
						    	  return;
						      }	
						      
						      Document document = DocumentHelper.parseText(response);
							    Element Allmember= document.getRootElement();
							    Iterator  Elements = Allmember.elementIterator(); 
							    while(Elements.hasNext()){  
							    	Element records = (Element)Elements.next();
							    	if("Records".equals(records.getName())){
							    		Iterator Elements1 = records.elementIterator("Record");
							    	    while(Elements1.hasNext()){  
							    	    	Element record = (Element)Elements1.next();
							    	    	String status = record.elementText("Status");
							    	    	String desc = record.elementText("Description");
											if ("3".equals(status) || "4".equals(status)) {
												ChargeManager.getInstance().updateResult(113, orderId,"3".equals(status), desc);
											}
							    	    }  		
							    	}
							    }						     			    
						} catch (Exception e) {
						      logger.error("内蒙古移动接口：获取数据失败", e);
					   }									
					}			    			
				}												
			}
		});	
		return null;
	}
	
    /**
     * 查询余额
     */
	public ResultBaseVO<Object> getBalance() {
		return null;
	}
}
