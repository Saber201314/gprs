<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<link
	href="${pageContext.request.contextPath}/resourse/assets/css/bootstrap.min.css"	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/font-awesome.min.css" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resourse/assets/css/font-awesome-ie7.min.css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/ace.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/ace-rtl.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/assets/css/ace-skins.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourse/css/style.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resourse/css/chosen.css" />	
<script src="${pageContext.request.contextPath}/resourse/assets/js/jquery.min.js"></script>

<script type="text/javascript">
			window.jQuery || document.write("<script src='${pageContext.request.contextPath}/resourse/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
</script>

<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${pageContext.request.contextPath}/resourse/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
</script>

<script src="${pageContext.request.contextPath}/resourse/assets/js/chosen.jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>

<script src="${pageContext.request.contextPath}/resourse/assets/js/chosen.jquery.min.js"></script>		
<script src="${pageContext.request.contextPath}/resourse/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/typeahead-bs2.min.js"></script>

<script src="${pageContext.request.contextPath}/resourse/assets/js/ace-elements.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/js/ace.min.js"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resourse/assets/laydate/laydate.js" type="text/javascript"></script>

<!-- inline scripts related to this page -->      	

<!-- <link rel="stylesheet" href="//g.alicdn.com/kissy/k/5.0.1/css/base.css"> -->
<%-- <link href="${pageContext.request.contextPath}/asserts/css/dx-combo.css" rel="stylesheet" type="text/css" /> --%>
<%-- <link href="${pageContext.request.contextPath}/asserts/css/gprs.css" rel="stylesheet" type="text/css" /> --%>
<script src="${pageContext.request.contextPath}/asserts/js/kissy/seed.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/asserts/js/kissy/import-style-min.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/asserts/js/core.js"></script>
<script src="${pageContext.request.contextPath}/asserts/js/gprs/gprs-base.js"></script>
<script>
	KISSY.use("gprs/gprs-base",function(S){
		GPRS.Base.init();				
	});

</script>