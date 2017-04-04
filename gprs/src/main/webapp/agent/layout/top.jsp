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
            	<li><img src="${pageContext.request.contextPath}/asserts/images/list1.gif"/><a href="#"><span id ="time">${session.user.username }</span></a></li>
                <li><img src="${pageContext.request.contextPath}/asserts/images/list2.gif"/><a href="#">代理商余额：${session.user.moneyString }元</a></li>
                <li style="background-color:#241b19;"><a href="${pageContext.request.contextPath}/exit.action" class="white" style="padding:0 5px;">退出系统</a></li>

            </ul>
        </div>
    </div>
	<div class="main" id="main">  
	   <div id="center" style="height:100%;float:left;">
	        <ul id="navigation" style="height:90%">
	        <li><a href="../layout/shouye.action" class="head"><span class="houseSpan"></span>首页</a></li>
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>业务管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=1}' >hidden</s:if>">
	          	<s:if test="#session.user.containsLimit(101)">
	          	<li><a href="../charge/singleCharge.action">单号充流量</a></li>
	          	</s:if>
	          	<s:if test="#session.user.containsLimit(102)">
	          	<li><a href="../charge/batchCharge.action">批量充流量</a></li>
	          	</s:if>
	          	<s:if test="#session.user.containsLimit(201)">
	          	<li><a href="../paycard/paycardList.action">充值卡管理</a></li>
	          	</s:if>
	         </ul>
	        </li>
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>数据查询</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=3}' >hidden</s:if>">
	            <li><a href="../query/chargeOrderList.action">流量充值记录</a></li>
	            <li><a href="../query/batchChargeList.action">批量任务查询</a></li>
	            <li><a href="../query/payLogList.action">消费明细查询</a></li>
	            <li><a href="../query/agentChargeList.action">财务管理</a></li>
	         </ul>
	        </li>
	         <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>费用设置</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=6}' >hidden</s:if>">
	          	 <li><a href="../paper/pricePaperList.action">报价管理</a></li>
	          	 <li><a href="../suite/suiteList.action">流量池管理</a></li>
	         </ul>
	        </li>
	        <s:if test="#session.user.containsLimit(301)">
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>代理商管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=2}' >hidden</s:if>">
	            <li><a href="../agent/agentList.action">代理商管理</a></li>
	          	 <li><a href="../agent/publishAgent.action">添加代理商</a></li>
	         </ul>
	        </li>
	        </s:if>      
	        <s:if test="#session.user.containsLimit(301)">
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>统计报表管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=2}' >hidden</s:if>">
	            <li><a href="../agent/chargeReportList.action">查询统计报表</a></li>
	         </ul>
	        </li>
	        </s:if>		         
	        <li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>移动网页管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=5}' >hidden</s:if>">
	          	<s:if test="#session.user.containsLimit(401)">
	            <li><a href="../wap/portalSetting.action">页面设置</a></li>
	            </s:if>
	            <s:if test="#session.user.containsLimit(402)">
	            <li><a href="../wap/advertiseList.action">广告管理</a></li>
	            </s:if>
	            <s:if test="#session.user.containsLimit(403)">
	            <li><a href="../wap/alipaySetting.action">支付宝充值</a></li>
	            </s:if>
	         </ul>
	        </li>
	      	<li> <a class="head ks-switchable-trigger"><span class="personSpan"></span><span>系统管理</span></a>
	          <ul class="ks-switchable-panel <s:if test='%{#menu_open!=4}' >hidden</s:if>" >
	          	<li><a href="../paper/myPrice.action">我的价格</a></li>
	          	<li><a href="../suiteOrder/mySuiteOrder.action">我的流量池</a></li>
	          	<li><a href="../setting/paramSetting.action">参数设置</a></li>
	            <li><a href="../user/changePassword.jsp">修改密码</a></li>
	          </ul>
	        </li>
	       
	      </ul>
	   </div>
	   <div id="container">
