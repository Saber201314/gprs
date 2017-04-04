<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<s:set name="menu_open" value="2"></s:set>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
	.msg-wrapper{width:100%;}
	.clearfix{margin-top:20px;}
	.Manager_style input,select{width:300px;}
	select{margin-left:10px;}
	li{padding-top:10px;}
	label{width:90px;}
</style>
<div id="screen" class="page-content">
	<div class="bar"></div>
	<div class="form-elem">
	<form action="doChargeForAgent.action" method="post" id="submit-form">
		<input type="hidden" name="id" value="${id}" />
		<div class="Manager_style">
			<span class="title_name">充值</span>
		<ul class="sendMsg clearfix">
			<li><label class="label-name" >帐号：</label>
				<input type="text" disabled="disabled" value="${user.username}" />
			</li>
			<li>
				<label class="label-name" >充值金额：</label>
				<input type="text" class="charge_price" name="money" required number required-msg="请填写金额" number-msg="请填写数字"/>
				<smap id="tipMessage" class="msg-wrapper" style="display:none;padding:5px 20px 3px;"></smap>				
			</li>
			<li><label class="label-name">充值方式：</label>
			<select name="payType">
				<option value="1">对公账号收款</option>
				<option value="2">对公支付宝收款</option>
				<option value="3">对私账号收款</option>
				<option value="4">对私支付宝收款</option>
				<option value="5">账号金额转移</option>
				<option value="6">授权信用加款</option>	
				<option value="8">账号测试加款</option>	
				<option value="9">其他原因充扣款</option>
			</select>				
			</li>
			<li><label class="label-name" >有效期：</label>
				<input type="text" name="validDate" value="<s:date name="user.validateTime" format="yyyy-MM-dd"></s:date>" id="start" class="inline laydate-icon"  />
			</li>
			<li><label class="label-name" >备注：</label>
				<textarea rows="4" cols="50" type="text" name="memo" style="width:500px;height:100px;"> <s:property value="memo" /></textarea>
			</li>						
		</ul>
		</div>	
		
		 <div class="sub_btn btn_operating">
			   <input class="submit-btn btn btn-info" type="button" value="提交">
			   <input type="button" class="btn cancel-btn1" value="取消">
	     </div>				
	</form>
	</div>
	<div style="display:none;" id="balance_tip"></div>
</div>
<script>

var start = {
	    elem: '#start',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
laydate(start);	
	
KISSY.use("gprs/gprs-post,gprs/gprs-calendar",function(S){
	GPRS.Post.post({
		successUrl:"../agent/agentList.action",
		cancelUrl:"../agent/agentList.action"
	});
	GPRS.Calendar.init();
	GPRS.Post.tipMessageByChangePrice();
});
</script>