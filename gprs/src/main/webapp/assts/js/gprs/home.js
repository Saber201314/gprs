layui.define(['jquery'],function(exports){
	var $ = layui.jquery;
	
	$(".layui-table tbody").empty();
	
	$(function(){
		
		getChannelResource();
		loadResumeTotalDetail();
		
		
	})
	/**
	 * 获取通道资源
	 */
	function getChannelResource(){
		$.ajax({
		    url:"/admin/layout/getChannelResource.action",
		    type:"post",
		    dataType:'json',
		    cache:false,
		    success:function(data){
		    	if(data != null && data.length>0){
					var html = [];
					var n = 0;
			     	for(var i =0;i<data.length;i++){
			     		html.push("<tr>");	
			     		n++;
			     		html.push("<td>"+n+"</td>");	
			     		var merchant = data[i].merchant;
			     		if(merchant == 1){
			     			merchant = "移动";
			     		}else if(merchant == 2){
			     			merchant = "联通";
			     		}else if(merchant == 3){
			     			merchant = "电信";
			     		}else{
			     			merchant = "";
			     		}    				     		
			     		html.push("<td>"+merchant+"</td>");	
			     		html.push("<td>"+data[i].district+"</td>");
			     		var locationType = data[i].location_type;
			     		if(locationType == 1){
			     			locationType = "漫游";
			     		}else if(locationType == 2){
			     			locationType = "非漫游";
			     		}else{
			     			locationType = "";
			     		}
			     		html.push("<td>"+locationType+"</td>");
			     		html.push("<td style='color:#5FB878;'>"+data[i].standard+"</td>");
			     		html.push("<td>"+data[i].in_discount+"</td>");
			     		var status = data[i].status;
			     		if(status == 1){
			     			status = "<span class='lable-success'>正常</span>";
			     		}else{
			     			status = "<span class='label-fail'>维护</span>";
			     		}
			     		html.push("<td>"+status+"</td>");
			     		var payBill = data[i].pay_bill;
			     		if(payBill == 1){
			     			payBill = "带票";
			     		}else if(payBill == 2){
			     			payBill = "不带票";
			     		}else{
			     			payBill = "";
			     		}    				     		
			     		html.push("<td>"+payBill+"</td>");
			     		var policy = data[i].policy;
			     		if(policy == 1){
			     			policy = "不限价";
			     		}else{
			     			policy = "限价";
			     		}    				     		
			     		html.push("<td>"+policy+"</td>");
			     		html.push("<td>"+data[i].message+"</td>");
			     		html.push("<td>"+data[i].channelName+"</td>");
			     		html.push("<td width='100'>");
			     		html.push('<button class="layui-btn layui-btn-mini">修改</button>');
			     		html.push('&nbsp;<button class="layui-btn layui-btn-mini">删除</button>');
			     		html.push("</td>");
			     		html.push("</tr>");   
			     		
			     		
			     	}
			     	$(".channelResource-table").append(html.join("")); 	
					
				} 	
		    },
		    error:function(){
		    }
		});
	}
	function loadResumeTotalDetail(){
		$.ajax({
			url:"/admin/layout/home.action",
	        data:{},
	        dataType:'json',
	        cache:false,
	        success:function(data){
	        	if(data != null){
	        		if (data.islogin>0) {
	        			data = data.data;
	        			var html = [],graph_data=[];
    	        		for(var i=0;i<data.length-1;i++){
    	        			html.push("<tr>");
    	        			html.push("<td width='50'>"+(i+1)+"</td>");
	    	        		html.push("<td>"+data[i].account+"</td>");
	    	        		var name = data[i].name ? data[i].name:"";
	    	        		
	    	        		html.push("<td>"+name+"</td>");
    	        			html.push("<td>"+data[i].resumePrice+"</td>");
    	        			html.push("<td>"+data[i].remainPrice+"</td>");
    	        			html.push("</tr>");
    	        			
    	        			if(i != data.length-2){
    	        				graph_data.push(data[i]);	    	        				
    	        			}	    	        			
    	        		}
    	        			    	        		    	        		
    					$(".resumeTotalDetail-table tbody").append(html.join("")); 	
					}else{
						top.location.href="/login.jsp";
					}
	        			        		
	        	}	
	        },
	        error:function(){
	        	top.layer.alert("服务器连接失败，请重试！");
	        }
		});
	}
	
	
	exports('home');
	
})