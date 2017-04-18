<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<p class="paginator" style="text-align:center margin-top:10px;">
	<c:choose>
		<c:when test="${page.total > 0l}">
			<span class="left">共有<strong id="total-record"> ${page.total }</strong> 条记录，
			<c:if test="${page.total > 0l}">
				当前第<strong> ${ page.pageNum}</strong> 页，
			</c:if>
			共 <strong>${page.pages }</strong> 页</span>
			<span>
			   	<c:if test="${ page.pageNum!=1}">
			        <a href="#" onclick="return goPage(1)">首页</a>
			    </c:if>
			    <c:if test="${( page.pageNum-1)>0}">
			        <a href="#" onclick="return goPage(${ page.pageNum-1})">上一页</a>
			    </c:if>			    
			    <c:if test="${( page.pageNum-3)>0}">
			        <a href="#" onclick="return goPage(${ page.pageNum-3})">${ page.pageNum-3}</a>
			    </c:if>			    
			    <c:if test="${( page.pageNum-2)>0}">
			        <a href="#" onclick="return goPage(${ page.pageNum-2})">${ page.pageNum-2}</a>
			    </c:if>
			    <c:if test="${( page.pageNum-1)>0}">
			        <a href="#" onclick="return goPage(${ page.pageNum-1})">${ page.pageNum-1}</a>
			    </c:if>
			    <a href="#" class="current_page" onclick="goPage(${ page.pageNum})">${ page.pageNum}</a>
			    <c:if test="${ page.pages>=( page.pageNum+1)}">
			        <a href="#" onclick="return goPage(${ page.pageNum+1})">${ page.pageNum+1}</a>
			    </c:if>
			    <c:if test="${ page.pages>=( page.pageNum+2)}">
			        <a href="#" onclick="return goPage(${ page.pageNum+2})">${ page.pageNum+2}</a>
			    </c:if>
			    <c:if test="${ page.pages>=( page.pageNum+3)}">
			        <a href="#" onclick="return goPage(${ page.pageNum+3})">${ page.pageNum+3}</a>
			    </c:if>		
			    <c:if test="${ page.pages>=( page.pageNum+1)}">
			        <a href="#" onclick="return goPage(${ page.pageNum+1})">下一页</a>
			    </c:if>		    	    
			    <c:if test="${ page.pageNum!= page.pages}">
			        <a href="#" onclick="return goPage(${ page.pages});">末页</a>
			    </c:if>	    
			</span>
		
		</c:when>
		<c:otherwise>
			没有记录！
		</c:otherwise>
	</c:choose>
</p>