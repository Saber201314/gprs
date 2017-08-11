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
			url : '/agent/chargeOrderList.action',
			type : "post",
			data : $('form').serialize(),
			dataType : 'json',
			cache : false,
			success : function(data) {
				layer.close(index);  
				fillchargeOrder(data);
				base.initpage(data,isinitpage,function(isinit){
					isinitpage = isinit;
					initChargeOrderList();
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
	function fillchargeOrder(data){
		if(data && data.list.length>0){
			var html = [];
			for(var i = 0;i<data.list.length;i++){
				html.push("<tr>");
        		//编号
        		html.push('<td>'+data.list[i].id+'</td>');
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
    			
    			//扣费金额
    			html.push('<td>'+data.list[i].payMoney+'</td>');
    			
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
    			
    			//回调内容
    			html.push('<td>'+data.list[i].reportContent+'</td>');
    			
				html.push('</tr>')
			}
			$(".layui-table tbody").append(html.join("")); 
			
			form.render('checkbox');
		}
	}
	
	exports('chargeOrderList'); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});