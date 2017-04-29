layui.define(['jquery','form','laydate','laypage','base'],function(exports){
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;
	
	$(function(){
		base.inittime('YYYY-MM-DD');
		base.initchannel();
		
	})
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
	
	exports('channellog');
	
})