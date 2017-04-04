<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>
.paginator a {padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:5px;font-size:13px;}
.current_page{background:#428bca;color:#FFFFFF !important;}
.left {margin-right:20px;}

</style>
<p class="paginator" style="text-align:center margin-top:10px;">
	<s:if test="%{pagePackageList.totalPages>0}">
		<span class="left">共有<strong id="total-record"> ${pagePackageList.totalRows }</strong> 条记录</span>
		
		<span id="page_info">
		   	<s:if test="%{pagePackageList.page!=1}">
		        <a href="#" onclick="return goPage(1)">首页</a>
		    </s:if>
		    <s:if test="%{(pagePackageList.page-1)>0}">
		        <a href="#" onclick="return goPage(${pagePackageList.page-1})">上一页</a>
		    </s:if>			    
		    <s:if test="%{(pagePackageList.page-3)>0}">
		        <a href="#" onclick="return goPage(${pagePackageList.page-3})">${pagePackageList.page-3}</a>
		    </s:if>			    
		    <s:if test="%{(pagePackageList.page-2)>0}">
		        <a href="#" onclick="return goPage(${pagePackageList.page-2})">${pagePackageList.page-2}</a>
		    </s:if>
		    <s:if test="%{(pagePackageList.page-1)>0}">
		        <a href="#" onclick="return goPage(${pagePackageList.page-1})">${pagePackageList.page-1}</a>
		    </s:if>
		    <a href="#" class="current_page" onclick="goPage(${pagePackageList.page})">${pagePackageList.page}</a>
		    <s:if test="%{pagePackageList.totalPages>=(pagePackageList.page+1)}">
		        <a href="#" onclick="return goPage(${pagePackageList.page+1})">${pagePackageList.page+1}</a>
		    </s:if>
		    <s:if test="%{pagePackageList.totalPages>=(pagePackageList.page+2)}">
		        <a href="#" onclick="return goPage(${pagePackageList.page+2})">${pagePackageList.page+2}</a>
		    </s:if>
		    <s:if test="%{pagePackageList.totalPages>=(pagePackageList.page+3)}">
		        <a href="#" onclick="return goPage(${pagePackageList.page+3})">${pagePackageList.page+3}</a>
		    </s:if>		
		    <s:if test="%{pagePackageList.totalPages>=(pagePackageList.page+1)}">
		        <a href="#" onclick="return goPage(${pagePackageList.page+1})">下一页</a>
		    </s:if>		    	    
		    <s:if test="%{pagePackageList.page!=pagePackageList.totalPages}">
		        <a href="#" onclick="return goPage(${pagePackageList.totalPages});">末页</a>
		    </s:if>	    
		</span>
	</s:if>
	<s:else>
		没有记录！
	</s:else>	
</p>