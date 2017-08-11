<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="/cssjs.jsp"></jsp:include>
<style>
body{
	padding: 0 15px;
}
.layui-table tbody tr td:nth-child(1){
	background-color: #f2f2f2;
}
.layui-table tbody tr td:nth-child(2){
	text-align : left;
}
</style>
<table class="layui-table">
	<tbody>
		<tr>
			<td>账号名称</td>
			<td>${user.name}</td>
		</tr>
		<tr>
			<td>代理商账号</td>
			<td>${user.username}</td>
		</tr>
		<tr>
			<td>余额</td>
			<td>${user.money}</td>
		</tr>
		<tr>
			<td>密钥</td>
			<td>${user.apiKey}</td>
		</tr>
		<tr>
			<td>IP白名单</td>
			<td>${user.whiteIp}</td>
		</tr>
	</tbody>
</table>

<script>

</script>
