KISSY.add(function(S,Node,Dom,Event,IO,Auth,AuthMsgs){
        var $ = Node.all,doc=document,ie = S.UA.ie;

        _Charge = {
    		asyncLoading : {
    			init : function() {
    				this.mask = doc.createElement('div');
    				this.mask.className = 'ui-dialog-mask';
    				Dom.css(this.mask, 'height', Dom.docHeight()+ 'px');
    				Dom.css(this.mask, 'line-height', Dom.docHeight()+ 'px');
    				Dom.html(this.mask,'<span class="process"></span>');
    				doc.body.appendChild(this.mask);
    				// add by weiming.zw @ 20110913 for ie6 select
    				// bug
    				if (ie && 6 === ie) {
    					var iframe = doc.createElement('iframe');
    					iframe.src = 'about:blank';
    					iframe.setAttribute('frameborder', '0');
    					iframe.setAttribute('scrolling', 'no');
    					Dom.addClass(iframe, 'overlay-iframe');
    					Dom.css(iframe, 'opacity', '0');
    					Dom.css(iframe, 'zIndex', '-1');
    					Dom.css(iframe, 'height', '100%');
    					Dom.css(iframe, 'width', '100%');
    					this.mask.appendChild(iframe);
    				}
    				;
    			},
    			show : function() {
    				Dom.css(this.mask, 'visibility', 'visible');
    				Dom.css(this.mask, 'height', Dom.docHeight()+ 'px');
    				Dom.addClass(doc.body, 'fix-select');
    			},
    			hide : function() {
    				Dom.css(this.mask, 'visibility', '');
    				Dom.css(this.mask, 'height', '0');
    				Dom.removeClass(doc.body, 'fix-select');
    			}
    		},
        	showMsg:function(msg){
        		Dom.html(".msg-wrapper",msg);
        		Dom.css(".msg-wrapper","display","inline-block");
        	},
        	mobileListener:function(){
        		var self=this;
        		Event.on("#mobile-input","keyup",function(){
        			var mobile=S.trim(Dom.val(this));
        			if(mobile.length==11&&mobile.charAt(0)=='1'){
        				self.getMobileCity(mobile);
        			}
        			else{
        				Dom.val("#type-input","");
						Dom.val("#location-input","");
						Dom.css(".msg-wrapper","display","none");
						Dom.css($(".single-select",".package-list"),"display","none");
		        		Dom.html(".money","0");
        			}
        		});
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
	    	        	Dom.css(".msg-wrapper","display","none");
	    	        	Dom.css(".process","display","inline-block");
	    	        },
	    	        complete:function(){
	    	        	Dom.css(".process","display","none");
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
								self.showMsg(location+" "+"移动");
							}
							else if(type==2){
								self.showMsg(location+" "+"联通");
							}
							else{
								self.showMsg(location+" "+"电信");
							}
							self.showPackageList();
						}
						else{
							layer.alert(data.error);
						}
	    	        },
	    	        error:function(){
	    	        	layer.alert("服务器连接失败，请重试！");
	    	        }
	    	    });
        	},
        	showPackageList:function(){
        		Dom.css($(".single-select",".package-list"),"display","none");
        		Dom.removeClass($(".single-select-selected",".package-list"),"single-select-selected");
        		Dom.html(".money","0");
        		Dom.html(".msg","");
        		
        		var type=Dom.val("#type-input"),location=Dom.val("#location-input");

        		if($(".single-select-selected",".location-type").length==0||type==''||location==''){
        			return;
        		}
        		var locationType=Dom.hasClass($(".single-select-selected",".location-type")[0],"country")?1:2;
        		var n=0;
        		S.each($(".single-select",".package-list"),function(pkg){
        			if(parseInt(Dom.attr(pkg,"type"))==type&&parseInt(Dom.attr(pkg,"location-type"))==locationType){
        				var suportLoc=Dom.attr(pkg,"locations");
        				if(suportLoc=='全国'||suportLoc.indexOf(location)!=-1){
        					Dom.css(pkg,"display","inline-block");
        					n++;
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
        			
        			if(Dom.hasClass(this,"location-type-item")){
        				self.showPackageList();
        			}
        			
        			if(Dom.hasClass(this,"package-list-item")){
        				Dom.html(".money",Dom.attr(this,"paymoney"));
        			}
        		});
        	},
        	submit:function(){
        		var self=this;
        		Event.on(".sendBtn","click",function(evt){
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
    	    	        	self.asyncLoading.show();
    	    	        },
    	    	        complete:function(){
    	    	        	self.asyncLoading.hide();
    	    	        },
    	    	        success:function(data){
    						if (!data) {
    							return;
    						}
    						if (data.success) {
    					        layer.alert('提交成功', {
    					            skin: '',
    					            icon:1
    					        }, function(){
    					        	window.location='../query/chargeOrderList.jsp';
    					        });   							
    						}
    						else{
    							layer.alert(data.error);
    						}
    	    	        },
    	    	        error:function(){
    	    	        	layer.alert("服务器连接失败，请重试！!");
    	    	        }
    	    	    });
        		});
        	},
	    	init:function(){
	    		this.submit();
	    		this.singleSelect();
	    		this.mobileListener();
	    		this.asyncLoading.init();
			}
	    };
		return GPRS.Charge = _Charge;
	},{
		requires : [
		            'node','dom','event','ajax','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
		]
	}
);