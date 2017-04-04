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
	.msg-wrapper{width:200px;margin-left:100px;}
	.Add_Manager_style input{height:30px;}
	input{text-indent:3px;margin-left:0px !important;}
	select{width:150px; !important}
	li{width:300px;}
</style>
<div id="screen" class="page-content">
	<form action="doPublishAgent.action" method="post" id="submit-form">
	<input type="hidden" name="user.id" value="<s:property value='user.id' />" />
	<input type="hidden" name="user.limits" value="${user.limits}"  id="hidden-user-limits" />
	<input type="hidden" name="user.feature" value="${user.feature}" />
	  <div class="upload">
	  		<br/>
	  		<br/>
	  		<dl>
	  		<dt>
			<div class="Manager_style clearfix">
				<span class="title_name">基础信息</span>
			  <ul class="Add_Manager_style ">
				<li>
					<label class="label_name">用户名：</label>
					<s:if test="user.id==0">
						<input type="text" name="user.username" value="<s:property value='user.username' />" id="username" required required-msg="用户名不能为空！" />
					</s:if>
					<s:else>
						<input type="hidden" name="user.username" value="<s:property value='user.username' />" >
						<input type="text" disabled="disabled" value="<s:property value='user.username' />" >
					</s:else>
				 </li>
				 <li>
					<label class="label_name">密码：</label>
					<input type="text" name="user.password" value="<s:property value='user.password' />" id="password" required required-msg="密码不能为空！" />
				 </li>
				 
				 <s:if test="user.id==0">
				 <li>
					<label class="label_name">初始余额：</label>
					<input type="text" name="user.money" value="<s:property value='user.money' />" id="money"  number="true" number-msg="初始余额请填写数字" />元
				 </li>
				 </s:if>
				 <li>
					<label class="label_name">透支金额：</label>
					<input type="text" name="user.preMoney" value="<s:property value='user.preMoney' />" id="preMoney"  number="true" number-msg="透支金额请填写数字" />元
				 </li>
				 <li>
					<label class="label_name">报价单：</label>
					<s:select name="user.paperId" list="paperList" listKey="id" listValue="name" theme="simple"></s:select>
				 </li>
				 <li>
					<label class="label_name">有效期：</label>
					<input type="text" name="user.validateTime" value="<s:date name="user.validateTime" format="yyyy-MM-dd"></s:date>" id="start" class="inline laydate-icon" />
				 </li>
				  <li>
					<label class="label_name">名称：</label>
					<input type="text" name="user.name" value="<s:property value='user.name' />"  />
				 </li>				 
				 <li>
					<label class="label_name">电话：</label>
					<input type="text" name="user.phone" value="<s:property value='user.phone' />"  />
				 </li>
				 <li>
					<label class="label_name">备注：</label>
					<input type="text" name="user.memo" value="<s:property value='user.memo' />" />
				 </li>	
				 <li>
					<label class="label_name">兼容性：</label>
					<s:select name="user.compatible" id="compatible" list="#{1:'启动密钥',0:'关闭密钥'}" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
				 </li>
				 <s:if test="user.compatible == 1">
					 <li>
						<label class="label_name">接口密码：</label>
						<input type="text" name="user.apiPassword" id="apiPassword" value="<s:property value='user.apiPassword' />"/>
					 </li>				 					 
					 <li>
						<label class="label_name">接口密钥：</label>
						<input type="text" name="user.apiKey" id="apiKey" value="<s:property value='user.apiKey' />" readonly/>
					 </li>	
				 </s:if>
				 <s:else>
					 <li style="display:none;">
						<label class="label_name">接口密码：</label>
						<input type="text" name="user.apiPassword" id="apiPassword" value="<s:property value='user.apiPassword' />"/>
					 </li>				 					 
					 <li style="display:none;">
						<label class="label_name">接口密钥：</label>
						<input type="text" name="user.apiKey" id="apiKey" value="<s:property value='user.apiKey' />" readonly/>
					 </li>				 
				 </s:else>					 				 		 
			  </ul>
			  </div>
			  </dt>
			  <br/>
			  <br/>
			  <br/>
			  <br/>
			<div class="Manager_style clearfix">
				<span class="title_name">权限管理</span>
				<ul>
						<table class="table table-striped table-bordered table-hover" style="margin-top:20px;">
							<tr>
								<th><div class="checkbox"><label><input type="checkbox" class="select-all ace" /><span class="lbl">充值</span></label></div></th>
								<td><div class="checkbox"><label><input type="checkbox" value="101" class="user-limit-list ace" <s:if test="%{user.containsLimit(101)}">checked="checked"</s:if>  /><span class="lbl">单号充流量</span></label></div></td>

								<td><div class="checkbox"><label><input type="checkbox" value="102" class="user-limit-list ace" <s:if test="%{user.containsLimit(102)}">checked="checked"</s:if>  /><span  class="lbl">批量充流量</span></label></div></td>
							</tr>
							<tr>
								<th><div class="checkbox"><label><input type="checkbox" class="select-all ace" /><span class="lbl">充值卡</span></label></div></th>
								<td><div class="checkbox"><label><input type="checkbox" value="201" class="user-limit-list ace" <s:if test="%{user.containsLimit(201)}">checked="checked"</s:if>  /><span class="lbl">查看充值卡</span></label></div></td>
								<td><div class="checkbox"><label><input type="checkbox" value="202" class="user-limit-list ace" <s:if test="%{user.containsLimit(202)}">checked="checked"</s:if>  /><span class="lbl">生成充值卡</span></label></div></td>
							</tr>
							<tr>
								<th><div class="checkbox"><label><input type="checkbox" class="select-all ace" /><span class="lbl">代理商</span></label></div></th>
								<td><div class="checkbox"><label><input type="checkbox" value="301" class="user-limit-list ace" <s:if test="%{user.containsLimit(301)}">checked="checked"</s:if>  /><span class="lbl">发展代理商</span></label></div></td>
								<td><div class="checkbox"><label><input type="checkbox" value="302" class="user-limit-list ace" <s:if test="%{user.containsLimit(302)}">checked="checked"</s:if>  /><span class="lbl">代理商充值</span></label></div></td>
							</tr>
							<tr>
								<th><div class="checkbox"><label><input type="checkbox" class="select-all ace" /><span class="lbl">移动端</span></label></div></th>
								<td><div class="checkbox"><label><input type="checkbox" value="401" class="user-limit-list ace" <s:if test="%{user.containsLimit(401)}">checked="checked"</s:if>  /><span class="lbl">移动页面设置</span></label></div></td>
								<td><div class="checkbox"><label><input type="checkbox" value="402" class="user-limit-list ace" <s:if test="%{user.containsLimit(402)}">checked="checked"</s:if>  /><span class="lbl">广告设置</span></label></div></td>
								<td><div class="checkbox"><label><input type="checkbox" value="403" class="user-limit-list ace" <s:if test="%{user.containsLimit(403)}">checked="checked"</s:if>  /><span class="lbl">支付宝充值</span></label></div></td>
							</tr>
						</table>
				</ul>
				</div>
				 <div class="btn_operating">
					   <input class="submit-btn btn btn-info" type="button" value="提交">
					   <input type="button" class="btn back-btn" value="返回" onclick="javascript:history.back(-1);">
			     </div>				
			</dl>
		</div>	
	</form>
