<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/cssjs.jsp"></jsp:include>
</head>
<body>
	<fieldset class="layui-elem-field">
		<div class="layui-field-box">
			<form class="layui-form" action="">
				<input type="hidden" id="pageNo" name="pageNo" value="1" class="layui-input">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">手机号码</label>
						<div class="layui-input-inline">
							<input type="tel" name="mobile"
								autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">充值通道</label>
						<div class="layui-input-inline">
							<select id="submitChannel" name="submitChannel"
								lay-filter="submitChannel" lay-search>
								<option value="-1">请选择</option>
							</select>
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">开始时间</label>
						<div class="layui-input-inline">
							<input id="start" name="from" class="layui-input" onclick="">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">结束时间</label>
						<div class="layui-input-inline">
							<input id="end" name="to" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit="" lay-filter="btn-submit">查询</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</fieldset>
	<div class="layui-form">
		<table class="layui-table">
			<thead>
				<tr>
					<th><input type="checkbox" name="" lay-skin="primary"
						lay-filter="allChoose"></th>
					<th>代理商</th>
					<th>手机号码</th>
					<th>号码类型</th>
					<th width = "60">流量类型</th>
					<th>流量值</th>
					<th>基础价格</th>
					<th>扣费金额</th>
					<th>充值时间</th>
					<th>回调时间</th>
					<th>充值方式</th>
					<th width="60">充值结果</th>
					<th>异常信息</th>
					<th width="70">充值通道</th>
					<th>接入</th>
					<th>外放</th>
					<th>带票</th>
					<th>盈利</th>
					<th>操作</th>
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