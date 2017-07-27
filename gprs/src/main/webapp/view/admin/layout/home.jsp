<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="/cssjs.jsp"></jsp:include>
<style>
body{
	padding: 0 15px;
}

</style>
<div style="margin-top: 5px">
	<button class="layui-btn layui-btn-mini">通道实时余额</button>
	<button class="layui-btn layui-btn-mini">新增</button>
</div>
<fieldset class="layui-elem-field layui-field-title">
	<legend>资源列表</legend>
	<div class="layui-field-box">
			<table class="layui-table channelResource-table" height="50px"
				style="overflow: auto;">
				<thead>
					<tr>
						<th>编号</th>
						<th>运营商</th>
						<th>地区</th>
						<th>流量类型</th>
						<th>产品规格</th>
						<th>接入折扣</th>
						<th>状态</th>
						<th>政策</th>
						<th>票据</th>
						<th>备注</th>
						<th>通道</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>
	</div>
</fieldset>
<fieldset class="layui-elem-field layui-field-title">
	<legend>统计列表</legend>
	<div class="layui-field-box">
		<table class="layui-table resumeTotalDetail-table">
			<thead>
				<tr>
					<th>序号</th>
					<th>代理商</th>
					<th>代理商名称</th>
					<th>消费金额</th>
					<th>代理商余额</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>
</fieldset>
<script>
layui.config({
	base : '/assts/js/gprs/' //你的模块目录
}).use('home'); //加载入口

</script>
