KISSY.add(function(S,Node,Dom,Event,IO,Tabs,Auth,AuthMsgs){
        var $ = S.all;
        var index = 0;
        _Order = {
        	packageTab:function(){
        		new Tabs({
                    srcNode: '#package-tabs'
                }).render();
        	},
        	findOrder:function(partPath){       		
        		/*function stringToDate(str){
        		    var tempStrs = str.split(" ");
        		    var dateStrs = tempStrs[0].split("-");
        		    var year = parseInt(dateStrs[0], 10);
        		    var month = parseInt(dateStrs[1], 10) - 1;
        		    var day = parseInt(dateStrs[2], 10);
        		    var timeStrs = tempStrs[1].split(":");
        		    var hour = parseInt(timeStrs [0], 10);
        		    var minute = parseInt(timeStrs[1], 10);
        		    var second = parseInt(timeStrs[2], 10);
        		    var date = new Date(year, month, day, hour, minute, second);
        		    return date;
        		}*/
        		
        		Event.on(".btn_search","click",function(evt){
        			var $ = jQuery.noConflict();
        			$("#order_table tbody tr:gt(0)").empty();
        			
        			index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
        			
    	    		var params=IO.serialize("#ydForm");
    				IO({
    	    	        url:"/"+partPath+"/chargeOrderList.action",
    	    	        data:params,
    	    	        dataType:'json',
    	    	        cache:false,
    	    	        success:function(data){
    	    	        	layer.close(index);  
    	    	        	if(data && data.length>0){
    	    	        		var html = [];
    	    	        		html.push("<tr>");
    	    	        		for(var i = 0;i<data.length;i++){
    	    	        			html.push('<td><input type="checkbox" class="check-item" name="queryChargeOrderDO.idList" value="'+data[i].id+'" /></td>');
    	    	        			html.push('<td>'+data[i].account+'</td>');
    	    	        			html.push('<td>'+data[i].mobile+'</td>');
    	    	        			var type = data[i].type;
    	    	        			if(type == 1){
    	    	        				type = "移动";
    	    	        			}else if(type == 2){
    	    	        				type = "联通";
    	    	        			}else if(type == 3){
    	    	        				type = "电信";
    	    	        			}
    	    	        			html.push('<td>'+data[i].location + ' &nbsp;' + type +'</td>');
    	    	        			var locationType = data[i].locationType;
    	    	        			if(locationType == 1){
    	    	        				locationType ="全国流量";
    	    	        			}else{
    	    	        				locationType ="省内流量";
    	    	        			}
    	    	        			
    	    	        			html.push('<td>'+locationType+'</td>');
    	    	        			html.push('<td>'+data[i].amount+'M</td>');
    	    	        			html.push('<td>'+data[i].money+'元</td>');
    	    	        			var discountMoney = data[i].discountMoney;
    	    	        			if(!discountMoney){discountMoney = "";}
    	    	        			html.push('<td>'+discountMoney+'</td>');
    	    	        			var optionTime = data[i].optionTime;   	    	        			
    	    	        			html.push('<td>'+optionTime+'</td>');
    	    	        			var reportDate = data[i].reportDate;
    	    	        			if(!reportDate){reportDate = "";}
    	    	        			html.push('<td>'+reportDate+'</td>');
    	    	        			var submitType = data[i].submitType;
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
    	    	        			var submitStatus = data[i].submitStatus;
    	    	        			var chargeStatus = data[i].chargeStatus;
    	    	        			var status = "";
    	    	        			if(submitStatus == 1){
    	    	        				if(chargeStatus == 0){
    	    	        					status = "充值中";
    	    	        				}else if(chargeStatus == 1){
    	    	        					status = "充值成功";
    	    	        				}else{
    	    	        					status = "充值失败 " + data[i].error;
    	    	        				}
    	    	        			}else{
    	    	        				status = "未提交 "+ data[i].error;
    	    	        			}
    	    	        			html.push('<td>'+status+'</td>');
    	    	        			var channelName = data[i].channelName;
    	    	        			if(!channelName){channelName = "";}
    	    	        			html.push('<td>'+channelName+'</td>');
    	    	        			var subHtml = [];
    	    	        			subHtml.push('<a class="badge badge-info simple-detail-triggle"');
    	    	        			subHtml.push('onclick="showSingleOrderInfo('+data[i].id+');"href="javascript:void(0);">详情</a>');
    	    	        			var backUrl = data[i].backUrl;
    	    	        			if(backUrl){
    	    	        				subHtml.push('<a class="badge badge-info simple-callback-triggle"');
    	    	        				subHtml.push('href="forceCallBack.action?id='+data[i].id+'">推送回调</a>');
    	    	        			}
    	    	        			if(submitStatus == 1){
    	    	        				if(chargeStatus == 0){
    	    	        					if(data[i].fail){
    	    	        						subHtml.push('<a class="badge badge-info simple-post-triggle"');
    	    	        						subHtml.push('href="failOrder.action?id='+data[i].id+'">退款</a>');
    	    	        					}
    	    	        				}
    	    	        			}
    	    	        			html.push('<td>' +subHtml.join("")+'</td>');
    	    	        			html.push("</tr>");   	    	        				    	        			
    	    	        		}  
    	    	        		$("#order_table tbody").append(html.join(""));  	    	        		
    	    	        	}	
    	    	        },
    	    	        error:function(){
    	    	        	layer.alert("服务器连接失败，请重试！");
    	    	    }});        			
        		});        		        		   		
        	}
	    };
		return GPRS.Order = _Order;
	},{
		requires : [
		            'node','dom','event','ajax','tabs','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
		]
	}
);