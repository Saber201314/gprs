/**
 *  充值记录
 * 
 * 
 * 
 **/
layui.define([ 'layer', 'form', 'laydate', 'element', 'laypage' ], function(
		exports) {
	var form = layui.form();
	var laypage = layui.laypage;
	var laydate = layui.laydate;
	var elem = layui.element();
	var $ = layui.jquery;
	
	
	var index;//加载loading
	var total;
	var current;
	var pages;
	var isinitpage=false;//是否初始化page信息
	
	/*
	 * 初始化数据
	 */
	$(function(){
		/*
		 * 初始化开始结束时间
		 */
		var start = {
			elem : document.getElementById('start'),
			max : '2099-06-16 23:59:59',
			format : 'YYYY-MM-DD hh:mm:ss',
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : document.getElementById('end'),
			max : '2099-06-16 23:59:59',
			format : 'YYYY-MM-DD hh:mm:ss',
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		$('#start').click(function() {
			laydate(start);
		})
		$('#end').click(function() {
			laydate(end);
		})
		$('#start').val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
		$('#end').val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));
		
		
		/*
		 * 初始化代理商
		 */
		$.ajax({
		    url:"/admin/query/userListByLevel.action",
		    type:"post",
		    dataType:'json',
		    cache:false,
		    success:function(data){
		    	if(data && data.success){
		    		var list=data.module;
		    		var options=[];
		    		$.each(list,function(i,item){
		    			options.push('<option value="'+item.username+'">'+item.name+'</option>');
		    		})
		    		$('#agent').append(options);
		    		form.render('select');
		    	}else{
		    		top.window.location = "/login.jsp";
		    	}
		    },
		    error:function(){
		    }
		});
		/*
		 * 初始化通道
		 */
		$.ajax({
	        url:"/admin/query/getCurrentChannelList.action",
	        type : 'post',
	        dataType:'json',
	        cache:false,
	        success:function(data){
	        	if(data && data.length >0){
	        		var options=[];
	        		for(var i =0;i<data.length;i++){
	        			options.push("<option value="+data[i].id+">"+data[i].name+"</option>");
	        		}
	        		$("#submitChannel").append(options);
	        		form.render('select');
	        	}
	        }
		});
		initChargeOrderList();
		
		
	});

	
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage=false;
		$('#pageNo').val(1);
		initChargeOrderList();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	function initChargeOrderList(){
		index=layer.load();
		$(".layui-table tbody").html('');	
		$.ajax({
			url : '/admin/chargeOrderList.action',
			type : "post",
			data : $('form').serialize(),
			dataType : 'json',
			cache : false,
			success : function(data) {
				layer.close(index);  
				if(data && data.list.length>0){
					var html = [];
					for(var i = 0;i<data.list.length;i++){
						html.push("<tr>");
		        		html.push('<td><input type="checkbox"  data-id="'+data.list[i].id+'" lay-skin="primary"/></td>');
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
	        			optionTime = optionTime.getFullYear()+"-"+
	        				Appendzero( (optionTime.getMonth()+1))+"-"+
	        				Appendzero(optionTime.getDate())+" "+
	        				Appendzero(optionTime.getHours())+":"+
	        				Appendzero(optionTime.getMinutes())+":"+
	        				Appendzero(optionTime.getSeconds());
	        			html.push('<td>'+optionTime+'</td>');
	        			var reportTime = data.list[i].reportTime;
	        			if(!reportTime || reportTime == ""){
	        				reportTime = "";
	        			}else{
	        				reportTime=new Date(reportTime);
	        				reportTime=reportTime.getFullYear()+"-"+
	        					Appendzero( (reportTime.getMonth()+1))+"-"+
	        					Appendzero(reportTime.getDate())+" "+
	        					Appendzero(reportTime.getHours())+":"+
	        					Appendzero(reportTime.getMinutes())+":"+
	        					Appendzero(reportTime.getSeconds());

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
	            					status = "<span class='lable-charging'>充值中</span>";
	            				}else if(chargeStatus == 1){
	            					status = "<span class='lable-success'>充值成功</span>";
	            				}else{
	                				var error = data.list[i].error;
	                				if(typeof(error) == "undefined" || error == "null" || error == null || error == ""){
	                					status = "<span class='lable-fail'>充值失败 </span>";
	                				}else{
	                    				status = "<p class='hide-option' style='white-space: nowrap;overflow:hidden;' title='"+error+"'></p><span onclick='showtip(this)' class='hide-option lable-fail' title='"+error+"'>充值失败</span>";
	                				}            					
	            				}
	            			}else{  
	            				var error = data.list[i].error;
	            				if(typeof(error) == "undefined" || error == "null" || error == null || error == ""){
	            					status = "<span class='lable-nosubmit'>未提交 </span>";
	            				}else{
	                				status = "<p class='hide-option' style='white-space: nowrap;overflow:hidden;' title='"+error+"'></p><span onclick='showtip(this)' class='hide-option lable-nosubmit' title='"+error+"'>未提交</span>";
	            				}

	            			}       				       				
	        			}else{
	        				status = "缓存中...";
	        			}
	        			html.push('<td style="padding : 5px 0px;">'+status+'</td>');
	        			
	        			var channelName = data.list[i].channelName;
            			if(!channelName){
            				channelName = "";
            			}          			
            			html.push('<td><span style="font-weight:bold;">'+channelName+'</span></td>'); 
            			
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
                				html.push('<td><i class="layui-icon" style="font-size: 25px; color:#5FB878;">&#xe616;</i></td>');
                			}else{
                    			html.push('<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></td>');        				
                			}       				
            			}else{
            				html.push('<td></td>');
            			}
            			//利润
            			var profit = data.list[i].profit==undefined ? "" : data.list[i].profit;
            			if(data.list[i].discountMoney){
                			if(profit>0){
                				profit = "<span class='profit' >￥" + profit + "</span>";
                			}else{
                				profit = "<span class='unprofit' >￥" + profit + "</span>";
                			}         				
            			}else{
            				html.push('<span></span>');      				
            			}
            			html.push('<td>'+profit+'</td>'); 
            			
            			var btnhtml=[];
            			
            			btnhtml.push('<button style="margin-top:2px; " class="layui-btn layui-btn-mini">详情</button>');
            			btnhtml.push('<button style="margin-top:2px; " class="layui-btn layui-btn-mini">推送回调</button>');
						html.push('<td>' +btnhtml.join("")+'</td>');
						html.push('</tr>')
					}
					$(".layui-table tbody").append(html.join("")); 
					
					$(".hide-option").each(function(){
	    				$(this).tooltip({
	    					position: {
	    				        my: "center bottom-20",
	    				        at: "center top",
	    				        using: function( position, feedback ) {
	    				          $( this ).css( position );
	    				          $( "<div>" )
	    				            .addClass( "arrow" )
	    				            .addClass( feedback.vertical )
	    				            .addClass( feedback.horizontal )
	    				            .appendTo( this );
	    				        }
	    				      }
	    				});        			   			
	        		});
					form.render('checkbox');
				}
				initPage(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	console.log(XMLHttpRequest.status);
		    	console.log(XMLHttpRequest.readyState);
		    	console.log(textStatus);
		    	console.log(errorThrown);
		    	layer.close(index);  
				top.layer.msg("连接服务器失败");
			}
		})
	}

	/*
	 * 初始化分页信息
	 * 
	 */
	function initPage(data){
	    total = data.allRecord;
	    pages = data.allPage;
	    current = data.pageNo;
	    $('#page-total').html(
				'共有<strong>' + total + '</strong> 条记录, 当前第<strong>'
						+ current + '</strong> 页， 共 <strong>' + pages
						+ '</strong> 页');
	    if(!isinitpage){
	    	isinitpage=true;
	    	//分页
	    	laypage({
	    		cont : 'pate',
	    		pages : pages, //总页数
	    		groups : 5, //连续显示分页数
	    		jump : function(obj, first) {
	    			if (!first) {
	    				top.layer.msg('第 ' + obj.curr + ' 页');
	    				$('#pageNo').val(obj.curr);
	    				initChargeOrderList();
	    			}
	    			
	    			

	    		}
	    	});
	    }
	}
	
	/*
	 * 时间补0
	 */
	function Appendzero(obj) {  
	    if(obj<10) return "0" +""+ obj;  
	    else return obj;  
	}
	
	/*
	 * 全选checkbox
	 */
	form.on('checkbox(allChoose)', function(data) {
		var child = $(data.elem).parents('table').find(
				'tbody input[type="checkbox"]');
		child.each(function(index, item) {
			item.checked = data.elem.checked;
		});
		form.render('checkbox');
	});
	/*
	 * 获取所有选中checkbox
	 */
	var ids = [];
	$('.getallcheck').click(function() {
		var checklist = $('tbody tr td input[type="checkbox"]');
		ids = [];
		checklist.each(function(index, item) {
			if (item.checked) {
				var oid = new Object();
				oid.id = $(this).data("id");
				ids.push(oid);
			}

		});
		top.layer.msg(JSON.stringify(ids));
	})
	

	exports('charge-order-admin'); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});