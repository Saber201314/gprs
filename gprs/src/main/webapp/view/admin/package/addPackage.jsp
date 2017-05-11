<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
</head>
<style>
form {
	padding-bottom: 5px;
}
</style>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		<input type="hidden" name="templateId" value="${templateId }"/>
		<legend>添加流量包</legend>
		<form class="layui-form" action="">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-inline">
						<input type="text" name="name" lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">
					</div>	
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">展示名称</label>
					<div class="layui-input-inline">
						<input type="text" name="alias" lay-verify="required" placeholder="请输入展示名称" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">流量值</label>
					<div class="layui-input-inline">
						<input type="text" name="amount" lay-verify="required" placeholder="请输入流量值" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准售价</label>
					<div class="layui-input-inline">
						<input type="text" name="money" lay-verify="required" placeholder="请输入售价" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">运营商类型</label>
					<div class="layui-input-inline">
						<select name="type" lay-verify="" lay-search>
							<option value="1">移动</option>
							<option value="2">联通</option>
							<option value="3">电信</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">流量类型</label>
					<div class="layui-input-inline">
						<select name="locationType" lay-verify="" lay-search>
							<option value="1">全国流量</option>
							<option value="2">省内流量</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">原始复选框</label>
				<div class="layui-input-block">
					<input type="checkbox" title="全国" value="全国" name="all" lay-skin="primary"
						lay-filter="allChoose">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<div id="province" style="width: 400px;">
					
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-inline">
						<input type="text" name="memo"  placeholder="请输入备注" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div>
				<div  class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="btn-submit">提交</button>
					</div>
				</div>
			</div>
		</form>
	</fieldset>

</body>
<script type="text/javascript">
	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).use('addPackage'); //加载入口
	
</script>
</html>