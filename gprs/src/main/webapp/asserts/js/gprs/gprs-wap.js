KISSY.add(function(S,Node,Dom,Event,IO,Auth,AuthMsgs){
        var $ = Node.all;

        _Wap = {
	    	acc:function(){
	    		var triggles=$("li","#tab_t");
	    		Event.on(triggles,"click",function(){
	    			var _index=Dom.index(this);
	    			Dom.removeClass(triggles,"cur")
	    			Dom.addClass(this,"cur");
	    			
	    			var couts=$(".cont","#tab_c");
	    			Dom.removeClass(couts,"show")
	    			Dom.addClass(couts[_index],"show");
	    		});
	    	},
	    	payByCard:function(){
        		var auth = new Auth('#pay-by-card-form');
        		auth.set('submitTest',false);
        	    auth.plug(new AuthMsgs());
        	    auth.render();
        	    
        	    var self=this;
        		
        		Event.on("#pay-by-card-btn","click",function(evt){
        			evt.preventDefault();
        			auth.test().then(function(){
        				var form = $("#pay-by-card-form")[0], url = Dom.attr(form, "action"), type = Dom.attr(form, "method");
        				new IO({
        	    	        url:url,
        	    	        type:type,
        	    	        form:form,
        	    	        serializeArray:false,
        	    	        dataType:'json',
        	    	        cache:false,
        	    	        beforeSend:function (xhr) {
        	    	        	Dom.css("#mark","display","block");
        	    	        },
        	    	        complete:function(){
        	    	        	Dom.css("#mark","display","none");
        	    	        },
        	    	        success:function(data){
        	    	        	Dom.css("#mark","display","none");
        						if (!data) {
        							return;
        						}
        						if (data.success) {
        							var module=data.module;
        							layer.alert("恭喜你，成功充值"+module.amount+"M流量。");
        							form.reset();
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
        	payByAlipay:function(){
        		var self=this;
        		Event.on(".pay-by-alipay","click",function(evt){
        			evt.preventDefault();
        			
        			var mobile=S.trim(Dom.val("#mobile-input"));
        			if(mobile==''){
        				layer.alert("请输入手机号码");
        				return;
        			}
        			var pkg=$(".single-select-selected",".package-list")[0];
        			if(!pkg){
        				layer.alert("请选择流量包");
        				return;
        			}
        			var time=$(".single-select-selected",".take-time")[0];
        			if(!time){
        				layer.alert("请选择生效时间");
        				return;
        			}
        			var chargeTime=Dom.hasClass(time,"immediately")?0:1;
        			new IO({
    	    	        url:Dom.val("#charge-url"),
    	    	        type:'post',
    	    	        serializeArray:false,
    	    	        dataType:'json',
    	    	        cache:false,
    	    	        data:{
    	    	        	mobile:mobile,
    	    	        	packageId:Dom.attr(pkg,"id"),
    	    	        	chargeTime:chargeTime,
    	    	        	locationType:Dom.hasClass($(".single-select-selected",".location-type")[0],"country")?1:2
    	    	        },
    	    	        beforeSend:function (xhr) {
    	    	        	Dom.css("#mark","display","block");
    	    	        },
    	    	        complete:function(){
    	    	        	Dom.css("#mark","display","none");
    	    	        },
    	    	        success:function(data){
    						if (!data) {
    							return;
    						}
    						if (data.success) {
    							alert("提交成功！");
    							var id=data.module.id;
    							window.location='../wap/alipayWeb.action?orderId='+id;
    						}
    						else{
    							layer.alert(data.error);
    						}
    	    	        },
    	    	        error:function(){
    	    	        	layer.alert("服务器连接失败，请重试！");
    	    	        }
    	    	    });
        		});
        	},
	    	init:function(){
				this.acc();
				this.payByCard();
				this.payByAlipay();
			}
	    };
		return GPRS.Wap = _Wap;
	},{
		requires : [
		            'node','dom','event','ajax','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
		]
	}
);