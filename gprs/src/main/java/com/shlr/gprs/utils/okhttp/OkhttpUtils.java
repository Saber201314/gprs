package com.shlr.gprs.utils.okhttp;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.shlr.gprs.utils.User;
import com.suwoit.json.util.StringUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 网络请求工具类
 * 
 * @author xucong
 *
 */
public class OkhttpUtils {
	
	private OkHttpClient mOkHttpClient;
	private OkHttpClient.Builder okHttpClientBuilder;
    private int mRetryCount = 0;     

	private OkhttpUtils() {
		okHttpClientBuilder = new OkHttpClient.Builder()
				.addInterceptor(new HttpLoggingInterceptor())
				.connectTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS);
	}

	public static OkhttpUtils getInstance() {
		return ClientHolder.okHttpUtils;
	}

	private static class ClientHolder {
		static OkhttpUtils okHttpUtils = new OkhttpUtils();
	}

	/**
	 * 获取OkHttpClient对象
	 */
	public OkHttpClient getOkHttpClient() {
		if (null == mOkHttpClient) {
			mOkHttpClient = okHttpClientBuilder.build();
		}
		return mOkHttpClient;
	}

	/**
	 * 获取OkHttpClientBuilder对象
	 */
	public OkHttpClient.Builder getOkHttpClientBuilder() {
		return okHttpClientBuilder;
	}
	
	 /** 超时重试次数 */
    public OkhttpUtils setRetryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");
        mRetryCount = retryCount;
        return this;
    }

    /** 超时重试次数 */
    public int getRetryCount() {
        return mRetryCount;
    }
    /** get请求 */
    public GetRequest get(String url) {
        return new GetRequest(url);
    }

    /** post请求 */
    public PostRequest post(String url) {
        return new PostRequest(url);
    }

}
