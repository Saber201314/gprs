<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
</head>
<body>
	
	<button class="layui-btn layui-btn-mini getallcheck" >删除</button>
	<a href="publishChannelTemplateCode.action?templateId=${templateId}">
	<button class="layui-btn layui-btn-mini">添加流量包</button>
	</a>
	<div class="layui-form">
		<table class="layui-table">
			<thead>
				<tr>
					<th><input type="checkbox" name="" lay-skin="primary"
						lay-filter="allChoose"></th>
					<th>编码</th>
					<th>运营商类型</th>
					<th>区域</th>
					<th>流量类型</th>
					<th>流量值（M）</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${list}">
					<tr>
					<td><input type="checkbox"  data-id="${item.id }" lay-skin="primary"/></td>
					<td>${item.code }</td>
					<td>
						<c:if test="${item.type == 1 }">移动</c:if>
						<c:if test="${item.type == 2 }">联通</c:if>
						<c:if test="${item.type == 3 }">电信</c:if>
					</td>
					<td>${item.location }</td>
					<td>
						<c:if test="${item.locationType == 1 }">全国流量</c:if>
						<c:if test="${item.locationType == 2}">省内流量</c:if>
					</td>
					<td>${item.amount} </td>
					<td>编辑</td>
				
				
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).use('channelTemplateCode'); //加载入口
	
</script>
</html>