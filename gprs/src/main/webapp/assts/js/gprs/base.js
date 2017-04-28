/*
 * 通用的方法
 * 
 * 
 */
layui.define([ 'jquery', 'form' ], function(exports) {
	var $ = layui.jquery;
	var form = layui.form();
	var obj = {
		/*
		 * 初始化代理商
		 */
		initagent : function() {
			
			$.ajax({
				url : "/admin/query/userListByLevel.action",
				type : "post",
				dataType : 'json',
				cache : false,
				success : function(data) {
					if (data && data.success) {
						var list = data.module;
						var options = [];
						$.each(list, function(i, item) {
							options.push('<option value="' + item.username
									+ '">' + item.name + '</option>');
						})
						$('#agent').append(options);
						form.render('select');
					} else {
						top.window.location = "/login.jsp";
					}
				},
				error : function() {

				}
			});
		},
		/*
		 * 初始化通道
		 */
		initchannel : function(){
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
		        		form.render('select');
		        	}
		        }
			});
		}

	}
	exports('base',obj);

})