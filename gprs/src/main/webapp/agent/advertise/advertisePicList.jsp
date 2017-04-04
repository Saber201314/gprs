<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<s:set name="menu_open" value="5"></s:set>
<jsp:include page="../layout/top.jsp"></jsp:include>
<div id="screen" class="page-content">
	<div class="bar">
	<span style="float:left;">图片列表</span>
	<span style="float:right">
		<span class="right" style="border:none;">
			<input type="file" name="file" hidefocus="true" class="file-input" value="上传图片" id="J_UploaderBtn" />
		</span>
		<span><a class="right delete-list" href="#">删除</a></span>
	</span>
	</div>
	<ul id="J_UploaderQueue" class="hidden"></ul>
	<form action="deleteAdvertisePicList.action" method="get" id="delete-form">
	<input id="advertiseId"  type="hidden" value="${advertiseId }"/>
	<table class="tab-list">
		<tr>
			<th width="10%"><input type="checkbox" class="select-all" />全选</th>
			<th>图片</th>
			<th>链接</th>
			<th>排序</th>
			<th width="12%">操作</th>
		</tr>
		<s:iterator value="picList">
			<tr>
				<td><input type="checkbox" class="check-item" name="idList" value="${id }" /></td>
				<td  class="align-left"><img height="50px" src="${pageContext.request.contextPath}/uploads/${pic}" /></td>
				<td><input value="${url }" class="input-url" /></td>
				<td><input value="${level }" class="input-level" /></td>
				<td><a href="#" data-id="${id }" data-type="${type }" data-filename=${pic} class="modify-ad-pic" >修改</a></td>
			</tr>
		</s:iterator>
	</table>
	</form>
</div>
<script>
KISSY.use("gprs/gprs-post,gprs/gprs-advertise",function(S){
	GPRS.Post.deleteAll();
	GPRS.Advertise.init();
});
</script>