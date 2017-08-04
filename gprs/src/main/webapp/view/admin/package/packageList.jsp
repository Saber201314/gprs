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
<body class="layui-form">
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
				<div class="layui-inline">
					<label class="layui-form-label">流量值</label>
					<div class="layui-input-inline">
						<input name="amount" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">运营商类型</label>
					<div class="layui-input-inline">
						<select name="type" lay-verify="" lay-search>
							<option value="0">请选择</option>
							<option value="1">移动</option>
							<option value="2">联通</option>
							<option value="3">电信</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">流量类型</label>
					<div class="layui-input-inline">
						<select name="rangeType" lay-verify="" lay-search>
							<option value="-1">请选择</option>
							<option value="0">全国流量</option>
							<option value="1">省内流量</option>
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
	<button class="layui-btn layui-btn-mini getallcheck" >删除</button>
	<a href="/admin/editPackage.action"><button class="layui-btn layui-btn-mini" >添加流量包</button></a>
	
	<div>
		<table class="layui-table">
			<thead>
				<tr>
					<th><input type="checkbox" name="" lay-skin="primary"
						lay-filter="allChoose"></th>
					<th>编号</th>
					<th>名称</th>
					<th>展示名称</th>
					<th>流量值(M)</th>
					<th>标准售价(元)</th>
					<th>运营商类型</th>
				    <th>流量类型</th>
				    <th>支持地区</th>
				    <th>添加时间</th>
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
	}).use('packageList'); //加载入口
	
</script>
</html>