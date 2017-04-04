KISSY.add(function(S,Node,Dom,Event,IO){
        var $ = Node.all,doc=document,ie = S.UA.ie;

        _BatchCharge = {
    		
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
        	singleSelect:function(){
        		var self=this;
        		Event.on(".single-select","click",function(){
        			var div=Dom.parent(this,"div");
        			Dom.removeClass($(".single-select",div),"single-select-selected");
        			Dom.addClass(this,"single-select-selected");
        			
        			if(Dom.hasClass(this,"location-type-item")){
        				Dom.removeClass($(".single-select-selected",".package-list"),"single-select-selected");
        				if(Dom.hasClass(this,"country")){
        					Dom.css(".contry-package","display","inline-block");
        					Dom.css(".province-package","display","none");
        				}
        				else{
        					Dom.css(".contry-package","display","none");
        					Dom.css(".province-package","display","inline-block");
        				}
        			}
        			
        			if(Dom.hasClass(this,"package-list-item")){
        				
        			}
        		});
        	},
        	clear_non_mobile:function(){
	    		Event.on(".clear_non_mobile","click",function(evt){
	    			evt.preventDefault();
	    			
	    			var oldTels=Dom.val($("#num-area")[0]).split("\n"),pattern=/^1[3|4|5|8|7][0-9]\d{8}$/,str="";
	    			var length=oldTels.length,line=0;
	    			for(var i=0;i<length;i++){
	    				var tel=oldTels[i];
	    				if(tel!=""&&pattern.test(tel)){
	    					str+=(tel+"\n");
	    					line++;
	    				}
	    			}
	    			Dom.val($("#num-area")[0],str);
	    			$("#num-count")[0].innerHTML=line;
	    		});
	    	},
	    	clear_repeat:function(){
	    		Event.on(".clear_repeat", "click",function(evt) {
	    			evt.preventDefault();
	    			
	    			var oldTels=Dom.val($("#num-area")[0]).split("\n");
	    			
	    			var tel;
	                var repeatTels = {}; //重复手机
	                var newTels = []; //过滤后手机
	                var len = oldTels.length;
	                for (var j = 0; j < len; j++) {
	                    tel = oldTels[j];
	                    if (repeatTels[tel] !== 1) {
	                        repeatTels[tel] = 1;
	                        newTels.push(tel);
	                    }
	                }
	                
	                var str="",length=newTels.length;
	                for(var i=0;i<length;i++){
	                	str+=(newTels[i]+"\n");
	                }
	                Dom.val($("#num-area")[0],str);
	                $("#num-count")[0].innerHTML=length;
				});
	    	},
	    	tongji:function(){
				Event.on($("#num-area"),"keyup",function(){
					var content=Dom.val(this),line=content.split("\n").length;
					$("#num-count")[0].innerHTML=line-1;
				});
			},
        	submit:function(){

        		var self=this;
        		Event.on(".sendBtn","click",function(evt){
        			evt.preventDefault();
        			
        			var mobiles=Dom.val($("#num-area")[0]).split("\n");
        			if(mobiles.length==0){
        				layer.alert("请输入手机号码。");
        				return;
        			}
        			
    				var mobile=0,unicom=0,telecom=0;
    				
    				var mobileItem=$(".single-select-selected",".mobile-package")[0];
    				if(mobileItem){
    					mobile=Dom.attr(mobileItem,"id");
    				}
    				var unicomItem=$(".single-select-selected",".unicom-package")[0];
    				if(unicomItem){
    					unicom=Dom.attr(unicomItem,"id");
    				}
    				var telecomItem=$(".single-select-selected",".telecom-package")[0];
    				if(telecomItem){
    					telecom=Dom.attr(telecomItem,"id");
    				}
    				if(mobile==0&&unicom==0&&telecom==0){
    					layer.alert("请至少选择一个流量包");
    					return;
    				}
    				
    				new IO({
    	    	        url:'doBatchCharge.action',
    	    	        type:'post',
    	    	        serializeArray:false,
    	    	        dataType:'json',
    	    	        cache:false,
    	    	        data:{
    	    	        	mobile:mobiles.join(","),
    	    	        	packageIdMobile:mobile,
            				packageIdUnicom:unicom,
            				packageIdTelecom:telecom,
            				takeTime:Dom.hasClass($(".single-select-selected",".take-time")[0],"immediately")?0:1,
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
    					            skin: ''
    					            ,closeBtn: 0
    					        }, function(){
    					        	window.location='../query/batchChargeList.action';
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
        		});
        	},
	    	init:function(){
	    		this.asyncLoading.init();
	    		this.submit();
	    		this.singleSelect();
	    		this.clear_non_mobile();
	    		this.clear_repeat();
	    		this.tongji();
			}
	    };
		return GPRS.BatchCharge = _BatchCharge;
	},{
		requires : [
		            'node','dom','event','ajax'
		]
	}
);