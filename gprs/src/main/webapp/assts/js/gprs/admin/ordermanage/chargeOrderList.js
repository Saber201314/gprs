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
		index=top.layer.load();
		$(".layui-table tbody").html('');	
		
		$.ajax({
			url : '/admin/chargeOrderList.action',
			type : "post",
			data : $('form').serialize(),
			dataType : 'json',
			cache : false,
			success : function(data) {
				top.layer.close(index);  
				fillchargeOrder(data);
				base.initpage(data,isinitpage,function(isinit){
					isinitpage = isinit;
					initChargeOrderList();
				})
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	top.layer.close(index);  
				top.layer.msg("连接服务器失败");
			}
		})
	}
	function fillchargeOrder(data){
		if(data && data.list.length>0){
			var html = [];
			for(var i = 0;i<data.list.length;i++){
				html.push("<tr>");
				//
        		html.push('<td><input type="checkbox"  data-id="'+data.list[i].id+'" lay-skin="primary"/></td>');
        		//编号
        		html.push('<td>'+data.list[i].id+'</td>');
        		//代理商
        		html.push('<td>'+data.list[i].account+'</td>');
        		//手机号
    			html.push('<td>'+data.list[i].mobile+'</td>');
    			//运营商 归属度
    			var type = data.list[i].type;
    			if(type == 1){
    				type = "移动";
    			}else if(type == 2){
    				type = "联通";
    			}else if(type == 3){
    				type = "电信";
    			}
    			html.push('<td>'+data.list[i].location + ' &nbsp;' + type +'</td>');
    			//流量类型
    			var rangeType = data.list[i].rangeType;
    			if(rangeType == 0){
    				rangeType ="全国流量";
    			}else{
    				rangeType ="省内流量";
    			}
    			html.push('<td>'+rangeType+'</td>');
    			//流量值
    			html.push('<td>'+data.list[i].amount+'M</td>');
    			//基础价格
    			var money = data.list[i].money;
    			if(money == 0){
    				money = "";
    			}else{
    				money = data.list[i].money + "元";
    			}
    			html.push('<td>'+money+'</td>');
    			
    			//状态
    			var status = data.list[i].chargeStatus;
    			if(status == 1){
    				html.push('<td><span class="lable-unknown">未知</span></td>');
    			}else if(status == 2){
    				html.push('<td><span class="lable-submitsuccess">提交成功</span></td>');
    			}else if(status == 3){
    				html.push('<td><span class="lable-fail">提交失败</span></td>');
    			}else if(status == 4){
    				html.push('<td><span class="lable-chargesuccess">充值成功</span></td>');
    			}else if(status == 5){
    				html.push('<td><span class="lable-fail">充值失败</span></td>');
    			}else{
    				html.push('<td></td>');
    			}
    			//订单生成时间
    			if(data.list[i].optionTime == undefined || data.list[i].optionTime == null){
    				html.push('<td></td>');
    			}else{
    				var optionTime = new Date(data.list[i].optionTime);
    				html.push('<td>'+optionTime.Format("yyyy-MM-dd hh:mm:ss")+'</td>');
    			}
    			
    			//提交时间
    			if(data.list[i].submitTime == undefined || data.list[i].submitTime == null){
    				html.push('<td></td>');
    			}else{
    				var submitTime = new Date(data.list[i].submitTime);
    				html.push('<td>'+submitTime.Format("yyyy-MM-dd hh:mm:ss")+'</td>');
    			}
    			//提交返回内容
    			html.push('<td>'+data.list[i].submitContent+'</td>');
    			
    			//回调时间
    			if(data.list[i].reportTime == undefined || data.list[i].reportTime == null){
    				html.push('<td></td>');
    			}else{
    				var reportTime = new Date(data.list[i].reportTime);
    				html.push('<td>'+reportTime.Format("yyyy-MM-dd hh:mm:ss")+'</td>');
    			}
    			//回调内容
    			html.push('<td>'+data.list[i].reportContent+'</td>');
    			
    			//充值方式
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
    			
    			//提交通道
    			var channelName = data.list[i].channelName;
    			if(!channelName){
    				channelName = "";
    			}          			
    			html.push('<td><span style="font-weight:bold;">'+channelName+'</span></td>'); 
    			
    			//接入折扣
    			html.push('<td>' + data.list[i].inDiscount + '</td>');  
    			
    			//折后价
    			var discountMoney = data.list[i].discountMoney;
    			if(!discountMoney || discountMoney == null){
    				discountMoney = "";
    			}else{
        			discountMoney = discountMoney + "元";          				
    			} 			
    			html.push('<td>'+discountMoney+'</td>');
    			
    			//放出折扣
    			html.push('<td>' + data.list[i].outDiscount + '</td>');
    			
    			//是否带票
    			if(data.list[i].payBill == 1){
    				html.push('<td><i class="layui-icon" style="font-size: 25px; color:#5FB878;">&#xe616;</i></td>');
    			}else{
        			html.push('<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></td>');        				
    			}
    			//扣费金额
    			html.push('<td>'+data.list[i].payMoney+'</td>');
    			//利润
    			var profit = data.list[i].profit==undefined ? "" : data.list[i].profit;
    			if(profit>0){
    				profit = "<span class='profit' >￥" + profit + "</span>";
    			}else{
    				profit = "<span class='unprofit' >￥" + profit + "</span>";
    			}  
    			html.push('<td>'+profit+'</td>');
    			
    			//操作按钮
    			var btnhtml=[];
    			btnhtml.push('<button style="margin-top:2px; " class="layui-btn layui-btn-mini">详情</button>');
    			btnhtml.push('<button style="margin-top:2px; " class="layui-btn layui-btn-mini">推送回调</button>');
				html.push('<td>' +btnhtml.join("")+'</td>');
				html.push('</tr>')
			}
			$(".layui-table tbody").append(html.join("")); 
			
			form.render('checkbox');
		}
	}
	
	
	
	
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
	exports('chargeOrderList'); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});