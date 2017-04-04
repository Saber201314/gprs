<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<div id="screen">
	<div class="bar">生成充值卡</div>
	<div class="form-elem">
	<form action="doPublishPaycard.action" method="post" id="submit-form">
		<input type="hidden" name="createPaycardDO.takeTime" id="takeTime" />
		<input type="hidden" name="createPaycardDO.packageIdMobile" id="packageIdMobile" />
		<input type="hidden" name="createPaycardDO.packageIdUnicom" id="packageIdUnicom" />
		<input type="hidden" name="createPaycardDO.packageIdTelecom" id="packageIdTelecom" />
		<ul>
			<li><span class="label-like" >代理商账号：</span>
				<select id="agent-level" name="createPaycardDO.account" data-account="${session.user.username}"
				 required required-msg="请选择代理商"></select>
			</li>
			<li><span class="label-like" >开卡数量：</span>
				<input type="text" name="createPaycardDO.num" 
				required numeber required-msg="请填写开卡数量" number-msg="请填写数字" />
			</li>
			<li><span class="label-like" >开卡面额：</span>
				<input type="text" name="createPaycardDO.money"
				required numeber required-msg="请填写开卡面额" number-msg="请填写数字" />
			</li>
			<li><span class="label-like" >有效期：</span>
				<input type="text" name="createPaycardDO.validateTime" class="date"
				required required-msg="请填写有效期" />
			</li>
			<li><span class="label-like" >关键词：</span>
				<input type="text" name="createPaycardDO.keyword" value="${createPaycardDO.memo}" 
				required required-msg="请填写关键词" />
			</li>
			<li class="charge-form-item charge-time">
	            <span class="label-like" >生效时间:</span>
	            <span class="single-select  immediately single-select-selected">立即生效</span>
	            <span class="single-select  next-month">次月生效</span>
	        </li>
			<li class="charge-form-item gprs-type">
	            <span class="label-like" >流量类型:</span>
	            <span class="single-select gprs-type-item country single-select-selected">全国流量</span>
	            <span class="single-select gprs-type-item  province">省内流量</span>
	        </li>
	        <li class="charge-form-item">
	            <span class="label-like">移动流量:</span>
	            <div class="package-list mobile-package">
		           	<s:iterator value="packageList">
		           		<s:if test="type==1">
			           		<span <s:if test="locationType==2">style="display:none;"</s:if> class="single-select package-list-item <s:if test="locationType==1">contry-package</s:if><s:else>province-package</s:else>" id="${id }"  paymoney=${paymoney }>${alias}</span>
		           		</s:if>
		           	</s:iterator>
	            </div>
	           	<div style="clear:both;"></div>
	        </li>
	        <li class="charge-form-item">
	            <span class="label-like">联通流量:</span>
	            <div class="package-list unicom-package">
		           	<s:iterator value="packageList">
		           		<s:if test="type==2">
			           		<span <s:if test="locationType==2">style="display:none;"</s:if> class="single-select package-list-item <s:if test="locationType==1">contry-package</s:if><s:else>province-package</s:else>" id="${id }"  paymoney=${paymoney }>${alias}</span>
		           		</s:if>
		           	</s:iterator>
	            </div>
	           	<div style="clear:both;"></div>
	        </li>
	        <li class="charge-form-item">
	            <span class="label-like">电信流量:</span>
	            <div class="package-list telecom-package">
		           	<s:iterator value="packageList">
		           		<s:if test="type==3">
			           		<span <s:if test="locationType==2">style="display:none;"</s:if> class="single-select package-list-item <s:if test="locationType==1">contry-package</s:if><s:else>province-package</s:else>" id="${id }"  paymoney=${paymoney }>${alias}</span>
		           		</s:if>
		           	</s:iterator>
	            </div>
	           	<div style="clear:both;"></div>
	        </li>
	        <li>
	        	<span class="label-like">激活状态:</span>
	        	<s:select name="queryChargeOrderDO.status" list="#{0:'已激活',-1:'未激活' }" listKey="key" listValue="value"  theme="simple"></s:select>
	        </li>
  			 <li class="sub_btn">
							   <button class="submit-btn">提交</button>
							   <button class="cancel-btn">取消</button>
		             </li>
		</ul>		
	</form>
	</div>
</div>
 <script>
 	KISSY.use("gprs/gprs-post,gprs/gprs-paycard,gprs/gprs-calendar",function(S){
		GPRS.Post.loadAgentLevel();
		GPRS.Paycard.init({
			successUrl:"../paycard/paycardList.action",
			cancelUrl:"../paycard/paycardList.action"
		});
		GPRS.Calendar.init();
	});
</script>