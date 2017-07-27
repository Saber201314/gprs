package gprs.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.coyote.http11.Http11AprProcessor;

import com.shlr.gprs.manager.ThreadManager;
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
	static ConcurrentHashMap<Integer, Double> money = new ConcurrentHashMap<Integer, Double>();
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		money.put(1, 100D);
		for (int i = 0; i < 10; i++) {
			ThreadManager.getInstance().execute(new Runnable() {
				
				@Override
				public void run() {
					Double double1 = money.get(1);
					System.out.println(double1);
					money.put(1, double1-1);
				}
			});
		}
		for (int i = 0; i < 5; i++) {
			ThreadManager.getInstance().execute(new Runnable() {
				
				@Override
				public void run() {
					Double double1 = money.get(1);
					System.out.println(double1);
					money.put(1, double1+1);
				}
			});
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---->>>>>>>"+money.get(1));
		
	}
}
