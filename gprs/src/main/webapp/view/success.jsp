<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Insert title here</title>
<jsp:include page="/cssjs.jsp"></jsp:include>
</head>
<body>
	success
	
	${packageObj}
</body>
<script type="text/javascript">
$(function(){
	var jsonList=[];
	var json={};
	json.channel =  "aa";
	jsonList.push(json);
	$.ajax({type:"POST",
			url:"saveChannelCacheData.action",
			data:{cacheChannelData:JSON.stringify(jsonList)},
			datatype:"json",
			success:function(data){  
		 	console.log('a');
		}
	})    
	
})


</script>
</html>