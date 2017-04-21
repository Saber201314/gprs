var curPage = 1;
var index = 0;
$(function(){
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
	
	initChargeOrderData();
});

var options=[];
options.push('<option value="">请选择</option>');

$(function(){
	$.ajax({
	    url:"userListByLevel.action",
	    type:"post",
	    dataType:'json',
	    cache:false,
	    success:function(data){
			if (!data) {
				return;
			}
			if (data.success) {				
				var list=data.module;
				$.each(list,function(i,parent){
					insertAgent(parent,0);
					var userList=parent.userList;
					if(userList){
						insertList(userList, 1);
					}
				});
				$("#agent-level").append(options.join(""));
			    $("#agent-level").chosen({search_contains:true});
			}
			else{
				layer.alert(data.error);
			}
	    },
	    error:function(){
	    }
	});		
});

function insertList(agentList,level){
	$.each(agentList,function(i,parent){
		insertAgent(parent,level);
		var userList=parent.userList;
		if(userList){
			insertList(userList, level+1);
		}
	});
}
function insertAgent(agent,level){
	var str=[];
	for(var i=0;i<level;i++){
		//str.push("--");
	}
	str.push(agent.name);
	options.push('<option value="'+agent.username+'">'+str.join("")+'</option>');
}


function buildParams(){
	var params = [];
	params.push("pageNo=");
	params.push(curPage);
	params.push("&");	
	params.push("account=");
	params.push($("#agent-level").val());
	params.push("&");
	params.push("mobile=");
	var mobile = $.trim($("#mobile").val());
	params.push(mobile);
	params.push("&");
	params.push("location=");
	params.push($(".terrority-select").val());
	params.push("&");	
	params.push("from=");
	params.push($("#start").val());
	params.push("&");
	params.push("to=");
	params.push($("#end").val());
	params.push("&");	
	params.push("type=");
	params.push($("#type").val());
	params.push("&");	
	params.push("amount=");
	params.push($(".amount-select").val());
	params.push("&");		
	params.push("locationType=");
	params.push($(".locationType-select").val());
	params.push("&");
	params.push("submitStatus=");
	var a=$("#submitStatus");
	params.push(a.val());
	params.push("&");
	params.push("submitChannel=");
	params.push($("#submit_channel").val());
	
	var len = $("#cacheFlag").length;
	if(len>0){
		params.push("&");
		params.push("cacheFlag=");
		params.push($("#cacheFlag").val());		
	}
	return params.join("");
}
       
function initBtnOperationStatus(){
	$.ajax({
        url:"/admin/getCurrentOperationStatus.action",
        data:{},
        dataType:'json',
        cache:false,
        success:function(data){       	
        if($(".submit-channel").length>0){
            if(data.cache){
         	   $(".submit-channel").css({color:"red"});
            }
            if(data.fail){
         	   $(".fail-cache-order").css({color:"red"});        	   
            }              
        }else{
        	if(data.success){
        		$(".success-order").css({color:"red"});
        	}
            if(data.fail){
          	   $(".fail-order").css({color:"red"});        	   
            }  
            if(data.back){
           	   $(".callback-order").css({color:"red"});        	   
            }             
        }

     },
     error:function(){
     	layer.alert("服务器连接失败，请重试！");
 }});
}

