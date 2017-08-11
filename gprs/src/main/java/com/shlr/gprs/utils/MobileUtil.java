package com.shlr.gprs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.suwoit.json.util.StringUtils;
import com.xiaoleilu.hutool.util.StrUtil;

import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MobileUtil {
	private static Logger logger = LoggerFactory.getLogger(MobileUtil.class);
	private static String Mobile = "^1(([3][456789])|([5][012789])|([8][23478])|([4][7])|([7][8]))[0-9]{8}$";
	private static String Unicom = "^1(([3][012])|([4][5])|([5][56])|([8][56])|([7][6]))[0-9]{8}$";

	public static boolean isNotMobileNO(String mobile) {
		return (StringUtils.isEmpty(mobile)) || (mobile.length() != 11)
				|| (mobile.charAt(0) != '1');
	}

	public static int checkType(String catName) {
		if (catName.equals("中国移动")){
			return 1;
		}else if (catName.equals("中国联通")) {
			return 2;
		}else{
			return 3;
		}
		
	}

	public static JSONObject getAddress(String mobile) {
		if (isNotMobileNO(mobile)) {
			return null;
		}
		Map<String,String> params = new HashMap<String,String>();
		params.put("tel", mobile);
		String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
		String response=null;
		int count = 5 ;
		while (count>0) {
			try {
				response = OkhttpUtils.getInstance()
					.get(url)
					.params(params, true)
					.execute().body().string();
				count = 0;
			} catch (IOException e) {
				count--;
			}
		}
		String province ="";
		if (response!=null) {
			String json = response.substring(response.indexOf("{"));
			JSONObject jsonObj = JSON.parseObject(json);
			return jsonObj;
		}
		
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
//		String address = getAddress("13545141090");
//		System.out.println(address);
		
		
		getAddress("13545141090");
	}
}