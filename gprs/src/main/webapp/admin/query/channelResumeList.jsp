<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style>
label{width:80px;}
btn {text-align:center;}
li {padding-top:5px !important;}
li select,li input{width:163px;}
.laydate-icon{width:163px;}
</style>

<div class="channel-resume-content" style="display:none;">
	<div class="bar">
	</div>	
	<div class="errorMsg"><s:actionerror /></div>
		<div class="search">
			<form id="syForm3">
			    <div class="search_style">
				      <ul  class="search_content clearfix">
				       	<li><span style="padding-right:10px;">充值通道：</span><select id="submit_channel" name="queryChannelResumeDO.channel" data-account=""></select></li>	
				      	<%-- <li><span>号码类型：</span><s:select id="flowType" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li> --%>
				      	
				      	<li><span>开始时间：</span><input type="text" name="queryChannelResumeDO.from"  id="start3" class="inline laydate-icon"  /></li>
				      	<li><span>结束时间：</span><input type="text" name="queryChannelResumeDO.to" id="end3" class="inline laydate-icon"  /></li>
					  	<li style="width:90px;"><a id="query_channel_info" class="btn_search btn">查询</a></li>
				      </ul>
			    </div>
		    </form>
		</div>
	<div>
		<table id="channel_resume_table_header" class="table table-striped table-bordered table-hover" style="table-layout:fixed;">
			<tr>
			    <th width='5%'>序号</th>
				<th>充值通道</th>
			    <th>通道消费金额(元)</th>
			    <th>开始时间</th>
			    <th>结束时间</th>
			</tr>	
		</table>
		<div  style="height:480px;overflow:auto;">
			<table id="channel_resume_table" class="table table-striped table-bordered table-hover" style="table-layout:fixed;margin-top:-1px;">
			<tbody>
			</tbody>
			</table>
		</div>
	</div>			
</div>
<script>
	var start = {
	    elem: '#start3',
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
	    elem: '#end3',
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
	
 	$("#start3").val(laydate.now(0,'YYYY-MM-DD 00:00:00'));
	$("#end3").val(laydate.now(0,'YYYY-MM-DD 23:59:59')); 	
			
	$(function(){
		if($("#submit_channel").length>0){
			$.ajax({
		        url:"getCurrentChannelList.action",
		        data:{},
		        dataType:'json',
		        cache:false,
		        success:function(data){
		        	if(data && data.length >0){
		    			var html = [];
		    			html.push('<option value="">请选择</option>');
		    			html.push("<option></option>");
		        		for(var i =0;i<data.length;i++){
		        			html.push("<option value="+data[i].id+">"+data[i].name+"</option>");
		        		}
		    			$("#submit_channel").append(html.join(""));
		    			$("#submit_channel").chosen({search_contains:true});
		        	}
		     }});		
		}
	});	
	
	function buildParams(){
		var params = [];	
		params.push("queryChannelResumeDO.account=");
		params.push($("#agent-levelss").val());
		params.push("&");
		params.push("queryChannelResumeDO.flowType=");
		params.push($("#flowType").val());
		params.push("&");		
		params.push("queryChannelResumeDO.channelId=");
		params.push($("#submit_channel").val());
		params.push("&");
		params.push("queryChannelResumeDO.from=");
		params.push($("#start3").val());
		params.push("&");	
		params.push("queryChannelResumeDO.to=");
		params.push($("#end3").val());	
		return params.join("");			
	}
	var index = null;
	$("#query_channel_info").click(function(){
			$("#channel_resume_table tbody").empty();
			index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
			var params = buildParams();	
 			$.ajax({
 				url : "getChannelResumeList.action",
 				type : "post",
 				data : params,
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
 							html.push("<td width='5%'>"+serial+"</td>");
 							html.push("<td>"+data[i].channelname+"</td>");
 							html.push("<td><span class='label label-success arrowed-in arrowed-in-right'>￥"+data[i].consume+"</span></td>");
 							html.push("<td>"+data[i].fromDate+"</td>");
 							html.push("<td>"+data[i].toDate+"</td>");
 							html.push("</tr>");
 						}
	  					$("#channel_resume_table tbody").append(html.join(""));
	  					$("#channel_resume_table_header").width($("#channel_resume_table tbody").width()-20);
	  					$("#channel_resume_table_header tbody tr th").each(function(i,k){
	  						$(this).width($("#channel_resume_table tbody tr:eq(0) td:eq("+i+")").width());		    						
	  					});
 					}
 					
 				},error:function(){
 				layer.close(index); 
  	        	layer.alert("服务器连接失败，请重试！");
  	        }});		
	});	
</script>