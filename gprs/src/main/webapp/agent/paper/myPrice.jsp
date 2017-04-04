<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%-- <s:set name="menu_open" value="4"></s:set> --%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
th,td{padding:6px !important;}
dt{font-size:14px;}
</style>
<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<div class="upload">
		<dl>
			<dt>全国流量</dt>
			<dd>
				<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
               		<tr>
              				<th width="40%">名称</th>
              				<th>包含流量</th>
              				<th>标准价格</th>
              				<th>我的折扣</th>
              			</tr>
               		<s:iterator value="packageList">
               			<s:if test="locationType==1">
               			<tr>
               				<td>${alias }</td>
               				<td>${amount }M</td>
               				<td>${money }元</td>
               				<td>${discount }折</td>
               			</tr>
               			</s:if>			              
               		</s:iterator>
               	</table>
			</dd>
			<dt>省内流量</dt>
			<dd>
				<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
               		<tr>
              				<th width="40%">名称</th>
              				<th>包含流量</th>
              				<th>标准价格</th>
              				<th>我的折扣</th>
              			</tr>
               		<s:iterator value="packageList">
               			<s:if test="locationType==2">
               			<tr>
               				<td>${alias }</td>
               				<td>${amount }M</td>
               				<td>${money }元</td>
               				<td>${discount }折</td>
               			</tr>
               			</s:if>			              
               		</s:iterator>
               	</table>
			</dd>
		</dl>	  
	</div>	
</div>
