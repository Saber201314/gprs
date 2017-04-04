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
<div class="cont1 cont show pay-v2">
	<form id="pay-by-card-form" method="post" action="${pageContext.request.contextPath}/wap/payByCard.action" novalidate="novalidate">
	<ul style="max-width: 40rem; min-width: 40rem;padding: 6rem 2rem;">
		<li><input type="text" placeholder="请输入您的手机号码" mobile-msg="请输入您的手机号码" value="${mobile }" required-msg="请输入您的手机号码" required="" class="txt" name="mobile"><div class="msg-wrapper" style="display: none;"></div></li>
		<li><input type="text" placeholder="请输入您的充值卡号" required-msg="请输入您的充值卡号" required="" class="txt" name="pin"><div class="msg-wrapper" style="display: none;"></div></li>
		<li><input type="text" placeholder="请输入您的充值卡密码" required-msg="请输入您的充值卡密码" required="" class="txt" name="password"><div class="msg-wrapper" style="display: none;"></div></li>
		<li class="btn">
			<a id="pay-by-card-btn" href="#">立即充值</a>
			<a onclick="javascript:history.go(-1);" href="javascript:void(0);">返回</a>
		</li>
	</ul>
	</form>
</div>
</body>
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
</html>