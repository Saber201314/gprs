package com.shlr.gprs.utils.okhttp;

import java.io.File;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
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

	/** 文件的键值对参数 */
	public LinkedHashMap<String, List<FileWrapper>> fileParamsMap;

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
			if (params.fileParamsMap != null && !params.fileParamsMap.isEmpty())
				fileParamsMap.putAll(params.fileParamsMap);
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
				urlValues = new ArrayList<>();
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

	public void put(String key, File file) {
		put(key, file, file.getName());
	}

	public void put(String key, File file, String fileName) {
		put(key, file, fileName, guessMimeType(fileName));
	}

	public void put(String key, FileWrapper fileWrapper) {
		if (key != null && fileWrapper != null) {
			put(key, fileWrapper.file, fileWrapper.fileName, fileWrapper.contentType);
		}
	}

	public void put(String key, File file, String fileName, MediaType contentType) {
		if (key != null) {
			List<FileWrapper> fileWrappers = fileParamsMap.get(key);
			if (fileWrappers == null) {
				fileWrappers = new ArrayList<>();
				fileParamsMap.put(key, fileWrappers);
			}
			fileWrappers.add(new FileWrapper(file, fileName, contentType));
		}
	}

	public void putFileParams(String key, List<File> files) {
		if (key != null && files != null && !files.isEmpty()) {
			for (File file : files) {
				put(key, file);
			}
		}
	}

	public void putFileWrapperParams(String key, List<FileWrapper> fileWrappers) {
		if (key != null && fileWrappers != null && !fileWrappers.isEmpty()) {
			for (FileWrapper fileWrapper : fileWrappers) {
				put(key, fileWrapper);
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

	private MediaType guessMimeType(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		path = path.replace("#", ""); // 解决文件名中含有#号异常的问题
		String contentType = fileNameMap.getContentTypeFor(path);
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return MediaType.parse(contentType);
	}

	/** 文件类型的包装类 */
	public static class FileWrapper {
		public File file;
		public String fileName;
		public MediaType contentType;
		public long fileSize;

		public FileWrapper(File file, String fileName, MediaType contentType) {
			this.file = file;
			this.fileName = fileName;
			this.contentType = contentType;
			this.fileSize = file.length();
		}

		@Override
		public String toString() {
			return "FileWrapper{" + "file=" + file + ", fileName='" + fileName + ", contentType=" + contentType
					+ ", fileSize=" + fileSize + '}';
		}
	}
}
