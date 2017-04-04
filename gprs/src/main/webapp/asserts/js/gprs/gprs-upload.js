KISSY.add(function(S,Node,Dom,Event,Uploader,DefaultTheme,Auth){
        var $ = Node.all;

        _Upload = {
    		init:function(baseUrl){
    			S.each($(".file-input"),function(input){
    				var li=Dom.parent(input,"li");
    				var uploader = new Uploader(input,{
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
    	    		        
    	    		        Dom.val($(".upload-input-hidden",li),result.url);
    	    		        Dom.attr($(".upload-pic",li),"src",baseUrl+result.url);
    	    		    });
    	    		    uploader.on('error', function (ev) {
    	    		        alert(ev.msg);
    	    		    });
    			});
        	}
	    };
		return GPRS.Upload = _Upload;
	},{
		requires : [
		            'node','dom','event','kg/uploader/2.0.2/index','kg/uploader/3.0.3/themes/default/index','kg/uploader/3.0.3/plugins/auth/auth','kg/uploader/3.0.3/themes/default/style.css'
		]
	}
);