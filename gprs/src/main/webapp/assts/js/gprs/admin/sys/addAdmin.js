layui.define(['base' ], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var base = layui.base;
	var laydate =  layui.laydate;

	
	$(function(){
		
	})
	/*
	 * 检测账号是否存在
	 */
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
	/*
	 * 拦截表单提交
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		console.log(data.field)
		var limits = '';
		$('input[name="limits"]').each(function(){
			var checked=$(this).is(':checked');
			if(checked){
				limits += $(this).val() + ",";
			}
		})	
		data.field.limits=limits;
			
		$.ajax({
			url : '/admin/saveAdmin.action',
			type : 'post',
			data : data.field,
			dataType : 'json',
			success : function(data){
				if( data && data.success){
					top.layer.msg("保存成功");
					window.location.href ="/view/admin/sys/adminList.jsp";
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
	
	exports('addAdmin');
})