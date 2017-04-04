<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style>
label{width:80px;}
btn{text-align:center;}
</style>

<div class="charge-info-content" style="display:none;">
	<div class="bar">
	</div>	
	<div class="errorMsg"><s:actionerror /></div>
		<div class="search">
			<form id="syForm2">
		    <div class="search_style">
			      <ul  class="search_content clearfix">
			      	<li>代理商：<select id="agent-levels" name="queryAgentChargeLogDO.account" data-account="${queryAgentChargeLogDO.account }"></select></li>	
			      	<li>开始时间：<input type="text" name="queryAgentChargeLogDO.from" value="<s:date name="queryAgentChargeLogDO.from" format="yyyy-MM-dd"></s:date>" id="start1" class="inline laydate-icon"  /></li>
			      	<li>结束时间：<input type="text" name="queryAgentChargeLogDO.to" value="<s:date name="queryAgentChargeLogDO.to" format="yyyy-MM-dd"></s:date>" id="end1" class="inline laydate-icon"  /></li>
				  	<li style="width:90px;"><a id="query_charge_info" class="btn_search btn">查询</a></li>
			      </ul>
		    </div>
		    </form>
		</div>
	<div>
	<table id="charge_info_table_header" class="table table-striped table-bordered table-hover" style="table-layout:fixed;">
		<tr>
			<th>代理商名称</th>
			<th>代理商账号</th>
		    <th>充值金额(元)</th>
		</tr>	
	</table>
	<div style="height:450px;overflow:auto;">
		<table id="charge_info_table" class="table table-striped table-bordered table-hover" style="table-layout:fixed;margin-top:-1px;">
			<tbody>
			</tbody>
		</table>
	</div>
</div>			

</div>
<script src="${pageContext.request.contextPath}/asserts/js/gprs/gprs-utils.js" type="text/javascript"></script>
<script>
	var start = {
	    elem: '#start1',
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
	    elem: '#end1',
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
	
	
	KISSY.use("gprs/gprs-post",function(S){
		//GPRS.Post.loadAgentLevels();
		GPRS.Post.queryChargeInfo();		
	});		
</script>