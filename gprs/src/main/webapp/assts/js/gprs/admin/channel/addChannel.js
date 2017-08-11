layui.define([ 'base', ], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;
	
	
	$(function(){
		
		initPackageList();
		//查询流量包
		$('#searchPackage').click(function(){
			isinitpage = false;
			$('#pageNo').val("1");
			initPackageList();
		})
		//全选择
		$('.checkAll').click(function(){
			var errorFlag = false ;
			$('.level').each(function(index,item){
				if( item.value == "" ){
					errorFlag = true;
					return;
				}
			})
			if(!errorFlag){
				$('input[type="checkbox"]').each(function(index,item){
					item.checked = true ;
					var tr = $(item).parent().parent();
					var packId = $(item).data("id");
					var discount = tr.find('.discount').val();
					var level = tr.find('.level').val();
					addPackCode(packId,discount,level);
				})
				form.render('checkbox');
			}else{
				top.layer.msg("优先级还没有设置！！");
			}
		})
		//全取消
		$('.cancelAll').click(function(){
			$('input[type="checkbox"]').each(function(index,item){
				item.checked = false ;
				var tr = $(item).parent().parent();
				var packId = $(item).data("id");
				var discount = tr.find('.discount').val();
				var level = tr.find('.level').val();
				removePackCode(packId,discount,level);
			})
			form.render('checkbox');
		})
		//设置折扣
		$('.setAllDiscount').click(function(){
			top.layer.prompt({
			    title: '请输入折扣',
			    formType: 0 //prompt风格，支持0-2
			}, function(discount){			
				if (isNaN(discount)){
					top.layer.msg("优先级必须为数字！")
					return;
			    } 
				$('.discount').each(function(){
					$(this).val(discount);
				});	
				top.layer.closeAll();			    								    
			});
		})
		//设置优先级
		$('.setAllLevel').click(function(){
			top.layer.prompt({
			    title: '请输入优先级',
			    formType: 0 //prompt风格，支持0-2
			}, function(level){			
				if (isNaN(level)){
					top.layer.msg("优先级必须为数字！")
					return;
			    }else{
			    	if(level.indexOf(".")>0){
			    		top.layer.msg("优先级必须为整数！")
			    		return;
			    	}
			    }
				$('.level').each(function(){
					$(this).val(level);
				});	
				top.layer.closeAll();			    								    
			});
		})
		//详情
		$('.showDetail').click(function(){
			var id = $('input[name="id"]').val();
			showChannelInfo(id);
		})
		
		
		
		
	})
	
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	
	form.on('submit(channel-submit)', function(data) {
		$.ajax({
			url : "/admin/saveChannel.action",
			type : "post",
			data : data.field,
			dataType : "json",
			cache : false,
			success : function(data){
				if(data && data.success){
					top.layer.msg(data.msg);
				}
			},
			error : function(){
				top.layer.msg('服务器连接失败');
			}
		})
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	
	form.on('checkbox(packActive)', function(data){
		var tr = $(data.elem).parent().parent();
		var packId = $(data.elem).data("id");
		var discount = tr.find('.discount').val();
		var level = tr.find('.level').val();
		if(data.elem.checked){
			if( level == ''){
				top.layer.msg("优先级还没有设置！！");
				data.elem.checked = false;
				form.render("checkbox");
				return;
			}else{
				if(isNaN(level)){
					top.layer.msg("优先级必须是数字");
				}else{
					if(level.indexOf(".")>0){
			    		top.layer.msg("优先级必须为整数！")
			    		return;
			    	}
				}
				//设置流量包
				addPackCode(packId,discount,level);
			}
		}else{
			//取消流量包
			removePackCode(packId,discount,level);
		}
	});
	function addPackCode(packId,discount,level){
		var packCode = $('#packCode').val();
		var packCodeArray = packCode.split(",");
		var isExist = false;
		$.each(packCodeArray,function(index,item){
			var codeItemArray = item.split(":");
			if(codeItemArray[0] == packId){
				isExist = true;
			}
		})
		if(!isExist){
			packCodeArray.push(packId+":"+discount+":"+level);
		}
		$('#packCode').val(packCodeArray.join(","));
	}
	function removePackCode(packId,discount,level){
		var packCode = $('#packCode').val();
		var packCodeArray = packCode.split(",");
		
		for(var i=0;i<packCodeArray.length;i++){
			var codeItemArray = packCodeArray[i].split(":");
			if(codeItemArray[0] == packId){
				var index = packCodeArray.indexOf(packCodeArray[i]);
				if( index > -1){
					packCodeArray.splice(index,1);
				}
			}
		}
//		$.each(packCodeArray,function(index,item){
//			var codeItemArray = item.split(":");
//			if(codeItemArray[0] == packId){
//				var index = packCodeArray.indexOf(item);
//				if( index > -1){
//					packCodeArray.splice(index,1);
//				}
//			}
//		})
		$('#packCode').val(packCodeArray.join(","));
	}
	
	
	function initPackageList(){
		$(".layui-table tbody").html('');
		var packageName = $('#packageName').val();
		var packPageNum = $('#pageNo').val();
		var packCode = $('#packCode').val();
		
		$.ajax({
			url : '/admin/packageList.action',
			type : 'post',
			data : {"name":packageName,"pageNo":packPageNum,"pageSize":10},
			dataType : 'json',
			cache : false,
			success : function(data){
				var html = [];
				if (data && data.list.length > 0) {
					var packArray = packCode.split(",");
					$.each(data.list, function(index, item) {
						html.push('<tr>')
						html.push('<td>'+item.name+'</td>')
						
						var isExist = false;
						var packInfo ;
						$.each(packArray ,function(index,pitem){
							packInfo =  pitem.split(":");
							if(packInfo[0] == item.id){
								isExist = true;
								return false;
							}
						})
						if(isExist){
							html.push('<td><input type="checkbox" data-id="'+item.id+'" lay-filter="packActive" checked name="" lay-skin="primary"></td>')
							html.push('<td><input width="50" type="text"  value="'+packInfo[1]+'" class="layui-input pack-input discount"></td>')
							html.push('<td><input width="50" type="number"  value="'+packInfo[2]+'" class="layui-input pack-input level"></td>')
						}else{
							html.push('<td><input type="checkbox" data-id="'+item.id+'" lay-filter="packActive" name="" lay-skin="primary"></td>')
							html.push('<td><input width="50" type="text"  value="10" class="layui-input pack-input discount"></td>')
							html.push('<td><input width="50" type="number"  value=""  class="layui-input pack-input level"></td>')
						}
						html.push('</tr>')
					})
					
					$(".layui-table tbody").append(html.join(''));
					base.initpage(data, isinitpage, function(isinit) {
						isinitpage = isinit;
						initPackageList();
					})
					form.render('checkbox');
				}
				
				
			},
			error : function(){
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
	
	exports('addChannel');

})