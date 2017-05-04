<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
.profile-user-info {margin-top:3px;}
.layui-layer{}
.profile-info-name{width:120px;}
#pay-bill-content span{font-weight:bold;}
</style>

<div id="pay-bill-content" style="display:none;">

<div class="profile-user-info profile-user-info-striped">
	<div class="profile-info-row">
		<div class="profile-info-name">公司名称</div>
		<div class="profile-info-value">
			<span id="compayName">无</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">代理商账号</div>
		<div class="profile-info-value">
			<span id="account1">无</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">第一笔订单余额</div>
		<div class="profile-info-value">
			<span id="startTime_balance">￥0.0</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">最后一笔订单余额</div>
		<div class="profile-info-value">
			<span id="endTime_balance">￥0.0</span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">期间打款金额</div>
		<div class="profile-info-value">
			<span id="remittance">￥0.0</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">期间消费金额</div>		
		<div class="profile-info-value">
			<span id="consume">￥0.0</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">核算差异金额</div>
		<div class="profile-info-value">
			<span id="diff" >￥0.0</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">核算方式</div>
		<div class="profile-info-value">
			<span>1、期间打款金额 + 第一笔订单余额 - 期间消费金额 - 最后一笔订单余额。
				   <br> 
				  2、如果等于0属于正常情况，否则异常，需要对数据消费进行核对排查。
				  <br> 
				  3、核算时,第一笔订单余额需要在期间打款金额之后，如对核算有任何疑问，请CALL 005。
				  </span>
		</div>
	</div>		
	<div class="profile-info-row">
		<div class="profile-info-name">核算状态</div>
		<div class="profile-info-value">
			<span id="accounting_status">无</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">期间退款金额</div>
		<div class="profile-info-value">
			<span id="refund">￥0.0</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">期间带票金额</div>
		<div class="profile-info-value">
			<span id="payBill">￥0.0</span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">期间无票金额</div>
		<div class="profile-info-value">
			<span id="unPayBill">￥0.0</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">实际带票金额</div>
		<div class="profile-info-value">
			<span id="factPayBill">￥0.0</span>
		</div>
	</div>	
	<div class="profile-info-row">
		<div class="profile-info-name">期间订单金额</div>
		<div class="profile-info-value">
			<span id="factPrice">￥0.0</span>
		</div>
	</div>
	<div class="profile-info-row">
		<div class="profile-info-name">期间订单差异金额</div>
		<div class="profile-info-value">
			<span id="factDiff">￥0.0</span>
		</div>
	</div>															
</div>
</div>
<script> 
</script>