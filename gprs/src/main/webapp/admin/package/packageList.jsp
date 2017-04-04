<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
label{width:90px;}
select{width:150px;}
</style>
<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a href="#" class="delete-list btn1">删除</a></span>
	  <span><a href="publishPackage.jsp" class="btn1">添加流量包</a></span>
	</span>
	</div>
	<div class="errorMsg"><s:actionerror /></div>
	<div class="search">
	    <form id="ydForm" action="packageList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
			      <li><label class="lf">名称：</label><input type="text" name="queryPackageDO.name" value="<s:property value='queryPackageDO.name' />" /></li>
			      <li><label class="lf">展示名称：</label><input type="text" name="queryPackageDO.alias"  value="<s:property value='queryPackageDO.alias' />" /></li>
			      <li><label class="lf">流量值：</label><input type="text" name="queryPackageDO.amount"  value="<s:property value='queryPackageDO.amount' />"  />M</li>
			      <li><label class="lf">运营商类型：</label><s:select name="queryPackageDO.type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select> </li>
				  <li><label class="lf">流量类型：</label><s:select name="queryPackageDO.locationType" list="#{1:'全国流量',2:'省内流量' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	</div>
	
	<form action="deletePackageList.action" method="get" id="delete-form">
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
		    <th width="20px"><input type="checkbox" class="select-all" /></th>
			<th>名称</th>
			<th>展示名称</th>
			<th>流量值（M）</th>
			<th>标准售价（元）</th>
			<th>运营商类型</th>
			<th>流量类型</th>
		    <th>支持地区</th>
		    <th>添加时间</th>
			<th>操作</th>
		</tr>
		<s:iterator value="packageList">
			<tr>
			    <td><input type="checkbox" name="queryPackageDO.idList" class="check-item" value="<s:property value='id' />" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="alias" /></td>
				<td><s:property value="amount" /></td>
				<td><s:property value="money" /></td>
				<td>
					<s:if test="%{type == 1}">移动</s:if>
				    <s:if test="%{type == 2}">联通</s:if>
				    <s:if test="%{type == 3}">电信</s:if>
				</td>
				<td>
					<s:if test="%{locationType == 1}">全国流量</s:if>
				    <s:if test="%{locationType == 2}">省内流量</s:if>
				</td>
				<td>${locations }</td>
				<td><s:date format="yyyy-MM-dd" name="optionTime" /></td>
				<td> 
					<a href="../package/modifyPackage.action?id=<s:property value='id' />">编辑</a>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="10"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>
<form id="pageForm" action="packageList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryPackageDO.name" value="<s:property value='queryPackageDO.name' />" />
    <input type="hidden" name="queryPackageDO.alias"  value="<s:property value='queryPackageDO.alias' />" />
    <input type="hidden" name="queryPackageDO.amount"  value="<s:property value='queryPackageDO.amount' />" />
    <input type="hidden" name="queryPackageDO.type" value="<s:property value='queryPackageDO.type' />" />  
    <input type="hidden" name="queryPackageDO.locationType" value="<s:property value='queryPackageDO.locationType' />" />  
    <input type="hidden" name="queryPackageDO.status" value="<s:property value='queryPackageDO.status' />" />  
</form>
<script>
	function goPage(pageNo){
	var form=document.getElementById("pageForm");
	var page=document.getElementById("page.pageNo");
	page.value=pageNo;
	form.submit();
	return false;
	}
	
	KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.deleteAll();
	});
</script>