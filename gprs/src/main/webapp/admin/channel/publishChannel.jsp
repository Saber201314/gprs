<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<jsp:include page="singleChannel.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/resourse/assets/layer/extend/layer.ext.js" type="text/javascript"></script>

<style>
.msg-wrapper{width:100%;}
.clearfix{margin-top:20px;}
.Manager_style input{width:300px;}
li{padding-top:10px;}
label{width:100px;}
th{padding:6px !important;}
.search_package{height:25px !important;}
.seach_condition li{float:left;}
.seach_condition li label{width:50px;}
.seach_condition li input{width:150px;}
.ks-tabs-bar{margin-bottom:5px;}
select{width:300px;}
.radio, .checkbox{margin-bottom: 0px;margin-top: 0px;padding-left:0px;}
.btn1{padding:5px;margin-left:15px;cursor:pointer;}
th,td{padding: 3px !important;}
</style>

<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<br/>
	<form action="doPublishChannel.action" method="post" id="submit-form">
	<input type="hidden" name="channel.id" value="<s:property value='channel.id' />" />
	<input type="hidden" name="channel.packages" value="<s:property value='channel.packages' />" id="channelPackages" />
	  <div class="form-elem Manager_style">
	  		<span class="title_name">添加通道</span>
			  <ul class="sendMsg clearfix">
					<li>
						<label class="label_name"> 名称：</label>
						<input type="text" name="channel.name" value="<s:property value='channel.name' />" id="channelId" required required-msg="通道名称不能为空！" />
					 </li>
					 <li>
						<label class="label_name"> 展示名称：</label>
						<input type="text" name="channel.alias" value="<s:property value='channel.alias' />" id="alias" required required-msg="展示名称不能为空！" />
					 </li>
					 <li>
						<label class="label_name" style="margin-right:10px;"> 通道模板：</label>
						<s:select name="channel.template" list="templateList" listKey="identity" listValue="name" theme="simple"></s:select>
					 </li>
					 <li>
						<label class="label_name"> 月门限值：</label>
						<input type="text" name="channel.monthLimit" value="<s:property value='channel.monthLimit' />" id="monthLimit" number="true" number-msg="请填写数字！" />M(0表示不限制) 
					 </li>
					 <li>
					 	<label class="label_name" style="float:left;margin-right:12px;"> 支持流量包：</label>
					 	<div id="package-tabs" class="ks-tabs ks-tabs-top">
			                <div class="ks-tabs-bar">
			                    <div class="ks-tabs-tab ks-button ks-tabs-tab-selected">全国流量</div>
			                    <div class="ks-tabs-tab ks-button">省内流量</div>
			                </div>
			                <div class="ks-tabs-body">
			                    <div class="ks-tabs-panel ks-tabs-panel-selected">
			                     	<div class="seach_condition">
	      								<ul>	
					                     	<li><label class="label_name"> 名称：</label>
					                     	    <input type="text" id="package_name"/>
					                     		<a class="btn1" id="searchPakcages">查询</a>
					                     		<a class="btn1" id="selectAll">全选择</a>
					                     		<a class="btn1" id="noSelectAll">全取消</a>
					                     		<a class="btn1" id="settingDiscount">设置折扣</a>	
					                     		<a class="btn1" id="settingLevel">设置优先级</a>	
					                     		<a class="btn1" href="javascript:void(0)" onclick="showChannelInfo(<s:property value='channel.id' />)">详情</a>			                     		
					                     	</li>	
				                     	</ul>				                     	
			                     	</div>			                     		                     			                     	
			                     	 <table id="nation_package" cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
			                     	   <thead>
			                     		<tr>
		                     				<th width="260">名称</th>
		                     				<th>是否支持</th>
		                     				<th>折扣</th>
		                     				<th>优先级</th>
		                     			</tr>			                     			
		                     			</thead>				                     					                     		
			                     		<s:iterator value="packageList" var="package"> 
			                     			<tr>
			                     				<td><s:property value="#package.name" /></td>
			                     				<td><div class="checkbox"><label><input class="tab-checkbox ace" onclick="clickCheckBox(this);" type="checkbox" data-id="<s:property value="#package.id" />" /><span class="lbl"></span></label></div></td>
			                     				<td><input class="tab-text discount" type="text" value="10"/></td>
			                     				<td><input class="tab-text level" type="text" /></td>
			                     			</tr>		              
			                     		</s:iterator> 				                     		
				                     </table>				                     	
									<div align="left" style="margin-top:10px;"><jsp:include page="../layout/paginatorNew.jsp"></jsp:include></div>			                     			                     	
			                    </div>
			                    <div class="ks-tabs-panel">			                    	
			                       <%--  <table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
			                     		<tr>
		                     				<th width="200">名称</th>
		                     				<th width="70">是否支持</th>
		                     				<th>折扣</th>
		                     				<th>优先级</th>
		                     			</tr>
		                     		</table>
		                     		<div class="table-tbody-div">
			                     		<table id="province_package" cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">	
				                     		<s:iterator value="packageList" var="package"> 
				                     			<s:if test="#package.locationType==2">
				                     			<tr>
				                     				<td width="90"><s:property value="#package.name" /></td>
				                     				<td width="70"><div class="checkbox"><label><input class="tab-checkbox ace" type="checkbox" data-id="<s:property value="#package.id" />" /><span class="lbl"></span></label></div></td>
				                     				<td><input class="tab-text discount" type="text" /></td>
				                     				<td><input class="tab-text level" type="text" /></td>
				                     			</tr>
				                     			</s:if>			              
				                     		 </s:iterator>
				                     	</table>
			                     	</div> --%>
			                    </div>
			                </div>
			            </div>
			            <!-- <div style="float:left;margin-top:300px;">>></div>
			            <div style="float:left;border:1px solid #ccc;width:300px;height:435px;margin-top:80px;">			            
							<table cellpadding="0" cellspacing="0" id="selected_package" class="table table-striped table-bordered table-hover">
	                     		<tr>
                     				<th width="90">名称</th>
                     				<th>折扣</th>
                     				<th>优先级</th>
                     			</tr>
	                     	</table>
			            </div> -->
					 </li>
					 <li class="clear">
					 	<div>
							<label class="label_name"> 备注：</label>
							<input type="text" style="margin-left:0px;" name="channel.memo" value="<s:property value='channel.memo' />" />
						</div>
					 </li>
			  </ul>
		</div>	
		<div class="sub_btn btn_operating">
			   <input class="submit-btn btn btn-info" type="button" value="提交">
			   <input type="button" class="btn cancel-btn" value="取消">
	    </div>
	</form>
