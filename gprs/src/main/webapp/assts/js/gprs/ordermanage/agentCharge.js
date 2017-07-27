layui.define([ 'layer', 'form', 'laydate', 'element', 'laypage','base' ], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var base = layui.base;
	
	$(function(){
		base.initagent();
		base.inittime();
		
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage=false;
		$('#pageNo').val(1);
		top.layer.msg("1");
		//initChargeOrderList();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	
	
	exports('agentCharge');
})