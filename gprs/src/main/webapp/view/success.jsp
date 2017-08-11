<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Insert title here</title>
<jsp:include page="/cssjs.jsp"></jsp:include>
</head>
<body>
	<form class="layui-form" action="">
		<div class="layui-form-item">
			<label class="layui-form-label">复选框</label>
			<div class="layui-input-block">
				<input type="checkbox" lay-skin="primary" name="limits" value="301" title="写作"> 
				<input type="checkbox" lay-skin="primary" name="limits" value="302" title="阅读" checked="">
				<input type="checkbox" lay-skin="primary" name="limits" value="303" title="游戏">
			</div>
		</div>
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
		      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		</div>
	</form>
</body>
<script type="text/javascript">
	//Demo
	layui.use(['form'], function(){
	  var form = layui.form();
	  
	  //监听提交
	  form.on('submit(formDemo)', function(data){
	    layer.msg(JSON.stringify(data.field));
	    return false;
	  });
	});

</script>
</html>