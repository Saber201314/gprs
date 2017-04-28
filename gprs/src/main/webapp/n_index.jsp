<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<jsp:include page="/cssjs.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/res/css/global.css" rel="stylesheet"/>
<title>利茸-流量分发平台</title>
</head>
<style>
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
#currenttime{
	
}
.out{
	float: right;
	margin-top: 4px;
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
				<span>欢迎光临<span style="color: #1F2D3D;margin: 0px 5px;">系统管理员</span></span>
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
					<li class="layui-nav-item"><a href="javascript:;"  ><i class="layui-icon" style="font-size: 20px; margin-right: 10px; ">&#xe617;</i>流量包管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('101','流量包管理')">流量包列表</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('102','添加流量包')">添加流量包</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px; ">&#xe609;</i>通道管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('201','通道管理')">通道管理</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('202','添加新通道')">添加新通道</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('203','通道模板列表')">通道模板列表</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe63b;</i>业务管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('301','单号充值')">单号充值</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('302','批量充值')">批量充值</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('303','充值卡管理')">充值卡管理</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe63c;</i>订单管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('401','流量充值记录','view/admin/chargeOrderList.jsp')">流量充值记录</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('402','流量缓存记录')">流量缓存记录</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('403','消费明细查询')">消费明细查询</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('404','财务管理')">财务管理</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe60c;</i>费用管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('501','报价管理')">报价管理</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('502','流量池管理')">流量池管理</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe612;</i>代理商管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('601','代理商管理')">代理商管理</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('602','添加代理商')">添加代理商</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe649;</i>统计管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('701','统计报表')">统计报表</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('702','区域报表')">区域报表</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe64c;</i>网页管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('801','页面设置')">页面设置</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('802','广告管理')">广告管理</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('803','支付宝充值')">支付宝充值</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe614;</i>系统管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('901','我的价格')">我的价格</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('902','我的流量池')">我的流量池</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('903','参数设置')">参数设置</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('904','修改密码')">修改密码</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe60e;</i>日志管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" onclick="addtab('1001','通道提交日志')">通道提交日志</a>
							</dd>
							<dd>
								<a href="javascript:;" onclick="addtab('1002','回调日志')">回调日志</a>
							</dd>
						</dl></li>
				</ul>
			</div>
		</div>
		<div class="">
			<!-- 内容主体区域 -->
			<div class="layui-tab layui-tab-card" lay-filter="content_tab" lay-allowClose="true">
				<ul  class="layui-tab-title site-demo-title">
					<li class="layui-this">首页</li>
				</ul>
				<div class="layui-body layui-tab-content site-demo site-demo-body" >
					<div class="layui-tab-item layui-show ">
						<div class="layui-main">
							<iframe width="100%" height="100%" style="border:0;" src="/view/admin/layout/home.jsp" > </iframe>
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
<script>
layui.config({
	base : '/assts/js/gprs/' //你的模块目录
}).extend({ //设定模块别名
  	base: 'base' //如果test.js是在根目录，也可以不用设定别名
});
var addtab;
layui.use([ 'layer', 'form', 'laydate', 'element','laypage' ], function() {
	var element = layui.element();
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var layer = layui.layer;
	
	/*
	 * 左侧导航栏点击添加tab
	 */
	addtab = function addtab(id,title,url){
		var t=$("#title_"+id);
		if( t[0]) {
			element.tabChange('content_tab',id);
			return ;
		}else{
			element.tabAdd('content_tab', {
				title: '<span id="title_'+id+'" >'+title+' </span>',
			    content: '<div class="layui-main"><iframe id="iframe_'+id+'" width="100%" height="100%" style="border:0;" src="${pageContext.request.contextPath}/'+url+'" ></iframe></div>', //支持传入html
				id: id
			});
			element.tabChange('content_tab',id);
		}
	}
	
	/*
	 * 监听tab切换  刷新内容  
	 */
	element.on('tab(content_tab)', function(data){
		var url= $('iframe:eq('+data.index+')').attr('src');
		$('iframe:eq('+data.index+')').attr('src', url);
	});
	
	
	/*
	 * 右上角时间
	 */
	setInterval(function(){
		$('#currenttime').html(laydate.now(0, 'YYYY年MM月DD日 hh时mm分ss秒'))
	},1000);
	
	/*
	 * 退出系统
	 */
	$('#exit').click(function(){
		
		layer.confirm('是否确定退出系统？', {
			  btn: ['是','否'] //按钮
			}, function(){
				$.ajax({
					url : 'exit.action',
					type : 'get',
					cache : false,
					success : function(data){
						data = JSON.parse(data);
						if(data){
							window.location = data.url;
						}
					},
					error : function(){
						
					}
				})
			}, function(){
				
			});
		
	})
	
	 
	
	
	
	
	
})



	
</script>


</html>