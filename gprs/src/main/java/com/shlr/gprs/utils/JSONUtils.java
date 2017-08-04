package com.shlr.gprs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Administrator
 */

public class JSONUtils {
	
	public static String toJsonString(Object obj){
		return JSON.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteMapNullValue,SerializerFeature.DisableCircularReferenceDetect);
	}
}
