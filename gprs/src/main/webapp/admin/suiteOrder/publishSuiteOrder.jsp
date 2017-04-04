<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:include page="../layout/top.jsp"></jsp:include>
<div id="screen" class="page-content">
	<div class="bar">
		<span class="left"><span class="img">&nbsp;</span>添加流量池</span>
	</div>
	<form action="doPublishSuiteOrder.action" method="post" id="submit-form">
	<input type="hidden" name="suiteOrderId" value="<s:property value='suiteOrderId' />" />
	<input type="hidden" name="userId" value="<s:property value='userId' />" />
	  <div class="form-elem">
			  <ul>
			  		<li>
			  			<span class="label-like"> 用户名：</span>
			  			<input value="${user.username }" disabled="disabled" />
			  		</li>
					<li>
						<span class="label-like"> 流量池：</span>
						<s:if test="suiteOrderId==0">
							<s:select name="suiteId" list="suiteList" listKey="id" listValue="name" ></s:select>
						</s:if>
						<s:else>
							<input value="${suiteName }" disabled="disabled" />
						</s:else>
					 </li>
					 <li>
						<span class="label-like"> 自动续费：</span>
						<s:select name="autoCharge" list="#{1:'自动续费',0:'不续费' }" listKey="key" listValue="value" theme="simple"></s:select>
					 </li>
					 <li class="sub_btn">
						<button class="submit-btn">提交</button>
   					   <button class="cancel-btn">取消</button>
		             </li>
			  </ul>
		</div>	
	</form>
</div>
 <script>
	 KISSY.use("gprs/gprs-post",function(S){
		GPRS.Post.post({
			successUrl:"../suiteOrder/userSuiteOrderList.action?userId=${userId}",
			cancelUrl:"../suiteOrder/userSuiteOrderList.action?userId=${userId}"
		});
	});
</script>
