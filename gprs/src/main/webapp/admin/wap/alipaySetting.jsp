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
ul{padding-top:10px;}
</style>
<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<br/>
	<form action="doAlipaySetting.action" method="post" id="submit-form">
	<div class="upload">
		<dl>
			<div class="Manager_style clearfix">
			<span class="title_name">基本设置</span>
				<ul class="Add_Manager_style">
					<li><label class="label_name" >开关：</label>
						<s:select name="user.onlinePay" list="#{1:'开启',0:'关闭' }" listKey="key" listValue="value" theme="simple"></s:select>
					</li>
					<li>
						<label class="label_name"> 支付报价单：</label>
						<s:select name="user.onlinePayPaper" list="paperList" listKey="id" listValue="name" theme="simple"></s:select>
					</li>
				</ul>
			</div>
			<br/>
			<br/>
			<div class="Manager_style clearfix">
			<span class="title_name">支付宝参数</span>
				<ul class="Add_Manager_style">
					<li><label class="label_name" >商家邮箱：</label>
						<input type="text" name="user.alipaySellerEmail" value="${user.alipaySellerEmail}" />
					</li>
					<li><label class="label_name" >商家ID：</label>
						<input type="text" name="user.alipayPartner" value="${user.alipayPartner}" />
					</li>
					<li><label class="label_name" >商家私钥：</label>
						<input type="text" name="user.alipayPrivateKey" value="${user.alipayPrivateKey}" />
					</li>
					<li><label class="label_name" >支付宝公钥：</label>
						<input type="text" name="user.alipayAliPublicKey" value="${user.alipayAliPublicKey}" />
					</li>
					<li><label class="label_name" >安全检验码：</label>
						<input type="text" name="user.alipayKey" value="${user.alipayKey}" />
					</li>

				</ul>
			</div>
		</dl>
		<div class="sub_btn btn_operating">
			<input class="submit-btn btn btn-info" type="button" value="提交">
		</div>		  
	</div>	
	</form>
</div>
 <script>
	 KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.post();
	});
</script>
