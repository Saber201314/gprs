package com.shlr.gprs.utils.okhttp;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
/**
* @author 作者 xucong
* @version 创建时间：2017-04-02
*/
public class HttpHeaders implements Serializable {
	
	Logger logger=LoggerFactory.getLogger(HttpHeaders.class);

	private static final long serialVersionUID = -4416564411921787575L;

    public LinkedHashMap<String, String> headersMap;

    private void init() {
        headersMap = new LinkedHashMap<String, String>();
    }

    public HttpHeaders() {
        init();
    }

    public HttpHeaders(String key, String value) {
        init();
        put(key, value);
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            headersMap.put(key, value);
        }
    }

    public void put(HttpHeaders headers) {
        if (headers != null) {
            if (headers.headersMap != null && !headers.headersMap.isEmpty()) headersMap.putAll(headers.headersMap);
        }
    }

    public String get(String key) {
        return headersMap.get(key);
    }

    public String remove(String key) {
        return headersMap.remove(key);
    }

    public void clear() {
        headersMap.clear();
    }

    public Set<String> getNames() {
        return headersMap.keySet();
    }
    public final String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            logger.error("HttpHeaders", e);
        }
        return jsonObject.toString();
    }
}
