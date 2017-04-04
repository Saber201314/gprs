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
	    <form id="ydForm" action="channelLogList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
			      <li><label class="lf">手机号码：</label><input type="text" name="queryChannelLogDO.mobile" value="<s:property value='queryChannelLogDO.mobile' />" /></li>
			      <li><label class="lf">开始时间：</label><input type="text" name="queryChannelLogDO.from" value="<s:date name="queryChannelLogDO.from" format="yyyy-MM-dd"></s:date>" id="start" class="inline laydate-icon" /></li>
			      <li><label class="lf">结束时间：</label><input type="text" name="queryChannelLogDO.to" value="<s:date name="queryChannelLogDO.to" format="yyyy-MM-dd"></s:date>" id="end" class="inline laydate-icon"  /></li>
			      <li><label class="lf">充值通道：</label><s:select name="queryChannelLogDO.templateId" list="templateList" listKey="id" listValue="name" headerKey="" headerValue="" theme="simple"></s:select></li>
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
		<s:iterator value="channelLogList">
			<tr>
				<td>${mobile}</td>
				<td>${orderId}</td>
				<td>${templateName }</td>
				<td><p style="white-space: nowrap;overflow:hidden;text-overflow:ellipsis;max-width:1200px;" title="${response }">${response }</p></td>
				<td><s:date format="yyyy-MM-dd HH:mm" name="optionTime" /></td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="5"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>
<form id="pageForm" action="channelLogList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryChannelLogDO.mobile" value="<s:property value='queryChannelLogDO.mobile' />" />
	<input type="hidden" name="queryChannelLogDO.from" value="<s:date name="queryChannelLogDO.from" format="yyyy-MM-dd"></s:date>"  />
	<input type="hidden" name="queryChannelLogDO.to" value="<s:date name="queryChannelLogDO.to" format="yyyy-MM-dd"></s:date>" />
	<input type="hidden" name="queryChannelLogDO.templateId" value="<s:property value='queryChannelLogDO.templateId' />" />
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
	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("page.pageNo");
		page.value=pageNo;
		form.submit();
		return false;
	}
	KISSY.use("gprs/gprs-calendar",function(S){
		GPRS.Calendar.init();
	});
</script>