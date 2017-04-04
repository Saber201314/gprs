<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>

<jsp:include page="chargeChartList.jsp"></jsp:include>
<jsp:include page="../agent/editChargeAgent.jsp"></jsp:include>
<jsp:include page="channelResumeList.jsp"></jsp:include>

<style>
.radio, .checkbox {padding-left: 0px;}
label{width:80px;}
th,td{padding:6px !important;}
.badge-info{color:#ffffff !important;}
.badge-info:hover{color:#F60 !important;}
li {padding-top:5px !important;}
</style>

<div id="screen" class="page-content">	
	<div class="bar1">
		<span style="float:right;">
		   <span><a class="btn1 charge_info" href="#">财务统计</a></span>
		   <span><a class="btn1 resume_info" href="#">通道消费统计</a></span>		   
		   <span><a class="btn1 export-excel-datas" href="#">导出Excel</a></span>
		</span>
	</div>	
	<div class="errorMsg"><s:actionerror /></div>
	<div class="search">
	    <form id="ydForm" action="agentChargeList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
		      	  <input type="hidden" id="agent_account" value="${queryAgentChargeLogDO.account }"/>
				  <li><label>代理商：</label><select id="agent-level" name="queryAgentChargeLogDO.account"></select></li>	      		
			      <li><label>开始时间：</label><input type="text" name="queryAgentChargeLogDO.from" value="<s:date name="queryAgentChargeLogDO.from" format="yyyy-MM-dd"></s:date>" id="start" class="inline laydate-icon"  /></li>
			      <li><label>结束时间：</label><input type="text" name="queryAgentChargeLogDO.to" value="<s:date name="queryAgentChargeLogDO.to" format="yyyy-MM-dd"></s:date>" id="end" class="inline laydate-icon"  /></li>
			      <li><label>支付方式：</label><s:select name="queryAgentChargeLogDO.payType" list="#{1:'对公账号收款',2:'对公支付宝收款',3:'对私账号收款',4:'对私支付宝收款',5:'账号金额转移',6:'授权信用加款',7:'充值未到账退款',8:'账号测试加款',9:'其他原因充扣款' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
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
		    <th>编辑</th>
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
				<td><a class="badge badge-info edit-charge-triggle" href="javascript:void(0);" onclick="editAgentChargeData(<s:property value='id' />)">编辑</a></td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="7"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
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
	$(".resume_info").click(function(){	
	    layer.open({
	    type: 1,
		title:'通道消费金额统计表【请尽量保证上游接入折扣的正确性】',
		area: ['1000px','680px'],
		fix: false,
    		shadeClose: true, //点击遮罩关闭层
		content: $('.channel-resume-content')				
		});		
	});
	function editAgentChargeData(id){
	    layer.open({
	    type: 1,
		title:'编辑充值记录',
		area: ['450px','450px'],
		fix: false,
    		shadeClose: true, //点击遮罩关闭层
		content: $('.agent_charge')				
		});
  			
 		var params = [];
 			params.push("queryAgentChargeLogDO.id=");
			params.push(id);
						
 		$.ajax({
			url : "getSingleAgentChargeData.action",
			type : "post",
			data: params.join(""),
			dataType : 'json',
			cache : false,
			success : function(data) {
				if(data && data.length > 0) {
					$("#chargeId").val(data[0].id);
					$("#account").val(data[0].account);
					$("#money").val(data[0].money);
					$("#payType").val(data[0].payType);
					var optionTime = data[0].optionTime;
					optionTime = optionTime.replace("T"," ");
					$("#validDate").val(optionTime);
					$("#memo").text(data[0].memo);				
					}
				}
			});	 									
		}
	
	$('.charge_info').on('click', function(){
   		    layer.open({
   		    type: 1,
   			title:'代理商充值统计',
   			area: ['1000px','650px'],
        	shadeClose: true, //点击遮罩关闭层
   			content: $('.charge-info-content')
   		  });
   		  		  
		$.ajax({
			url : "getChargeChartsList.action",
			type : "post",
			dataType : 'json',
			cache : false,
			success : function(data) {
				if(data && data.length > 0) {
					var html = [];
					var total = 0;
					for(var i =0;i<data.length;i++){
						total += data[i].charge_price;							
					}
					html.push("<tr>");
					html.push("<td>总计</td>");
					html.push("<td></td>");
					html.push("<td><span class='label label-success arrowed-in arrowed-in-right'>￥"+parseFloat(total).toFixed(2)+"</span></td>");
					html.push("</tr>");	
									
					for(var i=0;i<data.length;i++){
						html.push("<tr>");
						html.push("<td>"+data[i].name+"</td>");
						html.push("<td>"+data[i].account+"</td>");
						html.push("<td><span class='label label-success arrowed-in arrowed-in-right'>￥"+parseFloat(data[i].charge_price).toFixed(2)+"</span></td>");
						html.push("</tr>");
									
					}					
					$("#charge_info_table tbody").append(html.join("")); 	
	  				$("#charge_info_table_header").width($("#charge_info_table tbody").width()-20);
	  				$("#charge_info_table_header tbody tr th").each(function(i,k){
	  					$(this).width($("#charge_info_table tbody tr:eq(0) td:eq("+i+")").width());		    						
	  				});	
				}
			}
		});	   		  
	});	

	var start = {
	    elem: '#start',
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
	    elem: '#end',
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

	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("page.pageNo");
		page.value=pageNo;
		form.submit();
		return false;
	}
	
	KISSY.use("gprs/gprs-post,gprs/gprs-calendar",function(S){
		//GPRS.Post.loadAgentLevel();
		GPRS.Post.exportAgentChargeDatas();
		GPRS.Calendar.init();
	});
</script>