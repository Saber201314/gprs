<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>
li{padding-top:5px;}
label{width:80px;}
.fix-wrap{margin-left:100px;}
.btn_operating{margin-left:180px;width:auto !important;}
select,.chosen-container{margin-left:10px;}
.chosen-container{width:180px !important;}
.fix-wrap select,.fix-wrap input,.fix-wrap textarea,.fix-wrap input{width:180px;}
</style>
<div id="screen" class="channel-resource-content" style="display:none;">
	<div class="bar">
	</div>
	<br/>
	<form action="publishChannelResource.action" method="post" id="submit-form">
	  <div class="form-elem">
	  	<div class="fix-wrap">
	  		  <input type="hidden" id="resourceId"name="channelRes.id" value="<c:out value="${channelRes.id}"/>"/>
			  <ul>
				<li><label class="label_name" >上游通道：</label>
					<select id="submit_channel" ></select>
				</li>			  
				<li><label class="label_name" >运营商：</label> 
					<select id="merchant">
						<c:forEach var="item" items="移动,联通,电信" varStatus="status">
							<option value="${status.index+1 }">${item }</option>
						</c:forEach>
					</select> 
					<%-- <s:select id="merchant" list="#{1:'移动',2:'联通',3:'电信' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select> --%>
				</li>				
				<li><label class="label_name" >支持地区：</label>
					<select id="location" class="terrority-select" name="channelRes.district"></select>
				</li>
				<li><label class="label_name" >状态：</label>
					<select id="locationType">
						<c:forEach var="item" items="漫游,不漫游" varStatus="status">
							<option value="${status.index+1 }">${item }</option>
						</c:forEach>
					</select> 
						
					<%-- <s:select id="locationType" list="#{1:'漫游',2:'不漫游'}" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select> --%>
				</li>				
				<li><label class="label_name" >产品规格：</label>
					<textarea id="standard" style="height:70px;"></textarea>
				</li>
				<li><label class="label_name" >接入折扣：</label>
					<input type="text" id="inDiscount"/>
				</li>
				<li><label class="label_name" >状态：</label>
					<select id="status">
						<c:forEach var="item" items="正常,维护" varStatus="status">
							<option value="${status.index+1 }">${item }</option>
						</c:forEach>
					</select> 
				
					<%-- <s:select id="status" list="#{1:'正常',2:'维护'}" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select> --%>
				</li>
				<li><label class="label_name" >政策：</label>
					<select id="policy">
						<c:forEach var="item" items="不限价,限价" varStatus="status">
							<option value="${status.index+1 }">${item }</option>
						</c:forEach>
					</select>
					
					<%-- <s:select id="policy" list="#{1:'不限价',2:'限价'}" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select> --%>
				</li>
				<li><label class="label_name" >票据：</label>
					<select id="policy">
						<c:forEach var="item" items="带票,不带票" varStatus="status">
							<option value="${status.index+1 }">${item }</option>
						</c:forEach>
					</select>
					
					<%-- <s:select id="payBill" list="#{1:'带票',2:'不带票'}" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select> --%>
				</li>	
				<li><label class="label_name" >备注：</label>
					<textarea id="message" style="height:100px;"></textarea>
				</li>																				
			  </ul>
			</div>
			 <div class="sub_btn btn_operating">
				   <input class="submit-btn btn btn-info" type="button" value="提交">
				   <input type="button" class="btn cancel-btn" value="取消" onclick="clickCancelBtn();">
		     </div>				
		</div>	
	</form>
</div>
 <script>
	if($("#submit_channel").length>0){
		$.ajax({
	        url:"getCurrentChannelList.action",
	        data:{},
	        dataType:'json',
	        cache:false,
	        success:function(data){
	        	if(data && data.length >0){
	    			var html = [];
	    			html.push('<option value="">请选择</option>');
	    			html.push("<option></option>");
	        		for(var i =0;i<data.length;i++){
	        			html.push("<option value="+data[i].id+">"+data[i].name+"</option>");
	        		}
	    			$("#submit_channel").append(html.join(""));
	    			$("#submit_channel").chosen({search_contains:true});
	        	}
	     }});		
	}
	
	function updateChannelResource(resourceId){
		$.ajax({
	        url:"getChannelResourceData.action",
	        data:{"channelRes.id":resourceId},
	        dataType:'json',
	        cache:false,
	        success:function(data){
	        	$("#resourceId").val(data.id);
	        	$("#submit_channel").val(data.channelId);
	        	$("#submit_channel").trigger("chosen:updated");
	        	$("#merchant").val(data.merchant);
	        	$("#location").val(data.district);
	        	$("#locationType").val(data.locationType);
	        	$("#standard").val(data.standard);
	        	$("#inDiscount").val(data.inDiscount);
	        	
	        	$("#status").val(data.status);
	        	$("#payBill").val(data.payBill);
	        	$("#policy").val(data.policy);
	        	$("#message").val(data.message);
	        	
			    layer.open({
			    type: 1,
				title:'资源信息',
				area: ['500px','580px'],
				fix: false,
		    		shadeClose: true, //点击遮罩关闭层
				content: $('.channel-resource-content')				
				});		      				
	     }});
	}	
	
	function deleteChannelResource(resourceId){
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'], //按钮
			  icon:3
			}, function(){
				layer.closeAll();	
				$.ajax({
			        url:"deleteChannelResource.action",
			        data:{"channelRes.id":resourceId},
			        dataType:'json',
			        cache:false,
			        success:function(data){
						KISSY.use("gprs/gprs-post",function(S){
							GPRS.Post.getChannelResource(); 	
						});	 	        			
			     }});	
		});
	}
		
	$(".submit-btn").click(function(){
		var params = [];
		params.push("channelRes.id=");
		params.push($("#resourceId").val());
		params.push("&");
		params.push("channelRes.merchant=");
		params.push($("#merchant").val());
		params.push("&");			
		params.push("channelRes.channelId=");
		params.push($("#submit_channel").val());
		params.push("&");
		params.push("channelRes.district=");
		params.push($("#location").val());		
		params.push("&");
		params.push("channelRes.locationType=");
		params.push($("#locationType").val());		
		params.push("&");				
		params.push("channelRes.standard=");
		params.push($("#standard").val());		
		params.push("&");
		params.push("channelRes.inDiscount=");
		params.push($("#inDiscount").val());	
		params.push("&");
		params.push("channelRes.status=");
		params.push($("#status").val());			
		params.push("&");
		params.push("channelRes.payBill=");
		params.push($("#payBill").val());	
		params.push("&");
		params.push("channelRes.policy=");
		params.push($("#policy").val());	
		params.push("&");
		params.push("channelRes.message=");
		params.push($("#message").val());			
							
		$.ajax({
	        url:"publishChannelResource.action",
	        data:params.join(""),
	        dataType:'json',
	        cache:false,
	        success:function(data){
	        	layer.closeAll();
	        	
				KISSY.use("gprs/gprs-post",function(S){
					GPRS.Post.getChannelResource(); 	
				});	  	        				
	     }});	    	
	});
	
	function clickCancelBtn(){
		layer.closeAll();
	}	 
	 KISSY.use("gprs/gprs-post,gprs/gprs-terrority",function(S){
		GPRS.Terrority.initTerrority1();
	});
</script>
