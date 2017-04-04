<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/prettify/r224/prettify.min.js"></script>
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/prettify/r224/prettify.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/asserts/js/charts/morris.css">
<script src="${pageContext.request.contextPath}/asserts/js/charts/morris.js"></script>

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
	  <span><a class="btn1 export-excel-datas" href="#">导出Excel</a></span>
	</span>
	</div>
	<div class="search">
	    <form id="ydForm" action="chargeReportList.action" method="post">
	    <div class="search_style">
	      <ul class="search_content clearfix">
			  <li><label class="lf">代理商：</label><select id="agent-level" name="queryChargeReportDO.account" data-account="${queryChargeReportDO.account }"></select></li>      		
			  <li><label class="lf">运营商：</label><s:select name="queryChargeReportDO.type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>		      
		      <li><label class="lf">开始时间：</label><input type="text" id="formDateTime" name="queryChargeReportDO.from" value="<s:date name="queryChargeReportDO.from" format="yyyy-MM-dd"></s:date>" class="inline laydate-icon"/></li>
		      <li><label class="lf">结束时间：</label><input type="text" id="toDateTime" name="queryChargeReportDO.to" value="<s:date name="queryChargeReportDO.to" format="yyyy-MM-dd"></s:date>" class="inline laydate-icon" /></li>
		      <li style="width:20px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
	      </ul>
	      </div>
	    </form>
	</div>
	
	<form action="#" method="get" id="delete-form">
	<div style="max-height:200px;overflow:auto;">
		<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
			<tr>
				<th>代理商</th>
				<th>订单总数(笔)</th>				
			    <th>移动订单数(笔)</th>
			    <th>移动成功率(百分比)</th>
			    <th>联通订单数(笔)</th>
			    <th>联通成功率(百分比)</th>
			    <th>电信订单数(笔)</th>
			    <th>电信成功率(百分比)</th>    
				<th>消费总金额</th>
				<th>代理商余额</th>		    
			</tr>
			<s:iterator value="reportMapList" id="reportMap">	
				<tr>			
					<td>${reportMap.account}</td>
					<td>${reportMap.total_order_num}</td>
					<td>${reportMap.mobile_order_num}</td>
					<td>${reportMap.mobile_success_rate}</td>
					<td>${reportMap.union_order_num}</td>
					<td>${reportMap.union_success_rate}</td>
					<td>${reportMap.ctcc_order_num}</td>	
					<td>${reportMap.ctcc_success_rate}</td>
					<td>${reportMap.resume_price}</td>
					<td>${reportMap.remain_price}</td>						
				</tr>			
			</s:iterator>
		</table>
	</div>
	</form>

	<a id="switchView" class="btn1 switch-perspectives" href="#">切换视图</a>					
	<h3 class="header smaller lighter green"><i class="icon-signal"></i>&nbsp;&nbsp;统计信息</h3>	
	<div id="graph" style="height:400px;"></div>	
</div>


<form id="pageForm" action="chargeReportList.action" method="post" >
	<input type="hidden" name="queryChargeReportDO.account" value="<s:property value='queryChargeReportDO.account' />" />
	<input type="hidden" name="queryChargeReportDO.type" value="<s:property value='queryChargeReportDO.type' />" />
	<input type="hidden" name="queryChargeReportDO.from" value="<s:date name="queryChargeReportDO.from" format="yyyy-MM-dd"></s:date>"  />
	<input type="hidden" name="queryChargeReportDO.to" value="<s:date name="queryChargeReportDO.to" format="yyyy-MM-dd"></s:date>" />
</form>
<script>	
	var start = {
	    elem: '#formDateTime',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: false,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
	var end = {
	    elem: '#toDateTime',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59',
	    istime: false,
	    istoday: false,
	    choose: function(datas){
	        start.max = datas; //结束日选好后，重置开始日的最大日期
	    }
	};
	laydate(start);
	laydate(end);
	
	laydate.skin('danlan');
	
	KISSY.use("gprs/gprs-post,gprs/gprs-charts,gprs/gprs-calendar",function(S){    
		GPRS.Post.loadAgentLevel();
		GPRS.Charts.init();
		GPRS.Charts.switchChartView();
		GPRS.Calendar.init();
	});		
</script>