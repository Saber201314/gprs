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
	 */
	form.on('submit(btn-submit)', function(data) {
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
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
	
	exports('channelTemplateCode');

})