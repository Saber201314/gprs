<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>

<jsp:include page="singlePricePaper.jsp"></jsp:include>

<style>
.radio, .checkbox {padding-left: 0px;}
label{width:80px;}
.badge-info:hover,{color:#F60 !important;}
.badge-info{color:#ffffff !important;}
td{padding:6px !important;}
.routable{color:orange !important;}
</style>
<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a href="#" class="delete-list btn1">删除</a></span>
	  <span><a href="publishPricePaper.action" class="btn1">添加报价单</a></span>
	</span>
	</div>
	<div class="errorMsg"><s:actionerror /></div>
	<div class="search">
	    <form id="ydForm" action="pricePaperList.action" method="post">
	    <div class="search_style">
		      <ul class="search_content clearfix">
			      <li><label class="lf">名称：</label><input type="text" name="queryPricePaperDO.name" value="<s:property value='queryPricePaperDO.name' />" /></li>
			      <li><label class="lf">展示名称：</label><input type="text" name="queryPricePaperDO.alias"  value="<s:property value='queryPricePaperDO.alias' />" /></li>
			      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	</div>
	
	<form action="deletePaperList.action" method="get" id="delete-form">
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
		    <th width="20"><input type="checkbox" class="select-all" /></th>
			<th>名称</th>
			<th>展示名称</th>
		    <th>添加时间</th>
		    <th>备注</th>
			<th width="13%" colspan="3">操作</th>
		</tr>
		<s:iterator value="paperList">
			<tr>
			    <td><input type="checkbox" name="queryPricePaperDO.idList" class="check-item" value="<s:property value='id' />" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="alias" /></td>
				<td><s:date format="yyyy-MM-dd  HH:mm:ss" name="optionTime" /></td>
				<td>${memo}</td>									
				<td> 
					<a class="simple-post-triggle badge badge-info simple-detail-triggle" href="../paper/publishPricePaper.action?id=<s:property value='id' />">编辑</a>		
				</td>							
				<td>
					<a class="simple-post-triggle badge badge-info simple-detail-triggle" href="javascript:void(0);" onclick="showSinglePaperInfo(<s:property value='id' />,'<s:property value='name' />')">详情</a>
				</td>
				<td> 
					<s:if test="routable==0">
						<a class="simple-post-triggle badge badge-info simple-detail-triggle" href="javascript:void();" onclick="updateRoutable(this,${id})">开启路由</a>
					</s:if>
					<s:else>
						<a class="simple-post-triggle badge badge-info simple-detail-triggle routable" href="javascript:void();" onclick="updateRoutable(this,${id})">关闭路由</a>
				   </s:else>
				</td>				
			</tr>
		</s:iterator>
		<tr>
			<td colspan="8"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>
<form id="pageForm" action="pricePaperList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryPricePaperDO.name" value="<s:property value='queryPricePaperDO.name' />" />
    <input type="hidden" name="queryPricePaperDO.alias"  value="<s:property value='queryPricePaperDO.alias' />" />
</form>
<script>
	function updateRoutable(obj,id){
		var routable = 0;
		if($(obj).hasClass("routable")){
			routable = 0;
		}else{
			routable = 1;
		}
	 	  $.ajax({url:"updateRoutable.action",
	 		 data:{"id":id,"routable":routable},
	 		 dataType:"json",
	 		 success:function(data){
		 		if(routable == 0){
		 			if($(obj).hasClass("routable")){
		 		 		$(obj).removeClass("routable");
		 		 	}		 	
		 		 	$(obj).text("开启路由");
		 		 }else{
		 		 	if(!$(obj).hasClass("routable")){
		 		 		$(obj).addClass("routable");
		 		 	}
		 		 	$(obj).text("关闭路由");
		 		 } 
		 }});		
	}
	
	function showSinglePaperInfo(id,paperName){    
 	 $.ajax({url:"showSinglePaperInfo.action",
	 		 data:{"id":id},
	 		 dataType:"json",
	 		 success:function(data){
		    layer.open({
			    type: 1,
				title:paperName + ' 详细信息',
				area: ['600px','600px'],
				skin:'layui-layer-lan',
				shadeClose: true,
				content: $('#single-page-content')			
		    });	 		  		 
	 		 initPaperDetail(data);
	 }});	    
	}	
	
	function initPaperDetail(data){
		$("#paper_table tbody tr:not(:first)").empty();
		if(data && data.length>0){
	     	var html = [];
	     	for(var i =0;i<data.length;i++){
	     		html.push("<tr>");	
	     		html.push("<td>"+data[i].name+"</td>");	
	     		html.push("<td>"+data[i].preDiscount+"</td>");
	     		html.push("<td>"+data[i].afterDiscount+"</td>");
	     		var bill = data[i].bill;
	     		if(bill == 1){
	     			bill = "带票";
	     		}else{
	     			bill = "不带票";
	     		}	
	     		html.push("<td>"+bill+"</td>");     		
	     		html.push("<td>"+data[i].level+"</td>");	     		
	     		html.push("<td>"+data[i].channel+"</td>");
	     		html.push("</tr>");
	     	}	
	     	$("#paper_table tbody tr:eq(0)").after(html.join(""));	
		}		
	}
	
	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("page.pageNo");
		page.value=pageNo;
		form.submit();
		return false;
	}
	
	KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.deleteAll();
	});
</script>