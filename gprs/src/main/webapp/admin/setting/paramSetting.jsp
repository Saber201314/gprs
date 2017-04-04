<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
		
<script src="${pageContext.request.contextPath}/resourse/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/jquery.hotkeys.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/bootstrap-wysiwyg.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/bootbox.min.js"></script>


<style>
ul {margin-top:10px;}
select{width:150px;}
.wysiwyg-toolbar,.wysiwyg-editor{margin-left:170px !important;}
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
				<span class="title_name">公告设置</span>
				<ul>
					<%-- <li class="bz">
						<label class="label_name
						">IP白名单(多个分行填写)：</label> 
						<textarea rows="4" cols="50" name="user.whiteIp" style="width:1100px;height: 200px;"><s:property value="user.whiteIp" /></textarea>
					</li> --%>
					<br/>
					<li class="bz">
						<label class="label_name" style="padding-left:120px;">公告：</label> 
						<%-- <textarea rows="4" cols="50" name="user.gonggao" style="width:500px;height: 200px;"><s:property value="user.gonggao" /></textarea> --%>
						<div class="wysiwyg-editor" id="editor1"></div>
					    <input type="hidden" id="gonggao" name="user.gonggao">
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
 	$('#editor1').ace_wysiwyg({
		toolbar:
		[
			'font',
			null,
			'fontSize',
			null,
			{name:'bold', className:'btn-info'},
			{name:'italic', className:'btn-info'},
			{name:'strikethrough', className:'btn-info'},
			{name:'underline', className:'btn-info'},
			null,
			{name:'insertunorderedlist', className:'btn-success'},
			{name:'insertorderedlist', className:'btn-success'},
			{name:'outdent', className:'btn-purple'},
			{name:'indent', className:'btn-purple'},
			null,
			{name:'justifyleft', className:'btn-primary'},
			{name:'justifycenter', className:'btn-primary'},
			{name:'justifyright', className:'btn-primary'},
			{name:'justifyfull', className:'btn-inverse'},
			null,
			{name:'createLink', className:'btn-pink'},
			{name:'unlink', className:'btn-pink'},
			null,
			{name:'insertImage', className:'btn-success'},
			null,
			'foreColor',
			null,
			{name:'undo', className:'btn-grey'},
			{name:'redo', className:'btn-grey'}
		]
	}).prev().addClass('wysiwyg-style'); 

	 KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.post();
	});
</script>
