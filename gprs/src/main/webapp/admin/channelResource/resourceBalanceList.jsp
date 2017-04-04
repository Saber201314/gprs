<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="channel-balance-content" style="display:none;">
	<table id="channel_balance_table" class="table table-striped table-bordered table-hover">
		<tr>
		    <th width='70'>序号</th>
			<th>充值模板</th>
		    <th>通道余额(元)</th>
		</tr>				
	</table>
</div>			
<script>
</script>
