<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>

<style>
.radio,.checkbox {
	padding-left: 0px;
}
label {
	width: 80px;
}
li{padding-top:5px !important;}
</style>
<div id="screen" class="page-content">
	<div class="bar1">
		<span style="float:right"> 
			<span><a href="#"
				class="delete-list btn1" tip="删除代理商帐号将同时删除其子帐号，您确定要执行此操作吗？">删除</a></span>
			<span><a href="publishAgent.action" class="btn1">添加代理商</a></span>
			<span><a href="#" class="export-excel-datas btn1">导出Excel</a></span>
		</span>
	</div>
	<div class="errorMsg">
		<%-- <s:actionerror /> --%>
	</div>
	<div class="search">
		<form id="ydForm" action="agentList.action" method="post">
		<div class="search_style">
			<input type="hidden" id="username" name="agent" value="${agent}"/>
			<ul class="search_content clearfix">
				<%-- <li><label class="lf">代理商：</label><select id="agent-level" name="queryUserDO.agent"></select></li> --%>
				<li><label class="lf">用户名：</label><input type="text" name="username"
					value="${username }" /></li>
				<li><label class="lf">名称：</label><input type="text" name="name"
					value="${name }" /></li>
				<li style="width:20px;"><button type="submit" class="btn_search sub_btn1">查询</button></li>
			</ul>
		</div>
		</form>
	</div>

	<form action="deleteAgentList.action" method="get" id="delete-form">
		<table cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
			<tr>
				<th style="width:20px;"><input type="checkbox" class="select-all select-all_1" /></th>				
				<th>用户名</th>
				<th>密码</th>
				<th>代理商</th>
				<th>姓名</th>
				<th>电话</th>
				<th>余额</th>
				<th>透支金额</th>
				<th>有效期</th>
				<th>报价单</th>
				<th>流量池</th>
				<th>添加时间</th>
				<th width="10%" colspan="2">操作</th>
			</tr>
			<c:forEach var="item" items="${usersList}">
				<tr>
					<td><input type="checkbox" name="idList"
						class="check-item" value="${item.id}" /></td>
					<td>${item.username}</td>
					<td>${item.password}</td>
					<td>${item.agent}</td>
					<td>${item.name}</td>
					<td>${item.phone}</td>
					<td>${item.moneyString}</td>
					<td>${item.preMoney}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.validateTime }"/></td>
					<td>${item.paperName}</td>
					<td><a
						href="../suiteOrder/userSuiteOrderList.action?userId=${item.id }">${item.suiteSize }</a></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.optionTime }"/></td>
					<td><a
						href="../agent/chargeForAgent.action?id=${item.id }">充值</a>
					</td> 
					<td><a
						href="../agent/publishAgent.action?id=${item.id }">编辑</a>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="14"><div align="left"><jsp:include
							page="../layout/paginator.jsp"></jsp:include></div></td>
			</tr>
		</table>
	</form>
</div>
<form id="pageForm" action="agentList.action" method="post">
	<input type="hidden" id="page.pageNum" name="page.pageNum" value="" /> <input
		type="hidden" name="name"
		value="${name}" /> <input
		type="hidden" name="username"
		value="${username}" /> <input
		type="hidden" name="agent"
		value="${agent}" />
</form>

<script>
	function goPage(pageNo) {
		var form = document.getElementById("pageForm");
		var page = document.getElementById("page.pageNum");
		page.value = pageNo;
		form.submit();
		return false;
	}

	KISSY.use("gprs/gprs-post", function(S) {
		GPRS.Post.deleteAll();
		//GPRS.Post.loadAgentLevel();
		GPRS.Post.exportAgentDatas();
	});

	
</script>