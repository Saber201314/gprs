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
.layui-elem-field{
	padding-top: 15px;
	padding-bottom: 15px;
}
.role{
	float: left;
}

</style>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		<legend>基础信息</legend>
		<div class="layui-form" action="">
			<input type="hidden" name="id" value="${admin.id }"/>
			<input type="hidden" name="type" value="1"/>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">管理员账号</label>
					<div class="layui-input-inline">
						<input name="username" lay-verify="required" autocomplete="off" value="${admin.username }"
							class="layui-input">
					</div>
					<div id="hint" style="display: none;" class="layui-form-mid layui-word-aux"></div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">密码</label>
					<div class="layui-input-inline">
						<input name="password" type="password" lay-verify="required" autocomplete="off" value="${admin.password }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">管理员名称</label>
					<div class="layui-input-inline">
						<input name="name" lay-verify="required" value="${admin.name }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">电话</label>
					<div class="layui-input-inline">
						<input name="phone" value="${admin.phone }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-inline">
						<input name="memo" value="${admin.memo }"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">权限</label>
					<div class="layui-input-block">
						<input type="hidden" value="${admin.limits }"/>
						<div class="layui-tab">
							<ul class="layui-tab-title">
								<li class="layui-this">流量包</li>
								<li>通道管理</li>
								<li>业务分配</li>
								<li>订单管理</li>
								<li>费用管理</li>
								<li>代理商管理</li>
								<li>统计分析</li>
								<li>系统管理</li>
								<li>日志管理</li>
							</ul>
							<div class="layui-tab-content">
								<div class="layui-tab-item layui-show">
									<input type="checkbox" name="limits" value="101" title="流量包" <c:if test='${fn:contains(admin.limits, "101")}'>checked</c:if>  lay-skin="primary">
								</div>
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="201" title="通道管理" <c:if test='${fn:contains(admin.limits, "201")}'>checked</c:if>  lay-skin="primary">
									<input type="checkbox" name="limits" value="202" title="通道模板" <c:if test='${fn:contains(admin.limits, "202")}'>checked</c:if>  lay-skin="primary">
								</div>
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="301" title="单号充值" <c:if test='${fn:contains(admin.limits, "301")}'>checked</c:if>  lay-skin="primary">
									<input type="checkbox" name="limits" value="302" title="批量充值" <c:if test='${fn:indexOf(admin.limits, "302") >= 0}'>checked</c:if>  lay-skin="primary" >
								</div>
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="401" title="流量充值记录" <c:if test='${fn:indexOf(admin.limits, "401") >= 0}'>checked</c:if>  lay-skin="primary" >
									<input type="checkbox" name="limits" value="402" title="缓存订单" <c:if test='${fn:indexOf(admin.limits, "402") >= 0}'>checked</c:if> lay-skin="primary" >
									<input type="checkbox" name="limits" value="403" title="消费明细查询" <c:if test='${fn:indexOf(admin.limits, "403") >= 0}'>checked</c:if> lay-skin="primary" >
									<input type="checkbox" name="limits" value="404" title="财务管理" <c:if test='${fn:indexOf(admin.limits, "404") >= 0}'>checked</c:if> lay-skin="primary" >
								</div>
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="501" title="报价管理" <c:if test='${fn:contains(admin.limits, "501")}'>checked</c:if>  lay-skin="primary">
								</div>
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="601" title="代理商管理" <c:if test='${fn:contains(admin.limits, "501")}'>checked</c:if>  lay-skin="primary">
								</div>
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="701" title="统计报表" <c:if test='${fn:contains(admin.limits, "501")}'>checked</c:if>  lay-skin="primary">
									<input type="checkbox" name="limits" value="702" title="区域统计" <c:if test='${fn:contains(admin.limits, "501")}'>checked</c:if>  lay-skin="primary">
								
								</div>
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="801" title="管理员管理" <c:if test='${fn:contains(admin.limits, "501")}'>checked</c:if>  lay-skin="primary">
								</div>
								
								<div class="layui-tab-item">
									<input type="checkbox" name="limits" value="901" title="通道日志" <c:if test='${fn:contains(admin.limits, "501")}'>checked</c:if>  lay-skin="primary">
									<input type="checkbox" name="limits" value="902" title="回调日志" <c:if test='${fn:contains(admin.limits, "501")}'>checked</c:if>  lay-skin="primary">
								
								</div>
								
							</div>
						</div>
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
		base: 'base', //如果test.js是在根目录，也可以不用设定别名
		addAdmin: 'admin/sys/addAdmin' //设定别名
	}).use('addAdmin'); //加载入口
	
</script>
</html>