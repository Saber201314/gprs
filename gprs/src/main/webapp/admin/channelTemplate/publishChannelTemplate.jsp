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
.msg-wrapper{width:100%;}
.clearfix{margin-top:20px;}
.Manager_style input{width:300px;}
li{padding-top:10px;}
label{width:80px;}
</style>

<div id="screen">
	<div class="bar">
	</div>
	<form action="doPublishChannelTemplate.action" method="post" id="submit-form">
	<input type="hidden" name="channelTemplate.id" value="<s:property value='channelTemplate.id' />" />
	  <div class="form-elem Manager_style">
	  			<span class="title_name">修改通道模板</span>
			  <ul class="sendMsg clearfix">
					<li>
						<label class="label-name"> 名称：</label>
						<input type="text"  value="<s:property value='channelTemplate.name' />" disabled="disabled" />
					 </li>
					 <li>
						<label class="label-name"> 用户名：</label>
						<input type="text" name="channelTemplate.account" value="<s:property value='channelTemplate.account' />" />
					 </li>
					 <li>
						<label class="label-name"> 密码：</label>
						<input name="channelTemplate.password" style="margin-left:10px;" value="<s:property value='channelTemplate.password' />"  />
					 </li>
					 <li>
						<label class="label-name"> 密钥：</label>
						<input type="text" name="channelTemplate.sign" value="<s:property value='channelTemplate.sign' />" />
					 </li>
			  </ul>
		</div>	
		 <div class="sub_btn btn_operating">
			   <input class="submit-btn btn btn-info" type="button" value="提交">
			   <input type="button" class="btn cancel-btn" value="取消">
	     </div>			
	</form>
</div>
 <script>
	 KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.post({
			successUrl:"../channelTemplate/channelTemplateList.action",
			cancelUrl:"../channelTemplate/channelTemplateList.action"
		});
	});
</script>
