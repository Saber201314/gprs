<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<legend>基础信息</legend>
		<div class="layui-form" action="">
			<input type="hidden" name="id" value="${agent.id }"/>
			<input type="hidden" name="type" value="${agent.type}"/>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">代理账号</label>
					<div class="layui-input-inline">
						<input name="username" lay-verify="required" autocomplete="off" value="${agent.username }"
							class="layui-input">
					</div>
					<div id="hint" style="display: none;" class="layui-form-mid layui-word-aux"></div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">密码</label>
					<div class="layui-input-inline">
						<input name="password" type="password" lay-verify="required" autocomplete="off" value="${agent.password }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">代理名称</label>
					<div class="layui-input-inline">
						<input name="name" lay-verify="required" value="${agent.name }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">电话</label>
					<div class="layui-input-inline">
						<input name="phone" value="${agent.phone }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-inline">
						<input name="memo" value="${agent.memo }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">报价单</label>
					<div class="layui-input-inline">
						<select name="paperId">
							<option value="0">请选择</option>
							<c:forEach items="${paperList}" var="item">
									<option <c:if test="${ item.id == agent.paperId}"> selected  </c:if> value="${item.id}">${item.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">有效期</label>
					<div class="layui-input-inline">
						<input id="validateTime" name="validateTime" lay-verify="required" value="<fmt:formatDate value="${agent.validateTime}" pattern="yyyy-MM-dd" />" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">是否启用密钥</label>
					<div class="layui-input-inline">
						<input type="hidden" id="compatible" value="${agent.compatible}"/>
						<select name="compatible" lay-filter="isSecretKey">
							<option <c:if test="${agent.compatible == 0 }">selected</c:if> value="0">不启用</option>
							<option <c:if test="${agent.compatible == 1 }">selected</c:if> value="1">启用</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">接口密码</label>
					<div class="layui-input-inline">
						<input name="apiPassword" disabled="disabled" lay-verify="required" 
							style="background-color: #f1f1f1"  value="${agent.apiPassword}"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">密钥</label>
					<div class="layui-input-inline" style="width: 280">
						<input name="apiKey" disabled="disabled" lay-verify="required"   style="background-color: #f1f1f1"  value="${agent.apiKey}"
							class="layui-input">
						
					</div>
					<div class="layui-inline">
						<button id="genApiKey" disabled="disabled" class="layui-btn layui-btn-disabled">生成密钥</button>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">权限</label>
					<div class="layui-input-block">
						<input type="hidden" value="${agent.limits }"/>
						
						<input type="checkbox" name="limits" value="301" title="单号充值" <c:if test='${fn:contains(agent.limits, "301")}'>checked</c:if>  lay-skin="primary">
						
						
						<input type="checkbox" name="limits" value="302" title="批量充值" <c:if test='${fn:indexOf(agent.limits, "302") >= 0}'>checked</c:if>  lay-skin="primary" >
						<input type="checkbox" name="limits" value="401" title="流量充值记录" <c:if test='${fn:indexOf(agent.limits, "401") >= 0}'>checked</c:if>  lay-skin="primary" >
						<input type="checkbox" name="limits" value="403" title="消费明细查询" <c:if test='${fn:indexOf(agent.limits, "403") >= 0}'>checked</c:if> lay-skin="primary" >
						<input type="checkbox" name="limits" value="404" title="财务管理" <c:if test='${fn:indexOf(agent.limits, "404") >= 0}'>checked</c:if> lay-skin="primary" >
					</div>
				</div>
			</div>
			<div  class="layui-inline">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="btn-submit">提交</button>
				</div>
			</div>
			
			
			
			
		</div>
	</fieldset>
</body>
<script type="text/javascript">
	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).extend({ //设定模块别名
		addAgent: 'agentmanage/addAgent' //设定别名
	}).use('addAgent'); //加载入口
	
</script>
</html>