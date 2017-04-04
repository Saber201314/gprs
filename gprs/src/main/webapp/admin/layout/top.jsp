<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>流量分发平台</title>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>

</head>
<body>
<div class="top">
      <div class="content">
          <img src="${pageContext.request.contextPath}/asserts/images/top.png">
          <div class="sysTime">
             <p><i></i></p>
          </div>
      </div>
	</div>
	<div style="clear:both"></div>
    <div class="nav">
        <div class="right">
        	<ul>
        		<li class="marquee"><marquee><s:property value="#application.system.gdz" /></marquee></li>
            	<li><img src="${pageContext.request.contextPath}/asserts/images/list1.gif"/><a href="#"><span id ="time">流量分发平台</span></a></li>
                <li><img src="${pageContext.request.contextPath}/asserts/images/list2.gif"/><a href="showUser.action">${session.user.username }</a></li>
                <li style="background-color:#241b19;"><a href="${pageContext.request.contextPath}/exit.action" class="white" style="padding:0 5px;">退出系统</a></li>

            </ul>
        </div>
    </div>
	<div class="main" id="main">  
	   <div id="center" style="height:100%;float:left;">
	        <ul id="navigation" style="height:90%">
	        <li><a href="../notice/shouye.action" class="head"><span class="houseSpan"></span>首页</a></li>
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>流量包管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=1}' >hidden</s:if>">
	            <li><a href="../package/packageList.action">流量包列表</a></li>
	            <li><a href="../package/publishPackage.jsp">添加流量包</a></li>
	         </ul>
	        </li>
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>通道管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=5}' >hidden</s:if>">
	            <li><a href="../channel/channelList.action">通道管理</a>
	            <li><a href="../channel/publishChannel.action">添加新通道</a></li>
	            <li><a href="../channelTemplate/channelTemplateList.action">通道模板列表</a></li>
	         </ul>
	        </li>
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>数据查询</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=3}' >hidden</s:if>" >
	            <li><a href="../query/chargeOrderList.action">流量充值记录</a></li>
	            <li><a href="../query/channelLogList.action">通道提交日志</a></li>
	            <li><a href="../query/callbackList.action">回调日志</a></li>
	          </ul>
	        </li>
	      	<li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>个人信息管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=6}' >hidden</s:if>" >
	            <li><a href="../user/changePassword.jsp">修改密码</a></li>
	          </ul>
	        </li>
	       
	      </ul>
	   </div>
	   <div id="container">
