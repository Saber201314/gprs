<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%-- <s:set name="menu_open" value="4"></s:set> --%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
.msg-wrapper{width:100%;}
.clearfix{margin-top:20px;}
.Manager_style input{width:300px;}
li{padding-top:10px;}
label{width:80px;}
</style>
<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<br/>
	<form action="${pageContext.request.contextPath}/changePassword.action" method="post" id="submit-form">
		  <div class="page-content">
		  	<div class="Manager_style">
		  		<span class="title_name">修改密码</span>
		  		<ul class="sendMsg clearfix">
					<li><label class="label_name" >帐号：</label>
						<input type="text" disabled="disabled" value="${user.username}"/>
					</li>
					<li><label class="label_name" >原密码：</label>
						<input type="password" name="password" required required-msg="请输入原密码。" />
					</li>
					<li><label class="label_name" >新密码：</label>
						<input type="password" name="newPassword" id="password" required required-msg="请输入新密码。" />
					</li>
					<li><label class="label_name" >重复一次：</label>
						<input type="password" name="repeatPassword" id="repeatPassword" required required-msg="请重复新密码。" />
					</li>
		      </ul>       
			</div>
			 <div class="sub_btn btn_operating">
				   <input class="submit-btn btn btn-info" type="button" value="提交">
				   <input type="button" class="btn cancel-btn" value="取消">
		     </div>					
		</div>	
	</form>
</div>
 <script>
	 KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.post();
	});
</script>
