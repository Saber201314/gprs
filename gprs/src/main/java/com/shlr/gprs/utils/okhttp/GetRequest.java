package com.shlr.gprs.utils.okhttp;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
* @author xucong
* @version 创建时间：2017年4月2日 下午6:11:10
* 
*/
public class GetRequest extends BaseRequest<GetRequest>{

	public GetRequest(String url) {
		super(url);
		method="GET";
	}

	@Override
	public RequestBody generateRequestBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request generateRequest(RequestBody requestBody) {
		Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        url = HttpUtils.createUrlFromParams(baseUrl, params.urlParamsMap);
        return requestBuilder.get().url(url).build();
	}

}
