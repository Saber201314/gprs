/*
 * 通用的方法
 * 
 */
layui.define(['jquery','layer', 'form','laypage','laydate','element','tree'], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var laypage = layui.laypage;
	var layer = layui.layer;
	
	
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
		//JS  日期格式化
		Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
		
		
	})
	var obj = {
		/*
		 * 初始化省份
		 */
		initProvince : function(){
			var province = [];
			province.push('北京');
			province.push('天津');
			province.push('河北');
			province.push('山西');
			province.push('辽宁');
			province.push('吉林');
			province.push('上海');
			province.push('江苏');
			province.push('浙江');
			province.push('安徽');
			province.push('福建');
			province.push('江西');
			province.push('山东'); 
			province.push('河南');
			province.push('湖北');
			province.push('湖南');
			province.push('广东');
			province.push('广西');
			province.push('海南'); 
			province.push('重庆');
			province.push('四川');
			province.push('贵州');
			province.push('云南');
			province.push('西藏');
			province.push('陕西');
			province.push('甘肃');
			province.push('宁夏');
			province.push('青海');
			province.push('新疆');
			province.push('内蒙古');
			province.push('黑龙江');
			
			return province;
		},
		
		/*
		 * 初始化时间控件
		 */
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
				url: "/admin/query/agentList.action",
				type: "get",
				dataType : 'json',
				cache: false,
				success: function(data) {
					if (data && data.success) {
						var options = [];
						$.each(data.list, function(i, item) {
							options.push('<option value="' + item.username + '">' + item.name + '</option>');
						})
						$('#agent').append(options);
						form.render('select');
					}else{
						top.layer.msg(data.msg);
					}
				},
				error: function() {
					top.layer.msg('连接服务器失败');
					//top.window.location = "/login.jsp";
				}
			});
		},
		/*
		 * 初始化通道
		 */
		initchannel: function() {
			$.ajax({
				url: "/admin/query/getCurrentChannelList.action",
				type: 'get',
				dataType : 'json',
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
				},
				error: function() {
					top.layer.msg('连接服务器失败');
					//top.window.location = "/login.jsp";
				}
			});
		}

	}
	exports('base', obj);

})