function initChargeOrderData(){

	$("#order_table tbody tr:not(:first):not(:last)").empty();	
	$("#page_info").empty();
	$("#pageTr").hide();
	
	initBtnOperationStatus();
	
	index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
	var params = buildParams();
	var url = "/admin/chargeOrderList.action";
	if($("#cacheFlag").length>0){
		url = "chargeOrderCacheList.action";
	}

	$.ajax({
        url:url,
        data:params,
        type:"post",
        dataType:'json',
        cache:false,
        success:function(data){
        	layer.close(index);  
        	if(data && data.list.length>0){
        		var html = [];
        		html	.push("<tr>");
        		for(var i = 0;i<data.list.length;i++){
        			html.push('<td><input type="checkbox" class="check-item" name="queryChargeOrderDO.idList" value="'+data.list[i].id+'" /></td>');
        			html.push('<td>'+data.list[i].account+'</td>');
        			html.push('<td>'+data.list[i].mobile+'</td>');
        			var type = data.list[i].type;
        			if(type == 1){
        				type = "移动";
        			}else if(type == 2){
        				type = "联通";
        			}else if(type == 3){
        				type = "电信";
        			}
        			html.push('<td>'+data.list[i].location + ' &nbsp;' + type +'</td>');
        			var locationType = data.list[i].locationType;
        			if(locationType == 1){
        				locationType ="全国流量";
        			}else{
        				locationType ="省内流量";
        			}
        			
        			html.push('<td>'+locationType+'</td>');
        			html.push('<td>'+data.list[i].amount+'M</td>');
        			var money = data.list[i].money;
        			if(money == 0){
        				money = "";
        			}else{
        				money = data.list[i].money + "元";
        			}
        			html.push('<td>'+money+'</td>');
        			
        			     			
        			var discountMoney = data.list[i].discountMoney;
        			if(!discountMoney || discountMoney == null){
        				discountMoney = "";
        			}else{
            			discountMoney = discountMoney + "元";          				
        			} 			
        			html.push('<td>'+discountMoney+'</td>');
        			
        			var optionTime = data.list[i].optionTime; 
        			optionTime = new Date(optionTime);
        			optionTime = optionTime.getFullYear()+"-"+Appendzero( (optionTime.getMonth()+1))+"-"+Appendzero(optionTime.getDate())+" "+Appendzero(optionTime.getHours())+":"+Appendzero(optionTime.getMinutes())+":"+Appendzero(optionTime.getSeconds());
//        			optionTime = optionTime.replace("T"," "); 
        			
        			html.push('<td>'+optionTime+'</td>');
        			
        			var reportTime = data.list[i].reportTime;
        			if(!reportTime || reportTime == ""){
        				reportTime = "";
        			}else{
        				reportTime=new Date(reportTime);
        				reportTime=reportTime.getFullYear()+"-"+Appendzero( (reportTime.getMonth()+1))+"-"+Appendzero(reportTime.getDate())+" "+Appendzero(reportTime.getHours())+":"+Appendzero(reportTime.getMinutes())+":"+Appendzero(reportTime.getSeconds())
//        				reportDate = reportDate.replace("T"," ");   

        			}
        			html.push('<td>'+reportTime+'</td>');
        			var submitType = data.list[i].submitType;
        			if(submitType == 1){
        				submitType = "代理商直充";
        			}else if(submitType == 2){
        				submitType = "充值卡充值";
        			}else if(submitType == 3){
        				submitType = "支付宝充值";
        			}else if(submitType == 4){
        				submitType = "批量充值";
        			}else if(submitType == 5){
        				submitType = "接口充值";
        			}
        			html.push('<td>'+submitType+'</td>');
        			var submitStatus = data.list[i].submitStatus;
        			var chargeStatus = data.list[i].chargeStatus;
        			var status = "";
        			var cacheFlag = data.list[i].cacheFlag;
        			if(cacheFlag != 1){
            			if(submitStatus == 1){
            				if(chargeStatus == 0){
            					status = "<span class='label label-info'>充值中</span>";
            				}else if(chargeStatus == 1){
            					status = "<span class='label label-success'>充值成功</span>";
            				}else{
                				var error = data.list[i].error;
                				if(typeof(error) == "undefined" || error == "null" || error == null || error == ""){
                					status = "<span class='label label-danger'>充值失败 </span>";
                				}else{
                    				status = "<span class='label label-danger'><p id='hide-option' style='white-space: nowrap;overflow:hidden;' title='"+error+"'>充值失败</p></span>";
                				}            					
            				}
            			}else{  
            				var error = data.list[i].error;
            				if(typeof(error) == "undefined" || error == "null" || error == null || error == ""){
            					status = "<span class='label label-grey'>未提交 </span>";
            				}else{
                				status = "<span class='label label-grey'><p id='hide-option' style='white-space: nowrap;overflow:hidden;' title='"+error+"'>未提交</p></span>";
            				}

            			}       				       				
        			}else{
        				status = "缓存中...";
        			}
        			html.push('<td style="max-width:100px;">'+status+'</td>');
      			
        			if($("#adminRole").length>0){
        				if($("#adminRole").val()==1){
                			var channelName = data.list[i].channelName;
                			if(!channelName){channelName = "";}          			
                			html.push('<td><span style="font-weight:bold;">'+channelName+'</span></td>');        					
        				}      				
        			}
        			var discount = "",_discount = "";
        			if(data.list[i].discountMoney){
        				if(data.list[i].money){
        					discount = (data.list[i].discountMoney/data.list[i].money*10).toFixed(2);
        					if(data.list[i].profit){
        						_discount = ((data.list[i].discountMoney-data.list[i].profit)/data.list[i].money*10).toFixed(2);
        					}else{
        						_discount = discount;
        					}       					
        				}else{
        					discount = "10.0";
        				}       				
        			}

        			html.push('<td>' + _discount + '</td>');   
        			html.push('<td>' + discount + '</td>');

        			if(data.list[i].discountMoney){
            			if(data.list[i].payBill == 1){
            				html.push('<td><i class="icon-ok-sign" style="color:#8bad4c;"></i></td>');
            			}else{
                			html.push('<td><i class="icon-remove-sign" style="color:#d68273;"></i></td>');        				
            			}       				
        			}else{
        				html.push('<td></td>');
        			}
        			var profit = data.list[i].profit==undefined ? "" : data.list[i].profit;
        			
        			if(data.list[i].discountMoney){
            			if(profit>0){
            				profit = "<span style='color:#8bad4c;font-weight:bold;'>￥" + profit + "</span>";
            			}else{
            				profit = "<span style='color:#d68273;font-weight:bold;'>￥" + profit + "</span>";
            			}         				
        			}else{
        				html.push('<span></span>');      				
        			}
    			
        			html.push('<td>'+profit+'</td>');       		
        			var subHtml = [];
        			subHtml.push('&nbsp;<a class="badge badge-info simple-detail-triggle"');
        			subHtml.push('onclick="showSingleOrderInfo('+data.list[i].id+');"href="javascript:void(0);">详情</a>');
        			if(cacheFlag != 1){
            			var backUrl = data.list[i].backUrl;
            			if(backUrl){
            				subHtml.push('&nbsp;<a class="badge badge-info simple-callback-triggle"');
            				subHtml.push('href="javascript:void(0);"onclick="forceBackOrderStatus('+data.list[i].id+')">推送回调</a>');
            			}
            			/*if(submitStatus == 1){
            				if(chargeStatus == 0){
            					if(data.list[i].fail){
            						subHtml.push('&nbsp;<a class="badge badge-info simple-post-triggle"');
            						subHtml.push('href="failOrder.action?id='+data.list[i].id+'">退款</a>');
            					}
            				}
            			}  */      				
        				
        			}
        			html.push('<td>' +subHtml.join("")+'</td>');
        			html.push("</tr>");   	    	        				    	        			
        		}  
        		$("#order_table tbody tr:eq(0)").after(html.join("")); 
        		initPageInfo(data);
        		
        		$(".label #hide-option").each(function(){
    				$(this).tooltip({
    					hide: {
    						effect: "slideDown",
    						delay: 250
    					}
    				});        			   			
        		});       		
        	}	
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        	console.log(XMLHttpRequest.status);
        	console.log(XMLHttpRequest.readyState);
        	console.log(textStatus);
        	console.log(errorThrown);
        	layer.alert("服务器连接失败，请重试！");
//        	location.href="/index.jsp";        	
    }});   		
}

