KISSY.add(function(S, Node, Dom, Event, IO, Tabs, Auth, AuthMsgs) {
	var $ = Node.all;
	var g_data = {}, flag = 1;
	var index = 0;
	_Charts = {
		packageTab : function() {
			new Tabs({
				srcNode : '#package-tabs'
			}).render();
		},
		init : function() {
			var params = IO.serialize("#pageForm");
			IO({
				url : "chargeChartsList.action",
				type : "post",
				data : params,
				dataType : 'json',
				cache : false,
				success : function(data) {
					if (!data) {
						return;
					}
					if (data) {
						g_data = data;
						var day_data = [];
						for (var i = 0; i < data.length; i++) {
							day_data.push(data[i]);
						}

						Morris.Line({
							element : 'graph',
							resize : true,
							data : day_data,
							xkey : ['option_date'],
							lineColors : [ '#0178bb' ],
							ykeys : [ 'resume_price'],
							labels : [ '消费额' ],
							xLabelAngle : 60
						});
					} else {
						alert(data.error);
					}
				},
				error : function() {

				}
			});

		},
		switchChartView : function() {
			Event.on(".switch-perspectives", "click", function(evt) {
				evt.preventDefault();
				$("#graph").empty();
				var day_data = [];
				for (var i = 0; i < g_data.length; i++) {
					day_data.push(g_data[i]);
				}
				if (flag == 1) {
					flag = 2;
					Morris.Bar({
						element : 'graph',
						resize : true,
						data : day_data,
						xkey : ['option_date'],
						lineColors : [ '#0178bb' ],
						ykeys : [ 'resume_price'],
						labels : [ '消费金额' ],
						xLabelAngle : 60
					});
				} else if (flag == 2) {
					flag = 3;
					Morris.Line({
						element : 'graph',
						resize : true,
						data : day_data,
						xkey : ['option_date'],
						lineColors : [ '#0178bb' ],
						ykeys : [ 'resume_price' ],
						labels : [ '消费金额' ],
						xLabelAngle : 60
					});
				} else if (flag == 3) {
					flag = 1;
					Morris.Area({
						element : 'graph',
						resize : true,
						data : day_data,
						xkey : ['option_date'],
						lineColors : [ '#0178bb' ],
						ykeys : [ 'resume_price' ],
						labels : [ '消费金额' ],
						xLabelAngle : 60
					});
				}
			});
		},
    	initChargeCharts:function(){
			new IO({
    	        url:"getChargeChartsList.action",
    	        data:{},
    	        dataType:'json',
    	        serializeArray:false,			    	        
    	        cache:false,
    	        success:function(data){
    	        	if(data && data.length>0){
    					Morris.Bar({
    						element : 'graph',
    						resize : true,
    						data : data,
    						xkey : ['account'],
    						ymax : 'auto 1000',
    						lineColors : [ '#0178bb' ],
    						ykeys : [ 'charge_price' ],
    						labels : [ '充值金额' ],
    						parseTime: false,
    						xLabelAngle : 100
    					});	
    					$("#graph").resize()
    	        	}	    	        	
    	        },
    	        error:function(){
    	        	layer.alert("服务器连接失败，请重试！");
    	        }}); 	    			
    	},
    	loadLocationReportList:function(){	
    		function initLocationReportList(){
        		$("#graph").empty();      		
	    		var params=IO.serialize("#ydForm");
				IO({
	    	        url:"locationReportList.action",
	    	        data:params,
	    	        dataType:'json',
	    	        cache:false,
	    	        success:function(data){
	    	        	 layer.close(index);  
	    	        	if(data && data.length>0){	    	        			    	        		
	    	        		Morris.Bar({
	    	        			  element: 'graph',
	    	        			  data: data,
	    	        			  xkey: ['location'],
	    	        			  ykeys: ['cnt'],
	    	        			  labels: ['订单数量'],
	    	        			  parseTime: false
	    	        		});		    	        		
	    	        	}	
	    	        },
	    	        error:function(){
	    	        	layer.alert("服务器连接失败，请重试！");
	    	    }});	    			
    			   			
    		}
    		
    		Event.on(".btn_search","click",function(evt){
        		index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    			initLocationReportList();
    		});
 	    		
    	}
	};
	return GPRS.Charts = _Charts;
}, {
	requires : [ 'node', 'dom', 'event', 'ajax', 'tabs', 'kg/auth/2.0.6/',
			'kg/auth/2.0.6/plugin/msgs/' ]
});