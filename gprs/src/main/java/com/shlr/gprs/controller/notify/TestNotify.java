package com.shlr.gprs.controller.notify;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shlr.gprs.manager.ChargeManager;
import com.xiaoleilu.hutool.util.CharsetUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * @author Administrator
 */

@Controller
public class TestNotify {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/test.notify")
	public void channelnotify(HttpServletRequest request, HttpServletResponse response,
				String orderId,String code,String msg) throws IOException {
		try{
			
			String convert = CharsetUtil.convert(msg, CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
			logger.info("Receive <<<<<===== {} orderId={} code={} msg={}",request.getRequestURI(),orderId,code,msg);
			if ("0".equals(code)) {
				ChargeManager.getInstance().updateResult(1, orderId, true, code+":"+convert);
			} else if("1".equals(code)) {
				ChargeManager.getInstance().updateResult(1, orderId, false, code+":"+convert);
			}
			response.getWriter().print("200");

		} catch (Exception e) {
			response.getWriter().print("fail");
			logger.error(this.getClass().toString(), e);
		}

	}
}
