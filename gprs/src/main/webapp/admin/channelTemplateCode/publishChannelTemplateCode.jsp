<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%-- <s:set name="menu_open" value="5"></s:set> --%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
.msg-wrapper{width:100%;}
.clearfix{margin-top:20px;}
.Manager_style input,select{width:300px;}
li{padding-top:10px;}
label{width:90px;}
</style>

<div id="screen" class="page-content">
	<div class="bar">		
	</div>
	<form action="doPublishChannelTemplateCode.action" method="post" id="submit-form">
	<input type="hidden" name="code.id" value="<s:property value='id' />" />
	<input type="hidden" name="code.templateId" value="<s:property value='templateId' />" />
	  <div class="form-elem Manager_style">
			<span class="title_name">添加流量包</span>  		
			  <ul class="sendMsg clearfix">
					 <li>
						<label class="label_name"> 运营商类型：</label>
						<s:select name="code.type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" theme="simple"></s:select>
					 </li>
					 <li>
						<label class="label_name"> 流量类型：</label>
						<s:select name="code.locationType" list="#{1:'全国流量',2:'省内流量' }" listKey="key" listValue="value" theme="simple"></s:select>
					 </li>
			  		<li>
			  			<input type="hidden" id="location" value="${code.location}">
						<label class="label_name"> 区域：</label>
						<select name="code.location" class="terrority-select"></select>
					 </li>					 
			  		<li>
						<label class="label_name"> 流量值：</label>
						<input type="text" style="margin-left:0px;" name="code.amount" value="<s:property value='code.amount' />" id="amount" required number="true" required-msg="流量值不能为空！" number-msg="请填写数字" />M
					 </li>
					 <li>
						<label class="label_name"> 编码：</label>
						<input type="text" style="margin-left:0px;" name="code.code" value="<s:property value='code.code' />" />
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
	 KISSY.use("gprs/gprs-post,gprs/gprs-terrority",function(S){
		GPRS.Terrority.initTerrority();	 
		GPRS.Post.post({
			successUrl:"../channelTemplateCode/channelTemplateCodeList.action?templateId=${templateId}",
			cancelUrl:"../channelTemplateCode/channelTemplateCodeList.action?templateId=${templateId}"
		});	
	});
</script>
