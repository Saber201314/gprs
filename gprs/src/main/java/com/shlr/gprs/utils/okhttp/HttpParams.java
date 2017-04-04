package com.shlr.gprs.utils.okhttp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;

public class HttpParams implements Serializable {
	private static final long serialVersionUID = 8601949661419045928L;
	
	public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
	public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

	public static final boolean IS_REPLACE = true;

	/** 普通的键值对参数 */
	public LinkedHashMap<String, List<String>> urlParamsMap;

	public HttpParams() {
		init();
	}

	public HttpParams(String key, String value) {
		init();
		put(key, value, IS_REPLACE);
	}

	private void init() {
		urlParamsMap = new LinkedHashMap<String, List<String>>();
	}

	public void put(HttpParams params) {
		if (params != null) {
			if (params.urlParamsMap != null && !params.urlParamsMap.isEmpty())
				urlParamsMap.putAll(params.urlParamsMap);
		}
	}

	public void put(Map<String, String> params, boolean... isReplace) {
		if (params == null || params.isEmpty())
			return;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			put(entry.getKey(), entry.getValue(), isReplace);
		}
	}

	public void put(String key, String value, boolean... isReplace) {
		if (isReplace != null && isReplace.length > 0) {
			put(key, value, isReplace[0]);
		} else {
			put(key, value, IS_REPLACE);
		}
	}

	public void put(String key, int value, boolean... isReplace) {
		if (isReplace != null && isReplace.length > 0) {
			put(key, String.valueOf(value), isReplace[0]);
		} else {
			put(key, String.valueOf(value), IS_REPLACE);
		}
	}

	public void put(String key, long value, boolean... isReplace) {
		if (isReplace != null && isReplace.length > 0) {
			put(key, String.valueOf(value), isReplace[0]);
		} else {
			put(key, String.valueOf(value), IS_REPLACE);
		}
	}

	public void put(String key, float value, boolean... isReplace) {
		if (isReplace != null && isReplace.length > 0) {
			put(key, String.valueOf(value), isReplace[0]);
		} else {
			put(key, String.valueOf(value), IS_REPLACE);
		}
	}

	public void put(String key, double value, boolean... isReplace) {
		if (isReplace != null && isReplace.length > 0) {
			put(key, String.valueOf(value), isReplace[0]);
		} else {
			put(key, String.valueOf(value), IS_REPLACE);
		}
	}

	public void put(String key, char value, boolean... isReplace) {
		if (isReplace != null && isReplace.length > 0) {
			put(key, String.valueOf(value), isReplace[0]);
		} else {
			put(key, String.valueOf(value), IS_REPLACE);
		}
	}

	public void put(String key, boolean value, boolean... isReplace) {
		if (isReplace != null && isReplace.length > 0) {
			put(key, String.valueOf(value), isReplace[0]);
		} else {
			put(key, String.valueOf(value), IS_REPLACE);
		}
	}

	private void put(String key, String value, boolean isReplace) {
		if (key != null && value != null) {
			List<String> urlValues = urlParamsMap.get(key);
			if (urlValues == null) {
				urlValues = new ArrayList<String>();
				urlParamsMap.put(key, urlValues);
			}
			if (isReplace)
				urlValues.clear();
			urlValues.add(value);
		}
	}

	public void putUrlParams(String key, List<String> values) {
		if (key != null && values != null && !values.isEmpty()) {
			for (String value : values) {
				put(key, value, false);
			}
		}
	}

	public void removeUrl(String key) {
		urlParamsMap.remove(key);
	}

	public void remove(String key) {
		removeUrl(key);
	}

	public void clear() {
		urlParamsMap.clear();
	}

}
