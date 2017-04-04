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

<p class="paginator" style="text-align:center; margin-top:10px;">
	<s:if test="%{page.allRecord>0}">
		<span class="left">共有<strong id="total-record"> ${page.allRecord }</strong> 条记录，<s:if test="%{page.allRecord>0}">当前第<strong> ${ page.pageNo}</strong> 页，</s:if>共 <strong>${page.allPage }</strong> 页</span>
		<span>
		   	<s:if test="%{page.pageNo!=1}">
		        <a href="#" onclick="return goPage(1)">首页</a>
		    </s:if>
		    <s:if test="%{(page.pageNo-1)>0}">
		        <a href="#" onclick="return goPage(${page.pageNo-1})">上一页</a>
		    </s:if>	
		    <s:if test="%{(page.pageNo-3)>0}">
		        <a href="#" onclick="return goPage(${page.pageNo-3})">${page.pageNo-3}</a>
		    </s:if>		    	    
		    <s:if test="%{(page.pageNo-2)>0}">
		        <a href="#" onclick="return goPage(${page.pageNo-2})">${page.pageNo-2}</a>
		    </s:if>
		    <s:if test="%{(page.pageNo-1)>0}">
		        <a href="#" onclick="return goPage(${page.pageNo-1})">${page.pageNo-1}</a>
		    </s:if>
		    <a href="#" class="current_page" onclick="goPage(${page.pageNo})">${page.pageNo}</a>
		    <s:if test="%{page.allPage>=(page.pageNo+1)}">
		        <a href="#" onclick="return goPage(${page.pageNo+1})">${page.pageNo+1}</a>
		    </s:if>
		    <s:if test="%{page.allPage>=(page.pageNo+2)}">
		        <a href="#" onclick="return goPage(${page.pageNo+2})">${page.pageNo+2}</a>
		    </s:if>
		    <s:if test="%{page.allPage>=(page.pageNo+3)}">
		        <a href="#" onclick="return goPage(${page.pageNo+3})">${page.pageNo+3}</a>
		    </s:if>		    
		    <s:if test="%{page.allPage>=(page.pageNo+1)}">
		        <a href="#" onclick="return goPage(${page.pageNo+1})">下一页</a>
		    </s:if>		    
		    <s:if test="%{page.pageNo!=page.allPage}">
		        <a href="#" onclick="return goPage(${page.allPage});">末页</a>
		    </s:if>
		    
		    <%-- <input type="text" id="gotoNum" class="number"></input>
		    <a href="#" onclick="return goDestinePage(${page.allPage});">跳转</a> --%>
		</span>
	</s:if>
	<s:else>
		没有记录！
	</s:else>	
</p>