KISSY.add(function(S,Node,Dom,Event,IO,Uploader,DefaultTheme,Auth){
        var $ = Node.all;

        _Advertise = {
    		uploadPic:function(){
    			var uploader = new Uploader('#J_UploaderBtn',{
		          action:"uploadAdvertisePic.action"
		        });
    		    uploader.theme( new DefaultTheme({ queueTarget:'#J_UploaderQueue' }));
    		    uploader.plug(new Auth({
                    max:1000,
                    maxSize:100,
                    allowExts:'png,jpg,jpeg,PNG,JPG,JPEG'
                }));
    		    uploader.on('success', function (ev) {
    		        var result = ev.result;
    		        var html=[];
					html.push('<tr>');
					html.push('<td><input type="checkbox" class="check-item" /></td>');
					html.push('<td  class="align-left"><img height="50px" src="../../uploads/'+result.url+'" /></td>');
					html.push('<td><input value="" class="input-url" /></td>');
					html.push('<td><input value="0" class="input-level" /></td>');
					html.push('<td><a href="#" data-id="0" data-filename="'+result.url+'" class="publish-ad-pic" >提交</a></td>');
					html.push('</tr>');
					
					var table=$(".tab-list")[0];
					Dom.html(table,Dom.html(table).toLowerCase().replace("</tbody>",html.join("")+"</tbody>"));
					Event.detach(".publish-ad-pic","click");
					Event.on(".publish-ad-pic","click",function(evt){
						evt.preventDefault();
						if(!confirm("确实要提交吗")){
							return;
						}
						var triggle=this,tr=Dom.parent(this,"tr");
						IO({
	    	    	        url:"publicAdvertisePic.action",
	    	    	        type:"post",
	    	    	        data:{
	    	    	        	'advertisePic.advertiseId':Dom.val("#advertiseId"),
	    	    	        	'advertisePic.pic':Dom.attr(triggle,"data-filename"),
	    	    	        	'advertisePic.url':Dom.val($(".input-url",tr)[0]),
	    	    	        	'advertisePic.level':Dom.val($(".input-level",tr)[0])
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
    		    });
    		    uploader.on('error', function (ev) {
    		    	layer.alert(ev.msg);
    		    });
        	},
        	modifyPic:function(){
        		Event.on(".modify-ad-pic","click",function(evt){
					evt.preventDefault();
					if(!confirm("确实要提交吗")){
						return;
					}
					var triggle=this,tr=Dom.parent(this,"tr");
					IO({
    	    	        url:"modifyAdvertisePic.action",
    	    	        type:"post",
    	    	        data:{
    	    	        	'advertisePic.id':Dom.attr(triggle,"data-id"),
    	    	        	'advertisePic.type':Dom.attr(triggle,"data-type"),
    	    	        	'advertisePic.url':Dom.val($(".input-url",tr)[0]),
    	    	        	'advertisePic.level':Dom.val($(".input-level",tr)[0])
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
	    	init:function(){
	    		this.uploadPic();
	    		this.modifyPic();
			}
	    };
		return GPRS.Advertise = _Advertise;
	},{
		requires : [
		            'node','dom','event','ajax','kg/uploader/2.0.2/index','kg/uploader/3.0.3/themes/default/index','kg/uploader/3.0.3/plugins/auth/auth','kg/uploader/3.0.3/themes/default/style.css'
		]
	}
);