<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<s:set name="menu_open" value="4"></s:set>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
th,td{padding:6px !important;}
</style>
<div id="screen" class="page-content">
	<div class="bar" style="margin-top:10px;">
	</div>	
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th>流量池</th>
		    <th>流量值</th>
		    <th>已用大小</th>
		    <th>运营商类型</th>
			<th>流量类型</th>
		    <th width="20%">支持地区</th>
		    <th>租金</th>
		    <th>有效期</th>
		    <th>生效时间</th>
		    <th>失效时间</th>
		    <th>月底清零</th>
		    <th>自动续费</th>
		    <th>当前状态</th>
		</tr>
		<s:iterator value="suiteOrderList">
			<tr>
				<td>${suiteName}</td>
				<td>${amount }M</td>
				<td>${amountUsed }M</td>
				<td>
					<s:if test="type==1">移动</s:if>
					<s:elseif test="type==2">联通</s:elseif>
					<s:else>电信</s:else>
				</td>
				<td>
					<s:if test="locationType==1">全国流量</s:if>
					<s:else>省内流量</s:else>
				</td>
				<td>${locations }</td>
				<td>${money }元</td>
				<td>${validate }天</td>
				<td><s:date format="yyyy-MM-dd" name="beginTime"></s:date></td>
				<td><s:date format="yyyy-MM-dd" name="availableTime"></s:date></td>
				<td>
					<s:if test="clearMonthEnd==1">清零</s:if>
					<s:else>不清零</s:else>
				</td>
				<td>
					<s:if test="autoCharge==1">自动续费</s:if>
					<s:else>不续费</s:else>
				</td>
				<td>
					<s:if test="status==1">生效中</s:if>
					<s:else>未生效</s:else>
				</td>
			</tr>
		</s:iterator>
	</table>
</div>