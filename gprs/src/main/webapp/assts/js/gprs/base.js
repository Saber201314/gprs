/*
 * 通用的方法
 * 
 */
layui.define(['jquery', 'form','laypage','laydate'], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var laypage = layui.laypage;
	
	
	
	$(function(){
		/*
		 * 全选checkbox
		 */
		form.on('checkbox(allChoose)', function(data) {
			var child = $(data.elem).parents('table').find(
					'tbody input[type="checkbox"]');
			child.each(function(index, item) {
				item.checked = data.elem.checked;
			});
			form.render('checkbox');
		});
		
	})
	
	
	
	
	
	
	
	
	var obj = {
			
		inittime : function(pattern){
			/*
			 * 初始化开始结束时间
			 */
			var start = {
				elem : document.getElementById('start'),
				max : '2099-06-16 23:59:59',
				format : pattern,
				istime : true,
				istoday : false,
				choose : function(datas) {
					end.min = datas; //开始日选好后，重置结束日的最小日期
					end.start = datas //将结束日的初始值设定为开始日
				}
			};
			var end = {
				elem : document.getElementById('end'),
				max : '2099-06-16 23:59:59',
				format : pattern,
				istime : true,
				istoday : false,
				choose : function(datas) {
					start.max = datas; //结束日选好后，重置开始日的最大日期
				}
			};
			$('#start').click(function() {
				laydate(start);
			})
			$('#end').click(function() {
				laydate(end);
			})
		},
		/*
		 * 初始化分页
		 */
		initpage : function (data,isinitpage ,callback) {
			total = data.total;
			pages = data.pages;
			current = data.pageno;
			$('#page-total').html(
				'共有<strong>' + total + '</strong> 条记录, 当前第<strong>' + current + '</strong> 页， 共 <strong>' + pages + '</strong> 页');
			if (!isinitpage) {
				isinitpage = true;
				//分页
				laypage({
					cont: 'pate',
					pages: pages, //总页数
					groups: 5, //连续显示分页数
					jump: function(obj, first) {
						if (!first) {
							top.layer.msg('第 ' + obj.curr + ' 页');
							$('#pageNo').val(obj.curr);
							callback.call(this,isinitpage);
						}



					}
				});
			}
		},

		/*
		 * 初始化代理商
		 */
		initagent: function() {

			$.ajax({
				url: "/admin/query/userListByLevel.action",
				type: "post",
				dataType: 'json',
				cache: false,
				success: function(data) {
					if (data && data.success) {
						var list = data.module;
						var options = [];
						$.each(list, function(i, item) {
							options.push('<option value="' + item.username + '">' + item.name + '</option>');
						})
						$('#agent').append(options);
						form.render('select');
					} else {
						top.window.location = "/login.jsp";
					}
				},
				error: function() {

				}
			});
		},
		/*
		 * 初始化通道
		 */
		initchannel: function() {
			$.ajax({
				url: "/admin/query/getCurrentChannelList.action",
				type: 'post',
				dataType: 'json',
				cache: false,
				success: function(data) {
					if (data && data.length > 0) {
						var options = [];
						for (var i = 0; i < data.length; i++) {
							options.push("<option value=" + data[i].id + ">" + data[i].name + "</option>");
						}
						$("#submitChannel").append(options);
						form.render('select');
					}
				}
			});
		}

	}
	exports('base', obj);

})