KISSY.add(function(S,Node,Dom,Event,IO){
        var $ = Node.all;

        _Base = {
	    	acc:function(){
	    		var triggles=$(".ks-switchable-trigger","#navigation");
	    		Event.on(triggles,"click",function(){
	    			var pannel=Dom.next(this);
	    			if(pannel&&Dom.hasClass(pannel,"ks-switchable-panel")){
	    				Dom.toggleClass(pannel,"hidden");
	    			}
	    		});
	    		S.each(triggles,function(triggle){
	    			var ul=Dom.next(triggle);
	    			if($("li",ul).length==0){
	    				Dom.remove(Dom.parent(ul,"li"));
	    			}
	    		});
	    	},
	    	resize:function(){
	    		var clientHeight=document.documentElement.clientHeight,clientWidth=document.documentElement.clientWidth;
				Dom.css("#main","height",clientHeight-135+"px");
				Dom.css("#container","width",clientWidth-228+"px");
				Dom.css(".bar","width",clientWidth-300+"px");
	    	},
	    	init:function(){
				this.resize();
				this.acc();
				window.onresize = this.resize;
			}
	    };
		return GPRS.Base = _Base;
	},{
		requires : [
		            'node','dom','event','ajax'
		]
	}
);