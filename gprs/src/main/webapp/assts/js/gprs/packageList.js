layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		initPackageList();
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage = false;
		$('#pageNo').val(1);
		initPackageList();
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	function initPackageList() {
		index = top.layer.load();
		$(".layui-table tbody").html('');
		$.ajax({
			url : '/admin/packageList.action',
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
						html.push('<td><input type="checkbox"  data-id="'+item.id+'" lay-skin="primary"/></td>');
						html.push('<td>' + item.name + '</td>');
						html.push('<td>' + item.alias + '</td>');
						html.push('<td>' + item.amount + '</td>');
						html.push('<td>' + item.money + 'M</td>');
						var type=item.type;
						if(type == 1){
							html.push('<td>移动</td>');
						}else if(type == 2){
							html.push('<td>联通</td>');
						}else if(type == 3){
							html.push('<td>电信</td>');
						}
						var locationType=item.locationType;
						if(locationType == 1){
							html.push('<td>全国流量</td>');
						}else if(locationType == 2){
							html.push('<td>省内流量</td>');
						}
						html.push('<td>'+item.locations+'</td>');
						var newDate = new Date();
						newDate.setTime(item.optionTime);
						html.push('<td>' + newDate.toLocaleString() + '</td>');
						html.push('<td>编辑</td>');
						html.push('</tr>');
					});
					
					$(".layui-table tbody").append(html.join(''));
					base.initpage(data, isinitpage, function(isinit) {
						isinitpage = isinit;
						initPackageList();
					})
					form.render('checkbox');
				}

			},
			error : function() {
				top.layer.msg('连接服务器失败');
				top.layer.close(index);
			}
		})
	}

	exports('packageList');

})