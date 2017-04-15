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
li {padding-top:5px !important;}
</style>
<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<%-- <div class="errorMsg"><s:actionerror /></div> --%>
	<div class="search">
	    <form id="ydForm" action="channelLogList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
			      <li>
			      	<label class="lf">手机号码：</label>
			      	<input type="text" name="mobile" value="${mobile}" />
			      </li>
			      <li>
			      	<label class="lf">开始时间：</label>
			      	<input type="text" name="from" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${from}"/>" id="start" class="inline laydate-icon" />
			      </li>
			      <li>
			      	<label class="lf">结束时间：</label>
			      	<input type="text" name="to" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${to}"/>" id="end" class="inline laydate-icon"  />
			      </li>
			      <li>
			      	<label class="lf">充值通道：</label>
			      	<select id="templateId" name="templateId">
			      		<option>请选择</option>
			      		<c:forEach var="item" items="${templateList}">
			      			<option value="${item.id }" >${item.name }</option>
			      		</c:forEach>
			      	</select>
			      	<%-- <s:select name="templateId" list="templateList" listKey="id" listValue="name" headerKey="" headerValue="" theme="simple"></s:select> --%>
			      </li>
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	</div>
	
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th>手机号码</th>
			<th>订单号</th>
			<th>充值通道</th>
		    <th width="70%">充值结果</th>
		    <th>充值时间</th>
		</tr>
		<c:forEach var="item" items="${channelLogList }">
			<tr>
				<td>${item.mobile}</td>
				<td>${item.orderId}</td>
				<td>${item.templateName }</td>
				<td><p style="white-space: nowrap;overflow:hidden;text-overflow:ellipsis;max-width:1200px;" title="${item.response }">${util.ascii2native(item.response)}</p></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${item.optionTime}"/></td>
			</tr>
		
		</c:forEach>
			
		<tr>
			<td colspan="5"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
</div>
<form id="pageForm" action="channelLogList.action" method="post" >
	<input type="hidden" id="pageNo" name="pageNo" value="" />
	<input type="hidden" name="mobile" value="${mobile }" />
	<input type="hidden" name="from" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${from}"/>"  />
	<input type="hidden" name="queryChannelLogDO.to" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${to}"/>" />
	<input type="hidden" name="templateId" value="${templateId }" />
</form>
<script>
	var start = {
	    elem: '#start',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
	var end = {
	    elem: '#end',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59',
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	        start.max = datas; //结束日选好后，重置开始日的最大日期
	    }
	};
	laydate(start);
	laydate(end);
	laydate.skin('danlan');
	$("#templateId").chosen({search_contains:true});
	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("pageNo");
		page.value=pageNo;
		form.submit();
		return false;
	}
	KISSY.use("gprs/gprs-calendar",function(S){
		GPRS.Calendar.init();
	});
</script>