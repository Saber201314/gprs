/**
 *  充值记录
 **/
layui.define([ 'layer', 'form', 'laydate', 'element', 'laypage','base' ], function(
		exports) {
	var form = layui.form();
	var laypage = layui.laypage;
	var laydate = layui.laydate;
	var elem = layui.element();
	var $ = layui.jquery;
	var base = layui.base;
	
	var index;//加载loading
	var total;
	var current;
	var pages;
	var isinitpage=false;//是否初始化page信息
	
	/*
	 * 初始化数据
	 */
	$(function(){
		
		$('#start').val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
		$('#end').val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));
		base.inittime('YYYY-MM-DD hh:mm:ss');
		base.initagent();
		base.initchannel();
		
		initPayLogList();
		
		
	});

	
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage=false;
		$('#pageNo').val(1);
		initPayLogList();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	function initPayLogList(){
		index=layer.load();
		$(".layui-table tbody").html('');	
		
		$.ajax({
			url : '/admin/query/payLogList.action',
			type : "post",
			data : $('form').serialize(),
			dataType : 'json',
			cache : false,
			success : function(data) {
				layer.close(index);  
					if(data && data.list.length>0){
						var html = [];
						$(".layui-table tbody").append(html.join("")); 
						$.each(data.list,function(index,item){
							html.push('<tr>');
							html.push('<td>'+item.account+'</td>');
							
							if( item.type == 1){
								html.push('<td>流量充值</td>');
							}else if(item.type == 2){
								html.push('<td>流量池费用</td>');
							}else{
								html.push('<td>未知</td>');
							}
							html.push('<td>'+item.orderId+'</td>');
							html.push('<td>'+item.discount+'</td>');
							if(item.status == 1){
								html.push('<td><span class="lable-success">-'+item.money+'元</span></td>');
							}else if(item.status == 0){
								html.push('<td><span class="lable-charging">-'+item.money+'元</span></td>');
							}else if(item.status == -1){
								html.push('<td><span class="lable-fail">+'+(0-item.money)+'元</span></td>');
							}else if(item.status == -2){
								html.push('<td><span class="lable-nosubmit">-'+item.money+'元</span></td>');
							}
							if(item.status == 1){
								html.push('<td><span class="lable-success">'+item.balance+'元</span></td>');
							}else if(item.status == 0){
								html.push('<td><span class="lable-charging">'+item.balance+'元</span></td>');
							}else if(item.status == -1){
								html.push('<td><span class="lable-fail">'+item.balance+'元</span></td>');
							}else if(item.status == -2){
								html.push('<td><span class="lable-nosubmit">'+item.balance+'元</span></td>');
							}
							
																				
							
							html.push('<td>'+item.profit+'元</td>');
							html.push('<td>'+item.agentOrderId+'</td>');
							html.push('<td>'+item.memo+'</td>');
							
							var newDate = new Date();
							newDate.setTime(item.optionTime);
							html.push('<td>'+newDate.toLocaleString()+'</td>');
							
							if(item.status == 1){
								html.push('<td><span class="lable-success">充值成功</span></td>');
							}else if(item.status == 0){
								html.push('<td><span class="lable-charging">充值中</span></td>');
							}else if(item.status == -1){
								html.push('<td><span class="lable-fail">已退款</span></td>');
							}else if(item.status == -2){
								html.push('<td><span class="lable-nosubmit">充值失败</span></td>');
							}
							html.push('</tr>');
						})
						$(".layui-table tbody").append(html.join("")); 
					}
				base.initpage(data,isinitpage,function(isinit){
					isinitpage = isinit;
					initPayLogList();
				})
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
	
	$(".generate_bill").click(function(){
		$("#startTime_balance").text("￥0.0");
	 	$("#endTime_balance").text("￥0.0");
	 	$("#remittance").text("￥0.0");
	 	$("#consume").text("￥0.0");
	 	$("#diff").text("￥0.0");
	 	$("#accounting_status").text("无");
	 	$("#refund").text("￥0.0");	
	 	
	 	$("#payBill").text("￥0.0");
	 	$("#unPayBill").text("￥0.0");
	 	$("#factPayBill").text("￥0.0");
	 	$("#factPrice").text("￥0.0");
	 	
		var account = $("[name='account']").val();
		if(account == null || account == "-1"){
			top.layer.msg("生成核算数据需要选择代理商");
			return;
		}
		var from = $("[name='from']").val();
		if(from == null){
			top.layer.msg("生成核算数据需要选择一个时间段");
			return;		
		}
		var to = $("[name='to']").val();
		if(to == null){
			top.layer.msg("生成核算数据需要选择一个时间段");
			return;		
		}		
		top.layer.confirm('确定生成账单吗？', {
		  btn: ['确定','取消'], //按钮
		  icon:3
		}, function(){	
		top.layer.closeAll();		
	    layer.open({
		    type: 1,
			title:'账单统计明细',
			area: ['500px','630px'],
			skin:'layui-layer-molv',
			offset: '100px',
			shadeClose: true,
			content: $('#pay-bill-content')			    			               			
		 });		
		 
		  var params = [];
		  params.push("account=");
		  params.push(account);
		  params.push("&");
		  params.push("from=");
		  params.push(from);
		  params.push("&");  
		  params.push("to=");
		  params.push(to);
		  
		  index = top.layer.load(); //0代表加载的风格，支持0-2
		  
		  $.ajax({url:"/admin/query/showPayBillInfo.action",
		 		 data:params.join(""),
		 		 dataType:"json",
		 		 success:function(data){
		 		 top.layer.close(index);		 		 
		 		 if(data != null){
		 		 	$("#compayName").text(data.name);
		 		 	$("#account1").text(data.account);
		 		 			 			 		 	
		 		 	if(data.startBalance == null){
		 		 		data.startBalance = "0.0";
		 		 	}
		 		 	data.startDate = data.startDate.replace("T"," ");
		 		 	var startTime_balance = data.startDate + "     ￥" + data.startBalance;		 		 	
		 		 	$("#startTime_balance").text(startTime_balance);
		 		 	
		 		 	if(data.endBalance == null){
		 		 		data.endBalance = "0.0";
		 		 	}	
		 		 	data.endDate = data.endDate.replace("T"," ");	 		 		 		 	
		 		 	var endTime_balance = data.endDate + "         ￥" + data.endBalance;	 		 	
		 		 	$("#endTime_balance").text(endTime_balance);
		 		 	if(data.remittance == null){
		 		 		data.remittance = "0.0";
		 		 	}
		 		 	$("#remittance").text("￥"+ data.remittance);
		 		 	if(data.remittance == null){
		 		 		data.remittance = "0.0";
		 		 	}
		 		 	$("#consume").text("￥"+ data.consume);
		 		 	if(data.diff == null){
		 		 		data.diff = "0.0";
		 		 	}	 		 	
		 		 	$("#diff").text(data.diff);
		 		 	
		 		 	if(data.status == 1){
		 		 		data.status = "正常";
		 		 	}else{
		 		 		data.status = "异常";
		 		 	}
		 		 	$("#accounting_status").text(data.status);
		 		 	if(data.refund == null){
		 		 		data.refund = "0.0";
		 		 	}	 		 	
		 		 	$("#refund").text("￥"+ data.refund);
		 		 	
		 		 	if(data.payBill == null){
		 		 		data.payBill = "0.0";
		 		 	}	 		 	
		 		 	$("#payBill").text("￥"+ data.payBill);
		 		 	
		 		 	if(data.unPayBill == null){
		 		 		data.unPayBill = "0.0";
		 		 	}	 		 	
		 		 	$("#unPayBill").text("￥"+ data.unPayBill);	
		 		 	
		 		 	if(data.factPayBill == null){
		 		 		data.factPayBill = "0.0";
		 		 	}	 		 	payBill
		 		 	$("#factPayBill").text("￥"+ data.factPayBill);	
		 		 	
		 		 	if(data.factPayBill == null){
		 		 		data.factPayBill = "0.0";
		 		 	}	 		 	
		 		 	$("#factPrice").text("￥"+ parseFloat(data.factMoney).toFixed(2));			 		 		 		 		 		 	
		 		 }
		 }});			
		});	   		 
	});
	
	/*
	 * 时间补0
	 */
	function Appendzero(obj) {  
	    if(obj<10) return "0" +""+ obj;  
	    else return obj;  
	}
	
	
	exports('paylog'); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});