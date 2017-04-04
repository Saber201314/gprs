KISSY.add(function(S,Node,Dom,Event,IO,Auth,AuthMsgs){
        var $ = Node.all;

        _Wap2 = {
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
        							alert("恭喜你，成功充值"+module.amount+"M流量，运营商到账时间可能会有延迟，请耐心等待。到账后您会收到短信提醒，若120分钟后未能到账，系统会自动给您退款。");
        							form.reset();
        						}
        						else{
        							alert(data.error);
        						}
        	    	        },
        	    	        error:function(){
        	    	        	alert("服务器连接失败，请重试！");
        	    	        }
        	    	    });
        			}).fail(function(){
        				return;
        			});
    			});
        	},
        	mobileListener:function(){
        		var self=this;
        		Event.on("#mobile-input","keyup",function(){
        			var mobile=S.trim(Dom.val(this));
        			if(mobile.length==11&&mobile.charAt(0)=='1'){
        				self.getMobileCity(mobile);
        			}
        		});
        		var mobile=S.trim(Dom.val("#mobile-input"));
    			if(mobile.length==11&&mobile.charAt(0)=='1'){
    				self.getMobileCity(mobile);
    			}
        	},
        	getMobileCity:function(mobile){
        		var self=this;
        		new IO({
	    	        url:'getMobileInfo.action',
	    	        type:'get',
	    	        serializeArray:false,
	    	        dataType:'json',
	    	        cache:false,
	    	        data:{
	    	        	mobile:mobile
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
							var type=data.module.type,location=data.module.location;
							Dom.val("#type-input",type);
							Dom.val("#location-input",location);
							if(type==1){
								Dom.html(".msg-wrapper",location+" "+"移动");
							}
							else if(type==2){
								Dom.html(".msg-wrapper",location+" "+"联通");
							}
							else{
								Dom.html(".msg-wrapper",location+" "+"电信");
							}
							self.showPackageList();
						}
						else{
							alert(data.error);
						}
	    	        },
	    	        error:function(){
	    	        	alert("服务器连接失败，请重试！");
	    	        }
	    	    });
        	},
        	showPackageList:function(){
        		Dom.css($(".single-select",".package-list"),"display","none");
        		Dom.removeClass($(".single-select-selected",".package-list"),"single-select-selected");
        		Dom.html(".money","0");
//        		alert(Dom.css($("li",".buy-list"),"display"));
        		Dom.css($("li",".buy-list"),"display","none");
        		
        		var type=Dom.val("#type-input"),location=Dom.val("#location-input");
        		
        		if(type==''||location==''){
        			return;
        		}
        		var n=0;
        		var display={};
        		S.each($(".single-select",".package-list"),function(pkg){
        			if(!eval('display['+Dom.attr(pkg,"amount")+"]")){
        				if(parseInt(Dom.attr(pkg,"type"))==type){
            				var suportLoc=Dom.attr(pkg,"locations");
            				if(suportLoc=='全国'||suportLoc.indexOf(location)!=-1){
            					Dom.css(pkg,"display","inline-block");
            					eval('display['+Dom.attr(pkg,"amount")+"]=1")
            					n++;
            				}
            			}
        			}
        		});
        		if(n==0){
        			Dom.html(".msg","没有支持的流量包。");
        		}
        	},
        	singleSelect:function(){
        		var self=this;
        		Event.on(".single-select","click",function(evt){
        			evt.preventDefault();
        			
        			var div=Dom.parent(this,"div");
        			Dom.removeClass($(".single-select",div),"single-select-selected");
        			Dom.addClass(this,"single-select-selected");
        			Dom.css($("li",".buy-list"),"display","none");
        			
        			var amount=Dom.attr(this,"amount"),type=Dom.val("#type-input"),location=Dom.val("#location-input");
            		S.each($(".single-select",".package-list"),function(pkg){
        				if(parseInt(Dom.attr(pkg,"amount"))==amount&&parseInt(Dom.attr(pkg,"type"))==type){
        					var suportLoc=Dom.attr(pkg,"locations");
        					
            				if(suportLoc=='全国'||suportLoc.indexOf(location)!=-1){
            					var paymoney=Dom.attr(pkg,"paymoney");
            					
            					if(Dom.attr(pkg,"location-type")=='1'){
            						Dom.css(".li-country","display","-webkit-box");
            						Dom.html("#money-country",paymoney);
            					}
            					else{
            						Dom.css(".li-province","display","-webkit-box");
            						Dom.html("#money-province",paymoney);
            					}
            				}
            			}
            		});
        		});
        	},
        	payByAlipay:function(){
        		var self=this;
        		Event.on(".pay-by-alipay","click",function(evt){
        			evt.preventDefault();
        			
        			var mobile=S.trim(Dom.val("#mobile-input"));
        			var account=Dom.val("#hidden-mobile"),password=Dom.val("#hidden-password"),siteId=Dom.val("#hidden-siteId");
        			if(mobile==''){
        				layer.alert("请输入手机号码");
        				return;
        			}
        			var pkg=$(".single-select-selected",".package-list")[0];
        			if(!pkg){
        				layer.alert("请选择流量包");
        				return;
        			}
        			new IO({
    	    	        url:Dom.val("#charge-url"),
    	    	        type:'post',
    	    	        serializeArray:false,
    	    	        dataType:'json',
    	    	        cache:false,
    	    	        data:{
    	    	        	mobile:mobile,
    	    	        	packageId:Dom.attr(pkg,"id"),
    	    	        	locationType:Dom.hasClass(this,"country")?1:2,
    	    	        	useHongbao:Dom.val("#use-hongbao"),
    	    	        	account:account,
    	    	        	password:password,
    	    	        	siteId:siteId
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
    							var order=data.module;
    							if(order.submitType==6){
    								layer.alert("恭喜，成功充值"+order.amount+"M流量，由于运营商的流量充值到账时间可能有延迟，请耐心等待，到账后会有短信提醒，若120分钟后未到账，系统会自动退款到您的账户中。由于运营商的规定，月底最后二天中国移动的流量充值业务会顺延至次月初到账，所以不建议您月底最后二天充值。");
    								window.location="pay2.action?mobile="+account+"&password="+password+"&siteId="+siteId;
    							}
    							else{
    								window.location='../wap/alipayWeb.action?orderId='+order.id+"&account="+account+"&password="+password+"&siteId="+siteId;
    							}
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
	    		this.singleSelect();
	    		this.mobileListener();
	    		this.showPackageList();
				this.payByCard();
				this.payByAlipay();
			}
	    };
		return GPRS.Wap2 = _Wap2;
	},{
		requires : [
		            'node','dom','event','ajax','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
		]
	}
);