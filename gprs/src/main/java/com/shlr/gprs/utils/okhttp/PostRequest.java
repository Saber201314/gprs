package com.shlr.gprs.utils.okhttp;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
* @author xucong
* @version 创建时间：2017年4月2日 下午6:18:01
* 
*/
public class PostRequest extends BaseBodyRequest<PostRequest>{

	public PostRequest(String url) {
        super(url);
        method = "POST";
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        return requestBuilder.post(requestBody).url(url).build();
    }
}
