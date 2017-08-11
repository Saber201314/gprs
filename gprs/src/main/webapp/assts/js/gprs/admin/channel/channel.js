layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		initChannelList();
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage = false;
		$('#pageNo').val(1);
		initChannelList();
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	
	form.on('switch(status)', function(data){
		var id = $(data.elem).data("id");
		var status = "";
		if(data.elem.checked){
			status = "on"
		}else{
			status = "off"
		}
		layer.confirm('是否切换状态', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url : '/admin/changeChannelStatus.action?channelId='+id+'&statusFlag='+status,
					type : 'get',
					dataType : 'json',
					success : function(data){
						layer.closeAll();
						if(data && data.success){
							top.layer.msg("修改成功");
							initChannelList();
						}else{
							top.layer.msg("修改失败");
						}
					},
					error : function(){
						top.layer.msg("连接服务器失败");
					}
				})
			}, function(){
				data.elem.checked = !data.elem.checked;
				form.render("checkbox");
			});
	});
	function initChannelList() {
		index = top.layer.load();
		$(".layui-table tbody").html('');
		$.ajax({
			url : '/admin/query/channelList.action',
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
						//html.push('<td><input type="checkbox"  data-id="'+item.id+'" lay-skin="primary"/></td>');
						html.push('<td>' + item.id + '</td>');
						html.push('<td>' + item.name + '</td>');
						html.push('<td>' + item.alias + '</td>');
						html.push('<td>' + item.templateName + '</td>');
						html.push('<td>' + item.monthLimit + 'M</td>');
						var level=item.level;
						if(level){
							html.push('<td>' + item.level + '</td>');
						}else{
							html.push('<td></td>');
						}
						var newDate = new Date();
						newDate.setTime(item.optionTime);
						html.push('<td>' + newDate.toLocaleString() + '</td>');
						html.push('<td>'+item.memo+'</td>');
						var status = item.status;
						if(status == 0){
							html.push('<td><input type="checkbox" checked data-id="'+item.id+'" name="status" lay-skin="switch" lay-filter="status" lay-text="开启|暂停"></td>');
						}else if(status == -1){
							html.push('<td><input type="checkbox"  data-id="'+item.id+'" name="status" lay-skin="switch" lay-filter="status" lay-text="开启|暂停"></td>');
						}
						html.push('<td> <a href="/admin/editChannel.action?channelId='+item.id+'">  <button class="layui-btn layui-btn-mini">编辑</button> </a></td>');
						html.push('<td><button data-id="'+item.id+'" class="layui-btn layui-btn-mini showChannelInfo" >详情</button></td>');
						html.push('</tr>');
					});
					
					$(".layui-table tbody").append(html.join(''));
					base.initpage(data, isinitpage, function(isinit) {
						isinitpage = isinit;
						initChannelList();
					})
					form.render('checkbox');
				}

				$('.showChannelInfo').click(function(){
					var id=$(this).data('id');
					showChannelInfo(id);
				})
			},
			error : function() {
				top.layer.msg('连接服务器失败');
				top.layer.close(index);
			}
		})
	}
	
	function showChannelInfo(id){    
		$.ajax({
			url:"/admin/showSingleChannelInfo.action",
			type:'get',
	 		data: 'id='+id,
	 		dataType:"json",
	 		success:function(data){
			    layer.open({
				    type: 1,
					title:'通道详细信息',
					area: ['700px','600px'],
					offset: '100px',
					skin:'layui-layer-molv',
					shadeClose: true,
					content: $('#single-page-content')			
			    });	 		  		 
			    initChannelDetail(data);
	 		},
	 		error:function(){
	 			top.layer.msg('服务器连接失败');
	 		}
		});	
	}	
		
	function initChannelDetail(data){
		$("#single-page-content tbody").empty();
		if(data && data.length>0){
	     	var html = [];
	     	for(var i =0;i<data.length;i++){
	     		html.push("<tr>");		     		
	     		html.push("<td>"+data[i].name+"</td>");	
	     		html.push("<td>"+data[i].price+"</td>");
	     		html.push("<td>"+data[i].discount+"</td>");
	     		html.push("<td>"+data[i].actualPrice+"</td>");
	     		html.push("<td>"+data[i].level+"</td>");	     		
	     		html.push("<td>"+data[i].channelName+"</td>");		     		
	     		html.push("</tr>");
	     	}	
	     	$("#single-page-content tbody").append(html.join(""));	
		}		
	}

	exports('channel');

})