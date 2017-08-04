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
	public static void main(String[] args) throws UnsupportedEncodingException {
			PayLog payLog = new  PayLog();
			BackMoneyVO  backMoneyVO = new BackMoneyVO();
			ChargeOrBuckleVO buckleVO = new ChargeOrBuckleVO();
			System.out.println(payLog instanceof BackMoneyVO);
			System.out.println(backMoneyVO instanceof BackMoneyVO);
			System.out.println(buckleVO instanceof PayLog);
	}
}
