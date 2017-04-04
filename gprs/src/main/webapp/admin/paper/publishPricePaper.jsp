<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<s:set name="menu_open" value="6"></s:set>
<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/resourse/assets/layer/extend/layer.ext.js" type="text/javascript"></script>
<style>
.msg-wrapper{width:100%;}
.clearfix{margin-top:20px;}
.Manager_style input{width:300px;}
li{padding-top:10px;}
label{width:100px;}
.table-tbody-div input{margin-left:-5px;}
.table-tbody-div{margin-top:0px !important;}
.search_package{height:25px !important;}
.seach_condition li{float:left;}
.seach_condition li label{width:50px;}
.seach_condition li input{width:300px;}
.ks-tabs-bar{margin-bottom:5px;}
.radio, .checkbox{margin-bottom: 0px;margin-top: 0px;padding-left:0px;}
.btn1{padding-top:2px;margin-left:10px;cursor:pointer;}
#package_name{width:150px;}
th,td{padding: 3px !important;}
</style>

<jsp:include page="singlePricePaper.jsp"></jsp:include>

<div id="screen" class="page-content">
	<div class="bar" style="padding-bottom:3px;">
		<span style="float:right;color:red;font-size:14px;">注意：配置报价的顺序是：首先设置折扣->然后配置是否带票->最后才选择该报价是否支持这个流量包，否则设置可能不会生效！</span>
	</div>
	<form action="doPublishPricePaper.action" method="post" id="submit-form">
	<input type="hidden" name="paper.id" value="<s:property value='paper.id' />" />
	<input type="hidden" name="paper.routable" value="<s:property value='paper.routable' />" />
	<input type="hidden" name="paper.items" value="<s:property value='paper.items' />" id="paperItems" />
	  <div class="form-elem Manager_style">
	  		<span class="title_name">添加报价单</span>
			  <ul class="sendMsg clearfix">
					<li>
						<label class="label_name" > 名称：</label>
						<input type="text" name="paper.name" value="<s:property value='paper.name' />" id="paperId" required required-msg="用户名不能为空！" />
					 </li>
					 <li>
						<label class="label_name"> 展示名称：</label>
						<input type="text" name="paper.alias" value="<s:property value='paper.alias' />" id="alias" required required-msg="展示名称不能为空！" />
					 </li>
					 <li>
					 	<label class="label_name" style="float:left;margin-right:12px;"> 支持流量包：</label>
					 	<div id="package-tabs" class="ks-tabs ks-tabs-top">
			                <div class="ks-tabs-bar">
			                    <div class="ks-tabs-tab ks-button ks-tabs-tab-selected">全国流量</div>
			                    <div class="ks-tabs-tab ks-button">省内流量</div>
			                </div>
			                <div class="ks-tabs-body">			                
			                    <div class="ks-tabs-panel ks-tabs-panel-selected ">
									<div class="seach_condition">
	      								<ul>
					                     	<li><label class="label_name"> 名称：</label><input type="text" id="package_name"/>
					                     		<a class="btn1" id="searchPakcages">查询</a>
					                     		<a class="btn1" id="selectAll">选择支持</a>
					                     		<a class="btn1" id="noSelectAll">取消支持</a>
					                     		<a class="btn1" id="selectAll1">选择带票</a>
					                     		<a class="btn1" id="noSelectAll1">取消带票</a>					                     		
					                     		<a class="btn1" id="settingDiscount">设置折扣</a>
					                     		<a class="btn1" href="javascript:void(0);" onclick="showSinglePaperInfo(<s:property value='paper.id' />)">详情</a>
					                     	</li>	
				                     	</ul>				                     	
			                     	</div>
	                     			                    
			                     	<table id="nation_package" cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
			                     	   <thead>
			                     		<tr>
		                     				<th width="350">流量包名称</th>
		                     				<th width="70">是否支持</th>
		                     				<th width="70">是否带票</th>
		                     				<th>折扣</th>
		                     			</tr>
		                     			</thead>				                     		
			                     		<s:iterator value="pagePackageList.list" var="package">
			                     			<tr>
			                     				<td><s:property value="#package.name" /></td>
			                     				<td><div class="checkbox"><label><input class="tab-checkbox ace is_support" onclick="clickCheckBox(this);" type="checkbox" data-id="<s:property value="#package.id" />" /><span class="lbl"></span></label></div></td>
			                     				<td><div class="checkbox"><label><input class="tab-checkbox ace is_bill" type="checkbox" data-id-1="<s:property value="#package.id" />" checked/><span class="lbl"></span></label></div></td>
			                     				<td><input class="tab-text discount" type="text" />折</td>
			                     			</tr>			              
			                     		</s:iterator>			                     		
			                     	</table>
			                     	<div align="left" style="margin-top:10px;"><jsp:include page="../layout/paginatorNew2.jsp"></jsp:include></div>
			                    </div>
			                    <div class="ks-tabs-panel table-tbody-div">
			                       <%-- <table cellpadding="0" cellspacing="0"  class="table table-striped table-bordered table-hover">
			                     		<s:iterator value="packageList" var="package">
			                     			<s:if test="#package.locationType==2">
			                     			<tr>
			                     				<td width="40%"><s:property value="#package.alias" /></td>
			                     				<td width="30%"><div class="checkbox"><label><input class="tab-checkbox ace" type="checkbox" data-id="<s:property value="#package.id" />" /><span class="lbl">支持</span></label></div></td>
			                     				<td><input class="tab-text" type="text"  />折</td>
			                     			</tr>
			                     			</s:if>			              
			                     		</s:iterator>
			                     	</table> --%> 
			                    </div>
			                </div>
			            </div>
					 </li>
					 <li class="clear">
						<label class="label_name"> 备注：</label>
						<input type="text" name="paper.memo" value="<s:property value='paper.memo' />" />
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
 	function showSinglePaperInfo(id){  
 	 $.ajax({url:"showSinglePaperInfo.action",
	 		 data:{"id":id},
	 		 dataType:"json",
	 		 success:function(data){
		    layer.open({
			    type: 1,
				title:'报价详细信息',
				area: ['600px','600px'],
				skin:'layui-layer-lan',
				shadeClose: true,
				content: $('#single-page-content')			
		    });	 		  		 
	 		 initPaperDetail(data);
	 }});	    
	}	
	
	function initPaperDetail(data){
		$("#paper_table tbody tr:not(:first)").empty();
		if(data && data.length>0){
	     	var html = [];
	     	for(var i =0;i<data.length;i++){
	     		html.push("<tr>");	
	     		html.push("<td>"+data[i].name+"</td>");	
	     		html.push("<td>"+data[i].preDiscount+"</td>");
	     		html.push("<td>"+data[i].afterDiscount+"</td>");
	     		var bill = data[i].bill;
	     		if(bill == 1){
	     			bill = "带票";
	     		}else{
	     			bill = "不带票";
	     		}	
	     		html.push("<td>"+bill+"</td>"); 	     		
	     		
	     		html.push("<td>"+data[i].level+"</td>");	     		
	     		html.push("<td>"+data[i].channel+"</td>");
	     		html.push("</tr>");
	     	}	
	     	$("#paper_table tbody tr:eq(0)").after(html.join(""));	
		}		
	}	
 
 	function searchPackage(pageNo){
		var paperItems = $("#paperItems").val();
		var packageName = $("#package_name").val();
		var id = $("input[name='paper.id']").val();	
		$.ajax({
 	        url:"getPackageByName.action",
 	        data:{"packageName":packageName,"id":id,"paperItems":paperItems,"pageNo":pageNo},
 	        dataType:'json',
 	        cache:false,
 	        success:function(data){    
 	        	$("#nation_package tbody").empty();	  	        	      		        	
				if (!data) {
					return;
				}if(data && data.length>0) {
					var html = [];
					for(var i = 0;i<data.length;i++){
						html.push('<tr>');
						html.push('<td width="200">'+data[i].name+'</td>');
						var id = data[i].id;
						var nSel = data[i].nSel;
						var supportChecked = "";
						if(nSel == 1){
							supportChecked = "checked=\"checked\"";
						} 
						var discount = data[i].discount?data[i].discount:'';	
						var bill = data[i].bill;
						var billChecked = "";
						//如果选择了带票
						if((nSel != 1 && bill == 0) || bill == 1){
							billChecked = "checked=\"checked\"";
						}					  								
						html.push('<td width="70"><div class="checkbox"><label><input class="tab-checkbox ace is_support" onclick="clickCheckBox(this);" type="checkbox" data-id="'+id+'" '+supportChecked+'/><span class="lbl"></span></label></div></td>');
						html.push('<td width="70"><div class="checkbox"><label><input class="tab-checkbox ace is_bill" type="checkbox" data-id-1="'+id+'" '+billChecked+'/><span class="lbl"></span></label></div></td>');
						html.push('<td><input class="tab-text discount" type="text" width="50" value="'+discount+'"/>折</td>');
						html.push('</tr>');								
					}
					$("#nation_package tbody").append(html.join(""));			        			       		
					initPageInfo(data[0]);	
				}else{
					$("#total-record").text(0);
					$("#page_info").empty();
				}
 	        }
 	    });  	
 	 }
 	
	 $("#searchPakcages").click(function(){
	 	searchPackage(1);
	 }); 

	  $("#clearPakcages").click(function(){
	  	$("#package_name").val("");
	 	searchPackage(1);
	 });
	 
	 function goPage(pageNo){
	   searchPackage(pageNo);
 	 }	 	
 	
	 function initPageInfo(data){
	    var pageNo = data.pageNO;
	    var allPage = data.AllPage;
 	    
	    $("#total-record").text(data.AllRecord);
		$("#page_info").empty();
			    
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
  	
	Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
  	};
 
    function clickCheckBox(obj) {
 	  	var id = $(obj).attr("data-id"); 	  	
		var tdDom = $(obj).parentsUntil("tr");
		var bill = 0;
		//是否带票
		if(tdDom.next().find("input").is(":checked")){
			bill = 1;
		}
		var discount = $.trim(tdDom.next().next().find(".discount").val());

		var packages = null,channelPacks = [];
	 	if($(obj).is(":checked")){
			discount = parseFloat(discount);			
			if (isNaN(discount)){
				layer.msg("折扣还没有设置哟！");
				$(obj).attr("checked",false);		
				return;
			}	
			packages = id + ":" + discount + ":" + bill;
						
			var channelPack = $("#paperItems").val();
			if(channelPack && channelPack != ""){
				channelPacks = channelPack.split(",");
				for(var i = 0;i<channelPacks.length;i++){
					var pack = channelPacks[i];
					var len = pack.indexOf(":");
					var tempId = pack.substring(0,len);					
					if(id == tempId){
						channelPacks.remove(pack);						
					}	
				}
				channelPacks.push(packages);							
			}else channelPacks.push(packages);
					
	 	}else{
			var channelPack = $("#paperItems").val();
			if(channelPack && channelPack != ""){
				channelPacks = channelPack.split(",");
				for(var i=0;i<channelPacks.length;i++){
					var pack = channelPacks[i];
					var len = pack.indexOf(":");
					var tempId = pack.substring(0,len);					
					if(id == tempId){
						channelPacks.remove(pack);
					}	
				}
		
			}				 		
	 	}
	 	packages = channelPacks.join(",");	
	 	$("#paperItems").val(packages); 	 			 	       			          			
    }
    
    $(".tab-text").change(function(){
    	var val = $(this).val();  
    	if(!isNaN(val)){
    		if(val>10){
    			layer.msg("必须是小于10的数字！");
    			$(this).val(10);
    		}
    	}else{
    		$(this).val(10);     	
    		layer.msg("必须要是数字！"); 		
    	}
    });
    
    	//选择全部支持
  	$("#selectAll").click(function(){
  			var isDisc = true;
  			$(".tab-text").each(function(){
  				var discount = $.trim($(this).val());
  				if(discount == null || discount == ""){
  					isDisc = false;
  				}
  			});
  			if(isDisc){
  				$(".is_support").each(function(){	
  					if(!$(this).is(":checked"))
  					$(this).click();		
  				}); 			
  			}else{
				 layer.msg('折扣还没有设置哟！'); 			
  			}
  		});
  	
  	  //选择全部带票	
  	  $("#selectAll1").click(function(){
 		    $(".is_bill").each(function(){	
 				if(!$(this).is(":checked"))
 				$(this).click();		
 	   }); 
 	 });			
    
      //取消全部支持
      $("#noSelectAll").click(function(){
  		$(".is_support").each(function(){	
 			if($(this).is(":checked"))
 			$(this).click();		
 		});      		
      });
      
      //取消全部带票
      $("#noSelectAll1").click(function(){
  		$(".is_bill").each(function(){	
 			if($(this).is(":checked"))
 			$(this).click();		
 		});      		
      });      
      
      //设置折扣
      $("#settingDiscount").click(function(){
			layer.prompt({
			  title: '请输入折扣',
			  formType: 0 //prompt风格，支持0-2
				}, function(discount){
					discount = parseFloat(discount);			
					if (isNaN(discount)){
						return;
				    } 
					$(".tab-text").each(function(){
						$(this).val(discount);
					});	
					layer.closeAll();			    								    
		});
	});      	
 
	 KISSY.use("gprs/gprs-paper",function(S){
		GPRS.Paper.init();
	});
</script>
