<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
</head>
<style>
form{
	padding-bottom: 5px;
}
</style>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		<input type="hidden" name="templateId" value="${templateId }"/>
		<legend>添加流量包</legend>
		<form class="layui-form" action="">
			<input type="hidden" id="pageNo" name="pageNo" value="1" class="layui-input">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">运营商类型</label>
					<div class="layui-input-block">
						<select id="type" name="type">
							<option value="1">移动</option>
							<option value="2">联通</option>
							<option value="3">电信</option>
						</select>
					</div>	
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">流量类型</label>
					<div class="layui-input-block">
						<select id="locationType" name="locationType" >
							<option value="1">全国流量</option>
							<option value="2">省内流量</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">区域</label>
					<div class="layui-input-block">
						<select id="location" name="location" >
							<option value="-1">请选择</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">流量值</label>
					<div class="layui-input-block">
						<select id="agent" name="account">
							<option value="-1">请选择</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">编码</label>
					<div class="layui-input-block">
						<input type="text" name="username" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
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
	}).use('addChannelTemplateCode'); //加载入口
	
</script>
</html>