<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
.radio, .checkbox {padding-left: 0px;}
label{width:80px;}
th,td{padding:6px !important;}
</style>
<div id="screen" class="page-content">
	<div class="bar" style="padding-bottom:3px;">
	</div>
	
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<th>添加时间</th>
			<th>流量类型</th>
			<th>生效时间</th>
			<th>移动流量</th>
			<th>联通流量</th>
			<th>电信流量</th>
			<th>号码数量</th>
			<th>提交数量</th>
		</tr>
		<s:iterator value="batchChargeList">
			<tr>
				<td><s:date name="optionTime" format="yyyy-MM-dd HH:mm"></s:date></td>
				<td>
					<s:if test="locationType==1">全国流量</s:if>
					<s:else>省内流量</s:else>
				</td>
				<td>
					<s:if test="takeTime==0">立即生效</s:if>
					<s:else>次月生效</s:else>
				</td>
				<td>${packageAliasMobile}</td>
				<td>${packageAliasUnicom}</td>
				<td>${packageAliasTelecom}</td>
				<td><a href="exportBatchMobile.action?batchId=${id }">${mobileNum }</a></td>
				<td><a href="exportBatchMobile.action?batchId=${id }&submitStatus=1">${submitNum }</a></td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="8"><div><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
		</tr>
	</table>
</div>

<form id="pageForm" action="batchChargeList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
</form>
<script>
	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("page.pageNo");
		page.value=pageNo;
		form.submit();
		return false;
	}
</script>