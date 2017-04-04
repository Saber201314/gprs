<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<s:set name="menu_open" value="3"></s:set>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
label{width:80px;}
th,td{padding:6px !important;}
</style>

<div id="screen" class="page-content">
	<div class="bar">
	</div>
	
	<div class="errorMsg"><s:actionerror /></div>
	<div class="search">
	    <form id="ydForm" action="callbackList.action" method="post">
	    <div class="search_style">
		      <ul  class="search_content clearfix">
		      	  <li><label class="lf">代理商：</label><select id="agent-level" name="queryCallbackDO.account" data-account="${queryCallbackDO.account }"></select></li>
			      <li><label class="lf">手机号码：</label><input type="text" name="queryCallbackDO.mobile" value="<s:property value='queryCallbackDO.mobile' />" /></li>
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	</div>
	
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th>订单号</th>		
			<th>手机号码</th>
			<th>回调地址</th>
		    <th>请求</th>
		    <th>返回</th>
		    <th>回调时间</th>
		</tr>
		<s:iterator value="callbackList">
			<tr>
				<td>${orderId}</td>
				<td>${mobile}</td>
				<td><p style="white-space: nowrap;overflow:hidden;text-overflow:ellipsis;max-width:400px;" title="${request }">${url }</p></td>
				<td><p style="white-space: nowrap;overflow:hidden;text-overflow:ellipsis;max-width:800px;" title="${request }">${request }</p></td>
				<td>${response }</td>
				<td><s:date format="yyyy-MM-dd HH:mm" name="optionTime" /></td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="6"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>
<form id="pageForm" action="callbackList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryCallbackDO.mobile" value="<s:property value='queryCallbackDO.mobile' />" />
	<input type="hidden" name="queryCallbackDO.account" value="<s:property value='queryCallbackDO.account' />" />
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
		GPRS.Post.loadAgentLevel();
	});
</script>