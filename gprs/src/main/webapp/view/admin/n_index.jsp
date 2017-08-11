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
dd a i{
	margin-left: 25px;
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
					<button id="changePassword" class="layui-btn layui-btn-mini">修改密码</button>
				</div>
			</div>
			
		</div>
		<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<ul class="layui-nav layui-nav-tree" lay-filter="demo">
					<li class="layui-nav-item"><a href="">
						<i class="layui-icon" style="font-size: 20px; margin-right: 10px; ">&#xe600;</i>系统首页</a>
					</li>
					
					<c:if test="${fn:indexOf(user.limits, '10') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px; ">&#xe617;</i>流量包管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '101') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('101','流量包','view/admin/package/packageList.jsp')"><i class="layui-icon">&#xe623;</i>流量包</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '20') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px; ">&#xe609;</i>通道管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '201') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('201','通道管理','view/admin/channel/channelList.jsp')"><i class="layui-icon">&#xe623;</i>通道管理</a>
							</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '202') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('202','通道模板','view/admin/channel/channelTemplate.jsp')"><i class="layui-icon">&#xe623;</i>通道模板列表</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '30') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe63b;</i>业务管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '301') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('301','单号充值')"><i class="layui-icon">&#xe623;</i>单号充值</a>
							</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '302') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('302','批量充值')"><i class="layui-icon">&#xe623;</i>批量充值</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '40') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe63c;</i>订单管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '401') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('401','充值记录','view/admin/ordermanage/chargeOrderList.jsp')"><i class="layui-icon">&#xe623;</i>充值记录</a>
							</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '402') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('402','缓存订单','view/admin/ordermanage/chargeOrderCacheList.jsp')"><i class="layui-icon">&#xe623;</i>缓存订单</a>
							</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '403') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('403','消费明细','view/admin/ordermanage/paylog.jsp')"><i class="layui-icon">&#xe623;</i>消费明细</a>
							</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '404') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('404','财务管理','view/admin/ordermanage/agentChargeList.jsp')"><i class="layui-icon">&#xe623;</i>财务管理</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '50') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe60c;</i>费用管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '501') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('501','报价管理','view/admin/costmanage/pricePaperList.jsp')"><i class="layui-icon">&#xe623;</i>报价管理</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '60') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe612;</i>代理商管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '601') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('601','代理商管理', 'view/admin/agentmanage/agentList.jsp')"><i class="layui-icon">&#xe623;</i>代理商管理</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '70') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe649;</i>统计管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '701') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('701','通道统计','view/admin/statistics/channelStatis.jsp')"><i class="layui-icon">&#xe623;</i>通道统计</a>
							</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '702') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('702','区域报表')"><i class="layui-icon">&#xe623;</i>区域报表</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '80') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe614;</i>系统管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '801') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('801','管理员管理','view/admin/sys/adminList.jsp')"><i class="layui-icon">&#xe623;</i>管理员管理</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
					<c:if test="${fn:indexOf(user.limits, '90') != -1 }">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="layui-icon" style="font-size: 20px;margin-right: 10px; ">&#xe60e;</i>日志管理</a>
						<dl class="layui-nav-child">
							<c:if test="${fn:indexOf(user.limits, '901') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('901','通道日志','view/admin/log/channellog.jsp')"><i class="layui-icon">&#xe623;</i>通道日志</a>
							</dd>
							</c:if>
							<c:if test="${fn:indexOf(user.limits, '902') != -1 }">
							<dd>
								<a href="javascript:;" onclick="tab.add('902','回调日志','view/admin/log/callbacklog.jsp')"><i class="layui-icon">&#xe623;</i>回调日志</a>
							</dd>
							</c:if>
						</dl>
					</li>
					</c:if>
				</ul>
			</div>
		</div>
		<!-- 内容主体区域 -->
		<div class="layui-tab layui-tab-card" lay-filter="content_tab" lay-allowClose="true">
			<ul class="layui-tab-title site-demo-title" style="z-index: 9999">
				<li class="layui-this">首页</li>
			</ul>
			<div class="layui-body layui-tab-content site-tab-body" style="padding: 0">
				<div class="layui-tab-item layui-show ">
					<div class="layui-main" >
						<iframe width="100%" height="100%" style="border:0;" src="/view/admin/layout/home.jsp" ></iframe>
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
<div id="changePassword-content" style="display: none; padding-left: 5px;padding-right: 5px;">
	<form action="" class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">原密码</label>
			<div class="layui-input-block">
				<input type="password" name="password" value="" lay-verify="required"
					placeholder="原密码" class="layui-input" autocomplete="off">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">新密码</label>
			<div class="layui-input-block">
				<input type="password" name="newPassword" value="" lay-verify="required"
					placeholder="新密码" class="layui-input" autocomplete="off">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">确认新密码</label>
			<div class="layui-input-block">
				<input type="password" name="repeatPassword" value="" lay-verify="required"
					placeholder="确认新密码" class="layui-input" autocomplete="off">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit lay-filter="changePassword-btn">提交</button>
			</div>
		</div>
	
	</form>
		
</div>
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
	var form = layui.form();
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
	var index;
	$('#changePassword').click(function(){
		index = layer.open({
		    type: 1,
			title:'修改密码',
			skin:'layui-layer-molv',
			shadeClose: true,
			content: $('#changePassword-content')			
	    });	
	})
	
	form.on('submit(changePassword-btn)', function(data) {
		$.ajax({
			url :'/changePassword.action',
			type :'post',
			data : data.field,
			dataType : 'json',
			success : function(data){
				layer.msg(data.msg);
				layer.close(index);
			},
			error : function(){
				layer.msg("连接服务器失败");
				layer.close(index);
			}
			
		})
		return false;
		
	})
	
})
</script>
</html>