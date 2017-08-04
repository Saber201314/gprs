layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		
		
		
		
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		var templateId=$('input[name="templateId"]').val();
		$.ajax({
			url: "/admin/addTemplateCode.action",
			type: "post",
			data : $('form').serialize(),
			dataType : 'json',
			cache: false,
			success: function(data) {
				if(data && data.success){
					top.layer.msg(data.msg);
					window.location.href='/admin/query/channelTemplateCodeList.action?templateId='+templateId;
				}else{
					top.layer.msg(data.msg);
				}
			},
			error: function() {
				top.layer.msg('连接服务器失败');
				//top.window.location = "/login.jsp";
			}
		});
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	
	exports('channelTemplateCode');

})