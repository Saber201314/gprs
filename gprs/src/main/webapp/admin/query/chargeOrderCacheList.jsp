<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<jsp:include page="cacheManager.jsp"></jsp:include>

<style>
label{width:80px;}
th,td{padding:6px !important;}
li select,li input{width:163px;}
#mobile{width:163px;}
.laydate-icon{width:163px;}
li{padding-top:5px !important;}
.badge-info{color:#ffffff !important;}
.badge-info:hover{color:#F60 !important;}
</style>

<input type="hidden" id="cacheFlag" value="1">

<div id="screen" class="page-content">
	<div class="bar1">
		<span style="float:right;">
		  <span><a class="btn1 buffer-datas" href="#">缓存管理</a></span>		
		  <span><a class="btn1 submit-channel" href="#">批量提交</a></span>
		  <span><a class="btn1 stop_submit-channel" href="#">停止提交</a></span>
		  <span><a class="btn1 fail-cache-order" href="#">批量失败</a></span>
		  <span><a class="btn1 export-excel-datas" href="#">导出Excel</a></span>
		</span>
	</div>
	<div class="errorMsg"><s:actionerror /></div>	
	<div class="search">
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
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	</div>
	<form action="#" method="get" id="delete-form">
	<table id="order_table" class="table table-striped table-bordered table-hover">
		<tr>
			<th width="15"><input type="checkbox" class="select-all select-all_1" /></th>		
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
		    <th>充值结果</th>
		    <th>充值通道</th>		    
		    <th>接入</th>
		    <th>外放</th>	
		    <th>带票</th>
		    <th>盈利</th>	
		    <th>操作</th>
		</tr>
		<tr id="pageTr" style="display:none;">
			<td colspan="18"><div align="left"><jsp:include page="../layout/paginatorNew1.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>

<script src="${pageContext.request.contextPath}/asserts/js/gprs/gprs-order-admin.js" type="text/javascript"></script>
<script>
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

/* 	function goPage(pageNo){
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
	} */

	$('.buffer-datas').on('click', function(){
   		    layer.open({
   		    type: 1,
   			title:'缓存管理',
   			area: ['350px','550px'],
   			shadeClose: true,
   			content: $('#province'),
   			btn:['开始缓存'],
   			yes:function(index, layero){	
   					var jsonList = [];
   	    			$("#provice_panel input").each(function(){
   	    				var json = {};
   	    				if($(this).is(":checked")){
   	    					json.province = $(this).next().text();
   	    					jsonList.push(json);
   	    				}
   	    			});	
   	    			$("#channel_panel input").each(function(){
   	    				var json = {};
   	    				if($(this).is(":checked")){
   	    					json.channel = $(this).next().text();
   	    					jsonList.push(json);
   	    				}
   	    			});	
   	    			$.ajax({type:"POST",
   	    					url:"saveChannelCacheData.action",
   	    					data:{cacheChannelData:JSON.stringify(jsonList)},
   	    					datatype:"json",
   	    					success:function(data){  
   	    					  layer.alert('保存成功！',{
				               title: '提示框',				
								icon:1,		
							  }); 
							  layer.close(index); 	    						
   	    				}
   	    			})      			
   	    			//layer.close(index); 			
   				}
   		    });
	});
		
	KISSY.use("gprs/gprs-post,gprs/gprs-terrority",function(S){
		GPRS.Post.loadAgentLevel();
		//GPRS.Post.exportChargeOrderExcel();
		GPRS.Post.simplePost();
		GPRS.Post.submitCacheData();
		GPRS.Post.stopSubmitCacheData();	
		GPRS.Post.bulkFailCacheOrder();	
		GPRS.Terrority.initTerrority();
		GPRS.Terrority.initAmount();
		GPRS.Post.deleteAll();
	});
</script>