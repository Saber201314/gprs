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
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">名称</label>
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
	<div class="layui-form">
		<table class="layui-table">
			<colgroup>
				<col width="150"></col>
				<col width="150"></col>
				<col></col>
				<col width="350"></col>
				<col ></col>
				<col></col>
				<col width="150"></col>
			</colgroup>
			<thead>
				<tr>
					<th>编号</th>
					<th>名称</th>
					<th>用户名</th>
					<th>密码</th>
					<th>密钥</th>
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
	}).use('channelTemplate'); //加载入口
	
</script>
</html>