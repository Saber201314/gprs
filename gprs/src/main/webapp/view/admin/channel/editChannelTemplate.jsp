<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
</head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<style>
body{
	padding: 0 15px;
}
form {
	padding-bottom: 5px;
}
</style>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		<legend>编辑模板</legend>
		<form class="layui-form" action="">
			<c:if test="${template != null}">
				<input id="packageId" type="hidden" name="id" value="${ template.id }">
			</c:if>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-inline">
						<input type="text" name="name" value="${ template.name }" lay-verify="required" disabled="disabled" autocomplete="off" class="layui-input">
					</div>	
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">用户名</label>
					<div class="layui-input-inline">
						<input type="text" name="amount" value="${ template.account }" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">密码</label>
					<div class="layui-input-inline">
						<input type="text" name="password" value="${ template.password }" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">密钥</label>
					<div class="layui-input-inline">
						<input type="text" name="sign" value="${ template.sign }" lay-verify="required" placeholder="请输入密钥" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div>
				<div  class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit lay-filter="template-submit">保存</button>
					</div>
				</div>
			</div>
		</form>
	</fieldset>
</body>
<script type="text/javascript">
 	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).extend({ //设定模块别名
		base: 'base', //如果test.js是在根目录，也可以不用设定别名
		channelTemplate: 'admin/channel/channelTemplate' //设定别名
	}).use('channelTemplate'); //加载入口 
</script>
</html>