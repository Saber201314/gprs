<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<jsp:include page="singleChannel.jsp"></jsp:include>
<style>
.radio,.checkbox {padding-left: 0px;}
label{width:90px;}
.badge-info,.badge-danger{color:#ffffff !important;}
.badge-info:hover,.badge-danger:hover{color:#F60 !important;}
</style>

<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a href="#" class="delete-list btn1">删除</a></span>
	  <span><a href="publishChannel.action" class="btn1">添加通道</a></span>
	</span>
	</div>
	<div class="errorMsg"><s:actionerror /></div>
	<div class="search">
	    <form id="ydForm" action="channelList.action" method="post">
	    <div class="search_style">
	      <ul class="search_content clearfix">
		      <li><label class="lf">名称：</label><input type="text" name="queryChannelDO.name" value="<s:property value='queryChannelDO.name' />" /></li>
		      <li><label class="lf">展示名称：</label><input type="text" name="queryChannelDO.alias"  value="<s:property value='queryChannelDO.alias' />" /></li>
		      <li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
	      </ul>
	      </div>
	    </form>
	</div>
	
	<form action="deleteChannelList.action" method="get" id="delete-form">
	<table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
		<tr>
		    <th width="20"><input type="checkbox" class="select-all"/></th>
			<th>名称</th>
			<th>展示名称</th>
			<th>通道模板</th>
			<th>月门限值</th>
			<th>优先级</th>
		    <th>添加时间</th>
		    <th>备注</th>
			<th width="15%" colspan="3">操作</th>
		</tr>
		<s:iterator value="channelList">
			<tr>
			    <td><input type="checkbox" name="queryChannelDO.idList" class="check-item" value="<s:property value='id' />" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="alias" /></td>
				<td>${templateName}</td>
				<td>${monthLimit}M</td>
				<td>${level}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="optionTime" /></td>
				<td>${memo}</td>
				<td>
					<s:if test="status==0">
						<a class="simple-post-triggle badge badge-info simple-detail-triggle" href="changeChannelStatus.action?id=${id }&status=-1">禁用</a>
					</s:if>
					<s:else>
						<a class="simple-post-triggle badge badge-danger simple-detail-triggle" href="changeChannelStatus.action?id=${id }&status=0">启用</a>
					</s:else>
				</td>
				<td> 
					<a class="simple-detail-triggle" href="../channel/publishChannel.action?id=<s:property value='id' />">编辑</a>
				</td>
				<td> 
					<a class="simple-detail-triggle" href="javascript:void(0)" onclick="showChannelInfo(<s:property value='id' />)">详情</a>
				</td>				
			</tr>
		</s:iterator>
		<tr>
			<td colspan="11"><div align="left"><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
	   </tr>
	</table>
	</form>
</div>
<form id="pageForm" action="channelList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryChannelDO.name" value="<s:property value='queryChannelDO.name' />" />
    <input type="hidden" name="queryChannelDO.alias"  value="<s:property value='queryChannelDO.alias' />" />
</form>
<script>
	function showChannelInfo(id){    
 	 $.ajax({url:"showSingleChannelInfo.action",
	 		 data:{"id":id},
	 		 dataType:"json",
	 		 success:function(data){
		    layer.open({
			    type: 1,
				title:'通道详细信息',
				area: ['600px','600px'],
				skin:'layui-layer-lan',
				shadeClose: true,
				content: $('#single-page-content')			
		    });	 		  		 
	 		 initChannelDetail(data);
	 }});	    
	}	
	
	function initChannelDetail(data){
		$("#channel_table tbody tr:not(:first)").empty();
		if(data && data.length>0){
	     	var html = [];
	     	for(var i =0;i<data.length;i++){
	     		html.push("<tr>");		     		
	     		html.push("<td>"+data[i].name+"</td>");	
	     		html.push("<td>"+data[i].price+"</td>");
	     		html.push("<td>"+data[i].discount+"</td>");
	     		html.push("<td>"+data[i].actualPrice+"</td>");
	     		html.push("<td>"+data[i].level+"</td>");	     		
	     		html.push("<td>"+data[i].channelName+"</td>");		     		
	     		html.push("</tr>");
	     	}	
	     	$("#channel_table tbody tr:eq(0)").after(html.join(""));	
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
		GPRS.Post.simplePost();
		GPRS.Post.deleteAll();
	});
</script>