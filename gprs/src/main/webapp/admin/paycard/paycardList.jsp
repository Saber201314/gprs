<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
.radio, .checkbox {padding-left: 0px;}
label{width:80px;}
.search_content input{width:163px;}
select{width:163px;}
input[type='text']{margin-left:0px;}
</style>
<div id="screen" class="page-content">
	<div class="bar1">
	<span style="float:right">
	  <span><a class="btn1 export-card export-part" href="#">导出</a></span>
	  <span><a class="btn1 delete-list " href="#">删除</a></span>
	  <span><a class="btn1 distribute-list " href="#">划拨</a></span>
	  <span><a class="btn1 batch-change-status active" href="#" tip="确实要激活吗？">激活</a></span>
	  <span><a class="btn1 batch-change-status lock" href="#" tip="确实要锁定吗？">锁定</a></span>
	  <s:if test="#session.user.containsLimit(202)">
	  <span><a class="btn1" href="publishPaycard.action">生成充值卡</a></span>
	  </s:if>
	</span>
	</div>
	<div class="search">
	    <form method="post" action="paycardList.action" id="ydForm">
	    <div class="search_style">
		      <ul class="search_content clearfix">
		      	<li><label class="lf">代理商：</label><select id="agent-level" name="queryPaycardDo.account" data-account="${queryPaycardDo.account }"></select></li>
		      	<li><label class="lf">卡号：</label><input type="text" value="${queryPaycardDo.pin }" name="queryPaycardDo.pin"></li>
		      	<li><label class="lf">起始卡号：</label><input type="text" value="${queryPaycardDo.pinFrom }" name="queryPaycardDo.pinFrom"></li>
		      	<li><label class="lf">结束卡号：</label><input type="text" value="${queryPaycardDo.pinTo }" name="queryPaycardDo.pinTo"></li>
		      	<li><label class="lf">面额：</label><input type="text" value="${queryPaycardDo.money }" name="queryPaycardDo.money"></li>
		      	<li><label class="lf">激活状态：</label><s:select name="queryPaycardDo.status" list="#{0:'已激活',-1:'未激活' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
		      	<li><label class="lf">开卡日期：</label><input type="text" value="<s:date name="queryPaycardDo.createtimeFrom" format="yyyy-MM-dd HH:mm:ss"></s:date>" name="queryPaycardDo.createtimeFrom" id="start" class="inline laydate-icon"  ></li>
		      	<li><label class="lf">开卡日期：</label><input type="text" value="<s:date name="queryPaycardDo.createtimeTo" format="yyyy-MM-dd HH:mm:ss"></s:date>" name="queryPaycardDo.createtimeTo" id="end" class="inline laydate-icon" ></li>
				<li><label class="lf">使用状态：</label><s:select name="queryPaycardDo.sold" list="#{1:'已使用',0:'未使用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select></li>
		      	<li><label class="lf">手机号码：</label><input type="text" value="${queryPaycardDo.mobile }" name="queryPaycardDo.mobile"></li>
		      	<li style="width:90px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
		      </ul>
	      </div>
	    </form>
	 </div>
	
	<form action="deleteCardList.action" method="get" id="delete-form">
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<th width="20"><input name="form-field-checkbox" type="checkbox" class="select-all select-all_1" /></th>
			<th>关键词</th>
			<th width="100px">卡号</th>
			<th>密码</th>
			<th>面额</th>
			<th width="100px">流量组合</th>
			<th>生效时间</th>
			<th>开卡日期</th>
			<th>有效期</th>
			<th>使用状态</th>
			<th>激活状态</th>
			<th>代理商</th>
		</tr>
		<s:iterator value="paycardList">
			<tr>
				<td><input type="checkbox" class="check-item" name="queryPaycardDo.idList" value="${id }" /></td>
				<td>${keyword }</td>
				<td>${pin }</td>
				<td>${password}</td>
				<td>${money}</td>
				<td>
					<p>M:${packageAliasMobile}</p>
					<p>U:${packageAliasUnicom}</p>
					<p>T:${packageAliasTelecom}</p>
				</td>
				<td>
					<s:if test="takeTime==0">立即生效</s:if>
					<s:else>次月生效</s:else>
				</td>
				<td><s:date name="optionTime" format="yyyy-MM-dd"></s:date></td>
				<td><s:date name="validateTime" format="yyyy-MM-dd"></s:date></td>
				<td>
					<s:if test="sold==1">
						已使用/${mobile}<br/>
						<s:date name="usedtime" format="yyyy-MM-dd HH:mm"></s:date>
					</s:if>
					<s:else>未使用</s:else>
				</td>
				<td>
					<s:if test="status==0">已激活</s:if>
					<s:else>未激活</s:else>
				</td>
				<td>${account }</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="12"><div><jsp:include page="../layout/paginator.jsp"></jsp:include></div></td>
		</tr>
	</table>
	</form>
</div>

<form id="pageForm" action="paycardList.action" method="post" >
	<input type="hidden" id="page.pageNo" name="page.pageNo" value="" />
	<input type="hidden" name="queryPaycardDo.account" value="${queryPaycardDo.account }" />
	<input type="hidden" value="${queryPaycardDo.pin }" name="queryPaycardDo.pin">
	<input type="hidden" value="${queryPaycardDo.pinFrom }" name="queryPaycardDo.pinFrom">
	<input type="hidden" value="${queryPaycardDo.pinTo }" name="queryPaycardDo.pinTo">
	<input type="hidden" value="${queryPaycardDo.money }" name="queryPaycardDo.money">
	<input type="hidden" name="queryPaycardDo.sold" value="${queryPaycardDo.sold}">
	<input type="hidden" name="queryPaycardDo.status" value="${queryPaycardDo.status}">
	<input type="hidden" name="queryPaycardDo.mobile" value="${queryPaycardDo.mobile}">
	<input type="hidden" value="<s:date name="queryPaycardDo.createtimeFrom" format="yyyy-MM-dd HH:mm:ss"></s:date>" name="queryPaycardDo.createtimeFrom" />
	<input type="hidden" value="<s:date name="queryPaycardDo.createtimeTo" format="yyyy-MM-dd HH:mm:ss"></s:date>" name="queryPaycardDo.createtimeTo" />
</form>
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
	var end = {
	    elem: '#end',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59',
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	        start.max = datas; //结束日选好后，重置开始日的最大日期
	    }
	};
	laydate(start);
	laydate(end);	
	laydate.skin('danlan');
	function goPage(pageNo){
		var form=document.getElementById("pageForm");
		var page=document.getElementById("page.pageNo");
		page.value=pageNo;
		form.submit();
	return false;
	}
	KISSY.ready(function(S){  
		S.use("gprs/gprs-post,gprs/gprs-paycard",function(){
			GPRS.Post.loadAgentLevel();
			GPRS.Post.deleteAll();
			
			GPRS.Paycard.init();
		});
	});
</script>
<div id="popup-distribute-card" class="hidden">
	<div class="popup-body" style="width: 350px; height: 130px;">
		<div style="width:100%;height:100%;overflow:auto;">
			<ul class="distribute-card-form">
				<li>请选择代理商：<select class="select-agent"></select></li>
				<li><button class="distribute-btn">确定</button></li>
			</ul>
		</div>
	</div>
</div>