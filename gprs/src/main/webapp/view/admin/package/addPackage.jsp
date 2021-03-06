<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
</head>
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
		<legend>添加流量包</legend>
		<form class="layui-form" action="">
			<c:if test="${packageObj != null}">
				<input id="packageId" type="hidden" name="packageId" value="${ packageObj.id }">
			</c:if>
			<input id="packageId" type="hidden" name="status" value="${ packageObj.status }">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-inline">
						<input type="text" name="name" value="${ packageObj.name }" lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">
					</div>	
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">展示名称</label>
					<div class="layui-input-inline">
						<input type="text" name="alias" value="${ packageObj.alias }" lay-verify="required" placeholder="请输入展示名称" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">流量值</label>
					<div class="layui-input-inline">
						<input type="text" name="amount" value="${ packageObj.amount }" lay-verify="required" placeholder="请输入流量值" autocomplete="off" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准售价</label>
					<div class="layui-input-inline">
						<input type="text" name="money" value="${ packageObj.money }" lay-verify="required" placeholder="请输入售价" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">运营商类型</label>
					<div class="layui-input-inline">
						<select name="type" lay-verify="" lay-search>
							<option <c:if test="${ packageObj.type == 1 }">selected=""</c:if> value="1">移动</option>
							<option <c:if test="${ packageObj.type == 2 }">selected=""</c:if> value="2">联通</option>
							<option <c:if test="${ packageObj.type == 3 }">selected=""</c:if> value="3">电信</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">流量类型</label>
					<div class="layui-input-inline">
						<select name="rangeType" lay-verify="" lay-search>
							<option <c:if test="${ packageObj.rangeType == 0 }">selected=""</c:if> value="0">全国流量</option>
							<option <c:if test="${ packageObj.rangeType == 1 }">selected=""</c:if> value="1">省内流量</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item" pane="">
				<label class="layui-form-label">支持地区</label>
				<div class="layui-input-block">
					<input type="checkbox" title="全国" <c:if test="${ packageObj.locations == '全国' }">checked=""</c:if> value="全国" id="allcheck" name="all" lay-skin="primary"
						lay-filter="allChoose">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<div id="province" style="width: 400px;">
						<c:forEach var="item" items="${province }">
							<span style="width : 100px;">
								<c:choose>
									<c:when test="${fn:contains(packageObj.locations,item)}">
										<input lay-filter="province" type="checkbox" checked="" value="${item}"  data-id="${item}" name="provinces"  title="${item}" lay-skin="primary"/>
									</c:when>
									<c:when test="${fn:contains(packageObj.locations,'全国')}">
										<input lay-filter="province" type="checkbox" checked="" value="${item}"  data-id="${item}" name="provinces"  title="${item}" lay-skin="primary"/>
									</c:when>
									<c:otherwise>
										<input lay-filter="province" type="checkbox" value="${item}" data-id="${item}" name="provinces"  title="${item}" lay-skin="primary"/>
									</c:otherwise>
								</c:choose>
							</span>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-inline">
						<input type="text" name="memo" value="${packageObj.memo }"  placeholder="请输入备注" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div>
				<div  class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit lay-filter="package-submit">提交</button>
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
		addPackage: 'admin/package/addPackage' //相对于上述base目录的子目录
	}).use('addPackage'); //加载入口
	
</script>
</html>