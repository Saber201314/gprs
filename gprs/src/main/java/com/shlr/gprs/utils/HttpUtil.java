package com.shlr.gprs.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shlr.gprs.manager.ChargeManager;
import com.xiaoleilu.hutool.io.IoUtil;




public class HttpUtil {
	private static Logger logger=LoggerFactory.getLogger(HttpUtil.class);
	public static String readRequest(HttpServletRequest request) {
		ByteArrayOutputStream bos = null;
		ServletInputStream inputStream = null;
		try {
			request.setCharacterEncoding("UTF-8");
			inputStream = request.getInputStream();
			byte[] temp = new byte[1024];
			int n = 0;
			bos = new ByteArrayOutputStream();
			while ((n = inputStream.read(temp)) != -1) {
				bos.write(temp, 0, n);
			}
			byte[] msgBody = bos.toByteArray();
			if (msgBody.length == 0)
				return null;
			// byte[] msgBody;
			// int n;
			// byte[] temp;
			String string = new String(msgBody, "UTF-8").trim();
			String str1 = string;
			return str1;
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}

			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			inputStream = null;
			bos = null;
		}

		return null;
	}

	public static String getJsonString(HttpServletRequest request)
			throws IOException {
		request.setCharacterEncoding("utf-8");	
		InputStream in = request.getInputStream();
		BufferedReader buff = IoUtil.getReader(in, "UTF-8");
		StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s = buff.readLine()) != null) {
			sb.append(s);
		}
		buff.close();
		in.close();
		return sb.toString();
	}

}