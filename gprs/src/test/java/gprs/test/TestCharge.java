package gprs.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.alibaba.fastjson.JSON;
import com.shlr.gprs.constants.Const;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Administrator
 */

public class TestCharge {
	static Random random=new Random();
	static OkHttpClient client = null;
	static String url="http://xucongblog.com:8080";
	static ExecutorService service=Executors.newFixedThreadPool(20);
	public static void main(String[] args) throws Exception {
		client=new OkHttpClient.Builder()
			.connectTimeout(2, TimeUnit.SECONDS)
			.readTimeout(2, TimeUnit.SECONDS)
			.writeTimeout(2, TimeUnit.SECONDS)
			.build();
		
		
//		List<String> warnings = new ArrayList<String>();
//		boolean overwrite = true;
//		ConfigurationParser cp = new ConfigurationParser(warnings);
//		Configuration config = cp.parseConfiguration(
//				TestCharge.class.getResourceAsStream("/generatorConfig.xml"));
//		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//		myBatisGenerator.generate(null);
		
		int total = 0;
		int match = 0;
		Random random = new Random();
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		for (int i = 0; i < 1000000; i++) {
			int nextInt = random.nextInt(100);
			total++;
			if(nextInt <= 4){
				match++;
				String result = numberFormat.format((float) match / (float) total * 100);
				System.out.println(result+"% 中奖啦"+nextInt);
			}else{
				String result = numberFormat.format((float) match / (float) total * 100);
				System.out.println(result+"% 再接再厉"+nextInt);
			}
		}
		
		
		
		
//		charge();
//		callback();

//		test();
//		testcharge();http://101.37.32.70
		
//		fail();
	}
	public static void fail(){
		List<Integer> protList=new ArrayList<Integer>();
		String port="80,8080,3128,8081,9080,1080,21,23,443,69,22,25,110,7001,9090,3389,1521,1158,2100,1433,3306";
		String[] ports=port.split(",");
		for (int i = 0; i <ports.length; i++) {
			Request request=new Request.Builder()
					.url("http://120.26.229.52:"+ports[i]) 
					.get()
					.build();
			// TODO Auto-generated method stub
			try {
				Response execute = client.newCall(request).execute();
				System.out.print(ports[i]);
				if (execute.isSuccessful()) {
					System.out.println("  打开");
					execute.close();
					protList.add(i);
				}else{
					System.out.println("  关闭");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(ports[i]+"  关闭");
			}
		}
	}
	public static void testcharge(){
		Map<String, String> param= new HashMap<String, String>();
		param.put("username", "test");
		param.put("password", "51abc98035cc9999a8a776b5bd67326f");
		param.put("mobile", "13545141090");
		param.put("amount", "10");
		param.put("range", "0");
		param.put("backUrl", "");
		param.put("orderId",String.valueOf(random.nextInt(1000)) );
		
		RequestBody body=RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), createFromParams(param));
		
		Request request=new Request.Builder()
				.url(url+"/externalV2/charge.action")
				.post(body)
				.build();
		Response execute=null;
		try {
			execute = client.newCall(request).execute();
			System.out.println(execute.body().string());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void test(){
		Map<String, String> param= new HashMap<String, String>();
		param.put("orderid", "101");
		param.put("code", "1");
		param.put("message", "充值成功");
		
		RequestBody body=RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(param));
		
		Request request=new Request.Builder()
				.url(url+"/test.notify")
				.post(body)
				.build();
		Response execute=null;
		try {
			execute = client.newCall(request).execute();
			System.out.println(execute.body().string());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void charge(){
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
				System.out.println("休息一秒");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int j = 0; j <10; j++) {
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Map<String, String> param= new HashMap<String, String>();
						param.put("username", "test");
						param.put("password", "51abc98035cc9999a8a776b5bd67326f");
						param.put("mobile", "13545141090");
						param.put("amount", "10");
						param.put("range", "0");
						param.put("backUrl", "");
						param.put("orderId",String.valueOf(random.nextInt(1000)) );
						
						RequestBody body=RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), createFromParams(param));
						
						Request request=new Request.Builder()
								.url(url+"/externalV2/charge.action")
								.post(body)
								.build();
						Response execute=null;
						try {
							execute = client.newCall(request).execute();
							System.out.println(execute.body().string());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
				}).start();
			}
		}
	}
	public static void callback(){
		for (int i = 0; i < 10; i++) {
		try {
			Thread.sleep(1000);
			System.out.println("休息一秒");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j = 0; j <10; j++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Map<String, String> param= new HashMap<String, String>();
					param.put("orderid", Const.getOrderid().toString());
					param.put("code", "0");
					param.put("message", "充值失败");
					
					RequestBody body=RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(param));
					
					Request request=new Request.Builder()
							.url(url+"/test.notify")
							.post(body)
							.build();
					
					
					Response execute=null;
					try {
						execute = client.newCall(request).execute();
						System.out.println(execute.body().string());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
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
}
