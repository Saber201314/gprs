<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="utf-8">
<jsp:include page="/cssjs.jsp"></jsp:include>
<link href="/res/css/global.css" rel="stylesheet"/>
<title>利茸-流量分发平台</title>
</head>
<style>
body{
	height: 100%;
}
.layui-tab[lay-filter="content_tab"] .layui-tab-title li:first-child .layui-tab-close {
	display: none
}
dd{padding-left: 15px}
.layui-header{
	padding-right: 10px;
}
.site-name{
	float: left;
	height : 60px;
	text-align : center;
}
.baseinfo{
	text-align: right;
	margin-top: 5px;
	padding : 3px 10px;
	border-radius:3px;
	background-color: #009688;
	color: white;
}
.layui-main{
	width: auto;
	margin: 0;
}
#currenttime{
	
}
.out{
	float: right;
	margin-top: 4px;
}
.site-tab-body{
	margin-top: 40px;
}

</style>
 
<body>

	<div class="layui-layout layui-layout-admin">
	
		<!-- 头部区域（可配合layui已有的水平导航） -->
		<div class="layui-header">
			<div class="site-name">
				<small>
					<img alt="" height="60" src="${pageContext.request.contextPath}/res/images/logo.png">
				</small>
				
			
			</div>
		
		
			<div style="float: right;">
				<div class="baseinfo">
				<span>欢迎光临<span style="color: #1F2D3D;margin: 0px 5px;">${user.name }</span></span>
				<span id="currenttime"></span>
				
			</div>
			<div class="out">
				<button id="exit" class="layui-btn layui-btn-mini">退出系统</button>
				<button class="layui-btn layui-btn-mini">修改密码</button>
			</div>
			
			</div>
			
		</div>
		<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				
				<ul class="layui-nav layui-nav-tree" lay-filter="demo">
					<li class="layui-nav-item"><a href=""><i class="layui-icon" style="font-size: 20px; margin-right: 10px; ">&#xe600;</i>系统首页</a></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe63b;</i>业务管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '301') != -1 }">
								<dd>
									<a href="javascript:;" onclick="tab.add('301','单号充值')">单号充值</a>
								</dd>
							</c:if>
							
							<c:if test="${fn:indexOf(user.limits, '302') != -1 }">
								<dd>
									<a href="javascript:;" onclick="tab.add('302','批量充值')">批量充值</a>
								</dd>
							</c:if>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe63c;</i>订单管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '401') != -1 }">
								<dd>
									<a href="javascript:;" onclick="tab.add('401','充值记录','view/agent/ordermanage/chargeOrderList.jsp')">充值记录</a>
								</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '403') != -1 }">
								<dd>
									<a href="javascript:;" onclick="tab.add('403','消费明细','view/agent/ordermanage/paylog.jsp')">消费明细</a>
								</dd>
							</c:if>
						</dl></li>
				</ul>
			</div>
		</div>
		<div class="">
			<!-- 内容主体区域 -->
			<div class="layui-tab layui-tab-card" lay-filter="content_tab" lay-allowClose="true">
				<ul class="layui-tab-title site-demo-title" style="z-index: 9999">
					<li class="layui-this">首页</li>
				</ul>
				<div class="layui-body layui-tab-content site-tab-body" style="padding: 0">
					<div class="layui-tab-item layui-show ">
						<div class="layui-main" >
							<iframe width="100%" height="100%" style="border:0;" src="/agent/home.action"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-footer">
			<!-- 底部固定区域 -->
			<div style="text-align: center; line-height: 44px;">版权所有：利茸(上海)信息科技有限公司 沪ICP备11011739号</div>
		</div>
	</div>
</body>
<script src='/assts/js/tab.js'></script>
<script>
layui.config({
	base : '/assts/js/gprs/' //你的模块目录
}).extend({ //设定模块别名
  	base: 'base' //如果test.js是在根目录，也可以不用设定别名
});
var addtab;
var tab = null;
layui.use([ 'layer', 'form', 'laydate', 'element','laypage' ], function() {
	var element = layui.element();
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var layer = layui.layer;
	
	
	
	$(function(){
		tab = new Tab("content_tab");
	})
	
	
		
	
	
	
	/*
	 * 左侧导航栏点击添加tab
	 */
	addtab = function addtab(id, title, url) {
		var t = $("#title_" + id);
		if (t[0]) {
			element.tabChange('content_tab', id);
			return;
		} else {
			element.tabAdd('content_tab', {
				title: '<span id="title_' + id + '" >' + title + ' </span>',
				content: '<div class="layui-main"><iframe id="iframe_' + id + '" width="100%" height="100%" style="border:0;" src="/' + url + '" ></iframe></div>', //支持传入html
				id: id
			});
			element.tabChange('content_tab', id);
		}
	}

	/*
	 * 监听tab切换  刷新内容  
	 */
	element.on('tab(content_tab)', function(data) {
		/* var url = $('iframe:eq(' + data.index + ')').attr('src');
		$('iframe:eq(' + data.index + ')').attr('src', url); */
	});


	/*
	 * 右上角时间
	 */
	setInterval(function() {
		$('#currenttime').html(laydate.now(0, 'YYYY年MM月DD日 hh时mm分ss秒'))
	}, 1000);

	/*
	 * 退出系统
	 */
	$('#exit').click(function() {

		layer.confirm('是否确定退出系统？', {
			btn: ['是', '否'] //按钮
		}, function() {
			$.ajax({
				url: 'exit.action',
				type: 'get',
				cache: false,
				success: function(data) {
					data = JSON.parse(data);
					if (data) {
						window.location = data.url;
					}
				},
				error: function() {

				}
			})
		}, function() {

		});
	})	
})
</script>
</html>