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
			<dt>
				<h4 class="header smaller lighter blue"><i class="icon-list"></i>&nbsp;&nbsp;我的报价列表</h4>
			</dt>
			<dd>
				<table class="table table-striped table-bordered table-hover">
               		<tr>
              				<th>产品名称</th>
              				<th>流量规格</th>
              				<th>我的折扣</th>
              			</tr>
               		<s:iterator value="pagePaperList">
               			<tr>
               				<td><span class="label label-success arrowed-in arrowed-in-right" style="font-weight:bold;">${name}</span></td>
               				<td><span style="color:#438eb9">${amount}</span></td>
               				<td><span class="badge badge-pink">${discount} 折</span></td>
               			</tr>		              
               		</s:iterator>
               	</table>
			</dd>
		</dl>	  
	</div>	
</div>
