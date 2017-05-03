layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		base.initagent();
		initcallbackloglist();
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage = false;
		$('#pageNo').val(1);
		initcallbackloglist();
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	function initcallbackloglist() {
		index = top.layer.load();
		$(".layui-table tbody").html('');
		$.ajax({
			url : '/admin/query/callbackList.action',
			type : 'post',
			data : $('form').serialize(),
			dataType : 'json',
			cache : '',
			success : function(data) {
				var html = [];
				top.layer.close(index);
				if (data && data.list.length > 0) {
					$.each(data.list, function(index, item) {
						html.push('<tr>');
						html.push('<td>' + item.orderId + '</td>');
						html.push('<td>' + item.mobile + '</td>');
						html.push('<td>' + item.url + '</td>');
						html.push('<td style="overflow:hidden;text-overflow:ellipsis;max-width:800px;"  >' + item.request + '</td>');
						html.push('<td>' + item.response + '</td>');
						
						var newDate = new Date();
						newDate.setTime(item.optionTime);
						html.push('<td>' + newDate.toLocaleString() + '</td>');
						html.push('</tr>');
					});
					$(".layui-table tbody").append(html.join(''));
					base.initpage(data, isinitpage, function(isinit) {
						isinitpage = isinit;
						initcallbackloglist();
					})
				}

			},
			error : function() {
				top.layer.msg('连接服务器失败');
				top.layer.close(index);
			}
		})
	}

	exports('callbacklog');

})