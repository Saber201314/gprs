<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
body{
	padding: 15px 0px;
	background-color: #ecf0f5;
}
.container{
	width: 100%;
}
.panel-body .charts div{
	float: left;
}
.pay-info .col{
	width : 25%;
	float: left;
}
</style>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-lg-6 agent-panel">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe609;</i>
						今日代理消费趋势
					</h3>
				</div>
				<div class="panel-body">
					<div class="charts">
						<div id="balance" style="width: 100%;height:440px;"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-6 channel-pannel">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe609;</i>
						今日消费
					</h3>
				</div>
				<div class="panel-body">
					<div class="charts">
						<div id="agent" style="width: 50%;height:380px;"></div>
						<div id="channel" style="width: 50%;height:380px;"></div>
					</div>
					<div class="pay-info">
						<div class="col">
							<div>提交成功 ： 0</div>
							<div>结算金额 ： 0</div>
							<div>通道金额 ： 0</div>
						</div>
						<div class="col">
							<div>提交成功 ： 0</div>
							<div>结算金额 ： 0</div>
							<div>通道金额 ： 0</div>
						</div>
						<div class="col">
							<div>提交成功 ： 0</div>
							<div>结算金额 ： 0</div>
							<div>通道金额 ： 0</div>
						</div>
						<div class="col">
							<div>提交成功 ： 0</div>
							<div>结算金额 ： 0</div>
							<div>通道金额 ： 0</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
var agent = echarts.init(document.getElementById('agent'));
var channel = echarts.init(document.getElementById('channel'));
var balance = echarts.init(document.getElementById('balance'));
option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:['提交成功','提交失败','充值成功','充值失败']
    },
    series: [
        {
            name:'今日统计',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:0, name:'提交成功'},
                {value:0, name:'提交失败'},
                {value:0, name:'充值成功'},
                {value:0, name:'充值失败'}
            ]
        }
    ]
};
balanceOption = {
	    title: {
	        text: '代理消费',
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['代理消费']
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            dataZoom: {
	                yAxisIndex: 'none'
	            },
	            dataView: {readOnly: false},
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    xAxis:  {
	        type: 'category',
	        boundaryGap: true,
	        data: ['代理商']
	    },
	    yAxis: {
	        type: 'value',
	        axisLabel: {
	            formatter: '{value}'
	        }
	    },
	    series: [
	        {
	            name:'代理消费',
	            type:'bar',
	            data:[0],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        }
	    ]
	};
$.ajax({
	url :'/admin/statisticsToday.action',
	type :'get',
	dataType :'json',
	success : function(data){
		if(data.success){
			var agentMoney = [];
			var channelMoney = [];
			
			var agentHtml = [];
			$.each(data.list,function(index,item){
				var temp = new Object();
				
				temp.name = item.status;
				temp.value =  item.payMoney;
				agentMoney.push(temp);
				
				
				temp = new Object();
				temp.name = item.status;
				temp.value =  item.disCountMoney;
				channelMoney.push(temp);
				
				pushItemInfo(agentHtml,item);
			})
			
			$('.channel-pannel .pay-info').html('');
			$('.channel-pannel .pay-info').append(agentHtml.join(""));
			
			option.series[0].data = agentMoney;
			option.series[0].name = "代理商统计";
			agent.setOption(option);
			option.series[0].data = channelMoney;
			option.series[0].name = "通道统计";
			channel.setOption(option);
		}
	},
	error : function(){
		top.layer.msg("连接服务器失败");
	}
})
$.ajax({
	url:"/admin/layout/home.action",
       data:{},
       dataType:'json',
       cache:false,
       success:function(data){
       	if(data != null){
       		if (data.islogin>0) {
       			balanceOption.xAxis.data = [];
       			balanceOption.series[0].data = [];
       			var account = [];
       			var payMoney = [];
       			$.each(data.data,function(index,item){
       				var temp = new Object();
       				temp.account = item.account;
       				temp.name = item.name;
       				temp.payMoney = item.resumePrice;
       				temp.total = item.remainPrice;
       				
       				account.push(item.account)
       				payMoney.push(item.resumePrice);
       			})
       			balanceOption.xAxis.data = account;
       			balanceOption.series[0].data = payMoney;
       			balance.setOption(balanceOption);
			}else{
				top.location.href="/login.jsp";
			}
       			        		
       	}	
       },
       error:function(){
       	top.layer.alert("服务器连接失败，请重试！");
       }
});
function pushItemInfo(html,item){
	html.push('<div class="col">')
	html.push('<div> '+item.status+' ：'+item.num+'</div>')
	html.push('<div>代理金额 ：'+item.payMoney+'</div>')
	html.push('<div>通道金额 ：'+item.disCountMoney+'</div>')
	html.push('</div>')
}

agent.setOption(option);
channel.setOption(option);
balance.setOption(balanceOption);

</script>
</html>