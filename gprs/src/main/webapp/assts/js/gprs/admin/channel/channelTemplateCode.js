layui.define([ 'base'], function(exports) {
	var $ = layui.jquery;
	var laypage = layui.laypage;
	var form = layui.form();
	var base = layui.base;

	var index;
	var isinitpage = false;

	$(function() {
		
		
		
	})
	/*
	 * 拦截表单提交
	 */
	form.on('submit(btn-submit)', function(data) {
		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	/*
	 * 获取所有选中checkbox
	 */
	var ids = [];
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
				var templateId=$('input[name="templateId"]').val();
				$.ajax({
					url : '/admin/delTemplateCode.action',
					type : 'post',
					data : {'ids':ids},
					dataType : 'json',
					cache : false,
					success : function(data) {
						if ( data && data.success){
							top.layer.msg(data.msg);
							window.location.href='/admin/query/channelTemplateCodeList.action?templateId='+templateId;
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
			top.layer.msg('请选择流量包编码');
		}
	})
	
	exports('channelTemplateCode');

})