layui.define(['base' ], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var base = layui.base;
	var laydate =  layui.laydate;
	var validateTime = {
			elem : document.getElementById('validateTime'),
			max : '2099-06-16 23:59:59',
			format : 'YYYY-MM-DD',
			istime : true,
			istoday : false,
			choose : function(datas) {
				
			}
		};
	
	$(function(){
		
		changeCompatible($("#compatible").val());
		$('#validateTime').click(function() {
			laydate(validateTime);
		})
		
		
	})
	
	$('input[name="username"]').on('blur',function(){
		var id = $('input[name="id"]').val();
		if(id == ""){
			$.ajax({
				url : '/admin/checkUsername.action?username='+$(this).val(),
				type : 'get',
				dataType : 'json',
				success : function(data){
					if( data && data.success){
						$('#hint').css("display","block");
						$('#hint').css("color","green");
						$('#hint').text("账号可以使用");
					}else{
						$('#hint').css("display","block");
						$('#hint').css("color","red");
						$('#hint').text("账号已占用");
					}
				},
				error : function(){
					top.layer.msg("连接服务器失败");
				}
				
			})
		}
		
	})
	
	
	$('#genApiKey').on("click",function(){
		
		top.layer.confirm('是否确定重新生成密钥', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url : '/admin/genApiKey.action',
					type : 'get',
					dataType : 'json',
					success : function(data){
						if(data && data.success){
							$('input[name="apiKey"]').val (data.key);
						}else{
							top.layer.msg("生成失败");
						}
					},
					error : function(){
						top.layer.msg("连接服务器失败");
					}
					
					
				})
				top.layer.closeAll();
			}, function(){
			  
			});
	})
	
	
	/*
	 * 监听select选择
	 */
	form.on('select(isSecretKey)', function(data){
		changeCompatible(data.value)
	}); 
	function changeCompatible(value){
		if(value==1){
			//设置可用
			$('input[name="apiPassword"]').attr("disabled",false);
//			$('input[name="apiKey"]').attr("disabled",false);
			$('#genApiKey').attr("disabled",false);
			
			//设置必填项
			$('input[name="apiPassword"]').attr("lay-verify","required")
			
			//设置背景样式
			$('input[name="apiPassword"]').css("background-color","#fff")
//			$('input[name="apiKey"]').css("background-color","#fff")
			$('#genApiKey').removeClass("layui-btn-disabled");
		}else{
			//设置不可用
			$('input[name="apiPassword"]').attr("disabled",true);
//			$('input[name="apiKey"]').attr("disabled",true);
			$('#genApiKey').attr("disabled",true);
			
			//取消必填项
			$('input[name="apiPassword"]').removeAttr("lay-verify");
			
			//设置背景样式
			$('input[name="apiPassword"]').css("background-color","#f1f1f1")
//			$('input[name="apiKey"]').css("background-color","#f1f1f1")
			$('#genApiKey').addClass("layui-btn-disabled");
		}
	}
	
	
	
	
	/*
	 * 拦截表单提交
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		console.log(data.field)
		var limits = '';
		$('input[name="limits"]').each(function(){
			limits += $(this).val() + ",";
		})	
		data.field.limits=limits;
			
		$.ajax({
			url : '/admin/saveAgent.action',
			type : 'post',
			data : data.field,
			dataType : 'json',
			success : function(data){
				if( data && data.success){
					top.layer.msg("保存成功");
					window.location.href ="/view/admin/agentmanage/agentList.jsp";
				}else{
					top.layer.msg("保存失败");
				}
			},
			error : function(){
				top.layer.msg("连接服务器失败！");
			}
		})
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	
	exports('addAgent');
})