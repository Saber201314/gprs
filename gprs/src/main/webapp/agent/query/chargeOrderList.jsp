<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<jsp:include page="singleChargeOrder.jsp"></jsp:include>
<style>
label{width:80px;}
th,td{padding:6px !important;}
select{width:163px;}
li select,li input{width:163px;}
.laydate-icon{width:163px;}
li{padding-top:5px !important;}
.badge-info{color:#ffffff !important;}
.badge-info:hover{color:#F60 !important;}
</style>
<div id="screen" class="page-content">
	<div class="bar1">
		<span style="float:right;">
		  <span><a class="btn1 export-mobile" href="#">导出号码</a></span>
		  <span><a class="btn1 export-excel-datas" href="#">导出Excel</a></span>
		</span>
	</div>
	<div class="search">
	    <!-- <form id="ydForm" action="chargeOrderList.action" method="post"> -->
	    <div class="search_style">
		      <ul class="search_content clearfix">
				  <li><label class="lf">代理商：</label><select id="agent-level"></select></li>	      		
			      <li><label class="lf">手机号码：</label><input type="text" style="margin-left:0px;padding-bottom:3px;" id="mobile" /></li>
			      <li><label class="lf">归属地：</label><select id="location" class="terrority-select"></select></li>
			      
			      <li><label class="lf">开始时间：</label><input id="start" class="inline laydate-icon"/></li>
			      <li><label class="lf">结束时间：</label><input id="end" class="inline laydate-icon"  /></li>
			      <li><label class="lf">号码类型：</label><s:select id="type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
			      <li><label class="lf">流量值：</label><select id="amount" class="amount-select"></select></li>
			      <li><label class="lf">流量类型：</label><s:select id="locationType" class="locationType-select" list="#{1:'全国流量',2:'省内流量' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
			      <li><label class="lf">充值状态：</label><s:select id="submitStatus" list="#{0:'未提交',1:'充值中',3:'充值失败',2:'充值成功'}" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    <!-- </form> -->
	</div>
	
	<form action="#" method="get" id="delete-form">
	<table id="order_table" class="table table-striped table-bordered table-hover">
		<tr>
			<th width="20"><input type="checkbox" class="select-all select-all_1" /></th>
			<th>代理商</th>
			<th>手机号码</th>
		    <th>号码类型</th>
		    <th>流量类型</th>
		    <th>流量值</th>
		    <th>基础价格</th>
		    <th>扣费金额</th>
		    <th>充值时间</th>
		    <th>回调时间</th>
		    <th>充值方式</th>
		    <!-- <th>备注</th> -->
		    <th>充值结果</th>
		    <th style="max-width:120px;">操作</th>
		</tr>
		<tr id="pageTr" style="display:none;">
			<td colspan="13"><div align="left"><jsp:include page="../layout/paginatorNew1.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>

<script src="${pageContext.request.contextPath}/asserts/js/gprs/gprs-order-agent.js" type="text/javascript"></script>

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

	$("#start").val(laydate.now(-1,'YYYY-MM-DD 00:00:00'));
	$("#end").val(laydate.now(0,'YYYY-MM-DD 23:59:59'));
	
	laydate.skin('danlan');
		
	function showSingleOrderInfo(id){
	    layer.open({
	    type: 1,
		title:'订单详情',
		area: ['500px','590px'],
		skin:'layui-layer-lan',
		shift: 4,
		shadeClose: true,
		content: $('#single-page-content')			    			               			
	 });	
	 
	 $.ajax({url:"showSingleOrderInfo.action",
	 		 data:{"id":id},
	 		 dataType:"json",
	 		 success:function(data){
	 		 initOrderDetail(data);
	 }});
	}

	function getLocalTime(nS) { 
		return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " "); 
	} 
		
	function initOrderDetail(data){
		var compayName = data.name;
		if(compayName && compayName != ""){
			$("#compayName").text(compayName);		
		}else{
			$("#compayName").text("暂未配置公司名称");
		}	
		$("#account").text(data.account);
		var phone = data.phone;
		if(phone && phone != ""){
			$("#agentMobile").text(phone);
		}else{
			$("#agentMobile").text("暂未提供联系方式");
		}
		$("#orderId").text(data.id);
		$("#chargeMobile").text(data.mobile);
		var type = data.type;
		if(type == 1){
			type="移动";
		}else if(type == 2){
			type="联通";
		}else if(type == 3){
			type="电信";
		}		
		$("#chargeLocation").text(data.location +" " + type);
		var locationType = data.location_type;		
		if(locationType == 1){
			locationType = "全国流量";
		}else{
			locationType = "省内流量";
		}
		$("#locationType").text(locationType);
		
		$("#amount").text(data.amount + " M");
		$("#basePrice").text("￥" + data.money + "元");
		$("#chargePrice").text(data.discount_money?data.discount_money:0 + "元");
		
		$("#chargeTime").text(data.option_time);
		var submitType = data.submit_type;
		if(submitType == 1){
			submitType = "代理商直充";
		}else if(submitType==2){
			submitType = "充值卡充值";
		}else if(submitType==3){
			submitType = "支付宝充值";
		}else if(submitType==4){
			submitType = "批量充值";
		}else if(submitType==5){
			submitType = "接口充值";
		}		
		$("#chargeMethod").text(submitType);
		
		if(data.report_time){
			$("#reportTime").text(getLocalTime(data.report_time.toString().substr(0,10)));		
		}else{
			$("#reportTime").text("无");
		}
			
		var chargeStatus = data.charge_status;
		var submitStatus = data.submit_status;
		
		$("#chargeResult").removeClass();
		$("#chargePrice").removeClass();
		if(submitStatus == 1){
			if(chargeStatus == 0){
				chargeStatus = "充值中";
				$("#chargeResult").addClass("label label-info arrowed-right arrowed-in");	
				$("#chargePrice").addClass("label label-info arrowed-right arrowed-in");
				$("#chargePrice").text("￥-"+$("#chargePrice").text() + "元");		
			}else if(chargeStatus == 1){
				chargeStatus = "充值成功";	
				$("#chargeResult").addClass("label label-success arrowed-in arrowed-in-right");	
				$("#chargePrice").addClass("label label-success arrowed-in arrowed-in-right");	
				$("#chargePrice").text("￥-"+$("#chargePrice").text() + "元");
			}else{
				chargeStatus = "充值失败 "	+ data.error;
				$("#chargeResult").addClass("label label-danger arrowed");
				$("#chargePrice").addClass("label label-danger arrowed");
				$("#chargePrice").text("￥+"+$("#chargePrice").text() + "元");
			}
		}else{
				if(data.cache_flag == 1){
					chargeStatus = "充值中";
					$("#chargeResult").addClass("label label-info arrowed-right arrowed-in");	
					$("#chargePrice").addClass("label label-info arrowed-right arrowed-in");
					$("#chargePrice").text("￥-"+$("#chargePrice").text() + "元");					
				}else{
					chargeStatus = "提交失败(充值失败) "	+ data.error;
					$("#chargeResult").addClass("label arrowed");
					$("#chargePrice").addClass("label arrowed");	
				}
		}		
		$("#chargeResult").text(chargeStatus);
		
		
		
		var orderId = data.order_id;
		if(orderId && orderId != ""){
			$("#agentOrderId").text(orderId);		
		}else{
			$("#agentOrderId").text("无");			
		}
		
		var backUrl = data.backUrl;
		if(backUrl && backUrl != ""){
			$("#callBackAdr").text(backUrl);	
		}else{
			$("#callBackAdr").text("无");	
		}
		
		var response = data.response;
		if(response && response != ""){
			$("#callBackStatus").text(response);		
		}else{
			$("#callBackStatus").text("无");
		}
	}	
		
	KISSY.use("gprs/gprs-post,gprs/gprs-terrority",function(S){
		GPRS.Post.loadAgentLevel();
		GPRS.Post.deleteAll();
		GPRS.Post.exportMobile();
		//GPRS.Post.exportChargeOrderExcel();
		GPRS.Post.simplePost();
		GPRS.Terrority.initTerrority();
		GPRS.Terrority.initAmount();		
	});
</script>