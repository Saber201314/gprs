KISSY.add(function(S,Node,Dom,Event,IO,Auth,AuthMsgs){
        var $ = Node.all,doc=document,ie = S.UA.ie;
        _Post = {
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
        	post:function(config){
        		var auth = new Auth('#submit-form');
        		auth.set('submitTest',false);
        	    auth.plug(new AuthMsgs());
        	    auth.render();
        	    
        	    var self=this;
        		this.asyncLoading.init();
        		
        		Event.on(".submit-btn","click",function(evt){
        			evt.preventDefault();
        			auth.test().then(function(){
        				if($("#gonggao").length>0){
            				$("#gonggao").val($("#editor1").html());       					
        				}
        				
        				var form = $("#submit-form")[0], 
        				url = Dom.attr(form, "action"), 
        				type = Dom.attr(form, "method");
        				new IO({
        	    	        url:url,
        	    	        type:type,
        	    	        form:form,
        	    	        serializeArray:false,
        	    	        dataType:'json',
        	    	        cache:false,
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
        							self.asyncLoading.hide();
        							if(config&&config.showModule){
        								layer.alert(data.module);
        							}
        							else{
        								layer.alert("操作成功！");
            							if(config&&config.successUrl){
            								window.location=config.successUrl;                							
            							}      
        							}
        						}
        						else{      							       							        							        							        				      					        							
    							    layer.alert(data.error,{closeBtn:0,skin: 'layui-layer-lan'},function(){
    							    		if(data.module == "未到账退款"){
    							    			window.location = "../query/chargeOrderList.jsp";
        							    		return;   							    			
    							    		} 
	            							if(config&&config.successUrl){            								
	            								window.location=config.successUrl;                							
	            							}     							    	
    							    	}
    							    ); 							  							    
        							/*self.asyncLoading.hide();
        							layer.alert(data.error);*/
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
					if (confirm("确实要取消吗？")) {
						window.location = config.cancelUrl;
					}
				});
        	},
        	simplePost:function(){        		   		
        		var self=this;
        		self.asyncLoading.init();
        		
        		/*Event.on(".simple-callback-triggle","click",function(evt){
        			evt.preventDefault();
        			if(!confirm("确实要提交吗？")){
        				return;
        			}

           			var triggle=this;
        			var url = Dom.attr(triggle,"href");
	    			//询问框
	    			layer.confirm('确定要回调吗？', {
	    			  btn: ['确定','取消'], //按钮
	    			  icon:3
	    			}, function(){
	    				IO({
	    	    	        url:url,
	    	    	        type:"get",
	    	    	        dataType:'json',
	    	    	        cache:false,
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
	    							layer.alert("操作成功！");
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
        		});*/
        		
        		Event.on(".simple-post-triggle","click",function(evt){
        			evt.preventDefault();
        			if(!confirm("确实要提交吗？")){
        				return;
        			}
        			var triggle=this;
    				IO({
    	    	        url:Dom.attr(triggle,"href"),
    	    	        type:"get",
    	    	        dataType:'json',
    	    	        cache:false,
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
    							alert("操作成功！");
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
        	deleteAll:function(){
        		Event.on(".select-all","click",function(){
        			if(this.checked){
        				Dom.attr($(".check-item","#delete-form"),"checked","checked");
        			}
        			else{
        				Dom.attr($(".check-item","#delete-form"),"checked",false);
        			}
        		});
        		Event.on(".delete-list","click",function(evt){
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
        			if(!tip||tip==''){
        				tip="确定要删除选中的"+total+"条数据吗?";
        			}
    				if(!confirm(tip)){
    					return;
    				}
    				
    				IO({
    	    	        url:Dom.attr("#delete-form","action"),
    	    	        type:'post',
    	    	        form:form,
    	    	        dataType:'json',
    	    	        serializeArray:false,
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
        	loadAgentLevel:function(){
        		var select=$("#agent-level")[0];
        		var options=[];
        		options.push('<option value="">-请选择-</option>');
        		IO({
	    	        url:"userListByLevel.action",
	    	        type:"post",
	    	        dataType:'json',
	    	        cache:false,
	    	        success:function(data){
						if (!data) {
							return;
						}
						if (data.success) {
							var list=data.module;
							S.each(list,function(parent){
								insertAgent(parent,0);
								var userList=parent.userList;
								if(userList){
									insertList(userList, 1);
								}
							});
							Dom.html(select,options.join(""));
							Dom.val(select,Dom.attr(select,"data-account"));
						}
						else{
							layer.alert(data.error);
						}
	    	        },
	    	        error:function(){
	    	        }
	    	    });
        		function insertList(agentList,level){
        			S.each(agentList,function(parent){
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
        	},        	
        	loadAgentLevels:function(){
        		var select=$("#agent-levels")[0];
        		var options=[];
        		options.push('<option value="">-请选择-</option>');
        		IO({
	    	        url:"userListByLevel.action",
	    	        type:"post",
	    	        dataType:'json',
	    	        cache:false,
	    	        success:function(data){
						if (!data) {
							return;
						}
						if (data.success) {
							var list=data.module;
							S.each(list,function(parent){
								insertAgent(parent,0);
								var userList=parent.userList;
								if(userList){
									insertList(userList, 1);
								}
							});
							Dom.html(select,options.join(""));
							Dom.val(select,Dom.attr(select,"data-account"));	
						}
						else{
							layer.alert(data.error);
						}
	    	        },
	    	        error:function(){
	    	        }
	    	    });
        		function insertList(agentList,level){
        			S.each(agentList,function(parent){
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
        				str.push("--");
        			}
        			str.push(agent.username);
        			options.push('<option value="'+agent.username+'">'+str.join("")+'</option>');
        		}
        	},        	
        	initLocation:function(){
        		var selections=$("input","#showProvinces"),selectAll=$("#select-all-province")[0];
        		var locations=Dom.attr("#showProvinces","data");
        		if(locations!=''){
        			if(locations=='全国'){
        				Dom.attr(selectAll,"checked","checked");
        				Dom.attr(selections,"checked","checked");
        			}
        			else{
        				
        				S.each(selections,function(selection){
        					if(locations.indexOf(Dom.val(selection))!=-1){
        						Dom.attr(selection,"checked","checked");
        					}
        				});
        			}
        		}
        		Event.on(selectAll,"click",function(){
        			if(this.checked){
        				Dom.attr(selections,"checked","checked");
        			}
        			else{
        				Dom.attr(selections,"checked",false);
        			}
        		});
        		Event.on(selections,"click",function(){
        			if(!this.checked){
        				Dom.attr(selectAll,"checked",false);
        			}
        			else{
        				var b=true;
        				for(var i=0;i<selections.length;i++){
        					if(!selections[i].checked){
        						b=false;
        						break;
        					}
        				}
        				if(b){
        					Dom.attr(selectAll,"checked","checked");
        				}
        			}
        		});
	    	},
	    	exportMobile:function(){
        		Event.on(".export-mobile","click",function(evt){
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
            		window.location="exportChargeMobile.action?"+params;
        		});
        	},	    	
        	/*exportChargeOrderExcel:function(){
	    		Event.on(".export-excel-datas","click",function(evt){	   			
	    			evt.preventDefault(); 
	    			var params=IO.serialize("#ydForm");
	    			window.location="exportExcelChargeOrderDatas.action?"+params;	    			
	    		});
	    	},*/
	    	exportChargeLogExcel:function(){
	    		Event.on(".export-excel-datas","click",function(evt){	
	    			evt.preventDefault();
	    			var params=IO.serialize("#ydForm");
	    			window.location="exportExcelChargeLogDatas.action?"+params;	    			
	    		});
	    	},
	    	exportAgentDatas:function(){
	    		Event.on(".export-excel-datas","click",function(evt){	
	    			evt.preventDefault();
	    			window.location="exportAgentInfoDatas.action";	    			
	    		});	    		
	    	},
	    	exportAgentChargeDatas:function(){
	    		Event.on(".export-excel-datas","click",function(evt){	
	    			evt.preventDefault();
	    			var params=IO.serialize("#ydForm");
	    			window.location="exportAgentChargeDatas.action?"+params;	        			
	    		});	    		
	    	},	    	
	    	searchPackage:function(){
	    		Event.on("#searchPakcages","click",function(evt){	
	    			evt.preventDefault();
	    			var packageCode = $("#channelPackages").val();
	    			var packageName = $("#package_name").val();
	    			var id = $("input[name='channel.id']").val();
    				IO({
    	    	        url:"getPackageByName.action",
    	    	        data:{"packageName":packageName,"id":id,"packageCode":packageCode},
    	    	        dataType:'json',
    	    	        cache:false,
    	    	        success:function(data){
    	    	        	$("#nation_package tbody").empty();
    						if (!data) {
    							return;
    						}if (data && data.length) {
								var html = [];
    							for(var i = 0;i<data.length;i++){
    								if(data[i].locationType == 1){
    									html.push('<tr>');
    									html.push('<td width="200">'+data[i].name+'</td>');
    									var id = data[i].id;
    									var nSel = data[i].nSel;
    									var checked = "";
    									if(nSel == 1){
    										var checked = "checked=\"checked\"";
    									}   								
    									html.push('<td width="70"><div class="checkbox"><label><input class="tab-checkbox ace" onclick="clickCheckBox(this);" type="checkbox" data-id="'+id+'" '+checked+'/><span class="lbl"></span></label></div></td>');
    									var discount = data[i].discount?data[i].discount:'10';
    									html.push('<td><input class="tab-text discount" type="text" width="50" value="'+discount+'"/></td>');
    									var level = data[i].level?data[i].level:'';
    									html.push('<td><input class="tab-text level" type="text" width="50" value="'+level+'"/></td>');
    									html.push('</tr>');
    								}  								
    							}
    							$("#nation_package tbody").append(html.join(""));
    						}
    	    	        },
    	    	        error:function(){
    	    	        	layer.alert("服务器连接失败，请重试！");
    	    	        }
    	    	    });   			
	    		});	    			    		
	    	},
	    	loadResumeTotalDetail:function(){
				IO({
	    	        url:"home.action",
	    	        data:{},
	    	        dataType:'json',
	    	        cache:false,
	    	        success:function(data){
	    	        	if(data != null){
	    	        		if (data.islogin>0) {
	    	        			data = data.data;
	    	        			var html = [],graph_data=[];
		    	        		for(var i=0;i<data.length-1;i++){
		    	        			html.push("<tr>");
		    	        			html.push("<td width='50'>"+(i+1)+"</td>");
			    	        		html.push("<td>"+data[i].account+"</td>");
			    	        		var name = data[i].name ? data[i].name:"";
			    	        		
			    	        		html.push("<td>"+name+"</td>");
		    	        			html.push("<td>"+data[i].resumePrice+"</td>");
		    	        			html.push("<td>"+data[i].remainPrice+"</td>");
		    	        			html.push("</tr>");
		    	        			
		    	        			if(i != data.length-2){
		    	        				graph_data.push(data[i]);	    	        				
		    	        			}	    	        			
		    	        		}
		    	        		//$("#gonggao").append(data[data.length-1].gonggao);
		    	        			    	        		    	        		
	        					$("#resume_detail_table tbody").append(html.join("")); 	
	        					$("#resume_detail_header").width($("#resume_detail_table tbody").width()-2);		    	        		
		    	        		/*Morris.Line({
		    	        			  element: 'graph',
		    	        			  data: graph_data,
		    	        			  xkey: 'account',
		    	        			  ykeys: ['resumePrice', 'remainPrice'],
		    	        			  labels: ['消费额', '余额'],
		    	        			  parseTime: false
		    	        			});	*/  
							}else{
								top.location.href="/index.jsp";
							}
	    	        			        		
	    	        	}	
	    	        },
	    	        error:function(){
	    	        	layer.alert("服务器连接失败，请重试！");
	    	        }});
	    	},
	    	submitCacheData:function(){
	    		Event.on(".submit-channel","click",function(evt){			    			
        			var idList=[];
        			S.each($(".check-item"),function(item){
        				if(item.checked){
        					idList.push(Dom.val(item));
        				}
        			});
        			var total = idList.length;
        			var params=null;
        			
        			if(total==0){
        				params = [];
        				params.push("account=");
        				params.push($("#agent-level").val());
        				params.push("&");
        				params.push("mobile=");
        				var mobile = S.trim($("#mobile").val());
        				params.push(mobile);
        				params.push("&");
        				params.push("location=");
        				params.push($(".terrority-select").val());
        				params.push("&");	
        				params.push("from=");
        				params.push($("#start").val());
        				params.push("&");
        				params.push("to=");
        				params.push($("#end").val());
        				params.push("&");        				
        				params.push("type=");
        				params.push($("#type").val());
        				params.push("&");	
        				params.push("amount=");
        				params.push($(".amount-select").val());
        				params.push("&");		
        				params.push("locationType=");
        				params.push($(".locationType-select").val());
        			}
        			var content = "";
        			if(total == 0){
        				content = "确定要提交数据到通道吗？";
        				params = params.join("");
        			}else{
        				content = "确定要提交这"+total+"条数据到通道吗?";
						params=IO.serialize("#delete-form");
        			}	
     			
	    			//询问框
	    			layer.confirm(content, {
	    			  btn: ['确定','取消'], //按钮
	    			  icon:3
	    			}, function(){
						IO({
			    	        url:"submitCacheDataToChannel.action",
			    	        data:params,
			    	        dataType:'json',
			    	        cache:false,
			    	        serializeArray:false,
			    	        success:function(data){
			    	        	if(!data.success){
			    	        		layer.alert(data.error);
			    	        		$(".submit-channel").css({color:"red"});
			    	        		return;
			    	        	}	
			    	        	layer.alert("已经提交！");		
			    	        	location.reload();
			    	        },
			    	        error:function(){
			    	        	layer.alert("服务器连接失败，请重试！");
			    	        }});  	    				

	    			}, function(){

	    			});
	    				    			  			
	    		});    		
	    	},
	    	stopSubmitCacheData:function(){
	    		Event.on(".stop_submit-channel","click",function(evt){
	    			//询问框
	    			layer.confirm("确定要终止缓存数据的提交吗？", {
	    			  btn: ['确定','取消'], //按钮
	    			  icon:3
	    			}, function(){
						IO({
			    	        url:"stopCacheDataToChannel.action",
			    	        data:{},
			    	        dataType:'json',
			    	        cache:false,
			    	        serializeArray:false,
			    	        success:function(data){
			    	        	layer.alert("终止成功！");	
			    	        	$(".submit-channel").css({color:"#ffffff"});
			    	        },
			    	        error:function(){
			    	        	layer.alert("服务器连接失败，请重试！");
			    	        }});  	    				

	    			}, function(){

	    			});	    			
	    		});
	    	},
	    	bulkFailCacheOrder:function(){				    		
	    		Event.on(".fail-cache-order","click",function(evt){
        			var idList=[];
        			S.each($(".check-item"),function(item){
        				if(item.checked){
        					idList.push(Dom.val(item));
        				}
        			});
        			        			
        			var total = idList.length;
    				var params = null;					
           			if(total==0){
            				params = [];
            				params.push("queryChargeOrderDO.account=");
            				params.push($("#agent-level").val());
            				params.push("&");
            				params.push("queryChargeOrderDO.mobile=");
            				var mobile = S.trim($("#mobile").val());
            				params.push(mobile);
            				params.push("&");
            				params.push("queryChargeOrderDO.location=");
            				params.push($(".terrority-select").val());
            				params.push("&");	
            				params.push("queryChargeOrderDO.from=");
            				params.push($("#start").val());
            				params.push("&");
            				params.push("queryChargeOrderDO.to=");
            				params.push($("#end").val());
            				params.push("&");        				
            				params.push("queryChargeOrderDO.type=");
            				params.push($("#type").val());
            				params.push("&");	
            				params.push("queryChargeOrderDO.amount=");
            				params.push($(".amount-select").val());
            				params.push("&");		
            				params.push("queryChargeOrderDO.locationType=");
            				params.push($(".locationType-select").val());
            			}
            			var content = "";
            			if(total == 0){
            				content = "确定要提交数据充值失败吗？";
            				params = params.join("");
            			}else{
            				content = "确定要提交这"+total+"条数据充值失败吗?";
    						params=IO.serialize("#delete-form");
            			}					        			
//        			if(idList.length==0){
//        				layer.alert("没有勾选任何数据！");
//        				return;
//        			}
//        			
//        			var total = idList.length;
//        			params=IO.serialize("#delete-form");	    			
	    			
	    			//询问框
	    			layer.confirm(content, {
	    			  btn: ['确定','取消'], //按钮
	    			  icon:3
	    			}, function(){
						IO({
			    	        url:"bulkFailCacheOrder.action",
			    	        data:params,
			    	        dataType:'json',
			    	        cache:false,
			    	        serializeArray:false,
			    	        success:function(data){
			    	        	layer.alert("操作成功！");	
			    	        	location.reload();
			    	        },
			    	        error:function(){
			    	        	layer.alert("服务器连接失败，请重试！");
			    	        }});  	    				

	    			}, function(){

	    			});
	    				    			  			
	    		}); 	    			    		
	    	},
	    	bulkSucessOrder:function(){ 		
	    		Event.on(".success-order","click",function(evt){
	    			var params = null;
        			var idList=[];
        			S.each($(".check-item"),function(item){
        				if(item.checked){
        					idList.push(Dom.val(item));
        				}
        			});
        			if(idList.length==0){
        				layer.alert("没有勾选任何数据！");
        				return;
        			}
        			var total = idList.length;
        			params=IO.serialize("#delete-form");
    			    			
	    			//询问框
	    			layer.confirm('确定要提交这'+total+'条数据充值成功吗？', {
	    			  btn: ['确定','取消'], //按钮
	    			  icon:3
	    			}, function(){
						new IO({
			    	        url:"bulkSuccessOrder.action",
			    	        data:params,
			    	        dataType:'json',
			    	        serializeArray:false,			    	        
			    	        cache:false,
			    	        success:function(data){
			    	        	location.reload();			    	        	
			    	        },
			    	        error:function(){
			    	        	layer.alert("服务器连接失败，请重试！");
			    	        }});  	    				

	    			}, function(){

	    			});
	    				    			  			
	    		}); 	    			    		
	    	},
	    	bulkFailOrder:function(){
	    		Event.on(".fail-order","click",function(evt){
	    			var params = null;
        			var idList=[];
        			S.each($(".check-item"),function(item){
        				if(item.checked){
        					idList.push(Dom.val(item));
        				}
        			});
        			if(idList.length==0){
        				layer.alert("没有勾选任何数据！");
        				return;
        			}
        			var total = idList.length;
        			params=IO.serialize("#delete-form");
    			    			
	    			//询问框
	    			layer.confirm('确定要提交这'+total+'条数据充值失败吗？', {
	    			  btn: ['确定','取消'], //按钮
	    			  icon:3
	    			}, function(){
						new IO({
			    	        url:"bulkFailOrder.action",
			    	        data:params,
			    	        dataType:'json',
			    	        serializeArray:false,			    	        
			    	        cache:false,
			    	        success:function(data){
			    	        	location.reload();			    	        	
			    	        },
			    	        error:function(){
			    	        	layer.alert("服务器连接失败，请重试！");
			    	        }});  	    				

	    			}, function(){

	    			});
	    				    			  			
	    		}); 	    		
	    		
	    	},
	    	tipMessageByChangePrice:function(){
	    		var digitUppercase = function(n) {  
	    	        var fraction = ['角', '分'];  
	    	        var digit = [  
	    	            '零', '壹', '贰', '叁', '肆',  
	    	            '伍', '陆', '柒', '捌', '玖'  
	    	        ];  
	    	        var unit = [  
	    	            ['元', '万', '亿'],  
	    	            ['', '拾', '佰', '仟']  
	    	        ];  
	    	        var head = n < 0 ? '欠' : '';  
	    	        n = Math.abs(n);  
	    	        var s = '';  
	    	        for (var i = 0; i < fraction.length; i++) {  
	    	            s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');  
	    	        }  
	    	        s = s || '整';  
	    	        n = Math.floor(n);  
	    	        for (var i = 0; i < unit[0].length && n > 0; i++) {  
	    	            var p = '';  
	    	            for (var j = 0; j < unit[1].length && n > 0; j++) {  
	    	                p = digit[n % 10] + unit[1][j] + p;  
	    	                n = Math.floor(n / 10);  
	    	            }  
	    	            s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;  
	    	        }  
	    	        return head + s.replace(/(零.)*零元/, '元')  
	    	            .replace(/(零.)+/g, '零')  
	    	            .replace(/^整$/, '零元整');  
	    	    };  	    		
	    			
	    		Event.on(".charge_price","change",function(evt){
	    			var chargePrice = S.trim($(this).val());
	    			if(chargePrice && chargePrice != ""){
	    				chargePrice = parseFloat(chargePrice);
	    				if(isNaN(chargePrice)){
	    					$("#tipMessage").hide();
	    					return;
	    				}
	    				$("#tipMessage").text(digitUppercase(chargePrice));
	    				$("#tipMessage").show();
	    			}else{
	    				$("#tipMessage").hide();
	    			}
	    				    			
	    		});	    				    		
	    	},
	    	bulkCallBackOrder:function(){
	    		Event.on(".callback-order","click",function(evt){
	    			var params = null;
        			var idList=[];
        			S.each($(".check-item"),function(item){
        				if(item.checked){
        					idList.push(Dom.val(item));
        				}
        			});
        			if(idList.length==0){
        				layer.alert("没有勾选任何数据！");
        				return;
        			}
        			var total = idList.length;
        			params=IO.serialize("#delete-form");
    			    			
	    			//询问框
	    			layer.confirm('确定要回调'+total+'条数据吗？', {
	    			  btn: ['确定','取消'], //按钮
	    			  icon:3
	    			}, function(){
						new IO({
			    	        url:"bulkCallBackOrder.action",
			    	        data:params,
			    	        dataType:'json',
			    	        serializeArray:false,			    	        
			    	        cache:false,
			    	        success:function(data){
			    	        	location.reload();			    	        	
			    	        },
			    	        error:function(){
			    	        	layer.alert("服务器连接失败，请重试！");
			    	        }});  	    				

	    			}, function(){

	    			});
	    				    			  			
	    		}); 	    		
	   	    		
	    	},
	    	queryChargeInfo:function(){
	    		Event.on("#query_charge_info","click",function(evt){
	    			$("#charge_info_table tbody").empty(); 	    	   		
	    	   		var params=IO.serialize("#syForm2");  			  
	    			new IO({
	    				url : "getChargeChartsList.action",
	    				type : "post",
	    				data : params,
	    				dataType : 'json',
	    				cache : false,
	    				success : function(data) {
	    					if(data && data.length > 0) {
	    						var html = [];
	    						var total = 0;
	    						for(var i =0;i<data.length;i++){
	    							total += data[i].charge_price;							
	    						}
	    						html.push("<tr>");
	    						html.push("<td>总计</td>");
	    						html.push("<td></td>");
	    						html.push("<td><span class='label label label-success arrowed-in arrowed-in-right'>￥"+parseFloat(total).toFixed(2)+"</span></td>");
	    						html.push("</tr>");	
	    										
	    						for(var i=0;i<data.length;i++){
	    							html.push("<tr>");
	    							html.push("<td>"+data[i].name+"</td>");
	    							html.push("<td>"+data[i].account+"</td>");
	    							html.push("<td><span class='label label label-success arrowed-in arrowed-in-right'>￥"+parseFloat(data[i].charge_price).toFixed(2)+"</span></td>");
	    							html.push("</tr>");
	    										
	    						}					
	    						$("#charge_info_table tbody").append(html.join("")); 	
	    					}
	    				},
		    	        error:function(){
		    	        	layer.alert("服务器连接失败，请重试！");
		    	        }});
	    		});		    			   			    			    		
	    	},
	    	getChannelResource:function(){
	    		$("#channel_resource_table tbody").empty();
    			new IO({
    				url : "getChannelResource.action",
    				type : "post",
    				data : {},
    				dataType : 'json',
    				cache : false,
    				success : function(data) {
    					if(data != null && data.length>0){
    						var html = [];
    						var n = 0;
    				     	for(var i =0;i<data.length;i++){
    				     		html.push("<tr>");	
    				     		n++;
    				     		html.push("<td width='50'>"+n+"</td>");	
    				     		var merchant = data[i].merchant;
    				     		if(merchant == 1){
    				     			merchant = "移动";
    				     		}else if(merchant == 2){
    				     			merchant = "联通";
    				     		}else if(merchant == 3){
    				     			merchant = "电信";
    				     		}else{
    				     			merchant = "";
    				     		}    				     		
    				     		html.push("<td>"+merchant+"</td>");	
    				     		html.push("<td>"+data[i].district+"</td>");
    				     		var locationType = data[i].location_type;
    				     		if(locationType == 1){
    				     			locationType = "漫游";
    				     		}else if(locationType == 2){
    				     			locationType = "非漫游";
    				     		}else{
    				     			locationType = "";
    				     		}
    				     		html.push("<td>"+locationType+"</td>");
    				     		html.push("<td  style='color:#438eb9;'>"+data[i].standard+"</td>");
    				     		html.push("<td>"+data[i].in_discount+"</td>");
    				     		var status = data[i].status;
    				     		if(status == 1){
    				     			status = "<span class='label label-success'>正常</span>";
    				     		}else{
    				     			status = "<span class='label label-danger'>维护</span>";
    				     		}
    				     		html.push("<td>"+status+"</td>");
    				     		var payBill = data[i].pay_bill;
    				     		if(payBill == 1){
    				     			payBill = "带票";
    				     		}else if(payBill == 2){
    				     			payBill = "不带票";
    				     		}else{
    				     			payBill = "";
    				     		}    				     		
    				     		html.push("<td>"+payBill+"</td>");
    				     		var policy = data[i].policy;
    				     		if(policy == 1){
    				     			policy = "不限价";
    				     		}else{
    				     			policy = "限价";
    				     		}    				     		
    				     		html.push("<td>"+policy+"</td>");
    				     		html.push("<td>"+data[i].message+"</td>");
    				     		html.push("<td>"+data[i].channelName+"</td>");
    				     		html.push("<td width='100'>");
    				     		html.push('<a class="badge badge-info simple-detail-triggle" href="javascript:void(0);" onclick="updateChannelResource('+data[i].id+')">修改</a>');
    				     		html.push('&nbsp;<a class="badge badge-info simple-detail-triggle" href="javascript:void(0);" onclick="deleteChannelResource('+data[i].id+')">删除</a>');
    				     		html.push("</td>");
    				     		html.push("</tr>");    				     		
    				     	}
        					$("#channel_resource_table tbody").append(html.join("")); 	
        					$("#channel_resource_table_header").width($("#channel_resource_table tbody").width());
        	  				/*$("#channel_resource_table_header tbody tr th").each(function(i,k){
        	  					$(this).width($("#channel_resource_table tbody tr:eq(0) td:eq("+i+")").width());		    						
        	  				});*/ 						
    						
    					} 					
    			}});
	    	}
	    };
		return GPRS.Post = _Post;
},{
    requires : [
                'node','dom','event','ajax','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
            ]
        });