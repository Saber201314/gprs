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
		form.on('checkbox(province)', function(data) {
			checkAllProvince();
			form.render('checkbox');
		});
	})
	function checkAllProvince(){
		var hasNoChecked = false;
		var child = $('#province input[type="checkbox"]');
		child.each(function(index, item) {
			if(!item.checked){
				hasNoChecked = true;
			}
		});
		if(hasNoChecked){
			var allcheck = $('#allcheck')[0];
			allcheck.checked = false;
		}else{
			var allcheck = $('#allcheck')[0];
			allcheck.checked = true;
		}
	}
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(package-submit)', function(data) {
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