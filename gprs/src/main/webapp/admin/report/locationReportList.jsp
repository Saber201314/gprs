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
<script src="${pageContext.request.contextPath}/resourse/assets/layer/extend/layer.ext.js" type="text/javascript"></script>
<style>
.radio, .checkbox {padding-left: 0px;}
label{width:90px;}
th,td{padding:6px !important;}
select{width:150px;}
.page-header{font-size:14px;}
.page-header{color:#69aa46;}
</style>

<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a class="btn1 export-excel-datas" href="#">导出Excel</a></span>
	</span>
	</div>
	<div class="search">

	    <div class="search_style">
	      <ul class="search_content clearfix">	
	      	 <form id="ydForm">
		      	  <li><label class="lf">流量包类型：</label>
		      	  	<s:select name="queryChargeReportDO.flowType" list="#{0:'全部',1:'常规包',2:'特殊包'}" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple">
		      	  	</s:select>
		      	  </li>
		      	  <li><label class="lf">号码类型：</label><s:select name="queryChargeReportDO.type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>   
			      <li><label class="lf">开始日期：</label><input type="text" id="formDateTime" name="queryChargeReportDO.from" readonly value="<s:date name="queryChargeReportDO.from" format="yyyy-MM-dd"></s:date>" class="inline laydate-icon"/></li>
		      	  <li><label class="lf">结束时间：</label><input type="text" id="toDateTime" name="queryChargeReportDO.to" readonly value="<s:date name="queryChargeReportDO.to" format="yyyy-MM-dd"></s:date>" class="inline laydate-icon" /></li>
		      </form>
		      <li style="width:90px;"><button class="btn_search sub_btn1">查询</button></li>
	      </ul>
	      </div>

	</div>
	<br/>
	<br/>
	<br/>
	<h3 class="header smaller lighter green"><i class="icon-signal"></i>&nbsp;&nbsp;统计信息</h3>
	<div id="graph" style="height:400px;"></div>	
</div>

<script>	
	var start = {
	    elem: '#formDateTime',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: false,
	    isclear:false,
	    istoday: true,
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
	    isclear:false,
	    istoday: false,
	    choose: function(datas){
	        start.max = datas; //结束日选好后，重置开始日的最大日期
	    }	    
	};	
	
	laydate(start);
	laydate(end);
	
	$("#formDateTime").val(laydate.now());
	$("#toDateTime").val(laydate.now(+1));
	laydate.skin('danlan');
	
	KISSY.use("gprs/gprs-charts",function(S){
		GPRS.Charts.loadLocationReportList();
	});		
</script>