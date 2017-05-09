package gprs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.coyote.http11.Http11AprProcessor;

import com.shlr.gprs.utils.okhttp.HttpHeaders;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;

import okhttp3.MediaType;
import okhttp3.Response;

/**
* @author xucong
* @version 创建时间：2017年4月30日 下午6:26:46
* 
*/
public class TestJavaMail {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String a="ä½é¢ä¸è¶³";
		System.out.println(new String(a.getBytes("iso-8859-1"),"utf-8"));
				
	}
}
