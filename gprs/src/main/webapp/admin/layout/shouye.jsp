<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta charset="utf-8" />
<title>流量分发平台</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		
<link
	href="${pageContext.request.contextPath}/resourse/assets/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/font-awesome.min.css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/ace.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/ace-rtl.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/ace-skins.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/css/style.css" />
		
<script src="${pageContext.request.contextPath}/resourse/assets/js/ace-extra.min.js"></script>	

<script
	src="${pageContext.request.contextPath}/resourse/js/jquery-1.9.1.min.js"></script>
</head>

<div class="navbar navbar-default" id="navbar">
	<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>
	<div class="navbar-container" id="navbar-container">
		<div class="navbar-header pull-left">
			<a href="#" class="navbar-brand"> <small> <img src="${pageContext.request.contextPath}/resourse/images/logo.png">
			</small>
			</a>
			<!-- /.brand -->
		</div>
		<!-- /.navbar-header -->
		<div class="navbar-header pull-right" role="navigation">
			<div class="get_time">
				<span>欢迎光临,<label style="color:blue;margin-top:-3px;">&nbsp;系统管理员</label></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="time"></span>
			</div>
			<ul class="nav ace-nav">
				<li><a href="javascript:void(0)" class="change_Password">修改密码</a></li>
				<li><a href="javascript:void(0)" id="Exit_system">退出系统</a></li>

			</ul>
			<!-- /.ace-nav -->
		</div>
		<!-- /.navbar-header -->
	</div>
	<!-- /.container -->
