<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
.charge-form-item {
	padding-top:20px;
}
.titleBar {
	padding-bottom: 50px;	
}
textarea{width:600px;height:100px;}
.charge-form-item{width:600px;}
</style>
<div id="screen" class="page-content">
	<div class="Manager_style">
		<span class="title_name">批量充值</span>
		<div class="sendMsg">
			<div class="charge-form">
				<div class="charge-form-item">
					<span class="leftSpan">号码功能:</span> <span class="rightSpan">
						<a href="javascript:void(0)" class="btn clear_repeat">清除重号</a> <a
						href="javascript:void(0)" class="btn clear_non_mobile">清除非手机号</a>
					</span>
				</div>
				<div class="charge-form-item">
					<span class="leftSpan">手机号码:</span> <span class="rightSpan">
						<textarea class="txt" id="num-area" name="receivePhone"></textarea>
						<p>
							已输入[<a href="#" class="sig" id="num-count">0</a>]条
						</p>
					</span>
				</div>
				<div class="charge-form-item take-time">
					<span class="leftSpan">生效时间:</span> <span
						class="btn single-select  immediately single-select-selected">立即生效</span>
					<span class="btn single-select  next-month">次月生效</span>
				</div>
				<div class="charge-form-item location-type">
					<span class="leftSpan">流量类型:</span> <span
						class="btn single-select location-type-item country single-select-selected">全国流量</span>
					<span class="btn single-select location-type-item  province">省内流量</span>
				</div>
				<div class="charge-form-item">
					<span class="leftSpan">移动流量:</span>
					<div class="package-list mobile-package">
						<s:iterator value="packageList">
							<s:if test="type==1">
								<span <s:if test="locationType==2">style="display:none;"</s:if>
									class="single-select package-list-item <s:if test="locationType==1">contry-package</s:if><s:else>province-package</s:else>"
									id="${id }" paymoney="${money }" title="${money }元">${name}</span>
							</s:if>
						</s:iterator>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="charge-form-item">
					<span class="leftSpan">联通流量:</span>
					<div class="package-list unicom-package">
						<s:iterator value="packageList">
							<s:if test="type==2">
								<span <s:if test="locationType==2">style="display:none;"</s:if>
									class="single-select package-list-item <s:if test="locationType==1">contry-package</s:if><s:else>province-package</s:else>"
									id="${id }" paymoney="${money }" title="${money }元">${name}</span>
							</s:if>
						</s:iterator>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="charge-form-item">
					<span class="leftSpan">电信流量:</span>
					<div class="package-list telecom-package">
						<s:iterator value="packageList">
							<s:if test="type==3">
								<span <s:if test="locationType==2">style="display:none;"</s:if>
									class="single-select package-list-item <s:if test="locationType==1">contry-package</s:if><s:else>province-package</s:else>"
									id="${id }" paymoney="${money }" title="${money }元">${name}</span>
							</s:if>
						</s:iterator>
					</div>
					<div style="clear:both;"></div>
				</div>
				<!-- <div class="charge-form-item">
	        	<a href="#" class="sendBtn">提交</a>
	        </div> -->
			</div>
		</div>
	</div>
	<div style="clear:both;"></div>
	<div class="btn_operating">
		<button id="submit" class="btn btn-info sendBtn" type="button">提交</button>
	</div>
</div>
<script>
	KISSY.use("gprs/gprs-batch-charge", function(S) {
		GPRS.BatchCharge.init();
	});

	
</script>