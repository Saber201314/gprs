package gprs.test;

import java.io.IOException;

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
	public static void main(String[] args) {
		
		HttpHeaders headers=new HttpHeaders();
		
		headers.put("Cookie:_slcxesales_", "A6X6MX4XJHG7D5HCBCL3GLZT6YH5T6HE4JYGRKYKJVZABMXJCX3L65CFHYWAE3GE");
		headers.put("Origin", "http://sl.zgslcx.com");
		headers.put("Referer", "http://sl.zgslcx.com/order/order_list.action");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
		try {
			Response execute = OkhttpUtils.getInstance()
				.post("http://sl.zgslcx.com/order/order_list.action")
				.headers(headers)
				.params("query.orderId", "")
				.params("query.agentOrderNo", "")
				.params("query.createTimeMin", "2017-05-03 00:00:00")
				.params("query.createTimeMax", "2017-05-03 23:59:59")
				.params("query.rechargeType", "-1")
				.params("query.provinceCode", "1")
				.params("query.facePrice", "-1")
				.params("query.orderStatus", "-1")
				.params("query.phoneNo", "13589967675")
				.params("startPage", "")
				.params("endPage", "")
				.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}
