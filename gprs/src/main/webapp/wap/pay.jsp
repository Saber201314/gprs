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
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/asserts/css/m.css" />
	<script src="${pageContext.request.contextPath}/asserts/js/TouchSlide.1.1.js"></script>
</head>
<body>
	<div class="logo clearfix">
		<a href=""><img src="${pageContext.request.contextPath}/uploads/${user.siteLogo}" alt=""></a>
		<p>流量平台让生活飞起来！</p>
	</div>
	<div id="slideBox" class="slideBox">    
	    <div class="bd">
	      <ul>
	       <s:iterator value="adList">
				<li><a href="<s:property value='url' />"><img src="${pageContext.request.contextPath}/uploads/<s:property value='pic' />" /></a></li>
			</s:iterator>
	      </ul>
	    </div>
	    <div class="hd">
	      <ul></ul>
	    </div>
	   <script type="text/javascript">
			TouchSlide({ 
				slideCell:"#slideBox",
				titCell:".hd ul", //开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
				mainCell:".bd ul", 
				effect:"left", 
				autoPlay:true,//自动播放
				autoPage:true, //自动分页
				switchLoad:"_src" //切换加载，真实图片路径为"_src" 
			});
		</script> 
	</div>
	<div class="tab">
		<ul class="clearfix" id="tab_t">
			<li class="cur"><a>充值卡充值</a></li>
			<s:if test="user.onlinePay==1">
			<li><a>网银充值</a></li>
			</s:if>
		</ul>
	</div>
	<div class="main" id="tab_c">
		<div class="cont1 cont show">
			<form action="${pageContext.request.contextPath}/wap/payByCard.action" method="post" id="pay-by-card-form">
			<ul>
				<li><p>手机号码：</p><div><input name="mobile" class="txt" type="text"  required required-msg="请输入您的手机号码" mobile mobile-msg="请输入您的手机号码"></div></li>
				<li><p>充值卡卡号：</p><div><input name="pin" class="txt" type="text" required required-msg="请输入您的充值卡号"></div></li>
				<li><p>充值卡密码：</p><div><input name="password" class="txt" type="text" required required-msg="请输入您的充值卡密码"></div></li>
				<li class="btn"><input class="btn-i" type="button" value="立即充值" id="pay-by-card-btn"></li>
			</ul>
			</form>
		</div>
		<!--cont1/end-->
		<s:if test="user.onlinePay==1">
		<div class="cont2 cont">
			<input type="hidden" id="charge-url" value="${pageContext.request.contextPath}/wap/payByAlipay.action?siteId=${siteId}"   />
			<input type="hidden" id="type-input"   />
			<input type="hidden" id="location-input"   />
			<ul>
				<li class="text">
					<span>手机号码：</span>
					<div class="choose tel">
						<div class="i"><input type="text" placeholder="请输入您的手机号码" id="mobile-input"></div>
						<div class="msg-wrapper" style="display:none;"></div>
	            		<div class="process" style="display:none;">&nbsp;</div>
					</div>
				</li>
				<li class="text">
					<span>生效时间：</span>
					<div class="choose xz take-time">
						<a href="" class="single-select  immediately">立即生效</a>
						<a href="" class="single-select  next-month">次月生效</a>
					</div>
				</li>
				<li class="text">
					<span>流量类型：</span>
					<div class="choose xz location-type">
						<a href="" class="single-select location-type-item country">全国流量</a>
						<a href="" class="single-select location-type-item province">省内流量</a>
					</div>
				</li>
				<li class="text">
					<span>充值流量：</span>
					<div class="choose xz package-list">
						<s:iterator value="packageList">
		           		<a href="" style="display:none;" class="single-select package-list-item" id="${id }" type="${type }" location-type="${locationType}" locations="${locations }" paymoney=${paymoney }>${alias}</a>
		           		</s:iterator>
					</div>
				</li>
				<li class="text"><span>扣费金额：</span><div class="pay"><span class="money">0</span>元</div></li>
				<li class="btn"><input class="btn-i pay-by-alipay" type="submit" value="立即充值"></li>
			</ul>
		</div>
		</s:if>
	</div>
	<div class="mark" id="mark" style="display:none;">
		<img src="${pageContext.request.contextPath}/asserts/images/loading.gif" alt="">
	</div>
	<script src="http://g.tbcdn.cn/kissy/k/1.4.6/seed-min.js" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/asserts/js/core.js"></script>
	<script>
		KISSY.use("gprs/gprs-wap,gprs/gprs-charge",function(S){
			GPRS.Wap.init();
			GPRS.Charge.init();
		});
		//<s:if test="amount!=0">
		var amount = "${amount}";
		if(amount != 0)
		alert("恭喜，您成功为${mobile}充值${amount}M流量");
		//</s:if>
	</script>
</body>
</html>