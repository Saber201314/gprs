KISSY.add(function(S,Node,Dom,Event,IO,Tabs,Auth,AuthMsgs){
        var $ = Node.all;

        _Terrority = {
        	packageTab:function(){
        		new Tabs({
                    srcNode: '#package-tabs'
                }).render();
        	},
        	initTerrority:function(){
	    		var terrorityArr = [],location = $("#location").val();
	    		if(location && location != ''){
	    			terrorityArr.push("<option>"+location+"</option>");
	    		}
		    	terrorityArr.push("<option value=''>"+"请选择"+"</option>");	    			
	    		terrorityArr.push("<option>"+"北京"+"</option>");
	    		terrorityArr.push("<option>"+"天津"+"</option>");
	    		terrorityArr.push("<option>"+"河北"+"</option>");	
	    		terrorityArr.push("<option>"+"山西"+"</option>");
	    		terrorityArr.push("<option>"+"内蒙古"+"</option>");
	    		terrorityArr.push("<option>"+"辽宁"+"</option>");	
	    		terrorityArr.push("<option>"+"吉林"+"</option>");
	    		terrorityArr.push("<option>"+"黑龙江"+"</option>");
	    		terrorityArr.push("<option>"+"上海"+"</option>");	
	    		terrorityArr.push("<option>"+"江苏"+"</option>");
	    		terrorityArr.push("<option>"+"浙江"+"</option>");
	    		terrorityArr.push("<option>"+"安徽"+"</option>");	
	    		terrorityArr.push("<option>"+"福建"+"</option>");
	    		terrorityArr.push("<option>"+"江西"+"</option>");
	    		terrorityArr.push("<option>"+"山东"+"</option>");	
	    		terrorityArr.push("<option>"+"河南"+"</option>");
	    		terrorityArr.push("<option>"+"湖北"+"</option>");
	    		terrorityArr.push("<option>"+"湖南"+"</option>");	
	    		terrorityArr.push("<option>"+"广东"+"</option>");
	    		terrorityArr.push("<option>"+"广西"+"</option>");
	    		terrorityArr.push("<option>"+"海南"+"</option>");	
	    		terrorityArr.push("<option>"+"重庆"+"</option>");	
	    		terrorityArr.push("<option>"+"四川"+"</option>");	
	    		terrorityArr.push("<option>"+"贵州"+"</option>");	
	    		terrorityArr.push("<option>"+"云南"+"</option>");
	    		terrorityArr.push("<option>"+"西藏"+"</option>");
	    		terrorityArr.push("<option>"+"陕西"+"</option>");
	    		terrorityArr.push("<option>"+"甘肃"+"</option>");
	    		terrorityArr.push("<option>"+"宁夏"+"</option>");
	    		terrorityArr.push("<option>"+"青海"+"</option>");
	    		terrorityArr.push("<option>"+"新疆"+"</option>");
	    		$(".terrority-select").append(terrorityArr.join(""));
			},
        	initTerrority1:function(){
	    		var terrorityArr = [],location = $("#location").val();
	    		if(location && location != ''){
	    			terrorityArr.push("<option>"+location+"</option>");
	    		}
		    	terrorityArr.push("<option value=''>"+"请选择"+"</option>");
		    	terrorityArr.push("<option>"+"全国"+"</option>");
	    		terrorityArr.push("<option>"+"北京"+"</option>");
	    		terrorityArr.push("<option>"+"天津"+"</option>");
	    		terrorityArr.push("<option>"+"河北"+"</option>");	
	    		terrorityArr.push("<option>"+"山西"+"</option>");
	    		terrorityArr.push("<option>"+"内蒙古"+"</option>");
	    		terrorityArr.push("<option>"+"辽宁"+"</option>");	
	    		terrorityArr.push("<option>"+"吉林"+"</option>");
	    		terrorityArr.push("<option>"+"黑龙江"+"</option>");
	    		terrorityArr.push("<option>"+"上海"+"</option>");	
	    		terrorityArr.push("<option>"+"江苏"+"</option>");
	    		terrorityArr.push("<option>"+"浙江"+"</option>");
	    		terrorityArr.push("<option>"+"安徽"+"</option>");	
	    		terrorityArr.push("<option>"+"福建"+"</option>");
	    		terrorityArr.push("<option>"+"江西"+"</option>");
	    		terrorityArr.push("<option>"+"山东"+"</option>");	
	    		terrorityArr.push("<option>"+"河南"+"</option>");
	    		terrorityArr.push("<option>"+"湖北"+"</option>");
	    		terrorityArr.push("<option>"+"湖南"+"</option>");	
	    		terrorityArr.push("<option>"+"广东"+"</option>");
	    		terrorityArr.push("<option>"+"广西"+"</option>");
	    		terrorityArr.push("<option>"+"海南"+"</option>");	
	    		terrorityArr.push("<option>"+"重庆"+"</option>");	
	    		terrorityArr.push("<option>"+"四川"+"</option>");	
	    		terrorityArr.push("<option>"+"贵州"+"</option>");	
	    		terrorityArr.push("<option>"+"云南"+"</option>");
	    		terrorityArr.push("<option>"+"西藏"+"</option>");
	    		terrorityArr.push("<option>"+"陕西"+"</option>");
	    		terrorityArr.push("<option>"+"甘肃"+"</option>");
	    		terrorityArr.push("<option>"+"宁夏"+"</option>");
	    		terrorityArr.push("<option>"+"青海"+"</option>");
	    		terrorityArr.push("<option>"+"新疆"+"</option>");
	    		$(".terrority-select").append(terrorityArr.join(""));
			},			
			initAmount:function(){				
	    		var amountArr = [],amount = $("#amount").val();
	    		if(amount && amount != 0 && amount != ''){
	    	    	amountArr.push("<option value='"+amount+"'>"+amount+"M</option>");
	    		}
    			amountArr.push("<option value='0'>"+"请选择"+"</option>");		
	    		amountArr.push("<option value='5'>"+"5M"+"</option>");
	    		amountArr.push("<option value='10'>"+"10M"+"</option>");
	    		amountArr.push("<option value='20'>"+"20M"+"</option>");	
	    		amountArr.push("<option value='30'>"+"30M"+"</option>");
	    		amountArr.push("<option value='50'>"+"50M"+"</option>");
	    		amountArr.push("<option value='70'>"+"70M"+"</option>");	
	    		amountArr.push("<option value='100'>"+"100M"+"</option>");
	    		amountArr.push("<option value='150'>"+"150M"+"</option>");
	    		amountArr.push("<option value='200'>"+"200M"+"</option>");	
	    		amountArr.push("<option value='300'>"+"300M"+"</option>");	
	    		amountArr.push("<option value='500'>"+"500M"+"</option>");	
	    		amountArr.push("<option value='700'>"+"700M"+"</option>");	
	    		amountArr.push("<option value='1024'>"+"1G"+"</option>");
	    		amountArr.push("<option value='2048'>"+"2G"+"</option>");
	    		amountArr.push("<option value='3072'>"+"3G"+"</option>");
	    		amountArr.push("<option value='4096'>"+"4G"+"</option>");
	    		amountArr.push("<option value='6144'>"+"6G"+"</option>");
	    		amountArr.push("<option value='11264'>"+"11G"+"</option>");
	    		$(".amount-select").append(amountArr.join(""));
			}
        	
	    };
		return GPRS.Terrority = _Terrority;
	},{
		requires : [
		            'node','dom','event','ajax','tabs','kg/auth/2.0.6/','kg/auth/2.0.6/plugin/msgs/'
		]
	}
);