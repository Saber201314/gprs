<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
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
					<label class="layui-form-label">手机号码</label>
					<div class="layui-input-inline">
						<input type="tel" name="mobile" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">开始时间</label>
					<div class="layui-input-inline">
						<input id="start" name="from" class="layui-input" onclick="">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">开始时间</label>
					<div class="layui-input-inline">
						<input id="end" name="to" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">充值通道</label>
					<div class="layui-input-inline">
						<select id="submitChannel" name="submitChannel" lay-filter="submitChannel" lay-search>
							<option value="-1">请选择</option>
						</select>
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
			<thead>
				<tr>
					<th>手机号</th>
					<th>订单号</th>
					<th>充值通道</th>
					<th>充值结果</th>
					<th>充值时间</th>
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
	}).use('channellog'); //加载入口
	
</script>
</html>