<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>

<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/prettify/r224/prettify.min.js"></script>
<%-- <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/prettify/r224/prettify.min.css"> 

<link rel="stylesheet" href="${pageContext.request.contextPath}/asserts/js/charts/morris.css">
<script src="${pageContext.request.contextPath}/asserts/js/charts/morris.js"></script> --%>


<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<jsp:include page="../channelResource/resourceBalanceList.jsp"></jsp:include>
<jsp:include page="../channelResource/publishResource.jsp"></jsp:include>
</head>
<style type="text/css">
html {
	overflow-x: hidden;
	overflow-y: hidden !important;
}
.content{margin-top:20px;}
.page-header{font-size:14px;}
.page-header{color:#69aa46;}
td{padding:6px;}
.header{margin-top:-5px;}
.table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th{background-color:#ffffff;}

.badge-info{color:#ffffff !important;}
.badge-info:hover{color:#F60 !important;}
td{padding:3px !important}
</style>
<div class="bar1">
	<span style="float:right">
		<span><a href="#" class="btn-info btn1 query_balance">通道实时余额</a></span>	
		<span><a href="#" class="btn-info btn1 add_records">新增</a></span>		
	</span>
</div>
<div id="screen" class="page-content">	
		<!-- <div>
			<h4 class="header smaller lighter blue"><i class="icon-signal"></i>&nbsp;&nbsp;统计信息</h4>
			<div id="graph" style="height:200px;"></div>
		</div> 	-->			
		<div style="float:left;width:100%">
		    <h4 class="header smaller lighter blue"><i class="icon-comment"></i>&nbsp;&nbsp;资源列表</h4>
			<div id="gonggao1" style="height:200px;margin-bottom:50px;">
				<table id="channel_resource_table_header" class="table table-striped table-bordered table-hover" style="table-layout:fixed;">
					<tr>
						<th width='50'>序号</th>
						<th>运营商</th>   
						<th>地区</th>	
						<th>流量类型</th>					
						<th>产品规格</th>
						<th>接入折扣</th>
						<th>状态</th>	
						<th>政策</th>	
						<th>票据</th>	
						<th>备注</th>
						<th>通道</th>	
						<th width='100'>操作</th>		    
					</tr>				
				</table>
				<div style="height:200px;overflow:auto;">
					<table id="channel_resource_table" class="table table-striped table-bordered table-hover" style="table-layout:fixed;margin-top:-1px;">
					<tbody>
					</tbody>
					</table>
				</div>								
			</div>		
		</div> 
</div>
<div class="page-content">
	<h4 class="header smaller lighter blue"><i class="icon-list"></i>&nbsp;&nbsp;统计列表</h4>
	<div class="content" style="height:380px;overflow:auto;">
		<table id="resume_detail_header" class="table table-striped table-bordered table-hover"  style="table-layout:fixed;">
			<tr>
				<th width='50'>序号</th>
				<th>代理商</th>   
				<th>代理商名称</th>					
				<th>消费金额</th>
				<th>代理商余额</th>		    
			</tr>				
		</table>
		<div style="height:330px;overflow:auto;">
			<table id="resume_detail_table" class="table table-striped table-bordered table-hover" style="table-layout:fixed;margin-top:-1px;">
			<tbody>
			</tbody>
			</table>
		</div>								
	</div>
</div>
<script>
	$(".query_balance").click(function(){
	    layer.open({
	    type: 1,
		title:'通道实时余额',
		area: ['600px','780px'],
		fix: false,
    		shadeClose: true, //点击遮罩关闭层
		content: $('.channel-balance-content')				
		});	
		
		$("#channel_balance_table").empty();
		index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
		$.ajax({
			url : "getChannelBalancesList.action",
			type : "post",
			data : {},
			dataType : 'json',
			cache : false,
			success:function(data) {
				layer.close(index); 
				if(data && data.length>0){
					var html = [];
					var serial = 0;
					for(var i= 0;i<data.length;i++){
						html.push("<tr>");
						serial++;
						html.push("<td width='70'>"+serial+"</td>");
						html.push("<td>"+data[i].channelName+"</td>");
						html.push("<td><span class='label label-success arrowed-in arrowed-in-right'>￥"+data[i].balance+"</span></td>");
						html.push("</tr>");
					}
					$("#channel_balance_table").append(html.join("")); 	
				}
				
			},error:function(){
				layer.close(index); 
		       	layer.alert("服务器连接失败，请重试！");
		    }});				
	});	

	$(".add_records").click(function(){	
		
       	$("#resourceId").val(null);
       	$("#submit_channel").val("");
       	$("#submit_channel").trigger("chosen:updated");
       	$("#merchant").val("");
       	$("#locationType").val("");
       	$("#location").val("");
       	$("#standard").val("");
       	$("#inDiscount").val("");
       	
       	$("#status").val("");
       	$("#payBill").val("");
       	$("#policy").val("");
       	$("#message").val("");	
	
	    layer.open({
	    type: 1,
		title:'资源信息',
		area: ['500px','580px'],
		fix: false,
    		shadeClose: true, //点击遮罩关闭层
		content: $('.channel-resource-content')				
		});		
	});

	KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.getChannelResource();    
		GPRS.Post.loadResumeTotalDetail();
	});	
</script>
