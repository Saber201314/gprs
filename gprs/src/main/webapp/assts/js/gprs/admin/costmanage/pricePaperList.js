layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		initPricePaper();
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage = false;
		$('#pageNo').val(1);
		initPricePaper();
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	
	function initPricePaper() {
		index = top.layer.load();
		$(".layui-table tbody").html('');
		$.ajax({
			url : '/admin/getPricePaperList.action',
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
						html.push('<td>' + item.id + '</td>');
						html.push('<td>' + item.name + '</td>');
						html.push('<td>' + item.alias + '</td>');
						if(item.optionTime == undefined || item.optionTime == null){
							html.push('<td></td>');
						}else{
							html.push('<td>' + new Date(item.optionTime).Format('yyyy-MM-dd hh:mm:ss') + '</td>');
						}
						html.push('<td>'+item.memo+'</td>');
						html.push('<td> <a href="/admin/editPricePaper.action?id='+item.id+'">  <button class="layui-btn layui-btn-mini">编辑</button> </a></td>');
						html.push('<td><button data-id="'+item.id+'" class="layui-btn layui-btn-mini showPircePaperInfo" >详情</button></td>');
						html.push('</tr>');
					});
					
					$(".layui-table tbody").append(html.join(''));
					base.initpage(data, isinitpage, function(isinit) {
						isinitpage = isinit;
						initPricePaper();
					})
					form.render('checkbox');
				}

				$('.showPircePaperInfo').click(function(){
					var id=$(this).data('id');
					showPircePaperInfo(id);
				})
			},
			error : function() {
				top.layer.msg('连接服务器失败');
				top.layer.close(index);
			}
		})
	}
	
	function showPircePaperInfo(id){    
		$.ajax({
			url:"/admin/showPircePaperInfo.action",
			type:'get',
	 		data: 'id='+id,
	 		dataType:"json",
	 		success:function(data){
			    layer.open({
				    type: 1,
					title:'报价详细信息',
					area: ['700px','600px'],
					offset: '100px',
					skin:'layui-layer-molv',
					shadeClose: true,
					content: $('#single-page-content')			
			    });	 		  		 
			    initPricePaperDetail(data);
	 		},
	 		error:function(){
	 			top.layer.msg('服务器连接失败');
	 		}
		});	
	}	
		
	function initPricePaperDetail(data){
		$("#single-page-content tbody").empty();
		if(data && data.length>0){
	     	var html = [];
	     	for(var i =0;i<data.length;i++){
	     		html.push("<tr>");		     		
	     		html.push("<td>"+data[i].name+"</td>");	
	     		var indiscount = data[i].inDiscount == undefined ? "" : data[i].inDiscount;
	     		html.push('<td>'+indiscount +'</td>');
	     		html.push("<td>"+data[i].outDiscount+"</td>");
	     		if(data[i].payBill == 1){
	     			html.push("<td>带票</td>");
	     		}else{
	     			html.push("<td>不带票</td>");
	     		}
	     		var channelName = data[i].channelName == undefined ? "" : data[i].channelName;
	     		html.push('<td>'+channelName+'</td>');		
	     		html.push("</tr>");
	     	}	
	     	$("#single-page-content tbody").append(html.join(""));	
		}		
	}

	exports('pricePaperList');

})