</div>
 
 <script>
 	function showChannelInfo(id){    
 	 $.ajax({url:"showSingleChannelInfo.action",
	 		 data:{"id":id},
	 		 dataType:"json",
	 		 success:function(data){
		    layer.open({
			    type: 1,
				title:'通道详细信息',
				area: ['600px','600px'],
				skin:'layui-layer-lan',
				shadeClose: true,
				content: $('#single-page-content')			
		    });	 		  		 
	 		 initChannelDetail(data);
	 }});	    
	}	
	
	function initChannelDetail(data){
		$("#channel_table tbody tr:not(:first)").empty();
		if(data && data.length>0){
	     	var html = [];
	     	for(var i =0;i<data.length;i++){
	     		html.push("<tr>");		     		
	     		html.push("<td>"+data[i].name+"</td>");	
	     		html.push("<td>"+data[i].price+"</td>");
	     		html.push("<td>"+data[i].discount+"</td>");
	     		html.push("<td>"+data[i].actualPrice+"</td>");
	     		html.push("<td>"+data[i].level+"</td>");	     		
	     		html.push("<td>"+data[i].channelName+"</td>");		     		
	     		html.push("</tr>");
	     	}	
	     	$("#channel_table tbody tr:eq(0)").after(html.join(""));	
		}		
	}
 	
    $("#settingLevel").click(function(){
			layer.prompt({
			  title: '请输入优先级',
			  formType: 0 //prompt风格，支持0-2
				}, function(discount){
					discount = parseFloat(discount);			
					if (isNaN(discount)){
						return;
				    } 
					$(".level").each(function(){
						$(this).val(discount);
					});	
					layer.closeAll();			    								    
		});
	});
 
   $("#settingDiscount").click(function(){
			layer.prompt({
			  title: '请输入折扣',
			  formType: 0 //prompt风格，支持0-2
				}, function(discount){
					discount = parseFloat(discount);			
					if (isNaN(discount)){
						return;
				    } 
					$(".discount").each(function(){
						$(this).val(discount);
					});	
					layer.closeAll();			    								    
		});
	});
	
  	$("#selectAll").click(function(){
  			var isDisc = true;
  			var isLevel = true;
  			var isDiscNum = true;
  			var islevelNum = true;
  			$(".discount").each(function(){
  				var discount = $.trim($(this).val());
  				if(discount == null || discount == ""){
  					isDisc = false;
  				}else if(isNaN(discount)){
  					isDiscNum = false;	
  				}
  			});
  			
  			$(".level").each(function(){
  				var level = $.trim($(this).val());								
  				if(level == null || level == ""){
  					isLevel = false;
  				}else if(isNaN(level)){
  					islevelNum = false;
  				}
  			});
  			  			
  			if(!isDisc){
  				$(".tab-checkbox").each(function(){	
  					if($(this).is(":checked"))
  					$(this).click();		
  				}); 
  				layer.msg('折扣还没有设置哟！');
  				return; 			
  			}
  			
  			if(!isLevel){	
  				layer.msg('优先级还没有设置哟！'); 	
  				$(".tab-checkbox").each(function(){	
  					if($(this).is(":checked")){
    					$(this).click();					
  					}		
  				}); 
  				return;			
  			}
  			
  			if(!isDiscNum){
   				layer.msg('折扣必须是数字格式！'); 	
  				$(".tab-checkbox").each(function(){	
  					if($(this).is(":checked")){
    					$(this).click();					
  					}		
  				});
  				return;  			
  			}   			
  				
  			if(!islevelNum){
   				layer.msg('优先级必须是数字格式！'); 	
  				$(".tab-checkbox").each(function(){	
  					if($(this).is(":checked")){
    					$(this).click();					
  					}		
  				});
  				return;  			
  			} 
  			$(".tab-checkbox").each(function(){	
  				if(!$(this).is(":checked"))
  				$(this).click();
  			});			
  		});
    
      $("#noSelectAll").click(function(){
  		$(".tab-checkbox").each(function(){	
 			if($(this).is(":checked"))
 			$(this).click();		
 		});      		
      });	
      
      $(".level").change(function(){
      		var level = $.trim($(this).val());
      		if(isNaN(level)){
      			$(this).val(null);
      		}
      }); 

 	function searchPackage(pageNo){
		var packageCode = $("#channelPackages").val();
		var packageName = $("#package_name").val();
		var id = $("input[name='channel.id']").val();	
   		$.ajax({
	        url:"getDataByPackageName.action",
	        data:{"packageName":packageName,"id":id,"packageCode":packageCode,"pageNo":pageNo},
	        dataType:'json',
	        cache:false,
	        success:function(data){
	 	     	$("#nation_package tbody").empty();
				if (!data) {
					return;
				}if (data && data.length>0) {
					var html = [];
					for(var i = 0;i<data.length;i++){
						html.push('<tr>');
						html.push('<td>'+data[i].name+'</td>');
						var id = data[i].id;
						var nSel = data[i].nSel;
						var checked = "";
						if(nSel == 1){
							var checked = "checked=\"checked\"";
						}   								
						html.push('<td><div class="checkbox"><label><input class="tab-checkbox ace" onclick="clickCheckBox(this);" type="checkbox" data-id="'+id+'" '+checked+'/><span class="lbl"></span></label></div></td>');
						var discount = data[i].discount?data[i].discount:'10';
						html.push('<td><input class="tab-text discount" type="text" value="'+discount+'"/></td>');
						var level = data[i].level?data[i].level:'';
						html.push('<td><input class="tab-text level" type="text" value="'+level+'"/></td>');
						html.push('</tr>');								
					}
					$("#nation_package tbody").append(html.join(""));
					
		       		$("#total-record").text(data[0].allRecord);			        		
		       		$("#page_info").empty();
		       		initPageInfo(data[0]);
				
				}else{
					$("#total-record").text(0);
				}
 	   		}
 	});   			    			    		   
 }
 function initPageInfo(data){
    var pageNo = data.pageNo;
    var allPage = data.allPage;
	var html = [];
	if(pageNo!=1){
		html.push('<a href="#" onclick="return goPage(1)">首页</a>');        		      			
	}
	if(pageNo-1>0){
		html.push('<a href="#" onclick="return goPage('+(pageNo-1)+')">上一页</a>');        			
	}
	if(pageNo-2>0){
		html.push('<a href="#" onclick="return goPage('+(pageNo-2)+')">'+(pageNo-2)+'</a>');        			
	}
	if(pageNo-1>0){
		html.push('<a href="#" onclick="return goPage('+(pageNo-1)+')">'+(pageNo-1)+'</a>');        			
	}
	html.push('<a href="#" class="current_page" onclick="goPage('+pageNo+')">'+pageNo+'</a>');        		
	if(allPage>=pageNo+1){
		html.push('<a href="#" onclick="return goPage('+(pageNo+1)+')">'+(pageNo+1)+'</a>');        			
	}
	if(allPage>=pageNo+2){
		html.push('<a href="#" onclick="return goPage('+(pageNo+2)+')">'+(pageNo+2)+'</a>');        			
	}
	if(allPage>=pageNo+1){
		html.push('<a href="#" onclick="return goPage('+(pageNo+1)+')">下一页</a>');  
	}
	if(allPage!=pageNo){
		html.push('<a href="#" onclick="return goPage('+allPage+');">末页</a>');  
	}  
	$("#page_info").append(html.join("")); 
 }
 

 $("#searchPakcages").click(function(){
 	searchPackage(1);
 });
 
 function goPage(pageNo){
	searchPackage(pageNo);
 }
   Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
  };
 
   function clickCheckBox(obj) {	
 	  	var id = $(obj).attr("data-id");
		var tdDom = $(obj).parentsUntil("tr");
		var discount = $.trim(tdDom.next().find("input").val());
		var level = $.trim(tdDom.next().next().find("input").val());
		var name = tdDom.prev().text();
		var packages = null,channelPacks = [];
	 	if($(obj).is(":checked")){		
			packages = id + ":" + discount + ":" + level;
			if(discount == null || discount == ""){
				layer.msg('折扣还没有设置！');
				$(obj).attr("checked", false);
				return;
			}
			if(level == null || level == ""){
				layer.msg('优先级还没有设置！');
				$(obj).attr("checked", false);
				return;			
			}
			if(isNaN(discount)){
				layer.msg('折扣必须为数字格式！');
				$(obj).attr("checked", false);
				return;			
			}
			
			if(isNaN(level)){
				layer.msg('优先级必须为数字格式！');
				$(obj).attr("checked", false);
				return;			
			}			
				
			var channelPack = $("#channelPackages").val();
			if(channelPack && channelPack != ""){
				channelPacks = channelPack.split(",");
				for(var i = 0;i<channelPacks.length;i++){
					var pack = channelPacks[i];
					var len = pack.indexOf(":");
					var tempack = pack.substring(0,len);					
					if(id == tempack){
						channelPacks.remove(pack);						
					}	
				}
				channelPacks.push(packages);							
			}else channelPacks.push(packages);
					
	 	}else{
			var channelPack = $("#channelPackages").val();
			if(channelPack && channelPack != ""){
				channelPacks = channelPack.split(",");
				for(var i=0;i<channelPacks.length;i++){
					var pack = channelPacks[i];
					var len = pack.indexOf(":");
					var tempack = pack.substring(0,len);					
					if(id == tempack){
						channelPacks.remove(pack);
					}	
				}
		
			}				 		
	 	}
	 	packages = channelPacks.join(",");	
	 	$("#channelPackages").val(packages); 	
	 	       			          			
} 
       	
	KISSY.use("gprs/gprs-post,gprs/gprs-channel",function(S){
		GPRS.Channel.init();
	});
</script>
