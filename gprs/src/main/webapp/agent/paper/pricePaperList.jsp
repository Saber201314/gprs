<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<s:set name="menu_open" value="6"></s:set>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
.radio, .checkbox {padding-left: 0px;}
label{width:80px;}
</style>
<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a href="#" class="delete-list btn1">删除</a></span>
	  <span><a href="publishPricePaper.action" class="btn1">添加报价单</a></span>
	</span>
	</div>
	<div class="errorMsg"><s:actionerror /></div>
	<div class="search">
	    <form id="ydForm" action="pricePaperList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
			      <li><label class="lf">名称：</label><input type="text" name="queryPricePaperDO.name" value="<s:property value='queryPricePaperDO.name' />" /></li>
			      <li><label class="lf">展示名称：</label><input type="text" name="queryPricePaperDO.alias"  value="<s:property value='queryPricePaperDO.alias' />" /></li>
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	</div>
	
	<form action="deletePaperList.action" method="get" id="delete-form">
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
		    <th width="20"><input type="checkbox" class="select-all" /></th>
			<th>名称</th>
			<th>展示名称</th>
		    <th>添加时间</th>
		    <th>备注</th>
			<th width="10%">操作</th>
		</tr>
		<s:iterator value="paperList">
			<tr>
			    <td><input type="checkbox" name="queryPricePaperDO.idList" class="check-item" value="<s:property value='id' />" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="alias" /></td>
				<td><s:date format="yyyy-MM-dd" name="optionTime" /></td>
				<td>${memo}</td>
				<td> 
					<a href="../paper/publishPricePaper.action?id=<s:property value='id' />">编辑</a>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="6"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>
<form id="pageForm" action="pricePaperList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryPricePaperDO.name" value="<s:property value='queryPricePaperDO.name' />" />
    <input type="hidden" name="queryPricePaperDO.alias"  value="<s:property value='queryPricePaperDO.alias' />" />
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