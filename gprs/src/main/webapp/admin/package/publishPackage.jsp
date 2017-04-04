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
.Manager_style input,select{width:300px;}
li{padding-top:10px;}
label{width:90px;}
.province-controls{width:550px;padding-left:75px;margin-top:-30px;}
</style>

<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<br/>
	<form action="doPublishGprsPackage.action" method="post" id="submit-form">
	<input type="hidden" name="gprsPackage.id" value="<s:property value='gprsPackage.id' />" />
	  <div class="form-elem Manager_style add_user_style ">
	  		  <span class="title_name">添加流量包</span>
			  <ul class="sendMsg clearfix">
					<li>
						<label class="label_name" > 名称：</label>
						<input type="text" style="margin-left:0px;" name="gprsPackage.name" value="<s:property value='gprsPackage.name' />" id="gprsPackageId" required required-msg="名称不能为空！" />
					 </li>
					 <li>
						<label class="label_name" > 展示名称：</label>
						<input type="text" style="margin-left:0px;" name="gprsPackage.alias" value="<s:property value='gprsPackage.alias' />" id="alias" required required-msg="展示名称不能为空！" />
					 </li>
					 <li>
						<label class="label_name" > 流量值：</label>
						<input type="text" style="margin-left:0px;" name="gprsPackage.amount" value="<s:property value='gprsPackage.amount' />" id="amount" required number="true" required-msg="流量值不能为空！" number-msg="请填写数字" />M
					 </li>
					 <li>
						<label class="label_name" > 标准售价：</label>
						<input type="text" style="margin-left:0px;" name="gprsPackage.money" value="<s:property value='gprsPackage.money' />" id="amount" required number="true" required-msg="标准售价不能为空！" number-msg="请填写数字" />元
					 </li>
					 <li>
						<label class="label_name" > 运营商类型：</label>
						<s:select name="gprsPackage.type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" theme="simple"></s:select>
					 </li>
					 <li>
						<label class="label_name" > 流量类型：</label>
						<s:select name="gprsPackage.locationType" list="#{1:'全国流量',2:'省内流量' }" listKey="key" listValue="value" theme="simple"></s:select>
					 </li>
					 <li>
						<label class="label_name"> 支持地区：</label>
						<div class="province-controls">
	                        <div class="checkbox"><label> <input type="checkbox"  class="ace" id="select-all-province" name="allAreas" value="true"><span class="lbl">全国</span></label></div>
							<div id="showProvinces" data="${gprsPackage.locations }">
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="北京" 
							 	name="provinces" required required-msg="请选择支持地区！"> <span class="lbl">北京</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="广东" name="provinces"><span class="lbl">广东</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="上海" name="provinces"><span class="lbl">上海 </span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="天津" name="provinces"><span class="lbl">天津 </span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="重庆" name="provinces"><span class="lbl">重庆</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="辽宁" name="provinces"><span class="lbl">辽宁</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="江苏" name="provinces"><span class="lbl">江苏</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="湖北" name="provinces"><span class="lbl">湖北</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="四川" name="provinces"><span class="lbl">四川</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="陕西" name="provinces"><span class="lbl">陕西</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="河北" name="provinces"><span class="lbl">河北</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="山西" name="provinces"><span class="lbl">山西</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="河南" name="provinces"><span class="lbl">河南</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="吉林" name="provinces"><span class="lbl">吉林</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="山东" name="provinces"><span class="lbl">山东</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="安徽" name="provinces"><span class="lbl">安徽</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="浙江" name="provinces"><span class="lbl">浙江</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="福建" name="provinces"><span class="lbl">福建</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="湖南" name="provinces"><span class="lbl">湖南</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="广西" name="provinces"><span class="lbl">广西</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="江西" name="provinces"><span class="lbl">江西 </span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="贵州" name="provinces"><span class="lbl">贵州 </span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="云南" name="provinces"><span class="lbl">云南</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="西藏" name="provinces"><span class="lbl">西藏</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="海南" name="provinces"> <span class="lbl">海南</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="甘肃" name="provinces"><span class="lbl">甘肃 </span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="宁夏" name="provinces"><span class="lbl">宁夏</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="青海" name="provinces"><span class="lbl">青海</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="新疆" name="provinces"><span class="lbl">新疆</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="黑龙江" name="provinces"><span class="lbl">黑龙江</span></label></div></label>
							 	<label><div class="checkbox"><label><input type="checkbox" class="ace" value="内蒙古" name="provinces"><span class="lbl">内蒙古</span></label></div></label>

							</div>
	                    </div>
					 </li>
					 <li>
						<label class="label_name"> 备注：</label>
						<input type="text" style="margin-left:0px;" name="gprsPackage.memo" value="<s:property value='gprsPackage.memo' />" />
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
			successUrl:"../package/packageList.action",
			cancelUrl:"../package/packageList.action"
		});
		GPRS.Post.initLocation();
	});
</script>
