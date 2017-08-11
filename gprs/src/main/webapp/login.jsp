<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
<style>
canvas {  position: absolute; }


.login-content{
	width : 100%;
	height : 100%;
	overflow : hidden;
	background: #16a085;
}

.login_panel{
	width: 350px;
	position: relative;
	margin : 150px auto 0px auto;
    height:auto;
    padding : 50px 50px;
    border-radius : 10px;
    background-color:  white;
}
</style>
<jsp:include page="cssjs.jsp"></jsp:include>
<div class="login-content">
		<div class="login_panel">
			<img alt=""  src="${pageContext.request.contextPath}/res/images/logo.png">
			<form class="layui-form layui-form-pane"  action="">
			<div class="layui-form-item">
				<label class="layui-form-label">账号</label>
				<div class="layui-input-block">
					<input type="text" name="username" value="" required lay-verify="required"
						placeholder="账号" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="password" name="password" value="" required
						lay-verify="required" placeholder="密码" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<img id="securitycode" alt="" width="100%" height="38" src="/getSecurityCode.action?temp=Math.random()">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">验证码</label>
				<div class="layui-input-block">
					<input id="code"  name="securityCode" required
						lay-verify="required" placeholder="验证码" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter=login>登录</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
		
		</div>
		


	</div>

<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.min.js"></script> 
<script>
layui.use(['jquery','form'],function(){
	var $ = layui.jquery;
	var form = layui.form();
	
	$('#securitycode').click(function(){
		document.getElementById('securitycode').src='/getSecurityCode.action?temp='+Math.random();
	})
	form.on('submit(login)', function(data) {
		/* data.field.password = md5(data.field.password); */
			$.ajax({
				url : '/login.action',
				type : "post",
				data : data.field,
				cache : false,
				success : function(data) {
					data = JSON.parse(data);
					if(data && data.success){
						window.location = data.url;
					}else{
						layer.msg(data.error_msg);
						$('#code').val("");
						document.getElementById('securitycode').src='/getSecurityCode.action?temp='+Math.random();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown,data) {
					layer.msg("连接服务器失败");
				}
			})
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		})
	$(function(){
		$('.login-content').particleground({
		    dotColor: '#5cbdaa',
		    lineColor: '#5cbdaa'
		});
	});
})

</script>
</body>
</html>