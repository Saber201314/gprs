package gprs.test;

import java.io.IOException;

import com.shlr.gprs.utils.okhttp.OkhttpUtils;

import okhttp3.MediaType;
import okhttp3.Response;

/**
* @author xucong
* @version 创建时间：2017年4月30日 下午6:26:46
* 
*/
public class TestJavaMail {
	public static void main(String[] args) {
		Response execute=null;
		StringBuffer sb=new StringBuffer();
		sb.append("<data>")
			.append("<content>aaaaaaaaaa</content>")
			.append("</data>");
		String response="";
		try {
			execute = OkhttpUtils.getInstance()
				.post("http://127.0.0.1:8080/test")
				.upString(sb.toString(), MediaType.parse("text/xml;charset=utf-8"))
				.execute();
			if (execute!=null) {
				response=execute.body().string();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response);
			
	}
}
