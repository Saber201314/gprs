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
			$('.discount').each(function(index,item){
				if( item.value == "" ){
					errorFlag = true;
					return;
				}
			})
			if(!errorFlag){
				$('.packActive').each(function(index,item){
					item.checked = true ;
					var tr = $(item).parent().parent();
					var packId = $(item).data("id");
					var discount = tr.find('.discount').val();
					var checked = tr.find('.paybill').is(':checked');
					addPackCode(packId,discount,checked);
				})
				form.render('checkbox');
			}else{
				top.layer.msg("折扣还没有设置！！");
			}
		})
		//全取消
		$('.cancelAll').click(function(){
			$('.packActive').each(function(index,item){
				item.checked = false ;
				var tr = $(item).parent().parent();
				var packId = $(item).data("id");
				var discount = tr.find('.discount').val();
				removePackCode(packId);
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
					top.layer.msg("折扣必须为数字！")
					return;
			    } 
				$('.discount').each(function(){
					$(this).val(discount);
				});	
				top.layer.closeAll();			    								    
			});
		})
		//详情
		$('.showDetail').click(function(){
			var id = $('input[name="id"]').val();
			if(id!=""){
				showPircePaperInfo(id);
			}
		})
		
		
		
		
	})
	form.on('checkbox(allPaybill)', function(data) {
			var child = $(data.elem).parents('table').find(
					'tbody .paybill');
			child.each(function(index, item) {
				item.checked = data.elem.checked;
			});
			form.render('checkbox');
		});
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	
	form.on('submit(channel-submit)', function(data) {
		$.ajax({
			url : "/admin/savePricePaper.action",
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
		var checked = tr.find('.paybill').is(':checked');
		if(data.elem.checked){
			if( discount == ''){
				top.layer.msg("折扣还没有设置！！");
				data.elem.checked = false;
				form.render("checkbox");
				return;
			}else{
				if(isNaN(discount)){
					top.layer.msg("折扣必须是数字");
					return;
				}
				//设置流量包
				addPackCode(packId,discount,checked);
			}
		}else{
			//取消流量包
			removePackCode(packId);
		}
	});
	function addPackCode(packId,discount,checked){
		var packCode = $('#packCode').val();
		var packCodeArray = packCode.split(",");
		var paybill = "";
		if(checked){
			paybill = "1";
		}else{
			paybill = "0";
		}
		var isExist = false;
		$.each(packCodeArray,function(index,item){
			var codeItemArray = item.split(":");
			if(codeItemArray[0] == packId){
				isExist = true;
			}
		})
		if(!isExist){
			packCodeArray.push(packId+":"+discount+":"+paybill);
		}
		$('#packCode').val(packCodeArray.join(","));
	}
	function removePackCode(packId){
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
						html.push('<td>'+item.id+'</td>')
						html.push('<td>'+item.name+'</td>')
						
						var isExist = false;
						var packInfo ;
						$.each(packArray ,function(index,pitem){
							if(pitem != ""){
								packInfo =  pitem.split(":");
								if(packInfo[0] == item.id){
									isExist = true;
									return false;
								}
							}
							
						})
						if(isExist){
							
							html.push('<td><input type="checkbox" data-id="'+item.id+'" lay-filter="packActive" checked class="packActive" lay-skin="primary"></td>')
							html.push('<td><input width="50" type="text"  value="'+packInfo[1]+'" class="layui-input pack-input discount"></td>')
							if(packInfo[2] == 1){
								html.push('<td><input type="checkbox" class="paybill"  lay-skin="primary"  checked=""></td>')
							}else{
								html.push('<td><input type="checkbox" class="paybill"  lay-skin="primary"></td>')
							}
							
						}else{
							html.push('<td><input type="checkbox" data-id="'+item.id+'" lay-filter="packActive" class="packActive" lay-skin="primary"></td>')
							html.push('<td><input width="50" type="text"  value="" class="layui-input pack-input discount"></td>')
							html.push('<td><input type="checkbox" class="paybill"  lay-skin="primary"></td>')
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
	     		html.push('<tr>');		     		
	     		html.push('<td>'+data[i].name+'</td>');	
	     		
	     		var indiscount = data[i].inDiscount == undefined ? "" : data[i].inDiscount;
	     		html.push('<td>'+indiscount +'</td>');
	     		html.push('<td>'+data[i].outDiscount+'</td>');
	     		if(data[i].payBill == 1){
	     			html.push('<td>带票</td>');
	     		}else{
	     			html.push('<td>不带票</td>');
	     		}
	     		html.psuh('<td>'+data[i].priority+'</td>');
	     		var channelName = data[i].channelName == undefined ? "" : data[i].channelName;
	     		html.push('<td>'+channelName+'</td>');		     		
	     		html.push('</tr>');
	     	}	
	     	$("#single-page-content tbody").append(html.join(''));	
		}		
	}
	
	exports('addPricePaper');

})