</div>
<div class="main-container" id="main-container">
	<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
	<div class="main-container-inner">
		<a class="menu-toggler" id="menu-toggler" href="#"> <span
			class="menu-text"></span>
		</a>
		<div class="sidebar" id="sidebar">
			<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>
			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<!-- <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					</div> -->
				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span> <span class="btn btn-info"></span>
					<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
				</div>
			</div>
			<!-- #sidebar-shortcuts -->
			<ul class="nav nav-list" id="nav_list">
				<li class="home">
					<a href="javascript:void(0)" name="home.jsp"
						class="iframeurl" title=""><i class="icon-dashboard"></i><span
							class="menu-text"> 系统首页 </span></a>
					</li>
				<c:if test="${ sessionScope.user.username == 'admin'|| sessionScope.user.username  == 'super'}">
					<li><a href="#" class="dropdown-toggle"><i
							class="icon-bold"></i><span class="menu-text"> 流量包管理 </span><b
							class="arrow icon-angle-down"></b></a>
						<ul class="submenu">
							<li class="home"><a href="javascript:void(0)"
								name="../package/packageList.action" title="流量包列表"
								class="iframeurl"><i class="icon-double-angle-right"></i>流量包列表</a></li>
							<li class="home"><a href="javascript:void(0)"
								name="../package/publishPackage.jsp" title="添加流量包"
								class="iframeurl"><i class="icon-double-angle-right"></i>添加流量包</a></li>
						</ul></li>
					<li><a href="#" class="dropdown-toggle"><i
							class="icon-magnet"></i><span class="menu-text"> 通道管理 </span><b
							class="arrow icon-angle-down"></b></a>
						<ul class="submenu">
							<li class="home"><a href="javascript:void(0)"
								name="../channel/channelList.action" title="通道管理"
								class="iframeurl"><i class="icon-double-angle-right"></i>通道管理</a></li>
							<li class="home"><a href="javascript:void(0)"
								name="../channel/publishChannel.action" title="添加新通道"
								class="iframeurl"><i class="icon-double-angle-right"></i>添加新通道</a></li>
							<li class="home"><a href="javascript:void(0)"
								name="../channelTemplate/channelTemplateList.action"
								title="通道模板列表" class="iframeurl"><i
									class="icon-double-angle-right"></i>通道模板列表</a></li>
						</ul></li>
				</c:if>
				<c:if test="${sessionScope.user.username == 'admin' || sessionScope.user.username == 'super'}">
						<li><a href="#" class="dropdown-toggle"><i
								class="icon-bar-chart"></i><span class="menu-text"> 业务管理 </span><b
								class="arrow icon-angle-down"></b></a>
							<ul class="submenu">
								<li class="home"><a href="javascript:void(0)"
									name="../charge/singleCharge.action" title="单号充值" class="iframeurl"><i
										class="icon-double-angle-right"></i>单号充值</a></li>
								<li class="home"><a href="javascript:void(0)"
									name="../charge/batchCharge.action" title="批量充值" class="iframeurl"><i
										class="icon-double-angle-right"></i>批量充值</a></li>
								<li class="home"><a href="javascript:void(0)"
									name="../paycard/paycardList.action" title="充值卡管理" class="iframeurl"><i
										class="icon-double-angle-right"></i>充值卡管理</a></li>
							</ul></li>	
					</c:if>				
				<li><a href="#" class="dropdown-toggle"><i
						class="icon-shopping-cart"></i><span class="menu-text"> 订单管理 </span><b
						class="arrow icon-angle-down"></b></a>
					<ul class="submenu">
						<li class="home"><a href="javascript:void(0)"
							name="../query/chargeOrderList.jsp" title="流量充值记录" class="iframeurl"><i
								class="icon-double-angle-right"></i>流量充值记录</a></li>	
						<li class="home"><a href="javascript:void(0)"
							name="../query/chargeOrderCacheList.jsp" title="流量缓存记录" class="iframeurl"><i
								class="icon-double-angle-right"></i>流量缓存记录</a></li>
								
						<c:if test="${session.user.username} == 'admin'">																	
							<li class="home"><a href="javascript:void(0)"
								name="../query/batchChargeList.action" title="批量任务查询" class="iframeurl"><i
									class="icon-double-angle-right"></i>批量任务查询</a></li>
						</c:if>	
							
						<li class="home"><a href="javascript:void(0)"
							name="../query/payLogList.action" title="消费明细查询" class="iframeurl"><i
								class="icon-double-angle-right"></i>消费明细查询</a></li>	
						<li class="home"><a href="javascript:void(0)"
							name="../query/agentChargeList.action" title="财务管理" class="iframeurl"><i
								class="icon-double-angle-right"></i>财务管理</a></li>																															
					</ul></li>
					<c:if test="${sessionScope.user.username == 'admin' || sessionScope.user.username == 'super'}">
						<li><a href="#" class="dropdown-toggle"><i
								class="icon-dollar"></i><span class="menu-text"> 费用管理 </span><b
								class="arrow icon-angle-down"></b></a>
							<ul class="submenu">
								<li class="home"><a href="javascript:void(0)"
									name="../paper/pricePaperList.action" title="报价管理" class="iframeurl"><i
										class="icon-double-angle-right"></i>报价管理</a></li>							
								<li class="home"><a href="javascript:void(0)"
									name="../suite/suiteList.action" title="流量池管理" class="iframeurl"><i
										class="icon-double-angle-right"></i>流量池管理</a></li>																							
							</ul></li>	
							<li><a href="#" class="dropdown-toggle"><i
								class="icon-globe"></i><span class="menu-text"> 代理商管理 </span><b
								class="arrow icon-angle-down"></b></a>
								<ul class="submenu">
									<li class="home"><a href="javascript:void(0)"
										name="../agent/agentList.action" title="代理商管理" class="iframeurl"><i
											class="icon-double-angle-right"></i>代理商管理</a></li>							
									<li class="home"><a href="javascript:void(0)"
										name="../agent/publishAgent.action" title="添加代理商" class="iframeurl"><i
											class="icon-double-angle-right"></i>添加代理商</a></li>																							
								</ul></li>					
						<c:if test="${sessionScope.user.username == 'admin' || sessionScope.user.username== 'super'}">					
							<li><a href="#" class="dropdown-toggle"><i
									class="icon-list"></i><span class="menu-text"> 统计管理</span><b
									class="arrow icon-angle-down"></b></a>
								<ul class="submenu">
									<li class="home"><a href="javascript:void(0)"
										name="../admin/chargeReportList.action" title="统计报表" class="iframeurl"><i
											class="icon-double-angle-right"></i>统计报表</a></li>	
									<li class="home"><a href="javascript:void(0)"
										name="../report/locationReportList.jsp" title="区域报表" class="iframeurl"><i
											class="icon-double-angle-right"></i>区域报表</a></li>																																								
								</ul></li>	
						</c:if>	
											
						<li><a href="#" class="dropdown-toggle"><i
								class="icon-html5"></i><span class="menu-text"> 网页管理 </span><b
								class="arrow icon-angle-down"></b></a>
							<ul class="submenu">
								<li class="home"><a href="javascript:void(0)"
									name="../wap/portalSetting.action" title="页面设置" class="iframeurl"><i
										class="icon-double-angle-right"></i>页面设置</a></li>							
								<li class="home"><a href="javascript:void(0)"
									name="../wap/advertiseList.action" title="广告管理" class="iframeurl"><i
										class="icon-double-angle-right"></i>广告管理</a></li>		
								<li class="home"><a href="javascript:void(0)"
									name="../wap/alipaySetting.action" title="支付宝充值" class="iframeurl"><i
										class="icon-double-angle-right"></i>支付宝充值</a></li>																																								
							</ul></li>
						<li><a href="#" class="dropdown-toggle"><i
								class="icon-desktop"></i><span class="menu-text"> 系统管理 </span><b
								class="arrow icon-angle-down"></b></a>
							<ul class="submenu">
								<li class="home"><a href="javascript:void(0)"
									name="../paper/myPrice.action" title="我的价格" class="iframeurl"><i
										class="icon-double-angle-right"></i>我的价格</a></li>							
								<li class="home"><a href="javascript:void(0)"
									name="../suiteOrder/mySuiteOrder.action" title="我的流量池" class="iframeurl"><i
										class="icon-double-angle-right"></i>我的流量池</a></li>
							   	<li class="home"><a href="javascript:void(0)"
									name="../setting/paramSetting.action" title="参数设置" class="iframeurl"><i
										class="icon-double-angle-right"></i>参数设置</a></li>	
							   	<li class="home"><a href="javascript:void(0)"
									name="../user/changePassword.jsp" title="修改密码" class="iframeurl"><i
										class="icon-double-angle-right"></i>修改密码</a></li>																																								
							</ul></li>																							
					<li><a href="#" class="dropdown-toggle"><i
							class="icon-list-alt"></i><span class="menu-text"> 日志管理 </span><b
							class="arrow icon-angle-down"></b></a>
						<ul class="submenu">																								
							<li class="home"><a href="javascript:void(0)"
								name="../query/channelLogList.action" title="通道提交日志" class="iframeurl"><i
									class="icon-double-angle-right"></i>通道提交日志</a></li>
							<li class="home"><a href="javascript:void(0)"
								name="../query/callbackList.action" title="回调日志" class="iframeurl"><i
									class="icon-double-angle-right"></i>回调日志</a></li>
						</ul></li>	
					</c:if>				
			</ul>
			<!-- /.nav-list -->
			<div class="sidebar-collapse" id="sidebar-collapse">
				<i class="icon-double-angle-left"
					data-icon1="icon-double-angle-left"
					data-icon2="icon-double-angle-right"></i>
			</div>
			<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
		</div>
		<div class="main-content">
			<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="icon-home home-icon"></i> <a href="javascript:goHome();">首页</a></li>
					<li class="active"><span class="Current_page"></span></li>
					<li class="active" id="parentIframe"><span
						class="parentIframe"></span></li>
				</ul>
			</div>

			<iframe id="iframe"
				style="border:0; width:100%;background-color:#FFF;" frameborder="0"
				src="home.jsp"> </iframe> 


			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->

 		<div class="ace-settings-container" id="ace-settings-container">
			<div class="btn btn-app btn-xs btn-warning ace-settings-btn" style="height:40px;margin-top:30px;"
				id="ace-settings-btn">
				<i class="icon-cog bigger-150"></i>
			</div>

			<div class="ace-settings-box" id="ace-settings-box" style="margin-top:30px;">
 				<div>
					<div class="pull-left">
						<select id="skin-colorpicker" class="hide">
							<option data-skin="default" value="#438EB9">#438EB9</option>
							<option data-skin="skin-1" value="#222A2D">#222A2D</option>
							<option data-skin="skin-2" value="#C6487E">#C6487E</option>
							<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
						</select>
					</div>
					<span>&nbsp;&nbsp;&nbsp;&nbsp; 选择皮肤</span>
				</div> 

 				<div>
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-sidebar" /> <label class="lbl"
						for="ace-settings-sidebar">  &nbsp;&nbsp;&nbsp;&nbsp; 固定滑动条</label>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-rtl" /> <label class="lbl"
						for="ace-settings-rtl"> &nbsp;&nbsp;&nbsp;&nbsp; 切换到左边</label>
				</div>

				<div>
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-add-container" /> <label class="lbl"
						for="ace-settings-add-container">  &nbsp;&nbsp;&nbsp;&nbsp; 切换窄屏 <b></b>
					</label>
				</div>
			</div>  
		</div> 
		<!-- /#ace-settings-container -->
	</div>
	<!-- /.main-container-inner -->