</div>
 <script>
  	$("#compatible").change(function(){
 		var switchFlg = $(this).val();
 		if(switchFlg == 1){
 			//$("#generateKey").parent().show();
 			$("[name='user.apiPassword']").parent().show();
 			$("[name='user.apiKey']").parent().show();
 		}else{
 			//$("#generateKey").parent().hide();
 			$("[name='user.apiPassword']").parent().hide();
 			$("[name='user.apiKey']").parent().hide();		
 		}	
 	});
 	
 	$("#generateKey").click(function(){
	layer.confirm('确定将产生新的密钥，旧的密钥将会被覆盖，是否要这样操作？', {
		  btn: ['确定','取消'], //按钮
		  icon:3
		}, function(){
	 		$.ajax({
	 	        url:"generateApiKey.action",
	 	        data:null,
	 	        dataType:'json',
	 	        cache:false,
	 	        success:function(data){        	  	        	      		        	
					if(data){
						$("#apiKey").val(data.apiKey);
					} 
					layer.closeAll();
	 	        }
	 	     });		
		}); 	 		
 	});
 	var start = {
	    elem: '#start',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: false,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
	laydate(start);
	laydate.skin('danlan');
	 KISSY.use("gprs/gprs-publishAgent,gprs/gprs-calendar",function(S){
		 GPRS.PublishAgent.init();
		GPRS.Calendar.init();
	});
</script>
