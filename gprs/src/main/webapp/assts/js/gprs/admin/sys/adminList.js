layui.define(['base' ], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var base = layui.base;
	var laydate =  layui.laydate;
	
	var index;
	var isinitpage = false;
	
	$(function(){
		initAgentList();
	})
	/*
	 * 拦截表单提交
	 * 
	 * 
	 */
	form.on('submit(btn-submit)', function(data) {
		isinitpage=false;
		$('#pageNo').val(1);
		initAgentList();
		return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	})
	function initAgentList(){
		$('#pageNo').val("1");
		$('.layui-table tbody').html("");
		$.ajax({
			url : '/admin/query/page/adminList.action',
			type : 'post',
			data : $('form').serialize(),
			dataType : 'json',
			cache : false,
			success : function(data){
				if(data && data.success){
					var html = [];
					$.each(data.list,function(index,item){
						html.push('<tr>');
						html.push('<td>'+item.id+'</td>')
						html.push('<td>'+item.username+'</td>')
						html.push('<td>'+item.name+'</td>')
						html.push('<td>'+item.phone+'</td>')
						var date = new Date();
						date.setTime(item.optionTime);
						html.push('<td>'+date.Format("yyyy-MM-dd")+'</td>')
						
						html.push('<td><a href="/admin/tochargeAgentBalance.action?userId='+item.id+'"><button class="layui-btn layui-btn-mini">充值</button></a></td>')
						html.push('<td><a href="/admin/editAdmin.action?userId='+item.id+'"><button class="layui-btn layui-btn-mini">编辑</button></a></td>')
						html.push('</tr>');
					})
					$('.layui-table tbody').append(html.join(""));
					base.initpage(data, isinitpage, function(isinit) {
						isinitpage = isinit;
						initAgentList();
					})
					
				}
			},
			error : function(){
				
			}
			
		})
	}
	
	exports('adminList');
})