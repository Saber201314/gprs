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
th,td{padding:6px !important;}
select{width:150px;}
</style>
<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a href="#" class="delete-list btn1">删除</a></span>
	  <span><a class="btn1" href="publishSuite.jsp">添加流量池</a></span>
	</span>
	</div>
	<div class="search">
	    <form id="ydForm" action="suiteList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
			      <li>号码类型：<s:select name="querySuiteDO.type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
			      <li>流量类型：<s:select name="querySuiteDO.locationType" list="#{1:'全国流量',2:'省内流量' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
			      <li style="width:20px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	</div>
	
	<form action="deleteSuiteList.action" method="get" id="delete-form">
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th width="20px"><input type="checkbox" class="select-all select-all_1" /></th>
			<th>名称</th>
			<th>展示名称</th>
		    <th>流量值</th>
		    <th>运营商类型</th>
			<th>流量类型</th>
		    <th width="30%">支持地区</th>
		    <th>租金</th>
		    <th>有效期</th>
		    <th>月底清零</th>
		    <th width="10%">操作</th>
		</tr>
		<s:iterator value="suiteList">
			<tr>
				<td><input type="checkbox" class="check-item" name="querySuiteDO.idList" value="${id }" /></td>
				<td>${name}</td>
				<td>${alias}</td>
				<td>${amount }M</td>
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
				<td>
					<s:if test="clearMonthEnd==1">清零</s:if>
					<s:else>不清零</s:else>
				</td>
				<td> 
					<a href="../suite/modifySuite.action?id=<s:property value='id' />">编辑</a>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="11"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>
<form id="pageForm" action="suiteList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="querySuiteDO.type" value="<s:property value='querySuiteDO.type' />" />	      
	<input type="hidden" name="querySuiteDO.locationType" value="<s:property value='querySuiteDO.locationType' />" />
</form>
<script>
	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("page.pageNo");
		page.value=pageNo;
		form.submit();
		return false;
	}
	KISSY.use("gprs/gprs-post,gprs/gprs-calendar",function(S){
		GPRS.Post.loadAgentLevel();
		GPRS.Post.deleteAll();
	});
</script>