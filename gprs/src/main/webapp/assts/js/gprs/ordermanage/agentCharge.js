layui.define([ 'layer', 'form', 'laydate', 'element', 'laypage','base' ], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var base = layui.base;
	
	
	var isinitpage=false;//是否初始化page信息
	
	$(function(){
		$('#start').val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
		$('#end').val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));
		base.initagent();
		base.inittime();
		initAgentCharge();
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage=false;
		$('#pageNo').val(1);
		initAgentCharge();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	function initAgentCharge(){
		$(".layui-table tbody").html('');
		$.ajax({
			url : '/admin/getAgentChargeList.action',
			type : 'post',
			data : $('form').serialize(),
			cache : false,
			dataType : 'json',
			success : function(data){
				if( data && data.list.length > 0){
					var html = [];
					$.each(data.list,function(index,item){
						html.push('<tr>');
						
						html.push('<td>'+item.id+'</td>');
						
						html.push('<td>'+item.account+'</td>');
						
						html.push('<td>'+item.money+'</td>');
						
//						<option value="1">公账收款</option>
//					    <option value="2">公支付宝</option>
//					    <option value="3">私账</option>
//					    <option value="4">转移</option>
//					    <option value="5">授信</option>
//					    <option value="6">未到账退款</option>
//					    <option value="7">测试加款</option>
//					    <option value="8">其他原因充扣款</option>
						var paytype ="";
						if(item.payType == 1){
							paytype = "公账收款";
						}else if(item.payType == 2){
							paytype = "公支付宝";
						}else if(item.payType == 3){
							paytype = "私账";
						}else if(item.payType == 4){
							paytype = "转移";
						}else if(item.payType == 5){
							paytype = "授信";
						}else if(item.payType == 6){
							paytype = "未到账退款";
						}else if(item.payType == 7){
							paytype = "测试加款";
						}else if(item.payType == 8){
							paytype = "其他原因充扣款";
						}
						html.push('<td>'+paytype+'</td>')
						var date = new Date(item.optionTime)
						html.push('<td>'+date.Format('yyyy-MM-dd hh:mm:ss')+'</td>');
						html.push('<td>'+item.memo+'</td>');
						html.push('<td>'+item.agent+'</td>');
						html.push('<td></td>');
						html.push('</tr>');
					})
					$(".layui-table tbody").append(html.join("")); 
					base.initpage(data,isinitpage,function(isinit){
						isinitpage = isinit;
						initAgentCharge();
					})
				}
			},
			error : function(){
				
			}
			
		})
	}
	
	
	exports('agentCharge');
})