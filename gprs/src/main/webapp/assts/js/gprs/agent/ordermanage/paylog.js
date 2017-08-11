/**
 *  充值记录
 **/
layui.define(['layer', 'form', 'laydate', 'element', 'laypage','base' ], function(
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
	form.on('submit(excel-submit)', function(data) {
		var start = $('#start').val();
		var end = $('#end').val();
		var startDate = new Date(start);
		var endDate = new Date(end);
		if(startDate.getMonth() != endDate.getMonth()){
			top.layer.msg("不能导出超过一个月的记录！");
			return false;
		}
		index = top.layer.confirm('是否按当前条件导出数据?', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				data.form.submit();
				top.layer.close(index);
				return true;
			}, function(){
				submit = false;
			});
		return false
	})
	
	
	function initPayLogList(){
		index=top.layer.load(0, {shade: false});
		$(".layui-table tbody").html('');	
		
		$.ajax({
			url : '/agent/payLogList.action',
			type : "post",
			data : $('form').serialize(),
			dataType : 'json',
			cache : false,
			success : function(data) {
				top.layer.close(index);  
					if(data && data.list.length>0){
						var html = [];
						$(".layui-table tbody").append(html.join("")); 
						$.each(data.list,function(index,item){
							html.push('<tr>');
							html.push('<td>'+item.id+'</td>');
							
							
							if( item.type == 1){
								html.push('<td><span class="lable-chargeOrBuckle">冲扣值</span></td>');
							}else if(item.type == 2){
								html.push('<td><span class="lable-pay">充值扣款</span></td>');
							}else{
								html.push('<td><span class="lable-refund">失败退款</span></td>');
							}
							
							if(item.type == 1){
								html.push('<td><span class="lable-chargeOrBuckle">'+item.money+'元</span></td>');
							}else if(item.type == 2){
								html.push('<td><span class="lable-pay">'+item.money+'元</span></td>');
							}else if(item.type == 3){
								html.push('<td><span class="lable-refund">'+(item.money)+'元</span></td>');
							}
							html.push('<td>'+item.balance+'元</td>');
							html.push('<td>'+item.memo+'</td>');
							
							html.push('<td>'+new Date(item.optionTime).Format("yyyy-MM-dd hh:mm:ss")+'</td>');
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
		    	layer.close(index);  
				top.layer.msg("连接服务器失败");
			}
		})
	}
	
	
	exports('paylog'); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});