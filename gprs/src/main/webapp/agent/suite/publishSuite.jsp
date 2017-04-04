<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<s:set name="menu_open" value="6"></s:set>
<jsp:include page="../layout/top.jsp"></jsp:include>
<div id="screen">
	<div class="bar">
		<span class="left"><span class="img">&nbsp;</span>添加流量池</span>
	</div>
	<form action="doPublishSuite.action" method="post" id="submit-form">
	<input type="hidden" name="suite.id" value="<s:property value='suite.id' />" />
	  <div class="form-elem">
			  <ul>
					<li>
						<span class="label-like"> 名称：</span>
						<input type="text" name="suite.name" value="<s:property value='suite.name' />" id="suiteId" required required-msg="名称不能为空！" />
					 </li>
					 <li>
						<span class="label-like"> 展示名称：</span>
						<input type="text" name="suite.alias" value="<s:property value='suite.alias' />" id="alias" required required-msg="展示名称不能为空！" />
					 </li>
					 <li>
						<span class="label-like"> 流量值：</span>
						<input name="suite.amount" value="<s:property value='suite.amount' />" id="amount" required number="true" required-msg="流量值不能为空！" number-msg="请填写数字" />M
					 </li>
					 <li>
						<span class="label-like"> 运营商类型：</span>
						<s:select name="suite.type" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" theme="simple"></s:select>
					 </li>
					 <li>
						<span class="label-like"> 流量类型：</span>
						<s:select name="suite.locationType" list="#{1:'全国流量',2:'省内流量' }" listKey="key" listValue="value" theme="simple"></s:select>
					 </li>
					 <li>
						<span class="label-like"> 支持地区：</span>
						<div class="province-controls">
	                        <input type="checkbox"  id="select-all-province" name="allAreas" value="true">全国
							<div id="showProvinces" data="${suite.locations }">
							 	<input type="checkbox" value="北京" name="provinces" required required-msg="请选择支持地区！">北京 <input type="checkbox" value="广东" name="provinces">广东 <input type="checkbox" value="上海" name="provinces">上海 <input type="checkbox" value="天津" name="provinces">天津 <input type="checkbox" value="重庆" name="provinces">重庆 <input type="checkbox" value="辽宁" name="provinces">辽宁 <input type="checkbox" value="江苏" name="provinces">江苏 <input type="checkbox" value="湖北" name="provinces">湖北 <input type="checkbox" value="四川" name="provinces">四川 <input type="checkbox" value="陕西" name="provinces">陕西 <input type="checkbox" value="河北" name="provinces">河北 <input type="checkbox" value="山西" name="provinces">山西 <input type="checkbox" value="河南" name="provinces">河南 <input type="checkbox" value="吉林" name="provinces">吉林 <input type="checkbox" value="山东" name="provinces">山东 <input type="checkbox" value="安徽" name="provinces">安徽 <input type="checkbox" value="浙江" name="provinces">浙江 <input type="checkbox" value="福建" name="provinces">福建 <input type="checkbox" value="湖南" name="provinces">湖南 <input type="checkbox" value="广西" name="provinces">广西 <input type="checkbox" value="江西" name="provinces">江西 <input type="checkbox" value="贵州" name="provinces">贵州 <input type="checkbox" value="云南" name="provinces">云南 <input type="checkbox" value="西藏" name="provinces">西藏 <input type="checkbox" value="海南" name="provinces">海南 <input type="checkbox" value="甘肃" name="provinces">甘肃 <input type="checkbox" value="宁夏" name="provinces">宁夏 <input type="checkbox" value="青海" name="provinces">青海 <input type="checkbox" value="新疆" name="provinces">新疆 <input type="checkbox" value="黑龙江" name="provinces">黑龙江 <input type="checkbox" value="内蒙古" name="provinces">内蒙古
							</div>
	                    </div>
					 </li>
					 <li>
						<span class="label-like"> 有效期：</span>
						<input type="text" name="suite.validate" value="<s:property value='suite.validate' />"  required number="true" required-msg="有效期不能为空！" number-msg="请填写数字" />天
					 </li>
					 <li>
						<span class="label-like"> 价格：</span>
						<input type="text" name="suite.money" value="<s:property value='suite.money' />"  required number="true" required-msg="价格不能为空！" number-msg="请填写数字" />元
					 </li>
					 <li>
						<span class="label-like"> 月底清零：</span>
						<s:select name="suite.clearMonthEnd" list="#{0:'不清零',1:'清零' }" listKey="key" listValue="value" theme="simple"></s:select>
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
			successUrl:"../suite/suiteList.action",
			cancelUrl:"../suite/suiteList.action"
		});
		GPRS.Post.initLocation();
	});
</script>
