<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<jsp:include page="payBillInfo.jsp"></jsp:include>
<style>
.radio, .checkbox {padding-left: 0px;}
label{width:80px;}
th,td{padding:6px !important;}
li {padding-top:5px !important;}
</style>

<div id="screen" class="page-content">
	<div class="bar1">
		<span style="float:right;">
		  <span><a class="btn1 generate_bill" href="#">生成账单</a></span>		
		  <span><a class="btn1 export-excel-datas" href="#">导出Excel</a></span>
		</span>
	</div>	
	<%-- <div class="errorMsg"><s:actionerror /></div> --%>
	<div class="search">
	    <form id="ydForm" action="payLogList.action" method="post">
	    <div class="search_style">
	      <ul class="search_content clearfix">	
	      	  <input type="hidden" id="agent_account" value="${queryPayLogDO.account }"/>		  
			  <li><label class="lf">代理商：</label><select id="agent-level" name="queryPayLogDO.account"></select></li>	
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
		    <th width='150'>盈利金额</th> 
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
				<td>${profit}元</td>	
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
			<td colspan="11"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
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
<script src="${pageContext.request.contextPath}/asserts/js/gprs/gprs-utils.js" type="text/javascript"></script>

<script>
	var index = 0;
	$(".generate_bill").click(function(){
		$("#startTime_balance").text("￥0.0");
	 	$("#endTime_balance").text("￥0.0");
	 	$("#remittance").text("￥0.0");
	 	$("#consume").text("￥0.0");
	 	$("#diff").text("￥0.0");
	 	$("#accounting_status").text("无");
	 	$("#refund").text("￥0.0");	
	 	
	 	$("#payBill").text("￥0.0");
	 	$("#unPayBill").text("￥0.0");
	 	$("#factPayBill").text("￥0.0");
	 	$("#factPrice").text("￥0.0");
	 	
		var account = $("[name='queryPayLogDO.account']").val();
		if(account == null || account == ""){
			layer.msg("生成核算数据需要选择代理商");
			return;
		}
		var from = $("[name='queryPayLogDO.from']").val();
		if(from == null){
			layer.msg("生成核算数据需要选择一个时间段");
			return;		
		}
		var to = $("[name='queryPayLogDO.to']").val();
		if(to == null){
			layer.msg("生成核算数据需要选择一个时间段");
			return;		
		}		
		layer.confirm('确定生成账单吗？', {
		  btn: ['确定','取消'], //按钮
		  icon:3
		}, function(){	
		layer.closeAll();		
	    layer.open({
		    type: 1,
			title:'账单统计明细',
			area: ['500px','630px'],
			skin:'layui-layer-lan',
			shadeClose: true,
			content: $('#pay-bill-content')			    			               			
		 });		
		 
		  var params = [];
		  params.push("queryPayLogDO.account=");
		  params.push(account);
		  params.push("&");
		  params.push("queryPayLogDO.from=");
		  params.push(from);
		  params.push("&");  
		  params.push("queryPayLogDO.to=");
		  params.push(to);
		  
		  index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
		  
		  $.ajax({url:"showPayBillInfo.action",
		 		 data:params.join(""),
		 		 dataType:"json",
		 		 success:function(data){
		 		 layer.close(index);		 		 
		 		 if(data != null){
		 		 	$("#compayName").text(data.name);
		 		 	$("#account1").text(data.account);
		 		 			 			 		 	
		 		 	if(data.startBalance == null){
		 		 		data.startBalance = "0.0";
		 		 	}
		 		 	data.startDate = data.startDate.replace("T"," ");
		 		 	var startTime_balance = data.startDate + "     ￥" + data.startBalance;		 		 	
		 		 	$("#startTime_balance").text(startTime_balance);
		 		 	
		 		 	if(data.endBalance == null){
		 		 		data.endBalance = "0.0";
		 		 	}	
		 		 	data.endDate = data.endDate.replace("T"," ");	 		 		 		 	
		 		 	var endTime_balance = data.endDate + "         ￥" + data.endBalance;	 		 	
		 		 	$("#endTime_balance").text(endTime_balance);
		 		 	if(data.remittance == null){
		 		 		data.remittance = "0.0";
		 		 	}
		 		 	$("#remittance").text("￥"+ data.remittance);
		 		 	if(data.remittance == null){
		 		 		data.remittance = "0.0";
		 		 	}
		 		 	$("#consume").text("￥"+ data.consume);
		 		 	if(data.diff == null){
		 		 		data.diff = "0.0";
		 		 	}	 		 	
		 		 	$("#diff").text(data.diff);
		 		 	
		 		 	if(data.status == 1){
		 		 		data.status = "正常";
		 		 	}else{
		 		 		data.status = "异常";
		 		 	}
		 		 	$("#accounting_status").text(data.status);
		 		 	if(data.refund == null){
		 		 		data.refund = "0.0";
		 		 	}	 		 	
		 		 	$("#refund").text("￥"+ data.refund);
		 		 	
		 		 	if(data.payBill == null){
		 		 		data.payBill = "0.0";
		 		 	}	 		 	
		 		 	$("#payBill").text("￥"+ data.payBill);
		 		 	
		 		 	if(data.unPayBill == null){
		 		 		data.unPayBill = "0.0";
		 		 	}	 		 	
		 		 	$("#unPayBill").text("￥"+ data.unPayBill);	
		 		 	
		 		 	if(data.factPayBill == null){
		 		 		data.factPayBill = "0.0";
		 		 	}	 		 	
		 		 	$("#factPayBill").text("￥"+ data.factPayBill);	
		 		 	
		 		 	if(data.factPayBill == null){
		 		 		data.factPayBill = "0.0";
		 		 	}	 		 	
		 		 	$("#factPrice").text("￥"+ parseFloat(data.factMoney).toFixed(2));			 		 		 		 		 		 	
		 		 }
		 }});			
			
		});	   		 
	});
	
	var start = {
	    elem: '#start',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: true,
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
	    istoday: true,
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
		//GPRS.Post.loadAgentLevel();
		GPRS.Calendar.init();
	}); 
</script>