<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.profile-user-info {margin-top:3px;}
.layui-layer{}
</style>

<div id="single-page-content" style="display:none;">

<div class="profile-user-info profile-user-info-striped">
	<div class="profile-info-row">
		<div class="profile-info-name">公司名称</div>
		<div class="profile-info-value">
			<span id="compayName"></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">代理商账号</div>
		<div class="profile-info-value">
			<span id="account"></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">代理商联系号码</div>
		<div class="profile-info-value">
			<span id="agentMobile"></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">订单号码</div>
		<div class="profile-info-value">
			<span id="orderId"></span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">手机号码</div>
		<div class="profile-info-value">
			<span id="chargeMobile"></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">归属地</div>		
		<div class="profile-info-value">
			<i class="icon-map-marker light-orange bigger-110"></i>		
			<span id="chargeLocation"></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">流量类型</div>
		<div class="profile-info-value">
			<span id="locationType"></span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">流量值</div>
		<div class="profile-info-value">
			<span id="amount"></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">基础价格</div>
		<div class="profile-info-value">
			<span id="basePrice"></span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">扣费金额</div>
		<div class="profile-info-value">
			<span id="chargePrice"></span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">充值时间</div>
		<div class="profile-info-value" id="chargeTime">
			<%-- <span id="chargeTime"></span> --%>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">充值方式</div>
		<div class="profile-info-value">
			<span id="chargeMethod"></span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">回调时间</div>
		<div class="profile-info-value" id="reportTime">
			<%-- <span id="reportTime"></span> --%>
		</div>
	</div>		
	<div class="profile-info-row">
		<div class="profile-info-name">充值结果</div>
		<div class="profile-info-value">
			<span id="chargeResult"></span>
			<a class="badge badge-info simple-detail-triggle refund-oper" style="display:none;float:right;" href="#">实时退款</a>
		</div>
	</div>	
	<div class="profile-info-row" id="refundFlagRow">
		<div class="profile-info-name">退款标记</div>
		<div class="profile-info-value">
			<span id="refundFlag" style="color:orange"></span>
		</div>
	</div>		
	<div class="profile-info-row">
		<div class="profile-info-name">代理商订单号码</div>
		<div class="profile-info-value">
			<span id="agentOrderId"></span>
		</div>
	</div>		
	<div class="profile-info-row">
		<div class="profile-info-name">回调地址</div>
		<div class="profile-info-value">
			<span id="callBackAdr"></span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">回调状态</div>
		<div class="profile-info-value">
			<span id="callBackStatus"></span>
		</div>
	</div>													
</div>
</div>
<script>
</script>