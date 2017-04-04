KISSY.add(function(S,Node,Dom,Event,PopupDatePicker,DateFormat){
        var $ = Node.all;
        var picker=null;
        var dateFormat=new DateFormat("yyyy-MM-dd");
        
        _Calendar = {
    		createPicker:function() {
    			picker = new PopupDatePicker({
                    shim:true,
                    showTime:true
                });

                picker.on('blur', function () {
                    picker.hide();
                });
            },
	    	init:function(){
	    		this.createPicker();
	    		Event.on(".date","click",function(evt){
	    			var input=this;
	    			picker.set("align",{
	    				 node: input,
	    				 points: ['bl', 'tl']
	    				 
	    			});
                    picker.on('select', function (e) {
                        if (e.value) {
                        	Dom.val(picker.get('align').node,dateFormat.format(e.value));
                        } else {
                        	Dom.val(input,'');
                        }
                        picker.hide();
                    });
                    picker.show();
                    picker.focus();
	    		});
			}
	    };
		return GPRS.Calendar = _Calendar;
	},{
		requires : [
		            'node','dom','event','date/popup-picker',"date/format"
		]
	}
);