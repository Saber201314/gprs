package com.shlr.gprs.utils.okhttp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
* @author xucong
* @version 创建时间：2017年4月2日 下午6:18:28
* 
*/
public abstract class BaseBodyRequest<R extends BaseBodyRequest> extends BaseRequest<R>  {

	protected MediaType mediaType;      //上传的MIME类型
    protected String content;           //上传的文本内容
    protected byte[] bs;                //上传的字节数据

    protected RequestBody requestBody;
	
	
	public BaseBodyRequest(String url) {
		super(url);
	}
	
	

	
	@Override
	public RequestBody generateRequestBody() {
		if (requestBody != null) return requestBody;                                                //自定义的请求体
        if (content != null && mediaType != null) return RequestBody.create(mediaType, content);    //post上传字符串数据
        if (bs != null && mediaType != null) return RequestBody.create(mediaType, bs);              //post上传字节数组
        return HttpUtils.generateMultipartRequestBody(params);
	}


}
