layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var province ;

	$(function() {
		form.on('checkbox(allChoose)', function(data) {
			var child = $('#province input[type="checkbox"]');
			child.each(function(index, item) {
				item.checked = data.elem.checked;
			});
			form.render('checkbox');
		});
		form.render('checkbox');
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		$.ajax({
			url: "/admin/addPackage.action",
			type: "post",
			data : $('form').serialize(),
			dataType : 'json',
			cache: false,
			success: function(data) {
				if(data && data.success){
					top.layer.msg(data.msg);
					window.location.href='/view/admin/package/packageList.jsp';
				}else{
					top.layer.msg(data.msg);
				}
			},
			error: function() {
				top.layer.msg('连接服务器失败');
				//top.window.location = "/login.jsp";
			}
		});
		return false;
	})


	exports('addPackage');

})