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
					<label class="layui-form-label">名称</label>
					<div class="layui-input-inline">
						<input name="name" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">展示名称</label>
					<div class="layui-input-inline">
						<input name="alias" autocomplete="off"
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
	<!-- <button class="layui-btn layui-btn-mini " >删除</button>
	<button class="layui-btn layui-btn-mini addChannel">添加通道</button> -->
	<div class="layui-form">
		<table class="layui-table">
			<thead>
				<tr>
					<!-- <th><input type="checkbox" name="" lay-skin="primary"
						lay-filter="allChoose"></th> -->
					<th>编号</th>
					<th>名称</th>
					<th>展示名称</th>
					<th>通道模板</th>
					<th>月门限值</th>
					<th>优先级</th>
				    <th>添加时间</th>
				    <th>备注</th>
					<th width="15%" colspan="3">操作</th>
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
	<div id="single-page-content" style="display: none; padding-left: 5px;padding-right: 5px;">
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col width="80">
				<col width="80">
				<col width="80">
				<col width="150">
			</colgroup>
			<thead>
				<tr>
					<th>流量包名称</th>
           			<th>基础价格</th>
           			<th>折扣</th>
           			<th>我的价格</th>
           			<th>优先级</th>
           			<th>通道名称</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).extend({ //设定模块别名
		base: 'base', //如果test.js是在根目录，也可以不用设定别名
		channel: 'admin/channel/channel' //设定别名
	}).use('channel'); //加载入口
	
</script>
</html>