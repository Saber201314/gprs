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
	<div class="bar1">
		<span style="float:right;">
		  <span><a class="btn1 export-excel-datas" href="#">导出Excel</a></span>
		</span>
	</div>	
	<%-- <div class="errorMsg"><s:actionerror /></div> --%>
	<div class="search">
	    <form id="ydForm" action="payLogList.action" method="post">
	    <div class="search_style">
	      <ul class="search_content clearfix">			  
			  <li><label class="lf">代理商：</label><select id="agent-level" name="queryPayLogDO.account" data-account="${queryPayLogDO.account }"></select></li>	
			  <li><label class="lf">手机号码：</label><input type="text" name="queryPayLogDO.mobile" value="<s:property value='queryPayLogDO.mobile' />" /></li>  		
		      <li><label class="lf">开始时间：</label><input type="text" name="queryPayLogDO.from" value="<s:date name="queryPayLogDO.from"></s:date>" id="start" class="inline laydate-icon"  /></li>
		      <li><label class="lf">结束时间：</label><input type="text" name="queryPayLogDO.to" value="<s:date name="queryPayLogDO.to"></s:date>" id="end" class="inline laydate-icon" /></li>
		      <li><label class="lf">操作状态：</label><s:select name="queryPayLogDO.status" list="#{1:'充值成功',0:'充值中',-1:'已退款',-2:'充值失败' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
		      <li style="width:20px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
	      </ul>
	      </div>
	    </form>
	</div>
	
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
			<th>代理商</th>
			<th>类别</th>
			<th>流水号</th>
		    <th>折扣</th>				
		    <th width='150'>扣费金额</th>
		    <th width='150'>剩余金额</th>
		    <th>代理商订单号</th>
		    <th>明细</th>
		    <th>扣费时间</th>
		    <th width='130'>操作状态</th>
		</tr>
		<s:iterator value="payLogList">
			<tr>
				<td>${account}</td>
				<td>
					<s:if test="type==1">流量充值</s:if>
					<s:elseif test="type==2">流量池费用</s:elseif>
				</td>
				<td>${orderId}</td>
				<td>${discount}</td>				
				<td>
					<s:if test="status==1">
					<span class="label label-success arrowed-in arrowed-in-right">-${money }元</span>
					</s:if>
					<s:elseif test="status==0">
					<span class="label label-info arrowed-right arrowed-in">-${money }元</span>
					</s:elseif>
					<s:elseif test="status==-1">
					<span class="label label-danger arrowed">+${0-money }元</span>
					</s:elseif>						
					<s:else>
					<span class="label arrowed">-${money }元</span>
					</s:else>
				</td>
				<td>
				<s:if test="status==1">
					<span class="label label-success arrowed-in arrowed-in-right">${balance }元</span>
				</s:if>
				<s:elseif test="status==0">
					<span class="label label-info arrowed-right arrowed-in">${balance }元</span>
				</s:elseif>
				<s:elseif test="status==-1">
					<span class="label label-danger arrowed">${balance }元</span>
				</s:elseif>
				<s:else>
					<span class="label arrowed">${balance }元</span>
				</s:else>
				</td>
				<td>${agentOrderId}</td>															
				<td>${memo}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="optionTime" /></td>
				<td>
					<s:if test="status==1"><span class="label label-success arrowed-in arrowed-in-right">充值成功</span></s:if>
					<s:elseif test="status==0"><span class="label label-info arrowed-right arrowed-in">充值中</span></s:elseif>
					<s:elseif test="status==-2"><span class="label arrowed">充值失败</span></s:elseif>
					<s:else><span class="label label-danger arrowed">已退款</span></s:else>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="10"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
</div>
<form id="pageForm" action="payLogList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryPayLogDO.account" value="<s:property value='queryPayLogDO.account' />"/>
	<input type="hidden" name="queryPayLogDO.mobile" value="<s:property value='queryPayLogDO.mobile' />"/>
	<input type="hidden" name="queryPayLogDO.from" value="<s:date name="queryPayLogDO.from"></s:date>"/>
	<input type="hidden" name="queryPayLogDO.to" value="<s:date name="queryPayLogDO.to"></s:date>"/>
	<input type="hidden" name="queryPayLogDO.status" value="<s:property value='queryPayLogDO.status' />"/>
</form>
<script>
	var start = {
	    elem: '#start',
	    format: 'YYYY-MM-DD hh:mm:ss',
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
	    format: 'YYYY-MM-DD hh:mm:ss',
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
	function goDestinePage(allPage){
		var gotoNum=document.getElementById("gotoNum").value;	
		if(gotoNum >= allPage){
			goPage(allPage);
		}else{
			goPage(gotoNum);
		}
	}	
 	KISSY.use("gprs/gprs-post,gprs/gprs-calendar",function(S){
		GPRS.Post.exportChargeLogExcel();
		GPRS.Post.loadAgentLevel();
		GPRS.Calendar.init();
	}); 
</script>