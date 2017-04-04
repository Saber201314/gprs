package com.shlr.gprs.utils;

import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.suwoit.json.util.StringUtils;

import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class MobileUtil {
	private static Logger logger = Logger.getLogger(MobileUtil.class);
	private static String Mobile = "^1(([3][456789])|([5][012789])|([8][23478])|([4][7])|([7][8]))[0-9]{8}$";
	private static String Unicom = "^1(([3][012])|([4][5])|([5][56])|([8][56])|([7][6]))[0-9]{8}$";

	public static boolean isNotMobileNO(String mobile) {
		return (StringUtils.isEmpty(mobile)) || (mobile.length() != 11)
				|| (mobile.charAt(0) != '1');
	}

	public static int checkType(String mobile) {
		if (mobile.matches(Mobile))
			return 1;
		if (mobile.matches(Unicom)) {
			return 2;
		}
		return 3;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getAddress(String mobile) {
		if (isNotMobileNO(mobile)) {
			return "";
		}
		Map params = new HashMap();
		params.put("tel", mobile);
		String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
		String response=null;
		try {
			
			response = OkhttpUtils.get(url)
				.params(params, true)
				.execute().body().string();
		} catch (IOException e) {
			
			
			try {
				response = OkhttpUtils.get(url)
						.params(params, true)
						.execute().body().string();
			} catch (IOException e1) {
				logger.error("mobile interface failed");
				return "";
			}
		}
		if (response!=null) {
			int len = response.indexOf("province:");
			if (len == -1) {
				return "";
			}
			response = response.substring(len + 10);

			len = response.indexOf(",");
			if (len == -1) {
				return "";
			}
			response = response.substring(0, len - 1);
		}
		
		return response;
	}

	public static void main(String[] args) {
		// System.out.println(checkType("18483228871"));
	}
}