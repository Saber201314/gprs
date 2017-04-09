var curPage = 1;
var index = 0;
$(function(){
	initChargeOrderData();	
});

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
	params.push($("#submitStatus").val());	
	return params.join("");
}
       
function initChargeOrderData(){
	$("#order_table tbody tr:not(:first):not(:last)").empty();	
	$("#page_info").empty();
	$("#pageTr").hide();
	
	index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
	var params = buildParams();
	$.ajax({
        url:"/agent/chargeOrderList.action",
        data:params,
        dataType:'json',
        cache:false,
        success:function(data){
        	layer.close(index);  
        	if(data && data.list.length>0){
        		var html = [];
        		html.push("<tr>");
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
        			optionTime = optionTime.replace("T"," ");  
        			
        			html.push('<td>'+optionTime+'</td>');
        			
        			var reportDate = data.list[i].reportDate;
        			if(!reportDate || reportDate == ""){reportDate = "";}else{
        				reportDate = reportDate.replace("T"," ");   

        			}
        			html.push('<td>'+reportDate+'</td>');
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
        			if(submitStatus == 1){
        				if(chargeStatus == 0){
        					status = "充值中";
        				}else if(chargeStatus == 1){
        					status = "充值成功";
        				}else{
            				var error = data.list[i].error;
            				if(typeof(error) == "undefined" || error == "null" || error == null){
            					status = "充值失败 ";
            				}else{
                				status = "充值失败  "+ error;
            				}  
        				}
        			}else{
        				if(data.list[i].cacheFlag == 1){
        					status = "充值中";
        				}else{
            				var error = data.list[i].error;
            				if(typeof(error) == "undefined" || error == "null" || error == null){
            					status = "未提交 ";
            				}else{
                				status = "未提交  "+ error;
            				}        					
        				}
        			}
        			
        			html.push('<td style="max-width:120px;"><p style="white-space: nowrap;overflow:hidden;text-overflow:ellipsis;" title="'+status+'">'+status+'</p></td>');

        			var subHtml = [];
        			subHtml.push('&nbsp;<a class="badge badge-info simple-detail-triggle"');
        			subHtml.push('onclick="showSingleOrderInfo('+data.list[i].id+');"href="javascript:void(0);">详情</a>');
        			var backUrl = data.list[i].backUrl;
        			if(backUrl){
        				subHtml.push('&nbsp;<a class="badge badge-info simple-callback-triggle"');
        				subHtml.push('href="javascript:void(0);"onclick="forceBackOrderStatus('+data.list[i].id+')">推送回调</a>');
        			}
        			html.push('<td>' +subHtml.join("")+'</td>');
        			html.push("</tr>");   	    	        				    	        			
        		}  
        		$("#order_table tbody tr:eq(0)").after(html.join("")); 
        		initPageInfo(data.page);
        	}	
        },
        error:function(){
        	layer.alert("服务器连接失败，请重试！");
        	location.href="/exit.action";        	
    }});   		
}

$(".btn_search").on("click",function(){
	curPage = 1;
	initChargeOrderData();		
});    

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
  	        	layer.alert("服务器连接失败，请重试！");
  	        	location.href="/exit.action";
  	        }
  	    });	    				
	}); 		
}
