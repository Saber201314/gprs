KISSY.add(function(S,Node,Dom,Event,IO,Auth,AuthMsgs){
		var $ = Node.all,doc=document,ie = S.UA.ie;
        
        _PublishAgent = {
        	selectAll:function(){
        		Event.on(".select-all", "click",function() {
        			var tr=Dom.parent(this,"tr");
        			if(this.checked){
        				Dom.attr($(".user-limit-list",tr),"checked","checked");
        			}
        			else{
        				Dom.attr($(".user-limit-list",tr),"checked",false);
        			}
        		});
        		var trs=$("tr",".limit-setting");
        		S.each(trs,function(tr){
        			if($(".user-limit-list",tr).length==0){
        				Dom.remove(tr);
        			}
        		});
        	},
        	post:function(){
        		var auth = new Auth('#submit-form');
        		auth.set('submitTest',false);
        	    auth.plug(new AuthMsgs());
        	    auth.render();
        		
        		Event.on(".submit-btn","click",function(evt){
        			evt.preventDefault();
        			auth.test().then(function(){
        				var form = $("#submit-form")[0], url = Dom.attr(form, "action"), type = Dom.attr(form, "method");
        				var limits=$(".user-limit-list"),limitstring='';
            			S.each(limits,function(limit){
            				if(limit.checked){
            					limitstring=limitstring+Dom.val(limit)+",";
            				}
            			});
    	        		Dom.val("#hidden-user-limits",limitstring);
        				new IO({
        	    	        url:url,
        	    	        type:type,
        	    	        form:form,
        	    	        serializeArray:false,
        	    	        dataType:'json',
        	    	        cache:false,
        	    	        success:function(data){
        						if (!data) {
        							return;
        						}
        						if (data.success) {
        							window.location = 'agentList.action';
        						}
        						else{
        							layer.alert(data.error);
        						}
        	    	        },
        	    	        error:function(){
        	    	        	layer.alert("服务器连接失败，请重试！");
        	    	        }
        	    	    });
        			}).fail(function(){
        				return;
        			});
    			});
        	},
	    	init:function(){
	    		this.selectAll();
	    		this.post();
			}
	    };
		return GPRS.PublishAgent = _PublishAgent;
},{
    requires : [
                'node','dom','event','ajax','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
            ]
        });