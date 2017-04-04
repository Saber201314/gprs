KISSY.add(function(S,Node,Dom,Event,IO,Tabs,Auth,AuthMsgs){
        var $ = Node.all;

        _Paper = {
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
        						var packageId=Dom.attr(chk,"data-id"),discount=Dom.val($(".tab-text",Dom.parent(chk,"tr"))[0]);
        						if(S.trim(discount)!=''&&!isNaN(discount)){
        							pkgs.push(packageId+":"+discount);
        						}
        					}
        				});
/*        				if(pkgs.length==0){
        					layer.alert("请选择支持的流量包，并填写折扣。");
        					return;
        				}
        				Dom.val("#paperItems",pkgs.join(","));*/
        				
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
            							window.location='../paper/pricePaperList.action';
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
					window.location = '../paper/pricePaperList.action';
				});
        	},
        	initChk:function(){
        		var pkgs=Dom.val("#paperItems");
        		if(pkgs==''){
        			return;
        		}
        		var pkgData={},billData={};
        		S.each(pkgs.split(","),function(string){
        			var temp=string.split(":");
        			eval("pkgData["+temp[0]+"]="+temp[1]);
        			if(temp.length==2){
        				eval("billData["+temp[0]+"]='0'");
        			}else{
        				eval("billData["+temp[0]+"]="+temp[2]);
        			}
        		});

        		S.each($(".is_support",".ks-tabs-panel"),function(chk){
					var id=Dom.attr(chk,"data-id");
					if(pkgData[id]){
						Dom.attr(chk,"checked","checked");
						Dom.val($(".tab-text",Dom.parent(chk,"tr"))[0],pkgData[id]);
					}
        		});
        		
        		S.each($(".is_bill",".ks-tabs-panel"),function(chk){
					var id=Dom.attr(chk,"data-id-1");
					if(billData[id]){
						if(billData[id] == 1)
						Dom.attr(chk,"checked","checked");
					}
        		});        		
        	},
	    	init:function(){
	    		this.packageTab();
	    		this.initChk();
	    		this.submit();
			}
	    };
		return GPRS.Paper = _Paper;
	},{
		requires : [
		            'node','dom','event','ajax','tabs','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
		]
	}
);