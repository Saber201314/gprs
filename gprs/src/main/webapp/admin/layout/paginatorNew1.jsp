<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>
.paginator a {padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:5px;}
.current_page{background:#428bca;color:#FFFFFF !important;}
.left {margin-right:20px;}
</style>
<p class="paginator" style="text-align:center;padding:5px;">
		<span class="left" >共有<strong id="totalRecords"> </strong> 条记录，
		当前第<strong id="curPage"> </strong> 页，
		共 <strong id="allPage"></strong> 页</span>	
		<span id="page_info">		    		    	    
		</span>
</p>