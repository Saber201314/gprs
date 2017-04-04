package com.shlr.gprs.utils.okhttp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import okhttp3.RequestBody;

/**
* @author xucong
* @version 创建时间：2017年4月2日 下午6:19:18
* 
*/
public interface HasBody<R> {
	R isMultipart(boolean isMultipart);

    R requestBody(RequestBody requestBody);

    R upString(String string);

    R upJson(String json);

    R upJson(JSONObject jsonObject);

    R upJson(JSONArray jsonArray);

    R upBytes(byte[] bs);
}
