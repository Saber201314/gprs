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
	<div class="bar">
	</div>
	<div class="errorMsg"><s:actionerror /></div>
	<div class="search">
	    <form id="ydForm" action="agentChargeList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
				  <li>代理商：<select id="agent-level" name="queryAgentChargeLogDO.account" data-account="${queryAgentChargeLogDO.account }"></select></li>	      		
			      <li>开始时间：<input type="text" name="queryAgentChargeLogDO.from" value="<s:date name="queryAgentChargeLogDO.from" format="yyyy-MM-dd"></s:date>" id="start" class="inline laydate-icon"  /></li>
			      <li>结束时间：<input type="text" name="queryAgentChargeLogDO.to" value="<s:date name="queryAgentChargeLogDO.to" format="yyyy-MM-dd"></s:date>" id="end" class="inline laydate-icon"  /></li>
			      <li>支付方式：<s:select name="queryAgentChargeLogDO.payType" list="#{1:'对公账号收款',2:'对公支付宝收款',3:'对私账号收款',4:'对私支付宝收款',5:'账号金额转移',6:'授权信用加款',7:'充值未到账退款',8:'账号测试加款',9:'其他原因充扣款' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	</div>
	
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th>代理商</th>
		    <th>充值金额</th>
		    <th>充值后余额</th>
		    <th>充值方式</th>
		    <th>充值时间</th>
		    <th>备注</th>
		</tr>
		<s:iterator value="agentChargeLogList">
			<tr>
				<td>${account}</td>
				<s:if test="money>0">
					<td><span class="label label-info arrowed-right arrowed-in">${money }元</span></td>
				</s:if>
				<s:else>
					<td><span class="label label-danger arrowed">${money }元</span></td>
				</s:else>			
				<s:if test="balance==null">
					<td></td>
				</s:if>
				<s:else>
					<td><span class="label label-success arrowed-in arrowed-in-right">${balance }元</span></td>				
				</s:else>
				<td>
					<s:if test="payType==1">对公账号收款</s:if>
					<s:elseif test="payType==2">对公支付宝收款</s:elseif>
					<s:elseif test="payType==3">对私账号收款</s:elseif>
					<s:elseif test="payType==4">对私支付宝收款</s:elseif>
					<s:elseif test="payType==5">账号金额转移</s:elseif>
					<s:elseif test="payType==6">授权信用加款</s:elseif>	
					<s:elseif test="payType==7">充值未到账退款</s:elseif>
					<s:elseif test="payType==8">账号测试加款</s:elseif>		
					<s:elseif test="payType==9">其他原因充扣款</s:elseif>							
				</td>
				<td><s:date format="yyyy-MM-dd HH:mm" name="optionTime" /></td>
				<td>${memo }</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="6"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
</div>
<form id="pageForm" action="agentChargeList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryAgentChargeLogDO.account" value="<s:property value='queryAgentChargeLogDO.account' />" />
	<input type="hidden" name="queryAgentChargeLogDO.from" value="<s:date name="queryAgentChargeLogDO.from" format="yyyy-MM-dd"></s:date>"  />
	<input type="hidden" name="queryAgentChargeLogDO.to" value="<s:date name="queryAgentChargeLogDO.to" format="yyyy-MM-dd"></s:date>" />
	<input type="hidden" name="queryAgentChargeLogDO.payType" value="<s:property value='queryAgentChargeLogDO.payType' />" />
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
	KISSY.use("gprs/gprs-post,gprs/gprs-calendar",function(S){
		GPRS.Post.loadAgentLevel();
		GPRS.Calendar.init();
	});
</script>