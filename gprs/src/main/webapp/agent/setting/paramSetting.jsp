<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
ul {margin-top:10px;}
</style>
<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<br/>
	<form action="doParamSetting.action" method="post" id="submit-form">
	<div class="upload">
		<dl>
			<div class="Manager_style clearfix">
				<span class="title_name">短信通道设置</span>
					<ul class="Add_Manager_style">
						<li><label class="label_name">充值通知：</label>
							<s:select name="user.sms" list="#{1:'开启',0:'关闭' }" listKey="key" listValue="value" theme="simple"></s:select>
						</li>
						<li><label class="label_name">用户名：</label>
							<input type="text" name="user.smsUsername" value="${user.smsUsername }" />
						</li>
						<li><label class="label_name">密码：</label>
							<input type="text" name="user.smsPassword" value="${user.smsPassword }" />
						</li>
						<li><label class="label_name">签名：</label>
							<input type="text" name="user.smsSign" value="${user.smsSign }" />
						</li>
					</ul>
			</div>
			<br/>
			<div class="Manager_style clearfix">
				<span class="title_name">其他</span>
				<ul>
					<li class="bz">
						<label class="label_name
						">IP白名单(多个分行填写)：</label> 
						<textarea rows="4" cols="50" name="user.whiteIp" style="width:500px;height: 200px;"><s:property value="user.whiteIp" /></textarea>
					</li>
					<br/>
					<li class="bz">
						<label class="label_name" style="padding-left:120px;">公告：</label> 
						<textarea rows="4" cols="50" name="user.gonggao" style="width:500px;height: 200px;"><s:property value="user.gonggao" /></textarea>
					</li>
				</ul>
			</div>
		</dl>
		<div class="btn_operating">
				<button id="submit" class="btn btn-info submit-btn" type="button">提交</button>
		</div>	  
	</div>	
	</form>
</div>
 <script>
	 KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.post();
	});
</script>
