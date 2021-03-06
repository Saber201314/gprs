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
	<fieldset class="layui-elem-field" style="margin-top: 5px;padding: 10 0px">
		<legend>代理商充值</legend>
		<div class="layui-form">
			<input type="hidden" name="userId" value="${agent.id }"/>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">代理账号</label>
					<div class="layui-input-inline">
						<input name="username" disabled="disabled" lay-verify="required"  value="${agent.username }"
							class="layui-input">
							<%-- <div>${agent.username }</div> --%>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">充值金额</label>
					<div class="layui-input-inline">
						<input name="money" value="" lay-verify="required"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">充值方式</label>
					<div class="layui-input-inline">
						<select name=payType>
							<option value="1">公账收款</option>
						    <option value="2">公支付宝</option>
						    <option value="3">私账</option>
						    <option value="4">转移</option>
						    <option value="5">授信</option>
						    <option value="6">未到账退款</option>
						    <option value="7">测试加款</option>
						    <option value="8">其他原因充扣款</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-block">
						<div contenteditable="true" id="memo" name="memo" style="background-color:#f9f9f9;margin-top:50px; border:1px solid #ccc; transition:border linear .2s,box-shadow linear .2s; padding:4px 6px; color:#555;word-wrap: break-word;white-space: pre-wrap;width: 500px;margin-top: 0;margin-right: 5px; height:200px; overflow:auto;"></div>
						<div >在谷歌和火狐浏览器中可直接粘贴剪贴板图片，QQ截图按ctrl+alt+a</div>
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
		chargeAgentBalance: 'admin/agentmanage/chargeAgentBalance' //设定别名
	}).use('chargeAgentBalance'); //加载入口
</script>
<script>
window.addEventListener('load', function (e) {
		document.body.onpaste = function (e) {
			var items = e.clipboardData.items;
			for (var i = 0; i < items.length; ++i) {
				item = items[i];
				if (item && item.kind === 'file' && item.type.match(/^image\//i)) {
					imgReader(item);
					break;
				}
			}
		};
	});

	var imgReader = function (item) {
		var blob = item.getAsFile(),
        reader = new FileReader();

		reader.onload = function (e) {
			var img = new Image();

			img.src = e.target.result;
			var logBox = document.getElementById('memo');
			logBox.appendChild(img);
		};

		reader.readAsDataURL(blob);
	};

</script>
</html>