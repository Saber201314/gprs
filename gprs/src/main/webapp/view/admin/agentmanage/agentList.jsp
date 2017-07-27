<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
</head>
<style>
body{
	padding: 0 15px;
}

</style>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		<form class="layui-form" action="">
			<input type="hidden" id="pageNo" name="pageNo" value="1" class="layui-input">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">代理商账号</label>
					<div class="layui-input-inline">
						<input name="username" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">代理商名称</label>
					<div class="layui-input-inline">
						<input name="name" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div  class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="btn-submit">查询</button>
					</div>
				</div>


			</div>
		</form>
	</fieldset>
	<a href="/admin/addAgent.action"><button class="layui-btn layui-btn-mini" >添加代理商</button></a>
	<div class="layui-form">
		<table class="layui-table">
			<thead>
				<tr>
					<th>编号</th>
					<th>代理商账号</th>
					<th>代理商名称</th>
					<th>电话</th>
					<th>余额</th>
					<th>透支金额</th>
				    <th>有效期</th>
				    <th>报价单</th>
				    <th>有效期</th>
					<th>添加时间</th>
					<th colspan="2">操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div id="pageinfo" style="text-align: center;">
		<span id="page-total"></span>
		<div id="pate"></div>
	</div>
</body>
<script type="text/javascript">
	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).extend({ //设定模块别名
		agentList: 'agentmanage/agentList' //设定别名
	}).use('agentList'); //加载入口
	
</script>
</html>