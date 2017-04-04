<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>

<style>
.charge-form-item {
	padding-top:20px;
}

.titleBar {
	padding-bottom: 50px;
}
.charge-form-item{width:600px;}
</style>
<div id="screen" class="page-content">
	<div class="Manager_style">
		<span class="title_name">单号充值</span>
		<div class="sendMsg">
			<input type="hidden" id="charge-url"
				value="${pageContext.request.contextPath}/agent/charge/doSingleCharge.action" />
			<input type="hidden" id="type-input" /> <input type="hidden"
				id="location-input" />
			<div class="charge-form">
				<div class="charge-form-item">
					<span class="leftSpan">手机号码:</span> <input type="text"
							id="mobile-input" style="width:350px;"/>
					<div class="msg-wrapper" style="display:none;"></div>
					<div class="process" style="display:none;"></div>
				</div>
				<div class="charge-form-item take-time">
					<span class="leftSpan">生效时间:</span> <span
						class="btn single-select  immediately">立即生效</span> <span
						class="btn single-select  next-month">次月生效</span>
				</div>
				<div class="charge-form-item location-type">
					<span class="leftSpan">流量类型:</span> <span
						class="btn single-select location-type-item country">全国流量</span> <span
						class="btn single-select location-type-item  province">省内流量</span>
				</div>
				<div class="charge-form-item">
					<span class="leftSpan">充值流量:</span><span class="msg"></span>
					<div class="package-list" style="padding-top:15px;">
						<s:iterator value="packageList">
							<span title="${paymoney }" style="display:none;"
								class="single-select package-list-item" id="${id }"
								type="${type }" location-type="${locationType}"
								locations="${locations }" paymoney=${paymoney }>${name}</span>
						</s:iterator>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="charge-form-item">
					<span class="leftSpan">扣费金额:</span> <span class="money">0</span>元
				</div>
			</div>
		</div>
		<div style="clear:both;"></div>
	</div>
	<div class="btn_operating">
		<button id="submit" class="btn btn-info sendBtn" type="button">提交</button>
	</div>
</div>
<script>
 	KISSY.use("gprs/gprs-charge",function(S){
 		GPRS.Charge.init();
	});
</script>