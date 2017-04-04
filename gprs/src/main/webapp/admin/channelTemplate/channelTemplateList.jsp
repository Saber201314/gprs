<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>

<style>
.bar{margin-top:10px;}
th,td{padding:6px !important;}
</style>

<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<div class="search">
	    <form id="ydForm" action="channelTemplateList.action" method="post">
	    <div class="search_style">
	      <ul class="search_content clearfix">
		      <li><label class="lf">名称：</label><input type="text" name="queryChannelDO.name" value="<s:property value='queryChannelDO.name' />" /></li>
		      <li><label class="lf">展示名称：</label><input type="text" name="queryChannelDO.alias"  value="<s:property value='queryChannelDO.alias' />" /></li>
		      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
	      </ul>
	      </div>
	    </form>
	</div>	
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th>名称</th>
			<th>用户名</th>
			<th>密码</th>
			<th width="350">密钥</th>
		    <th>添加时间</th>
		    <th colspan="2">操作</th>
		</tr>
		<s:iterator value="templateList">
			<tr>
				<td><s:property value="name" /></td>
				<td><s:property value="account" /></td>
				<td>${password}</td>
				<td>${sign}</td>
				<td><s:date format="yyyy-MM-dd" name="optionTime" /></td>
				<td> 
					<a href="../channelTemplateCode/channelTemplateCodeList.action?templateId=${id }">流量包编号</a>
				</td>
				<td> 
					<a href="../channelTemplate/publishChannelTemplate.action?id=${id }">编辑</a>
				</td>
			</tr>
		</s:iterator>
	</table>
</div>