/**
 *  充值记录
 * 
 * 
 * 
 **/
layui.define([ 'layer', 'form', 'laydate', 'element', 'laypage' ], function(
		exports) {
	var form = layui.form();
	var laypage = layui.laypage;
	var laydate = layui.laydate;
	var elem = layui.element();
	var $ = layui.jquery;
	/*
	 * 初始化开始结束时间
	 */
	var start = {
		elem : document.getElementById('start'),
		max : '2099-06-16 23:59:59',
		format : 'YYYY-MM-DD hh:mm:ss',
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
		format : 'YYYY-MM-DD hh:mm:ss',
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
	$('#start').val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
	$('#end').val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));
	
	/*
	 * 初始化options
	 */
	var options=[];
	/*
	 * 初始化数据
	 */
	$(function(){
		/*
		 * 初始化代理商
		 */
		$.ajax({
		    url:"/admin/query/userListByLevel.action",
		    type:"post",
		    dataType:'json',
		    cache:false,
		    success:function(data){
		    	if(data || data.success){
		    		var list=data.module;
		    		var options=[];
		    		$.each(list,function(i,item){
		    			options.push('<option value="'+item.username+'">'+item.name+'</option>');
		    		})
		    		$('#agent').append(options);
		    		
		    		form.render('select(agent)');
		    	}else{
		    		layer.msg(data.error);
		    	}
		    },
		    error:function(){
		    }
		});	
		/*
		 * 初始化通道
		 */
		$.ajax({
	        url:"/admin/query/getCurrentChannelList.action",
	        type : 'post',
	        dataType:'json',
	        cache:false,
	        success:function(data){
	        	if(data && data.length >0){
	        		var options=[];
	        		for(var i =0;i<data.length;i++){
	        			options.push("<option value="+data[i].id+">"+data[i].name+"</option>");
	        		}
	    			$("#submitChannel").append(options);
	    			form.render('select(submitChannel)');
	        	}
	        	
	        }
		});
	});

	
	/*
	 * 拦截表单提交
	 */
	form.on('submit(btn-submit)', function(data) {
		console.log(data.elem); //被执行事件的元素DOM对象，一般为button对象
		console.log(data.form); //被执行提交的form对象，一般在存在form标签时才会返回
		console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
		top.layer.msg(JSON.stringify(data.field), {
			time : 10000
		});
		/* $.ajax({
			url : '/gprs-new/success.jsp',
			type : "get",
			cache : false,
			success : function(data) {
				layer.msg("连接服务器成功");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	console.log(XMLHttpRequest.status);
		    	console.log(XMLHttpRequest.readyState);
		    	console.log(textStatus);
		    	console.log(errorThrown);
				layer.msg("连接服务器失败");
			}
		}) */
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
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
	//分页
	laypage({
		cont : 'pate',
		pages : 10, //总页数
		groups : 5, //连续显示分页数
		jump : function(obj, first) {
			if (!first) {
				layer.msg('第 ' + obj.curr + ' 页');
			}
			$('#page-total').html(
					'共有<strong>' + 8692 + '</strong> 条记录, 当前第<strong>'
							+ obj.curr + '</strong> 页， 共 <strong>' + obj.pages
							+ '</strong> 页');

		}
	});

	exports('charge-order-admin'); //注意，这里是模块输出的核心，模块名必须和use时的模块名一致
});