$(".btn_search").on("click",function(){
	curPage = 1;
	initChargeOrderData();		
});    
function Appendzero(obj) {  
    if(obj<10) return "0" +""+ obj;  
    else return obj;  
}

function initPageInfo(data){	
    var allRecord = data.allRecord;
    var allPage = data.allPage;
    var pageNo = data.pageNo;
    $("#totalRecords").text(allRecord);
    $("#curPage").text(pageNo);
    $("#allPage").text(allPage);

	var html = [];
	if(pageNo!=1){
		html.push('<a href="#" onclick="return goPage(1)">首页</a>');        		      			
	}
	if(pageNo-1>0){
		html.push('<a href="#" onclick="return goPage('+(pageNo-1)+')">上一页</a>');        			
	}
	if(pageNo-4>0){
		html.push('<a href="#" onclick="return goPage('+(pageNo-4)+')">'+(pageNo-4)+'</a>');        			
	}		
	if(pageNo-3>0){
		html.push('<a href="#" onclick="return goPage('+(pageNo-3)+')">'+(pageNo-3)+'</a>');        			
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
	if(allPage>=pageNo+3){
		html.push('<a href="#" onclick="return goPage('+(pageNo+3)+')">'+(pageNo+3)+'</a>');        			
	}
	if(allPage>=pageNo+4){
		html.push('<a href="#" onclick="return goPage('+(pageNo+4)+')">'+(pageNo+4)+'</a>');        			
	}	
	if(allPage>=pageNo+1){
		html.push('<a href="#" onclick="return goPage('+(pageNo+1)+')">下一页</a>');  
	}
	if(allPage!=pageNo){
		html.push('<a href="#" onclick="return goPage('+allPage+');">末页</a>');  
	}  
	$("#page_info").append(html.join(""));
	$("#pageTr").show();
 }

function goPage(pageNo){
	curPage = pageNo;
	initChargeOrderData();
}

$(function(){
	$(".export-excel-datas").click(function(){	
		window.location="exportExcelChargeOrderDatas.action?"+buildParams();		
	});	
});

function forceBackOrderStatus(id){
	layer.confirm('确定要回调吗？', {
		  btn: ['确定','取消'], //按钮
		  icon:3
		}, function(){
			$.ajax({
  	        url:"forceCallBack.action?id="+id,
  	        type:"get",
  	        dataType:'json',
  	        cache:false,
  	        success:function(data){
					if (!data) {
						return;
					}
					if (data.success) {
						layer.alert("操作成功！");
					}else{
						layer.alert(data.error);
					}
  	        },
  	        error:function(){
  	        	layer.close(index);
  	        	layer.alert("服务器连接失败，请重试！");
//  	        	location.href="/exit.action";
  	        }
  	    });	    				
	}); 		
}