</div>
<!--底部样式-->

<div class="footer_style" id="footerstyle">
	<p class="rf">版权所有：利茸(上海)信息科技有限公司 沪ICP备11011739号 </p>
</div>

<!--修改密码样式-->
<div class="change_Pass_style" id="change_Pass">
	<ul class="xg_style">
		<li><label class="label_name">原&nbsp;&nbsp;密&nbsp;码</label><input
			name="原密码" type="password" name="password" class="" id="password"></li>
		<li><label class="label_name">新&nbsp;&nbsp;密&nbsp;码</label><input
			name="新密码" type="password" name="newPassword" class="" id="Nes_pas"></li>
		<li><label class="label_name">确认密码</label><input name="再次确认密码"
			type="password" name="repeatPassword" class="" id="c_mew_pas"></li>

	</ul>
	<!--       <div class="center"> <button class="btn btn-primary" type="button" id="submit">确认修改</button></div>-->
</div>
<!-- /.main-container -->
<!-- basic scripts -->

<!--[if !IE]>
<script type="text/javascript">
			window.jQuery || document.write("<script src='${pageContext.request.contextPath}/resourse/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>
<![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${pageContext.request.contextPath}/resourse/assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

<script type="text/javascript">
if("ontouchend" in document) document.write("<script src='${pageContext.request.contextPath}/resourse/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>
		
<script src="${pageContext.request.contextPath}/resourse/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/typeahead-bs2.min.js"></script>
<!-- page specific plugin scripts -->

<!-- ace scripts -->

<script src="${pageContext.request.contextPath}/resourse/assets/js/ace-elements.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/ace.min.js"></script>

<script src="${pageContext.request.contextPath}/resourse/assets/layer/layer.js" type="text/javascript"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">	
function goHome(){	
	$("#iframe").attr("src","home.jsp");
	$(".Current_page").text("");
}
//初始化宽度、高度    
$("#main-container").height($(window).height()-76); 
$("#iframe").height($(window).height()-150); 
$(".sidebar").height($(window).height()-99); 
var thisHeight = $("#nav_list").height($(window).height()-185); 
$(".submenu").height($(thisHeight).height()-560);
$("#nav_list").children(".submenu").css("height",thisHeight);

   //当文档窗口发生改变时 触发  
$(window).resize(function(){
	$("#main-container").height($(window).height()-76); 
	$("#iframe").height($(window).height()-155);
	$(".sidebar").height($(window).height()-99);  
	var thisHeight = $("#nav_list").height($(window).height()-185); 
	$(".submenu").height($(thisHeight).height()-400);
	$("#nav_list").children(".submenu").css("height",thisHeight);
});

$(".iframeurl").on("click",function(){
            var cid = $(this).attr("name");
var cname = $(this).attr("title");
            $("#iframe").attr("src",cid).ready();
$("#Bcrumbs").attr("href",cid).ready();
$(".Current_page a").attr('href',cid).ready();	
$(".Current_page").html(cname).ready();	
$("#parentIframe").html(""). css("display","none").ready();						
});  
	  	


//jQuery( document).ready(function(){
//	  $("#submit").click(function(){
//	// var num=0;
//     var str="";
//     $("input[type$='password']").each(function(n){
//          if($(this).val()=="")
//          {
//              // num++;
//			   layer.alert(str+=""+$(this).attr("name")+"不能为空！\r\n",{
//                title: '提示框',				
//				icon:0,				
//          }); 
//             // layer.msg(str+=""+$(this).attr("name")+"不能为空！\r\n");
//             layer.close(index);
//          }		  
//     });    
//})		
//	});
/*********************点击事件*********************/
/* $( document).ready(function(){
  $('#nav_list').find('li.home').click(function(){
	$('#nav_list').find('li.home').removeClass('active');
	$(this).addClass('active');
  });	
													
}) */
 $('#nav_list').find('li.home').click(function(){
	$('#nav_list').find('li.home').removeClass('active');
	$(this).addClass('active');
 });

//时间设置
  function currentTime(){ 
    var d=new Date(),str=''; 
    str+=d.getFullYear()+'年'; 
    str+=d.getMonth() + 1+'月'; 
    str+=d.getDate()+'日'; 
    str+=d.getHours()+'时'; 
    str+=d.getMinutes()+'分'; 
    str+= d.getSeconds()+'秒'; 
    return str; 
} 
setInterval(function(){$('#time').html(currentTime)},1000); 


//修改密码
$('.change_Password').on('click', function(){
    layer.open({
    type: 1,
	title:'修改密码',
	area: ['300px','300px'],
	shadeClose: true,
	content: $('#change_Pass'),
	btn:['确认修改'],
	yes:function(index, layero){		
		   if ($("#password").val()==""){
			  layer.alert('原密码不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          } 
		  if ($("#Nes_pas").val()==""){
			  layer.alert('新密码不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          } 
		   
		  if ($("#c_mew_pas").val()==""){
			  layer.alert('确认新密码不能为空!',{
              title: '提示框',				
				icon:0,
			    
			 });
			return false;
          }
		    if(!$("#c_mew_pas").val || $("#c_mew_pas").val() != $("#Nes_pas").val() )
        {
            layer.alert('密码不一致!',{
              title: '提示框',				
				icon:0,
			    
			 });
			 return false;
        }   
		 else{			  
		 	   $.ajax({url:"changePassword.action",
		 	  		data:{"newPassword":$("#Nes_pas").val(),"repeatPassword":$("#c_mew_pas").val(),"password":$("#password").val()},
		 	  		dataType:"json",
		 	  		success:function(data){
		 	  		if (data.success) {
					  layer.alert('修改成功！',{
		               title: '提示框',				
						icon:2,		
					  }); 
					  layer.close(index); 			 	  		
		 	  		}else{
		 	  		layer.alert(data.error,{
		              title: '提示框',				
						icon:0,
					    
					 });
		 	  		}
	 	  	 
				}});   
		  }	 
	}
    });
});
  $('#Exit_system').on('click', function(){
      layer.confirm('是否确定退出系统？', {
     btn: ['是','否'] //按钮
    }, 
	function(){
	  location.href="/exit.action";
        
    });
}); 
</script>