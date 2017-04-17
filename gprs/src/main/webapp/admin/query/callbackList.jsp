<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- <s:set name="menu_open" value="3"></s:set> --%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
label{width:80px;}
th,td{padding:6px !important;}
li {padding-top: 5px !important;}
</style>

<div id="screen" class="page-content">
	<div class="bar">
	</div>
	
	<%-- <div class="errorMsg"><s:actionerror /></div> --%>
	<div class="search">
	    <form id="ydForm" action="callbackList.action" method="post">
	    <div class="search_style">
		      <ul  class="search_content clearfix">
		      	  <li><label class="lf">代理商：</label><select id="agent-level" name="account" data-account="${account }"></select></li>
			      <li><label class="lf">手机号码：</label><input type="text" name="mobile" value='${mobile}' /></li>
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
		<c:forEach var="item" items="${callbackList}">
			<tr>
				<td>${item.orderId}</td>
				<td>${item.mobile}</td>
				<td><p style="white-space: nowrap;overflow:hidden;text-overflow:ellipsis;max-width:400px;" title="${item.url }">${item.url }</p></td>
				<td><p style="white-space: nowrap;overflow:hidden;text-overflow:ellipsis;max-width:800px;" title="${item.request }">${item.request }</p></td>
				<td>${item.response }</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${item.optionTime }"/></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
</div>
<form id="pageForm" action="callbackList.action" method="post" >
	<input type="hidden" id="pageNo" name="pageNo" value="" />
	<input type="hidden" name="mobile" value="${mobile}" />
	<input type="hidden" name="account" value="${account}" />
</form>

<script src="${pageContext.request.contextPath}/asserts/js/gprs/gprs-utils.js" type="text/javascript"></script>
<script>
	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("pageNo");
		page.value=pageNo;
		form.submit();
		return false;
	}
	KISSY.use("gprs/gprs-post",function(S){
		/* GPRS.Post.loadAgentLevel(); */
	});
</script>