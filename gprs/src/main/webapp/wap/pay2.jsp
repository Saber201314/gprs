<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${user.siteName}</title>
	    <meta http-equiv="P3P" content='CP="IDC DSP COR CURa ADMa  OUR IND PHY ONL COM STA"'>
	<meta name="viewport" content="width=device-width; initial-scale=1.0;  minimum-scale=1.0; maximum-scale=2.0"/>
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/asserts/css/reset.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/asserts/css/m2.css" />
</head>
<body style="background-color:#fff;">
	<input type="hidden" id="charge-url" value="${pageContext.request.contextPath}/wap/payByAlipay.action"   />
	<input type="hidden" id="type-input" value="3"  />
	<input type="hidden" id="location-input" value="江苏"  />
	<input type="hidden" id="hidden-mobile" value="${mobile }"  />
	<input type="hidden" id="hidden-password" value="${password }"  />
	<input type="hidden" id="hidden-siteId" value="${siteId }"  />
	<div class="order-box">
		<ul class="list-type">
			<li><a href="payByCardPage.action?mobile=${mobile }"><span class="ico"><i></i></span><p>充值卡充值</p></a></li>
			<li><a href="javascript:void(0);"><span class="ico"><i></i></span><p>现金充值</p></a></li>
		</ul>
		<div class="phone">
			<div class="p">
				<div class="i"><input type="text" placeholder="请输入您的手机号码" id="mobile-input" value="${mobile }"></div>
				<p><span class="msg-wrapper">江苏电信</span></p>
			</div>
		</div>
		<div class="choice-list package-list">
			<ul>
				<s:iterator value="packageList">
				<li class="single-select" id="${id }" amount="${amount }" type="${type }" location-type="${locationType}" locations="${locations }" paymoney="${paymoney }">
					<p>${amount}M</p>
				</li>
		        </s:iterator>
			</ul>
		</div>
		<div class="hb">
			<span class="left"><input type="checkbox" value="true" id="use-hongbao"  <s:if test="hongbao>0">checked="checked"</s:if>/>使用流量积分</span>
			<span class="right">您的可用积分为<em>${hongbao}</em>元</span>
		</div>
		<div class="buy-list">
			<ul>
				<li class="li-province">
					<p><strong>购买价格：<em id="money-province" class="money">0</em>元</strong>本地流量，即时生效，月底清零</p>
					<div class="i"><a href="#" class="pay-by-alipay province enable" >购买</a></div>
				</li>
				<li class="li-country">
					<p><strong>购买价格：<em id="money-country"  class="money">0</em>元</strong>全国流量，即时生效，月底清零</p>
					<div class="i"><a href="#" class="pay-by-alipay country enable">购买</a></div>
				</li>
			</ul>
		</div>
		<div class="tips"><p>由于运营商的流量充值到账时间可能有延迟，请耐心等待，到账后会有短信提醒，若120分钟后未到账，系统会自动退款到您的账户中。由于运营商的规定，月底最后二天中国移动的流量充值业务会顺延至次月初到账，所以不建议您月底最后二天充值。</p></div>
	</div>
	<br><br><br><br>
	<div class="mark" id="mark" style="display:none;">
		<img src="${pageContext.request.contextPath}/asserts/images/loading.gif" alt="">
	</div>
	<script src="http://g.tbcdn.cn/kissy/k/1.4.6/seed-min.js" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/asserts/js/core.js"></script>
	<script>
		KISSY.use("gprs/gprs-wap2",function(S){
			GPRS.Wap2.init();
		});
	</script>
</body>
</html>