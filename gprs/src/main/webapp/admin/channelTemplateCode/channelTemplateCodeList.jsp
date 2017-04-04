<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- <s:set name="menu_open" value="5"></s:set> --%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
.bar{margin-top:10px;}
</style>

<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a href="#" class="delete-list btn1">删除</a></span>
	  <span><a href="publishChannelTemplateCode.action?templateId=${templateId }" class="btn1">添加编码</a></span>
	</span>
	</div>
	
	<form action="deleteChannelTemplateCodeList.action" method="get" id="delete-form">
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th><input type="checkbox" class="select-all" /></th>
			<th>编码</th>
			<th>运营商类型</th>
			<th>区域</th>
			<th>流量类型</th>
			<th>流量值（M）</th>
			<th width="10%">操作</th>
		</tr>
		<s:iterator value="codeList">
			<tr>
				<td><input type="checkbox" name="idList" class="check-item" value="<s:property value='id' />" /></td>
				<td><s:property value="code" /></td>
				<td>
					<s:if test="%{type == 1}">移动</s:if>
				    <s:if test="%{type == 2}">联通</s:if>
				    <s:if test="%{type == 3}">电信</s:if>
				</td>
				<td><s:property value="location" /></td>
				<td>
					<s:if test="%{locationType == 1}">全国流量</s:if>
				    <s:if test="%{locationType == 2}">省内流量</s:if>
				</td>
				<td><s:property value="amount" /></td>
				<td> 
					<a href="../channelTemplateCode/publishChannelTemplateCode.action?id=<s:property value='id' />">编辑</a>
				</td>
			</tr>
		</s:iterator>
	</table>
	</form>
</div>
<script>
	KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.deleteAll();
	});
</script>