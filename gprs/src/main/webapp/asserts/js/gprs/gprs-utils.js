var options=[];
options.push('<option value="">请选择</option>');
$(function(){
	$.ajax({
	    url:"userListByLevel.action",
	    type:"post",
	    dataType:'json',
	    cache:false,
	    success:function(data){
			if (data.success) {				
				var list=data.module;
				$.each(list,function(i,parent){
					insertAgent(parent,0);
					var userList=parent.userList;
					if(userList){
						insertList(userList, 1);
					}
				});
				$("#agent-level").append(options.join(""));
				var account = $("#agent_account").val();
				$("#agent-level option[value='"+account+"']").attr("selected","selected"); 
				$("#agent-level").val($("#agent_account").val());
			    $("#agent-level").chosen({search_contains:true});

			    if($("#agent-levels").length>0){
					$("#agent-levels").append(options.join(""));
				    $("#agent-levels").chosen({search_contains:true});			    			    	
			    }
			}
			else{
				layer.alert(data.error);
			}
	    },
	    error:function(){
	    }
	});		
});

function insertList(agentList,level){
	$.each(agentList,function(i,parent){
		insertAgent(parent,level);
		var userList=parent.userList;
		if(userList){
			insertList(userList, level+1);
		}
	});
}
function insertAgent(agent,level){
	var str=[];
	for(var i=0;i<level;i++){
		//str.push("--");
	}
	str.push(agent.name);
	options.push('<option value="'+agent.username+'">'+str.join("")+'</option>');
}
