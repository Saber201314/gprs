<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="cssjs.jsp"></jsp:include>
<canvas width="300" height="300"></canvas>
<div class="login-content">
		<div class="login_panel">
			<img alt=""  src="${pageContext.request.contextPath}/res/images/logo.png">
			<form class="layui-form layui-form-pane"  action="">
			<div class="layui-form-item">
				<label class="layui-form-label">账号</label>
				<div class="layui-input-block">
					<input type="text" name="username" required lay-verify="required"
						placeholder="账号" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="password" name="password" required
						lay-verify="required" placeholder="密码" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<img id="securitycode" alt="" width="100%" height="38" src="getSecurityCode.action?temp=Math.random()">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">验证码</label>
				<div class="layui-input-block">
					<input  name="securityCode" required
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

<style>
html, body { background: #000; margin: 0; padding:0;}
canvas {  position: absolute; }


.login-content{
	padding-top: 150px;
}

.login_panel{
	width: 350px;
	position: relative;
	margin : 0px auto;
    height:auto;
    padding : 50px 50px;
    border-radius : 10px;
    background-color:  white;


}



</style>
<script>
layui.use(['jquery','layer','form'],function(){
	var $ = layui.jquery;
	var form = layui.form();
	
	$('#securitycode').click(function(){
		document.getElementById('securitycode').src='getSecurityCode.action?temp='+Math.random();
	})
	form.on('submit(login)', function(data) {
			console.log(data.elem); //被执行事件的元素DOM对象，一般为button对象
			console.log(data.form); //被执行提交的form对象，一般在存在form标签时才会返回
			console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
			//top.layer.msg(JSON.stringify(data.field));
			var d = $('form').serialize();
			$.ajax({
				url : 'login.action',
				type : "post",
				data : d,
				cache : false,
				success : function(data) {
					data = JSON.parse(data);
					if(data && data.success){
						window.location = data.url;
					}else{
						layer.msg(data.error_msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown,data) {
		        	console.log(XMLHttpRequest.status);
		        	console.log(XMLHttpRequest.readyState);
		        	console.log(textStatus);
		        	console.log(errorThrown);
		        	console.log(data);
					layer.msg("连接服务器失败");
				}
			})
			return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
		})
	
	
	$(function(){
		  var canvas = document.querySelector('canvas'),
		      ctx = canvas.getContext('2d')
		  canvas.width = window.innerWidth;
		  canvas.height = window.innerHeight;
		  ctx.lineWidth = .3;
		  ctx.strokeStyle = (new Color(150)).style;

		  var mousePosition = {
		    x: 30 * canvas.width / 100,
		    y: 30 * canvas.height / 100
		  };

		  var dots = {
		    nb: 500,
		    distance: 150,
		    d_radius: 50,
		    array: []
		  };

		  function colorValue(min) {
		    return Math.floor(Math.random() * 255 + min);
		  }

		  function createColorStyle(r,g,b) {
		    return 'rgba(' + r + ',' + g + ',' + b + ', 0.8)';
		  }

		  function mixComponents(comp1, weight1, comp2, weight2) {
		    return (comp1 * weight1 + comp2 * weight2) / (weight1 + weight2);
		  }

		  function averageColorStyles(dot1, dot2) {
		    var color1 = dot1.color,
		        color2 = dot2.color;

		    var r = mixComponents(color1.r, dot1.radius, color2.r, dot2.radius),
		        g = mixComponents(color1.g, dot1.radius, color2.g, dot2.radius),
		        b = mixComponents(color1.b, dot1.radius, color2.b, dot2.radius);
		    return createColorStyle(Math.floor(r), Math.floor(g), Math.floor(b));
		  }

		  function Color(min) {
		    min = min || 0;
		    this.r = colorValue(min);
		    this.g = colorValue(min);
		    this.b = colorValue(min);
		    this.style = createColorStyle(this.r, this.g, this.b);
		  }

		  function Dot(){
		    this.x = Math.random() * canvas.width;
		    this.y = Math.random() * canvas.height;

		    this.vx = -.5 + Math.random();
		    this.vy = -.5 + Math.random();

		    this.radius = Math.random() * 2;

		    this.color = new Color();
		  }

		  Dot.prototype = {
		    draw: function(){
		      ctx.beginPath();
		      ctx.fillStyle = this.color.style;
		      ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2, false);
		      ctx.fill();
		    }
		  };

		  function createDots(){
		    for(i = 0; i < dots.nb; i++){
		      dots.array.push(new Dot());
		    }
		  }

		  function moveDots() {
		    for(i = 0; i < dots.nb; i++){

		      var dot = dots.array[i];

		      if(dot.y < 0 || dot.y > canvas.height){
		        dot.vx = dot.vx;
		        dot.vy = - dot.vy;
		      }
		      else if(dot.x < 0 || dot.x > canvas.width){
		        dot.vx = - dot.vx;
		        dot.vy = dot.vy;
		      }
		      dot.x += dot.vx;
		      dot.y += dot.vy;
		    }
		  }

		  function connectDots() {
		    for(i = 0; i < dots.nb; i++){
		      for(j = 0; j < dots.nb; j++){
		        i_dot = dots.array[i];
		        j_dot = dots.array[j];

		        if((i_dot.x - j_dot.x) < dots.distance && (i_dot.y - j_dot.y) < dots.distance && (i_dot.x - j_dot.x) > - dots.distance && (i_dot.y - j_dot.y) > - dots.distance){
		          if((i_dot.x - mousePosition.x) < dots.d_radius && (i_dot.y - mousePosition.y) < dots.d_radius && (i_dot.x - mousePosition.x) > - dots.d_radius && (i_dot.y - mousePosition.y) > - dots.d_radius){
		            ctx.beginPath();
		            ctx.strokeStyle = averageColorStyles(i_dot, j_dot);
		            ctx.moveTo(i_dot.x, i_dot.y);
		            ctx.lineTo(j_dot.x, j_dot.y);
		            ctx.stroke();
		            ctx.closePath();
		          }
		        }
		      }
		    }
		  }

		  function drawDots() {
		    for(i = 0; i < dots.nb; i++){
		      var dot = dots.array[i];
		      dot.draw();
		    }
		  }

		  function animateDots() {
		    ctx.clearRect(0, 0, canvas.width, canvas.height);
		    moveDots();
		    connectDots();
		    drawDots();

		    requestAnimationFrame(animateDots);
		  }

		  $('canvas').on('mousemove', function(e){
		    mousePosition.x = e.pageX;
		    mousePosition.y = e.pageY;
		  });

		  $('canvas').on('mouseleave', function(e){
		    mousePosition.x = canvas.width / 2;
		    mousePosition.y = canvas.height / 2;
		  });

		  createDots();
		  requestAnimationFrame(animateDots);
		  
		  
		  $(window).resize(resizeCanvas);  
		   
		  function resizeCanvas() {  
		    
		         canvas.attr("width", document.body.clientWidth);  
		    
		         canvas.attr("height", document.body.clientHeight);  
		    
		         context.fillRect(0, 0, canvas.width(), canvas.height());  
		    
		  };  
		    
		  resizeCanvas();
		  
		  
		});
})

</script>
</body>
</html>