layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		initChannelTemplate();
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage = false;
		$('#pageNo').val(1);
		initChannelTemplate();
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	function initChannelTemplate() {
		index = top.layer.load();
		$(".layui-table tbody").html('');
		$.ajax({
			url : '/admin/query/channelTemplateList.action',
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
						html.push('<td>' + item.name + '</td>');
						html.push('<td>' + item.account + '</td>');
						html.push('<td>' + item.password + '</td>');
						var sign=item.sign;
						if(sign){
							html.push('<td style="max-width:200px;word-break:break-all;"><div>' + item.sign + '</div></td>');
						}else{
							html.push('<td></td>');
						}
						
						var newDate = new Date();
						newDate.setTime(item.option_time);
						html.push('<td>' + newDate.toLocaleString() + '</td>');
						html.push('<td width="150"><a style="color : #009688;" href="/admin/query/channelTemplateCodeList.action?templateId='+item.id+'">流量包编号</a></td>');
						html.push('<td width="50"><a style="color : #009688;" href="javascript:;">编辑</a></td>');
						html.push('</tr>');
					});
					
					$(".layui-table tbody").append(html.join(''));
					base.initpage(data, isinitpage, function(isinit) {
						isinitpage = isinit;
						initChannelTemplate();
					})
				}
			},
			error : function() {
				top.layer.msg('连接服务器失败');
				top.layer.close(index);
			}
		})
	}
	exports('channelTemplate');

})