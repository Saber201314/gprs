<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
</head>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		<form class="layui-form" action="">
			<input type="hidden" id="pageNo" name="pageNo" value="1" class="layui-input">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">代理商</label>
					<div class="layui-input-inline">
						<select id="agent" name="account" lay-filter="agent" lay-search>
							<option value="-1">请选择</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">手机号码</label>
					<div class="layui-input-inline">
						<input type="tel" name="mobile" autocomplete="off"
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
				<col width="">
				<col width="">
				<col width="">
				<col width="100">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>订单号</th>
					<th>手机号</th>
					<th>回调地址</th>
					<th>请求报头</th>
					<th>返回</th>
					<th>回调时间</th>
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
	}).use('callbacklog'); //加载入口
	
</script>
</html>