layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		initPackageList();
		form.on('checkbox(allChoose)', function(data) {
			var child = $('tbody input[type="checkbox"]');
			child.each(function(index, item) {
				item.checked = data.elem.checked;
			});
			form.render('checkbox');
		});
	})
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
			cache : false,
			success : function(data) {
				var html = [];
				top.layer.close(index);
				if (data && data.list.length > 0) {
					$.each(data.list, function(index, item) {
						html.push('<tr>');
						html.push('<td><input type="checkbox"  data-id="'+item.id+'" lay-skin="primary"/></td>');
						html.push('<td>' + item.id + '</td>');
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
						var rangeType=item.rangeType;
						if(rangeType == 0){
							html.push('<td>全国流量</td>');
						}else if(rangeType == 1){
							html.push('<td>省内流量</td>');
						}
						html.push('<td style="max-width:50px;">'+item.locations+'</td>');
						var newDate = new Date();
						newDate.setTime(item.optionTime);
						html.push('<td>' + newDate.toLocaleString() + '</td>');
						html.push('<td><a href="/admin/editPackage.action?id='+item.id+'"><button class="layui-btn layui-btn-mini">编辑</button></a></td>');
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
	/*
	 * 获取所有选中checkbox
	 */
	
	$('.getallcheck').click(function() {
		var checklist = $('tbody tr td input[type="checkbox"]');
		var ids = [];
		checklist.each(function(index, item) {
			if (item.checked) {
				var id = $(this).data("id");
				ids.push(id);
			}
		});
		if( ids.length > 0){
			top.layer.confirm('确定删除选择数据吗？',{
				btn: ['确定','取消']
			},function(){
				$.ajax({
					url : '/admin/delPackage.action',
					type : 'post',
					data : {'ids':ids},
					dataType : 'json',
					cache : false,
					success : function(data) {
						if ( data && data.success){
							top.layer.msg(data.msg);
							initPackageList();
						}else{
							top.layer.msg(data.msg);
						}
					},
					error : function() {
						top.layer.msg('连接服务器失败');
						top.layer.close(index);
					}
				})
			})
		}else{
			top.layer.msg('请选择流量包');
		}
		
	})

	exports('packageList');

})