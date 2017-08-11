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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shlr.gprs.domain.PayLog;
import com.shlr.gprs.manager.ThreadManager;
import com.shlr.gprs.utils.okhttp.HttpHeaders;
import com.shlr.gprs.utils.okhttp.OkhttpUtils;
import com.shlr.gprs.vo.BackMoneyVO;
import com.shlr.gprs.vo.ChargeOrBuckleVO;

import okhttp3.MediaType;
import okhttp3.Response;

/**
* @author xucong
* @version 创建时间：2017年4月30日 下午6:26:46
* 
*/
public class TestJavaMail {
	public static void main(String[] args) throws IOException {
			Response execute = OkhttpUtils.getInstance()
				.post("http://ip.taobao.com/service/getIpInfo.php")
				.headers("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
				.params("ip","58.246.140.150")
				.execute();
			JSONObject jsonObject = JSON.parseObject(execute.body().string());
			System.out.println();
	}
}
