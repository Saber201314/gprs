package com.shlr.gprs.utils.okhttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
* @author xucong
* @version 创建时间：2017年4月2日 下午6:12:39
* 
*/
public class HttpUtils {
	
	static Logger logger=LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 * 读取request请求内容
	 * @param request
	 * @return
	 */
	public static String readContent(HttpServletRequest request){
		byte[] b = new byte[1024];
		ServletInputStream is ;
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		int len= -1;
		String content="";
		try {
			is = request.getInputStream();
			while(( len = is.read(b) ) != -1){
				bos.write(b);
			}
			byte[] contentbyte = bos.toByteArray();
			if (contentbyte.length != 0) {
				content=new String(contentbyte, "utf-8");
			}
			if (bos != null) {
				bos.close();
			}
			if (is != null) {
				is.close();
			}
			return content;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	/** 
	 * 将传递进来的参数拼接成 url
	 */
    public static String createUrlFromParams(String url, Map<String, List<String>> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
            else sb.append("?");
            for (Map.Entry<String, List<String>> urlParams : params.entrySet()) {
                List<String> urlValues = urlParams.getValue();
                for (String value : urlValues) {
                    //对参数进行 utf-8 编码,防止头信息传中文
                    String urlValue = URLEncoder.encode(value, "UTF-8");
                    sb.append(urlParams.getKey()).append("=").append(urlValue).append("&");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("HttpUtils",e);
        }
        return url;
    }
    /** 将传递进来的参数拼接成 url */
    public static String createFromParams(Map<String, String> params) {
    	StringBuilder sb = new StringBuilder();
        try {
            
            for (Map.Entry<String, String> urlParams : params.entrySet()) {
                Object obj = urlParams.getValue();
                    //对参数进行 utf-8 编码,防止头信息传中文
                    String urlValue = URLEncoder.encode(obj.toString(), "UTF-8");
                    sb.append(urlParams.getKey()).append("=").append(urlValue).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
        }
        return sb.toString();
    }

    /** 通用的拼接请求头 */
    public static Request.Builder appendHeaders(HttpHeaders headers) {
        Request.Builder requestBuilder = new Request.Builder();
        if (headers.headersMap.isEmpty()) return requestBuilder;
        Headers.Builder headerBuilder = new Headers.Builder();
        try {
            for (Map.Entry<String, String> entry : headers.headersMap.entrySet()) {
                //对头信息进行 utf-8 编码,防止头信息传中文,这里暂时不编码,可能出现未知问题,如有需要自行编码
//                String headerValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                headerBuilder.add(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
        	logger.error("HttpUtils",e);
        }
        requestBuilder.headers(headerBuilder.build());
        return requestBuilder;
    }
    
    /** 生成类似表单的请求体 */
    public static RequestBody generateMultipartRequestBody(HttpParams params) {
    	//表单提交，没有文件
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (String key : params.urlParamsMap.keySet()) {
            List<String> urlValues = params.urlParamsMap.get(key);
            for (String value : urlValues) {
                bodyBuilder.add(key, value);
            }
        }
        return bodyBuilder.build();
    }




}
