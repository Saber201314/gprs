<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<s:set name="menu_open" value="5"></s:set>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
th,td{padding:6px !important;}
table{margin-top:20px;}
</style>
<div id="screen" class="page-content">
	<span class="title_name">广告列表</span>
	<div class="errorMsg"><s:actionerror /></div>
	
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<th>名称</th>
			<th>尺寸</th>
			<th  width="12%">操作</th>
			
		</tr>
		<s:iterator value="advertiseList">
			<tr>
				<td  class="align-left">${name }</td>
				<td>${size}</td>
				<td><a href="advertisePicList.action?advertiseId=${id }">查看内容</a></td>
			</tr>
		</s:iterator>
	</table>
</div>
