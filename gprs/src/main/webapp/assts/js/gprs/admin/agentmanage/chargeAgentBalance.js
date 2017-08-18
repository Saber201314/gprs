layui.define(['base' ], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var base = layui.base;
	var laydate =  layui.laydate;
	
	$(function(){
		
	})
	
	
	/*
	 * 监听select选择
	 */
	form.on('select(*)', function(data){
		changeCompatible(data.value)
	}); 
	
	
	
	
	/*
	 * 拦截表单提交
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		console.log(data.field)
		data.field.memo = $('#memo').html();
		$.ajax({
			url : '/admin/chargeAgentBalance.action',
			type : 'post',
			data : data.field,
			dataType : 'json',
			success : function(data){
				if( data && data.success){
					
					var html=[];
					html.push('<div>充值金额：'+data.money+'</div>');
					top.layer.alert(html.join(""),{icon: 1}, function(index){
						top.layer.close(index);
						  window.location.href="/view/admin/agentmanage/agentList.jsp";
						}); 
				}else{
					top.layer.msg("保存失败");
				}
			},
			error : function(){
				top.layer.msg("连接服务器失败！");
			}
		})
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	
	exports('chargeAgentBalance');
})