KISSY.add(function(S,Node,Dom,Event,IO,Tabs,Auth,AuthMsgs){
        var $ = Node.all;

        _Channel = {
        	packageTab:function(){
        		new Tabs({
                    srcNode: '#package-tabs'
                }).render();
        	},
        	submit:function(){
        		var auth = new Auth('#submit-form');
        		auth.set('submitTest',false);
        	    auth.plug(new AuthMsgs());
        	    auth.render();
        		
        		Event.on(".submit-btn","click",function(evt){
        			evt.preventDefault();
        			auth.test().then(function(){
        				var form = $("#submit-form")[0], url = Dom.attr(form, "action"), type = Dom.attr(form, "method");
        				var pkgs=[];
        				S.each($(".tab-checkbox",".ks-tabs-panel"),function(chk){
        					if(chk.checked){
        						var packageId=Dom.attr(chk,"data-id");
        						var discount=Dom.val($(".discount",Dom.parent(chk,"tr"))[0]);
        						var level=Dom.val($(".level",Dom.parent(chk,"tr"))[0]);
        						if(S.trim(discount)==''||isNaN(discount)){
        							discount='0';
        						}
        						if(S.trim(level)==''||isNaN(level)){
        							level='0';
        						}
        						pkgs.push(packageId+":"+discount+":"+level);
        					}
        				});
 //       				if(pkgs.length==0){
 //       					layer.alert("请选择支持的流量包。");
//        					return;
//        				}

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
        							layer.alert('提交成功', {
        					            skin: ''
        					            ,closeBtn: 0
        					        }, function(){
            							window.location='../channel/channelList.action';
        					        });         							

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
        			
        		Event.on(".cancel-btn", "click", function(evt) {
        			evt.preventDefault();
        			window.history.back(-1); 
				});      		
        	},
        	initChk:function(){
        		var pkgs=Dom.val("#channelPackages");
        		if(pkgs==''){
        			return;
        		}
        		var discountData={};
        		var levelData={};
        		S.each(pkgs.split(","),function(string){
        			var temp=string.split(":");
        			eval("discountData["+temp[0]+"]="+temp[1]);
        			eval("levelData["+temp[0]+"]="+temp[2]);
        		});
        		S.each($(".tab-checkbox",".ks-tabs-panel"),function(chk){
					var id=Dom.attr(chk,"data-id");
					if(discountData[id]||discountData[id]==0){
						Dom.attr(chk,"checked","checked");
						Dom.val($(".discount",Dom.parent(chk,"tr"))[0],discountData[id]);
						Dom.val($(".level",Dom.parent(chk,"tr"))[0],levelData[id]);
					}
        		});
        	},
	    	init:function(){
	    		this.packageTab();
	    		this.initChk();
	    		this.submit();	        		
			}
	    };
		return GPRS.Channel = _Channel;
	},{
		requires : [
		            'node','dom','event','ajax','tabs','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
		]
	}
);