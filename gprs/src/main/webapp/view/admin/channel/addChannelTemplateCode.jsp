<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
</head>
<style>
body{
	padding: 0 15px;
}
form{
	padding-bottom: 5px;
}
</style>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		
		<legend>添加流量包编码</legend>
		<form class="layui-form" action="">
			<input type="hidden" name="templateId" value="${templateId }"/>
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
						<select id="rangeType" name="rangeType" >
							<option value="0">全国流量</option>
							<option value="1">省内流量</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">区域</label>
					<div class="layui-input-block">
						<select id="location" name="location" >
							<option>全国</option>
							<option>北京</option>
							<option>天津</option>
							<option>河北</option>
							<option>山西</option>
							<option>内蒙古</option>
							<option>辽宁</option>
							<option>吉林</option>
							<option>黑龙江</option>
							<option>上海</option>
							<option>江苏</option>
							<option>浙江</option>
							<option>安徽</option>
							<option>福建</option>
							<option>江西</option>
							<option>山东</option>
							<option>河南</option>
							<option>湖北</option>
							<option>湖南</option>
							<option>广东</option>
							<option>广西</option>
							<option>海南</option>
							<option>重庆</option>
							<option>四川</option>
							<option>贵州</option>
							<option>云南</option>
							<option>西藏</option>
							<option>陕西</option>
							<option>甘肃</option>
							<option>宁夏</option>
							<option>青海</option>
							<option>新疆</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">流量值</label>
					<div class="layui-input-block">
						<input type="text" name="amount" lay-verify="required" placeholder="请输入流量值" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">编码</label>
					<div class="layui-input-block">
						<input type="text" name="code" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div>
				<div  class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="btn-submit" lay-filter="btn-submit">提交</button>
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
		addChannelTemplateCode: 'admin/channel/addChannelTemplateCode' //设定别名
	}).use('addChannelTemplateCode'); //加载入口
	
</script>
</html>