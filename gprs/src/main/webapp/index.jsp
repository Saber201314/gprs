<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>登录</title>

<link href="${pageContext.request.contextPath}/resourse/css/login.css" rel="stylesheet" type="text/css">
	
	
<script
	src="${pageContext.request.contextPath}/resourse/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/asserts/js/canvas.js"></script>
	

</head>

<script>
if(window !=top){  
    top.location.href=location.href;  
} 
</script>

<body class="login">
<div class="login_m1">
	<div class="login_logo"><img src="${pageContext.request.contextPath}/resourse/images/logo.png"></div>
</div>
	<%-- <img src="${pageContext.request.contextPath}/resourse/images/login_bg.jpg" id="background-img" /> --%>
<canvas id="root" style="position:absolute;display: block;" width="1900" height="1010"  ></canvas>
<div class="login_m">
	<div class="login_boder">
	<form action="${pageContext.request.contextPath}/login.action" method="post">
		<div class="login_padding">
			<h2><li class="error-msg">${error_msg }</li></h2>		
			<h2>用户名</h2>
			<label>
				<input type="text" name="username" class="txt_input txt_input2">
			</label>
			<h2>密码</h2>
			<label>
				<input type="password" name="password" class="txt_input">
			</label>
			<label class="yzm clearfix">
			<h2>验证码：</h2>
				<input type="text" name="securityCode" id="securityCode"/>
				<span class="yzmimg" style="float:left;padding-left:15px !important;"><img src="getSecurityCode.action?temp='+Math.random();" width="150" height="38" alt="" id="code-img"
				onclick="document.getElementById('code-img').src='getSecurityCode.action?temp='+Math.random();"></span>
			</label>		
			<div class="rem_sub">
				<label>
					<input type="submit" class="sub_button" name="button" id="button" value="登录" >
				</label>
			</div>
		</div>
		</form>
	</div><!--login_boder end-->
</div><!--login_m end-->

<br />
<br />

<p align="center">版权所有：流量分发平台</p>

<script>
$(".login_logo").css("margin-top",$(".login_boder").offset().top-60);



var root = document.querySelector("#root")
var a = new CanvasAnimate(root,{length:150,clicked:true,moveon:true})
    a.Run()

</script>
</body>
</html>

