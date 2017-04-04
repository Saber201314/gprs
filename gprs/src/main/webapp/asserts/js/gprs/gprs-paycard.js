KISSY.add(function(S,Node,Dom,Event,IO,Auth,AuthMsgs,Dialog){
        var $ = Node.all,doc=document,ie = S.UA.ie;

        _Paycard = {
    		popup : function(config) {
    			var self = this, popup = config.popup;
    			var dialog;
    			dialog = new GPRS.Dialog(
    					{
    						head : true,
    						body : (function() {
    							var temp = Dom.clone(popup, true,
    									true, true);
    							Dom.removeClass(temp, 'hidden');
    							Dom.removeAttr(temp, 'id');
    							return temp;
    						})(),
    						foot:true,
    						width : 'auto',
    						destroy : true,
    						close : true,
    						title:config.title
    					});
    			Event.on($(".content", dialog.elemBody),
    					'mouseenter mouseleave', function() {
    						Dom.toggleClass(this, 'hover');
    					});
    			config.callback(dialog);
    			dialog.show();
    		},
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
        			var div=Dom.parent(this,"li");
        			Dom.removeClass($(".single-select",div),"single-select-selected");
        			Dom.addClass(this,"single-select-selected");
        			
        			if(Dom.hasClass(this,"gprs-type-item")){
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
        	submit:function(config){
        		var auth = new Auth('#submit-form');
        		auth.set('submitTest',false);
        	    auth.plug(new AuthMsgs());
        	    auth.render();
        	    
        		var self=this;
        		Event.on(".submit-btn","click",function(evt){
        			evt.preventDefault();
        			
        			auth.test().then(function(){
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
        				Dom.val("#takeTime",Dom.hasClass($(".single-select-selected",".charge-time")[0],"immediately")?0:1);
        				Dom.val("#packageIdMobile",mobile);
        				Dom.val("#packageIdUnicom",unicom);
        				Dom.val("#packageIdTelecom",telecom);
        				
        				new IO({
        	    	        url:'doPublishPaycard.action',
        	    	        type:'post',
        	    	        serializeArray:false,
        	    	        dataType:'json',
        	    	        cache:false,
        	    	        form:"#submit-form",
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
        							alert("提交成功！");
        							window.location=config.successUrl;
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
        		});
        		Event.on(".cancel-btn", "click", function(evt) {
        			evt.preventDefault();
					if (confirm("确实要取消吗？")) {
						window.location = config.cancelUrl;
					}
				});
        	},
        	exportCard:function(){
        		Event.on(".export-card","click",function(evt){
        			evt.preventDefault();
        			
        			var params=null;
            		if(!$(".select-all")[0].checked){
            			var idList=[];
            			S.each($(".check-item"),function(item){
            				if(item.checked){
            					idList.push(Dom.val(item));
            				}
            			});
            			if(idList.length==0){
            				layer.alert("没有数据");
            				return;
            			}
            			params=IO.serialize("#delete-form");
            		}
            		else{
            			params=IO.serialize("#pageForm");
            		}
            		window.location="exportFile.action?"+params;
        		});
        	},
        	changeCardStatus:function(){
        		Event.on(".batch-change-status","click",function(evt){
        			evt.preventDefault();
        			var form=$("#pageForm")[0],total=0;
        			var selectAll=$(".select-all")[0];
        			if(form&&selectAll&&selectAll.checked){
        				total=parseInt(Dom.html("#total-record"));
        			}
        			else{
        				form=$("#delete-form")[0];
            			var idList=[];
            			S.each($(".check-item"),function(item){
            				if(item.checked){
            					idList.push(Dom.val(item));
            				}
            			});
            			total=idList.length;
        			}	
        			if(total==0){
        				layer.alert("没有数据");
        				return;
        			}
        			var tip=Dom.attr(this,"tip");
    				if(!confirm(tip)){
    					return;
    				}
    				var newStatus=0;
    				if(Dom.hasClass(this,'lock')){
    					newStatus=-1;
    				}
    				IO({
    	    	        url:'changeCardStatus.action',
    	    	        type:'post',
    	    	        form:form,
    	    	        data:{
    	    	        	newStatus:newStatus
    	    	        },
    	    	        dataType:'json',
    	    	        cache:false,
    	    	        success:function(data){
    						if (!data) {
    							return;
    						}
    						if (data.success) {
    							window.location=window.location;
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
        	distributeCard:function(){
        		var self=this;
        		Event.on(".distribute-list","click",function(evt){
        			evt.preventDefault();
        			
        			var form=$("#pageForm")[0],total=0;
        			var selectAll=$(".select-all")[0];
        			if(form&&selectAll&&selectAll.checked){
        				total=parseInt(Dom.html("#total-record"));
        			}
        			else{
        				form=$("#delete-form")[0];
            			var idList=[];
            			S.each($(".check-item"),function(item){
            				if(item.checked){
            					idList.push(Dom.val(item));
            				}
            			});
            			total=idList.length;
        			}	
        			if(total==0){
        				layer.alert("没有数据");
        				return;
        			}
    				if(!confirm("确定要划拨选中的"+total+"张卡吗?")){
    					return;
    				}
        			
        			self.popup({
    					title:'划拨卡密',
    					popup:$('#popup-distribute-card')[0],
    					callback:function(dialog){
    						Dom.html($(".select-agent",dialog.elemBody),Dom.html("#agent-level"));
    						Dom.val($(".select-agent",dialog.elemBody),"");
    						Event.on($(".distribute-btn",dialog.elemBody),"click",function(){
    							var agent=Dom.val($(".select-agent",dialog.elemBody));
    							if(agent==''){
    								layer.alert("请选择代理商");
    								return;
    							}
    							new IO({
    		    	    	        url:'distributePaycard.action',
    		    	    	        type:'post',
    		    	    	        data:{
    		    	    	        	newAgent:agent
    		    	    	        },
    		    	    	        form:form,
    		    	    	        dataType:'json',
    		    	    	        cache:false,
    		    	    	        success:function(data){
    		    						if (!data) {
    		    							return;
    		    						}
    		    						if (data.success) {
    		    							window.location=window.location;
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
    					}
    				});
        		});
        	},
	    	init:function(config){
	    		this.asyncLoading.init();
	    		this.submit(config);
	    		this.singleSelect();
	    		this.exportCard();
	    		this.changeCardStatus();
	    		this.distributeCard();
			}
	    };
		return GPRS.Paycard = _Paycard;
	},{
		requires : [
		            'node','dom','event','ajax','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/','gprs/util/dialog'
		]
	}
);